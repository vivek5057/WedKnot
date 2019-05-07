package com.example.acerpc.wedknot.MyWedknotInside;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.acerpc.wedknot.InitialForms.PartnerPreferencesInitialPojo;
import com.example.acerpc.wedknot.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class BasicDetailsMyPartner extends AppCompatActivity {

    private Spinner minAge;
    private Spinner maxAge;
    private Spinner minHeight;
    private Spinner maxHeight;
    private SearchableSpinner country;
    private Spinner maritalStatus;
    private Spinner religion;
    private Spinner language;
    private Spinner income;
    private Spinner education;
    static String fetchedMinAge;
    static String fetchedMaxAge;
    static String fetchedMinHeight;
    static String fetchedMaxHeight;
    static String fetchedReligion;
    static String fetchedLanguage;
    static String fetchedCountry;
    static String fetchedEducation;
    static String fetchedIncome;
    static String fetchedMaritalStatus;
    ArrayAdapter minAgeAdapter;
    ArrayAdapter maxAgeAdapter;
    ArrayAdapter minHeightAdapter;
    ArrayAdapter maxHeightAdapter;
    ArrayAdapter incomeSpinnerAdapter;
    ArrayAdapter religionAdapter;
    ArrayAdapter highestEducationSpinnerAdapter;
    ArrayAdapter motherTongueAdapter;
    ArrayAdapter countryAdapter;
    ArrayAdapter myAdapterMaritalStatus;

    private Button saveButton;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    FirebaseUser firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basic_details_my_partner);
        init();
        mySpinnerAdapter();
        compareHeightAge();
        readData();

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                writeData();

            }
        });

    }

    public void init() {
        minAge = (Spinner) findViewById(R.id.min_age_spinner);
        maxAge = (Spinner) findViewById(R.id.max_age_spinner);
        minHeight = (Spinner) findViewById(R.id.min_height_spinner);
        maxHeight = (Spinner) findViewById(R.id.max_height_spinner);
        country = (SearchableSpinner) findViewById(R.id.country_spinner_my_partner);
        maritalStatus = (Spinner) findViewById(R.id.marital_status_spinner_my_partner);
        religion = (Spinner) findViewById(R.id.religion_spinner_my_partner);
        language = (Spinner) findViewById(R.id.language_spinner_my_partner);
        income = (Spinner) findViewById(R.id.annual_income_spinner_my_partner);
        education = (Spinner) findViewById(R.id.education_spinner_my_partner);
        saveButton = (Button) findViewById(R.id.save_button);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
    }

    public void mySpinnerAdapter() {

        //maritalStatus
        List<String> maritalList = new ArrayList<String>();
        maritalList.add("Not Filled");
        maritalList.add("Never married");
        maritalList.add("Awaiting Divorce");
        maritalList.add("Divorced");
        maritalList.add("Widowed");
        myAdapterMaritalStatus = new ArrayAdapter(this, android.R.layout.simple_spinner_item, maritalList);
        myAdapterMaritalStatus.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        maritalStatus.setPrompt("Marital Status");
        maritalStatus.setAdapter(myAdapterMaritalStatus);

        List<String> educationList = new ArrayList<String>();
        educationList.add("Select Highest Education");
        educationList.add("B.Arch");
        educationList.add("B.E/B.Tech");
        educationList.add("B.Pharma");
        educationList.add("M.Arch");
        educationList.add("M.E/M.Tech");
        educationList.add("M.Pharma");
        educationList.add("M.S(Engineering)");
        educationList.add("BCA");
        educationList.add("MCA/PGDCA");
        educationList.add("B.Com");
        educationList.add("M.Com");
        educationList.add("CA");
        educationList.add("CFA");
        educationList.add("BBA");
        educationList.add("BHM");
        educationList.add("MBA");
        educationList.add("BAMS");
        educationList.add("BDS");
        educationList.add("DM");
        educationList.add("M.D");
        educationList.add("M.S(Medicine)");
        educationList.add("MBBS");
        educationList.add("MPT");
        educationList.add("BL/LLB");
        educationList.add("ML/LLM");
        educationList.add("B.A");
        educationList.add("B.Ed");
        educationList.add("B.Sc");
        educationList.add("M.A");
        educationList.add("M.Ed");
        educationList.add("M.Sc");
        educationList.add("M.Phill");
        educationList.add("Ph.D");
        educationList.add("High School");
        educationList.add("Trade School");
        educationList.add("Diploma");
        educationList.add("Other");

        //Highest Education
        highestEducationSpinnerAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, educationList);
        highestEducationSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        education.setPrompt("Select Highest Education");
        education.setAdapter(highestEducationSpinnerAdapter);

        //income
        List<String> incomeList = new ArrayList<String>();
        incomeList.add("Not Filled");
        incomeList.add("No Income");
        incomeList.add("Rs. 0-2 Lakh");
        incomeList.add("Rs. 2-4 Lakh");
        incomeList.add("Rs. 4-6 Lakh");
        incomeList.add("Rs. 6-8 Lakh");
        incomeList.add("Rs. 8-10 Lakh");
        incomeList.add("Rs. 10-15 Lakh");
        incomeList.add("Rs. 15-20 Lakh");
        incomeList.add("Rs. 20-30 Lakh");
        incomeList.add("Rs. 30-50 Lakh");
        incomeList.add("Rs. 50-70 Lakh");
        incomeList.add("Rs. 70 Lakh- 1 Crore");
        incomeList.add("Rs. 1 Crore above");

        //Income
        incomeSpinnerAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, incomeList);
        incomeSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        incomeSpinnerAdapter.add("");
        income.setPrompt("Select Income");
        income.setAdapter(incomeSpinnerAdapter);


        //minAge
        List<String> minAgeList = new ArrayList<>();
        for (int i = 18; i <= 65; i++) {
            String age = Integer.toString(i);
            minAgeList.add(age);
        }

        minAgeAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, minAgeList);
        minAgeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        minAge.setAdapter(minAgeAdapter);


        List<String> maxAgeList = new ArrayList<>();
        for (int i = 18; i <= 65; i++) {
            String age = Integer.toString(i);
            maxAgeList.add(age);
        }

        //maxAge
        maxAgeAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, maxAgeList);
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

        minHeightAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, height);
        minHeightAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        minHeight.setAdapter(minHeightAdapter);

        //maxHeight
        maxHeightAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, height);
        maxHeightAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
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

        religionAdapter = new ArrayAdapter(this, R.layout.custom_simple_spinner_item, religionList);
        religionAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        religion.setAdapter(religionAdapter);

        //Mother Language Status
        ArrayList<String> languagesList = null;
        try {
            languagesList = getLanguages("languages.json");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        motherTongueAdapter = new ArrayAdapter(this, R.layout.custom_simple_spinner_item, languagesList);
        motherTongueAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        language.setAdapter(motherTongueAdapter);

        //country
        ArrayList<String> countryList = null;
        try {
            countryList = getCountries("All_Locations.json");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        countryAdapter = new ArrayAdapter(this, R.layout.custom_simple_spinner_item, countryList);
        countryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        country.setAdapter(countryAdapter);
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

    public void readData() {
        databaseReference.child(firebaseUser.getEmail().replace(".", "")).child("PartnerPreferences")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        PartnerPreferencesInitialPojo partnerPreferencesInitialPojo = dataSnapshot.getValue(PartnerPreferencesInitialPojo.class);
                        if(partnerPreferencesInitialPojo!=null) {
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
                           // Log.v("Partner::", fetchedMaxAge);
                        }

                        placeAtRightPosition();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
    }

    public void writeData(){
        String writeMaxAge = maxAge.getSelectedItem().toString();
        String writeMinAge = minAge.getSelectedItem().toString();
        String writeMaxHeight = maxHeight.getSelectedItem().toString();
        String writeMinHeight = minHeight.getSelectedItem().toString();
        String writeReligion = religion.getSelectedItem().toString();
        String writeLanguage = language.getSelectedItem().toString();
        String writeCountry = country.getSelectedItem().toString();

        String writeIncome = income.getSelectedItem().toString();
        String writeEducation = education.getSelectedItem().toString();
        String writeMaritalStatus = maritalStatus.getSelectedItem().toString();

        PartnerPreferencesInitialPojo partnerPreferencesInitialPojo1 = new PartnerPreferencesInitialPojo(writeMinAge,writeMaxAge,writeMinHeight,writeMaxHeight,writeReligion,writeLanguage,writeCountry,writeIncome,writeEducation,writeMaritalStatus);
        databaseReference.child(firebaseUser.getEmail().replace(".", "")).child("PartnerPreferences").setValue(partnerPreferencesInitialPojo1);

        placeAtRightPosition();
        Toast.makeText(this,"Updated",Toast.LENGTH_SHORT);
    }

    public void placeAtRightPosition() {
        maxAge.setSelection(maxAgeAdapter.getPosition(fetchedMaxAge));
        minAge.setSelection(minAgeAdapter.getPosition(fetchedMinAge));
        maxHeight.setSelection(maxHeightAdapter.getPosition(fetchedMaxHeight));
        minHeight.setSelection(minHeightAdapter.getPosition(fetchedMinHeight));
        religion.setSelection(religionAdapter.getPosition(fetchedReligion));
        language.setSelection(motherTongueAdapter.getPosition(fetchedLanguage));
        country.setSelection(countryAdapter.getPosition(fetchedCountry));
        income.setSelection(incomeSpinnerAdapter.getPosition(fetchedIncome));
        education.setSelection(highestEducationSpinnerAdapter.getPosition(fetchedEducation));
        maritalStatus.setSelection(myAdapterMaritalStatus.getPosition(fetchedMaritalStatus));
        //Log.v("RightPosition2","after");

    }
}
