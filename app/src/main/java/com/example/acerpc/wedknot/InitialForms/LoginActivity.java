package com.example.acerpc.wedknot.InitialForms;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.example.acerpc.wedknot.BottomNavigationActivity;
import com.example.acerpc.wedknot.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "FireBase";
    private AwesomeValidation awesomeValidation;
    private Button loginButton;

    //To Keep LogIn
    SharedPreferences pref;
    SharedPreferences.Editor edit;

    TextInputEditText emailEditText, passwordEdittext;
    TextView forgotPassword;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseApp.initializeApp(this);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();

        awesomeValidation = new AwesomeValidation(ValidationStyle.UNDERLABEL);
        awesomeValidation.setContext(this);
        awesomeValidation.addValidation(this, R.id.username_edittext, "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+", R.string.email_error);
        awesomeValidation.addValidation(this, R.id.passsword_edittext, "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$", R.string.password_error);
        emailEditText = findViewById(R.id.username_edittext);
        passwordEdittext = findViewById(R.id.passsword_edittext);

        pref  = getSharedPreferences("prefs",MODE_PRIVATE);
        edit = pref.edit();

        loginButton = (Button) findViewById(R.id.login_button);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitForm();
            }
        });

        forgotPassword = findViewById(R.id.forgot_password_Text_View);
        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog();
            }

            private void openDialog() {
                ForgotPassword forgotPassword = new ForgotPassword();
                forgotPassword.show(getSupportFragmentManager(), "Password Reset");
            }
        });

    }

    //Form Validation Submit Form
    public void submitForm() {
        //first validate the form then move ahead
        //if this becomes true that means validation is successfull
        if (awesomeValidation.validate()) {
            LogIn();
        }
    }

    public void LogIn() {

        final String userEmail = emailEditText.getText().toString();
        String userpass = passwordEdittext.getText().toString();

        mAuth.signInWithEmailAndPassword(userEmail, userpass)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            //To Keep LogIn
                            edit.putBoolean("permission",true).apply();
                            updateUI(user);


                        } else {
                            // If sign in fails, display a message to the user.
                            Log.d(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(LoginActivity.this, "LogIn failed." + task.getException(),
                                    Toast.LENGTH_SHORT).show();
                            //updateUI(null);
                        }

                    }

                    private void updateUI(FirebaseUser user) {
                        startActivity(new Intent(LoginActivity.this, BottomNavigationActivity.class));
                        finish();
                    }
                });

    }

}

