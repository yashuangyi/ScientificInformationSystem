package com.yashuangyi.scientificinformationsystem.viewJumpControl;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author yashuangyi
 * @version 1.0
 * @content
 * @date 2020-04-22 18:25
 */
@Controller
public class BaseJumpController {

    @GetMapping("/login")
    String toLogin(){
        return "login";
    }

    @GetMapping("/userHome")
    String toUserHome(){
        return "user/userHome";
    }

    @GetMapping("/adminHome")
    String toAdminHome(){
        return "admin/adminHome";
    }

    @GetMapping("/adminHomePage")
    String toAdminHomePage(){
        return "admin/adminHomePage";
    }

    @GetMapping("/userHomePage")
    String toUserHomePage(){
        return "user/userHomePage";
    }

    @GetMapping("/declare")
    String toUserDeclare(){
        return "user/project";
    }

    @GetMapping("/info")
    String toInfo(){
        return "info";
    }

    @GetMapping("/editPW")
    String toEditPW(){
        return "editPW";
    }

    @GetMapping("/userManage")
    String toUserManage(){
        return "admin/userManage";
    }

    @GetMapping("/projectManage")
    String toProjectManage(){
        return "admin/projectManage";
    }

    @GetMapping("/prizeManage")
    String toPrizeManage(){
        return "admin/prizeManage";
    }

    @GetMapping("/patentManage")
    String toPatentManage(){
        return "admin/patentManage";
    }

    @GetMapping("/paperManage")
    String toPaperManage(){
        return "admin/paperManage";
    }

    @GetMapping("/myProject")
    String toMyProject(){
        return "user/myProject";
    }

    @GetMapping("/myPrize")
    String toMyPrize(){
        return "user/myPrize";
    }

    @GetMapping("/myPaper")
    String toMyPaper(){
        return "user/myPaper";
    }

    @GetMapping("/myPatent")
    String toMyPatent(){
        return "user/myPatent";
    }

    @GetMapping("/message")
    String toMessage(){
        return "message";
    }

    @GetMapping("/statisty")
    String toStatisty(){
        return "admin/statisty";
    }
}
