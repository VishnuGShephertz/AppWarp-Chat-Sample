package com.shephertz.appwarp.chat;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.shephertz.app42.gaming.multiplayer.client.ConnectionState;
import com.shephertz.app42.gaming.multiplayer.client.WarpClient;
import com.shephertz.app42.gaming.multiplayer.client.command.WarpResponseResultCode;
import com.shephertz.app42.gaming.multiplayer.client.events.ChatEvent;
import com.shephertz.app42.gaming.multiplayer.client.events.ConnectEvent;
import com.shephertz.app42.gaming.multiplayer.client.events.LiveRoomInfoEvent;
import com.shephertz.app42.gaming.multiplayer.client.events.LobbyData;
import com.shephertz.app42.gaming.multiplayer.client.events.MoveEvent;
import com.shephertz.app42.gaming.multiplayer.client.events.RoomData;
import com.shephertz.app42.gaming.multiplayer.client.events.RoomEvent;
import com.shephertz.app42.gaming.multiplayer.client.events.UpdateEvent;
import com.shephertz.app42.gaming.multiplayer.client.listener.ChatRequestListener;
import com.shephertz.app42.gaming.multiplayer.client.listener.ConnectionRequestListener;
import com.shephertz.app42.gaming.multiplayer.client.listener.NotifyListener;
import com.shephertz.app42.gaming.multiplayer.client.listener.RoomRequestListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ChatAppActivity extends AppCompatActivity implements RoomRequestListener, NotifyListener, ConnectionRequestListener, ChatRequestListener {
    private Handler handler = new Handler();
    private WarpClient theClient;
    private ArrayList<User> onlineUserList = new ArrayList<User>();
    private ProgressDialog progressDialog;
    private EditText inputEditText;
    private List<ChatMessage> messageList = null;
    private ChatAppMsgAdapter chatAppMsgAdapter;
    private RecyclerView msgRecyclerView;
    private String myUserName,roomId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_app);
        try {
            theClient = WarpClient.getInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        msgRecyclerView = (RecyclerView)findViewById(R.id.chat_recycler_view);
        messageList = new ArrayList<ChatMessage>();
      //  ChatMessage msgDto = new ChatMessage(ChatMessage.MSG_TYPE_RECEIVED, "hello");
//        for(int i=0;i<10;i++){
//            ChatMessage msgDto = new ChatMessage(ChatMessage.MSG_TYPE_RECEIVED, "hello"+i);
//            messageList.add(msgDto);
//        }


        // Create the data adapter with above data list.
        chatAppMsgAdapter = new ChatAppMsgAdapter(messageList);

        // Set data adapter to RecyclerView.
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        msgRecyclerView.setLayoutManager(llm);
        msgRecyclerView.setAdapter(chatAppMsgAdapter);
        inputEditText = (EditText)findViewById(R.id.chat_input_msg);

        myUserName=getIntent().getStringExtra("userName");
        roomId=getIntent().getStringExtra("roomId");
        TextView heading=findViewById(R.id.txtHeading);
        heading.setText("RoomID : "+roomId);
    }

    @Override
    protected void onStart() {
        super.onStart();
        theClient.addConnectionRequestListener(this);
        theClient.addRoomRequestListener(this);
        theClient.addNotificationListener(this);
        theClient.addChatRequestListener(this);
        if (theClient.getConnectionState() == ConnectionState.CONNECTED) {
          //  progressDialog = ProgressDialog.show(this, "", "Please wait..");
           theClient.getLiveRoomInfo(roomId);
            System.out.println(" Test ");
        } else {
            theClient.RecoverConnection();
        }

    }

    @Override
    protected void onStop() {
        super.onStop();
        theClient.removeConnectionRequestListener(this);
        theClient.removeRoomRequestListener(this);
        theClient.removeNotificationListener(this);
        theClient.removeChatRequestListener(this);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (theClient != null) {
            theClient.removeConnectionRequestListener(this);
            theClient.removeRoomRequestListener(this);
            theClient.removeNotificationListener(this);
            theClient.removeChatRequestListener(this);
            handleLeaveRoom();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    private void handleLeaveRoom() {
        if (theClient != null) {
            theClient.unsubscribeRoom(roomId);
            theClient.leaveRoom(roomId);
            theClient.disconnect();
        }
    }


    @Override
    public void onGetLiveRoomInfoDone(final LiveRoomInfoEvent event) {
        if (progressDialog != null) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    progressDialog.dismiss();
                }
            });
        }

        if (event.getResult() == WarpResponseResultCode.SUCCESS) {
            onlineUserList.clear();
            if(event.getJoinedUsers()!=null&&event.getJoinedUsers().length>1) {// if more than one user is online
                final String onlineUser[] = Utils.removeLocalUserNameFromArray(event.getJoinedUsers());
                for (int i = 0; i < onlineUser.length; i++) {
                    User user = new User(onlineUser[i].toString(), true);
                    Log.d(onlineUser[i].toString(), onlineUser[i].toString());
                    onlineUserList.add(user);
                }
            }
        } else {
            showToastOnUIThread("onGetLiveRoomInfoDone Failed with ErrorCode: " + event.getResult());
        }
    }

    private void showToastOnUIThread(final String message) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(ChatAppActivity.this, message, Toast.LENGTH_LONG).show();
            }
        });
    }

    private void updateChatList(String messageType,String message,String sender){
        ChatMessage msgDto = new ChatMessage(messageType, message,sender);
        messageList.add(msgDto);

        int newMsgPosition = messageList.size() - 1;

        // Notify recycler view insert one new data.
        chatAppMsgAdapter.notifyItemInserted(newMsgPosition);

        // Scroll RecyclerView to the last message.
        msgRecyclerView.scrollToPosition(newMsgPosition);

        // Empty the input edit text box.
        inputEditText.setText("");
//        chatAppMsgAdapter = new ChatAppMsgAdapter(messageList);
//        msgRecyclerView.setAdapter(chatAppMsgAdapter);
    }
    public void onSendClicked(View view) {

            theClient.sendChat(inputEditText.getText().toString());
            System.out.println("Users");
            updateChatList(ChatMessage.MSG_TYPE_SENT,inputEditText.getText().toString(),myUserName);


    }

    @Override
    public void onJoinRoomDone(RoomEvent arg0) {

    }

    @Override
    public void onLeaveRoomDone(RoomEvent arg0) {

    }

    @Override
    public void onSetCustomRoomDataDone(LiveRoomInfoEvent arg0) {

    }

    @Override
    public void onSubscribeRoomDone(RoomEvent arg0) {

    }

    @Override
    public void onUnSubscribeRoomDone(RoomEvent arg0) {

    }

    @Override
    public void onUpdatePropertyDone(LiveRoomInfoEvent arg0) {

    }

    private void updateReceviedChat(String userName,String message){
        if(myUserName!=null&&!myUserName.equalsIgnoreCase(userName)){
            updateChatList(ChatMessage.MSG_TYPE_RECEIVED,message,userName);
        }
    }
    @Override
    public void onChatReceived(final ChatEvent event) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                updateReceviedChat(event.getSender(),event.getMessage());

            }
        });
    }

    @Override
    public void onPrivateChatReceived(final String userName, final String message) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {

            }
        });
    }

    @Override
    public void onSendPrivateChatDone(byte result) {


    }

    @Override
    public void onRoomCreated(RoomData arg0) {

    }

    @Override
    public void onRoomDestroyed(RoomData arg0) {

    }

    @Override
    public void onUpdatePeersReceived(UpdateEvent arg0) {

    }

    @Override
    public void onUserJoinedLobby(LobbyData arg0, String arg1) {

    }

    @Override
    public void onUserJoinedRoom(final RoomData roomData, final String userName) {
        if (userName.equals(Utils.myUserName) == false) {
            onlineUserList.add(new User(userName, true));

        }
    }

    @Override
    public void onUserLeftLobby(LobbyData arg0, String arg1) {

    }

    @Override
    public void onUserLeftRoom(final RoomData roomData, final String userName) {
        for (int i = 0; i < onlineUserList.size(); i++) {
            User user = onlineUserList.get(i);
            if (user.getName().equals(userName)) {
                onlineUserList.remove(user);
            }
        }

    }

    @Override
    public void onMoveCompleted(MoveEvent arg0) {

    }


    @Override
    public void onUserPaused(String locid, boolean isLobby, String userName) {
        for (int i = 0; i < onlineUserList.size(); i++) {
            User user = onlineUserList.get(i);
            if (user.getName().equals(userName)) {
                user.setStatus(false);
            }
        }

    }

    @Override
    public void onUserResumed(String locid, boolean isLobby, String userName) {
        for (int i = 0; i < onlineUserList.size(); i++) {
            User user = onlineUserList.get(i);
            if (user.getName().equals(userName)) {
                user.setStatus(true);
            }
        }

    }

    @Override
    public void onLockPropertiesDone(byte arg0) {

    }

    @Override
    public void onUnlockPropertiesDone(byte arg0) {

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
            showToastOnUIThread("Connection success");
        } else if (event.getResult() == WarpResponseResultCode.SUCCESS_RECOVERED) {
            showToastOnUIThread("Connection recovered");
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (progressDialog != null) {
                        progressDialog.dismiss();
                    }
                    progressDialog = ProgressDialog.show(ChatAppActivity.this, "", "Please wait..");
                }
            });

            theClient.getLiveRoomInfo(roomId);
        } else if (event.getResult() == WarpResponseResultCode.CONNECTION_ERROR_RECOVERABLE) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    progressDialog = ProgressDialog.show(ChatAppActivity.this, "", "Recoverable connection error. Recovering session after 5 seconds");
                }
            });
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    progressDialog.setMessage("Recovering...");
                    theClient.RecoverConnection();
                }
            }, 5000);
        } else {
            showToastOnUIThread("Non-recoverable connection error." + event.getResult());
            handleLeaveRoom();
            this.finish();
        }

    }

    @Override
    public void onDisconnectDone(ConnectEvent arg0) {

    }

    @Override
    public void onInitUDPDone(byte result) {

    }

    @Override
    public void onSendChatDone(byte result) {
        if (result != WarpResponseResultCode.SUCCESS) {
            showToastOnUIThread("onSendChatDone Failed with ErrorCode: " + result);
        }
    }


    @Override
    public void onGameStarted(String arg0, String arg1, String arg2) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onGameStopped(String arg0, String arg1) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onUserChangeRoomProperty(RoomData arg0, String arg1,
                                         HashMap<String, Object> arg2, HashMap<String, String> arg3) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onNextTurnRequest(String arg0) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onPrivateUpdateReceived(String arg0, byte[] arg1, boolean arg2) {
        // TODO Auto-generated method stub

    }
}
