package com.example.acerpc.wedknot;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.example.acerpc.wedknot.NavigationFragment.ChatFragment;
import com.example.acerpc.wedknot.NavigationFragment.InboxFragment;
import com.example.acerpc.wedknot.NavigationFragment.MatchesFragment;
import com.example.acerpc.wedknot.NavigationFragment.MyWedknotFragment;

public class BottomNavigationActivity extends AppCompatActivity {

    private TextView mTextMessage;
    private long doubleTap=0;
    private Toast doubleTapToast;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            Fragment selectedFragment =null;

            switch (item.getItemId()) {
                case R.id.bottom_my_wedknot:
                    selectedFragment=new MyWedknotFragment();
                    break;
                case R.id.bottom_matches:
                    selectedFragment=new MatchesFragment();
                    break;
                case R.id.bottom_inbox:
                    selectedFragment=new InboxFragment();
                    break;
                case R.id.bottom_chat:
                    selectedFragment=new ChatFragment();
                    break;
            }
            getSupportFragmentManager().beginTransaction().replace(R.id.mycontainer,selectedFragment).commit();
            return true;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom_navigation);
        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);


        if (savedInstanceState == null) {
            navigation.setSelectedItemId(R.id.bottom_my_wedknot); // change to whichever id should be default
        }
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
}
