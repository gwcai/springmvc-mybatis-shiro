package com.spring.base.system;

import lombok.Data;

import java.util.List;

/**
 * @author lili14520@gmail.com
 * @date 2018/7/17
 */
@Data
public class Page<T> {
    Integer pageSize;
    Integer pageIndex;
    Long total;

    List<T> rows;
}
