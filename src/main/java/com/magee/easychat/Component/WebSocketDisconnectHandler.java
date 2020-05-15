package com.magee.easychat.Component;

import com.magee.easychat.modol.enums.LoginTypeEnum;
import com.magee.easychat.modol.po.LoginInfo;
import com.magee.easychat.modol.po.User;
import com.magee.easychat.modol.vo.ParticipantRepository;
import com.magee.easychat.service.UserService;
import com.magee.easychat.util.ConstUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import java.util.Date;
import java.util.Map;

/**
 * 从Spring4.0.3开始，
 * Spring新增了
 * SessionConnectedEvent、SessionConnectEvent、SessionDisconnectEvent，
 * 这三个事件，用于为Spring新增的WebSocket功能服务；
 * 监听SessionDisconnectEvent事件-----为session断开--用户下线做准备
 */
public class WebSocketDisconnectHandler implements ApplicationListener<SessionDisconnectEvent> {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private SimpMessagingTemplate messagingTemplate; //消息模板
    @Autowired
    private ParticipantRepository participantRepository; //用户仓库
    @Autowired
    private UserService userService; //用户业务



    @Override
    public void onApplicationEvent(SessionDisconnectEvent sessionDisconnectEvent) {
        //维护用户仓库
        Map<String, User> activeSessions = participantRepository.getActiveSessions();

        //通过STOMP首部存取器获取session中的user对象
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(sessionDisconnectEvent.getMessage());
        Map<String, Object> sessionAttributes = headerAccessor.getSessionAttributes();
        User user = (User) sessionAttributes.get("User");

        //获取用户名字
        String disconnectUserName = user.getName();

        //从容器中移除该用户----并保存注销信息
        if (participantRepository.containsUserName(disconnectUserName)) {
            User removeUser = participantRepository.remove(disconnectUserName);
            removeUser.setLogoutDate(new Date());
            //保存登出信息
            User saveUser = userService.getUserByName(removeUser.getName());
            LoginInfo loginInfo = LoginInfo.builder().userId(saveUser == null ? null : saveUser.getId())
                    .userName(removeUser.getName())
                    .status(LoginTypeEnum.LOGOUT.getCode())
                    .createTime(new Date())
                    .build();
            userService.addUserLoginInfo(loginInfo);
            logger.info(removeUser.getLogoutDate() + ", " + removeUser.getName() + " logout.");
            messagingTemplate.convertAndSend(ConstUtils.SUBSCRIBE_LOGOUT_URI, removeUser);
        }

    }
}
