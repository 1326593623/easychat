package com.magee.easychat.service.impl;


import com.magee.easychat.mapper.LoginInfoMapper;
import com.magee.easychat.mapper.MessageRecordMapper;
import com.magee.easychat.mapper.UserMapper;
import com.magee.easychat.modol.po.LoginInfo;
import com.magee.easychat.modol.po.MessageRecord;
import com.magee.easychat.modol.po.User;
import com.magee.easychat.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 用户service实现类
 */
@Service
public class UserServiceImpl implements UserService {
    @SuppressWarnings("all")
    @Autowired
    private UserMapper userMapper;
    @SuppressWarnings("all")
    @Autowired
    private LoginInfoMapper loginInfoMapper;
    @SuppressWarnings("all")
    @Autowired
    private MessageRecordMapper messageRecordMapper;

    @Override
    public User validateUserPassword(String name, String password) {
        User user = new User();
        user.setName(name);
        user.setPassword(password);
        return userMapper.getUserByNameAndPwd(user);
    }

    @Override
    public boolean isExistUser(String name) {
        User user = userMapper.getUserByname(name);
        return user != null;
    }

    @Override
    public Integer insertUser(String name, String password) {
        User user = new User();
        user.setName(name);
        user.setPassword(password);
        return userMapper.insUser(user);
    }

    @Override
    public Integer addUserLoginInfo(LoginInfo loginInfo) {
        return loginInfoMapper.addLoginInfo(loginInfo);
    }

    @Override
    public Integer addUserMessageRecord(MessageRecord messageRecord) {
        return messageRecordMapper.addMessageRecord(messageRecord);
    }

    @Override
    public User getUserByName(String name) {
        return userMapper.getUserByname(name);
    }
}
