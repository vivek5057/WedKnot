package com.example.acerpc.wedknot.NavigationFragment;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.acerpc.wedknot.InitialForms.AllFormDetailsPojo;
import com.example.acerpc.wedknot.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class RecentlyJoinedRecyclerViewAdapter extends RecyclerView.Adapter<RecentlyJoinedRecyclerViewAdapter.MyViewHolder>  {

    Context context;
    List<RecentlyJoinedPojo> recentlyJoinedPojoList;
    public static final String EXTRA_USERE = "userEmail";

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    FirebaseAuth mAuth;

    public RecentlyJoinedRecyclerViewAdapter(Context context, List<RecentlyJoinedPojo> recentlyJoinedPojoList) {
        this.context = context;
        this.recentlyJoinedPojoList = recentlyJoinedPojoList;

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
        mAuth = FirebaseAuth.getInstance();

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, final int i) {
        final View view = LayoutInflater.from(context).inflate(R.layout.recently_joined_item,viewGroup,false);
        final MyViewHolder myViewHolder = new MyViewHolder(view);

        return myViewHolder;
    }

    public void removeSentItems(final MyViewHolder myViewHolder,final int i) {

        databaseReference.child("ConnectionRequest").child(mAuth.getCurrentUser().getEmail().replace(".", "")).orderByChild("request_type").equalTo("sent").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapShot : dataSnapshot.getChildren()) {
                    final String userSent = postSnapShot.getKey();
                    Log.v("Name:::::", userSent);
                    Log.v("Index",""+myViewHolder.getAdapterPosition());

                    databaseReference.child(userSent).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                            AllFormDetailsPojo allFormDetailsPojo = dataSnapshot.getValue(AllFormDetailsPojo.class);
                            String fetchedUserEmail = allFormDetailsPojo.loginDetailsEmailId;

                            String finalUserEmail = fetchedUserEmail.replace(".", "");

                            RecentlyJoinedPojo recentlyJoinedPojo = recentlyJoinedPojoList.get(i);
                            String compareEmail = recentlyJoinedPojo.recentlyJoinedUserEmail;

                            if (finalUserEmail.equals(compareEmail)) {
                                myViewHolder.interestSent.setVisibility(View.VISIBLE);
                                myViewHolder.sendInterest.setVisibility(View.GONE);
                                myViewHolder.interestSent.setText("Request Sent");

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
        });

    }

    public void removeAcceptedOnes(final MyViewHolder myViewHolder,final int i){

        databaseReference.child("ConnectionSuccessful").child(mAuth.getCurrentUser().getEmail().replace(".","")).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapShot : dataSnapshot.getChildren()) {
                    final String connectedUser = postSnapShot.getKey();
                    Log.v("Name:::::", connectedUser);
                    Log.v("Index",""+myViewHolder.getAdapterPosition());

                    databaseReference.child(connectedUser).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                            AllFormDetailsPojo allFormDetailsPojo = dataSnapshot.getValue(AllFormDetailsPojo.class);
                            String fetchedUserName = allFormDetailsPojo.loginDetailsFullName;
                            String fetchedUserEmail = allFormDetailsPojo.loginDetailsEmailId;

                            String finalUserEmail = fetchedUserEmail.replace(".", "");
                            Log.v("SentName:::::", fetchedUserName);


                            RecentlyJoinedPojo recentlyJoinedPojo = recentlyJoinedPojoList.get(i);
                            String compareEmail = recentlyJoinedPojo.recentlyJoinedUserEmail;

                            if (finalUserEmail.equals(compareEmail)) {
                                myViewHolder.interestSent.setVisibility(View.VISIBLE);
                                myViewHolder.sendInterest.setVisibility(View.GONE);
                                myViewHolder.interestSent.setText("Connected");

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
        });

    }

    public void receivedRequest(final MyViewHolder myViewHolder,final int i){

        databaseReference.child("ConnectionRequest").child(mAuth.getCurrentUser().getEmail().replace(".", "")).orderByChild("request_type").equalTo("received").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapShot : dataSnapshot.getChildren()) {
                    final String userSent = postSnapShot.getKey();
                    Log.v("Name:::::", userSent);
                    Log.v("Index",""+myViewHolder.getAdapterPosition());

                    databaseReference.child(userSent).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                            AllFormDetailsPojo allFormDetailsPojo = dataSnapshot.getValue(AllFormDetailsPojo.class);
                            String fetchedUserEmail = allFormDetailsPojo.loginDetailsEmailId;

                            String finalUserEmail = fetchedUserEmail.replace(".", "");

                            RecentlyJoinedPojo recentlyJoinedPojo = recentlyJoinedPojoList.get(i);
                            String compareEmail = recentlyJoinedPojo.recentlyJoinedUserEmail;

                            if (finalUserEmail.equals(compareEmail)) {
                                myViewHolder.interestSent.setVisibility(View.VISIBLE);
                                myViewHolder.sendInterest.setVisibility(View.GONE);
                                myViewHolder.interestSent.setText("Request Received");

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
        });


    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder myViewHolder, final int i) {
        RecentlyJoinedPojo recentlyJoinedPojo = recentlyJoinedPojoList.get(i);

      myViewHolder.recently_joinedUserName.setText(recentlyJoinedPojo.getRecentlyJoinedUserName());
      myViewHolder.recently_joinedUserAge.setText(recentlyJoinedPojo.getRecentlyJoinedUserAge());
      myViewHolder.recently_joinedUserHeight.setText(recentlyJoinedPojo.getRecentlyJoinedUserHeight());
      myViewHolder.recently_joinedUserLanguage.setText(recentlyJoinedPojo.getRecentlyJoinedUserLanguage());
      myViewHolder.recently_joinedUserReligion.setText(recentlyJoinedPojo.getRecentlyJoinedUserReligion());
      myViewHolder.recently_joinedUserCity.setText(recentlyJoinedPojo.getRecentlyJoinedUserCity());
      myViewHolder.recently_joinedUserEmail.setText(recentlyJoinedPojo.getRecentlyJoinedUserEmail());

        if(recentlyJoinedPojo.getRecentlyJoinedUserImage()!=null) {
            Glide.with(context).load(recentlyJoinedPojo.getRecentlyJoinedUserImage()).into(myViewHolder.recently_joinedUserImage);
        }

        removeSentItems(myViewHolder,i);
        removeAcceptedOnes(myViewHolder,i);
        receivedRequest(myViewHolder,i);

        myViewHolder.recently_joinedUserImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RecentlyJoinedPojo recentlyJoinedPojo = recentlyJoinedPojoList.get(i);
                String dotReceiverEmail = recentlyJoinedPojo.recentlyJoinedUserEmail;
                Log.v("Hungama",dotReceiverEmail);
                Intent intent = new Intent(context,UserDetailOnClick.class);
                intent.putExtra(EXTRA_USERE,dotReceiverEmail);
                context.startActivity(intent);
            }
        });

        myViewHolder.sendInterest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                RecentlyJoinedPojo recentlyJoinedPojo = recentlyJoinedPojoList.get(i);
                String dotReceiverEmail = recentlyJoinedPojo.recentlyJoinedUserEmail;

                String dotCurrentUser = mAuth.getCurrentUser().getEmail();
                final String receiverUser = dotReceiverEmail.replace(".", "");
                final String senderUser = dotCurrentUser.replace(".", "");

                databaseReference.child("ConnectionRequest").child(senderUser).child(receiverUser).child("request_type").setValue("sent")
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    databaseReference.child("ConnectionRequest").child(receiverUser).child(senderUser).child("request_type").setValue("received");

                                    myViewHolder.sendInterest.setVisibility(View.GONE);
                                    myViewHolder.interestSent.setVisibility(View.VISIBLE);
                                    myViewHolder.interestSent.setText("Request Sent");

                                }
                            }
                        });
            }
        });


    }

    @Override
    public int getItemCount() {
        return recentlyJoinedPojoList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        TextView recently_joinedUserName;
        TextView recently_joinedUserAge;
        TextView recently_joinedUserHeight;
        TextView recently_joinedUserLanguage;
        TextView recently_joinedUserReligion;
        TextView recently_joinedUserCity;
        TextView recently_joinedUserCountry;
        TextView recently_joinedUserEmail;
        ImageView recently_joinedUserImage;
        Button sendInterest;
        Button interestSent;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            recently_joinedUserName = itemView.findViewById(R.id.recently_joined_user_name);
            recently_joinedUserAge = itemView.findViewById(R.id.recently_joined_user_age);
            recently_joinedUserHeight = itemView.findViewById(R.id.recently_joined_user_height);
            recently_joinedUserLanguage = itemView.findViewById(R.id.recently_joined_user_language);
            recently_joinedUserReligion = itemView.findViewById(R.id.recently_joined_user_religion);
            recently_joinedUserCity = itemView.findViewById(R.id.recently_joined_user_city);
            recently_joinedUserCountry = itemView.findViewById(R.id.recently_joined_user_country);
            recently_joinedUserImage = itemView.findViewById(R.id.recently_joined_user_image);
            recently_joinedUserEmail = itemView.findViewById(R.id.recently_joined_user_email);
            sendInterest = itemView.findViewById(R.id.recently_joined_send_interest_button);
            interestSent = itemView.findViewById(R.id.recently_joined_interest_sent_button);

        }
    }
}
