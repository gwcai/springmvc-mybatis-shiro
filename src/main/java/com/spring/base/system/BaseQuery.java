package com.spring.base.system;

import lombok.Data;

/**
 * @author GaoWeicai.(gaowc@gfire.cn)
 * @date 2018/7/17
 */
@Data
public class BaseQuery {
    Integer pageSize = 20;
    Integer pageIndex = 0;
}
