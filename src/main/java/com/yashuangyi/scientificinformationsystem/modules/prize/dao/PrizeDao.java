package com.yashuangyi.scientificinformationsystem.modules.prize.dao;

import com.yashuangyi.scientificinformationsystem.modules.prize.entity.Prize;
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
public interface PrizeDao extends JpaRepository<Prize, Integer>, JpaSpecificationExecutor<Prize> {

    @Query(value="select p1.id as id, p1.name as name, p2.name as projectName, p2.id as projectId, " +
            "u.name as userName from Prize p1 inner join Project p2 on p1.projectId = p2.id " +
            "inner join User u on u.id = p2.userId and p2.name like %?1%")
    Page<Map<String, Object>> findAllPrize(String userName, Pageable pageable);

    @Query(value="select p1.id as id, p1.name as name, p2.name as projectName, p2.id as projectId, " +
            "u.name as userName from Prize p1 inner join Project p2 on p1.projectId = p2.id " +
            "inner join User u on u.id = p2.userId")
    Page<Map<String, Object>> findAllPrize( Pageable pageable);

    @Query(value="select p1.id as id, p1.name as name, p2.name as projectName " +
            "from Prize p1 inner join Project p2 on p1.projectId = p2.id " +
            "inner join User u on u.id = ?1 and p2.userId = u.id")
    Page<Map<String, Object>> findUserPrize(Integer userId, Pageable pageable);

    Prize findByProjectId(Integer projectId);
}
