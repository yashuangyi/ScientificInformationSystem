package com.yashuangyi.scientificinformationsystem.modules.project.controller;
import java.text.SimpleDateFormat;
import	java.util.UUID;
import	java.io.IOException;

import com.yashuangyi.scientificinformationsystem.common.GemBeanUtils;
import com.yashuangyi.scientificinformationsystem.common.PageInfo;
import com.yashuangyi.scientificinformationsystem.common.SisResult;
import com.yashuangyi.scientificinformationsystem.enums.ResultEnum;
import com.yashuangyi.scientificinformationsystem.modules.message.entity.Message;
import com.yashuangyi.scientificinformationsystem.modules.message.service.MessageService;
import com.yashuangyi.scientificinformationsystem.modules.pass.entity.Pass;
import com.yashuangyi.scientificinformationsystem.modules.pass.service.PassService;
import com.yashuangyi.scientificinformationsystem.modules.project.entity.Project;
import com.yashuangyi.scientificinformationsystem.modules.project.service.ProjectService;
import com.yashuangyi.scientificinformationsystem.modules.project.vo.ProjectAddVo;
import com.yashuangyi.scientificinformationsystem.modules.project.vo.ProjectVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * @author yashuangyi
 * @version 1.0
 * @content
 * @date 2020-04-24 20:44
 */
@Controller
@RequestMapping("/project")
public class ProjectController {

    @Autowired
    ProjectService projectService;

    @Autowired
    PassService passService;

    @Autowired
    MessageService messageService;

    private static Integer adminNum = 3;

    @PostMapping("add")
    @ResponseBody
    public SisResult add(ProjectAddVo addVo){
        Project project = Project.translate(addVo);
        for(int i=1;i<=adminNum;i++){
            Message message = new Message(i, 1, "项目-"+addVo.getName()+"已申报，请及时查阅并授权");
            messageService.addMessage(message);
        }
        return projectService.addProject(project) == true ? SisResult.ok() : SisResult.build(ResultEnum.FAIL);
    }

    @DeleteMapping("delete")
    @ResponseBody
    public SisResult delete(Integer id){
        projectService.deleteProjectById(id);

        return SisResult.ok();
    }

    @PostMapping("submitResult")
    @ResponseBody
    public SisResult submitResult(Project project){
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Project target = projectService.findProjectById(project.getId());
        Project status = new Project(project.getId(),"待审批", df.format(System.currentTimeMillis()));
        GemBeanUtils.copyProperties(project,target);
        GemBeanUtils.copyProperties(status,target);
        for(int i=1;i<=adminNum;i++){
            Message message = new Message(i, 1, "项目-"+target.getName()+"已提交成果，请及时查阅并审批");
            messageService.addMessage(message);
        }
        return projectService.updateProject(target) == true ? SisResult.ok() : SisResult.build(ResultEnum.FAIL);
    }

    @PostMapping("license")
    @ResponseBody
    public SisResult license(Integer id){
        Project project = new Project(id,"已授权-开发中");
        Project target = projectService.findProjectById(id);
        GemBeanUtils.copyProperties(project,target);
        Message message = new Message(target.getUserId(), 0, "您的项目-"+target.getName()+"已获得授权，您可开始项目的开发");
        messageService.addMessage(message);
        return projectService.updateProject(target) == true ? SisResult.ok() : SisResult.build(ResultEnum.FAIL);
    }

    @PostMapping("no-license")
    @ResponseBody
    public SisResult noLicense(Integer id){
        Project project = new Project(id,"授权不通过");
        Project target = projectService.findProjectById(id);
        GemBeanUtils.copyProperties(project,target);
        Message message = new Message(target.getUserId(), 0, "您的项目-"+target.getName()+"未获得授权，请再接再厉");
        messageService.addMessage(message);
        return projectService.updateProject(target) == true ? SisResult.ok() : SisResult.build(ResultEnum.FAIL);
    }

    @PostMapping("pass")
    @ResponseBody
    public SisResult pass(Integer projectId, Integer adminId){
        if(passService.existsPassByProjectIdAndAdminId(projectId,adminId)){
            return SisResult.build(404,"您已经审批过该项目了，请勿重复操作!");
        }
        Pass pass = new Pass(projectId, adminId, "通过");
        passService.addPass(pass);
        Project target = projectService.findProjectById(projectId);
        Project project;
        if(passService.getPassCount(projectId)==3){
            project = new Project(projectId, target.getPassNum()+1, "审批通过");
            Message message = new Message(target.getUserId(), 0, "您的项目-"+target.getName()+"已通过审批，请耐心等待奖项及专利的颁发~");
            messageService.addMessage(message);
        }else{
            project = new Project(projectId, target.getPassNum()+1);
        }
        GemBeanUtils.copyProperties(project,target);
        return projectService.updateProject(target) == true ? SisResult.ok() : SisResult.build(ResultEnum.FAIL);
    }

    @PostMapping("no-pass")
    @ResponseBody
    public SisResult noPass(Integer projectId, Integer adminId){
        if(passService.existsPassByProjectIdAndAdminId(projectId,adminId)){
            return SisResult.build(404,"您已经审批过该项目了，请勿重复操作!");
        }
        Pass pass = new Pass(projectId, adminId, "不通过");
        passService.addPass(pass);
        Project target = projectService.findProjectById(projectId);
        Project project = new Project(projectId, target.getPassNum()+1, "审批不通过");
        GemBeanUtils.copyProperties(project,target);
        Message message = new Message(target.getUserId(), 0, "您的项目-"+target.getName()+"未通过审批，请再接再厉");
        messageService.addMessage(message);
        return projectService.updateProject(target) == true ? SisResult.ok() : SisResult.build(ResultEnum.FAIL);
    }

    @PostMapping("submit")
    @ResponseBody
    public SisResult submit(@RequestParam("file") MultipartFile file,  String dict) throws IOException {
        if(!file.isEmpty()){
            Map<String,String> data = new HashMap<>();
            String fileName = file.getOriginalFilename();
            //File directory = new File("src/main/resources/");
            //String path = directory.getCanonicalPath()+"/static/"+dict;
            File directory = new File("C:/sources/");
            String path = directory.getCanonicalPath()+"/"+dict;
            String name = UUID.randomUUID().toString();
            String ext = file.getOriginalFilename().substring(
                    file.getOriginalFilename().lastIndexOf("."));
            data.put("name",fileName);
            //data.put("path",path+name+ext);
            data.put("path",dict+name+ext);
            try{
                BufferedOutputStream out = new BufferedOutputStream(
                        new FileOutputStream(new File(path, name+ext)));
                out.write(file.getBytes());
                out.flush();
                out.close();
            }catch (IOException e){
                e.printStackTrace();
                return SisResult.build(ResultEnum.FAIL);
            }
            return SisResult.build(200,"上传成功！",data);
        }else{
            return SisResult.build(ResultEnum.FAIL);
        }
    }

    @GetMapping("queryAll")
    @ResponseBody
    public SisResult queryAll(){
        return SisResult.ok(projectService.findAll());
    }

    @GetMapping("pageByParams")
    @ResponseBody
    public SisResult pageByParams(ProjectVo vo, Pageable pageable){
        PageInfo pageInfo = projectService.findPageByParams(vo, pageable);
        return SisResult.getTable(pageInfo);
    }

    @GetMapping("pageUserAndProjectByParams")
    @ResponseBody
    public SisResult pageUserAndProjectByParams(ProjectVo vo, Pageable pageable){
        PageInfo pageInfo = projectService.findUserAndProjectPageByParams(vo, pageable);
        return SisResult.getTable(pageInfo);
    }

    @GetMapping("getUserCount")
    @ResponseBody
    SisResult getUserCount(Integer userId){
        Map<String,Integer> data=new HashMap<>();
        data.put("declare",projectService.getUserDeclare(userId));
        data.put("prize",projectService.getUserPrize(userId));
        data.put("pass",projectService.getUserPass(userId));
        return SisResult.build(200,"返回名字",data);
    }

    @GetMapping("getAdminCount")
    @ResponseBody
    SisResult getAdminCount(){
        Map<String,Integer> data=new HashMap<>();
        data.put("shouldGrant",projectService.getAdminShouldGrant());
        data.put("shouldPass",projectService.getAdminShouldPass());
        data.put("hasDeclare",projectService.getAdminHasDeclare());
        return SisResult.build(200,"返回名字",data);
    }
}
