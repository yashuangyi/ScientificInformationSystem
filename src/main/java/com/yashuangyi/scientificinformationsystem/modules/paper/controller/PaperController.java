package com.yashuangyi.scientificinformationsystem.modules.paper.controller;

import com.yashuangyi.scientificinformationsystem.common.GemBeanUtils;
import com.yashuangyi.scientificinformationsystem.common.PageInfo;
import com.yashuangyi.scientificinformationsystem.common.SisResult;
import com.yashuangyi.scientificinformationsystem.enums.ResultEnum;
import com.yashuangyi.scientificinformationsystem.modules.message.entity.Message;
import com.yashuangyi.scientificinformationsystem.modules.message.service.MessageService;
import com.yashuangyi.scientificinformationsystem.modules.paper.entity.Paper;
import com.yashuangyi.scientificinformationsystem.modules.paper.service.PaperService;
import com.yashuangyi.scientificinformationsystem.modules.paper.vo.PaperVo;
import com.yashuangyi.scientificinformationsystem.modules.project.entity.Project;
import com.yashuangyi.scientificinformationsystem.modules.project.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * @author yashuangyi
 * @version 1.0
 * @content
 * @date 2020-05-02 23:58
 */
@Controller
@RequestMapping("/paper")
public class PaperController {

    @Autowired
    PaperService paperService;

    @Autowired
    ProjectService projectService;

    @Autowired
    MessageService messageService;

    private static Integer adminNum = 3;

    @PostMapping("add")
    @ResponseBody
    public SisResult add(Integer projectId, String path){
        Paper old = paperService.findPaperByProjectId(projectId);
        if(old != null){
            return SisResult.build(404,"您已上传过该项目的论文，请勿重复操作!");
        }
        Project project = projectService.findProjectById(projectId);
        for(int i=1;i<=adminNum;i++){
            Message message = new Message(i, 1, "项目-"+project.getName()+"已上传论文，请及时查阅");
            messageService.addMessage(message);
        }
        Paper paper = new Paper(projectId, path);
        return paperService.addPaper(paper) == true ? SisResult.ok() : SisResult.build(ResultEnum.FAIL);
    }

    @DeleteMapping("delete")
    @ResponseBody
    public SisResult delete(Integer id){
        paperService.deletePaperById(id);
        return SisResult.ok();
    }

    @PostMapping("update")
    @ResponseBody
    public SisResult update(Integer projectId, String path){
        Project project = projectService.findProjectById(projectId);
        for(int i=1;i<=adminNum;i++){
            Message message = new Message(i, 1, "项目-"+project.getName()+"已修正论文，请及时查阅");
            messageService.addMessage(message);
        }
        Paper target = paperService.findPaperByProjectId(projectId);
        Paper paper = new Paper(projectId, path);
        GemBeanUtils.copyProperties(paper,target);
        return paperService.updatePaper(target) == true ? SisResult.ok() : SisResult.build(ResultEnum.FAIL);
    }

    @GetMapping("pageByParams")
    @ResponseBody
    public SisResult pageByParams(PaperVo vo, Pageable pageable){
        PageInfo pageInfo = paperService.findPageByParams(vo, pageable);
        return SisResult.getTable(pageInfo);
    }

    @GetMapping("pageByUserParams")
    @ResponseBody
    public SisResult pageUserAndProjectByParams(Integer userId, Pageable pageable){
        PageInfo pageInfo = paperService.findPageByUserParams(userId, pageable);
        return SisResult.getTable(pageInfo);
    }
}
