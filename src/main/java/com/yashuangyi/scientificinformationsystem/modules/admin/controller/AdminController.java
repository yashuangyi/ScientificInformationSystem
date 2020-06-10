package com.yashuangyi.scientificinformationsystem.modules.admin.controller;

import com.yashuangyi.scientificinformationsystem.common.GemBeanUtils;
import com.yashuangyi.scientificinformationsystem.common.SisResult;
import com.yashuangyi.scientificinformationsystem.enums.ResultEnum;
import com.yashuangyi.scientificinformationsystem.modules.admin.entity.Admin;
import com.yashuangyi.scientificinformationsystem.modules.admin.service.AdminService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * @author yashuangyi
 * @version 1.0
 * @content
 * @date 2020-04-22 20:32
 */
@Controller
@RequestMapping("admin")
public class AdminController {

    @Autowired
    AdminService adminService;

    @ApiOperation(value = "更新",notes = "")
    @PostMapping("update")
    @ResponseBody
    SisResult update(Admin admin){
        Admin target = adminService.findAdminById(admin.getId());
        GemBeanUtils.copyProperties(admin,target);
        return adminService.updateAdmin(target) == true ? SisResult.ok() : SisResult.build(ResultEnum.FAIL);
    }

    @GetMapping("getName")
    @ResponseBody
    SisResult getName(Integer userId){
        Admin user = adminService.findAdminById(userId);
        Map<String,String> data=new HashMap<>();
        data.put("name",user.getName());
        return SisResult.build(200,"返回名字",data);
    }

    @GetMapping("getAccount")
    @ResponseBody
    SisResult getAccount(Integer userId){
        Admin user = adminService.findAdminById(userId);
        Map<String,String> data=new HashMap<>();
        data.put("account",user.getAccount());
        return SisResult.build(200,"返回账号",data);
    }
}
