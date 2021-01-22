package com.org.api.vietin.service;

import com.org.api.vietin.common.constant.Constant;
import com.org.api.vietin.common.constant.DataConstant;
import com.org.api.vietin.common.id.GenIDComponent;
import com.org.api.vietin.common.utils.CommonUtils;
import com.org.api.vietin.common.utils.EncryptUtils;
import com.org.api.vietin.mapper.ServerInfoMapper;
import com.org.api.vietin.mapper.UserMapper;
import com.org.api.vietin.model.dataset.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
public class UserService {

    private final Constant constant;
    private final GenIDComponent genIDComponent;
    private final UserMapper userMapper;
    private final ServerInfoMapper serverInfoMapper;

    public UserService(Constant constant, GenIDComponent genIDComponent, UserMapper userMapper, ServerInfoMapper serverInfoMapper) {
        this.constant = constant;
        this.genIDComponent = genIDComponent;
        this.userMapper = userMapper;
        this.serverInfoMapper = serverInfoMapper;
    }

    public UserDataset getUserInfoComm(String id) {
        return userMapper.selUserInfoComm(id);
    }

    public UserServerDataset getUserInfo(String id) {
        return userMapper.selUserInfo(id);
    }

    @Transactional(rollbackFor = Throwable.class)
    public boolean createUser(UserServerDataset userServerDataset) {
        int resultCount = 0;
        String userId = genIDComponent.getId(7, "ca_user");
        userServerDataset.setUserId(userId);
        userServerDataset.setPassword(EncryptUtils.encryptMD5(userServerDataset.getPassword()));
        resultCount += userMapper.insUser(userServerDataset);

        String serverId = genIDComponent.getId(7, "ca_server_info");
        userServerDataset.setServerId(serverId);
        resultCount += serverInfoMapper.insServerInfo(userServerDataset);

        if (resultCount >= 2) {
            try {
                // create vhost
                String vhostContent = DataConstant.VHOST_CONFIG_TEMPLATE.replaceAll("SERVER_NAME_CONTENT", userServerDataset.getServerName())
                        .replaceAll("SERVER_ALIAS_CONTENT", userServerDataset.getServerAlias());
                Path vhostConfigPath = Paths.get(constant.getApacheConfExtraDir() + "httpd-vhosts.conf");
                Files.write(vhostConfigPath, vhostContent.getBytes(StandardCharsets.UTF_8),
                        StandardOpenOption.CREATE,
                        StandardOpenOption.APPEND);

                // create default template
                String templateContent = DataConstant.DEFAULT_TEMPLATE.replaceAll("SERVER_NAME_CONTENT", userServerDataset.getServerName());

                String templateDir = constant.getApacheHtdocs() + userServerDataset.getServerName();
                File templateFolder = new File(templateDir);
                if (!templateFolder.exists())
                    templateFolder.mkdirs();

                File templateFile = new File(templateDir + "/index.html");
                if (!templateFile.exists())
                    templateFile.createNewFile();

                Path templatePath = Paths.get(constant.getApacheHtdocs() + userServerDataset.getServerName() + "/index.html");
                Files.write(templatePath, templateContent.getBytes(StandardCharsets.UTF_8),
                        StandardOpenOption.CREATE,
                        StandardOpenOption.APPEND);

                // restart apache
                CommonUtils.restartHttpd(constant);
            } catch (IOException | InterruptedException e) {
                return false;
            }

            return true;
        }
        return false;
    }

    public boolean changePassword(UserPasswordDataset userPasswordDataset) {
        userPasswordDataset.setOldPassword(EncryptUtils.encryptMD5(userPasswordDataset.getOldPassword()));
        userPasswordDataset.setNewPassword(EncryptUtils.encryptMD5(userPasswordDataset.getNewPassword()));
        Integer result = userMapper.updPassword(userPasswordDataset);
        return Objects.nonNull(result) && result != 0;
    }

    public boolean updateUserStatus(String id, String status) {
        boolean result = userMapper.updStatus(id, status) != 0;
        if (result) {
            try {
                activeVHost();

                // restart apache
                CommonUtils.restartHttpd(constant);
                return true;
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        }

        return false;
    }

    public ErroMsgDataset checkExist(String serverName, String serverAlias, String username) {
        ErroMsgDataset erroMsgDataset = new ErroMsgDataset();
        erroMsgDataset.setMsg("");
        if (userMapper.selExistUsername(username) != 0) {
            erroMsgDataset.setMsg("Username has been existed!");
        } else if (serverInfoMapper.selExistServerName(serverName) != 0) {
            erroMsgDataset.setMsg("Server name already in use!");
        } else if (serverInfoMapper.selExistServerAlias(serverAlias) != 0) {
            erroMsgDataset.setMsg("Server alias already in use!");
        }

        return erroMsgDataset;
    }

    public List<UserServerDataset> getAllUser() {
        return userMapper.selAllUser();
    }

    private void activeVHost() throws IOException {
        List<UserServerDataset> activeUserServers = userMapper.selAllActiveUser();
        String vhosts = "";
        for (UserServerDataset server: activeUserServers) {
            vhosts += DataConstant.VHOST_CONFIG_TEMPLATE.replaceAll("SERVER_NAME_CONTENT", server.getServerName())
                    .replaceAll("SERVER_ALIAS_CONTENT", server.getServerAlias());
        }

        File modConfFile = new File(constant.getApacheConfExtraDir() + "httpd-vhosts.conf");
        modConfFile.delete();

        File newModConfFile = new File(constant.getApacheConfExtraDir() + "httpd-vhosts.conf");
        newModConfFile.createNewFile();

        Path path = Paths.get(constant.getApacheConfExtraDir() + "httpd-vhosts.conf");
        Files.write(path, vhosts.getBytes(StandardCharsets.UTF_8),
                StandardOpenOption.CREATE,
                StandardOpenOption.APPEND);
    }
}
