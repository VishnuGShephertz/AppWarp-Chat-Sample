package com.shephertz.app42.message;

import org.json.JSONObject;

import com.shephertz.app42.message.Templates.Args;

public class CenterPopupdata extends MessageData{

	private int width;
	public int getWidth() {
		return width;
	}
	public void setWidth(int width) {
		this.width = width;
	}
	public int getHeight() {
		return height;
	}
	public void setHeight(int height) {
		this.height = height;
	}
	private int height;
	public CenterPopupdata(JSONObject msgJson) {
		super(msgJson);
		// TODO Auto-generated constructor stub
		this.width=msgJson.optInt(Args.LayourWidth);
		this.height=msgJson.optInt(Args.LayoutHeight);
	}

}
