package com.example.acerpc.wedknot.InboxFragmentInside;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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

public class InboxConnectedFragment extends Fragment {

    List<InterestReceivedPojo> connectedPojoList;
    RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private InboxConnectedAdapter adapter;
    View emptyView;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    FirebaseAuth mAuth;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_inbox_connected, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.inbox_connected_recycler_view);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        connectedPojoList = new ArrayList<>();

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
        mAuth = FirebaseAuth.getInstance();
        emptyView = view.findViewById(R.id.connection_empty_view);

        getData();

        return view;
    }


    public void getData() {
        String dotCurrentUser = mAuth.getCurrentUser().getEmail();
        String currentUser = dotCurrentUser.replace(".", "");

        databaseReference.child("ConnectionSuccessful").child(currentUser).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                connectedPojoList.clear();
                for (DataSnapshot postDataSnapShot : dataSnapshot.getChildren()) {
                    final String requestFromPerson = postDataSnapShot.getKey();
                 //   Log.v("Person Requesting:", requestFromPerson);

                    databaseReference.child(requestFromPerson).child("Image").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            final String personImage = dataSnapshot.getValue(String.class);
                         //   Log.v("Person Image", personImage);

                            databaseReference.child(requestFromPerson).addValueEventListener(new ValueEventListener() {
                                @Override

                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    AllFormDetailsPojo allFormDetailsPojo = dataSnapshot.getValue(AllFormDetailsPojo.class);
                                    if(allFormDetailsPojo!=null) {
                                        String personName = allFormDetailsPojo.loginDetailsFullName;
                                        String personCity = allFormDetailsPojo.personalDetailsCity;
                                        String personCountry = allFormDetailsPojo.personalDetailsCountry;
                                        String personEmail = allFormDetailsPojo.loginDetailsEmailId.replace(".", "");

                                        //   Log.v("Person Data", personName + " " + personCity + " " + personCountry + " " + personEmail);

                                        connectedPojoList.add(new InterestReceivedPojo(personName, personCity, personCountry, personImage, personEmail));
                                    }
                                    adapter = new InboxConnectedAdapter(getActivity(), connectedPojoList);
                                    recyclerView.setAdapter(adapter);

                                    if (connectedPojoList.isEmpty()) {
                                        recyclerView.setVisibility(View.GONE);
                                        emptyView.setVisibility(View.VISIBLE);
                                    }
                                    else {
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
    }

}
