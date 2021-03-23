package com.st.fly.dao;


import com.st.fly.entity.Question;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface QuestionDao {

    @Insert("insert into question (title, content, type, kiss, uid) "
            + "values (#{title}, #{content}, #{type}, #{kiss}, #{uid})")
    @Options(useGeneratedKeys = true,   //使用数据库自动生成的主键
            keyProperty = "id")     //将自动生成的主键保存到question对象的id属性上
    int insert(Question question);

    @Select("select * from question where id = #{id}")
    @Results({
            @Result(column = "uid", property = "user",
                    one = @One(select = "com.st.fly.dao.UserDao.selectById"))
    })
    Question selectById(Integer id);

    @Select("select * from question where level = 1 or level = 3 order by createtime desc")
    @Results({      //查询时可以根据uid查询user表，返回具体的user，并将uid映射为question对象中的user属性（映射后uid=null）
            @Result(column = "uid", property = "user",
                    one = @One(select = "com.st.fly.dao.UserDao.selectById"))    //这是将question和user解析成了一个一对一的关系（一个帖子只能是一个用户发表的）
    })
    List<Question> selectTop();

    @Select("<script>" +
            "   select * from question " +
            "<where>" +
            "   <if test='question.status!=null'>status=#{question.status}</if>" +
            "   <if test='question.level!=null'>and level=#{question.level}</if>" +
            "</where>" +
            "   order by ${order} desc" +
            "</script>")
    @Results({      //查询时可以根据uid查询user表，返回具体的user，并将uid映射为question对象中的user属性（映射后uid=null）
            @Result(column = "uid", property = "user",
                    one = @One(select = "com.st.fly.dao.UserDao.selectById"))    //这是将question和user解析成了一个一对一的关系（一个帖子只能是一个用户发表的）
    })
    List<Question> select(Question question, String order);

    @Select("select * from question where uid = #{uid}")
    List<Question> selectByUid(Integer uid);

    @Update("update question set viewsnum=#{viewsnum} where id=#{id}")
    void updateByViewsnum(Integer id, Integer viewsnum);

    @Update("update question set replynum=#{replynum} where id=#{id}")
    void updateByReplynum(Integer id, Integer replynum);
}
