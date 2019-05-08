package com.example.acerpc.wedknot.InboxFragmentInside;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.example.acerpc.wedknot.InitialForms.AllFormDetailsPojo;
import com.example.acerpc.wedknot.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class InterestSentFragment extends Fragment {

    List<InterestReceivedPojo> sentPojoList;
    RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private InterestSentAdapter adapter;
    View emptyView;
    LottieAnimationView animationView;
    ImageView emptyImage;
    TextView emptyText;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    FirebaseAuth mAuth;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_interest_sent, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.inbox_sent_recycler_view);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        sentPojoList = new ArrayList<>();

        emptyImage = view.findViewById(R.id.empty_image);
        emptyText = view.findViewById(R.id.empty_title_text);
        animationView = view.findViewById(R.id.animsent);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
        mAuth = FirebaseAuth.getInstance();
        emptyView = view.findViewById(R.id.sent_empty_view);

        getData();

        return view;
    }

    public void getData() {
        String dotCurrentUser = mAuth.getCurrentUser().getEmail();
        String currentUser = dotCurrentUser.replace(".", "");

        databaseReference.child("ConnectionRequest").child(currentUser).orderByChild("request_type").equalTo("sent").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                sentPojoList.clear();
                for (DataSnapshot postDataSnapShot : dataSnapshot.getChildren()) {
                    final String requestFromPerson = postDataSnapShot.getKey();
                   // Log.v("Person to Cancel:", requestFromPerson);

                    databaseReference.child(requestFromPerson).child("Image").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            final String personImage = dataSnapshot.getValue(String.class);
                      //      Log.v("Person Image", personImage);

                            databaseReference.child(requestFromPerson).addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                    AllFormDetailsPojo allFormDetailsPojo = dataSnapshot.getValue(AllFormDetailsPojo.class);
                                    if(allFormDetailsPojo!=null) {
                                        String personName = allFormDetailsPojo.loginDetailsFullName;
                                        String personCity = allFormDetailsPojo.personalDetailsCity;
                                        String personCountry = allFormDetailsPojo.personalDetailsCountry;
                                        String personEmail = allFormDetailsPojo.loginDetailsEmailId.replace(".", "");


                                        //  Log.v("Person Data", personName + " " + personCity + " " + personCountry + " " + personEmail);

                                        sentPojoList.add(new InterestReceivedPojo(personName, personCity, personCountry, personImage, personEmail));
                                    }
                                    adapter = new InterestSentAdapter(getActivity(), sentPojoList);
                                    recyclerView.setAdapter(adapter);

                                    if (sentPojoList.isEmpty()) {
                                        recyclerView.setVisibility(View.GONE);
                                        emptyView.setVisibility(View.VISIBLE);
                                    } else {
                                        recyclerView.setVisibility(View.VISIBLE);
                                        emptyView.setVisibility(View.GONE);
                                    }


                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

                    //
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        int count = sentPojoList.size();
        animationView.setVisibility(View.GONE);
        // Log.v("count",count+"");
        if(count==0)

        {
            recyclerView.setVisibility(View.GONE);
            emptyImage.setVisibility(View.VISIBLE);
            emptyText.setVisibility(View.VISIBLE);
            animationView.setVisibility(View.GONE);
        }
        else
        {
            recyclerView.setVisibility(View.VISIBLE);
            emptyView.setVisibility(View.GONE);
        }

    }

}
