package com.org.api.vietin.service;

import com.org.api.vietin.common.id.GenIDComponent;
import com.org.api.vietin.common.utils.CommonUtils;
import com.org.api.vietin.common.utils.EncryptUtils;
import com.org.api.vietin.mapper.UserMapper;
import com.org.api.vietin.model.dataset.UserDataset;
import com.org.api.vietin.model.dataset.UserPasswordDataset;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * UserServiceImp
 *
 *
 * @since 2020/11/29
 */
@Service
public class UserServiceImp implements UserService {

    private final GenIDComponent genIDComponent;
    private final UserMapper userMapper;

    public UserServiceImp(GenIDComponent genIDComponent, UserMapper userMapper) {
        this.genIDComponent = genIDComponent;
        this.userMapper = userMapper;
    }


    @Override
    public UserDataset getUserInfo(String id) {
        return userMapper.selUserInfo(id);
    }

    @Override
    public boolean createUser(UserDataset userDataset) {
        String userId = genIDComponent.getId(7, "ca_user");
        userDataset.setId(userId);
        userDataset.setPassword(EncryptUtils.encryptMD5(userDataset.getPassword()));
        return userMapper.insUser(userDataset) != 0;
    }

    @Override
    public boolean changePassword(UserPasswordDataset userPasswordDataset) {
        userPasswordDataset.setOldPassword(EncryptUtils.encryptMD5(userPasswordDataset.getOldPassword()));
        userPasswordDataset.setNewPassword(EncryptUtils.encryptMD5(userPasswordDataset.getNewPassword()));
        Integer result = userMapper.updPassword(userPasswordDataset);
        return Objects.nonNull(result) && result != 0;
    }

    @Override
    public boolean updateUserStatus(String id) {
        return userMapper.updStatus(id) != 0;
    }

    @Override
    public List<UserDataset> getAllUser() {
        return userMapper.selAllUser();
    }
}
