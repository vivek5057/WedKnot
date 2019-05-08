package com.example.acerpc.wedknot.InitialForms;

import android.app.ActivityOptions;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.example.acerpc.wedknot.R;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class PersonalDetailsActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    //Global variable to get country index.
    int countryindex;

    static Spinner heightSpinner;
    static Spinner countrySpinner;
    static Spinner stateSpinner;
    static Spinner ageSpinner;
    static Spinner citySpinner;
    static RadioGroup genderGroup;
    RadioButton radioButton;
    static String sGenderId;
    private Button loginPersonalButton;
    private AwesomeValidation awesomeValidation;
    static EditText dobText;
    Calendar myCalendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.personal_details_activity);


        //Getting the instance of Spinner and applying OnItemSelectedListener on it
        heightSpinner = (Spinner) findViewById(R.id.heightSpinner);
        countrySpinner = (Spinner) findViewById(R.id.mycountry);
        stateSpinner = (Spinner) findViewById(R.id.state);
        citySpinner = (Spinner) findViewById(R.id.cities);
        genderGroup = findViewById(R.id.gender_group);
        ageSpinner = findViewById(R.id.ageSpinner);

        myCalendar = Calendar.getInstance();

        //Validation
        awesomeValidation = new AwesomeValidation(ValidationStyle.UNDERLABEL);
        awesomeValidation.setContext(this);

        //adding validation to edittexts

        //awesomeValidation.addValidation(this, R.id.country, "[a-zA-Z]{2,}", R.string.country_error);
        awesomeValidation.addValidation(this, R.id.dobText, "^(?:(?:31(\\/|-|\\.)(?:0?[13578]|1[02]))\\1|(?:(?:29|30)(\\/|-|\\.)(?:0?[1,3-9]|1[0-2])\\2))(?:(?:1[6-9]|[2-9]\\d)?\\d{2})$|^(?:29(\\/|-|\\.)0?2\\3(?:(?:(?:1[6-9]|[2-9]\\d)?(?:0[48]|[2468][048]|[13579][26])|(?:(?:16|[2468][048]|[3579][26])00))))$|^(?:0?[1-9]|1\\d|2[0-8])(\\/|-|\\.)(?:(?:0?[1-9])|(?:1[0-2]))\\4(?:(?:1[6-9]|[2-9]\\d)?\\d{2})$", R.string.dob_error);


        List<String> ageList = new ArrayList<>();
        for(int i=18 ; i<=65 ; i++){
            String age = Integer.toString(i);
            ageList.add(age);
        }

        //maxAge
        ArrayAdapter ageSpinnerAdapter = new ArrayAdapter(this,android.R.layout.simple_spinner_item,ageList);
        ageSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ageSpinner.setAdapter(ageSpinnerAdapter);


        List<String> height = new ArrayList<String>();
        height.add("Select Height");
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


        genderGroup = (RadioGroup) findViewById(R.id.gender_group);

        //Getting the instance of Spinner and applying OnItemSelectedListener on it
        heightSpinner = (Spinner) findViewById(R.id.heightSpinner);
        countrySpinner = (Spinner) findViewById(R.id.mycountry);
        stateSpinner = (Spinner) findViewById(R.id.state);
        citySpinner = (Spinner) findViewById(R.id.cities);


        //Height Spinner
        ArrayAdapter heightSpinnerAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, height);
        heightSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        heightSpinner.setPrompt("Height");
        heightSpinner.setAdapter(heightSpinnerAdapter);


        //CountrySpinner
        ArrayList<String> countryList = null;
        try {
            countryList = getCountries("All_Locations.json");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        ArrayAdapter countrySpinnerAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, countryList);
        countrySpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        countrySpinner.setPrompt("Country");
        countrySpinner.setAdapter(countrySpinnerAdapter);
        countrySpinner.setOnItemSelectedListener(this);

        //Next Button
        loginPersonalButton = (Button) findViewById(R.id.personalButton);
        loginPersonalButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v == loginPersonalButton) {
                    int selectedGender = genderGroup.getCheckedRadioButtonId();
                    radioButton = (RadioButton) findViewById(selectedGender);
                    sGenderId = radioButton.getText().toString();
                    submitForm();
                }

            }
        });

        //DateOfBirth
        dobText = (EditText) findViewById(R.id.dobText);
        dobText.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                new DatePickerDialog(PersonalDetailsActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

    }

    //validation
    void submitForm() {
        //first validate the form then move ahead
        //if this becomes true that means validation is successfull

        if (genderGroup.getCheckedRadioButtonId() == -1) {
            Toast.makeText(this, "Select Gender", Toast.LENGTH_SHORT).show();

        }else if (heightSpinner.getSelectedItem().toString().trim().equals("Select Height")) {
            Toast.makeText(this, "Select Height", Toast.LENGTH_SHORT).show();
        }else if (countrySpinner.getSelectedItem().toString().trim().equals("Select Country")) {
            Toast.makeText(this, "Select Country", Toast.LENGTH_SHORT).show();
        }
        if (awesomeValidation.validate() && genderGroup.getCheckedRadioButtonId() != -1
                && !heightSpinner.getSelectedItem().toString().trim().equals("Select Height")
                && !countrySpinner.getSelectedItem().toString().trim().equals("Select Country")) {
            //Toast.makeText(this, "Validation Successfull", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(PersonalDetailsActivity.this, CareerDetails.class);
            Bundle bundleAnimation = ActivityOptions.makeCustomAnimation(getApplicationContext(), R.anim.animation, R.anim.animation2).toBundle();
            startActivity(intent, bundleAnimation);
            finish();
            //process the data further
        }
    }

    DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateLabel();
        }

    };

    private void updateLabel() {
        String myFormat = "dd/MM/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        dobText.setText(sdf.format(myCalendar.getTime()));
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        switch (parent.getId()) {
            case R.id.mycountry:
                // List<String> stateList = getStates("country_state");

                JSONArray jsonArray = null;
                ArrayList<String> sList = new ArrayList<>();
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

                            if (position == i) {
                                countryindex = i;
                                for (int j = 0; j < jsonArray.length(); j++)
                                    sList.add(jsonArray.getJSONObject(i).getJSONArray("States").getJSONObject(j).getString("StateName"));
                            }
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                ArrayAdapter stateSpinnerAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, sList);
                stateSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                stateSpinner.setPrompt("States");

                stateSpinner.setAdapter(stateSpinnerAdapter);
                stateSpinner.setOnItemSelectedListener(this);
                break;

            case R.id.state:


                JSONArray jsonCityArray = null;
                ArrayList<String> cityList = new ArrayList<>();
                try {
                    InputStream takeJsonCountry = getResources().getAssets().open("All_Locations.json");
                    int size = takeJsonCountry.available();
                    byte[] dataJsonCountry = new byte[size];
                    takeJsonCountry.read(dataJsonCountry);
                    takeJsonCountry.close();

                    String json = new String(dataJsonCountry, "UTF-8");
                    jsonCityArray = new JSONArray(json);
                    if (jsonCityArray != null) {


                        for (int j = 0; j < jsonCityArray.length(); j++) {

                            if (position == j) {

                                for (int k = 0; k < jsonCityArray.getJSONObject(countryindex).getJSONArray("States").getJSONObject(j).getJSONArray("Cities").length(); k++) {
                                    cityList.add(jsonCityArray.getJSONObject(countryindex).getJSONArray("States").getJSONObject(j).getJSONArray("Cities").getString(k));
                                }
                            }

                        }
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }


                ArrayAdapter citySpinnerAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, cityList);
                citySpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                citySpinner.setPrompt("States");
                citySpinner.setAdapter(citySpinnerAdapter);
        }

    }


    @Override
    public void onNothingSelected(AdapterView<?> parent) {

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

   /* public ArrayList<String> getStates(String fileName) {
        return cList;
    }*/

    /*String json =  new String(dataJsonCountry,"UTF-8");
    jsonArray = new JSONArray(json);
            if(jsonArray!=null){


        for(int i=0;i<jsonArray.length();i++){
            cList.add(jsonArray.getJSONObject(i).getString("country"));
        }
    }*/

}
