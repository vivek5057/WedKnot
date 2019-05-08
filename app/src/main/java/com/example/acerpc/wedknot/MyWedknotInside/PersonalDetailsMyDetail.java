package com.example.acerpc.wedknot.MyWedknotInside;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

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

public class PersonalDetailsMyDetail extends AppCompatActivity {

    TextView nameMyDetail;
    EditText contactNoMyDetail;
    TextView genderMyDetail;
    TextView religionMyDetail;
    TextView languageMyDetail;
    Spinner educationMyDetail;
    Spinner occupationMyDetail;
    String fetchedName;
    String fetchedGender;
    String fetchedLanguage;
    String fetchedReligion;

    String fetchedEducation;
    String fetchedOccupation;
    String fetchedContactNo;
    Button saveButton;

    ArrayAdapter workAreaSpinnerAdapter;
    ArrayAdapter highestEducationSpinnerAdapter;


    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    FirebaseUser firebaseUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_details_my_detail);
        getSupportActionBar().hide();
        init();
        allSpinners();
        readData();

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                writeData();
            }
        });

    }

    public void init(){
        nameMyDetail = findViewById(R.id.name_my_detail);
        contactNoMyDetail = findViewById(R.id.contact_no_my_detail);
        genderMyDetail = findViewById(R.id.gender_my_detail);
        religionMyDetail = findViewById(R.id.religion_my_detail);
        languageMyDetail = findViewById(R.id.language_my_detail);
        educationMyDetail = findViewById(R.id.education_my_detail);
        occupationMyDetail = findViewById(R.id.occupation_my_detail);
        saveButton = findViewById(R.id.save_button_my_detail);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
    }

    public void allSpinners(){

        List<String> educationList = new ArrayList<String>();
        educationList.add("Not Filled");
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
        educationMyDetail.setPrompt("Select Highest Education");
        educationMyDetail.setAdapter(highestEducationSpinnerAdapter);

        //Work_Area
        ArrayList<String> workareaList = null;
        try {
            workareaList = getWorkArea();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        workAreaSpinnerAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, workareaList);
        workAreaSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        occupationMyDetail.setPrompt("Select Occupation");
        occupationMyDetail.setAdapter(workAreaSpinnerAdapter);


    }

    //Getting WorkArea
    public ArrayList<String> getWorkArea() throws JSONException {
        JSONArray jsonArray = null;
        ArrayList<String> cList = new ArrayList<>();
        try {
            InputStream takeJsonCountry = getResources().getAssets().open("work_area_again.json");
            int size = takeJsonCountry.available();
            byte[] dataJsonCountry = new byte[size];
            takeJsonCountry.read(dataJsonCountry);
            takeJsonCountry.close();

            String json = new String(dataJsonCountry, "UTF-8");
            jsonArray = new JSONArray(json);
            if (jsonArray != null) {
                for (int i = 0; i < jsonArray.length(); i++) {
                    cList.add(jsonArray.getString(i));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return cList;
    }


    public void writeData(){
        String educationThis = educationMyDetail.getSelectedItem().toString();
        String occupationThis = occupationMyDetail.getSelectedItem().toString();
        String contactThis = contactNoMyDetail.getText().toString();
        databaseReference.child(firebaseUser.getEmail().replace(".", "")).child("careerDetailsHighestEducation").setValue(educationThis);
        databaseReference.child(firebaseUser.getEmail().replace(".", "")).child("careerDetailsWorkArea").setValue(occupationThis);
        databaseReference.child(firebaseUser.getEmail().replace(".", "")).child("loginDetailsPhoneNo").setValue(contactThis);

        placeAtRightPosition();
    }
    
    public void readData(){
        
        databaseReference.child(firebaseUser.getEmail().replace(".", "")).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                AllFormDetailsPojo allFormDetailsPojo = dataSnapshot.getValue(AllFormDetailsPojo.class);
                if(allFormDetailsPojo!=null) {
                    fetchedName = allFormDetailsPojo.loginDetailsFullName;
                    fetchedGender = allFormDetailsPojo.personalDetailsGender;
                    fetchedLanguage = allFormDetailsPojo.socialDetailsMotherTongue;
                    fetchedReligion = allFormDetailsPojo.socialDetailsReligion;

                    fetchedEducation = allFormDetailsPojo.careerDetailsHighestEducation;
                    fetchedOccupation = allFormDetailsPojo.careerDetailsWorkArea;
                    fetchedContactNo = allFormDetailsPojo.loginDetailsPhoneNo;
                }
                 placeAtRightPosition();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        
    }
    
    public void placeAtRightPosition(){
        nameMyDetail.setText(fetchedName);
        contactNoMyDetail.setText(fetchedContactNo);
        genderMyDetail.setText(fetchedGender);
        religionMyDetail.setText(fetchedReligion);
        languageMyDetail.setText(fetchedLanguage);
        contactNoMyDetail.setText(fetchedContactNo);

        educationMyDetail.setSelection(highestEducationSpinnerAdapter.getPosition(fetchedEducation));
        occupationMyDetail.setSelection(workAreaSpinnerAdapter.getPosition(fetchedOccupation));
    }


}
