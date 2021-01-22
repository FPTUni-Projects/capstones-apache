package com.org.api.vietin.model.dataset;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * UserDataset
 *
 *
 * @since 2020/11/14
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserServerDataset {

    private String userId;
    private String username;
    private String password;
    private String fullName;
    private String phoneNumber;
    private String userStatus;
    private String serverId;
    private String roleId;
    private String serverName;
    private String serverAlias;
    private String serverStatus;

}
