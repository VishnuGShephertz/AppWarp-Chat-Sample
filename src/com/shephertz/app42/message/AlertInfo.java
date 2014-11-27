package com.shephertz.app42.message;

import org.json.JSONObject;
import com.shephertz.app42.message.Templates.Args;

public class AlertInfo {

	private String title;
	private String message;
	private String confirmBtntext;
	private String dismissBtnText;
	private String cancelBtnText;
	private String cancelBtnAction;
	private String confirmbtnAction;
	private String dismissBtnAction;
	
	
	public AlertInfo(JSONObject msgJson) {
		   if (msgJson == null) {
			      return;
			    }
		   this.title=msgJson.optString(Args.Title);
		   this.message=msgJson.optString(Args.Messagetext);
		   this.confirmBtntext=msgJson.optString(Args.AcceptButtonText);
		   this.confirmbtnAction=msgJson.optString(Args.AcceptButtonAction);
		   this.dismissBtnText=msgJson.optString(Args.DismissButtonText);
		   this.dismissBtnAction=msgJson.optString(Args.DismissButtonAction);
		   this.cancelBtnText=msgJson.optString(Args.CancelButtonText);
		   this.cancelBtnAction=msgJson.optString(Args.CancelButtonAction);
		   
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getConfirmBtntext() {
		return confirmBtntext;
	}
	public void setConfirmBtntext(String confirmBtntext) {
		this.confirmBtntext = confirmBtntext;
	}
	public String getDismissBtnText() {
		return dismissBtnText;
	}
	public void setDismissBtnText(String dismissBtnText) {
		this.dismissBtnText = dismissBtnText;
	}
	public String getCancelBtnText() {
		return cancelBtnText;
	}
	public void setCancelBtnText(String cancelBtnText) {
		this.cancelBtnText = cancelBtnText;
	}
	public String getCancelBtnAction() {
		return cancelBtnAction;
	}
	public void setCancelBtnAction(String cancelBtnAction) {
		this.cancelBtnAction = cancelBtnAction;
	}
	public String getConfirmbtnAction() {
		return confirmbtnAction;
	}
	public void setConfirmbtnAction(String confirmbtnAction) {
		this.confirmbtnAction = confirmbtnAction;
	}
	public String getDismissBtnAction() {
		return dismissBtnAction;
	}
	public void setDismissBtnAction(String dismissBtnAction) {
		this.dismissBtnAction = dismissBtnAction;
	}
	
	
}
