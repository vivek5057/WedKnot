package com.example.acerpc.wedknot.ChatFragmentInside;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.acerpc.wedknot.R;

import java.util.List;

public class ChatRecycleViewAdapter extends RecyclerView.Adapter<ChatRecycleViewAdapter.MyViewHolder> {

    Context context;
    final private ListItemClickListener listItemClickListener;
    List<ChatListItemPojo> chatlist;
    Dialog imageDialog;
    static  public View view;

    public interface ListItemClickListener {
        void onListItemClick(int clickedItemIndex);
    }

    public ChatRecycleViewAdapter(Context context, ListItemClickListener listItemClickListener, List<ChatListItemPojo> chatlist) {
        this.context = context;
        this.listItemClickListener = listItemClickListener;
        this.chatlist = chatlist;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        view = LayoutInflater.from(context).inflate(R.layout.chat_recycleview_list_item, viewGroup, false);

        final MyViewHolder myHolder = new MyViewHolder(view);
        imageDialog = new Dialog(context);

        //Clicking Image Dialog popup

        myHolder.chatImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageDialog.setContentView(R.layout.click_image);
                ImageView dialogImage = (ImageView) imageDialog.findViewById(R.id.imageClick);
                TextView nameImage = (TextView) imageDialog.findViewById(R.id.name_on_image);
                //dialogImage.setImageResource(Integer.parseInt(customPojos.get(myHolder.getAdapterPosition()).getImageView()));
                ChatListItemPojo customPojo = chatlist.get(myHolder.getAdapterPosition());
                nameImage.setText(customPojo.getChatname());
                if(customPojo.getChatimage()!=null)
                Glide.with(context).load(customPojo.getChatimage()).into(dialogImage);
                imageDialog.show();
            }
        });
        return myHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        ChatListItemPojo chatListItemPojo = chatlist.get(i);
        myViewHolder.chatName.setText(chatListItemPojo.getChatname());
        Glide.with(context).load(chatListItemPojo.getChatimage()).into(myViewHolder.chatImage);
    }

    @Override
    public int getItemCount() {
        return chatlist.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView chatImage;
        TextView chatName;
        RelativeLayout textNameRelative;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            textNameRelative = (RelativeLayout) itemView.findViewById(R.id.text_name_relative);
            chatImage = (ImageView) itemView.findViewById(R.id.person_image);
            chatName = (TextView) itemView.findViewById(R.id.person_name);
            textNameRelative.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            listItemClickListener.onListItemClick(getAdapterPosition());
        }
    }
}
