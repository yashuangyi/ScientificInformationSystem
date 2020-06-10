package com.yashuangyi.scientificinformationsystem.modules.project.service.impl;
import	java.util.HashMap;

import com.yashuangyi.scientificinformationsystem.common.GemBeanUtils;
import com.yashuangyi.scientificinformationsystem.common.PageInfo;
import com.yashuangyi.scientificinformationsystem.modules.project.dao.ProjectDao;
import com.yashuangyi.scientificinformationsystem.modules.project.entity.Project;
import com.yashuangyi.scientificinformationsystem.modules.project.service.ProjectService;
import com.yashuangyi.scientificinformationsystem.modules.project.vo.ProjectVo;
import org.apache.poi.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author yashuangyi
 * @version 1.0
 * @content
 * @date 2020-04-24 20:42
 */
@Service
public class ProjectServiceImpl implements ProjectService {

    @Autowired
    private ProjectDao projectDao;

    @Override
    public int getAdminHasDeclare() {
        return projectDao.getAdminHasDeclare();
    }

    @Override
    public int getAdminShouldPass() {
        return projectDao.getAdminShouldPass();
    }

    @Override
    public int getAdminShouldGrant() {
        return projectDao.getAdminShouldGrant();
    }

    @Override
    public Project findProjectById(Integer id) {
        Project project = projectDao.findById(id).get();
        return project;
    }

    @Override
    public boolean addProject(Project project) {
        Project save = projectDao.save(project);
        return save != null ? true : false;
    }

    @Override
    public boolean deleteProjectById(Integer id) {
        projectDao.deleteById(id);
        return true;
    }

    @Override
    public int getUserPass(Integer id) {
        return projectDao.getUserPass(id);
    }

    @Override
    public int getUserPrize(Integer id) {
        return projectDao.getUserPrize(id);
    }

    @Override
    public int getUserDeclare(Integer id) {
        return projectDao.getUserDeclare(id);
    }

    @Override
    public PageInfo findPageByParams(ProjectVo vo, Pageable pageable) {
        Project entity = new Project();
        GemBeanUtils.copyProperties(vo, entity);
        //创建匹配器，即如何使用查询条件
        ExampleMatcher matcher = ExampleMatcher.matching() //构建对象
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING) //改变默认字符串匹配方式：模糊查询
                .withIgnoreCase(true); //改变默认大小写忽略方式：忽略大小写
        Page<Project> page = projectDao.findAll(Example.of(entity,matcher),pageable);
        PageInfo pageInfo = PageInfo.builder()
                .total(page.getTotalElements())
                .rows(page.getContent())
                .build();
        return pageInfo;
    }

    @Override
    public PageInfo findUserAndProjectPageByParams(ProjectVo vo, Pageable pageable) {
        Page<Map<String,Object>> page;
        if(vo.getName() == null || vo.getName().isEmpty()){
            page = projectDao.findAllUserAndProject(pageable);
        }else{
            page = projectDao.findAllUserAndProject(vo.getName(),pageable);
        }
        PageInfo pageInfo = PageInfo.builder()
                .total(page.getTotalElements())
                .rows(page.getContent())
                .build();
        return pageInfo;
    }

    @Override
    public boolean updateProject(Project project) {
        Project save = projectDao.save(project);
        return save != null ? true : false;
    }

    @Override
    public List<Project> findAll() {
        List<Project> all = projectDao.findAll();
        return all;
    }
}
