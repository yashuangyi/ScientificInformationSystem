package com.yashuangyi.scientificinformationsystem.modules.user.service.impl;

import com.yashuangyi.scientificinformationsystem.common.GemBeanUtils;
import com.yashuangyi.scientificinformationsystem.common.PageInfo;
import com.yashuangyi.scientificinformationsystem.modules.project.entity.Project;
import com.yashuangyi.scientificinformationsystem.modules.user.dao.UserDao;
import com.yashuangyi.scientificinformationsystem.modules.user.entity.User;
import com.yashuangyi.scientificinformationsystem.modules.user.service.UserService;
import com.yashuangyi.scientificinformationsystem.modules.user.vo.UpdateVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author yashuangyi
 * @version 1.0
 * @content
 * @date 2020-04-21 11:11
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    public boolean addUser(User user) {
        User save = userDao.save(user);
        return save != null ? true : false;
    }

    @Override
    public boolean deleteUserById(Integer id) {
        userDao.deleteById(id);
        return true;
    }

    @Override
    public PageInfo findPageByParams(UpdateVo vo, Pageable pageable) {
        User entity = new User();
        GemBeanUtils.copyProperties(vo, entity);
        //创建匹配器，即如何使用查询条件
        ExampleMatcher matcher = ExampleMatcher.matching() //构建对象
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING) //改变默认字符串匹配方式：模糊查询
                .withIgnoreCase(true); //改变默认大小写忽略方式：忽略大小写
        Page<User> page = userDao.findAll(Example.of(entity,matcher),pageable);
        PageInfo pageInfo = PageInfo.builder()
                .total(page.getTotalElements())
                .rows(page.getContent())
                .build();
        return pageInfo;
    }

    @Override
    public boolean updateUser(User user) {
        User save = userDao.save(user);
        return save != null ? true : false;
    }

    @Override
    public List<User> findUserByName(String name) {
        List<User> l = userDao.findByName(name);
        for(User user:l){
            user.setPassword("***");
        }
        return l;
    }

    @Override
    public User findUserById(Integer id) {
        User user = userDao.findById(id).get();
        return user;
    }

    @Override
    public List<User> findAll() {
        List<User> all = userDao.findAll();
        for(User user:all){
            user.setPassword("***");
        }
        return all;
    }

    @Override
    public User findByAccount(String account) {
        return userDao.findByAccount(account);
    }

    @Override
    public boolean isExist(String account, String password) {
        return userDao.findByAccountAndPassword(account,password)!=null?true:false;
    }
}
