package com.yashuangyi.scientificinformationsystem.modules.paper.service.impl;

import com.yashuangyi.scientificinformationsystem.common.PageInfo;
import com.yashuangyi.scientificinformationsystem.modules.paper.dao.PaperDao;
import com.yashuangyi.scientificinformationsystem.modules.paper.entity.Paper;
import com.yashuangyi.scientificinformationsystem.modules.paper.service.PaperService;
import com.yashuangyi.scientificinformationsystem.modules.paper.vo.PaperVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author yashuangyi
 * @version 1.0
 * @content
 * @date 2020-05-03 0:00
 */
@Service
public class PaperServiceImpl implements PaperService {

    @Autowired
    private PaperDao paperDao;

    @Override
    public boolean addPaper(Paper paper) {
        Paper save = paperDao.save(paper);
        return save != null ? true : false;
    }

    @Override
    public boolean deletePaperById(Integer id) {
        paperDao.deleteById(id);
        return true;
    }

    @Override
    public boolean updatePaper(Paper paper) {
        Paper save = paperDao.save(paper);
        return save != null ? true : false;
    }

    @Override
    public Paper findPaperByProjectId(Integer projectId) {
        return paperDao.findByProjectId(projectId);
    }

    @Override
    public PageInfo findPageByParams(PaperVo vo, Pageable pageable) {
        Page<Map<String,Object>> page;
        if(vo.getProjectName() == null || vo.getProjectName().isEmpty()){
            page = paperDao.findAllPaper(pageable);
        }else{
            page = paperDao.findAllPaper(vo.getProjectName(), pageable);
        }
        PageInfo pageInfo = PageInfo.builder()
                .total(page.getTotalElements())
                .rows(page.getContent())
                .build();
        return pageInfo;
    }

    @Override
    public PageInfo findPageByUserParams(Integer userId, Pageable pageable) {
        Page<Map<String,Object>> page = paperDao.findUserPaper(userId, pageable);
        PageInfo pageInfo = PageInfo.builder()
                .total(page.getTotalElements())
                .rows(page.getContent())
                .build();
        return pageInfo;
    }
}
