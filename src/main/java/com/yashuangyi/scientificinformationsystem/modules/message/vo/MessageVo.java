package com.yashuangyi.scientificinformationsystem.modules.message.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author yashuangyi
 * @version 1.0
 * @content
 * @date 2020-04-30 8:59
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MessageVo {

    private Integer id;
    private Integer targetId;
    private Integer flag;
    private String time;
    private String content;
}
