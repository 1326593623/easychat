package com.magee.easychat.api;

import com.magee.easychat.modol.enums.LoginTypeEnum;
import com.magee.easychat.modol.po.LoginInfo;
import com.magee.easychat.modol.po.User;
import com.magee.easychat.modol.vo.AjaxResult;
import com.magee.easychat.modol.vo.ParticipantRepository;
import com.magee.easychat.service.UserService;
import com.magee.easychat.util.ConstUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;

/**
 * 处理登陆请求的Api
 *
 */
@Api
@Controller
public class LoginApi {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private UserService userService; //用户service类
    @Autowired
    private SimpMessagingTemplate messagingTemplate; //消息模板
    @Autowired
    private ParticipantRepository participantRepository; //用户仓库


    @ApiOperation("登录校验结果--判断是否可以登录")
    @PostMapping("/reply/login")
    @ResponseBody
    public AjaxResult replayLoginMessage(@RequestBody User user) {
        if (user.getName() == null || user.getName().trim().equals("")
                || user.getPassword() == null || user.getPassword().equals("")){
            return new AjaxResult(false, ConstUtils.USER_NAME_OR_PASSWORD_NULL);
        }
        boolean isExist = userService.isExistUser(user.getName());
        if (!isExist){
            return new AjaxResult(false, ConstUtils.USER_NAME_NOT_EXIST);
        }
        User res = userService.validateUserPassword(user.getName(), user.getPassword());
        if (res == null){
            return new AjaxResult(false, ConstUtils.USER_PASSWORD_WRONG);
        }
        return new AjaxResult(true);
    }

    /**
     * 反馈前端ajax注册的消息
     * @param user
     * @return
     */
    @ApiOperation("用户注册")
    @PostMapping(value = "/reply/regist")
    @ResponseBody
    public AjaxResult replyRegistMessage(@RequestBody User user){
        boolean isExist = userService.isExistUser(user.getName());
        if (isExist){
            return new AjaxResult(false, ConstUtils.USER_NAME_EXIST);
        }
        if (user.getPassword() != null){
            userService.insertUser(user.getName(), user.getPassword());
        }
        return new AjaxResult(true);
    }

    /**
     * 登录进入聊天室
     * @param user u
     * @param request q
     * @return
     */
    @ApiOperation("登录进入聊天室")
    @PostMapping("/chat")
    public String loginIntoChatRoom(User user, HttpServletRequest request) {
        user = userService.validateUserPassword(user.getName(), user.getPassword());
        if (user == null){
            return "login";
        }
        user.setLoginDate(new Date());
        user.setPassword(null);  //设空防止泄露给其他用户
        HttpSession session = request.getSession();
        session.setAttribute("user", user);
        //保存登录信息
        LoginInfo loginInfo = LoginInfo.builder().userId(user.getId()).userName(user.getName()).
                status(LoginTypeEnum.LOGIN.getCode()).createTime(new Date()).build();
        userService.addUserLoginInfo(loginInfo);

        messagingTemplate.convertAndSend(ConstUtils.SUBSCRIBE_LOGIN_URI, user);
        participantRepository.add(user.getName(), user);
        logger.info(user.getLoginDate() + ", " + user.getName() + " login.");

        return "chatroom";
    }

    /**
     * 登录页面
     * @return
     */
    @GetMapping(value = {"/", "/index", ""})
    public String index(){
        return "index";
    }

    /**
     * 返回当前在线人数
     * @return
     */
    @ApiOperation("使用websocket或SockJS：订阅获取在线人数")
    @SubscribeMapping("/chat/participants")
    public Long getActiveUserNumber(){
        return Long.valueOf(participantRepository.getActiveSessions().values().size());
    }

}
