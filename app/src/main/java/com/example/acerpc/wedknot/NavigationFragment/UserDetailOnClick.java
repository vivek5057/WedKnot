package com.example.acerpc.wedknot.NavigationFragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.acerpc.wedknot.InitialForms.AllFormDetailsPojo;
import com.example.acerpc.wedknot.InitialForms.FamilyDetailsInitialPojo;
import com.example.acerpc.wedknot.InitialForms.PartnerPreferencesInitialPojo;
import com.example.acerpc.wedknot.MyWedknotInside.LifeStylePojo;
import com.example.acerpc.wedknot.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static com.example.acerpc.wedknot.NavigationFragment.RecentlyJoinedRecyclerViewAdapter.EXTRA_USERE;

public class UserDetailOnClick extends AppCompatActivity {
    
    ImageView userDetailImage;
    TextView userName;
    TextView userAge;
    TextView userHeight;
    TextView userLanguage;
    TextView userReligion;
    TextView userProfession;
    TextView userCity;
    TextView userCountry;
    TextView userAboutName;
    TextView userAbout;
    TextView userContactNo;
    TextView userEmail;
    TextView userLifeStyleAge;
    TextView userLifeStyleHeight;
    TextView userLifeStyleMarriageStatus;
    TextView userLifeStyleCity;
    TextView userLifeStyleState;
    TextView userLifeStyleCountry;
    TextView userLifeStyleDiet;
    TextView userLifeStyleDrink;
    TextView userLifeStyleSmoke;
    TextView userLifeStyleSkinColor;
    TextView userLifeStyleBodyType;
    TextView userEducation;
    TextView userIncome;
    TextView userFamilyFatherO;
    TextView userFamilyMotherO;
    TextView userFamilyBrother;
    TextView userFamilySister;
    TextView userFamilyIncome;
    TextView userFamilyStatus;
    TextView userFamilyType;
    TextView userFamilyLivingWithParents;
    TextView userPartnerPrefAge;
    TextView userPartnerPrefHeight;
    TextView userPartnerPrefMaritalStatus;
    TextView userPartnerPrefReligion;
    TextView userPartnerPrefLanguage;
    TextView userPartnerPrefCountryLivingIn;
    TextView userPartnerPrefEducation;
    TextView userPartnerPrefAnnualIncome;
    String userDetail;

    FirebaseUser firebaseUser;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_detail_on_click);
        getSupportActionBar().hide();
        Intent intent = getIntent();
        userDetail = intent.getStringExtra(EXTRA_USERE);

        init();
        readData();


    }

    public void readData(){
       
                databaseReference.child(userDetail).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        AllFormDetailsPojo allFormDetailsPojo = dataSnapshot.getValue(AllFormDetailsPojo.class);
                         String fetchedUserName = allFormDetailsPojo.loginDetailsFullName;
                         userName.setText(fetchedUserName);
                         userAboutName.setText(fetchedUserName);
                         String fetchedUserAge = allFormDetailsPojo.personalDetailsAge;
                         userAge.setText(fetchedUserAge);
                         String fetchedUserHeight = allFormDetailsPojo.personalDetailsHeight;
                         userHeight.setText(fetchedUserHeight);
                         String fetchedUserMotherTongue = allFormDetailsPojo.socialDetailsMotherTongue;
                         userLanguage.setText(fetchedUserMotherTongue);
                         String fetchedUserReligion = allFormDetailsPojo.socialDetailsReligion;
                         userReligion.setText(fetchedUserReligion);
                         String fetchedWorkArea = allFormDetailsPojo.careerDetailsWorkArea;
                         userProfession.setText(fetchedWorkArea);
                         String fetchedUserCity = allFormDetailsPojo.personalDetailsCity;
                         userCity.setText(fetchedUserCity);
                         String fetchedUserCountry = allFormDetailsPojo.personalDetailsCountry;
                         userCountry.setText(fetchedUserCountry);
                         String fetchedMaritalStatusThis = allFormDetailsPojo.socialDetailsMartialStatus;
                         userLifeStyleMarriageStatus.setText(fetchedMaritalStatusThis);
                         String fetchedStateThis = allFormDetailsPojo.personalDetailsState;
                         userLifeStyleState.setText(fetchedStateThis);
                         String fetchedUserEmail = allFormDetailsPojo.loginDetailsEmailId;
                         userEmail.setText(fetchedUserEmail);
                         String fetchedUserMobile = allFormDetailsPojo.loginDetailsPhoneNo;
                         userContactNo.setText(fetchedUserMobile);
                         String fetchedUserIncome = allFormDetailsPojo.careerDetailsAnnualIncome;
                         userIncome.setText(fetchedUserIncome);
                         String fetchedUserEducation = allFormDetailsPojo.careerDetailsHighestEducation;
                         userEducation.setText(fetchedUserEducation);
                        
                        
                        
                        databaseReference.child(userDetail).child("FamilyDetails").addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                FamilyDetailsInitialPojo familyDetailsInitialPojo = dataSnapshot.getValue(FamilyDetailsInitialPojo.class);
                                 String fetchedFatherOccupation = familyDetailsInitialPojo.familyfatherOccupation;
                                 userFamilyFatherO.setText(fetchedFatherOccupation);
                                 String fetchedMotherOccupation = familyDetailsInitialPojo.familymotherOccupation;
                                 userFamilyMotherO.setText(fetchedMotherOccupation);
                                 String fetchedBrothers = familyDetailsInitialPojo.familybrothers;
                                 userFamilyBrother.setText(fetchedBrothers);
                                 String fetchedSisters = familyDetailsInitialPojo.familysisters;
                                 userFamilySister.setText(fetchedSisters);
                                 String fetchedFamilyIncome = familyDetailsInitialPojo.familyIncome;
                                 userFamilyIncome.setText(fetchedFamilyIncome);
                                 String fetchedFamilyStatus = familyDetailsInitialPojo.familyStatus;
                                 userFamilyStatus.setText(fetchedFamilyStatus);
                                 String fetchedFamilyType = familyDetailsInitialPojo.familyTypeSpinner;
                                 userFamilyType.setText(fetchedFamilyType);
                                 String fetchedLivingWithParents = familyDetailsInitialPojo.familylivingWithParents;
                                 userFamilyLivingWithParents.setText(fetchedLivingWithParents);

                                databaseReference.child(userDetail).child("PartnerPreferences").addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                         PartnerPreferencesInitialPojo partnerPreferencesInitialPojo = dataSnapshot.getValue(PartnerPreferencesInitialPojo.class);
                                         String fetchedMinAge = partnerPreferencesInitialPojo.minAge;
                                         userPartnerPrefAge.setText(fetchedMinAge);
                                         String fetchedMaxAge = partnerPreferencesInitialPojo.maxAge;
                                         String fetchedMinHeight = partnerPreferencesInitialPojo.minHeight;
                                         String fetchedMaxHeight = partnerPreferencesInitialPojo.maxHeight;
                                         userPartnerPrefAge.setText(fetchedMaxAge);
                                         String fetchedReligion = partnerPreferencesInitialPojo.religion;
                                         userPartnerPrefReligion.setText(fetchedReligion);
                                         String fetchedLanguage = partnerPreferencesInitialPojo.motherTongue;
                                         userPartnerPrefLanguage.setText(fetchedLanguage);
                                         String fetchedCountry = partnerPreferencesInitialPojo.country;
                                         userPartnerPrefCountryLivingIn.setText(fetchedCountry);
                                         String fetchedIncome = partnerPreferencesInitialPojo.income;
                                         userPartnerPrefAnnualIncome.setText(fetchedIncome);
                                         String fetchedEducation = partnerPreferencesInitialPojo.education;
                                         userPartnerPrefEducation.setText(fetchedEducation);
                                         String fetchedMaritalStatus = partnerPreferencesInitialPojo.maritalStatus;
                                         userPartnerPrefMaritalStatus.setText(fetchedMaritalStatus);

                                        databaseReference.child(userDetail).child("LifeStyleDetail").addValueEventListener(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                LifeStylePojo lifeStylePojo = dataSnapshot.getValue(LifeStylePojo.class);
                                                String fetchedAboutMe = lifeStylePojo.aboutMe;
                                                userAbout.setText(fetchedAboutMe);
                                                String fetchedFood = lifeStylePojo.food;
                                                userLifeStyleDiet.setText(fetchedFood);
                                                String fetchedDrink = lifeStylePojo.drink;
                                                userLifeStyleDrink.setText(fetchedDrink);
                                                String fetchedSmoke = lifeStylePojo.smoke;
                                                userLifeStyleSmoke.setText(fetchedSmoke);
                                                String fetchedBodyType = lifeStylePojo.bodyType;
                                                userLifeStyleBodyType.setText(fetchedBodyType);
                                                String fetchedSkinColor = lifeStylePojo.skinColor;
                                                userLifeStyleSkinColor.setText(fetchedSkinColor);
                                                
                                                databaseReference.child(userDetail).child("Image").addValueEventListener(new ValueEventListener() {
                                                    @Override
                                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                        String fetchedImage = dataSnapshot.getValue(String.class);
                                                        if(fetchedImage==null){
                                                            return;
                                                        }else{
                                                            Glide.with(UserDetailOnClick.this).load(fetchedImage).into(userDetailImage);
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
    
    public void init(){

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();

         userDetailImage=findViewById(R.id.user_detail_image);
         userName=findViewById(R.id.user_detail_name);
         userAge=findViewById(R.id.user_detail_age);
         userHeight=findViewById(R.id.user_detail_height);
         userLanguage=findViewById(R.id.user_detail_language);
         userReligion=findViewById(R.id.user_detail_religion);
         userProfession=findViewById(R.id.user_detail_workarea);
         userCity=findViewById(R.id.user_detail_city);
         userCountry=findViewById(R.id.user_detail_country);
         userAboutName=findViewById(R.id.about_user_detail_name);
         userAbout=findViewById(R.id.about_user_detail_info);
         userContactNo=findViewById(R.id.user_detail_mobile_no);
         userEmail=findViewById(R.id.user_detail_email_id);
         userLifeStyleAge=findViewById(R.id.user_detail_lifestyle_age_partner);
         userLifeStyleHeight=findViewById(R.id.user_detail_lifestyle_height_partner);
         userLifeStyleMarriageStatus=findViewById(R.id.user_detail_marriage_status_partner);
         userLifeStyleCity=findViewById(R.id.user_detail_matches_partner_city);
         userLifeStyleState=findViewById(R.id.user_detail_matches_partner_state);
         userLifeStyleCountry=findViewById(R.id.user_detail_matches_partner_country);
         userLifeStyleDiet=findViewById(R.id.user_detail_food_status_partner);
         userLifeStyleDrink=findViewById(R.id.user_detail_drinks_status_partner);
         userLifeStyleSmoke=findViewById(R.id.user_detail_smoke_status_partner);
         userLifeStyleSkinColor=findViewById(R.id.user_detail_complexion_status_partner);
         userLifeStyleBodyType=findViewById(R.id.user_detail_bodytype_status_partner);
         userEducation=findViewById(R.id.user_detail_education_partner);
         userIncome=findViewById(R.id.user_detail_income_partner);
         userFamilyFatherO=findViewById(R.id.user_detail_matches_father_occupation);
         userFamilyMotherO=findViewById(R.id.user_detail_matches_mothers_occupation);
         userFamilyBrother=findViewById(R.id.user_detail_matches_brothers);
         userFamilySister=findViewById(R.id.user_detail_matches_sisters);
         userFamilyIncome=findViewById(R.id.user_detail_matches_family_income);
         userFamilyStatus=findViewById(R.id.user_detail_matches_family_status);
         userFamilyType=findViewById(R.id.user_detail_matches_family_type);
         userFamilyLivingWithParents=findViewById(R.id.user_detail_matches_living_with_parents);
         userPartnerPrefAge=findViewById(R.id.user_detail_partner_preference_age);
         userPartnerPrefHeight=findViewById(R.id.user_detail_partner_preference_height);
         userPartnerPrefMaritalStatus=findViewById(R.id.user_detail_partner_preference_maritalstatus);
         userPartnerPrefReligion=findViewById(R.id.user_detail_partner_preference_religion);
         userPartnerPrefLanguage=findViewById(R.id.user_detail_partner_preference_mothertongue);
         userPartnerPrefCountryLivingIn=findViewById(R.id.user_detail_partner_preference_country);
         userPartnerPrefEducation=findViewById(R.id.user_detail_partner_preference_education);
         userPartnerPrefAnnualIncome=findViewById(R.id.user_detail_partner_preference_annualincome);


    }
}
