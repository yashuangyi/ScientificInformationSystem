package com.yashuangyi.scientificinformationsystem.modules.patent.service;

import com.yashuangyi.scientificinformationsystem.common.PageInfo;
import com.yashuangyi.scientificinformationsystem.modules.patent.entity.Patent;
import com.yashuangyi.scientificinformationsystem.modules.patent.vo.PatentVo;
import org.springframework.data.domain.Pageable;

/**
 * @author yashuangyi
 * @version 1.0
 * @content
 * @date 2020-05-03 0:01
 */
public interface PatentService {

    boolean addPatent(Patent patent);

    boolean deletePatentById(Integer id);

    Patent findPatentByProjectId(Integer projectId);

    PageInfo findPageByParams(PatentVo vo, Pageable pageable);

    PageInfo findPageByUserParams(Integer userId, Pageable pageable);
}
