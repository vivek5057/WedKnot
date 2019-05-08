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

public class InboxConnectedAdapter extends RecyclerView.Adapter<InboxConnectedAdapter.MyViewHolder> {

    Context context;
    List<InterestReceivedPojo> interestConnectedPojoList;
    public static final String EXTRA_USERE = "userEmail";

    FirebaseAuth mAuth;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    public InboxConnectedAdapter(Context context, List<InterestReceivedPojo> interestConnectedPojoList) {
        this.context = context;
        this.interestConnectedPojoList = interestConnectedPojoList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        final View view = LayoutInflater.from(context).inflate(R.layout.inbox_connected_recycler_view_item,viewGroup,false);
        final MyViewHolder myViewHolder = new MyViewHolder(view);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
        mAuth = FirebaseAuth.getInstance();


        myViewHolder.removeConnection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                InterestReceivedPojo interestReceivedPojo = interestConnectedPojoList.get(myViewHolder.getAdapterPosition());

                String dotNotWantEmail = interestReceivedPojo.getFriendEmail();
                String dotCurrentUser = mAuth.getCurrentUser().getEmail();

                final String wantToBecomeFriend = dotNotWantEmail.replace(".", "");
                final String currentUserInbox = dotCurrentUser.replace(".", "");

                databaseReference.child("ConnectionSuccessful").child(wantToBecomeFriend).child(currentUserInbox).removeValue()
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {

                                    Toast.makeText(view.getContext(), "Connection Removed", Toast.LENGTH_SHORT).show();
                                    databaseReference.child("ConnectionSuccessful").child(currentUserInbox).child(wantToBecomeFriend).removeValue();

                                    interestConnectedPojoList.remove(myViewHolder.getAdapterPosition());
                                    notifyItemRemoved(myViewHolder.getAdapterPosition());


                                }
                            }
                        });

            }
        });

        return myViewHolder;
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
    public void onBindViewHolder(@NonNull final MyViewHolder myViewHolder, final int i) {
        final InterestReceivedPojo interestConnectedPojo = interestConnectedPojoList.get(i);
        myViewHolder.friendName.setText(interestConnectedPojo.getFriendName());
        myViewHolder.friendCity.setText(interestConnectedPojo.getFriendCity());
        myViewHolder.friendCountry.setText(interestConnectedPojo.getFriendCountry());
        if(interestConnectedPojo.getFriendPic()!=null) {
            Glide.with(context).load(interestConnectedPojo.getFriendPic()).into(myViewHolder.friendPic);
        }

        myViewHolder.connectedCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InterestReceivedPojo interestConnectedPojo = interestConnectedPojoList.get(i);
                String dotReceiverEmail = interestConnectedPojo.getFriendEmail();
               // Log.v("Hungama",dotReceiverEmail);
                Intent intent = new Intent(context, UserDetailOnClick.class);
                intent.putExtra(EXTRA_USERE,dotReceiverEmail);
                context.startActivity(intent);

            }
        });

    }

    @Override
    public int getItemCount() {
        return interestConnectedPojoList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        TextView friendName;
        TextView friendCity;
        TextView friendCountry;
        ImageView friendPic;
        CardView connectedCard;

        Button removeConnection;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            friendName = itemView.findViewById(R.id.inbox_connected_user_name);
            friendCity = itemView.findViewById(R.id.inbox_connected_user_city);
            friendCountry = itemView.findViewById(R.id.inbox_connected_user_country);
            friendPic = itemView.findViewById(R.id.inbox_connected_user_pic);
            removeConnection = itemView.findViewById(R.id.inbox_connected_cancel_user);
            connectedCard = itemView.findViewById(R.id.connected_card_view);
        }
    }
}
