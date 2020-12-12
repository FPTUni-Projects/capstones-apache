package com.org.api.vietin.model.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * UserEntity
 *
 * @author khal
 * @since 2020/11/14
 */
@Data
@Entity
@Table(name = "vi_user")
public class UserEntity implements Serializable {

    @Id @Column(name = "id", length = 7, nullable = false)
    private String id;
    @Id @Column(name = "username", length = 100, nullable = false)
    private String username;
    @Column(name = "password", length = 32, nullable = false)
    private String password;
    @Column(name = "full_name", length = 255, nullable = false)
    private String fullName;
    @Column(name = "phone_number", length = 30, nullable = false)
    private String phoneNumber;
    @Column(name = "role_id", length = 1, nullable = false)
    private String roleId;
    @Column(name = "status", length = 1, nullable = false)
    private String status;

}
