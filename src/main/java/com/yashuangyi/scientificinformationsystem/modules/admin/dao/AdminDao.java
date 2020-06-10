package com.yashuangyi.scientificinformationsystem.modules.admin.dao;

import com.yashuangyi.scientificinformationsystem.modules.admin.entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author yashuangyi
 * @version 1.0
 * @content
 * @date 2020-04-22 20:53
 */
public interface AdminDao extends JpaRepository<Admin,Integer> {

    Admin findByAccount(String account);

    Admin findByAccountAndPassword(String account, String password);

}
