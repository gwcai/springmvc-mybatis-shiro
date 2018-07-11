package com.spring.base.entity;

import lombok.Data;

import java.util.List;

@Data
public class Role {
    private Integer id;

    private String name;

    private String code;

    private String remark;

    List<User> userList;

    List<Permission> permissionList;
}