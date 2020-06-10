package com.yashuangyi.scientificinformationsystem.modules.paper.entity;

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
@Table(name = "Paper")
public class Paper {

    @Id
    @GeneratedValue
    private Integer id;
    @Column(unique = true)
    private Integer projectId;
    private String paperPath;

    public Paper(Integer projectId, String paperPath) {
        this.projectId = projectId;
        this.paperPath = paperPath;
    }
}
