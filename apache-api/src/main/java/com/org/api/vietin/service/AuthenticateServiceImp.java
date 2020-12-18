package com.org.api.vietin.service;

import com.org.api.vietin.common.utils.CookiesUtils;
import com.org.api.vietin.common.utils.EncryptUtils;
import com.org.api.vietin.mapper.AuthSessionMapper;
import com.org.api.vietin.mapper.UserMapper;
import com.org.api.vietin.model.dataset.AuthSessionDataset;
import com.org.api.vietin.model.dataset.UserDataset;
import com.org.api.vietin.model.dataset.UserInfoAuthDataset;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

/**
 * AuthenticateServiceImp
 *
 *
 * @since 2020/11/14
 */
@Service
public class AuthenticateServiceImp implements AuthenticateService {

    private final UserMapper userMapper;
    private final AuthSessionMapper authSessionMapper;

    public AuthenticateServiceImp(UserMapper userMapper,
                                  AuthSessionMapper authSessionMapper) {
        this.userMapper = userMapper;
        this.authSessionMapper = authSessionMapper;
    }

    /**
     * Check user are logged in
     * @return boolean
     */
    @Override
    public boolean isLoggedIn(String userId, String sessionId) {
        boolean isLoggedIn = false;
        AuthSessionDataset authSessionInfo = authSessionMapper.selAuthSessionInfo(userId, sessionId);
        if (Objects.nonNull(authSessionInfo)) {
            LocalDateTime expirationTime = LocalDateTime.parse(authSessionInfo.getExpirationTime(),
                    DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));

            if (expirationTime.isAfter(LocalDateTime.now())) {
                System.out.println("==> [LOGGED IN]]");
                isLoggedIn = true;
            } else {
                String timeoutFlg = "1";
                String logoutTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
                authSessionMapper.updExpirationSession(userId, sessionId, logoutTime, timeoutFlg);
            }
        }

        return isLoggedIn;
    }

    /**
     * Login
     * @param userDataset
     * @return boolean
     */
    @Override
    public UserInfoAuthDataset login(HttpServletResponse response, UserDataset userDataset) {
        String username = userDataset.getUsername();
        String password = EncryptUtils.encryptMD5(userDataset.getPassword());

        UserInfoAuthDataset userInfoAuthDataset = new UserInfoAuthDataset();
        userInfoAuthDataset.setStatus(false);

        UserDataset userInfoForAuth = userMapper.selUserInfoForAuth(username, password);
        if (Objects.nonNull(userInfoForAuth)) {
            String userId = userInfoForAuth.getId();
            String sessionId = UUID.randomUUID().toString();
            String loginTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
            String logoutTime = loginTime;
            String expirationTime = LocalDateTime.now().plusHours(3).format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
            String roleId = userInfoForAuth.getRoleId();

            // expire all session by user's id
            authSessionMapper.updExpirationAllSession(userInfoForAuth.getId(), logoutTime);

            // save new login session
            AuthSessionDataset authSessionDataset = new AuthSessionDataset();
            authSessionDataset.setUserId(userId);
            authSessionDataset.setSessionId(sessionId);
            authSessionDataset.setAuthKey(password);
            authSessionDataset.setLoginTime(loginTime);
            authSessionDataset.setTimeoutFlg("0");
            authSessionDataset.setExpirationTime(expirationTime);
            authSessionMapper.insAuthSessionInfo(authSessionDataset);

            // save login info into cookies
            Map<String, String> cookieMap = new HashMap<>();
            cookieMap.put("_uid", userId);
            cookieMap.put("_sid", sessionId);
            cookieMap.put("_rid", roleId);
            CookiesUtils.addCookies(response, cookieMap, 3);

            // return user's information authentication
            userInfoAuthDataset = new UserInfoAuthDataset();
            userInfoAuthDataset.setUId(userInfoForAuth.getId());
            userInfoAuthDataset.setSId(sessionId);
            userInfoAuthDataset.setRId(roleId);
            userInfoAuthDataset.setStatus(true);
        }

        return userInfoAuthDataset;
    }

    /**
     * Get cookie information
     *
     * @param request
     * @return
     */
    @Override
    public UserInfoAuthDataset getCookieInfo(HttpServletRequest request) {
        String userId = CookiesUtils.getCookieValueByKey(request, "_uid");
        String sessionId = CookiesUtils.getCookieValueByKey(request, "_sid");
        String roleId = CookiesUtils.getCookieValueByKey(request, "_rid");

        UserInfoAuthDataset userInfoAuthDataset = new UserInfoAuthDataset();
        if (StringUtils.hasLength(userId) && StringUtils.hasLength(sessionId) && StringUtils.hasLength(roleId)) {
            userInfoAuthDataset.setUId(userId);
            userInfoAuthDataset.setSId(sessionId);
            userInfoAuthDataset.setRId(roleId);
            userInfoAuthDataset.setStatus(true);
        } else {
            userInfoAuthDataset.setStatus(false);
        }

        return userInfoAuthDataset;
    }

    /**
     * Logout
     *
     * @param response
     * @param request
     */
    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response) {
        String userId = request.getHeader("_uid");
        String sessionId = request.getHeader("_sid");
        userId = !StringUtils.hasLength(userId) ? CookiesUtils.getCookieValueByKey(request, "_uid") : userId;
        sessionId = !StringUtils.hasLength(sessionId) ? CookiesUtils.getCookieValueByKey(request, "_sid") : sessionId;

        if (StringUtils.hasLength(userId) && StringUtils.hasLength(sessionId)) {
            String timeoutFlg = "1";
            String logoutTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
            authSessionMapper.updExpirationSession(userId, sessionId, logoutTime, timeoutFlg);
        }

        // expired cookie information
        CookiesUtils.deleteCookieByKey(response, "_uid");
        CookiesUtils.deleteCookieByKey(response, "_sid");
        CookiesUtils.deleteCookieByKey(response, "_rid");
    }
}
