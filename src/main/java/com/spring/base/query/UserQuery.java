package com.spring.base.query;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.spring.base.system.BaseQuery;
import lombok.Getter;
import lombok.Setter;

/**
 * @author GaoWeicai.(gaowc@gfire.cn)
 * @date 2018/7/17
 */
@Getter
@Setter

@JsonIgnoreProperties(ignoreUnknown = true)
public class UserQuery extends BaseQuery {
    private String name;
    private String sex;
}
