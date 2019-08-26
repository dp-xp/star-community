package priv.alisa.community.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;
import priv.alisa.community.model.User;

@Mapper
@Component
public interface UserMapper {
    @Insert("insert into user (account_id,name,token,gmt_create,gmt_modified) values(#{accountId},#{name},#{token},#{gmtCreate},#{gmtModified})")
    void insert(User user);

    @Select("select * from user where token = #{token}")
    User findByToken(String token);
}
