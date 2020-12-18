package com.org.api.vietin.model.dataset;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * LogDataset
 *
 * @since 2020/12/17
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LogDataset {

    private String time;
    private String error;
    private String host;
    private String description;

}
