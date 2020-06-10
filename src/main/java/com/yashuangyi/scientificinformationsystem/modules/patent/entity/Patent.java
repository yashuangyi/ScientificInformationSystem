package com.yashuangyi.scientificinformationsystem.modules.patent.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * @author yashuangyi
 * @version 1.0
 * @content
 * @date 2020-05-02 23:59
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Patent")
public class Patent {

    @Id
    @GeneratedValue
    private Integer id;
    @Column(unique = true)
    private Integer projectId;
    private String flag;

    public Patent(Integer projectId, String flag) {
        this.projectId = projectId;
        this.flag = flag;
    }
}
