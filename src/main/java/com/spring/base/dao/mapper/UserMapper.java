package com.spring.base.dao.mapper;

import com.spring.base.entity.User;
import com.spring.base.query.UserQuery;
import com.spring.base.system.BaseQuery;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    User selectByLoginName(String loginName);

    List<User> findAll(UserQuery query);

    Long count(UserQuery query);

    int updatePassword(User user);
}