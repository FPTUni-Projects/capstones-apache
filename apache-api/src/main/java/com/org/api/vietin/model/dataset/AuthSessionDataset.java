package com.org.api.vietin.model.dataset;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

/**
 * AuthSessionDataset
 *
 *
 * @since 2020/11/14
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AuthSessionDataset implements Serializable {

    private String userId;
    private String sessionId;
    private String expirationTime;
    private String authKey;
    private String timeoutFlg;
    private String loginTime;
    private String logoutTime;

}
