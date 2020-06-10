package com.yashuangyi.scientificinformationsystem.modules.user.service;

import com.yashuangyi.scientificinformationsystem.common.PageInfo;
import com.yashuangyi.scientificinformationsystem.modules.project.vo.ProjectVo;
import com.yashuangyi.scientificinformationsystem.modules.user.entity.User;
import com.yashuangyi.scientificinformationsystem.modules.user.vo.UpdateVo;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @author yashuangyi
 * @version 1.0
 * @content
 * @date 2020-04-21 11:08
 */
public interface UserService {

    boolean addUser(User user);

    boolean deleteUserById(Integer id);

    boolean updateUser(User user);

    List<User> findUserByName(String name);

    User findUserById(Integer id);

    List<User> findAll();

    User findByAccount(String account);

    boolean isExist(String account,String password);

    PageInfo findPageByParams(UpdateVo vo, Pageable pageable);

}
