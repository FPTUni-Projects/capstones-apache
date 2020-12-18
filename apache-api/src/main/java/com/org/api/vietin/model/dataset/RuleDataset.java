package com.org.api.vietin.model.dataset;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

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
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class RuleDataset implements Serializable {

    private String id;
    private String name;
    private String userId;
    private String publisher;
    private String fileName;
    private String description;
    private String status;
    private MultipartFile file;

}
