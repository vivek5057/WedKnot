package com.example.acerpc.wedknot.ChatFragmentInside;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.acerpc.wedknot.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import static com.example.acerpc.wedknot.NavigationFragment.ChatFragment.EXTRA_NAME;
import static com.example.acerpc.wedknot.NavigationFragment.ChatFragment.EXTRA_URL;
import static com.example.acerpc.wedknot.NavigationFragment.ChatFragment.EXTRA_USER_EMAIL;

public class ChatBoxActivity extends AppCompatActivity {

    ImageView userImageInChatBox;
    TextView userNameInChatBox;
    TextView messageEditText;
    ImageView sendMessageButton;
    static ImageView deleteMessageButton;
    String friendEmail;


    RecyclerView recyclerView;
    ChatBoxRecycleViewAdapter chatBoxRecycleViewAdapter;
    LinearLayoutManager linearLayoutManager;
    List<ChatBubblePojo> messagesList;

    FirebaseAuth mAuth;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_box);

        userImageInChatBox = (ImageView) findViewById(R.id.chatbox_userimage);
        userNameInChatBox = (TextView) findViewById(R.id.chatbox_username);
        messageEditText = findViewById(R.id.message_edit_text);
        sendMessageButton = findViewById(R.id.send_message_button);
        deleteMessageButton = findViewById(R.id.delete_message_bubble);

        recyclerView = findViewById(R.id.chatbox_recycler_view);
        linearLayoutManager = new LinearLayoutManager(ChatBoxActivity.this);
        recyclerView.setLayoutManager(linearLayoutManager);
        linearLayoutManager.setStackFromEnd(true);
        messagesList = new ArrayList<>();

        mAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();

        //Getting Content
        Intent intent = getIntent();
        String imageURL = intent.getStringExtra(EXTRA_URL);
        String userName = intent.getStringExtra(EXTRA_NAME);
        friendEmail = intent.getStringExtra(EXTRA_USER_EMAIL);
        if(imageURL!=null) {
            Glide.with(this).load(imageURL).into(userImageInChatBox);
        }
        userNameInChatBox.setText(userName);

        sendMessageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (messageEditText.getText().toString().isEmpty()) {
                    Toast.makeText(ChatBoxActivity.this, "Type a message", Toast.LENGTH_SHORT).show();
                } else {
                    writeMessage();
                }
                messageEditText.setText("");
            }
        });

        deleteMessageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               // final String currentUser = mAuth.getCurrentUser().getEmail().replace(".", "");

               /* databaseReference.child("Chats").child(currentUser).child(friendEmail).child("messages").child("currentUserMessage")..removeValue()
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                databaseReference.child("Chats").child(friendEmail).child(currentUser).child("messages").child("currentUserMessage").removeValue();
                            }
                        });*/

            }
        });


        readMessage();
    }

    public void writeMessage() {
        final String currentUser = mAuth.getCurrentUser().getEmail().replace(".", "");

        final String typedMessage = messageEditText.getText().toString().trim();
        final ChatBubblePojo chatBubblePojo = new ChatBubblePojo(typedMessage,currentUser);

        databaseReference.child("Chats").child(currentUser).child(friendEmail).child("messages").push().setValue(chatBubblePojo)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        databaseReference.child("Chats").child(friendEmail).child(currentUser).child("messages").push().setValue(chatBubblePojo);
                    }
                });
    }

    public void readMessage(){

        final String currentUser = mAuth.getCurrentUser().getEmail().replace(".", "");

        databaseReference.child("Chats").child(currentUser).child(friendEmail).child("messages").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                ChatBubblePojo chatBubblePojo = dataSnapshot.getValue(ChatBubblePojo.class);
                if(chatBubblePojo!=null) {
                    String read_message = chatBubblePojo.currentUserMessage;
                    String messageEmailBox = chatBubblePojo.messageEmail;

                    //  Log.v("friendMessage", read_message);

                    messagesList.add(new ChatBubblePojo(read_message, messageEmailBox));
                }
                chatBoxRecycleViewAdapter = new ChatBoxRecycleViewAdapter(ChatBoxActivity.this, messagesList);
                recyclerView.setAdapter(chatBoxRecycleViewAdapter);

            }


            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    @Override
    public void onBackPressed() {
        supportFinishAfterTransition();
    }

    /* databaseReference.child("Chats").child(currentUser).child(friendEmail).child("sent_message").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot postSnapShot : dataSnapshot.getChildren()) {
                    final String read_sent_message = postSnapShot.getValue(String.class);
                    Log.v("currentUser_Message",read_sent_message);

                    databaseReference.child("Chats").child(currentUser).child(friendEmail).child("received_message").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                            for(DataSnapshot postSnapShot : dataSnapshot.getChildren()){

                                String read_received_message = postSnapShot.getValue(String.class);
                                Log.v("friendMessage",read_received_message);

                                messagesList.add(new ChatBubblePojo(read_sent_message,read_received_message));
                                chatBoxRecycleViewAdapter = new ChatBoxRecycleViewAdapter(ChatBoxActivity.this,messagesList);
                                recyclerView.setAdapter(chatBoxRecycleViewAdapter);

                            }

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

                    }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });*/

}
