package com.magee.easychat.service;

import com.magee.easychat.modol.po.LoginInfo;
import com.magee.easychat.modol.po.MessageRecord;
import com.magee.easychat.modol.po.User;

public interface UserService {

    /**
     * 验证用户密码
     * @param name
     * @param password
     * @return 正确返回该用户对象，否则返回空
     */
    public User validateUserPassword(String name, String password);

    /**
     * 该用户是否已经注册
     * @param name
     * @return
     */
    public boolean isExistUser(String name);

    /**
     * 插入一名用户
     * @param name
     * @param password
     */
    public Integer insertUser(String name, String password);

    public Integer addUserLoginInfo(LoginInfo loginInfo);

    public Integer addUserMessageRecord(MessageRecord messageRecord);

    public User getUserByName(String name);
}
