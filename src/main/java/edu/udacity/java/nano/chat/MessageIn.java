package edu.udacity.java.nano.chat;

import javax.validation.constraints.NotBlank;

/**
 * WebSocket message model
 */
public class MessageIn {
    @NotBlank
    private String username;
    private String msg;

    public String getUsername() {
        return this.username;
    }
    public void setUsername(String name) {
        this.username = name;
    }

    public String getMsg() {
        return this.msg;
    }
    public void setMsg(String msg) { this.msg = msg; }

}
