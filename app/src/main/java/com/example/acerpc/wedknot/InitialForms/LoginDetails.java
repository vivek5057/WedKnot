package com.example.acerpc.wedknot.InitialForms;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.example.acerpc.wedknot.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginDetails extends AppCompatActivity {

    private static final String TAG = "Firebase";

    // static EditText username;
    static EditText emailId;
    static EditText fullName;
    static EditText phoneNo;
    static EditText passwordEditText;

    LottieAnimationView animationView;

    private Button loginDetailsButton;
    private AwesomeValidation awesomeValidation;

    //Firebase
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    FirebaseAuth mAuth;

    //To Keep LogIn
    SharedPreferences pref;
    SharedPreferences.Editor edit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_details);

        pref = getSharedPreferences("prefs", MODE_PRIVATE);
        edit = pref.edit();

        init();

        //Firebase initialization
        firebaseDatabase = FirebaseDatabase.getInstance();
        mAuth = FirebaseAuth.getInstance();
        databaseReference = firebaseDatabase.getReference();

        //Validation intializaation
        awesomeValidation = new AwesomeValidation(ValidationStyle.UNDERLABEL);
        awesomeValidation.setContext(this);
        awesomeValidation.addValidation(this, R.id.full_name,
                "^[a-zA-Z]+(([',. -][a-zA-Z ])?[a-zA-Z]*)*$", R.string.fullname_error);
        awesomeValidation.addValidation(this, R.id.email_id,
                "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)" +
                        "|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$", R.string.email_error);
        awesomeValidation.addValidation(this, R.id.phone_no, "^(\\+\\d{1,3}" +
                "[- ]?)?\\d{10}$", R.string.phoneno_error);
        awesomeValidation.addValidation(this, R.id.passsword_edittext, "^(?=." +
                "*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$", R.string.password_error);

        //Next Button
        loginDetailsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                animationView.setVisibility(View.VISIBLE);
                submitForm();
            }
        });
    }

    //Form Validation Submit Form
    public void submitForm() {
        //first validate the form then move ahead
        //if this becomes true that means validation is successfull
        if (awesomeValidation.validate()) {
            registerNewUser();
        }
    }

    //comparing with exiting username
    public void compareUsername() {

        //String userName = username.getText().toString();
        String userEmail = (String) emailId.getText().toString().replace(".", "");


        ValueEventListener eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    animationView.setVisibility(View.GONE);
                    Toast.makeText(LoginDetails.this, "Email Already Exists.",
                            Toast.LENGTH_SHORT).show();
                } else {
                    writeInDatabase();
                    writeGenderOfEmail();
                    Intent intent = new Intent(LoginDetails.this, FamilyDetailsActivity.class);
                    startActivity(intent);
                    animationView.setVisibility(View.GONE);
                    //To Keep LogIn
                    edit.putBoolean("permission", true).apply();
                    finish();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d(TAG, databaseError.getMessage());
            }
        };
        databaseReference.child(userEmail).addListenerForSingleValueEvent(eventListener);
    }


    //SendToDatabase
    public void writeInDatabase() {

        String userEmail = emailId.getText().toString().replace(".", "");

        PersonalDetailsActivity personalDetailsActivity = new PersonalDetailsActivity();
        SocialDetails socialDetails = new SocialDetails();
        CareerDetails careerDetails = new CareerDetails();

        //PersonalDetails
        String personalDetailsGender = personalDetailsActivity.sGenderId;
        String personalDetailsDob = personalDetailsActivity.dobText.getText().toString();
        String personalDetailsHeight = personalDetailsActivity.heightSpinner.getSelectedItem().toString();
        String personalDetailsCountry = personalDetailsActivity.countrySpinner.getSelectedItem().toString();
        String personalDetailsState = personalDetailsActivity.stateSpinner.getSelectedItem().toString();
        String personalDetailsCity = personalDetailsActivity.citySpinner.getSelectedItem().toString();
        String personalDetailsAge = personalDetailsActivity.ageSpinner.getText().toString();

        //SocialDetails
        String socialDetailsMartialStatus = socialDetails.martialStatus.getSelectedItem().toString();
        String socialDetailsMotherTongue = socialDetails.motherTongue.getSelectedItem().toString();
        String socialDetailsReligion = socialDetails.religion.getSelectedItem().toString();

        //CareerDetails
        String careerDetailsHighestEducation = careerDetails.highestEducation.getSelectedItem().toString();
        String careerDetailsCollege = careerDetails.collegeName.getText().toString();
        String careerDetailsWorkArea = careerDetails.workArea.getSelectedItem().toString();
        String careerDetailsAnnualIncome = careerDetails.income.getSelectedItem().toString();

        //LoginDetails
        //String loginDetailsUsername = username.getText().toString();
        String loginDetailsFullName = fullName.getText().toString();
        String loginDetailsEmailId = emailId.getText().toString();
        String loginDetailsPhoneNo = phoneNo.getText().toString();

        AllFormDetailsPojo allFormDetailsPojo = new AllFormDetailsPojo(personalDetailsGender, personalDetailsDob, personalDetailsAge,
                personalDetailsHeight, personalDetailsCountry, personalDetailsState, personalDetailsCity,
                socialDetailsMartialStatus, socialDetailsMotherTongue, socialDetailsReligion,
                careerDetailsHighestEducation, careerDetailsCollege, careerDetailsWorkArea, careerDetailsAnnualIncome,
                loginDetailsFullName, loginDetailsEmailId, loginDetailsPhoneNo);

        databaseReference.child(userEmail).setValue(allFormDetailsPojo);
    }

    public void writeGenderOfEmail() {
        String userEmail = emailId.getText().toString().replace(".", "");

        long currentTimeForAccount = System.currentTimeMillis();

        if (PersonalDetailsActivity.sGenderId.equals("Male")) {
            databaseReference.child("Male").child(userEmail).setValue(currentTimeForAccount);
        } else {
            databaseReference.child("Female").child(userEmail).setValue(currentTimeForAccount);
        }
    }

    public void registerNewUser() {
        final String userEmail = emailId.getText().toString();
        String userPass = passwordEditText.getText().toString();

        mAuth.createUserWithEmailAndPassword(userEmail, userPass)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(LoginDetails.this, "This Email Already Exist.",
                                    Toast.LENGTH_SHORT).show();
                            //updateUI(null);
                        }
                    }

                    private void updateUI(Object o) {
                        compareUsername();
                    }
                });
    }

    public void init() {
        emailId = findViewById(R.id.email_id);
        fullName = findViewById(R.id.full_name);
        phoneNo = findViewById(R.id.phone_no);
        loginDetailsButton = findViewById(R.id.LoginDetailsButton);
        passwordEditText = findViewById(R.id.password);
        animationView = findViewById(R.id.anim);

    }

}


