package com.spring.base.system;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;

/**
 * @author GaoWeicai.(gaowc@gfire.cn)
 * @date 2018/7/12
 */
@Getter
@Setter
public class BaseForm<T,PK extends Serializable> {
    private PK id;

    public BaseForm<T,PK> setEntity(T entity){
        BeanUtils.copyProperties(entity,this);
        return this;
    }

    public T as(){
        Class<T> tClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        T entity = null;
        try {
            entity = tClass.newInstance();
            BeanUtils.copyProperties(this,entity);
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return entity;
    }
}
