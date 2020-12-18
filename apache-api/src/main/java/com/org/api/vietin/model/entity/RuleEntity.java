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
 *
 * @since 2020/11/14
 */
@Data
@Entity
@Table(name = "ca_rule")
public class RuleEntity implements Serializable {

    @Id @Column(name = "id", length = 7, nullable = false)
    private String id;
    @Id @Column(name = "user_id", length = 7, nullable = false)
    private String userId;
    @Column(name = "name", length = 255, nullable = false)
    private String name;
    @Column(name = "file_name", length = 255, nullable = false)
    private String fileName;
    @Column(name = "description", length = 1000, nullable = false)
    private String description;
    @Column(name = "status", length = 1, nullable = false)
    private String status;

}
