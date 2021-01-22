package com.org.api.vietin.controller;

import com.org.api.vietin.service.AuthenticateService;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * DispatcherController
 *
 *
 * @since 2020/11/14
 */
@Controller("mainDispatcherController")
@RequestMapping(value = "")
public class DispatcherController implements ErrorController {

    private final AuthenticateService authenticateService;

    public DispatcherController(AuthenticateService authenticateService) {
        this.authenticateService = authenticateService;
    }

    @GetMapping(value = {"", "/", "/dashboard"})
    public String redirectDashboarPage(HttpServletRequest request) {
        return "dashboard";
    }

    @GetMapping(value = {"/user-info"})
    public String redirectUserInfoPage(HttpServletRequest request) {
        return "user-info";
    }

    @GetMapping(value = {"/create-rule"})
    public String redirectCreateRulePage(HttpServletRequest request) {
        return "create-rule";
    }

    @GetMapping(value = {"/manage-log"})
    public String redirectManageLogPage(HttpServletRequest request) {
        return "manage-log";
    }

    @GetMapping(value = {"/manage-user"})
    public String redirectManageUserPage(HttpServletRequest request) {
        return "manage-user";
    }

    @GetMapping(value = {"/create-user"})
    public String redirectCreateUserPage(HttpServletRequest request) {
        return "create-user";
    }

    @GetMapping(value = {"/change-password"})
    public String redirectChangePasswordPage(HttpServletRequest request) {
        return "change-password";
    }

    @GetMapping(value = {"/login"})
    public String redirectLoginPage(HttpServletRequest request) {
        return "login";
    }

    @Override
    public String getErrorPath() {
        return "error";
    }
}
