package com.yashuangyi.scientificinformationsystem.modules.paper.service;

import com.yashuangyi.scientificinformationsystem.common.PageInfo;
import com.yashuangyi.scientificinformationsystem.modules.paper.entity.Paper;
import com.yashuangyi.scientificinformationsystem.modules.paper.vo.PaperVo;
import org.springframework.data.domain.Pageable;

/**
 * @author yashuangyi
 * @version 1.0
 * @content
 * @date 2020-05-03 0:00
 */
public interface PaperService {

    boolean addPaper(Paper paper);

    boolean deletePaperById(Integer id);

    Paper findPaperByProjectId(Integer projectId);

    boolean updatePaper(Paper paper);

    PageInfo findPageByParams(PaperVo vo, Pageable pageable);

    PageInfo findPageByUserParams(Integer userId, Pageable pageable);
}
