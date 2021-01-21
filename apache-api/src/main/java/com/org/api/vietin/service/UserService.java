package com.org.api.vietin.service;

import com.org.api.vietin.model.dataset.ErroMsgDataset;
import com.org.api.vietin.model.dataset.UserDataset;
import com.org.api.vietin.model.dataset.UserPasswordDataset;

import java.util.List;

/**
 * UserService
 *
 *
 * @since 2020/11/29
 */
public interface UserService {

    UserDataset getUserInfo(String id);
    boolean createUser(UserDataset userDataset);
    boolean changePassword(UserPasswordDataset userPasswordDataset);
    boolean updateUserStatus(String id);
    ErroMsgDataset checkExist(String serverName, String serverAlias, String username);
    List<UserDataset> getAllUser();

}
