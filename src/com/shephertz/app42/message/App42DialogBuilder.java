package com.shephertz.app42.message;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.widget.Toast;
public class App42DialogBuilder {

	public static void showAlertDialog(final Context currentContext,final AlertInfo alertInfo) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
        		currentContext);
        alertDialogBuilder
            .setTitle(alertInfo.getTitle())
            .setMessage(alertInfo.getMessage())
            .setCancelable(false)
            .setPositiveButton(alertInfo.getDismissBtnText(),
                new DialogInterface.OnClickListener() {
                  public void onClick(DialogInterface dialog, int id) {
                	  showToast(currentContext,alertInfo.getDismissBtnAction());
                  }
                });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
	}
	
	private static void showToast(Context currentContext,String message){
		Toast.makeText(currentContext, message, Toast.LENGTH_SHORT).show();
	}
	public static void showConfirmDialog(final Context currentContext,final AlertInfo msgInfo) {
	    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
        		currentContext);
        alertDialogBuilder
            .setTitle(msgInfo.getTitle())
            .setMessage(msgInfo.getMessage())
            .setCancelable(false)
           .setPositiveButton(msgInfo.getConfirmBtntext(),
                    new DialogInterface.OnClickListener() {
                      public void onClick(DialogInterface dialog, int id) {
                    	  showToast(currentContext,msgInfo.getConfirmbtnAction());
                      }
                    })
                .setNegativeButton(msgInfo.getCancelBtnText(),
                    new DialogInterface.OnClickListener() {
                      public void onClick(DialogInterface dialog, int id) {
                    	  showToast(currentContext,msgInfo.getCancelBtnAction());
                      }
                    });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();

	}
}
