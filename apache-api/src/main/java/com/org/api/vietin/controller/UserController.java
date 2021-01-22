package com.org.api.vietin.controller;

import com.org.api.vietin.model.dataset.*;
import com.org.api.vietin.service.UserService;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * UserController
 *
 *
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


    @GetMapping(value = "/get-user-info-comm", produces = MediaType.APPLICATION_JSON_VALUE)
    public UserDataset getUserInfoComm(@RequestParam("userId") String userId) {
        return userService.getUserInfoComm(userId);
    }

    @GetMapping(value = "/get-user-info", produces = MediaType.APPLICATION_JSON_VALUE)
    public UserServerDataset getUserInfo(@RequestParam("userId") String userId) {
        return userService.getUserInfo(userId);
    }

    @PostMapping(value = "/create-user", produces = MediaType.APPLICATION_JSON_VALUE)
    public boolean createUser(@RequestBody UserServerDataset userServerDataset) {
        return userService.createUser(userServerDataset);
    }

    @PostMapping(value = "/change-password", produces = MediaType.APPLICATION_JSON_VALUE)
    public boolean changePassword(@RequestBody UserPasswordDataset userPasswordDataset) {
        return userService.changePassword(userPasswordDataset);
    }

    @GetMapping(value = "/get-all-user", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<UserServerDataset> getAllUser() {
        return userService.getAllUser();
    }

    @GetMapping(value = "/check-exist", produces = MediaType.APPLICATION_JSON_VALUE)
    public ErroMsgDataset checkExistUser(@RequestParam("serverName") String serverName,
                                     @RequestParam("serverAlias") String serverAlias,
                                     @RequestParam("username") String username) {
        return userService.checkExist(serverName, serverAlias, username);
    }


    @GetMapping(value = "/update-user-status", produces = MediaType.APPLICATION_JSON_VALUE)
    public boolean updateUserStatus(@RequestParam("userId") String userId,
                                    @RequestParam("status") String status) {
        return userService.updateUserStatus(userId, status);
    }
}
