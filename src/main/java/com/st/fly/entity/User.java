package com.st.fly.entity;

import java.util.Date;
import lombok.Getter;
import lombok.Setter;


/**
 * 这是一个实体类
 * 与数据库中的实体(数据表)相对应的类
 *
 * 编写实体的四个步骤:
 * 1. 定义一个类, 类名与数据表匹配, 名字要一样, 采用大驼峰命名法
 * 2. 实体类的属性, 要与数据表的字段相匹配
 * 		名字要一样(包括 大小写)
 * 		数据类型要匹配(数据库中的数据类型, 与Java中的数据类型不一样)
 * 			字符串使用String
 * 			基本类型, 使用包装类
 * 3. 所有的属性私有化(使用private 修饰), 为每一属性添加公共的get和set方法
 * 		在类中, 右键-> source -> generate Getters and Setters...
 * 			在弹出窗口中, 点击右上解的Select All, 再点击右下的Generate
 * 			所有属性的get 和 set方法就会自动生成.
 *
 * 4. 实体类, 应该有一个无参的构造方法.
 *
 * 数据库中的其它的表, 也应该有相应的实体类.
 *
 */
@Getter
@Setter
public class User {
    private Integer id;
    private String username;
    private String password;
    private String nickname;
    private String sex;
    private String city;
    private String phone;
    private String email;
    private String head;
    private String signature;
    private Integer kiss;
    private Integer vip;
    private Integer role;
    private Date regtime;
    private String cert;
    private Integer status;

    // 创建两个可以将uid映射为相应的值
    private Question question;
    private Answer answer;
}
