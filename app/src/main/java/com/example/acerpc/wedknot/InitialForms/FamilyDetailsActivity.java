package com.example.acerpc.wedknot.InitialForms;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.example.acerpc.wedknot.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class FamilyDetailsActivity extends AppCompatActivity {

    private Button SkipButton;
    private Button NextButton;
    private Spinner fatherOccupation;
    private Spinner motherOccupation;
    private Spinner brothers;
    private Spinner sisters;
    private Spinner familyIncome;
    private Spinner familyStatusSpinner;
    private Spinner familyTypeSpinner;
    private Spinner livingWithParentsSpinner;
    String userEmail;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    DatabaseReference userReference;
    FirebaseUser firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_family_details);
        getSupportActionBar().hide();
        init();
        allSpinners();

        SkipButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                writeToDatabase();
                Intent intent = new Intent(FamilyDetailsActivity.this, LifeStyleInitial.class);
                startActivity(intent);
                finish();
            }
        });

        NextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                writeToDatabase();
                Intent intent = new Intent(FamilyDetailsActivity.this, LifeStyleInitial.class);
                startActivity(intent);
                finish();

            }
        });
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

        ArrayAdapter fatherOccupationAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, fatherOccupationList);
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

        ArrayAdapter motherOccupationAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, motherOccupationList);
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

        ArrayAdapter brothersAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, brothersList);
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

        ArrayAdapter sistersAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, sistersList);
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

        ArrayAdapter familyIncomeAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, familyIncomeList);
        familyIncomeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        familyIncome.setPrompt("Select Family Income");
        familyIncome.setAdapter(familyIncomeAdapter);


        //FamilyStatus
        List<String> familyStatus = new ArrayList<String>();
        familyStatus.add("Not Filled");
        familyStatus.add("Rich/Affulent");
        familyStatus.add("Upper Middle Class");
        familyStatus.add("Middle Class");

        ArrayAdapter familyStatusAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, familyStatus);
        familyStatusAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        familyStatusSpinner.setPrompt("Select Family Status");
        familyStatusSpinner.setAdapter(familyStatusAdapter);

        //FamilyType
        List<String> familyType = new ArrayList<String>();
        familyType.add("Not Filled");
        familyType.add("Joint Family");
        familyType.add("Nuclear Family");
        familyType.add("Others");

        ArrayAdapter familyTypeAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, familyType);
        familyTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        familyTypeSpinner.setPrompt("Select Family Type");
        familyTypeSpinner.setAdapter(familyTypeAdapter);

        //Living
        List<String> livingWithParents = new ArrayList<String>();
        livingWithParents.add("Not Filled");
        livingWithParents.add("Yes");
        livingWithParents.add("No");

        ArrayAdapter livingWithParentsAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, livingWithParents);
        livingWithParentsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        livingWithParentsSpinner.setPrompt("Select Family Income");
        livingWithParentsSpinner.setAdapter(livingWithParentsAdapter);

    }

    public void writeToDatabase() {

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

        userReference.setValue(familyDetailsInitialPojo);

    }


    public void init() {

        SkipButton = (Button) findViewById(R.id.skip_button);
        NextButton = (Button) findViewById(R.id.next_button);
        fatherOccupation = findViewById(R.id.father_occupation);
        motherOccupation = findViewById(R.id.mother_occupation);
        brothers = findViewById(R.id.brothers);
        sisters = findViewById(R.id.sisters);
        familyIncome = findViewById(R.id.family_income);
        familyStatusSpinner = findViewById(R.id.family_status);
        familyTypeSpinner = findViewById(R.id.family_types);
        livingWithParentsSpinner = findViewById(R.id.living_with_parents);

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        userEmail = LoginDetails.emailId.getText().toString().replace(".","");

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference(userEmail);
        userReference = databaseReference.child("FamilyDetails");

    }


}
