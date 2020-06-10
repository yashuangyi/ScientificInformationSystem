package com.yashuangyi.scientificinformationsystem.modules.message.service;

import com.yashuangyi.scientificinformationsystem.common.PageInfo;
import com.yashuangyi.scientificinformationsystem.modules.message.entity.Message;
import com.yashuangyi.scientificinformationsystem.modules.message.vo.MessageVo;
import org.springframework.data.domain.Pageable;

/**
 * @author yashuangyi
 * @version 1.0
 * @content
 * @date 2020-04-30 8:55
 */
public interface MessageService {

    boolean addMessage(Message message);

    PageInfo findPageByParams(Integer userId, Integer flag, MessageVo vo, Pageable pageable);
}
