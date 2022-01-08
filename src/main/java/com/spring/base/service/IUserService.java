package com.spring.base.service;

import com.spring.base.entity.User;

/**
 * @author lili14520@gmail.com
 * @date 2018/7/5
 */
public interface IUserService extends IBaseService<User,Integer>{
    User findByLoginName(String loginName);
    int updatePassword(User user);
}
