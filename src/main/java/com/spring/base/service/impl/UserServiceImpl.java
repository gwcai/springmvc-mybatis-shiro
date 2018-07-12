package com.spring.base.service.impl;

import com.spring.base.dao.mapper.UserMapper;
import com.spring.base.entity.User;
import com.spring.base.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author GaoWeicai.(gaowc@gfire.cn)
 * @date 2018/7/5
 */
@Service("userService")
public class UserServiceImpl implements IUserService {
    @Autowired
    UserMapper userMapper;

    public User getById(int id) {
        return userMapper.selectByPrimaryKey(id);
    }

    public User getByLoginName(String loginName) {
        return userMapper.selectByLoginName(loginName);
    }

    @Override
    public int save(User user) {
        if(null == user.getId()){
            return userMapper.insert(user);
        }
        return userMapper.updateByPrimaryKey(user);
    }
}
