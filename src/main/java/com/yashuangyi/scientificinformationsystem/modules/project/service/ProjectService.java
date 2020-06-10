package com.yashuangyi.scientificinformationsystem.modules.project.service;

import com.yashuangyi.scientificinformationsystem.common.PageInfo;
import com.yashuangyi.scientificinformationsystem.modules.project.entity.Project;
import com.yashuangyi.scientificinformationsystem.modules.project.vo.ProjectVo;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @author yashuangyi
 * @version 1.0
 * @content
 * @date 2020-04-24 20:42
 */
public interface ProjectService {

    boolean addProject(Project project);

    boolean deleteProjectById(Integer id);

    boolean updateProject(Project project);

    List<Project> findAll();

    Project findProjectById(Integer id);

    PageInfo findPageByParams(ProjectVo vo, Pageable pageable);

    PageInfo findUserAndProjectPageByParams(ProjectVo vo, Pageable pageable);

    int getUserPass(Integer id);

    int getUserPrize(Integer id);

    int getUserDeclare(Integer id);

    int getAdminHasDeclare();

    int getAdminShouldPass();

    int getAdminShouldGrant();
}
