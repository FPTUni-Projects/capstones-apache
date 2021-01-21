package com.org.api.vietin.model.dataset;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Id;

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
public class UserDataset {

    private String id;
    private String username;
    private String password;
    private String fullName;
    private String phoneNumber;
    private String roleId;
    private String serverName;
    private String serverAlias;
    private String status;

}
