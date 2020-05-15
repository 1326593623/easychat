package com.magee.easychat.api;

import com.magee.easychat.modol.enums.MessageTypeEnum;
import com.magee.easychat.modol.po.MessageRecord;
import com.magee.easychat.modol.po.User;
import com.magee.easychat.modol.vo.Message;
import com.magee.easychat.service.UserService;
import com.magee.easychat.util.ConstUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.util.Date;

@Api
@Controller
public class MessageApi {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private SimpMessagingTemplate messagingTemplate;
    @Autowired
    private UserService userService;

    /**
     * 接收并且转发消息
     * @param message
     */
    @ApiOperation("接收消息并把消息发送到："+ConstUtils.SUBSCRIBE_MESSAGE_URI)
    @MessageMapping("/chat/message")
    public void receiveMessage(Message message){
        message.setSendDate(new Date());
        message.setMessageType("text");
        logger.info(message.getSendDate() + "," + message.getUserName() + " send a message:" + message.getContent());

        //保存聊天信息
        User userByName = userService.getUserByName(message.getUserName());
        MessageRecord messageRecordDo = MessageRecord.messageRecordBuilder()
                .userId(userByName == null ? null : userByName.getId())
                .userName(message.getUserName()).content(message.getContent())
                .messageType(MessageTypeEnum.TEXT.getCode()).createTime(new Date()).build();
        userService.addUserMessageRecord(messageRecordDo);

        //消息转发
        messagingTemplate.convertAndSend(ConstUtils.SUBSCRIBE_MESSAGE_URI, message);
    }

    /**
     * 接收转发图片
     * @param request
     * @param imageFile
     * @param userName
     * @return
     */
    @ApiOperation("接收图片类型数据")
    @PostMapping(value = "/upload/image")
    @ResponseBody
    public String handleUploadImage(HttpServletRequest request, @RequestParam("image") MultipartFile imageFile,
                                    @RequestParam("userName")String userName){
        if (!imageFile.isEmpty()){
            String imageName = userName + "_" + (int)(Math.random() * 1000000) + ".jpg"; //用户名+随机数拼接
            String path = ConstUtils.LOCAL_PATH + imageName;
            logger.info("****************"+ path + "****************");
            File localImageFile = new File(path);

/*            ClassPathResource resource = new ClassPathResource("/META-INF/resources/media/image/" + imageName);
            logger.info("***********resourcePath:"+ resource.getPath() + "****************");*/
            try {

                //上传图片到目录
                byte[] bytes = imageFile.getBytes();
                BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(localImageFile));
                bufferedOutputStream.write(bytes);
                bufferedOutputStream.close();


                /*
                Path streamPath = Paths.get(resource.getURI());
                logger.info("***********resourceUri:"+ resource.getURI() + "****************");
                System.out.println(streamPath.toString());
                imageFile.transferTo(streamPath);
                */

                Message message = new Message();
                message.setMessageType("image");
                message.setUserName(userName);
                message.setSendDate(new Date());
                message.setContent("/Path/"+ imageName);

                //保存发送图片信息
                User userByName = userService.getUserByName(message.getUserName());
                MessageRecord messageRecordDo = MessageRecord.messageRecordBuilder()
                        .userId(userByName == null ? null : userByName.getId())
                        .userName(userName).content(message.getContent())
                        .messageType(MessageTypeEnum.IMAGE.getCode()).createTime(new Date()).build();
                userService.addUserMessageRecord(messageRecordDo);

                messagingTemplate.convertAndSend(ConstUtils.SUBSCRIBE_MESSAGE_URI, message);
            } catch (IOException e) {
                logger.error("图片上传失败：" + e.getMessage(), e);
                return "upload false";
            }
        }
        return "upload success";
    }
}
