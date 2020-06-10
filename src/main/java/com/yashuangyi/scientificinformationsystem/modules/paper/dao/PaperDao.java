package com.yashuangyi.scientificinformationsystem.modules.paper.dao;

import com.yashuangyi.scientificinformationsystem.modules.paper.entity.Paper;
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
 * @date 2020-05-02 23:59
 */
public interface PaperDao extends JpaRepository<Paper, Integer>, JpaSpecificationExecutor<Paper> {

    @Query(value="select p1.id as id, p1.paperPath as paperPath, p2.name as projectName, " +
            "u.name as userName from Paper p1 inner join Project p2 on p1.projectId = p2.id " +
            "inner join User u on u.id = p2.userId and p2.name like %?1%")
    Page<Map<String, Object>> findAllPaper(String userName, Pageable pageable);

    @Query(value="select p1.id as id, p1.paperPath as paperPath, p2.name as projectName, " +
            "u.name as userName from Paper p1 inner join Project p2 on p1.projectId = p2.id " +
            "inner join User u on u.id = p2.userId")
    Page<Map<String, Object>> findAllPaper(Pageable pageable);

    @Query(value="select p1.id as id, p1.paperPath as paperPath, p2.name as projectName, p1.projectId as projectId " +
            "from Paper p1 inner join Project p2 on p1.projectId = p2.id " +
            "inner join User u on u.id = ?1 and p2.userId = u.id")
    Page<Map<String, Object>> findUserPaper(Integer userId, Pageable pageable);

    Paper findByProjectId(Integer projectId);
}
