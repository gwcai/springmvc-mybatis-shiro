package com.spring.base.entity;

import lombok.Data;

import java.util.List;

@Data
public class Permission {
    private Integer id;

    private String code;

    private String name;

    private String remark;

    List<Role> roleList;
}