package com.magee.easychat.mapper;


import com.magee.easychat.modol.po.LoginInfo;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;

@Mapper
public interface LoginInfoMapper {

    @Options(useGeneratedKeys = true, keyColumn = "id", keyProperty = "id")
    @Insert("INSERT INTO login_info(user_id, user_name, status, create_time)\n" +
            "        VALUES (#{userId}, #{userName}, #{status}, #{createTime})")
    public Integer addLoginInfo(LoginInfo loginInfo);
}

