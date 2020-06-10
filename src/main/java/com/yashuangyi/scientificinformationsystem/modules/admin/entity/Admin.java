package com.yashuangyi.scientificinformationsystem.modules.admin.entity;

import lombok.Data;

import javax.persistence.*;

/**
 * @author yashuangyi
 * @version 1.0
 * @content
 * @date 2020-04-22 20:53
 */
@Entity
@Data
@Table(name = "Admin")
public class Admin {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @Column(unique = true)
    private String account;
    private String password;
    private String name;

    public Admin(String account, String password, String name) {
        this.account = account;
        this.password = password;
        this.name = name;
    }

    public Admin() {
    }
}

