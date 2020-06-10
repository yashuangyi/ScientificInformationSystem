package com.yashuangyi.scientificinformationsystem.modules.message.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.text.SimpleDateFormat;

/**
 * @author yashuangyi
 * @version 1.0
 * @content
 * @date 2020-04-30 8:45
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Message")
public class Message {
    @Id
    @GeneratedValue
    private Integer id;
    @Column(unique = true)
    private Integer targetId;
    private Integer flag;
    private String time;
    private String content;

    public Message(Integer targetId, Integer flag, String content) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        this.targetId = targetId;
        this.flag = flag;
        this.time = df.format(System.currentTimeMillis());
        this.content = content;
    }

    public Message(Integer flag, Integer targetId){
        this.flag = flag;
        this.targetId = targetId;
    }
}
