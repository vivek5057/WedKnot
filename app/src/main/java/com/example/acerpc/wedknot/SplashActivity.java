package com.example.acerpc.wedknot;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.airbnb.lottie.LottieAnimationView;

public class SplashActivity extends AppCompatActivity {

    SharedPreferences pref;
    SharedPreferences.Editor edit;
    LottieAnimationView animationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        pref = getSharedPreferences("prefs",MODE_PRIVATE);
        edit = pref.edit();

        requestWindowFeature(Window.FEATURE_NO_TITLE); //will hide the title
        getSupportActionBar().hide(); // hide the title bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN); //enable full screen

        setContentView(R.layout.activity_splash);
        final View view = LayoutInflater.from(SplashActivity.this).inflate(R.layout.no_internet,null);
        animationView = view.findViewById(R.id.no_net);
        animationView.setAnimation("no_net.json");
        animationView.playAnimation();


        if (!InternetConnection.ConnectedOrNot(SplashActivity.this)) {
            AlertDialog.Builder builder = new AlertDialog.Builder(SplashActivity.this);
            builder.setTitle("Internet Not Working").setView(view).setCancelable(false).setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                }
            }).create().show();
        }else{

            Handler handler=new Handler();
            handler.postDelayed(new Runnable() {

                @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                public void run() {

                    if(pref.getBoolean("permission",false)){
                        startActivity(new Intent(SplashActivity.this,BottomNavigationActivity.class));
                    }else{
                        startActivity(new Intent(SplashActivity.this,MainActivity.class));
                    }

                    finish();
                }
            },2000);

        }



    }
}