package com.yashuangyi.scientificinformationsystem.modules.pass.dao;

import com.yashuangyi.scientificinformationsystem.modules.pass.entity.Pass;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * @author yashuangyi
 * @version 1.0
 * @content
 * @date 2020-04-26 9:43
 */
public interface PassDao extends JpaRepository<Pass, Integer> {

    boolean existsPassByProjectIdAndAdminId(Integer projectId, Integer adminId);

    @Query(value="select count(p) from Pass as p where project_id=?1")
    int getCount(Integer projectId);

    @Query(value="select count(p) from Pass as p where project_id=?1 and result='通过'")
    int getPassCount(Integer projectId);
}
