package com.example.acerpc.wedknot.ChatFragmentInside;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.acerpc.wedknot.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

public class ChatBoxRecycleViewAdapter extends RecyclerView.Adapter<ChatBoxRecycleViewAdapter.MyViewHolder> {

    Context context;
    List<ChatBubblePojo> chatBubbleList;
    FirebaseUser user;
    View view;
    static String deleteMessage;

    public ChatBoxRecycleViewAdapter(Context context, List<ChatBubblePojo> chatBubbleList) {
        this.context = context;
        this.chatBubbleList = chatBubbleList;
        user = FirebaseAuth.getInstance().getCurrentUser();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        view = LayoutInflater.from(context).inflate(R.layout.chatbox_message_item,viewGroup,false);
        final MyViewHolder myViewHolder = new MyViewHolder(view);

        myViewHolder.currentUserBubbleLayout.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                ChatBubblePojo chatBubblePojo = chatBubbleList.get(myViewHolder.getAdapterPosition());

                ChatBoxActivity.deleteMessageButton.setVisibility(View.VISIBLE);
                deleteMessage = chatBubblePojo.getCurrentUserMessage();
                return true;
            }
        });


        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {

        ChatBubblePojo chatBubblePojo = chatBubbleList.get(i);


        if(user.getEmail().replace(".","").equals(chatBubblePojo.getMessageEmail())){
            myViewHolder.currentUserMessageBubble.setText(chatBubblePojo.getCurrentUserMessage());
            myViewHolder.senderBubbleLayout.setVisibility(View.GONE);
            myViewHolder.currentUserBubbleLayout.setVisibility(View.VISIBLE);
        }else{
            myViewHolder.senderMessageBubble.setText(chatBubblePojo.getCurrentUserMessage());
            myViewHolder.currentUserBubbleLayout.setVisibility(View.GONE);
            myViewHolder.senderBubbleLayout.setVisibility(View.VISIBLE);
        }

      /*  myViewHolder.senderBubbleLayout.setVisibility(View.GONE);
            myViewHolder.currentUserBubbleLayout.setVisibility(View.VISIBLE);

        } else {
            myViewHolder.senderBubbleLayout.setVisibility(View.VISIBLE);
            myViewHolder.currentUserBubbleLayout.setVisibility(View.GONE);
        }*/

    }

    @Override
    public int getItemCount() {
        return chatBubbleList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        TextView currentUserMessageBubble;
        TextView senderMessageBubble;
        LinearLayout currentUserBubbleLayout;
        LinearLayout senderBubbleLayout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            currentUserMessageBubble = itemView.findViewById(R.id.current_user_message);
            senderMessageBubble = itemView.findViewById(R.id.sender_message);
            currentUserBubbleLayout = itemView.findViewById(R.id.currentuserlinearLayout);
            senderBubbleLayout = itemView.findViewById(R.id.friendlinearLayout);
        }
    }
}
