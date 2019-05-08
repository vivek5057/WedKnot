package com.example.acerpc.wedknot.InboxFragmentInside;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.acerpc.wedknot.NavigationFragment.UserDetailOnClick;
import com.example.acerpc.wedknot.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class InterestSentAdapter extends RecyclerView.Adapter<InterestSentAdapter.MyViewHolder> {

    Context context;
    List<InterestReceivedPojo> interestSentPojoList;
    public static final String EXTRA_USERE = "userEmail";

    FirebaseAuth mAuth;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;




    public InterestSentAdapter(Context context, List<InterestReceivedPojo> interestSentPojoList) {
        this.context = context;
        this.interestSentPojoList = interestSentPojoList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        final View view = LayoutInflater.from(context).inflate(R.layout.inbox_sent_recycler_view_item,viewGroup,false);
        final MyViewHolder myViewHolder = new MyViewHolder(view);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
        mAuth = FirebaseAuth.getInstance();

        myViewHolder.cancelRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final InterestReceivedPojo interestReceivedPojo = interestSentPojoList.get(myViewHolder.getAdapterPosition());

                String dotNotWantEmail = interestReceivedPojo.getFriendEmail();
                String dotCurrentUser = mAuth.getCurrentUser().getEmail();

                final String wantToBecomeFriend = dotNotWantEmail.replace(".", "");
                final String currentUserInbox = dotCurrentUser.replace(".", "");

                databaseReference.child("ConnectionRequest").child(wantToBecomeFriend).child(currentUserInbox).removeValue()
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {

                                    Toast.makeText(view.getContext(), "Request Removed", Toast.LENGTH_SHORT).show();
                                    //received request
                                    databaseReference.child("ConnectionRequest").child(currentUserInbox).child(wantToBecomeFriend).removeValue();
                                    //SearchMatchesRecyclerAdapter.MyViewholder.sendInterest.setText("Request Sent");

                                    interestSentPojoList.remove(myViewHolder.getAdapterPosition());
                                    notifyItemRemoved(myViewHolder.getAdapterPosition());

                                }
                            }
                        });
            }
        });



        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, final int i) {
        InterestReceivedPojo interestSentPojo = interestSentPojoList.get(i);
        myViewHolder.friendName.setText(interestSentPojo.getFriendName());
        myViewHolder.friendCity.setText(interestSentPojo.getFriendCity());
        myViewHolder.friendCountry.setText(interestSentPojo.getFriendCountry());
        if(interestSentPojo.getFriendPic()!=null) {
            Glide.with(context).load(interestSentPojo.getFriendPic()).into(myViewHolder.friendPic);
        }

        myViewHolder.sentCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InterestReceivedPojo interestReceivedPojo = interestSentPojoList.get(i);
                String dotReceiverEmail = interestReceivedPojo.getFriendEmail();
               // Log.v("Hungama",dotReceiverEmail);
                Intent intent = new Intent(context, UserDetailOnClick.class);
                intent.putExtra(EXTRA_USERE,dotReceiverEmail);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return interestSentPojoList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        TextView friendName;
        TextView friendCity;
        TextView friendCountry;
        ImageView friendPic;
        CardView sentCard;
        Button cancelRequest;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            friendName = itemView.findViewById(R.id.inbox_sent_user_name);
            friendCity = itemView.findViewById(R.id.inbox_sent_user_city);
            friendCountry = itemView.findViewById(R.id.inbox_sent_user_country);
            friendPic = itemView.findViewById(R.id.inbox_sent_user_pic);
            cancelRequest = itemView.findViewById(R.id.inbox_sent_cancel_user);
            sentCard = itemView.findViewById(R.id.sent_card_view);
        }
    }

}
