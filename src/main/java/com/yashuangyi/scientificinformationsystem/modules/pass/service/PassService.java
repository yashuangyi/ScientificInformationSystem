package com.yashuangyi.scientificinformationsystem.modules.pass.service;

import com.yashuangyi.scientificinformationsystem.modules.pass.entity.Pass;

/**
 * @author yashuangyi
 * @version 1.0
 * @content
 * @date 2020-04-26 9:42
 */
public interface PassService {

    boolean addPass(Pass pass);

    boolean existsPassByProjectIdAndAdminId(Integer projectId, Integer adminId);

    int getCount(Integer projectId);

    int getPassCount(Integer projectId);
}
