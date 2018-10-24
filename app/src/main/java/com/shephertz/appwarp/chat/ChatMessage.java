package com.shephertz.appwarp.chat;

public class ChatMessage {
    public final static String MSG_TYPE_SENT = "MSG_TYPE_SENT";

    public final static String MSG_TYPE_RECEIVED = "MSG_TYPE_RECEIVED";

    // Message content.
    private String msgContent;

    // Message type.
    private String msgType;

    public String getSenderName() {
        return senderName;
    }

    private String senderName;

    public ChatMessage(String msgType, String msgContent,String sender) {
        this.msgType = msgType;
        this.msgContent = msgContent;
        this.senderName=sender;
    }

    public String getMsgContent() {
        return msgContent;
    }

    public void setMsgContent(String msgContent) {
        this.msgContent = msgContent;
    }

    public String getMsgType() {
        return msgType;
    }

    public void setMsgType(String msgType) {
        this.msgType = msgType;
    }
}

