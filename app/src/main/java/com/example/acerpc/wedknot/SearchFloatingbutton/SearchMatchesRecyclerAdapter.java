package com.example.acerpc.wedknot.SearchFloatingbutton;

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
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.acerpc.wedknot.InitialForms.AllFormDetailsPojo;
import com.example.acerpc.wedknot.NavigationFragment.UserDetailOnClick;
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

public class SearchMatchesRecyclerAdapter extends RecyclerView.Adapter<SearchMatchesRecyclerAdapter.MyViewholder> {

    Context context;
    static List<SearchMatchesItemPojo> searchMatchesItemPojoLists;
    public static final String EXTRA_USERE = "userEmail";
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    FirebaseAuth mAuth;


    public SearchMatchesRecyclerAdapter() {

    }


    public SearchMatchesRecyclerAdapter(Context context, List<SearchMatchesItemPojo> searchMatchesItemPojoList) {
        this.context = context;
        this.searchMatchesItemPojoLists = searchMatchesItemPojoList;

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
        mAuth = FirebaseAuth.getInstance();

    }


    @NonNull
    @Override
    public MyViewholder onCreateViewHolder(@NonNull ViewGroup viewGroup, final int i) {
        final View view = LayoutInflater.from(context).inflate(R.layout.search_matches_item, viewGroup, false);
        final MyViewholder myViewholder = new MyViewholder(view);

        removeSentItems(myViewholder);
        removeAcceptedOnes(myViewholder);
        receivedRequest(myViewholder);


        myViewholder.sendInterest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SearchMatchesItemPojo searchMatchesItemPojo = searchMatchesItemPojoLists.get(myViewholder.getAdapterPosition());
                String dotReceiverEmail = searchMatchesItemPojo.getSearchUserEmail();
                String dotCurrentUser = mAuth.getCurrentUser().getEmail();
                final String receiverUser = dotReceiverEmail.replace(".", "");
                final String senderUser = dotCurrentUser.replace(".", "");

                databaseReference.child("ConnectionRequest").child(senderUser).child(receiverUser).child("request_type").setValue("sent")
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(view.getContext(), "Request Sent", Toast.LENGTH_SHORT).show();
                                    databaseReference.child("ConnectionRequest").child(receiverUser).child(senderUser).child("request_type").setValue("received");

                                    myViewholder.sendInterest.setVisibility(View.GONE);
                                    myViewholder.interestSent.setVisibility(View.VISIBLE);
                                    myViewholder.interestSent.setText("Request Sent");

                                }
                            }
                        });
            }
        });

        return myViewholder;
    }

    public void removeSentItems(final MyViewholder myViewholder) {

        databaseReference.child("ConnectionRequest").child(mAuth.getCurrentUser().getEmail().replace(".", "")).orderByChild("request_type").equalTo("sent").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapShot : dataSnapshot.getChildren()) {
                    final String userSent = postSnapShot.getKey();
                    Log.v("Name:::::", userSent);

                            databaseReference.child(userSent).addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                    AllFormDetailsPojo allFormDetailsPojo = dataSnapshot.getValue(AllFormDetailsPojo.class);
                                    String fetchedUserEmail = allFormDetailsPojo.loginDetailsEmailId;

                                    String finalUserEmail = fetchedUserEmail.replace(".", "");

                                    SearchMatchesItemPojo searchMatchesItemPojo = searchMatchesItemPojoLists.get(myViewholder.getAdapterPosition());
                                    String compareEmail = searchMatchesItemPojo.searchUserEmail;

                                    if (finalUserEmail.equals(compareEmail)) {
                                        myViewholder.interestSent.setVisibility(View.VISIBLE);
                                        myViewholder.sendInterest.setVisibility(View.GONE);
                                        myViewholder.interestSent.setText("Request Sent");

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

    public void removeAcceptedOnes(final MyViewholder myViewholder){

        databaseReference.child("ConnectionSuccessful").child(mAuth.getCurrentUser().getEmail().replace(".","")).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapShot : dataSnapshot.getChildren()) {
                    final String connectedUser = postSnapShot.getKey();
                    Log.v("Name:::::", connectedUser);

                    databaseReference.child(connectedUser).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                            AllFormDetailsPojo allFormDetailsPojo = dataSnapshot.getValue(AllFormDetailsPojo.class);
                            String fetchedUserName = allFormDetailsPojo.loginDetailsFullName;
                            String fetchedUserEmail = allFormDetailsPojo.loginDetailsEmailId;

                            String finalUserEmail = fetchedUserEmail.replace(".", "");
                            Log.v("SentName:::::", fetchedUserName);

                            SearchMatchesItemPojo searchMatchesItemPojo = searchMatchesItemPojoLists.get(myViewholder.getAdapterPosition());
                            String compareEmail = searchMatchesItemPojo.searchUserEmail;

                            if (finalUserEmail.equals(compareEmail)) {
                                myViewholder.interestSent.setVisibility(View.VISIBLE);
                                myViewholder.sendInterest.setVisibility(View.GONE);
                                myViewholder.interestSent.setText("Connected");

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

    public void receivedRequest(final MyViewholder myViewholder){

        databaseReference.child("ConnectionRequest").child(mAuth.getCurrentUser().getEmail().replace(".", "")).orderByChild("request_type").equalTo("received").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapShot : dataSnapshot.getChildren()) {
                    final String userSent = postSnapShot.getKey();
                    Log.v("Name:::::", userSent);

                    databaseReference.child(userSent).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                            AllFormDetailsPojo allFormDetailsPojo = dataSnapshot.getValue(AllFormDetailsPojo.class);
                            String fetchedUserEmail = allFormDetailsPojo.loginDetailsEmailId;

                            String finalUserEmail = fetchedUserEmail.replace(".", "");

                            SearchMatchesItemPojo searchMatchesItemPojo = searchMatchesItemPojoLists.get(myViewholder.getAdapterPosition());
                            String compareEmail = searchMatchesItemPojo.searchUserEmail;

                            if (finalUserEmail.equals(compareEmail)) {
                                myViewholder.interestSent.setVisibility(View.VISIBLE);
                                myViewholder.sendInterest.setVisibility(View.GONE);
                                myViewholder.interestSent.setText("Request Received");

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
    public void onBindViewHolder(@NonNull MyViewholder myViewholder, final int position) {

        SearchMatchesItemPojo searchMatchesItemPojo = searchMatchesItemPojoLists.get(position);
        myViewholder.searchUserName.setText(searchMatchesItemPojo.getSearchUserName());
        myViewholder.searchUserAge.setText(searchMatchesItemPojo.getSearchUserAge());
        myViewholder.searchUserHeight.setText(searchMatchesItemPojo.getSearchUserHeight());
        myViewholder.searchUserLanguage.setText(searchMatchesItemPojo.getSearchUserLanguage());
        myViewholder.searchUserReligion.setText(searchMatchesItemPojo.getSearchUserReligion());
        myViewholder.searchUserWorkArea.setText(searchMatchesItemPojo.getSearchUserWorkArea());
        myViewholder.searchUserCity.setText(searchMatchesItemPojo.getSearchUserCity());
        myViewholder.searchUserCountry.setText(searchMatchesItemPojo.getSearchUserCountry());
        myViewholder.searchUserEmail.setText(searchMatchesItemPojo.getSearchUserEmail());
        //myViewholder.searchUserImage.setImageResource(searchMatchesItemPojo.getSearchUserImage());
        if(searchMatchesItemPojo.getSearchUserImage()!=null) {
            Glide.with(context).load(searchMatchesItemPojo.getSearchUserImage()).into(myViewholder.searchUserImage);
        }

        myViewholder.searchUserImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchMatchesItemPojo searchMatchesItemPojo = searchMatchesItemPojoLists.get(position);
                String dotReceiverEmail = searchMatchesItemPojo.getSearchUserEmail();
                Log.v("Hungama",dotReceiverEmail);
                Intent intent = new Intent(context, UserDetailOnClick.class);
                intent.putExtra(EXTRA_USERE,dotReceiverEmail);
                context.startActivity(intent);
            }
        });



    }


    @Override
    public int getItemCount() {
        return searchMatchesItemPojoLists.size();
    }

    static class MyViewholder extends RecyclerView.ViewHolder {

        TextView searchUserName;
        TextView searchUserAge;
        TextView searchUserHeight;
        TextView searchUserLanguage;
        TextView searchUserReligion;
        TextView searchUserWorkArea;
        TextView searchUserCity;
        TextView searchUserCountry;
        TextView searchUserEmail;
        ImageView searchUserImage;
        Button sendInterest;
        Button interestSent;


        public MyViewholder(@NonNull View itemView) {
            super(itemView);

            searchUserName = itemView.findViewById(R.id.search_user_name);
            searchUserAge = itemView.findViewById(R.id.search_user_age);
            searchUserHeight = itemView.findViewById(R.id.search_user_height);
            searchUserLanguage = itemView.findViewById(R.id.search_user_language);
            searchUserReligion = itemView.findViewById(R.id.search_user_religion);
            searchUserWorkArea = itemView.findViewById(R.id.search_user_workarea);
            searchUserCity = itemView.findViewById(R.id.search_user_city);
            searchUserCountry = itemView.findViewById(R.id.search_user_country);
            searchUserImage = itemView.findViewById(R.id.search_user_image);
            searchUserEmail = itemView.findViewById(R.id.search_user_email);
            sendInterest = itemView.findViewById(R.id.send_interest_button);
            interestSent = itemView.findViewById(R.id.interest_sent_button);


        }


    }
}
