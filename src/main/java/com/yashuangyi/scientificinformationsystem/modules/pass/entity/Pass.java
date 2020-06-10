package com.yashuangyi.scientificinformationsystem.modules.pass.entity;

import lombok.Data;

import javax.persistence.*;

/**
 * @author yashuangyi
 * @version 1.0
 * @content
 * @date 2020-04-26 9:43
 */
@Entity
@Data
@Table(name = "Pass")
public class Pass {
    @Id
    @GeneratedValue
    private Integer id;
    @Column(unique = true)
    private Integer projectId;
    private Integer adminId;
    private String result;

    public Pass(Integer projectId, Integer adminId, String result){
        this.projectId = projectId;
        this.adminId = adminId;
        this.result = result;
    }

    public Pass(){

    }
}
