package com.example.acerpc.wedknot.SearchFloatingbutton;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Spinner;

import com.example.acerpc.wedknot.InitialForms.AllFormDetailsPojo;
import com.example.acerpc.wedknot.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class SearchPartnerPreferences extends AppCompatActivity {

    Spinner minAge;
    Spinner maxAge;
    Spinner minHeight;
    Spinner maxHeight;
    Spinner religion;
    Spinner motherTongue;
    Spinner country;
    Button searchbutton;
    String currentUserGender;
    Boolean found;

    String fetchedUserName;
    String fetchedUserAge;
    String fetchedUserHeight;
    String fetchedUserMotherTongue;
    String fetchedUserReligion;
    String fetchedWorkArea;
    String fetchedUserCity;
    String fetchedUserCountry;
    String fetchedUserEmail;

    String finalUserEmail;

    int listSize = 0;

   static  public List<SearchMatchesItemPojo> searchMatchesItemPojoList;

    RelativeLayout relativeLayout;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    FirebaseUser currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_partner_preferences);
        init();
        compareHeightAge();
        allSpinners();
        found = false;
        readCurrentUserGender();

        searchbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //writeData();
                compareUser();
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {

                    public void run() {
                        startIntent();

                    }
                }, 1800);


            }
        });
    }


    public void allSpinners() {

        //minAge
        List<String> minAgeList = new ArrayList<>();
        for (int i = 18; i <= 65; i++) {
            String age = Integer.toString(i);
            minAgeList.add(age);
        }

        ArrayAdapter minAgeAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, minAgeList);
        minAgeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        minAge.setAdapter(minAgeAdapter);

        List<String> maxAgeList = new ArrayList<>();
        for (int i = 18; i <= 65; i++) {
            String age = Integer.toString(i);
            maxAgeList.add(age);
        }

        //maxAge
        ArrayAdapter maxAgeAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, maxAgeList);
        maxAgeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        maxAge.setAdapter(maxAgeAdapter);

        //minHeight
        List<String> height = new ArrayList<String>();
        height.add("1.21 mts(4'0 ft)");
        height.add("1.24 mts(4'1 ft)");
        height.add("1.28 mts(4'2 ft)");
        height.add("1.31 mts(4'3 ft)");
        height.add("1.34 mts(4'4 ft)");
        height.add("1.37 mts(4'5 ft)");
        height.add("1.40 mts(4'6 ft)");
        height.add("1.43 mts(4'8 ft)");
        height.add("1.45 mts(4'9 ft)");
        height.add("1.47 mts(4'10 ft)");
        height.add("1.49 mts(4'11 ft)");
        height.add("1.52 mts(5'0 ft)");
        height.add("1.54 mts(5'1 ft)");
        height.add("1.57 mts(5'2 ft)");
        height.add("1.60 mts(5'3 ft)");
        height.add("1.62 mts(5'4 ft)");
        height.add("1.65 mts(5'5 ft)");
        height.add("1.67 mts(5'6 ft)");
        height.add("1.70 mts(5'7 ft)");
        height.add("1.72 mts(5'8 ft)");
        height.add("1.75 mts(5'9 ft)");
        height.add("1.77 mts(5'10 ft)");
        height.add("1.80 mts(5'11 ft)");
        height.add("1.82 mts(6'0 ft)");
        height.add("1.85 mts(6'1 ft)");
        height.add("1.87 mts(6'2 ft)");
        height.add("1.90 mts(6'3 ft)");
        height.add("1.93 mts(6'4 ft)");
        height.add("1.95 mts(6'5 ft)");

        ArrayAdapter minHeightAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, height);
        minHeightAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        minHeight.setAdapter(minHeightAdapter);

        //maxHeight
        ArrayAdapter maxHeightAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, height);
        maxAgeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        maxHeight.setAdapter(maxHeightAdapter);

        //religion
        List<String> religionList = new ArrayList<String>();
        religionList.add("Select Religion");
        religionList.add("Hindu");
        religionList.add("Muslim");
        religionList.add("Sikh");
        religionList.add("Christian");
        religionList.add("Buddhist");
        religionList.add("Jain");
        religionList.add("Parsi");
        religionList.add("Jewish");
        religionList.add("Bahai");
        religionList.add("Other");

        ArrayAdapter religionAdapter = new ArrayAdapter(this, R.layout.custom_simple_spinner_item, religionList);
        religionAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        religion.setAdapter(religionAdapter);

        //Mother Language Status
        ArrayList<String> languagesList = null;
        try {
            languagesList = getLanguages("languages.json");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        ArrayAdapter motherTongueAdapter = new ArrayAdapter(this, R.layout.custom_simple_spinner_item, languagesList);
        motherTongueAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        motherTongue.setAdapter(motherTongueAdapter);

        //country
        ArrayList<String> countryList = null;
        try {
            countryList = getCountries("All_Locations.json");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        ArrayAdapter countryTongueAdapter = new ArrayAdapter(this, R.layout.custom_simple_spinner_item, countryList);
        countryTongueAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        country.setAdapter(countryTongueAdapter);
    }


    //Getting List of  Languages from JSONFile(Assets)
    public ArrayList<String> getLanguages(String fileName) throws JSONException {
        JSONArray jsonArray = null;
        ArrayList<String> lList = new ArrayList<>();
        try {
            InputStream takeJsonLang = getResources().getAssets().open(fileName);
            int size = takeJsonLang.available();
            byte[] dataJsonLang = new byte[size];
            takeJsonLang.read(dataJsonLang);
            takeJsonLang.close();

            String json = new String(dataJsonLang, "UTF-16");
            jsonArray = new JSONArray(json);
            if (jsonArray != null) {
                for (int i = 0; i < jsonArray.length(); i++) {
                    lList.add(jsonArray.getJSONObject(i).getString("name"));
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return lList;
    }

    //Getting List of Countries from JSONFile(Assets)
    public ArrayList<String> getCountries(String fileName) throws JSONException {
        JSONArray jsonArray = null;
        ArrayList<String> cList = new ArrayList<>();
        try {
            InputStream takeJsonCountry = getResources().getAssets().open("All_Locations.json");
            int size = takeJsonCountry.available();
            byte[] dataJsonCountry = new byte[size];
            takeJsonCountry.read(dataJsonCountry);
            takeJsonCountry.close();

            String json = new String(dataJsonCountry, "UTF-8");
            jsonArray = new JSONArray(json);
            if (jsonArray != null) {
                for (int i = 0; i < jsonArray.length(); i++) {
                    cList.add(jsonArray.getJSONObject(i).getString("CountryName"));
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return cList;
    }

    public void compareHeightAge() {

        minAge.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (maxAge.getSelectedItemPosition() < minAge.getSelectedItemPosition()) {
                    int currentMinPosition = minAge.getSelectedItemPosition();
                    int currentMaxPosition = maxAge.getSelectedItemPosition();
                    int difference = (currentMinPosition + 1) - (currentMaxPosition + 1);
                    currentMinPosition = currentMinPosition - difference;
                    minAge.setSelection(currentMinPosition);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        minHeight.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (maxHeight.getSelectedItemPosition() < minHeight.getSelectedItemPosition()) {
                    int currentMinPosition = minHeight.getSelectedItemPosition();
                    int currentMaxPosition = maxHeight.getSelectedItemPosition();
                    int difference = (currentMinPosition + 1) - (currentMaxPosition + 1);
                    currentMinPosition = currentMinPosition - difference;
                    minHeight.setSelection(currentMinPosition);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    /*public void writeData() {

        final String userEmail = currentUser.getEmail().replace(".", "");
        String minAgeThis = minAge.getSelectedItem().toString();
        String maxAgeThis = maxAge.getSelectedItem().toString();
        String minHeightThis = minHeight.getSelectedItem().toString();
        String maxHeightThis = maxHeight.getSelectedItem().toString();
        String religionThis = religion.getSelectedItem().toString();
        String motherTongueThis = motherTongue.getSelectedItem().toString();
        String countryThis = country.getSelectedItem().toString();

        PartnerPreferencesInitialPojo partnerPreferencesInitialPojo = new PartnerPreferencesInitialPojo(minAgeThis, maxAgeThis, minHeightThis, maxHeightThis, religionThis, motherTongueThis, countryThis);
        databaseReference.child(userEmail).child("SearchedPartnerPreferences").setValue(partnerPreferencesInitialPojo);
    }*/

    public void readCurrentUserGender() {

        String userEmail = currentUser.getEmail().replace(".", "");

        databaseReference.child(userEmail).child("personalDetailsGender").addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                currentUserGender = dataSnapshot.getValue(String.class);
               // Log.v("User Emails: ", currentUserGender);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        });
    }

    //FloatingSearchFieldResults
    public void compareUser() {

        if (currentUserGender.equals("Male")) {
            databaseReference.child("Female").addListenerForSingleValueEvent(new ValueEventListener() {

                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    searchMatchesItemPojoList.clear();
                    for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {

                        final String userEmailKey = postSnapshot.getKey();
                     //   Log.v("User Emails: ", userEmailKey);

                        //Image
                        databaseReference.child(userEmailKey).child("Image").addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                final String fetchedUserImage = dataSnapshot.getValue(String.class);
                         //       Log.v("Fetched Image", fetchedUserImage);

                                databaseReference.child(userEmailKey).addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        AllFormDetailsPojo allFormDetailsPojo = dataSnapshot.getValue(AllFormDetailsPojo.class);
                                        if(allFormDetailsPojo!=null) {
                                             fetchedUserName = allFormDetailsPojo.loginDetailsFullName;
                                             fetchedUserAge = allFormDetailsPojo.personalDetailsAge;
                                             fetchedUserHeight = allFormDetailsPojo.personalDetailsHeight;
                                             fetchedUserMotherTongue = allFormDetailsPojo.socialDetailsMotherTongue;
                                             fetchedUserReligion = allFormDetailsPojo.socialDetailsReligion;
                                             fetchedWorkArea = allFormDetailsPojo.careerDetailsWorkArea;
                                             fetchedUserCity = allFormDetailsPojo.personalDetailsCity;
                                             fetchedUserCountry = allFormDetailsPojo.personalDetailsCountry;
                                             fetchedUserEmail = allFormDetailsPojo.loginDetailsEmailId;

                                        }
                                        finalUserEmail = fetchedUserEmail.replace(".", "");

                                        char[] dividefetchedUserHeight = fetchedUserHeight.toCharArray();
                                        char[] divideUserMinHeight = minHeight.getSelectedItem().toString().toCharArray();
                                        char[] divideUserMaxHeight = maxHeight.getSelectedItem().toString().toCharArray();

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

                                        if (Integer.parseInt(fetchedUserAge) >= Integer.parseInt(minAge.getSelectedItem().toString()) && Integer.parseInt(fetchedUserAge) <= Integer.parseInt(maxAge.getSelectedItem().toString())) {
                                            if (Integer.parseInt(finalUserHeight) >= Integer.parseInt(finalUserMinHeight) && Integer.parseInt(finalUserHeight) <= Integer.parseInt(finalUserMaxHeight)) {
                                                if (fetchedUserReligion.equals(religion.getSelectedItem().toString())) {
                                                    if (fetchedUserMotherTongue.equals(motherTongue.getSelectedItem().toString())) {
                                                        if (fetchedUserCountry.equals(country.getSelectedItem().toString())) {


                                                         /*   Log.v("Fetched Age", Integer.parseInt(fetchedUserAge) + " min" + Integer.parseInt(minAge.getSelectedItem().toString()) + " max" + Integer.parseInt(maxAge.getSelectedItem().toString()));
                                                            Log.v("Fetched Height", Integer.parseInt(finalUserHeight) + " min" + Integer.parseInt(finalUserMinHeight) + " max" + Integer.parseInt(finalUserMaxHeight));
                                                            Log.v("Fetched Data", fetchedUserName + " " + fetchedUserAge + " " + fetchedUserHeight + " " + fetchedUserMotherTongue + " " + fetchedUserReligion + " " + fetchedWorkArea + " " + fetchedUserCity + " " + fetchedUserCountry + " " + fetchedUserImage);
*/
                                                            searchMatchesItemPojoList.add(new SearchMatchesItemPojo(fetchedUserName, fetchedUserAge, fetchedUserHeight, fetchedUserMotherTongue, fetchedUserReligion, fetchedWorkArea, fetchedUserCity, fetchedUserCountry, fetchedUserImage, finalUserEmail));

                                                        }
                                                    }
                                                }
                                            }
                                        }
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
                    searchMatchesItemPojoList.clear();
                    for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                        final String userEmailKey = postSnapshot.getKey();
                        Log.v("User Emails: ", userEmailKey);

                        //Image
                        databaseReference.child(userEmailKey).child("Image").addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                final String fetchedUserImage = dataSnapshot.getValue(String.class);
                                Log.v("Fetched Image", fetchedUserImage);

                                databaseReference.child(userEmailKey).addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        AllFormDetailsPojo allFormDetailsPojo = dataSnapshot.getValue(AllFormDetailsPojo.class);
                                        if(allFormDetailsPojo!=null) {
                                             fetchedUserName = allFormDetailsPojo.loginDetailsFullName;
                                             fetchedUserAge = allFormDetailsPojo.personalDetailsAge;
                                             fetchedUserHeight = allFormDetailsPojo.personalDetailsHeight;
                                             fetchedUserMotherTongue = allFormDetailsPojo.socialDetailsMotherTongue;
                                             fetchedUserReligion = allFormDetailsPojo.socialDetailsReligion;
                                             fetchedWorkArea = allFormDetailsPojo.careerDetailsWorkArea;
                                             fetchedUserCity = allFormDetailsPojo.personalDetailsCity;
                                             fetchedUserCountry = allFormDetailsPojo.personalDetailsCountry;
                                             fetchedUserEmail = allFormDetailsPojo.loginDetailsEmailId;

                                        }
                                        finalUserEmail = fetchedUserEmail.replace(".", "");

                                        char[] dividefetchedUserHeight = fetchedUserHeight.toCharArray();
                                        char[] divideUserMinHeight = minHeight.getSelectedItem().toString().toCharArray();
                                        char[] divideUserMaxHeight = maxHeight.getSelectedItem().toString().toCharArray();

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

                                        if (Integer.parseInt(fetchedUserAge) >= Integer.parseInt(minAge.getSelectedItem().toString()) && Integer.parseInt(fetchedUserAge) <= Integer.parseInt(maxAge.getSelectedItem().toString())) {
                                            if (Integer.parseInt(finalUserHeight) >= Integer.parseInt(finalUserMinHeight) && Integer.parseInt(finalUserHeight) <= Integer.parseInt(finalUserMaxHeight)) {
                                                if (fetchedUserReligion.equals(religion.getSelectedItem().toString())) {
                                                    if (fetchedUserMotherTongue.equals(motherTongue.getSelectedItem().toString())) {
                                                        if (fetchedUserCountry.equals(country.getSelectedItem().toString())) {

                                                            Log.v("Fetched Age", Integer.parseInt(fetchedUserAge) + " min" + Integer.parseInt(minAge.getSelectedItem().toString()) + " max" + Integer.parseInt(maxAge.getSelectedItem().toString()));
                                                            Log.v("Fetched Height", Integer.parseInt(finalUserHeight) + " min" + Integer.parseInt(finalUserMinHeight) + " max" + Integer.parseInt(finalUserMaxHeight));
                                                            Log.v("Fetched Data", fetchedUserName + " " + fetchedUserAge + " " + fetchedUserHeight + " " + fetchedUserMotherTongue + " " + fetchedUserReligion + " " + fetchedWorkArea + " " + fetchedUserCity + " " + fetchedUserCountry + " " + fetchedUserImage);

                                                            searchMatchesItemPojoList.add(new SearchMatchesItemPojo(fetchedUserName, fetchedUserAge, fetchedUserHeight, fetchedUserMotherTongue, fetchedUserReligion, fetchedWorkArea, fetchedUserCity, fetchedUserCountry, fetchedUserImage, finalUserEmail));

                                                            //
                                                            /*databaseReference.child("ConnectionRequest").child(currentUser.getEmail().replace(".","")).child(userEmailKey).child("request_type").addValueEventListener(new ValueEventListener() {
                                                                @Override
                                                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                                    String currentRequest = dataSnapshot.getValue(String.class);
                                                                    Log.v("Request Type--",currentRequest);

                                                                    if(currentRequest.equals("sent")){

                                                                        searchMatchesItemPojoList.remove(new SearchMatchesItemPojo(fetchedUserName, fetchedUserAge, fetchedUserHeight, fetchedUserMotherTongue, fetchedUserReligion, fetchedWorkArea, fetchedUserCity, fetchedUserCountry, fetchedUserImage, finalUserEmail));

                                                                    }else{
                                                                        return;
                                                                    }

                                                                }

                                                                @Override
                                                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                                                }
                                                            });*/

                                                        }
                                                    }
                                                }
                                            }
                                        }
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
    }



    public void startIntent() {
        listSize = searchMatchesItemPojoList.size();

        if (listSize != 0) {
            startActivity(new Intent(SearchPartnerPreferences.this, MatchesForSearch.class));
            Log.v("Else:IF", "First");
        } else if (listSize == 0) {
            Snackbar.make(relativeLayout, "No Matches Found", Snackbar.LENGTH_LONG).show();
            Log.v("Else:IF", "Second");

        }

    }


    public void init() {
        minAge = findViewById(R.id.search_min_age);
        maxAge = findViewById(R.id.search_max_age_spinner);
        minHeight = findViewById(R.id.search_min_height_spinner);
        maxHeight = findViewById(R.id.search_max_height_spinner);
        religion = findViewById(R.id.search_religion_spinner);
        motherTongue = findViewById(R.id.search_mother_tongue_spinner);
        country = findViewById(R.id.search_country_spinner);
        searchbutton = findViewById(R.id.search_matches_recyclerview_button);
        relativeLayout = findViewById(R.id.main_relative);

        searchMatchesItemPojoList = new ArrayList<>();

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
        currentUser = FirebaseAuth.getInstance().getCurrentUser();

    }


}
