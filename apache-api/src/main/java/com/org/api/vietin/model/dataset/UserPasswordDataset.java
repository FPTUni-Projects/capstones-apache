package com.org.api.vietin.model.dataset;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * UserPasswordDataset
 *
 *
 * @since 2020/11/14
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserPasswordDataset {

    private String id;
    private String oldPassword;
    private String newPassword;

}
