package com.yashuangyi.scientificinformationsystem.modules.message.controller;

import com.yashuangyi.scientificinformationsystem.common.PageInfo;
import com.yashuangyi.scientificinformationsystem.common.SisResult;
import com.yashuangyi.scientificinformationsystem.modules.message.service.MessageService;
import com.yashuangyi.scientificinformationsystem.modules.message.vo.MessageVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * @author yashuangyi
 * @version 1.0
 * @content
 * @date 2020-04-30 8:54
 */
@Controller
@RequestMapping("/message")
public class MessageController {

    @Autowired
    MessageService messageService;

    @GetMapping("pageByParams")
    @ResponseBody
    public SisResult pageByParams(Integer userId, String power, MessageVo vo, Pageable pageable){
        PageInfo pageInfo;
        if(power.equals("admin")){
            pageInfo = messageService.findPageByParams(userId,1,vo, pageable);
        }else{
            pageInfo = messageService.findPageByParams(userId,0,vo, pageable);
        }
        return SisResult.getTable(pageInfo);
    }
}
