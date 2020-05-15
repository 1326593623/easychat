package com.magee.easychat.util;

import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;

/**
 * 常量类
 */
public class ConstUtils {
    /**
     * 文件上传
     */
    public static final String LOCAL_PATH = "/home/image/";

    /**
     * ajax错误状态码-------------登录错误状态码
     */
    public static final Integer USER_NAME_NOT_EXIST = 1; //当前登录的用户名尚未注册
    public static final Integer USER_PASSWORD_WRONG = 2; //登录密码错误
    public static final Integer USER_NAME_OR_PASSWORD_NULL = 3; //用户姓名或密码未填写
    /**
     * ajax错误状态码-------------注册错误状态码
     */
    public static final Integer USER_NAME_EXIST = 1; //注册名已经存在

    /**
     * 访问地址
     */
    public static final String SUBSCRIBE_LOGIN_URI = "/topic/login";   //订阅的登录地址
    public static final String SUBSCRIBE_LOGOUT_URI = "/topic/logout"; //退出登录的地址
    public static final String SUBSCRIBE_MESSAGE_URI = "/topic/chat/message"; //订阅接收消息地址


    /**
     * 获取项目根路径
     *
     * @return
     */
    private static String getResourceBasePath() {
        // 获取跟目录
        File path = null;
        try {
            path = new File(ResourceUtils.getURL("classpath:").getPath());
        } catch (FileNotFoundException e) {
            // nothing to do
        }
        if (path == null || !path.exists()) {
            path = new File("");
        }
        String pathStr = path.getAbsolutePath();
        // 如果是在eclipse中运行，则和target同级目录,如果是jar部署到服务器，则默认和jar包同级
        pathStr = pathStr.replace("\\target\\classes", "");

        return pathStr;
    }

}
