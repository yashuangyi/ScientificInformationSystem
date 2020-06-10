package com.yashuangyi.scientificinformationsystem.modules.message.dao;

import com.yashuangyi.scientificinformationsystem.modules.message.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @author yashuangyi
 * @version 1.0
 * @content
 * @date 2020-04-30 8:53
 */
public interface MessageDao extends JpaRepository<Message, Integer>, JpaSpecificationExecutor<Message> {

}
