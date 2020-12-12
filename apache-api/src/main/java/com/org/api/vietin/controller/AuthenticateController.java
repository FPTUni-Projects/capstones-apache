package com.org.api.vietin.controller;

import com.org.api.vietin.model.dataset.UserDataset;
import com.org.api.vietin.model.dataset.UserInfoAuthDataset;
import com.org.api.vietin.service.AuthenticateService;
import com.sun.istack.NotNull;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * AuthenticateController
 *
 * @author khal
 * @since 2020/11/14
 */
@RestController
@Validated
@RequestMapping(value = "/vi/auth/api/v1")
public class AuthenticateController {

    private final AuthenticateService authenticateService;

    public AuthenticateController(AuthenticateService authenticateService) {
        this.authenticateService = authenticateService;
    }

    @PostMapping(value = "/auth/login", produces = MediaType.APPLICATION_JSON_VALUE)
    public UserInfoAuthDataset login(HttpServletResponse response,
                                     @RequestBody @NotNull UserDataset userDataset) {
        return authenticateService.login(response, userDataset);
    }

    @GetMapping(value = "/auth/get-cookie-info", produces = MediaType.APPLICATION_JSON_VALUE)
    public UserInfoAuthDataset getCookieInfo(HttpServletRequest request) {
        return authenticateService.getCookieInfo(request);
    }

    @GetMapping(value = "/auth/logout", produces = MediaType.APPLICATION_JSON_VALUE)
    public boolean logout(HttpServletRequest request,
                       HttpServletResponse response) {
        authenticateService.logout(request, response);
        return true;
    }
}
