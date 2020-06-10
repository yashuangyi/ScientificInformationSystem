package com.yashuangyi.scientificinformationsystem.modules.project.dao;

import com.yashuangyi.scientificinformationsystem.modules.project.entity.Project;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.Map;


/**
 * @author yashuangyi
 * @version 1.0
 * @content
 * @date 2020-04-24 20:45
 */
public interface ProjectDao extends JpaRepository<Project,Integer>, JpaSpecificationExecutor<Project> {
    @Query(value="select p.name as name, p.id as id, p.description as description, p.startTime as startTime, " +
            "p.passNum as passNum, " +
            "p.endTime as endTime, p.status as status, p.resultPath as resultPath, " +
            " u.name as userName from Project p inner join User u on u.id = p.userId and p.name like %?1%")
    Page<Map<String, Object>> findAllUserAndProject(String name, Pageable pageable);

    @Query(value="select p.name as name, p.id as id, p.description as description, p.startTime as startTime, " +
            "p.passNum as passNum, " +
            "p.endTime as endTime, p.status as status, p.resultPath as resultPath, " +
            " u.name as userName from Project p inner join User u on u.id = p.userId")
    Page<Map<String, Object>> findAllUserAndProject(Pageable pageable);

    void deleteById(Integer id);

    Project getById(Integer id);

    @Query(value="select count(p) from Project as p where userId=?1")
    int getUserDeclare(Integer id);

    @Query(value="select count(p1) from Prize as p1, Project as p2 where p1.projectId = p2.id and p2.userId=?1")
    int getUserPrize(Integer id);

    @Query(value="select count(p) from Project as p where userId=?1 and status='审批通过'")
    int getUserPass(Integer id);

    @Query(value="select count(p) from Project as p where status='待授权'")
    int getAdminShouldGrant();

    @Query(value="select count(p) from Project as p where status='待审批'")
    int getAdminShouldPass();

    @Query(value="select count(p) from Project as p")
    int getAdminHasDeclare();
}
