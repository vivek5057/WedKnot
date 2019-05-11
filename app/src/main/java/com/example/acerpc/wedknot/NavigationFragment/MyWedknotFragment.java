package com.example.acerpc.wedknot.NavigationFragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.bumptech.glide.Glide;
import com.example.acerpc.wedknot.InitialForms.AllFormDetailsPojo;
import com.example.acerpc.wedknot.MainActivity;
import com.example.acerpc.wedknot.MyWedknotInside.MyDetailActivity;
import com.example.acerpc.wedknot.MyWedknotInside.MyFamilyActivity;
import com.example.acerpc.wedknot.MyWedknotInside.MyPartnerDetailsActivity;
import com.example.acerpc.wedknot.R;
import com.example.acerpc.wedknot.SearchFloatingbutton.SearchPartnerPreferences;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class MyWedknotFragment extends Fragment {

    private LinearLayout myDetailLayout;
    private LinearLayout myPartnerLayout;
    private LinearLayout myFamilyLayout;
    private FloatingActionButton searchFloatingButton;
    private CircleImageView userProfilePic;
    TextView currentUserNameTextView;
    FirebaseUser currentUser;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    TextView logOut;
    String userEmail;
    Button weekButton;
    Button week1Button;
    Button monthButton;
    Button month1Button;
    String currentUserGender;
    LottieAnimationView animationView;

    SharedPreferences filterRecentlyJoin;
    SharedPreferences.Editor filterEdit;

    View emptyView;
    RecyclerView recyclerView;
    RecentlyJoinedRecyclerViewAdapter adapter;
    LinearLayoutManager linearLayoutManager;
    List<RecentlyJoinedPojo> recentlyJoinedPojos;
    ImageView emptyImage;
    TextView emptyText;

    // To LogOut
    SharedPreferences pref;
    SharedPreferences.Editor edit;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_my_wedknot, container, false);

        recentlyJoinedPojos = new ArrayList<>();
        recyclerView = view.findViewById(R.id.recently_joined_recycler_view);
        linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();

        weekButton = view.findViewById(R.id.week_filter);
        week1Button = view.findViewById(R.id.week1_filter);
        monthButton = view.findViewById(R.id.month_filter);
        month1Button = view.findViewById(R.id.month1_filter);

        animationView = view.findViewById(R.id.animrecent);
        emptyImage = view.findViewById(R.id.empty_image);
        emptyText = view.findViewById(R.id.empty_title_text);

        filterRecentlyJoin = getActivity().getSharedPreferences("recently_joined", Context.MODE_PRIVATE);
        filterEdit = filterRecentlyJoin.edit();

        emptyView = view.findViewById(R.id.recent_join_empty_view);


        week1Button.setEnabled(false);
        monthButton.setEnabled(false);
        weekButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                monthButton.setVisibility(View.VISIBLE);
                week1Button.setVisibility(View.VISIBLE);
                weekButton.setVisibility(View.INVISIBLE);
                month1Button.setVisibility(View.INVISIBLE);
                long day = (1000 * 60 * 60 * 24); // 24 hours in milliseconds
                long mSecInWeek = day * 7;
                recentlyJoinedPojos.clear();
                readRecentlyJoinedAccounts(mSecInWeek);

            }
        });

        monthButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                weekButton.setVisibility(View.VISIBLE);
                month1Button.setVisibility(View.VISIBLE);
                monthButton.setVisibility(View.INVISIBLE);
                week1Button.setVisibility(View.INVISIBLE);
                filterEdit.putBoolean("button", true);
                long day = (1000 * 60 * 60 * 24); // 24 hours in milliseconds
                long mSecInMonth = day * 30;
                recentlyJoinedPojos.clear();
                readRecentlyJoinedAccounts(mSecInMonth);
            }
        });


        pref = getActivity().getSharedPreferences("prefs", Context.MODE_PRIVATE);
        edit = pref.edit();

        logOut = view.findViewById(R.id.log_out_button);
        currentUserNameTextView = view.findViewById(R.id.current_user_name);
        searchFloatingButton = view.findViewById(R.id.search_floating_button);
        searchFloatingButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), SearchPartnerPreferences.class));
            }
        });

        userProfilePic = view.findViewById(R.id.main_user_profile_pic);

        myDetailLayout = view.findViewById(R.id.my_detail_layout);
        //  pref = getActivity().getSharedPreferences("prefs",MODE_PRIVATE);
        //  edit = pref.edit();

        myDetailLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), MyDetailActivity.class));
            }
        });

        myPartnerLayout = view.findViewById(R.id.my_partner_layout);
        myPartnerLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), MyPartnerDetailsActivity.class));
            }
        });

        myFamilyLayout = view.findViewById(R.id.my_family_layout);
        myFamilyLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), MyFamilyActivity.class));
            }
        });

        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edit.putBoolean("permission", false).apply();
                startActivity(new Intent(getActivity(), MainActivity.class));
                getActivity().finish();
            }
        });

        readImageData();
        readCurrentUserData();

        return view;
    }

    public void readImageData() {
        if (currentUser.getEmail().replace(".", "") == null) {
            return;
        } else {
            userEmail = currentUser.getEmail().replace(".", "");
        }

        databaseReference.child(userEmail).child("Image").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (getActivity() == null) {
                    return;
                }
                String imageUri = dataSnapshot.getValue(String.class);

                if (imageUri == null) {
                    return;
                } else {
                    Glide.with(getActivity()).load(imageUri).into(userProfilePic);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getActivity(), "" + databaseError.toException(), Toast.LENGTH_LONG).show();
            }
        });

    }

    public void readCurrentUserData() {

        String userEmail = currentUser.getEmail().replace(".", "");

        databaseReference.child(userEmail).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                AllFormDetailsPojo allFormDetailsPojo = dataSnapshot.getValue(AllFormDetailsPojo.class);
                if (allFormDetailsPojo != null) {
                    String currentUserName = allFormDetailsPojo.loginDetailsFullName;
                    currentUserGender = allFormDetailsPojo.personalDetailsGender;
                    currentUserNameTextView.setText(currentUserName);
                }
                long day = (1000 * 60 * 60 * 24); // 24 hours in milliseconds
                long mSecInWeek = day * 7;
                recentlyJoinedPojos.clear();
                readRecentlyJoinedAccounts(mSecInWeek);
                monthButton.setEnabled(true);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getActivity(), "" + databaseError.toException(), Toast.LENGTH_LONG).show();
            }
        });

    }

    public void readRecentlyJoinedAccounts(long milliSec) {
        long currentTime = System.currentTimeMillis();
        long oneMonthBefore = currentTime - milliSec;

        if (currentUserGender.equals("Male")) {
            databaseReference.child("Female").orderByValue().startAt(oneMonthBefore).endAt(currentTime).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot postSnapShot : dataSnapshot.getChildren()) {
                        final String userEmailKey = postSnapShot.getKey();
                        //    Log.v("Recently Joined:", userEmailKey);

                        databaseReference.child(userEmailKey).child("Image").addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                final String fetchedUserImage = dataSnapshot.getValue(String.class);

                                //Log.v("Image",fetchedUserImage);

                                databaseReference.child(userEmailKey).addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                        AllFormDetailsPojo allFormDetailsPojo = dataSnapshot.getValue(AllFormDetailsPojo.class);
                                        if (allFormDetailsPojo != null) {
                                            String fetchedUserName = allFormDetailsPojo.loginDetailsFullName;
                                            String fetchedUserAge = allFormDetailsPojo.personalDetailsAge;
                                            String fetchedUserHeight = allFormDetailsPojo.personalDetailsHeight;
                                            String fetchedUserMotherTongue = allFormDetailsPojo.socialDetailsMotherTongue;
                                            String fetchedUserReligion = allFormDetailsPojo.socialDetailsReligion;
                                            String fetchedUserCity = allFormDetailsPojo.personalDetailsCity;
                                            String fetchedUserCountry = allFormDetailsPojo.personalDetailsCountry;
                                            String fetchedUserEmail = allFormDetailsPojo.loginDetailsEmailId;


                                            String finalUserEmail = fetchedUserEmail.replace(".", "");

                                            recentlyJoinedPojos.add(new RecentlyJoinedPojo(fetchedUserName, fetchedUserAge, fetchedUserHeight, fetchedUserMotherTongue, fetchedUserReligion, fetchedUserCity, fetchedUserCountry, finalUserEmail, fetchedUserImage));
                                            adapter = new RecentlyJoinedRecyclerViewAdapter(getActivity(), recentlyJoinedPojos);
                                            recyclerView.setAdapter(adapter);


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

                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }


            });


        } else {
            databaseReference.child("Male").orderByValue().startAt(oneMonthBefore).endAt(currentTime).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot postSnapShot : dataSnapshot.getChildren()) {
                        final String userEmailKey = postSnapShot.getKey();
                        //  Log.v("Recently Joined:",userEmailKey);

                        databaseReference.child(userEmailKey).child("Image").addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                final String fetchedUserImage = dataSnapshot.getValue(String.class);

                                databaseReference.child(userEmailKey).addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                        AllFormDetailsPojo allFormDetailsPojo = dataSnapshot.getValue(AllFormDetailsPojo.class);
                                        if (allFormDetailsPojo != null) {
                                            String fetchedUserName = allFormDetailsPojo.loginDetailsFullName;
                                            String fetchedUserAge = allFormDetailsPojo.personalDetailsAge;
                                            String fetchedUserHeight = allFormDetailsPojo.personalDetailsHeight;
                                            String fetchedUserMotherTongue = allFormDetailsPojo.socialDetailsMotherTongue;
                                            String fetchedUserReligion = allFormDetailsPojo.socialDetailsReligion;
                                            String fetchedUserCity = allFormDetailsPojo.personalDetailsCity;
                                            String fetchedUserCountry = allFormDetailsPojo.personalDetailsCountry;
                                            String fetchedUserEmail = allFormDetailsPojo.loginDetailsEmailId;

                                            String finalUserEmail = fetchedUserEmail.replace(".", "");

                                            recentlyJoinedPojos.add(new RecentlyJoinedPojo(fetchedUserName, fetchedUserAge, fetchedUserHeight, fetchedUserMotherTongue, fetchedUserReligion, fetchedUserCity, fetchedUserCountry, finalUserEmail, fetchedUserImage));
                                            adapter = new RecentlyJoinedRecyclerViewAdapter(getActivity(), recentlyJoinedPojos);
                                            recyclerView.setAdapter(adapter);


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


                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });


        }
    }


}
