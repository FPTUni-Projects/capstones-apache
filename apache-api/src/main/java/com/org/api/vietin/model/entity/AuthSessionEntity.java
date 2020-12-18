package com.org.api.vietin.model.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

/**
 * AuthSessionEntity
 *
 *
 * @since 2020/11/14
 */
@Data
@Entity
@Table(name = "ca_auth_session")
public class AuthSessionEntity implements Serializable {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private long id;

    @Column(name = "user_id", nullable = false, length = 30)
    private String userId;

    @Column(name = "session_id", nullable = false, length = 36)
    private String sessionId;

    @Column(name = "expiration_time", nullable = false, length = 14)
    private String expirationTime;

    @Column(name = "auth_key", nullable = false, length = 32)
    private String authKey;

    @Column(name = "timeout_flg", nullable = false, length = 1)
    private String timeoutFlg;

    @Column(name = "login_time", nullable = false, length = 14)
    private String loginTime;

    @Column(name = "logout_time", length = 14)
    private String logoutTime;

}
