package com.magee.easychat.modol.po;

import lombok.Data;

import java.util.Date;

@Data
public class User {
    private int id;
    private String name;
    private String password;

    private Date loginDate;
    private Date logoutDate;
}
