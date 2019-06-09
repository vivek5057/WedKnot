package com.example.acerpc.wedknot.SearchFloatingbutton;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.acerpc.wedknot.R;

public class MatchesForSearch extends AppCompatActivity {

    RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;
    SearchMatchesRecyclerAdapter searchMatchesRecyclerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matches_for_search);
        getSupportActionBar().hide();
        init();
    }

    public void init() {

        recyclerView = findViewById(R.id.search_matches_recyclerview);
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        searchMatchesRecyclerAdapter = new SearchMatchesRecyclerAdapter(MatchesForSearch.this, SearchPartnerPreferences.searchMatchesItemPojoList);
        recyclerView.setAdapter(searchMatchesRecyclerAdapter);


    }


   /* public List<SearchMatchesItemPojo> getList(){
        List<SearchMatchesItemPojo> list = new ArrayList<>();

        String[] searchUserName={"Aditi","Aditi","Aditi","Aditi","Aditi","Aditi","Aditi","Aditi"};
        String[] searchUserAge={"20","20","20","20","20","20","20","20"};
        String[] searchUserHeight={"5'7","5'7","5'7","5'7","5'7","5'7","5'7","5'7"};
        String[] searchUserLanguage={"Hindi","Hindi","Hindi","Hindi","Hindi","Hindi","Hindi","Hindi"};
        String[] searchUserReligion={"Hindu","Hindu","Hindu","Hindu","Hindu","Hindu","Hindu","Hindu"};
        String[] searchUserWorkArea={"Teacher","Teacher","Teacher","Teacher","Teacher","Teacher","Teacher","Teacher"};
        String[] searchUserCity={"New Delhi","New Delhi","New Delhi","New Delhi","New Delhi","New Delhi","New Delhi","New Delhi"};
        String[] searchUserCountry={"India","India","India","India","India","India","India","India"};
        int[] searchUserImage={R.drawable.sample_match_img2,R.drawable.sample_match_img3,R.drawable.sample_match_img4,R.drawable.sample_match_img5,
                                R.drawable.sample_match_im,R.drawable.sample_match_img2,R.drawable.sample_match_img3,R.drawable.sample_match_img4,};

        for(int i=0;i<searchUserName.length;i++){
            SearchMatchesItemPojo searchMatchesItemPojo = new SearchMatchesItemPojo(searchUserName[i],searchUserAge[i],searchUserHeight[i],
                    searchUserLanguage[i],searchUserReligion[i],searchUserWorkArea[i],searchUserCity[i],searchUserCountry[i],searchUserImage[i]);
            list.add(searchMatchesItemPojo);
        }
        return  list;
    }*/
}
