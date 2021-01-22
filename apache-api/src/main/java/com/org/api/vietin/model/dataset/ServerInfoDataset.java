package com.org.api.vietin.model.dataset;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * ServerInfoDataset
 *
 * @since 2021/01/22
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ServerInfoDataset {

    private String id;
    private String userId;
    private String ruleId;
    private String serverName;
    private String serverAlias;
    private String status;

}
