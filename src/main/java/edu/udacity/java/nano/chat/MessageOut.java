package edu.udacity.java.nano.chat;

enum MessageType {
    SPEAK
}

public class MessageOut {
    private String username;
    private String msg;
    private MessageType type;
    private int onlineCount;

    public int getOnlineCount() {
        return onlineCount;
    }
    public void setOnlineCount(int onlineCount) {
        this.onlineCount = onlineCount;
    }

    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }

    public String getMsg() {
        return this.msg;
    }
    public void setMsg(String message) {
        this.msg = message;
    }

    public MessageType getType() {
        return type;
    }
    public void setType(MessageType type) {
        this.type = type;
    }

    public MessageOut(String username, String message, MessageType type, int count) {
        this.username = username;
        this.msg = message;
        this.type = type;
        this.onlineCount = count;
    }
}
