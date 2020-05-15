package com.magee.easychat.modol.po;

import lombok.Data;

import java.util.Date;

@Data
public class LoginInfo {
    private Integer id;
    private Integer userId;
    private String userName;
    private Integer status;
    private Date createTime;

    public LoginInfo(Integer id, Integer userId, String userName, Integer status, Date createTime) {
        this.id = id;
        this.userId = userId;
        this.userName = userName;
        this.status = status;
        this.createTime = createTime;
    }
    public static LoginInfoBuilder builder(){
        return new LoginInfoBuilder();
    }
    public static class LoginInfoBuilder{
        private Integer id;
        private Integer userId;
        private String userName;
        private Integer status;
        private Date createTime;

        public LoginInfoBuilder id(Integer id) {
            this.id = id;
            return this;
        }

        public LoginInfoBuilder userId(Integer userId) {
            this.userId = userId;
            return this;
        }

        public LoginInfoBuilder userName(String userName) {
            this.userName = userName;
            return this;
        }

        public LoginInfoBuilder status(Integer status) {
            this.status = status;
            return this;
        }

        public LoginInfoBuilder createTime(Date createTime) {
            this.createTime = createTime;
            return this;
        }

        public LoginInfo build(){
            return new LoginInfo(id, userId, userName, status, createTime);
        }
    }

}
