package com.yashuangyi.scientificinformationsystem.modules.patent.dao;

import com.yashuangyi.scientificinformationsystem.modules.patent.entity.Patent;
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
public interface PatentDao extends JpaRepository<Patent, Integer>, JpaSpecificationExecutor<Patent> {

    @Query(value="select p1.id as id, p1.flag as flag, p2.name as projectName, " +
            "u.name as userName from Patent p1 inner join Project p2 on p1.projectId = p2.id " +
            "inner join User u on u.id = p2.userId and p2.name like %?1%")
    Page<Map<String, Object>> findAllPatent(String userName, Pageable pageable);

    @Query(value="select p1.id as id, p1.flag as flag, p2.name as projectName, " +
            "u.name as userName from Patent p1 inner join Project p2 on p1.projectId = p2.id " +
            "inner join User u on u.id = p2.userId")
    Page<Map<String, Object>> findAllPatent(Pageable pageable);

    @Query(value="select p1.id as id, p1.flag as flag, p2.name as projectName " +
            "from Patent p1 inner join Project p2 on p1.projectId = p2.id " +
            "inner join User u on u.id = ?1 and p2.userId = u.id")
    Page<Map<String, Object>> findUserPatent(Integer userId, Pageable pageable);

    Patent findByProjectId(Integer projectId);
}
