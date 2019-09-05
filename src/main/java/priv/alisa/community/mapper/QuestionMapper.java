package priv.alisa.community.mapper;

import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Controller;
import priv.alisa.community.dto.QuestionDTO;
import priv.alisa.community.model.Question;

import java.util.List;

@Controller
@Mapper
public interface QuestionMapper {
    @Insert("insert into question(title,description,gmt_create,gmt_modified,creator,tag) values(#{title},#{description},#{gmtCreate},#{gmtModified},#{creator},#{tag})")
    void create(Question question);

    @Select("select * from question limit #{offset},#{size}")
    List<Question> queryAllQuestion(@Param(value = "offset") Integer offset, @Param(value = "size") Integer size);

    @Select("select count(1) from question")
    Integer count();

    @Select("select * from question where creator = #{userId} limit #{offset},#{size}")
    List<Question> queryAllQuestionByUserId(@Param(value = "userId") Integer userId, @Param(value = "offset")Integer offset, @Param(value = "size") Integer size);

    @Select("select count(1) from question where creator = #{userId}")
    Integer countByUserId(@Param(value = "userId") Integer userId);

    @Select("select * from question where id=#{id}")
    Question queryById(@Param(value = "id") Integer id);

    @Update("update question set title=#{title},description=#{description},gmt_modified=#{gmtModified},tag=#{tag},view_count=#{viewCount}+1 where id=#{id}")
    int update(Question question);
}
