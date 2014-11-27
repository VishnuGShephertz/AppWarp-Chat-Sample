package com.shephertz.app42.message;

import java.util.Random;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;


public class Templates {
	static class Args {
		
		  static final String MessageType = "type";
		//for confirm and AlertDialog
	    static final String Title = "title";
	    static final String Message = "message";
	    static final String AcceptButtonText = "accept.text";
	    static final String CancelButtonText = "cancel.text";
	    static final String DismissButtonText = "dismiss.text";
	    static final String AcceptButtonAction = "accept.action";
	    static final String CancelButtonAction = "cancel.action";
	    static final String DismissButtonAction = "dismiss.action";
	    
	    //For center Popup/Graphical popup and Interistial popup
	    static final String TitleText = "title.text";
	    static final String TitleColor = "title.color";
	    static final String Messagetext = "meesage.text";
	    static final String MessageColor = "message.color";
	   
	    static final String AcceptButtonBackgroundColor = "acceptButton.backgroundcolor";
	    static final String AcceptButtonTextColor = "acceptButton.textcolor";
	    static final String BackgroundColor = "background.color";
	    static final String BackgroundImage = "background.image";
	    static final String LayourWidth = "layout.width";
	    static final String LayoutHeight = "layout.height";
	  }

	  static class Values {
	    static final String AlertMessage = "Alert message goes here.";
	    static final String ConfirmMessage = "Confirmation message goes here.";
	    static final String PopupMessage = "Popup message goes here.";
	    static final String InterstitialMessage = "Interstitial message goes here.";
	    static final String OkText = "OK";
	    static final String YesText = "Yes";
	    static final String NoText = "No";
	    static final int CenterPopupWidth = 300;
	    static final int CenterPopupHeight = 250;
	  }
	  private static  AlertInfo generateAlertObject(){
		  AlertInfo alert=null;
			try {
				JSONObject json=new JSONObject();
				json.put(Args.Title, "Sample Alert");
				json.put(Args.DismissButtonText, "Dismiss");
				json.put(Args.DismissButtonAction, "Alert dialog is dismissed");
				json.put(Args.Messagetext, "Welcome to alert dialog");
				alert=new AlertInfo(json);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		return alert;
			
		}
	  
	  private static  AlertInfo generateConfirmObject(){
		  AlertInfo alert=null;
			try {
				JSONObject json=new JSONObject();
				json.put(Args.Title, "Sample Confirmation");
				json.put(Args.Messagetext, "Welcome to confirmation dialog");
				json.put(Args.AcceptButtonText, "Confirm");
				json.put(Args.AcceptButtonAction, "Confirmation done");
				json.put(Args.CancelButtonText, "Reject");
				json.put(Args.CancelButtonAction, "Offer is rejected by User");
				alert=new AlertInfo(json);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		return alert;
		}
	  
	  
	  public static void showAlertDialog(Context context){
		  int val=new Random().nextInt(2);
		
		  if(val==0){
			  AlertInfo  alert=generateAlertObject();
		  App42DialogBuilder.showAlertDialog(context, alert);
		  }
		  else{
			  AlertInfo  alert=generateConfirmObject();
			  App42DialogBuilder.showConfirmDialog(context, alert);
		  }
		 
	  }


}
