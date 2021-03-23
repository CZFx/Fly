package com.st.fly.dao;

import com.st.fly.entity.Answer;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Mapper
@Repository
public interface AnswerDao {

    @Insert("insert into answer (content, qid, uid) " +
            "values(#{content}, #{qid}, #{uid})")
    int insert(Answer answer);

    @Select("select * from answer where qid=#{id}")
    @Results ({
            @Result(column = "uid", property = "user",
                    one = @One(select = "com.st.fly.dao.UserDao.selectById"))
            /*@Result(column = "qid", property = "question",
                    one = @One(select = "com.st.fly.dao.QuestionDao.selectById"))*/
    })
    List<Answer> selectByQid(Integer id);

    /**
     * 根据用户统计数量（回复的数量）
     */
    @Select("select `user`.id, `user`.nickname, `user`.head, a.count\n" +
            "from `user`\n" +
            "LEFT JOIN (\n" +
            "SELECT *, count(*) as count from answer where createtime > #{date}\n" +
            "group by uid\n" +
            ") as a on `user`.id = a.uid\n" +
            "order by a.count desc;\n")
    List<Map<String, Object>> selectCountByUser(Date date);

    @Select("select * from answer where uid=#{uid}")
    List<Answer> selectByUid(Integer uid);
}
