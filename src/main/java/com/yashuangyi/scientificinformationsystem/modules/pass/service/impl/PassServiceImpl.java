package com.yashuangyi.scientificinformationsystem.modules.pass.service.impl;

import com.yashuangyi.scientificinformationsystem.modules.pass.dao.PassDao;
import com.yashuangyi.scientificinformationsystem.modules.pass.entity.Pass;
import com.yashuangyi.scientificinformationsystem.modules.pass.service.PassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author yashuangyi
 * @version 1.0
 * @content
 * @date 2020-04-26 9:42
 */
@Service
public class PassServiceImpl implements PassService {

    @Autowired
    private PassDao passDao;

    @Override
    public boolean addPass(Pass pass) {
        Pass save = passDao.save(pass);
        return save != null ? true : false;
    }

    @Override
    public boolean existsPassByProjectIdAndAdminId(Integer projectId, Integer adminId) {
        return passDao.existsPassByProjectIdAndAdminId(projectId, adminId);
    }

    @Override
    public int getCount(Integer projectId) {
        return passDao.getCount(projectId);
    }

    @Override
    public int getPassCount(Integer projectId) {
        return passDao.getPassCount(projectId);
    }
}
