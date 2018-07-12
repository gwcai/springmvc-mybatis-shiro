package com.spring.base.form;

import com.spring.base.entity.Role;
import com.spring.base.entity.User;
import com.spring.base.system.BaseForm;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

/**
 * @author GaoWeicai.(gaowc@gfire.cn)
 * @date 2018/7/12
 */
@Getter
@Setter
public class UserForm extends BaseForm<User,Integer>{
    private String name;

    private Date birthday;

    private String sex;

    private String address;

    private String loginName;

    private String password;

    private String salt;

    List<Role> roleList;
}
