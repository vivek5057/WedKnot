package com.example.acerpc.wedknot.MatchesFragmentInside;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
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
import com.example.acerpc.wedknot.InitialForms.FamilyDetailsInitialPojo;
import com.example.acerpc.wedknot.InitialForms.PartnerPreferencesInitialPojo;
import com.example.acerpc.wedknot.MyWedknotInside.LifeStylePojo;
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

public class MatchesProfileSliderAdapter extends PagerAdapter {


    Context context;
    List<MatchesPojo> matchesPojoList;
    List<FamilyDetailsInitialPojo> familyDetailsInitialPojoList;
    List<PartnerPreferencesInitialPojo> partnerPreferencesInitialPojoList;
    List<LifeStylePojo> lifeStylePojoList;
    View view;


    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    FirebaseAuth mAuth;


    public MatchesProfileSliderAdapter(Context context, List<MatchesPojo> matchesPojoList, List<FamilyDetailsInitialPojo> familyDetailsInitialPojoList, List<PartnerPreferencesInitialPojo> partnerPreferencesInitialPojoList, List<LifeStylePojo> lifeStylePojoList) {
        this.context = context;
        this.matchesPojoList = matchesPojoList;
        this.familyDetailsInitialPojoList = familyDetailsInitialPojoList;
        this.partnerPreferencesInitialPojoList = partnerPreferencesInitialPojoList;
        this.lifeStylePojoList = lifeStylePojoList;

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
        mAuth = FirebaseAuth.getInstance();

    }

    String[] username_text = {"aditi5057", "komaljalebi", "iampayal", "simuu199X", "dubey007"};
    Integer[] about_user_info = {R.string.aboutme, R.string.aboutme, R.string.aboutme, R.string.aboutme, R.string.aboutme};

    @Override
    public int getCount() {
        return matchesPojoList.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }


    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, final int position) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.matches_customlist_item, container, false);
        //
        ImageView imageid = (ImageView) view.findViewById(R.id.user_image);
        if (matchesPojoList.get(position).getPartnerImage() != null) {
            Glide.with(context).load(matchesPojoList.get(position).getPartnerImage()).into(imageid);
        }

        TextView username = view.findViewById(R.id.user_name);
        username.setText(matchesPojoList.get(position).getPartnerName());

        TextView aboutusername = view.findViewById(R.id.about_user_name);
        aboutusername.setText(matchesPojoList.get(position).getPartnerName());

        TextView userage = view.findViewById(R.id.user_age);
        userage.setText(matchesPojoList.get(position).getPartnerAge());

        TextView lifeuserage = view.findViewById(R.id.lifestyle_age_partner);
        lifeuserage.setText(matchesPojoList.get(position).getPartnerName());

        TextView userheight = view.findViewById(R.id.user_height);
        userheight.setText(matchesPojoList.get(position).getPartnerHeight());

        TextView lifestyleuserheight = view.findViewById(R.id.lifestyle_height_partner);
        lifestyleuserheight.setText(matchesPojoList.get(position).getPartnerHeight());

        TextView userlanguage = view.findViewById(R.id.user_language);
        userlanguage.setText(matchesPojoList.get(position).getPartnerLanguage());

        TextView partner_preference_mothertongue = view.findViewById(R.id.partner_preference_mothertongue);
        partner_preference_mothertongue.setText(partnerPreferencesInitialPojoList.get(position).getMotherTongue());

        TextView userreligion = view.findViewById(R.id.user_religion);
        userreligion.setText(matchesPojoList.get(position).getPartnerReligion());

        TextView partner_preference_religion = view.findViewById(R.id.partner_preference_religion);
        partner_preference_religion.setText(partnerPreferencesInitialPojoList.get(position).getReligion());

        TextView userworkarea = view.findViewById(R.id.user_workarea);
        userworkarea.setText(matchesPojoList.get(position).getPartnerWorkArea());
//
        TextView partner_work_area = view.findViewById(R.id.partner_work_area);
        partner_work_area.setText(matchesPojoList.get(position).getPartnerWorkArea());

        TextView usercity = view.findViewById(R.id.user_city);
        usercity.setText(matchesPojoList.get(position).getPartnerCity());

        TextView matches__partner_city = view.findViewById(R.id.matches__partner_city);
        matches__partner_city.setText(matchesPojoList.get(position).getPartnerCity());

        TextView usercountry = view.findViewById(R.id.user_country);
        usercountry.setText(matchesPojoList.get(position).getPartnerCountry());

        TextView matches_partner_country = view.findViewById(R.id.matches_partner_country);
        matches_partner_country.setText(matchesPojoList.get(position).getPartnerCountry());

        TextView partner_preference_country = view.findViewById(R.id.partner_preference_country);
        partner_preference_country.setText(partnerPreferencesInitialPojoList.get(position).getCountry());

        TextView usernametext = view.findViewById(R.id.username_text);
        usernametext.setText(username_text[position]);

        TextView aboutuserinfo = view.findViewById(R.id.about_user_info);
        aboutuserinfo.setText(about_user_info[position]);

        TextView mobileno = view.findViewById(R.id.mobile_no);
        mobileno.setText(matchesPojoList.get(position).getPartnerMobile());

        TextView emailid = view.findViewById(R.id.email_id);
        emailid.setText(matchesPojoList.get(position).getPartnerEmail());

        TextView marriagestatuspartner = view.findViewById(R.id.marriage_status_partner);
        marriagestatuspartner.setText(matchesPojoList.get(position).getPartnerMaritalStatus());

        TextView partner_preference_maritalstatus = view.findViewById(R.id.partner_preference_maritalstatus);
        partner_preference_maritalstatus.setText(partnerPreferencesInitialPojoList.get(position).getMaritalStatus());

        TextView matchespartnerstate = view.findViewById(R.id.matches__partner_state);
        matchespartnerstate.setText(matchesPojoList.get(position).getPartnerState());

        TextView foodstatuspartner = view.findViewById(R.id.food_status_partner);
        foodstatuspartner.setText(lifeStylePojoList.get(position).getFood());

        TextView drinkstatuspartner = view.findViewById(R.id.drinks_status_partner);
        drinkstatuspartner.setText(lifeStylePojoList.get(position).getDrink());

        TextView smokestatuspartner = view.findViewById(R.id.smoke_status_partner);
        smokestatuspartner.setText(lifeStylePojoList.get(position).getSmoke());

        TextView complexionstatuspartner = view.findViewById(R.id.complexion_status_partner);
        complexionstatuspartner.setText(lifeStylePojoList.get(position).getSkinColor());

        TextView bodytypestatuspartner = view.findViewById(R.id.bodytype_status_partner);
        bodytypestatuspartner.setText(lifeStylePojoList.get(position).getBodyType());

        TextView educationpartner = view.findViewById(R.id.education_partner);
        educationpartner.setText(matchesPojoList.get(position).getPartnerEducation());

        TextView incomepartner = view.findViewById(R.id.income_partner);
        incomepartner.setText(matchesPojoList.get(position).getPartnerIncome());
        //
        TextView partnerpreferenceage = view.findViewById(R.id.partner_preference_age);
        partnerpreferenceage.setText(partnerPreferencesInitialPojoList.get(position).getMaxAge());
        //
        TextView partnerpreferenceheight = view.findViewById(R.id.partner_preference_height);
        partnerpreferenceheight.setText(partnerPreferencesInitialPojoList.get(position).getMinHeight());

        TextView partnerpreferenceeducation = view.findViewById(R.id.partner_preference_education);
        partnerpreferenceeducation.setText(partnerPreferencesInitialPojoList.get(position).getEducation());

        TextView partnerpreferenceannualincome = view.findViewById(R.id.partner_preference_annualincome);
        partnerpreferenceannualincome.setText(partnerPreferencesInitialPojoList.get(position).getIncome());


        final Button sentInterest = view.findViewById(R.id.sent_interest_matches_button);
        final Button interestSent = view.findViewById(R.id.interest_sent_matches_button);

        //Sent Request On Click
        sentInterest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String dotReceiverEmail = matchesPojoList.get(position).getPartnerEmail();
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

                                    sentInterest.setVisibility(View.GONE);
                                    interestSent.setVisibility(View.VISIBLE);
                                    interestSent.setText("Request Sent");
                                    interestSent.setEnabled(false);

                                }
                            }
                        });
            }
        });


        //Check Sent Request
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

                            String compareEmail = matchesPojoList.get(position).getPartnerEmail();

                            if (finalUserEmail.equals(compareEmail)) {
                                interestSent.setVisibility(View.VISIBLE);
                                sentInterest.setVisibility(View.GONE);
                                interestSent.setText("Request Sent");

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

        //Check Received Request
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

                            String compareEmail = matchesPojoList.get(position).getPartnerEmail();

                            if (finalUserEmail.equals(compareEmail)) {
                                interestSent.setVisibility(View.VISIBLE);
                                sentInterest.setVisibility(View.GONE);
                                interestSent.setText("Request Received");

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

        //Already Connected
        databaseReference.child("ConnectionSuccessful").child(mAuth.getCurrentUser().getEmail().replace(".", "")).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapShot : dataSnapshot.getChildren()) {
                    final String connectedUser = postSnapShot.getKey();
                    Log.v("Name:::::", connectedUser);

                    databaseReference.child(connectedUser).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                            AllFormDetailsPojo allFormDetailsPojo = dataSnapshot.getValue(AllFormDetailsPojo.class);
                            String fetchedUserEmail = allFormDetailsPojo.loginDetailsEmailId;

                            String finalUserEmail = fetchedUserEmail.replace(".", "");

                            String compareEmail = matchesPojoList.get(position).getPartnerEmail();

                            if (finalUserEmail.equals(compareEmail)) {
                                interestSent.setVisibility(View.VISIBLE);
                                sentInterest.setVisibility(View.GONE);
                                interestSent.setText("Connected");

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

        ((ViewPager) container).addView(view, position);

        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}
