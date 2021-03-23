package com.st.fly.dao;

import com.st.fly.entity.User;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

/**
 * 访问数据库的接口，访问User接口
 *  1. 给接口加上Mapper注解
 *      当SpringBoot项目启动时，Spring的容器会扫描所有的类和接口，
 *      当发现有@Mapper注解时，会将其交给MyBatis处理，MyBatis会
 *      扫描这个接口的方法以及方法上的注解，根据注解生成方法执行的过程，
 *      再利用动态代理的方式，创建该接口的代理对象，然后将这个对象放入
 *      Spring容器中。
 *
 *  2. 定义需要的方法
 *  3. 配置方法需要执行的SQL语句
 */
@Mapper
@Repository
public interface UserDao {

    @Select("select * from user where username=#{username} and password=#{password}")
    User select(String username, String password);

    @Select("select * from user where username=#{username}")
    User selectByUsername(String username);

    @Select("select * from user where nickname=#{nickname}")
    User selectByNickname(String nickname);

    @Insert("insert into user (username, password, nickname) values(#{username}, #{password}, #{nickname})")
    int insert(User user);


    @Select("select * from user where id=#{uid}")
    User selectById(Integer uid);

    /**
     * 修改用户信息（头像）
     */
    @Update("update user set head=#{head} where id=#{id}")
    int update(User user);
}
