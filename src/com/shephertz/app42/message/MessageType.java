package com.shephertz.app42.message;

import java.util.HashMap;
import java.util.Map;

public enum MessageType {
	Alert(0), Confirm(1),CenterPopup(2),Interstitial(3),OpenUrl(4),CustomPopup(5);
	private static final Map<Integer, MessageType> typeMap = new HashMap<Integer, MessageType>();
	  static {
	       
	        for (MessageType msgType : values()) {
	        	typeMap.put(msgType.getType(), msgType);
	        }
	    }
	private int type;
	private MessageType(final int type) { 
		this.type = type;
	}
	public int getType() {
		return type;
	}
  
    public static MessageType getByType(int value) {
        return typeMap.get(value);
    }

}
