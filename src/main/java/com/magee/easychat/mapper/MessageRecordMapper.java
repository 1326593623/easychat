package com.magee.easychat.mapper;

import com.magee.easychat.modol.po.MessageRecord;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;

@Mapper
public interface MessageRecordMapper {


    @Options(useGeneratedKeys = true, keyColumn = "id", keyProperty = "id")
    @Insert("INSERT INTO message_record(user_id, user_name, message_type, content, create_time)\n" +
            "        VALUES (#{userId}, #{userName}, #{messageType}, #{content}, #{createTime})")
    public Integer addMessageRecord(MessageRecord messageRecord);
}
