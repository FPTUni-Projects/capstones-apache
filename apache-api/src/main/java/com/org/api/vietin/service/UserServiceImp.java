package com.org.api.vietin.service;

import com.org.api.vietin.common.constant.Constant;
import com.org.api.vietin.common.constant.DataConstant;
import com.org.api.vietin.common.id.GenIDComponent;
import com.org.api.vietin.common.utils.EncryptUtils;
import com.org.api.vietin.mapper.UserMapper;
import com.org.api.vietin.model.dataset.ErroMsgDataset;
import com.org.api.vietin.model.dataset.UserDataset;
import com.org.api.vietin.model.dataset.UserPasswordDataset;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
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

    private final Constant constant;
    private final GenIDComponent genIDComponent;
    private final UserMapper userMapper;

    public UserServiceImp(Constant constant, GenIDComponent genIDComponent, UserMapper userMapper) {
        this.constant = constant;
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
        try {
            // create vhost
            String vhostContent = DataConstant.VHOST_CONFIG_TEMPLATE.replaceAll("SERVER_NAME_CONTENT", userDataset.getServerName())
                    .replaceAll("SERVER_ALIAS_CONTENT", userDataset.getServerAlias());
            Path vhostConfigPath = Paths.get(constant.getApacheConfExtraDir() + "httpd-vhosts.conf");
            Files.write(vhostConfigPath, vhostContent.getBytes(StandardCharsets.UTF_8),
                    StandardOpenOption.CREATE,
                    StandardOpenOption.APPEND);

            // create default template
            String templateContent = DataConstant.DEFAULT_TEMPLATE.replaceAll("SERVER_NAME_CONTENT", userDataset.getServerName());

            String templateDir = constant.getApacheHtdocs() + userDataset.getServerName();
            File templateFolder = new File(templateDir);
            if (!templateFolder.exists())
                templateFolder.mkdirs();

            File templateFile = new File(templateDir + "/index.html");
            if (!templateFile.exists())
                templateFile.createNewFile();

            Path templatePath = Paths.get(constant.getApacheHtdocs() + userDataset.getServerName() + "/index.html");
            Files.write(templatePath, templateContent.getBytes(StandardCharsets.UTF_8),
                    StandardOpenOption.CREATE,
                    StandardOpenOption.APPEND);
        } catch (IOException e) {
            e.printStackTrace();
        }

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
    public ErroMsgDataset checkExist(String serverName, String serverAlias, String username) {
        ErroMsgDataset erroMsgDataset = new ErroMsgDataset();
        erroMsgDataset.setMsg("");
        if (userMapper.selExistUsername(username) != 0) {
            erroMsgDataset.setMsg("Username has been existed!");
        } else if (userMapper.selExistServerName(serverName) != 0) {
            erroMsgDataset.setMsg("Server name already in use!");
        } else if (userMapper.selExistServerAlias(serverAlias) != 0) {
            erroMsgDataset.setMsg("Server alias already in use!");
        }

        return erroMsgDataset;
    }

    @Override
    public List<UserDataset> getAllUser() {
        return userMapper.selAllUser();
    }
}
