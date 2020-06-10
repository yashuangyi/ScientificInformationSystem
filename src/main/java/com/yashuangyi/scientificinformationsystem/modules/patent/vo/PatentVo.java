package com.yashuangyi.scientificinformationsystem.modules.patent.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author yashuangyi
 * @version 1.0
 * @content
 * @date 2020-05-03 0:05
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PatentVo {

    private Integer id;
    private String flag;
    private String projectName;
}
