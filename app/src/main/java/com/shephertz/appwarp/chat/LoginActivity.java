package com.shephertz.appwarp.chat;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.shephertz.app42.gaming.multiplayer.client.WarpClient;
import com.shephertz.app42.gaming.multiplayer.client.command.WarpResponseResultCode;
import com.shephertz.app42.gaming.multiplayer.client.events.AllRoomsEvent;
import com.shephertz.app42.gaming.multiplayer.client.events.AllUsersEvent;
import com.shephertz.app42.gaming.multiplayer.client.events.ConnectEvent;
import com.shephertz.app42.gaming.multiplayer.client.events.LiveRoomInfoEvent;
import com.shephertz.app42.gaming.multiplayer.client.events.LiveUserInfoEvent;
import com.shephertz.app42.gaming.multiplayer.client.events.MatchedRoomsEvent;
import com.shephertz.app42.gaming.multiplayer.client.events.RoomEvent;
import com.shephertz.app42.gaming.multiplayer.client.listener.ConnectionRequestListener;
import com.shephertz.app42.gaming.multiplayer.client.listener.RoomRequestListener;
import com.shephertz.app42.gaming.multiplayer.client.listener.ZoneRequestListener;

public class LoginActivity extends AppCompatActivity implements ConnectionRequestListener, RoomRequestListener, ZoneRequestListener {
    private EditText nameEditText;
    private WarpClient theClient;
    private ProgressDialog progressDialog;
    private Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        nameEditText = (EditText) findViewById(R.id.username);
        // progressDialog=new ProgressDialog(this);
        init();
    }

    @Override
    protected void onStart() {
        super.onStart();
        theClient.addConnectionRequestListener(this);
        theClient.addRoomRequestListener(this);
        theClient.addZoneRequestListener(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        theClient.removeConnectionRequestListener(this);
        theClient.removeRoomRequestListener(this);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (theClient != null) {
            theClient.disconnect();
        }
    }


    private void showProgressDialog(String messsage) {
//        progressDialog.setMessage(messsage);
//        progressDialog.show();
//        progressDialog.setCancelable(true);

        progressDialog = ProgressDialog.show(this, "", messsage);
        progressDialog.setCancelable(true);
    }

    public void onConnectClicked(View view) {
        String userName = nameEditText.getText().toString();
        if (userName.length() > 0) {
            Utils.myUserName = userName;
            showProgressDialog("Connecting...");

            theClient.connectWithUserName(userName);
        } else {
            Utils.showToast(this, "Please enter name");
        }
    }

    private void init() {
        WarpClient.initialize(Constants.apiKey, Constants.secretKey);
        WarpClient.setRecoveryAllowance(30);
        try {
            theClient = WarpClient.getInstance();
            WarpClient.enableTrace(true);
        } catch (Exception ex) {
            Toast.makeText(this, "Exception in Initilization", Toast.LENGTH_LONG).show();
        }

    }

    @Override
    public void onConnectDone(final ConnectEvent event) {
        if (progressDialog != null) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    progressDialog.dismiss();

                }
            });
        }
        if (event.getResult() == WarpResponseResultCode.SUCCESS) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    showProgressDialog("Joining Room...");
                    theClient.joinRoomInRange(1, Constants.MaxUser, false);
                }
            });

        } else if (event.getResult() == WarpResponseResultCode.SUCCESS_RECOVERED) {
            showToastOnUIThread("Connection recovered");
        } else if (event.getResult() == WarpResponseResultCode.CONNECTION_ERROR_RECOVERABLE) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    progressDialog = ProgressDialog.show(LoginActivity.this, "", "Recoverable connection error. Recovering session after 5 seconds");
                }
            });
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    progressDialog.setMessage("Recovering...");
                    theClient.RecoverConnection();
                }
            }, 5000);
        } else if (event.getResult() == WarpResponseResultCode.AUTH_ERROR) {
            showToastOnUIThread("AUTH Error");
        } else if (event.getResult() == WarpResponseResultCode.CONNECTION_ERROR) {
            showToastOnUIThread("Non-recoverable connection error.");
        } else {
            showToastOnUIThread("Unable to connect, try again." + event.getResult());
        }
    }

    @Override
    public void onDisconnectDone(final ConnectEvent event) {

    }

    @Override
    public void onInitUDPDone(byte result) {

    }

    @Override
    public void onGetLiveRoomInfoDone(LiveRoomInfoEvent arg0) {


    }

    @Override
    public void onJoinRoomDone(final RoomEvent event) {
        if (progressDialog != null) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (progressDialog.isShowing())
                        progressDialog.dismiss();
                    if (event.getResult() == WarpResponseResultCode.SUCCESS) {
                        showProgressDialog("Subscribing Room...");
                        theClient.subscribeRoom(event.getData().getId());
                    } else if (event.getResult() == WarpResponseResultCode.RESOURCE_NOT_FOUND) {
                        showProgressDialog("Creating Room...");
                        theClient.createRoom("ChatRoom", nameEditText.getText().toString(), Constants.MaxUser, null);
                    } else {
                        Toast.makeText(LoginActivity.this, "onJoinRoomDone with ErrorCode: " + event.getResult(), Toast.LENGTH_LONG).show();

                    }
                }
            });
        }

    }

    @Override
    public void onLeaveRoomDone(RoomEvent arg0) {


    }

    @Override
    public void onLockPropertiesDone(byte arg0) {


    }

    @Override
    public void onSetCustomRoomDataDone(LiveRoomInfoEvent arg0) {


    }

    private void moveToChatScreen(String roomID) {
        Intent intent = new Intent(this, ChatAppActivity.class);
        intent.putExtra("userName", nameEditText.getText().toString());
        intent.putExtra("roomId", roomID);
        startActivity(intent);
    }

    @Override
    public void onSubscribeRoomDone(final RoomEvent event) {
        if (progressDialog != null) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (progressDialog.isShowing())
                        progressDialog.dismiss();

                    if (event.getResult() == WarpResponseResultCode.SUCCESS) {
                        moveToChatScreen(event.getData().getId().toString());
                    } else {
                        Toast.makeText(LoginActivity.this,"onSubscribeRoomDone Failed with ErrorCode: " + event.getResult(),Toast.LENGTH_SHORT).show();

                    }

                }
            });
        }

    }

    @Override
    public void onUnSubscribeRoomDone(RoomEvent arg0) {


    }

    @Override
    public void onUnlockPropertiesDone(byte arg0) {


    }

    @Override
    public void onUpdatePropertyDone(LiveRoomInfoEvent arg0) {


    }

    private void showToastOnUIThread(final String message) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(LoginActivity.this, message, Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onDeleteRoomDone(RoomEvent roomEvent) {

    }

    @Override
    public void onGetAllRoomsDone(AllRoomsEvent allRoomsEvent) {

    }

    @Override
    public void onCreateRoomDone(final RoomEvent roomEvent) {
        if (progressDialog != null) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (progressDialog.isShowing())
                        progressDialog.dismiss();
                    if (roomEvent.getResult() == WarpResponseResultCode.SUCCESS) {
                        showProgressDialog("Joing Room");
                        theClient.joinRoom(roomEvent.getData().getId());
                    } else {
                        Toast.makeText(LoginActivity.this, "Unable to create Chat Room", Toast.LENGTH_LONG).show();

                    }
                }
            });
        }

    }

    @Override
    public void onGetOnlineUsersDone(AllUsersEvent allUsersEvent) {

    }

    @Override
    public void onGetLiveUserInfoDone(LiveUserInfoEvent liveUserInfoEvent) {

    }

    @Override
    public void onSetCustomUserDataDone(LiveUserInfoEvent liveUserInfoEvent) {

    }

    @Override
    public void onGetMatchedRoomsDone(MatchedRoomsEvent matchedRoomsEvent) {

    }

    @Override
    public void onGetRoomsCountDone(RoomEvent roomEvent) {

    }

    @Override
    public void onGetUsersCountDone(AllUsersEvent allUsersEvent) {

    }
}
