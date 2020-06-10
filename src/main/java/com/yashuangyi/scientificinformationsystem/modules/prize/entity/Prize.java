package com.yashuangyi.scientificinformationsystem.modules.prize.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * @author yashuangyi
 * @version 1.0
 * @content
 * @date 2020-05-03 0:00
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Prize")
public class Prize {

    @Id
    @GeneratedValue
    private Integer id;
    @Column(unique = true)
    private Integer projectId;
    private String name;

    public Prize(Integer projectId, String name) {
        this.projectId = projectId;
        this.name = name;
    }
}
