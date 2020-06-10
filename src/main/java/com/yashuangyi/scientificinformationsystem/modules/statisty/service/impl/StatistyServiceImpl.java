package com.yashuangyi.scientificinformationsystem.modules.statisty.service.impl;

import com.yashuangyi.scientificinformationsystem.modules.statisty.dao.StatistyDao;
import com.yashuangyi.scientificinformationsystem.modules.statisty.service.StatistyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author yashuangyi
 * @version 1.0
 * @content
 * @date 2020-05-03 23:32
 */
@Service
public class StatistyServiceImpl implements StatistyService {

    @Autowired
    private StatistyDao statistyDao;

    @Override
    public int getProjectOne() {
        return statistyDao.getProjectOne();
    }

    @Override
    public int getProjectTwo() {
        return statistyDao.getProjectTwo();
    }

    @Override
    public int getProjectThree() {
        return statistyDao.getProjectThree();
    }

    @Override
    public int getProjectFour() {
        return statistyDao.getProjectFour();
    }

    @Override
    public int getProjectFive() {
        return statistyDao.getProjectFive();
    }

    @Override
    public int getProjectSix() {
        return statistyDao.getProjectSix();
    }
}
