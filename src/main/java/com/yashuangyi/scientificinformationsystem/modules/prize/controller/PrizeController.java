package com.yashuangyi.scientificinformationsystem.modules.prize.controller;

import com.yashuangyi.scientificinformationsystem.common.GemBeanUtils;
import com.yashuangyi.scientificinformationsystem.common.PageInfo;
import com.yashuangyi.scientificinformationsystem.common.SisResult;
import com.yashuangyi.scientificinformationsystem.enums.ResultEnum;
import com.yashuangyi.scientificinformationsystem.modules.message.entity.Message;
import com.yashuangyi.scientificinformationsystem.modules.message.service.MessageService;
import com.yashuangyi.scientificinformationsystem.modules.prize.entity.Prize;
import com.yashuangyi.scientificinformationsystem.modules.prize.service.PrizeService;
import com.yashuangyi.scientificinformationsystem.modules.prize.vo.PrizeVo;
import com.yashuangyi.scientificinformationsystem.modules.project.entity.Project;
import com.yashuangyi.scientificinformationsystem.modules.project.service.ProjectService;
import com.yashuangyi.scientificinformationsystem.modules.project.vo.ProjectVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * @author yashuangyi
 * @version 1.0
 * @content
 * @date 2020-05-02 23:59
 */
@Controller
@RequestMapping("/prize")
public class PrizeController {

    @Autowired
    PrizeService prizeService;

    @Autowired
    ProjectService projectService;

    @Autowired
    MessageService messageService;

    @PostMapping("add")
    @ResponseBody
    public SisResult add(Integer projectId, String name){
        Prize old = prizeService.findPrizeByProjectId(projectId);
        if(old != null){
            return SisResult.build(404,"该项目已有奖项，请勿重复操作!");
        }
        Project project = projectService.findProjectById(projectId);
        Message message = new Message(project.getUserId(), 0, "您的项目-"+project.getName()+"获得奖项——"+name);
        messageService.addMessage(message);
        Prize prize = new Prize(projectId, name);
        return prizeService.addPrize(prize) == true ? SisResult.ok() : SisResult.build(ResultEnum.FAIL);
    }

    @DeleteMapping("delete")
    @ResponseBody
    public SisResult delete(Integer id){
        prizeService.deletePrizeById(id);
        return SisResult.ok();
    }

    @PostMapping("update")
    @ResponseBody
    public SisResult update(Integer projectId, String name){
        Project project = projectService.findProjectById(projectId);
        Message message = new Message(project.getUserId(), 0, "您的项目-"+project.getName()+"奖项已更改为——"+name);
        messageService.addMessage(message);
        Prize target = prizeService.findPrizeByProjectId(projectId);
        Prize prize = new Prize(projectId, name);
        GemBeanUtils.copyProperties(prize,target);
        return prizeService.updatePrize(target) == true ? SisResult.ok() : SisResult.build(ResultEnum.FAIL);
    }

    @GetMapping("pageByParams")
    @ResponseBody
    public SisResult pageByParams(PrizeVo vo, Pageable pageable){
        PageInfo pageInfo = prizeService.findPageByParams(vo, pageable);
        return SisResult.getTable(pageInfo);
    }

    @GetMapping("pageByUserParams")
    @ResponseBody
    public SisResult pageByUserParams(Integer userId, Pageable pageable){
        PageInfo pageInfo = prizeService.findPageByUserParams(userId, pageable);
        return SisResult.getTable(pageInfo);
    }
}
