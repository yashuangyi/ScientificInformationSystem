package com.yashuangyi.scientificinformationsystem.modules.user.entity;

import com.yashuangyi.scientificinformationsystem.modules.user.vo.AddVo;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;

/**
 * @author yashuangyi
 * @version 1.0
 * @content 用户实体类
 * @date 2020-04-21 10:51
 */
@Entity
@Data
@AllArgsConstructor
@Table(name = "User")
public class User {
    @Id
    @GeneratedValue
    private Integer id;
    @Column(unique = true)
    private String account;
    private String password;
    private String name;

    public static User translate(AddVo addVo){
        return new User(addVo.getAccount(),addVo.getPassword(),addVo.getName());
    }

    public User(String account, String password, String name) {
        this.account = account;
        this.password = password;
        this.name = name;
    }

    public User() {
    }
}
