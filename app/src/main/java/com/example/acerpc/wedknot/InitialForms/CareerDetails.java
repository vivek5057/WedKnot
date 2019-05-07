package com.example.acerpc.wedknot.InitialForms;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.acerpc.wedknot.R;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class CareerDetails extends AppCompatActivity {

    static Spinner highestEducation;
    static Spinner workArea;
    static Spinner income;
    static EditText collegeName;
    private Button careerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_career_details);

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


        List<String> incomeList = new ArrayList<String>();
        incomeList.add("Select Income");
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

        collegeName = findViewById(R.id.college_name_optional);

        //Next Button
        careerButton = (Button) findViewById(R.id.careerButton);
        careerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v == careerButton) {
                    submitForm();
                }

            }
        });

        //Highest Education
        highestEducation = (Spinner) findViewById(R.id.highest_education);
        ArrayAdapter highestEducationSpinnerAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, educationList);
        highestEducationSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        highestEducation.setPrompt("Select Highest Education");
        highestEducation.setAdapter(highestEducationSpinnerAdapter);

        //Income
        income = (Spinner) findViewById(R.id.income);
        ArrayAdapter incomeSpinnerAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, incomeList);
        incomeSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        income.setPrompt("Select Income");
        income.setAdapter(incomeSpinnerAdapter);


        //Work_Area
        workArea = (Spinner) findViewById(R.id.work_area);
        ArrayList<String> workareaList = null;
        try {
            workareaList = getWorkArea("All_Locations.json");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        ArrayAdapter workAreaSpinnerAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, workareaList);
        workAreaSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        workArea.setPrompt("Select WorkArea");
        workArea.setAdapter(workAreaSpinnerAdapter);


    }


    //Getting WorkArea
    public ArrayList<String> getWorkArea(String fileName) throws JSONException {
        JSONArray jsonArray = null;
        ArrayList<String> cList = new ArrayList<>();
        try {
            InputStream takeJsonCountry = getResources().getAssets().open("work_area.json");
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

    //Submit Form Validation
    public void submitForm() {

        if (highestEducation.getSelectedItem().toString().trim().equals("Select Highest Education")) {
            Toast.makeText(this, "Select Highest Education", Toast.LENGTH_SHORT).show();
        } else if (workArea.getSelectedItem().toString().trim().equals("Select Work Area")) {
            Toast.makeText(this, "Select Work Area", Toast.LENGTH_SHORT).show();
        } else if (income.getSelectedItem().toString().trim().equals("Select Income")) {
            Toast.makeText(this, "Select Income", Toast.LENGTH_SHORT).show();
        }
        if (!highestEducation.getSelectedItem().toString().trim().equals("Select Highest Education")
        && !workArea.getSelectedItem().toString().trim().equals("Select Work Area")
                && !income.getSelectedItem().toString().trim().equals("Select Income")) {

            Intent intent = new Intent(CareerDetails.this, SocialDetails.class);
            Bundle bundleAnimation = ActivityOptions.makeCustomAnimation(getApplicationContext(), R.anim.animation, R.anim.animation2).toBundle();
            startActivity(intent, bundleAnimation);
            finish();
        }

    }
}
