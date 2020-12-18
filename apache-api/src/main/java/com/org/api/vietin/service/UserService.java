package com.org.api.vietin.service;

import com.org.api.vietin.model.dataset.UserDataset;
import com.org.api.vietin.model.dataset.UserPasswordDataset;

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

}
