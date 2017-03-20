package com.py.firebasechat;

import java.util.Date;

/**
 * Created on 20.03.2017
 *
 * @author Puzino Yury
 */

public class Message {

    private String messageText;
    private String messageAuthor;
    private long messageTime;

    public Message(String messageText, String messageAuthor) {
        this.messageText = messageText;
        this.messageAuthor = messageAuthor;

        messageTime = new Date().getTime();
    }

    public Message(){

    }

    public String getMessageText() {
        return messageText;
    }

    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }

    public String getMessageAuthor() {
        return messageAuthor;
    }

    public void setMessageAuthor(String messageAuthor) {
        this.messageAuthor = messageAuthor;
    }

    public long getMessageTime() {
        return messageTime;
    }

    public void setMessageTime(long messageTime) {
        this.messageTime = messageTime;
    }
}
