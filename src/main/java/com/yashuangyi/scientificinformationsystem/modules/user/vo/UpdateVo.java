package com.yashuangyi.scientificinformationsystem.modules.user.vo;

import lombok.Data;

/**
 * @author yashuangyi
 * @version 1.0
 * @content
 * @date 2020-04-25 14:55
 */
@Data
public class UpdateVo {

    private Integer id;
    private String account;
    private String password;
    private String name;
}
