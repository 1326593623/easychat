package com.magee.easychat.mapper;

import com.magee.easychat.modol.po.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface UserMapper {

    @Select("SELECT id, name, password FROM user\n" +
            "        WHERE name = #{name} AND password = #{password}")
    public User getUserByNameAndPwd(User user);

    @Select("SELECT id, name, password FROM user\n" +
            "        WHERE name = #{name}")
    public List<User> queryUserByname(String name);

    @Select("SELECT * FROM user\n" +
            "        where name = #{name}\n" +
            "        limit 1")
    public User getUserByname(String name);


    @Insert("INSERT INTO user(name, password) VALUES(#{name}, #{password})")
    public Integer insUser(User user);

}
