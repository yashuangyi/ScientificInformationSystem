package com.yashuangyi.scientificinformationsystem.modules.patent.service.impl;

import com.yashuangyi.scientificinformationsystem.common.PageInfo;
import com.yashuangyi.scientificinformationsystem.modules.patent.dao.PatentDao;
import com.yashuangyi.scientificinformationsystem.modules.patent.entity.Patent;
import com.yashuangyi.scientificinformationsystem.modules.patent.service.PatentService;
import com.yashuangyi.scientificinformationsystem.modules.patent.vo.PatentVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author yashuangyi
 * @version 1.0
 * @content
 * @date 2020-05-03 0:01
 */
@Service
public class PatentServiceImpl implements PatentService {

    @Autowired
    private PatentDao patentDao;

    @Override
    public boolean addPatent(Patent patent) {
        Patent save = patentDao.save(patent);
        return save != null ? true : false;
    }

    @Override
    public boolean deletePatentById(Integer id) {
        patentDao.deleteById(id);
        return true;
    }

    @Override
    public Patent findPatentByProjectId(Integer projectId) {
        return patentDao.findByProjectId(projectId);
    }

    @Override
    public PageInfo findPageByParams(PatentVo vo, Pageable pageable) {
        Page<Map<String,Object>> page;
        if(vo.getProjectName() == null || vo.getProjectName().isEmpty()){
            page = patentDao.findAllPatent(pageable);
        }else{
            page = patentDao.findAllPatent(vo.getProjectName(), pageable);
        }
        PageInfo pageInfo = PageInfo.builder()
                .total(page.getTotalElements())
                .rows(page.getContent())
                .build();
        return pageInfo;
    }

    @Override
    public PageInfo findPageByUserParams(Integer userId, Pageable pageable) {
        Page<Map<String,Object>> page = patentDao.findUserPatent(userId, pageable);
        PageInfo pageInfo = PageInfo.builder()
                .total(page.getTotalElements())
                .rows(page.getContent())
                .build();
        return pageInfo;
    }
}
