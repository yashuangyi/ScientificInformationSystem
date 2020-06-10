package com.yashuangyi.scientificinformationsystem.modules.statisty.controller;

import com.yashuangyi.scientificinformationsystem.common.SisResult;
import com.yashuangyi.scientificinformationsystem.modules.statisty.service.StatistyService;
import com.yashuangyi.scientificinformationsystem.modules.statisty.vo.Echarts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;

/**
 * @author yashuangyi
 * @version 1.0
 * @content
 * @date 2020-05-03 23:29
 */
@Controller
@RequestMapping("/statisty")
public class StatistyController {

    @Autowired
    StatistyService statistyService;

    @GetMapping("getEchartsPie")
    @ResponseBody
    public SisResult getEchartsPie(){
        ArrayList<Echarts> list = new ArrayList<Echarts>();
        Echarts echarts = new Echarts("待授权",statistyService.getProjectOne());
        list.add(echarts);
        echarts = new Echarts("已授权-开发中",statistyService.getProjectTwo());
        list.add(echarts);
        echarts = new Echarts("授权不通过",statistyService.getProjectThree());
        list.add(echarts);
        echarts = new Echarts("待审批",statistyService.getProjectFour());
        list.add(echarts);
        echarts = new Echarts("审批通过",statistyService.getProjectFive());
        list.add(echarts);
        echarts = new Echarts("审批不通过",statistyService.getProjectSix());
        list.add(echarts);
        return SisResult.build(200,"",list);
    }
}
