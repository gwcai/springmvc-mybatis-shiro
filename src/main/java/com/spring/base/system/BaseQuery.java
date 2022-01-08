package com.spring.base.system;

import lombok.Data;

/**
 * @author lili14520@gmail.com
 * @date 2018/7/17
 */
@Data
public class BaseQuery {
    Integer pageSize = 20;
    Integer pageIndex = 0;
}
