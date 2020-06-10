package com.yashuangyi.scientificinformationsystem.modules.admin.service;

import com.yashuangyi.scientificinformationsystem.modules.admin.entity.Admin;

/**
 * @author yashuangyi
 * @version 1.0
 * @content
 * @date 2020-04-22 20:53
 */
public interface AdminService {

    boolean updateAdmin(Admin admin);

    Admin findByAccount(String account);

    Admin findAdminById(Integer id);

}
