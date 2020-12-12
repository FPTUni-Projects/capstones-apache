package com.org.api.vietin.filter;

import com.org.api.vietin.common.utils.CommonUtils;
import com.org.api.vietin.common.utils.CookiesUtils;
import com.org.api.vietin.service.AuthenticateService;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * AuthenticateFilter
 *
 * @author khal
 * @since 2020/11/14
 */
@Order(1)
@Component
public class AuthenticateFilter implements Filter {

    private final AuthenticateService authenticateService;

    public AuthenticateFilter(AuthenticateService authenticateService) {
        this.authenticateService = authenticateService;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        String userId = req.getHeader("_uid");
        String sessionId = req.getHeader("_sid");
        if (!StringUtils.hasLength(userId) || !StringUtils.hasLength(sessionId)) {
            userId = CookiesUtils.getCookieValueByKey(req, "_uid");
            sessionId = CookiesUtils.getCookieValueByKey(req, "_sid");
        }
        String uri = req.getRequestURI();

        boolean isStaticResource = CommonUtils.isStaticResouces(uri);
        if (isStaticResource) {
            chain.doFilter(request, response);
        } else {
            boolean isLoggedIn = authenticateService.isLoggedIn(userId, sessionId);
            if (isLoggedIn) {
                if (uri.contains("/login")) {
                    res.sendRedirect("/");
                } else {
                    chain.doFilter(request, response);
                }
            } else if (!isLoggedIn && !uri.contains("/login") && !uri.contains("/vi/main/api/v1/login")) {
                res.sendRedirect("/login");
            } else {
                chain.doFilter(request, response);
            }
        }
    }

}
