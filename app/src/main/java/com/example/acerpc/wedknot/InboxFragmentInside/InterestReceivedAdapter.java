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

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

public class InterestReceivedAdapter extends RecyclerView.Adapter<InterestReceivedAdapter.MyViewHolder> {

    Context context;
    static public List<InterestReceivedPojo> interestReceivedPojoList;

    FirebaseAuth mAuth;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    public static final String EXTRA_USERE = "userEmail";

    // OnClickListenerRequest onClickListenerRequest;

   /* public interface OnClickListenerRequest{
         void clickOnAccept(int position);
         void clickOnReject(int position);
    }*/

    public InterestReceivedAdapter() {
    }

    public InterestReceivedAdapter(Context context, List<InterestReceivedPojo> interestReceivedPojoList) {
        this.context = context;
        this.interestReceivedPojoList = interestReceivedPojoList;
       // this.onClickListenerRequest = onClickListenerRequest;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        final View view = LayoutInflater.from(context).inflate(R.layout.inbox_received_recycler_view_item,viewGroup,false);
        final MyViewHolder myViewHolder = new MyViewHolder(view);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
        mAuth = FirebaseAuth.getInstance();

        //reject friendRequest
        myViewHolder.rejectRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                InterestReceivedPojo interestReceivedPojo = interestReceivedPojoList.get(myViewHolder.getAdapterPosition());

                String dotNotWantEmail = interestReceivedPojo.getFriendEmail();
                String dotCurrentUser = mAuth.getCurrentUser().getEmail();

                final String wantToBecomeFriend = dotNotWantEmail.replace(".", "");
                final String currentUserInbox = dotCurrentUser.replace(".", "");

                //sender sent request to receiver (sent)
                databaseReference.child("ConnectionRequest").child(wantToBecomeFriend).child(currentUserInbox).removeValue()
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {

                                    Toast.makeText(view.getContext(), "Request Removed", Toast.LENGTH_SHORT).show();
                                    //received request
                                    databaseReference.child("ConnectionRequest").child(currentUserInbox).child(wantToBecomeFriend).removeValue();
                                    //SearchMatchesRecyclerAdapter.MyViewholder.sendInterest.setText("Request Sent");

                                    interestReceivedPojoList.remove(myViewHolder.getAdapterPosition());
                                    notifyItemRemoved(myViewHolder.getAdapterPosition());

                                }
                            }
                        });
            }
        });

        //Accept Request
        myViewHolder.acceptRequest.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Calendar calendar = Calendar.getInstance();
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MMMM-yyyy");
                final String currentDate =  simpleDateFormat.format(calendar.getTime());

                InterestReceivedPojo interestReceivedPojo = interestReceivedPojoList.get(myViewHolder.getAdapterPosition());

                String dotNotWantEmail = interestReceivedPojo.getFriendEmail();
                String dotCurrentUser = mAuth.getCurrentUser().getEmail();


                final String wantToBecomeFriend = dotNotWantEmail.replace(".", "");
                final String curentUserInbox = dotCurrentUser.replace(".", "");


                databaseReference.child("ConnectionSuccessful").child(curentUserInbox).child(wantToBecomeFriend).child("date").setValue(currentDate)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()){
                                    databaseReference.child("ConnectionSuccessful").child(wantToBecomeFriend).child(curentUserInbox).child("date").setValue(currentDate)
                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {

                                                    //Deleting sent-request
                                                    databaseReference.child("ConnectionRequest").child(wantToBecomeFriend).child(curentUserInbox).removeValue()
                                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                @Override
                                                                public void onComplete(@NonNull Task<Void> task) {
                                                                    if (task.isSuccessful()) {

                                                                        InterestReceivedAdapter interestReceivedAdapter = new InterestReceivedAdapter();

                                                                        Toast.makeText(view.getContext(), "Request Accepted", Toast.LENGTH_SHORT).show();
                                                                        //received request
                                                                        databaseReference.child("ConnectionRequest").child(curentUserInbox).child(wantToBecomeFriend).removeValue();
                                                                        //SearchMatchesRecyclerAdapter.MyViewholder.sendInterest.setText("Request Sent");

                                                                        interestReceivedPojoList.remove(myViewHolder.getAdapterPosition());
                                                                        notifyItemRemoved(myViewHolder.getAdapterPosition());

                                                                    }
                                                                }
                                                            });
                                                    //
                                                }
                                            });

                                }
                            }
                        });
            }
        });

        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, final int i) {
        final InterestReceivedPojo interestReceivedPojo = interestReceivedPojoList.get(i);
        myViewHolder.friendName.setText(interestReceivedPojo.getFriendName());
        myViewHolder.friendCity.setText(interestReceivedPojo.getFriendCity());
        myViewHolder.friendCountry.setText(interestReceivedPojo.getFriendCountry());
        if(interestReceivedPojo.getFriendPic()!=null) {
            Glide.with(context).load(interestReceivedPojo.getFriendPic()).into(myViewHolder.friendPic);
        }

        myViewHolder.receivedCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InterestReceivedPojo interestReceivedPojo = interestReceivedPojoList.get(i);
                String dotReceiverEmail = interestReceivedPojo.getFriendEmail();
                //Log.v("Hungama",dotReceiverEmail);
                Intent intent = new Intent(context, UserDetailOnClick.class);
                intent.putExtra(EXTRA_USERE,dotReceiverEmail);
                context.startActivity(intent);
            }
        });


    }




    @Override
    public int getItemCount() {
        return interestReceivedPojoList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return  position;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView friendName;
        TextView friendCity;
        TextView friendCountry;
        ImageView friendPic;
        CardView receivedCard;

        Button acceptRequest;
        Button rejectRequest;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            friendName = itemView.findViewById(R.id.inbox_received_user_name);
            friendCity = itemView.findViewById(R.id.inbox_received_user_city);
            friendCountry = itemView.findViewById(R.id.inbox_received_user_country);
            friendPic = itemView.findViewById(R.id.inbox_received_user_pic);
            acceptRequest = itemView.findViewById(R.id.inbox_received_accept_user);
            rejectRequest = itemView.findViewById(R.id.inbox_received_cancel_user);
            receivedCard = itemView.findViewById(R.id.received_card_view);
        }
    }
}
