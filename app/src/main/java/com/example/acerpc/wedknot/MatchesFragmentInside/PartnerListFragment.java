package com.example.acerpc.wedknot.MatchesFragmentInside;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.example.acerpc.wedknot.InitialForms.AllFormDetailsPojo;
import com.example.acerpc.wedknot.InitialForms.FamilyDetailsInitialPojo;
import com.example.acerpc.wedknot.InitialForms.PartnerPreferencesInitialPojo;
import com.example.acerpc.wedknot.MyWedknotInside.LifeStylePojo;
import com.example.acerpc.wedknot.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class PartnerListFragment extends Fragment {

    ViewPager viewPager;
    View emptyView;
    MatchesProfileSliderAdapter matchesProfileSliderAdapter;
    static List<MatchesPojo> matchesPojos;
    static List<FamilyDetailsInitialPojo> familyDetailsInitialPojos;
    static List<PartnerPreferencesInitialPojo> partnerPreferencesInitialPojos;
    static List<LifeStylePojo> lifeStylePojos;
    FirebaseAuth mAuth;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    String currentUserGender;

    LottieAnimationView animationView;
    ImageView emptyImage;
    TextView emptyText;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_partner_list, container, false);
        viewPager = view.findViewById(R.id.slideprofiles_viewpager);
        matchesPojos = new ArrayList<>();
        familyDetailsInitialPojos = new ArrayList<>();
        partnerPreferencesInitialPojos = new ArrayList<>();
        lifeStylePojos = new ArrayList<>();
        emptyView = view.findViewById(R.id.matches_empty_view);
        mAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
        animationView = emptyView.findViewById(R.id.animmatches);
        emptyImage = view.findViewById(R.id.empty_image);
        emptyText = view.findViewById(R.id.empty_title_text);

        readCurrentUserGender();
        return view;
    }


    public void readCurrentUserGender() {

        String userEmail = mAuth.getCurrentUser().getEmail().replace(".", "");

        databaseReference.child(userEmail).child("personalDetailsGender").addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                currentUserGender = dataSnapshot.getValue(String.class);
//                Log.v("User Emails: ", currentUserGender);
                if (currentUserGender != null) {
                    NewThread newThread = new NewThread();
                    newThread.execute();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        });
    }

    class NewThread extends AsyncTask {


        String fetchedUserName;
        String fetchedUserAge;
        String fetchedUserHeight;
        String fetchedUserMotherTongue;
        String fetchedUserReligion;
        String fetchedWorkArea;
        String fetchedUserCity;
        String fetchedUserCountry;
        String fetchedMaritalStatusThis;
        String fetchedStateThis;
        String fetchedUserEmail;
        String fetchedUserMobile;
        String fetchedUserIncome;
        String fetchedUserEducation;

        String fetchedFatherOccupation;
        String fetchedMotherOccupation;
        String fetchedBrothers;
        String fetchedSisters;
        String fetchedFamilyIncome;
        String fetchedFamilyStatus;
        String fetchedFamilyType;
        String fetchedLivingWithParents;

        String fetchedMinAge;
        String fetchedMaxAge;
        String fetchedMinHeight;
        String fetchedMaxHeight;
        String fetchedReligion;
        String fetchedLanguage;
        String fetchedCountry;
        String fetchedIncome;
        String fetchedEducation;
        String fetchedMaritalStatus;

        String fetchedAboutMe;
        String fetchedFood;
        String fetchedDrink;
        String fetchedSmoke;
        String fetchedBodyType;
        String fetchedSkinColor;


        ProgressDialog progDailog = new ProgressDialog(getActivity());

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progDailog.setMessage("Loading...");
            progDailog.setIndeterminate(false);
            progDailog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progDailog.setCancelable(true);
            progDailog.show();
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
            progDailog.dismiss();
        }

        @Override
        protected Object doInBackground(Object[] objects) {

            if (currentUserGender.equals("Male")) {
                databaseReference.child("Female").addListenerForSingleValueEvent(new ValueEventListener() {

                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                            final String userEmailKey = postSnapshot.getKey();
                            //  Log.v("User Emails: ", userEmailKey);

                            //Image
                            databaseReference.child(userEmailKey).child("Image").addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    final String fetchedUserImage = dataSnapshot.getValue(String.class);
                                    //  Log.v("Fetched Image", fetchedUserImage);

                                    databaseReference.child(userEmailKey).addValueEventListener(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                            AllFormDetailsPojo allFormDetailsPojo = dataSnapshot.getValue(AllFormDetailsPojo.class);
                                            if (allFormDetailsPojo != null) {
                                                fetchedUserName = allFormDetailsPojo.loginDetailsFullName;
                                                fetchedUserAge = allFormDetailsPojo.personalDetailsAge;
                                                fetchedUserHeight = allFormDetailsPojo.personalDetailsHeight;
                                                fetchedUserMotherTongue = allFormDetailsPojo.socialDetailsMotherTongue;
                                                fetchedUserReligion = allFormDetailsPojo.socialDetailsReligion;
                                                fetchedWorkArea = allFormDetailsPojo.careerDetailsWorkArea;
                                                fetchedUserCity = allFormDetailsPojo.personalDetailsCity;
                                                fetchedUserCountry = allFormDetailsPojo.personalDetailsCountry;
                                                fetchedMaritalStatusThis = allFormDetailsPojo.socialDetailsMartialStatus;
                                                fetchedStateThis = allFormDetailsPojo.personalDetailsState;
                                                fetchedUserEmail = allFormDetailsPojo.loginDetailsEmailId;
                                                fetchedUserMobile = allFormDetailsPojo.loginDetailsPhoneNo;
                                                fetchedUserIncome = allFormDetailsPojo.careerDetailsAnnualIncome;
                                                fetchedUserEducation = allFormDetailsPojo.careerDetailsHighestEducation;

                                            }

                                            final String finalFetchedUserName = fetchedUserName;
                                            final String finalFetchedUserAge = fetchedUserAge;
                                            final String finalFetchedUserHeight = fetchedUserHeight;
                                            final String finalFetchedUserMotherTongue = fetchedUserMotherTongue;
                                            final String finalFetchedUserReligion = fetchedUserReligion;
                                            final String finalFetchedWorkArea = fetchedWorkArea;
                                            final String finalFetchedUserCity = fetchedUserCity;
                                            final String finalFetchedUserCountry = fetchedUserCountry;
                                            final String finalFetchedMaritalStatusThis = fetchedMaritalStatusThis;
                                            final String finalFetchedStateThis = fetchedStateThis;
                                            final String finalFetchedUserMobile = fetchedUserMobile;
                                            final String finalFetchedUserIncome = fetchedUserIncome;
                                            final String finalFetchedUserEducation = fetchedUserEducation;
                                            final String finalFetchedUserEmail = fetchedUserEmail;

                                            //final String finalUserEmail = fetchedUserEmail.replace(".", "");


                                            databaseReference.child(userEmailKey).child("FamilyDetails").addValueEventListener(new ValueEventListener() {
                                                @Override
                                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                    FamilyDetailsInitialPojo familyDetailsInitialPojo = dataSnapshot.getValue(FamilyDetailsInitialPojo.class);
                                                    if (familyDetailsInitialPojo != null) {
                                                        fetchedFatherOccupation = familyDetailsInitialPojo.familyfatherOccupation;
                                                        fetchedMotherOccupation = familyDetailsInitialPojo.familymotherOccupation;
                                                        fetchedBrothers = familyDetailsInitialPojo.familybrothers;
                                                        fetchedSisters = familyDetailsInitialPojo.familysisters;
                                                        fetchedFamilyIncome = familyDetailsInitialPojo.familyIncome;
                                                        fetchedFamilyStatus = familyDetailsInitialPojo.familyStatus;
                                                        fetchedFamilyType = familyDetailsInitialPojo.familyTypeSpinner;
                                                        fetchedLivingWithParents = familyDetailsInitialPojo.familylivingWithParents;
                                                    }

                                                    final String finalFetchedFatherOccupation = fetchedFatherOccupation;
                                                    final String finalFetchedMotherOccupation = fetchedMotherOccupation;
                                                    final String finalFetchedBrothers = fetchedBrothers;
                                                    final String finalFetchedSisters = fetchedSisters;
                                                    final String finalFetchedFamilyIncome = fetchedFamilyIncome;
                                                    final String finalFetchedFamilyStatus = fetchedFamilyStatus;
                                                    final String finalFetchedFamilyType = fetchedFamilyType;
                                                    final String finalFetchedLivingWithParents = fetchedLivingWithParents;

                                                    databaseReference.child(userEmailKey).child("PartnerPreferences").addValueEventListener(new ValueEventListener() {
                                                        @Override
                                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                            final PartnerPreferencesInitialPojo partnerPreferencesInitialPojo = dataSnapshot.getValue(PartnerPreferencesInitialPojo.class);
                                                            if (partnerPreferencesInitialPojo != null) {
                                                                fetchedMinAge = partnerPreferencesInitialPojo.minAge;
                                                                fetchedMaxAge = partnerPreferencesInitialPojo.maxAge;
                                                                fetchedMinHeight = partnerPreferencesInitialPojo.minHeight;
                                                                fetchedMaxHeight = partnerPreferencesInitialPojo.maxHeight;
                                                                fetchedReligion = partnerPreferencesInitialPojo.religion;
                                                                fetchedLanguage = partnerPreferencesInitialPojo.motherTongue;
                                                                fetchedCountry = partnerPreferencesInitialPojo.country;
                                                                fetchedIncome = partnerPreferencesInitialPojo.income;
                                                                fetchedEducation = partnerPreferencesInitialPojo.education;
                                                                fetchedMaritalStatus = partnerPreferencesInitialPojo.maritalStatus;
                                                            }

                                                            final String finalFetchedMinAge = fetchedMinAge;
                                                            final String finalFetchedMaxAge = fetchedMaxAge;
                                                            final String finalFetchedMinHeight = fetchedMinHeight;
                                                            final String finalFetchedMaxHeight = fetchedMaxHeight;
                                                            final String finalFetchedReligion = fetchedReligion;
                                                            final String finalFetchedLanguage = fetchedLanguage;
                                                            final String finalFetchedCountry = fetchedCountry;
                                                            final String finalFetchedIncome = fetchedIncome;
                                                            final String finalFetchedEducation = fetchedEducation;
                                                            final String finalFetchedMaritalStatus = fetchedMaritalStatus;

                                                            char[] dividefetchedUserHeight = finalFetchedUserHeight.toCharArray();
                                                            char[] divideUserMinHeight = finalFetchedMinHeight.toCharArray();
                                                            char[] divideUserMaxHeight = finalFetchedMaxHeight.toCharArray();

                                                            String convertHeight = "";
                                                            String convertMinHeight = "";
                                                            String convertMaxHeight = "";

                                                            for (int i = 0; i < 4; i++) {
                                                                convertHeight = convertHeight + dividefetchedUserHeight[i];
                                                                convertMinHeight = convertMinHeight + divideUserMinHeight[i];
                                                                convertMaxHeight = convertMaxHeight + divideUserMaxHeight[i];
                                                            }

                                                            final String finalUserHeight = convertHeight.replace(".", "");
                                                            final String finalUserMinHeight = convertMinHeight.replace(".", "");
                                                            final String finalUserMaxHeight = convertMaxHeight.replace(".", "");


                                                            databaseReference.child(userEmailKey).child("LifeStyleDetail").addValueEventListener(new ValueEventListener() {
                                                                @Override
                                                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                                    LifeStylePojo lifeStylePojo = dataSnapshot.getValue(LifeStylePojo.class);
                                                                    if (lifeStylePojo != null) {
                                                                        fetchedAboutMe = lifeStylePojo.aboutMe;
                                                                        fetchedFood = lifeStylePojo.food;
                                                                        fetchedDrink = lifeStylePojo.drink;
                                                                        fetchedSmoke = lifeStylePojo.smoke;
                                                                        fetchedBodyType = lifeStylePojo.bodyType;
                                                                        fetchedSkinColor = lifeStylePojo.skinColor;
                                                                    }

                                                                    String finalFetchedAboutMe = fetchedAboutMe;
                                                                    String finalFetchedFood = fetchedFood;
                                                                    String finalFetchedDrink = fetchedDrink;
                                                                    String finalFetchedSmoke = fetchedSmoke;
                                                                    String finalFetchedBodyType = fetchedBodyType;
                                                                    String finalFetchedSkinColor = fetchedSkinColor;


                                                                    if (Integer.parseInt(finalFetchedUserAge) >= Integer.parseInt(finalFetchedMinAge) && Integer.parseInt(finalFetchedUserAge) <= Integer.parseInt(finalFetchedMaxAge)) {
                                                                        if (Integer.parseInt(finalUserHeight) >= Integer.parseInt(finalUserMinHeight) && Integer.parseInt(finalUserHeight) <= Integer.parseInt(finalUserMaxHeight)) {
                                                                            if (finalFetchedUserReligion.equals(finalFetchedReligion)) {
                                                                                if (finalFetchedUserMotherTongue.equals(finalFetchedLanguage)) {
                                                                                    if (finalFetchedUserCountry.equals(finalFetchedCountry)) {

                                                                                        //Log.v("finalFetched Data", finalFetchedUserName + " " + finalFetchedUserAge + " " + finalFetchedUserHeight + " " + finalFetchedUserMotherTongue + " " + finalFetchedUserReligion + " " + finalFetchedWorkArea + " " + finalFetchedUserCity + " " + finalFetchedUserCountry + " " + fetchedUserImage);

                                                                                        matchesPojos.add(new MatchesPojo(finalFetchedUserName, fetchedUserImage, finalFetchedUserAge, finalFetchedUserHeight, finalFetchedUserMotherTongue, finalFetchedUserReligion, finalFetchedWorkArea, finalFetchedUserCity, finalFetchedUserCountry, finalFetchedUserMobile, finalFetchedUserEmail, finalFetchedMaritalStatusThis, finalFetchedStateThis, finalFetchedUserEducation, finalFetchedUserIncome));
                                                                                        partnerPreferencesInitialPojos.add(new PartnerPreferencesInitialPojo(finalFetchedMinAge, finalFetchedMaxAge, finalFetchedMinHeight, finalFetchedMaxHeight, finalFetchedReligion, finalFetchedLanguage, finalFetchedCountry, finalFetchedIncome, finalFetchedEducation, finalFetchedMaritalStatus));
                                                                                        lifeStylePojos.add(new LifeStylePojo(finalFetchedAboutMe, finalFetchedFood, finalFetchedDrink, finalFetchedSmoke, finalFetchedSkinColor, finalFetchedBodyType));
                                                                                        familyDetailsInitialPojos.add(new FamilyDetailsInitialPojo(finalFetchedFatherOccupation, finalFetchedMotherOccupation, finalFetchedBrothers, finalFetchedSisters, finalFetchedFamilyIncome, finalFetchedFamilyStatus, finalFetchedFamilyType, finalFetchedLivingWithParents));
                                                                                        matchesProfileSliderAdapter = new MatchesProfileSliderAdapter(getActivity(), matchesPojos, familyDetailsInitialPojos, partnerPreferencesInitialPojos, lifeStylePojos);
                                                                                        viewPager.setAdapter(matchesProfileSliderAdapter);


                                                                                    }
                                                                                }
                                                                            }
                                                                        }
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

                                                @Override
                                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                                }
                                            });

                                        }

                                        //UserDetailsCancelled
                                        @Override
                                        public void onCancelled(@NonNull DatabaseError databaseError) {

                                        }
                                    });

                                }

                                //ImageCancelled
                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });

                            //EXIT
                        }
                    }

                    //UserEmailKeyCancelled
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            } else {
                databaseReference.child("Male").addValueEventListener(new ValueEventListener() {

                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        matchesPojos.clear();
                        lifeStylePojos.clear();
                        familyDetailsInitialPojos.clear();
                        partnerPreferencesInitialPojos.clear();
                        for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                            final String userEmailKey = postSnapshot.getKey();
                            //    Log.v("User Emails: ", userEmailKey);

                            //Image
                            databaseReference.child(userEmailKey).child("Image").addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    final String fetchedUserImage = dataSnapshot.getValue(String.class);
                                    //    Log.v("Fetched Image", fetchedUserImage);

                                    databaseReference.child(userEmailKey).addValueEventListener(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                            AllFormDetailsPojo allFormDetailsPojo = dataSnapshot.getValue(AllFormDetailsPojo.class);
                                            if (allFormDetailsPojo != null) {
                                                fetchedUserName = allFormDetailsPojo.loginDetailsFullName;
                                                fetchedUserAge = allFormDetailsPojo.personalDetailsAge;
                                                fetchedUserHeight = allFormDetailsPojo.personalDetailsHeight;
                                                fetchedUserMotherTongue = allFormDetailsPojo.socialDetailsMotherTongue;
                                                fetchedUserReligion = allFormDetailsPojo.socialDetailsReligion;
                                                fetchedWorkArea = allFormDetailsPojo.careerDetailsWorkArea;
                                                fetchedUserCity = allFormDetailsPojo.personalDetailsCity;
                                                fetchedUserCountry = allFormDetailsPojo.personalDetailsCountry;
                                                fetchedMaritalStatusThis = allFormDetailsPojo.socialDetailsMartialStatus;
                                                fetchedStateThis = allFormDetailsPojo.personalDetailsState;
                                                fetchedUserEmail = allFormDetailsPojo.loginDetailsEmailId;
                                                fetchedUserMobile = allFormDetailsPojo.loginDetailsPhoneNo;
                                                fetchedUserIncome = allFormDetailsPojo.careerDetailsAnnualIncome;
                                                fetchedUserEducation = allFormDetailsPojo.careerDetailsHighestEducation;

                                            }

                                            final String finalFetchedUserName = fetchedUserName;
                                            final String finalFetchedUserAge = fetchedUserAge;
                                            final String finalFetchedUserHeight = fetchedUserHeight;
                                            final String finalFetchedUserMotherTongue = fetchedUserMotherTongue;
                                            final String finalFetchedUserReligion = fetchedUserReligion;
                                            final String finalFetchedWorkArea = fetchedWorkArea;
                                            final String finalFetchedUserCity = fetchedUserCity;
                                            final String finalFetchedUserCountry = fetchedUserCountry;
                                            final String finalFetchedMaritalStatusThis = fetchedMaritalStatusThis;
                                            final String finalFetchedStateThis = fetchedStateThis;
                                            final String finalFetchedUserMobile = fetchedUserMobile;
                                            final String finalFetchedUserIncome = fetchedUserIncome;
                                            final String finalFetchedUserEducation = fetchedUserEducation;
                                            final String finalFetchedUserEmail = fetchedUserEmail;

                                            // final String finalUserEmail = fetchedUserEmail.replace(".", "");

                                            databaseReference.child(userEmailKey).child("FamilyDetails").addValueEventListener(new ValueEventListener() {
                                                @Override
                                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                    FamilyDetailsInitialPojo familyDetailsInitialPojo = dataSnapshot.getValue(FamilyDetailsInitialPojo.class);
                                                    if (familyDetailsInitialPojo != null) {
                                                        fetchedFatherOccupation = familyDetailsInitialPojo.familyfatherOccupation;
                                                        fetchedMotherOccupation = familyDetailsInitialPojo.familymotherOccupation;
                                                        fetchedBrothers = familyDetailsInitialPojo.familybrothers;
                                                        fetchedSisters = familyDetailsInitialPojo.familysisters;
                                                        fetchedFamilyIncome = familyDetailsInitialPojo.familyIncome;
                                                        fetchedFamilyStatus = familyDetailsInitialPojo.familyStatus;
                                                        fetchedFamilyType = familyDetailsInitialPojo.familyTypeSpinner;
                                                        fetchedLivingWithParents = familyDetailsInitialPojo.familylivingWithParents;
                                                    }


                                                    final String finalFetchedFatherOccupation = fetchedFatherOccupation;
                                                    final String finalFetchedMotherOccupation = fetchedMotherOccupation;
                                                    final String finalFetchedBrothers = fetchedBrothers;
                                                    final String finalFetchedSisters = fetchedSisters;
                                                    final String finalFetchedFamilyIncome = fetchedFamilyIncome;
                                                    final String finalFetchedFamilyStatus = fetchedFamilyStatus;
                                                    final String finalFetchedFamilyType = fetchedFamilyType;
                                                    final String finalFetchedLivingWithParents = fetchedLivingWithParents;


                                                    databaseReference.child(userEmailKey).child("PartnerPreferences").addValueEventListener(new ValueEventListener() {
                                                        @Override
                                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                            PartnerPreferencesInitialPojo partnerPreferencesInitialPojo = dataSnapshot.getValue(PartnerPreferencesInitialPojo.class);
                                                            if (partnerPreferencesInitialPojo != null) {
                                                                fetchedMinAge = partnerPreferencesInitialPojo.minAge;
                                                                fetchedMaxAge = partnerPreferencesInitialPojo.maxAge;
                                                                fetchedMinHeight = partnerPreferencesInitialPojo.minHeight;
                                                                fetchedMaxHeight = partnerPreferencesInitialPojo.maxHeight;
                                                                fetchedReligion = partnerPreferencesInitialPojo.religion;
                                                                fetchedLanguage = partnerPreferencesInitialPojo.motherTongue;
                                                                fetchedCountry = partnerPreferencesInitialPojo.country;
                                                                fetchedIncome = partnerPreferencesInitialPojo.income;
                                                                fetchedEducation = partnerPreferencesInitialPojo.education;
                                                                fetchedMaritalStatus = partnerPreferencesInitialPojo.maritalStatus;
                                                                Log.d("Height",""+fetchedReligion);
                                                                Log.d("Height",""+fetchedMaxAge);
                                                            }

                                                            final String finalFetchedMinAge = fetchedMinAge;
                                                            final String finalFetchedMaxAge = fetchedMaxAge;
                                                            final String finalFetchedMinHeight = fetchedMinHeight;
                                                            final String finalFetchedMaxHeight = fetchedMaxHeight;
                                                            final String finalFetchedReligion = fetchedReligion;
                                                            final String finalFetchedLanguage = fetchedLanguage;
                                                            final String finalFetchedCountry = fetchedCountry;
                                                            final String finalFetchedIncome = fetchedIncome;
                                                            final String finalFetchedEducation = fetchedEducation;
                                                            final String finalFetchedMaritalStatus = fetchedMaritalStatus;


                                                            char[] dividefetchedUserHeight = finalFetchedUserHeight.toCharArray();
                                                            char[] divideUserMinHeight = finalFetchedMinHeight.toCharArray();
                                                            char[] divideUserMaxHeight = finalFetchedMaxHeight.toCharArray();

                                                            String convertHeight = "";
                                                            String convertMinHeight = "";
                                                            String convertMaxHeight = "";

                                                            for (int i = 0; i < 4; i++) {
                                                                convertHeight = convertHeight + dividefetchedUserHeight[i];
                                                                convertMinHeight = convertMinHeight + divideUserMinHeight[i];
                                                                convertMaxHeight = convertMaxHeight + divideUserMaxHeight[i];
                                                            }

                                                            String finalUserHeight = convertHeight.replace(".", "");
                                                            String finalUserMinHeight = convertMinHeight.replace(".", "");
                                                            String finalUserMaxHeight = convertMaxHeight.replace(".", "");

                                                            final int ffinalhieght = Integer.parseInt(finalUserHeight);
                                                            final int ffinalminhieght = Integer.parseInt(finalUserMinHeight);
                                                            final int ffinalmaxhieght = Integer.parseInt(finalUserMaxHeight);


                                                            databaseReference.child(userEmailKey).child("LifeStyleDetail").addValueEventListener(new ValueEventListener() {
                                                                @Override
                                                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                                    LifeStylePojo lifeStylePojo = dataSnapshot.getValue(LifeStylePojo.class);
                                                                    if (lifeStylePojo != null) {
                                                                        fetchedAboutMe = lifeStylePojo.aboutMe;
                                                                        fetchedFood = lifeStylePojo.food;
                                                                        fetchedDrink = lifeStylePojo.drink;
                                                                        fetchedSmoke = lifeStylePojo.smoke;
                                                                        fetchedBodyType = lifeStylePojo.bodyType;
                                                                        fetchedSkinColor = lifeStylePojo.skinColor;
                                                                    }

                                                                    String finalFetchedAboutMe = fetchedAboutMe;
                                                                    String finalFetchedFood = fetchedFood;
                                                                    String finalFetchedDrink = fetchedDrink;
                                                                    String finalFetchedSmoke = fetchedSmoke;
                                                                    String finalFetchedBodyType = fetchedBodyType;
                                                                    String finalFetchedSkinColor = fetchedSkinColor;


                                                                    if (Integer.parseInt(finalFetchedUserAge) >= Integer.parseInt(finalFetchedMinAge) && Integer.parseInt(finalFetchedUserAge) <= Integer.parseInt(finalFetchedMaxAge)) {
                                                                        if (ffinalhieght >= ffinalminhieght && ffinalhieght <= ffinalmaxhieght) {
                                                                            if (finalFetchedUserReligion.equals(finalFetchedReligion)) {
                                                                                if (finalFetchedUserMotherTongue.equals(finalFetchedLanguage)) {
                                                                                    if (finalFetchedUserCountry.equals(finalFetchedCountry)) {

                                                                                        //      Log.v("finalFetched Data", finalFetchedUserName + " " + finalFetchedUserAge + " " + finalFetchedUserHeight + " " + finalFetchedUserMotherTongue + " " + finalFetchedUserReligion + " " + finalFetchedWorkArea + " " + finalFetchedUserCity + " " + finalFetchedUserCountry + " " + finalFetchedUserImage);

                                                                                        matchesPojos.add(new MatchesPojo(finalFetchedUserName, fetchedUserImage, finalFetchedUserAge, finalFetchedUserHeight, finalFetchedUserMotherTongue, finalFetchedUserReligion, finalFetchedWorkArea, finalFetchedUserCity, finalFetchedUserCountry, finalFetchedUserMobile, finalFetchedUserEmail, finalFetchedMaritalStatusThis, finalFetchedStateThis, finalFetchedUserEducation, finalFetchedUserIncome));
                                                                                        partnerPreferencesInitialPojos.add(new PartnerPreferencesInitialPojo(finalFetchedMinAge, finalFetchedMaxAge, finalFetchedMinHeight, finalFetchedMaxHeight, finalFetchedReligion, finalFetchedLanguage, finalFetchedCountry, finalFetchedIncome, finalFetchedEducation, finalFetchedMaritalStatus));
                                                                                        lifeStylePojos.add(new LifeStylePojo(finalFetchedAboutMe, finalFetchedFood, finalFetchedDrink, finalFetchedSmoke, finalFetchedSkinColor, finalFetchedBodyType));
                                                                                        familyDetailsInitialPojos.add(new FamilyDetailsInitialPojo(finalFetchedFatherOccupation, finalFetchedMotherOccupation, finalFetchedBrothers, finalFetchedSisters, finalFetchedFamilyIncome, finalFetchedFamilyStatus, finalFetchedFamilyType, finalFetchedLivingWithParents));
                                                                                        matchesProfileSliderAdapter = new MatchesProfileSliderAdapter(getActivity(), matchesPojos, familyDetailsInitialPojos, partnerPreferencesInitialPojos, lifeStylePojos);
                                                                                        viewPager.setAdapter(matchesProfileSliderAdapter);

                                                                                    }
                                                                                }
                                                                            }
                                                                        }
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

                                                @Override
                                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                                }
                                            });

                                            //Log.v("Fetched Age", Integer.parseInt(fetchedUserAge) + " min" + Integer.parseInt(minAge.getSelectedItem().toString()) + " max" + Integer.parseInt(maxAge.getSelectedItem().toString()));
                                            //Log.v("Fetched Height", Integer.parseInt(finalUserHeight) + " min" + Integer.parseInt(finalUserMinHeight) + " max" + Integer.parseInt(finalUserMaxHeight));

                                        }

                                        //UserDetailsCancelled
                                        @Override
                                        public void onCancelled(@NonNull DatabaseError databaseError) {

                                        }
                                    });

                                }

                                //ImageCancelled
                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });

                            //EXIT
                        }
                    }

                    //UserEmailKeyCancelled
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }


                });


            }

            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    animationView.setVisibility(View.GONE);


                }
            });



            return null;
        }
    }

}
