package com.example.acerpc.wedknot.MyWedknotInside;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.View;

import com.bumptech.glide.Glide;
import com.example.acerpc.wedknot.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class MyPartnerDetailsActivity extends AppCompatActivity {

    private CardView basicDetails;
    private CircleImageView myPartnerUserPic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_partner_details);
        init();
        clickListeners();
        loadingImage();
    }

    public void init(){
        basicDetails = (CardView) findViewById(R.id.card_basic_details_my_partner);
        }

    public void clickListeners(){
        basicDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MyPartnerDetailsActivity.this,BasicDetailsMyPartner.class));
            }
        });
    }

    public void loadingImage(){
        myPartnerUserPic = findViewById(R.id.my_partner_user_pic);
        if(MyDetailActivity.imageUri!=null) {
            Glide.with(getApplicationContext()).load(MyDetailActivity.imageUri).into(myPartnerUserPic);
        }
    }

}
