package com.org.api.vietin.service;

import com.org.api.vietin.model.dataset.UserDataset;
import com.org.api.vietin.model.dataset.UserInfoAuthDataset;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * AuthenticateService
 *
 * @author khal
 * @since 2020/11/14
 */
public interface AuthenticateService {

    boolean isLoggedIn(String userId, String sessionId);
    UserInfoAuthDataset login(HttpServletResponse response, UserDataset userDataset);
    UserInfoAuthDataset getCookieInfo(HttpServletRequest request);
    void logout(HttpServletRequest request, HttpServletResponse response);

}
