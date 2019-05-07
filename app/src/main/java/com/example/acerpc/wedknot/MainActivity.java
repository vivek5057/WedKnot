package com.example.acerpc.wedknot;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.acerpc.wedknot.InitialForms.LoginActivity;
import com.example.acerpc.wedknot.InitialForms.PersonalDetailsActivity;
import com.tbuonomo.viewpagerdotsindicator.WormDotsIndicator;


public class MainActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private ImageSliderMainActivity imageSliderAdapter;
    private long doubleTap;
    private Toast doubleTapToast;
    //private final int INTERNET_PERMISSION_CODE=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide(); // hide the title bar

        setContentView(R.layout.activity_main);
        viewPager = (ViewPager)  findViewById(R.id.viewPagerSlider);
        imageSliderAdapter = new ImageSliderMainActivity(this);
        WormDotsIndicator wormDotsIndicator = (WormDotsIndicator) findViewById(R.id.worm_dots_indicator);
        //TabLayout tabLayout = (TabLayout) findViewById(R.id.dottablayout);
        //tabLayout.setupWithViewPager(viewPager,true);
        wormDotsIndicator.setViewPager(viewPager);
        viewPager.setAdapter(imageSliderAdapter);

        //internetPermission();

        Button loginButton = (Button) findViewById(R.id.login);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent loginIntent = new Intent(MainActivity.this, LoginActivity.class);
                Bundle bundleAnimation = ActivityOptions.makeCustomAnimation(getApplicationContext(),R.anim.animation,R.anim.animation2).toBundle();
                startActivity(loginIntent,bundleAnimation);
            }
        });

        Button registerButton = (Button) findViewById(R.id.register);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registerIntent = new Intent(MainActivity.this, PersonalDetailsActivity.class);
                Bundle bundleAnimation = ActivityOptions.makeCustomAnimation(getApplicationContext(),R.anim.animation,R.anim.animation2).toBundle();
                startActivity(registerIntent,bundleAnimation);
            }
        });
    }

    @Override
    public void onBackPressed() {
        if(doubleTap + 2000>System.currentTimeMillis()){
            doubleTapToast.cancel();
            super.onBackPressed();
            return;
        }else{
            doubleTapToast = Toast.makeText(this,"Press Back again to Exit",Toast.LENGTH_SHORT);
            doubleTapToast.show();
        }
        doubleTap = System.currentTimeMillis();
    }

    //Permissions
    /*public void internetPermission(){
        if(ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)==PackageManager.PERMISSION_GRANTED){
            Toast.makeText(MainActivity.this,"You have already granted this permission!",Toast.LENGTH_SHORT).show();
        }else{
            requestInternetPermission();
        }
    }

    public void requestInternetPermission(){
        if(ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.READ_EXTERNAL_STORAGE)){

            new AlertDialog.Builder(this)
                    .setTitle("Permission needed")
                    .setMessage("This Permission is needed to access internet")
                    .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},INTERNET_PERMISSION_CODE);

                        }
                    })
                    .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
                    .create().show();

        }else{
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},INTERNET_PERMISSION_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == INTERNET_PERMISSION_CODE){
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Toast.makeText(this,"Permission GRANTED",Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(this,"Permission DENIED",Toast.LENGTH_SHORT).show();

            }
        }
    }*/
}
