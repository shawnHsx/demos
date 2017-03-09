package com.semion.web.action;

import java.io.Serializable;

/**
 * Created by heshuanxu on 2017/2/16.
 */
public class LoginUser implements Serializable {


    private static final long serialVersionUID = -6409115308367331221L;
    private String userId;
    private String nick;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }
}
