package com.org.api.vietin.model.dataset;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * LoginInfo
 *
 *
 * @since 2020/11/14
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserInfoAuthDataset {

    private String uId;
    private String sId;
    private String rId;
    private boolean status;

}
