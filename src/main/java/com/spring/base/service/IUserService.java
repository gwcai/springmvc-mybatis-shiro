package com.spring.base.service;

import com.spring.base.entity.User;

/**
 * @author GaoWeicai.(gaowc@gfire.cn)
 * @date 2018/7/5
 */
public interface IUserService {
    User getById(int id);

    User getByLoginName(String loginName);
}
