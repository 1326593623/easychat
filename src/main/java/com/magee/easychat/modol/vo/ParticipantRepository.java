package com.magee.easychat.modol.vo;

import com.magee.easychat.modol.po.User;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 *在线用户仓库，存储在线用户
 */
@Data
@Component
public class ParticipantRepository {
    //在线用户map，键：用户名称，值：用户对象
    private Map<String, User> activeSessions = new ConcurrentHashMap<String, User>();

    public void add(String name, User user){
        activeSessions.put(name, user);

    }
    public User remove(String name){
        return activeSessions.remove(name);
    }
    public boolean containsUserName(String name){
        return activeSessions.containsKey(name);
    }

}
