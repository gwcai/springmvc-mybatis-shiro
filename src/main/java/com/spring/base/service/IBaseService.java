package com.spring.base.service;

import com.spring.base.system.BaseQuery;
import com.spring.base.system.Page;

import java.io.Serializable;

/**
 * @author lili14520@gmail.com
 * @date 2018/7/17
 */
public interface IBaseService<T,PK extends Serializable> {
    Page<T> findAll(BaseQuery query);
    T findById(PK id);
    int save(T entity);
    int delete(PK id);
}
