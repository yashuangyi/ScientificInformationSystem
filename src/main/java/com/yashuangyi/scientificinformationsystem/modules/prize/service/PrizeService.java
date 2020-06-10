package com.yashuangyi.scientificinformationsystem.modules.prize.service;

import com.yashuangyi.scientificinformationsystem.common.PageInfo;
import com.yashuangyi.scientificinformationsystem.modules.prize.entity.Prize;
import com.yashuangyi.scientificinformationsystem.modules.prize.vo.PrizeVo;
import org.springframework.data.domain.Pageable;

/**
 * @author yashuangyi
 * @version 1.0
 * @content
 * @date 2020-05-03 0:01
 */
public interface PrizeService {

    boolean addPrize(Prize prize);

    boolean deletePrizeById(Integer id);

    boolean updatePrize(Prize prize);

    Prize findPrizeByProjectId(Integer projectId);

    PageInfo findPageByParams(PrizeVo vo, Pageable pageable);

    PageInfo findPageByUserParams(Integer userId, Pageable pageable);
}
