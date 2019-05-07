package com.example.acerpc.wedknot.MyWedknotInside;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

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

public class LifeStyleMyDetail extends AppCompatActivity {

    EditText aboutMe;
    Spinner food;
    Spinner drink;
    Spinner smoke;
    Spinner skinColor;
    Spinner bodyType;
    Button save_button;

    String fetchedAboutMe;
    String fetchedFood;
    String fetchedDrink;
    String fetchedSmoke;
    String fetchedBodyType;
    String fetchedSkinColor;

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
        setContentView(R.layout.activity_life_style_my_detail);
        init();
        allSpinners();
        readData();

        save_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                writeData();
            }
        });

    }

    public void init(){
        aboutMe = findViewById(R.id.about_me_my_detail);
        food = findViewById(R.id.food_my_detail);
        drink = findViewById(R.id.drink_my_detail);
        smoke = findViewById(R.id.smoke_my_detail);
        skinColor = findViewById(R.id.skin_color_my_detail);
        bodyType = findViewById(R.id.body_type_my_detail);
        save_button = findViewById(R.id.save_button_lifestyle);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
    }

    public void allSpinners(){
        List<String> foodList = new ArrayList<>();
        foodList.add("Vegetarian");
        foodList.add("Non-Vegetarian");
        foodList.add("Eggetarian");
        foodAdapter = new ArrayAdapter(this,android.R.layout.simple_spinner_dropdown_item,foodList);
        food.setAdapter(foodAdapter);

        List<String> drinkList = new ArrayList<>();
        drinkList.add("Yes");
        drinkList.add("No");
        drinkList.add("Occasionally");
        drinkAdapter = new ArrayAdapter(this,android.R.layout.simple_spinner_dropdown_item,drinkList);
        drink.setAdapter(drinkAdapter);

        List<String> smokeList = new ArrayList<>();
        smokeList.add("Yes");
        smokeList.add("No");
        smokeList.add("Occasionally");
        smokeAdapter = new ArrayAdapter(this,android.R.layout.simple_spinner_dropdown_item,smokeList);
        smoke.setAdapter(smokeAdapter);

        List<String> skinColorList = new ArrayList<>();
        skinColorList.add("Very Fair");
        skinColorList.add("Fair");
        skinColorList.add("Wheatish");
        skinColorList.add("Wheatish Brown");
        skinColorList.add("Dark");
        skinColorAdapter = new ArrayAdapter(this,android.R.layout.simple_spinner_dropdown_item,skinColorList);
        skinColor.setAdapter(skinColorAdapter);

        List<String> bodyTypeList = new ArrayList<>();
        bodyTypeList.add("Slim");
        bodyTypeList.add("Average");
        bodyTypeList.add("Athletic");
        bodyTypeList.add("Heavy");
        bodyTypeAdapter = new ArrayAdapter(this,android.R.layout.simple_spinner_dropdown_item,bodyTypeList);
        bodyType.setAdapter(bodyTypeAdapter);
    }

    public void writeData(){
        String aboutMeThis = aboutMe.getText().toString();
        String foodThis = food.getSelectedItem().toString();
        String drinkThis = drink.getSelectedItem().toString();
        String smokeThis = smoke.getSelectedItem().toString();
        String bodyTypeThis = bodyType.getSelectedItem().toString();
        String skinColorThis = skinColor.getSelectedItem().toString();

        LifeStylePojo lifeStylePojo = new LifeStylePojo(aboutMeThis,foodThis,drinkThis,smokeThis,skinColorThis,bodyTypeThis);
        databaseReference.child(firebaseUser.getEmail().replace(".","")).child("LifeStyleDetail").setValue(lifeStylePojo);
        placeAtRightPosition();
    }

    public void readData(){
        databaseReference.child(firebaseUser.getEmail().replace(".","")).child("LifeStyleDetail").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                LifeStylePojo lifeStylePojo = dataSnapshot.getValue(LifeStylePojo.class);
                if(lifeStylePojo!=null) {
                    fetchedAboutMe = lifeStylePojo.aboutMe;
                    fetchedFood = lifeStylePojo.food;
                    fetchedDrink = lifeStylePojo.drink;
                    fetchedSmoke = lifeStylePojo.smoke;
                    fetchedBodyType = lifeStylePojo.bodyType;
                    fetchedSkinColor = lifeStylePojo.skinColor;
                }
                placeAtRightPosition();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void placeAtRightPosition(){
        aboutMe.setText(fetchedAboutMe);
        food.setSelection(foodAdapter.getPosition(fetchedFood));
        drink.setSelection(drinkAdapter.getPosition(fetchedDrink));
        smoke.setSelection(smokeAdapter.getPosition(fetchedSmoke));
        bodyType.setSelection(bodyTypeAdapter.getPosition(fetchedBodyType));
        skinColor.setSelection(skinColorAdapter.getPosition(fetchedSkinColor));

    }


}
