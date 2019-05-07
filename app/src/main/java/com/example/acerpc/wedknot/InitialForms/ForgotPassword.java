package com.example.acerpc.wedknot.InitialForms;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatDialogFragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.acerpc.wedknot.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPassword extends AppCompatDialogFragment {
    private TextInputEditText emailForgotPassword;
    private Button sendEmailForgotPassword;
    private FirebaseAuth mAuth;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        mAuth = FirebaseAuth.getInstance();

        LayoutInflater layoutInflater = getActivity().getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.forgot_password_dialogbox,null);

        builder.setView(view);

        emailForgotPassword = view.findViewById(R.id.email_forgot_password_text);
        sendEmailForgotPassword = view.findViewById(R.id.send_email_for_reset_password_button);

        sendEmailForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userEmail = emailForgotPassword.getText().toString();

                if(TextUtils.isEmpty(userEmail)){
                    Toast.makeText(getActivity(),"Enter Email",Toast.LENGTH_SHORT).show();
                }else{

                    mAuth.sendPasswordResetEmail(userEmail).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            if(task.isSuccessful()) {

                                Toast.makeText(getActivity(), "Please check your Email Account to reset password.", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(getActivity(), LoginActivity.class));
                            }else{
                                String message = task.getException().getMessage();
                                Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }

            }
        });

        return builder.create();
    }
}
