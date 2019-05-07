package com.example.acerpc.wedknot.MyWedknotInside;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.View;

import com.bumptech.glide.Glide;
import com.example.acerpc.wedknot.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class MyFamilyActivity extends AppCompatActivity {

    private CardView myFamilyCard;
    private CircleImageView myDetailFamilyPic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_family);
        myFamilyCard = (CardView) findViewById(R.id.my_family_card);
        imageLoading();
        myFamilyCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MyFamilyActivity.this, MyFamilyCardFromMyWedknot.class));

            }
        });
    }

    public void imageLoading() {
        myDetailFamilyPic = findViewById(R.id.my_detail_family_pic);
        if(MyDetailActivity.imageUri!=null) {
            Glide.with(getApplicationContext()).load(MyDetailActivity.imageUri).into(myDetailFamilyPic);
        }

    }
}
