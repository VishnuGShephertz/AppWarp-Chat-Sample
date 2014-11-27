package com.shephertz.app42.message;

import org.json.JSONObject;

import com.shephertz.app42.message.Templates.Args;

public class MessageData {
	private MessageType msgType;
	private String title;
	private int titleColor;
	private String messageText;
	private int messageColor;
//	private Bitmap backgroundImage;
	private int backgroundColor;
	private String acceptBtnText;
	private int acceptBtnBackColor;
	private int acceptBtnTextColor;
	private String acceptAction;

	public MessageData(JSONObject msgJson) {
		   if (msgJson == null) {
			      return;
			    }
		   this.msgType=MessageType.getByType(msgJson.optInt(Args.MessageType));
		   this.title=msgJson.optString(Args.TitleText);
		   this.titleColor=msgJson.optInt(Args.TitleColor);
		   this.messageText=msgJson.optString(Args.Messagetext);
		   this.messageColor=msgJson.optInt(Args.MessageColor);
		   this.backgroundColor=msgJson.optInt(Args.BackgroundColor);
		   this.acceptBtnText=msgJson.optString(Args.AcceptButtonText);
		   this.acceptBtnBackColor=msgJson.optInt(Args.AcceptButtonBackgroundColor);
		   this.acceptBtnTextColor=msgJson.optInt(Args.AcceptButtonTextColor);
		   this.acceptAction=msgJson.optString(Args.AcceptButtonAction);
		   
	}

	public MessageType getMsgType() {
		return msgType;
	}

	public void setMsgType(MessageType msgType) {
		this.msgType = msgType;
	}
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getTitleColor() {
		return titleColor;
	}

	public void setTitleColor(int titleColor) {
		this.titleColor = titleColor;
	}

	public String getMessageText() {
		return messageText;
	}

	public void setMessageText(String messageText) {
		this.messageText = messageText;
	}

	public int getMessageColor() {
		return messageColor;
	}

	public void setMessageColor(int messageColor) {
		this.messageColor = messageColor;
	}

//	public Bitmap getBackgroundImage() {
//		return backgroundImage;
//	}
//
//	public void setBackgroundImage(Bitmap backgroundImage) {
//		this.backgroundImage = backgroundImage;
//	}

	public int getBackgroundColor() {
		return backgroundColor;
	}

	public void setBackgroundColor(int backgroundColor) {
		this.backgroundColor = backgroundColor;
	}

	public String getAcceptBtnText() {
		return acceptBtnText;
	}

	public void setAcceptBtnText(String acceptBtnText) {
		this.acceptBtnText = acceptBtnText;
	}

	public int getAcceptBtnBackColor() {
		return acceptBtnBackColor;
	}

	public void setAcceptBtnBackColor(int acceptBtnBackColor) {
		this.acceptBtnBackColor = acceptBtnBackColor;
	}

	public int getAcceptBtnTextColor() {
		return acceptBtnTextColor;
	}

	public void setAcceptBtnTextColor(int acceptBtnTextColor) {
		this.acceptBtnTextColor = acceptBtnTextColor;
	}

	public String getAcceptAction() {
		return acceptAction;
	}

	public void setAcceptAction(String acceptAction) {
		this.acceptAction = acceptAction;
	}
}
