package com.spring.base.service.impl;

import com.spring.base.dao.mapper.UserMapper;
import com.spring.base.entity.User;
import com.spring.base.query.UserQuery;
import com.spring.base.service.IUserService;
import com.spring.base.system.BaseQuery;
import com.spring.base.system.MD5Util;
import com.spring.base.system.Page;
import com.spring.base.system.ShiroUserRealm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author lili14520@gmail.com
 * @date 2018/7/5
 */
@Service("userService")
public class UserServiceImpl implements IUserService {
    @Autowired
    UserMapper userMapper;

    @Override
    public Page<User> findAll(BaseQuery query) {
        Page<User> page = new Page<>();
        page.setPageIndex(query.getPageIndex());
        page.setPageSize(query.getPageSize());
        page.setTotal(userMapper.count((UserQuery) query));
        page.setRows(userMapper.findAll((UserQuery) query));
        return page;
    }

    @Override
    public User findById(Integer id) {
        return userMapper.selectByPrimaryKey(id);
    }

    @Override
    public int save(User user) {
        if(null == user.getId()){
            user = setPassword(user);
            return userMapper.insert(user);
        }
        return userMapper.updateByPrimaryKey(user);
    }

    @Override
    public int delete(Integer id) {
        return userMapper.deleteByPrimaryKey(id);
    }

    @Override
    public User findByLoginName(String loginName) {
        return userMapper.selectByLoginName(loginName);
    }

    @Override
    public int updatePassword(User user) {
        user = setPassword(user);
        return updatePassword(user);
    }

    private User setPassword(User user){
        String salt = String.valueOf(System.currentTimeMillis());
        user.setPassword(ShiroUserRealm.getPasswrod(MD5Util.getRowMD5(user.getPassword()),salt));
        user.setSalt(salt);
        return user;
    }
}
