package com.spring.base.entity;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class User {
    private Integer id;

    private String name;

    private Date birthday;

    private String sex;

    private String address;

    private String loginName;

    private String password;

    private String salt;

    List<Role> roleList;
}