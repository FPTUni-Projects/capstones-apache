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
 * @since 2020/11/14
 */
@Data
@Entity
@Table(name = "ca_server_info")
public class ServerInfoEntity implements Serializable {

    @Id @Column(name = "id", length = 7, nullable = false)
    private String id;
    @Id @Column(name = "user_id", length = 7, nullable = false)
    private String userId;
    @Column(name = "server_name", length = 250, nullable = false)
    private String serverName;
    @Column(name = "server_alias", length = 250, nullable = false)
    private String serverAlias;
    @Column(name = "status", length = 1, nullable = false)
    private String status;

}
