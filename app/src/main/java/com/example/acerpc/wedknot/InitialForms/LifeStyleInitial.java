package com.example.acerpc.wedknot.InitialForms;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.acerpc.wedknot.MyWedknotInside.LifeStylePojo;
import com.example.acerpc.wedknot.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class LifeStyleInitial extends AppCompatActivity {

    EditText aboutMe;
    Spinner food;
    Spinner drink;
    Spinner smoke;
    Spinner skinColor;
    Spinner bodyType;
    Button next_button;
    Button skip_button;

    ArrayAdapter foodAdapter;
    ArrayAdapter drinkAdapter;
    ArrayAdapter smokeAdapter;
    ArrayAdapter skinColorAdapter;
    ArrayAdapter bodyTypeAdapter;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    FirebaseUser firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_life_style_initial);
        getSupportActionBar().hide();
        init();
        allSpinners();

        skip_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                writeData();
                Intent intent = new Intent(LifeStyleInitial.this, PartnerPreferencesInitial.class);
                startActivity(intent);
                finish();
            }
        });

        next_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                writeData();
                Intent intent = new Intent(LifeStyleInitial.this, PartnerPreferencesInitial.class);
                startActivity(intent);
                finish();

            }
        });
    }

    public void init(){
        aboutMe = findViewById(R.id.about_me_my_lifestyle);
        food = findViewById(R.id.food_my_lifestyle);
        drink = findViewById(R.id.drink_my_lifestyle);
        smoke = findViewById(R.id.smoke_my_lifestyle);
        skinColor = findViewById(R.id.skin_color_my_lifestyle);
        bodyType = findViewById(R.id.body_type_my_lifestyle);
        next_button = findViewById(R.id.next_button_lifestyle);
        skip_button = findViewById(R.id.skip_button_lifestyle);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
    }

    public void allSpinners(){
        List<String> foodList = new ArrayList<>();
        foodList.add("Not Filled");
        foodList.add("Vegetarian");
        foodList.add("Non-Vegetarian");
        foodList.add("Eggetarian");
        foodAdapter = new ArrayAdapter(this,android.R.layout.simple_spinner_dropdown_item,foodList);
        food.setAdapter(foodAdapter);

        List<String> drinkList = new ArrayList<>();
        drinkList.add("Not Filled");
        drinkList.add("Yes");
        drinkList.add("No");
        drinkList.add("Occasionally");
        drinkAdapter = new ArrayAdapter(this,android.R.layout.simple_spinner_dropdown_item,drinkList);
        drink.setAdapter(drinkAdapter);

        List<String> smokeList = new ArrayList<>();
        smokeList.add("Not Filled");
        smokeList.add("Yes");
        smokeList.add("No");
        smokeList.add("Occasionally");
        smokeAdapter = new ArrayAdapter(this,android.R.layout.simple_spinner_dropdown_item,smokeList);
        smoke.setAdapter(smokeAdapter);

        List<String> skinColorList = new ArrayList<>();
        skinColorList.add("Not Filled");
        skinColorList.add("Very Fair");
        skinColorList.add("Fair");
        skinColorList.add("Wheatish");
        skinColorList.add("Wheatish Brown");
        skinColorList.add("Dark");
        skinColorAdapter = new ArrayAdapter(this,android.R.layout.simple_spinner_dropdown_item,skinColorList);
        skinColor.setAdapter(skinColorAdapter);

        List<String> bodyTypeList = new ArrayList<>();
        bodyTypeList.add("Not Filled");
        bodyTypeList.add("Slim");
        bodyTypeList.add("Average");
        bodyTypeList.add("Athletic");
        bodyTypeList.add("Heavy");
        bodyTypeAdapter = new ArrayAdapter(this,android.R.layout.simple_spinner_dropdown_item,bodyTypeList);
        bodyType.setAdapter(bodyTypeAdapter);
    }

    public void writeData(){
        String aboutMeThis;
        if(aboutMe.getText().toString().isEmpty()){
            aboutMeThis = "Not Filled";
        }else{
            aboutMeThis = aboutMe.getText().toString();
        }
        String foodThis = food.getSelectedItem().toString();
        String drinkThis = drink.getSelectedItem().toString();
        String smokeThis = smoke.getSelectedItem().toString();
        String bodyTypeThis = bodyType.getSelectedItem().toString();
        String skinColorThis = skinColor.getSelectedItem().toString();

        LifeStylePojo lifeStylePojo = new LifeStylePojo(aboutMeThis,foodThis,drinkThis,smokeThis,skinColorThis,bodyTypeThis);
        databaseReference.child(firebaseUser.getEmail().replace(".","")).child("LifeStyleDetail").setValue(lifeStylePojo);
    }


}
