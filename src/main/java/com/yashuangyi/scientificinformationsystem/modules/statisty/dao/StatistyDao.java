package com.yashuangyi.scientificinformationsystem.modules.statisty.dao;

import com.yashuangyi.scientificinformationsystem.modules.project.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

/**
 * @author yashuangyi
 * @version 1.0
 * @content
 * @date 2020-05-03 23:30
 */
public interface StatistyDao extends JpaRepository<Project,Integer>, JpaSpecificationExecutor<Project> {

    @Query(value="select count(p) from Project as p where status='待授权'")
    int getProjectOne();

    @Query(value="select count(p) from Project as p where status='已授权-开发中'")
    int getProjectTwo();

    @Query(value="select count(p) from Project as p where status='授权不通过'")
    int getProjectThree();

    @Query(value="select count(p) from Project as p where status='待审批'")
    int getProjectFour();

    @Query(value="select count(p) from Project as p where status='审批通过'")
    int getProjectFive();

    @Query(value="select count(p) from Project as p where status='审批不通过'")
    int getProjectSix();

}
