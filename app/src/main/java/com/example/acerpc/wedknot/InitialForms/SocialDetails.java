package com.example.acerpc.wedknot.InitialForms;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.acerpc.wedknot.R;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class SocialDetails extends AppCompatActivity  {

    static Spinner martialStatus;
    static Spinner motherTongue;
    static Spinner religion;
    private  Button socialButton;
    private TextView castetext;
    private Spinner casteSpinner;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_social_details);

        castetext = (TextView) findViewById(R.id.caste_text);
        casteSpinner = (Spinner) findViewById(R.id.caste_spinner);

        List<String> maritalList = new ArrayList<String>();
        maritalList.add("Select Marital Status");
        maritalList.add("Never married");
        maritalList.add("Awaiting Divorce");
        maritalList.add("Divorced");
        maritalList.add("Widowed");

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


        //Next Button
        socialButton = (Button) findViewById(R.id.socialButton);
        socialButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v == socialButton) {
                    submitForm();
                }
            }
        });

        //Marital Status
        martialStatus = (Spinner) findViewById(R.id.marital_status);
        ArrayAdapter myAdapterMaritalStatus = new ArrayAdapter(this, android.R.layout.simple_spinner_item, maritalList);
        myAdapterMaritalStatus.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        martialStatus.setPrompt("Marital Status");
        martialStatus.setAdapter(myAdapterMaritalStatus);

        //Religion
        religion = (Spinner) findViewById(R.id.religion_spinner);
        ArrayAdapter myReligionAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, religionList);
        myReligionAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        religion.setPrompt("Select Religion");
        religion.setAdapter(myReligionAdapter);
        //religion.setOnItemSelectedListener(this);



        //Mother Language Status
        ArrayList<String> languagesList = null;
        try {
            languagesList = getLanguages("languages.json");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        motherTongue = (Spinner) findViewById(R.id.mother_tongue);
        ArrayAdapter motherLanguageSpinnerAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, languagesList);
        motherLanguageSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        motherTongue.setPrompt("Mother Language");
        motherTongue.setAdapter(motherLanguageSpinnerAdapter );
    }

    //Getting List of  Languages from JSONFile(Assets)
    public ArrayList<String> getLanguages(String fileName) throws JSONException {
        JSONArray jsonArray = null;
        ArrayList<String> lList = new ArrayList<>();
        try {
            InputStream takeJsonCountry = getResources().getAssets().open("languages.json");
            int size = takeJsonCountry.available();
            byte[] dataJsonCountry = new byte[size];
            takeJsonCountry.read(dataJsonCountry);
            takeJsonCountry.close();

            String json = new String(dataJsonCountry, "UTF-16");
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

    //Form Validation SubmitForm
    public void submitForm(){

        if (martialStatus.getSelectedItem().toString().trim().equals("Select Marital Status")) {
            Toast.makeText(this, "Select Marital Status", Toast.LENGTH_SHORT).show();
        } else if (motherTongue.getSelectedItem().toString().trim().equals("Select Language")) {
            Toast.makeText(this, "Select Mother Tongue", Toast.LENGTH_SHORT).show();
        } else if (religion.getSelectedItem().toString().trim().equals("Select Religion")) {
            Toast.makeText(this, "Select Religion", Toast.LENGTH_SHORT).show();
        }
        if (!martialStatus.getSelectedItem().toString().trim().equals("Select Marital Status")
                && !motherTongue.getSelectedItem().toString().trim().equals("Select Language")
                && !religion.getSelectedItem().toString().trim().equals("Select Religion")) {

            Intent intent = new Intent(SocialDetails.this, LoginDetails.class);
            Bundle bundleAnimation = ActivityOptions.makeCustomAnimation(getApplicationContext(),R.anim.animation,R.anim.animation2).toBundle();
            startActivity(intent,bundleAnimation);
            finish();
        }
    }

   /* @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if(position == 1){
            castetext.setVisibility(View.VISIBLE);
            casteSpinner.setVisibility(View.VISIBLE);


        }else{
            castetext.setVisibility(View.GONE);
            casteSpinner.setVisibility(View.GONE);
        }
    }
    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }*/
}

