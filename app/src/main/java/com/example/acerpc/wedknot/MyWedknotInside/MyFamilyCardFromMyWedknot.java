package com.example.acerpc.wedknot.MyWedknotInside;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.acerpc.wedknot.InitialForms.FamilyDetailsInitialPojo;
import com.example.acerpc.wedknot.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MyFamilyCardFromMyWedknot extends AppCompatActivity {

    Spinner fatherOccupation;
    Spinner motherOccupation;
    Spinner brothers;
    Spinner sisters;
    Spinner familyIncome;
    Spinner familyStatusSpinner;
    Spinner familyTypeSpinner;
    Spinner livingWithParentsSpinner;
    ArrayAdapter fatherOccupationAdapter;
    ArrayAdapter motherOccupationAdapter;
    ArrayAdapter brothersAdapter;
    ArrayAdapter sistersAdapter;
    ArrayAdapter familyIncomeAdapter;
    ArrayAdapter familyStatusAdapter;
    ArrayAdapter familyTypeAdapter;
    ArrayAdapter livingWithParentsAdapter;

    String fetchedFatherOccupation;
    String fetchedMotherOccupation;
    String fetchedBrothers;
    String fetchedSisters;
    String fetchedFamilyIncome;
    String fetchedFamilyStatus;
    String fetchedFamilyType;
    String fetchedLivingWithParents;


    Button saveButton;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    FirebaseUser firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_family_card_from_my_wedknot);

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

    public void init() {
        fatherOccupation = findViewById(R.id.father_occupation_myFamily);
        motherOccupation = findViewById(R.id.mother_occupation_myFamily);
        brothers = findViewById(R.id.brothers_myFamily);
        sisters = findViewById(R.id.sisters_myFamily);
        familyIncome = findViewById(R.id.family_income_myFamily);
        familyStatusSpinner = findViewById(R.id.family_status_myFamily);
        familyTypeSpinner = findViewById(R.id.family_type_myFamily);
        livingWithParentsSpinner = findViewById(R.id.living_with_parents_myFamily);
        saveButton = findViewById(R.id.save_button_my_family);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
    }

    public void allSpinners() {
        //Father
        List<String> fatherOccupationList = new ArrayList<>();
        fatherOccupationList.add("Not Filled");
        fatherOccupationList.add("Business/Entrepreneur");
        fatherOccupationList.add("Service - Private");
        fatherOccupationList.add("Service - Govt./PSU");
        fatherOccupationList.add("Army/Armed Forces");
        fatherOccupationList.add("Civil Services");
        fatherOccupationList.add("Retired");
        fatherOccupationList.add("Not Employed");
        fatherOccupationList.add("Expired");

        fatherOccupationAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, fatherOccupationList);
        fatherOccupationAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        fatherOccupation.setPrompt("Select Father's Occupation");
        fatherOccupation.setAdapter(fatherOccupationAdapter);

        //Mother
        List<String> motherOccupationList = new ArrayList<>();
        motherOccupationList.add("Not Filled");
        motherOccupationList.add("HouseWife");
        motherOccupationList.add("Business/Entrepreneur");
        motherOccupationList.add("Service - Private");
        motherOccupationList.add("Service - Govt./PSU");
        motherOccupationList.add("Army/Armed Forces");
        motherOccupationList.add("Civil Services");
        motherOccupationList.add("Retired");
        motherOccupationList.add("Teacher");
        motherOccupationList.add("Not Employed");
        motherOccupationList.add("Expired");

        motherOccupationAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, motherOccupationList);
        motherOccupationAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        motherOccupation.setPrompt("Select Mother's Occupation");
        motherOccupation.setAdapter(motherOccupationAdapter);

        //Brothers
        List<String> brothersList = new ArrayList<>();

        brothersList.add("Not Filled");
        brothersList.add("0");
        brothersList.add("1");
        brothersList.add("2");
        brothersList.add("3");
        brothersList.add("3+");

        brothersAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, brothersList);
        brothersAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        brothers.setPrompt("Select No. of Brothers");
        brothers.setAdapter(brothersAdapter);

        //Sisters
        List<String> sistersList = new ArrayList<>();

        sistersList.add("Not Filled");
        sistersList.add("0");
        sistersList.add("1");
        sistersList.add("2");
        sistersList.add("3");
        sistersList.add("3+");

        sistersAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, sistersList);
        sistersAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sisters.setPrompt("Select No.of Sisters");
        sisters.setAdapter(sistersAdapter);

        //Family Income
        List<String> familyIncomeList = new ArrayList<String>();
        familyIncomeList.add("Not Filled");
        familyIncomeList.add("No Income");
        familyIncomeList.add("Rs. 0-2 Lakh");
        familyIncomeList.add("Rs. 2-4 Lakh");
        familyIncomeList.add("Rs. 4-6 Lakh");
        familyIncomeList.add("Rs. 6-8 Lakh");
        familyIncomeList.add("Rs. 8-10 Lakh");
        familyIncomeList.add("Rs. 10-15 Lakh");
        familyIncomeList.add("Rs. 15-20 Lakh");
        familyIncomeList.add("Rs. 20-30 Lakh");
        familyIncomeList.add("Rs. 30-50 Lakh");
        familyIncomeList.add("Rs. 50-70 Lakh");
        familyIncomeList.add("Rs. 70 Lakh- 1 Crore");
        familyIncomeList.add("Rs. 1 Crore above");

        familyIncomeAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, familyIncomeList);
        familyIncomeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        familyIncome.setPrompt("Select Family Income");
        familyIncome.setAdapter(familyIncomeAdapter);


        //FamilyStatus
        List<String> familyStatus = new ArrayList<String>();
        familyStatus.add("Not Filled");
        familyStatus.add("Rich/Affulent");
        familyStatus.add("Upper Middle Class");
        familyStatus.add("Middle Class");

        familyStatusAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, familyStatus);
        familyStatusAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        familyStatusSpinner.setPrompt("Select Family Income");
        familyStatusSpinner.setAdapter(familyStatusAdapter);

        //FamilyType
        List<String> familyType = new ArrayList<String>();
        familyType.add("Not Filled");
        familyType.add("Joint Family");
        familyType.add("Nuclear Family");
        familyType.add("Others");

        familyTypeAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, familyType);
        familyTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        familyTypeSpinner.setPrompt("Select Family Income");
        familyTypeSpinner.setAdapter(familyTypeAdapter);

        //Living
        List<String> livingWithParents = new ArrayList<String>();
        livingWithParents.add("Not Filled");
        livingWithParents.add("Yes");
        livingWithParents.add("No");

        livingWithParentsAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, livingWithParents);
        livingWithParentsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        livingWithParentsSpinner.setPrompt("Select Family Income");
        livingWithParentsSpinner.setAdapter(livingWithParentsAdapter);

    }

    public void readData() {
        databaseReference.child(firebaseUser.getEmail().replace(".", "")).child("FamilyDetails")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        FamilyDetailsInitialPojo familyDetailsInitialPojo = dataSnapshot.getValue(FamilyDetailsInitialPojo.class);
                        if(familyDetailsInitialPojo!=null) {
                            fetchedFatherOccupation = familyDetailsInitialPojo.familyfatherOccupation;
                            fetchedMotherOccupation = familyDetailsInitialPojo.familymotherOccupation;
                            fetchedBrothers = familyDetailsInitialPojo.familybrothers;
                            fetchedSisters = familyDetailsInitialPojo.familysisters;
                            fetchedFamilyIncome = familyDetailsInitialPojo.familyIncome;
                            fetchedFamilyStatus = familyDetailsInitialPojo.familyStatus;
                            fetchedFamilyType = familyDetailsInitialPojo.familyTypeSpinner;
                            fetchedLivingWithParents = familyDetailsInitialPojo.familylivingWithParents;
                        }
                        placeAtRightPosition();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
    }



    public void writeData(){
        //FamilyDetails
        String fatherOccupationThis = fatherOccupation.getSelectedItem().toString();
        String motherOccupationThis = motherOccupation.getSelectedItem().toString();
        String brothersThis = brothers.getSelectedItem().toString();
        String sistersThis = sisters.getSelectedItem().toString();
        String familyIncomeThis = familyIncome.getSelectedItem().toString();
        String familyStatusSpinnerThis = familyStatusSpinner.getSelectedItem().toString();
        String familyTypeSpinnerThis = familyTypeSpinner.getSelectedItem().toString();
        String livingWithParentsSpinnerThis = livingWithParentsSpinner.getSelectedItem().toString();

        FamilyDetailsInitialPojo familyDetailsInitialPojo = new FamilyDetailsInitialPojo(fatherOccupationThis,motherOccupationThis,brothersThis,
                sistersThis,familyIncomeThis,familyStatusSpinnerThis,familyTypeSpinnerThis,livingWithParentsSpinnerThis);

        databaseReference.child(firebaseUser.getEmail().replace(".","")).child("FamilyDetails").setValue(familyDetailsInitialPojo);
        Toast.makeText(this, "Updated", Toast.LENGTH_SHORT).show();

        placeAtRightPosition();
    }

    public void placeAtRightPosition(){
        fatherOccupation.setSelection(fatherOccupationAdapter.getPosition(fetchedFatherOccupation));
        motherOccupation.setSelection(motherOccupationAdapter.getPosition(fetchedMotherOccupation));
        brothers.setSelection(brothersAdapter.getPosition(fetchedBrothers));
        sisters.setSelection(sistersAdapter.getPosition(fetchedSisters));
        familyIncome.setSelection(familyIncomeAdapter.getPosition(fetchedFamilyIncome));
        familyStatusSpinner.setSelection(familyStatusAdapter.getPosition(fetchedFamilyStatus));
        familyTypeSpinner.setSelection(familyTypeAdapter.getPosition(fetchedFamilyType));
        livingWithParentsSpinner.setSelection(livingWithParentsAdapter.getPosition(fetchedLivingWithParents));
    }



}
