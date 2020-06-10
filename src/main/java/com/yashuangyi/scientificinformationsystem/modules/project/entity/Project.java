package com.yashuangyi.scientificinformationsystem.modules.project.entity;

import com.yashuangyi.scientificinformationsystem.modules.project.vo.ProjectAddVo;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;
import java.text.SimpleDateFormat;

/**
 * @author yashuangyi
 * @version 1.0
 * @content
 * @date 2020-04-24 20:43
 */
@Entity
@Data
@AllArgsConstructor
@Table(name = "Project")
public class Project {
    @Id
    @GeneratedValue
    private Integer id;
    @Column(unique = true)
    private Integer userId;
    private String name;
    private String description;
    private String startTime;
    private String endTime;
    private Integer passNum;
    private String status;
    private String resultPath;

    public static Project translate(ProjectAddVo addVo){
        return new Project(addVo.getName(),addVo.getDescription(),addVo.getUserId());
    }

    public Project(String name, String description, Integer userId) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        this.name = name;
        this.description = description;
        this.startTime = df.format(System.currentTimeMillis());
        this.status = "待授权";
        this.passNum = 0;
        this.userId = userId;
    }

    public Project(Integer id, Integer passNum){
        this.id = id;
        this.passNum = passNum;
    }

    public Project(Integer id, Integer passNum, String status){
        this.id = id;
        this.passNum = passNum;
        this.status = status;
    }

    public Project(Integer id, String status){
        this.id = id;
        this.status = status;
    }

    public Project(Integer id, String status, String endTime){
        this.id = id;
        this.status = status;
        this.endTime = endTime;
    }

    public Project(Integer userId) {
        this.userId = userId;
    }

    public Project() {
    }
}

