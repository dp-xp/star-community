package priv.alisa.community.mapper;

import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;
import priv.alisa.community.model.User;

@Mapper
@Component
public interface UserMapper {
    @Insert("insert into user (account_id,name,token,gmt_create,gmt_modified,avatar_url) values(#{accountId},#{name},#{token},#{gmtCreate},#{gmtModified},#{avatarUrl})")
    void insert(User user);

    @Select("select * from user where token = #{token}")
    User findByToken(String token);

    @Select("select * from user where id = #{id}")
    User findById(@Param("id") Integer id);

    @Select("select * from user where account_id = #{accountId}")
    User findByAccountId(@Param("accountId") String accountId);

    @Update("update user set name = #{name},gmt_modified = #{gmtModified},token = #{token},avatar_url = #{avatarUrl} where id = #{id}")
    void update(User user);
}
