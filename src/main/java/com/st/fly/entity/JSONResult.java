package com.st.fly.entity;

import lombok.Getter;
import lombok.Setter;

/**
 * 向客户端返回数据的结果
 */
@Getter
@Setter
public class JSONResult {

    private Integer status = 0;
    private String msg;
    private Object data;

}
