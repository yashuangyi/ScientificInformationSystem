package com.yashuangyi.scientificinformationsystem.modules.project.vo;

import lombok.*;


/**
 * @author yashuangyi
 * @version 1.0
 * @content
 * @date 2020-04-25 10:16
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProjectVo {

    private Integer id;
    private Integer userId;
    private Integer prizeId;
    private Integer paperId;
    private Integer patentId;
    private String name;
    private String description;
    private String startTime;
    private String endTime;
    private Integer passNum;
    private String status;
    private String resultPath;
    private String userName;
}
