package com.org.api.vietin.controller;

import com.org.api.vietin.model.dataset.UserDataset;
import com.org.api.vietin.model.dataset.UserInfoAuthDataset;
import com.org.api.vietin.model.dataset.UserPasswordDataset;
import com.org.api.vietin.service.AuthenticateService;
import com.org.api.vietin.service.UserService;
import com.sun.istack.NotNull;
import org.apache.tomcat.jni.User;
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
@RequestMapping(value = "/vi/user/api/v1")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = "/get-user-info", produces = MediaType.APPLICATION_JSON_VALUE)
    public UserDataset getUserInfo(@RequestParam("userId") String userId) {
        return userService.getUserInfo(userId);
    }

    @PostMapping(value = "/create-user", produces = MediaType.APPLICATION_JSON_VALUE)
    public boolean createUser(@RequestBody UserDataset userDataset) {
        return userService.createUser(userDataset);
    }

    @PostMapping(value = "/change-password", produces = MediaType.APPLICATION_JSON_VALUE)
    public boolean changePassword(@RequestBody UserPasswordDataset userPasswordDataset) {
        return userService.changePassword(userPasswordDataset);
    }

}
