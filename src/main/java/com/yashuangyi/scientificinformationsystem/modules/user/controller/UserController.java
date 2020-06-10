package com.yashuangyi.scientificinformationsystem.modules.user.controller;

import com.yashuangyi.scientificinformationsystem.common.GemBeanUtils;
import com.yashuangyi.scientificinformationsystem.common.PageInfo;
import com.yashuangyi.scientificinformationsystem.common.SisResult;
import com.yashuangyi.scientificinformationsystem.enums.ResultEnum;
import com.yashuangyi.scientificinformationsystem.modules.project.vo.ProjectVo;
import com.yashuangyi.scientificinformationsystem.modules.user.entity.User;
import com.yashuangyi.scientificinformationsystem.modules.user.service.UserService;
import com.yashuangyi.scientificinformationsystem.modules.user.vo.AddVo;
import com.yashuangyi.scientificinformationsystem.modules.user.vo.UpdateVo;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author yashuangyi
 * @version 1.0
 * @content
 * @date 2020-04-21 13:01
 */
@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    @ApiOperation(value = "增加",notes = "")
    @PostMapping("add")
    @ResponseBody
    public SisResult add(AddVo addVo){
        User byNo = userService.findByAccount(addVo.getAccount());
        if(byNo!=null){
            return SisResult.build(ResultEnum.USER_ACCOUNT_ALREADY_EXIST);
        }
        User user = User.translate(addVo);
        return userService.addUser(user) == true ? SisResult.ok() : SisResult.build(ResultEnum.FAIL);
    }

    @ApiOperation(value = "删除",notes = "")
    @DeleteMapping("delete")
    @ResponseBody
    public SisResult delete(Integer id){
        userService.deleteUserById(id);
        return SisResult.ok();
    }

    @ApiOperation(value = "更新",notes = "")
    @PostMapping("update")
    @ResponseBody
    public SisResult update(User user){
        User target = userService.findUserById(user.getId());
        GemBeanUtils.copyProperties(user,target);
        return userService.updateUser(target) == true ? SisResult.ok() : SisResult.build(ResultEnum.FAIL);
    }

    @ApiOperation(value = "获取全部",notes = "")
    @GetMapping("queryAll")
    @ResponseBody
    public SisResult queryAll(){
        return SisResult.ok(userService.findAll());
    }

    @ApiOperation(value = "通过名字查询",notes = "")
    @PostMapping("queryByName")
    @ResponseBody
    public SisResult queryByName(String name){
        return SisResult.ok(userService.findUserByName(name));
    }

    @ApiOperation(value = "通过ID查询",notes = "")
    @PostMapping("queryById")
    @ResponseBody
    public SisResult queryById(Integer id){
        User user = userService.findUserById(id);
        return user==null?SisResult.build(ResultEnum.FAIL):SisResult.ok(user);
    }

    @ApiOperation(value = "更改密码")
    @PostMapping("updatePassWord")
    @ResponseBody
    SisResult updatePassWord(Integer id,String oldPassWord,String newPassword){
        User userById = userService.findUserById(id);
        if(!userById.getPassword().equals(oldPassWord)){
            return SisResult.build(ResultEnum.OLD_PASSWORD_ERROR);
        }
        userById.setPassword(newPassword);
        boolean b = userService.addUser(userById);
        return b==true?SisResult.ok():SisResult.build(ResultEnum.FAIL);
    }


    @ApiOperation(value = "通过账号查找用户")
    @PostMapping("queryByUserAccount")
    String queryByUserAccount(String account, Map<String,Object> map){
        User byNo = userService.findByAccount(account);
        List<User> list=new ArrayList<>();
        list.add(byNo);
        map.put("userInfo",list);
        return "permission/index";
    }

    @GetMapping("getName")
    @ResponseBody
    SisResult getName(Integer userId){
        User user = userService.findUserById(userId);
        Map <String,String> data=new HashMap<>();
        data.put("name",user.getName());
        return SisResult.build(200,"返回名字",data);
    }

    @GetMapping("getAccount")
    @ResponseBody
    SisResult getAccount(Integer userId){
        User user = userService.findUserById(userId);
        Map <String,String> data=new HashMap<>();
        data.put("account",user.getAccount());
        return SisResult.build(200,"返回账号",data);
    }

    @GetMapping("pageByParams")
    @ResponseBody
    public SisResult pageByParams(UpdateVo vo, Pageable pageable){
        PageInfo pageInfo = userService.findPageByParams(vo, pageable);
        return SisResult.getTable(pageInfo);
    }
}
