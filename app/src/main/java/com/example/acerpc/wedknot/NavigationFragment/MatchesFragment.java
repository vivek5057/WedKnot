package com.example.acerpc.wedknot.NavigationFragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.acerpc.wedknot.MatchesFragmentInside.PartnerListFragment;
import com.example.acerpc.wedknot.R;

public class MatchesFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_matches, container, false);
        getFragmentManager().beginTransaction().replace(R.id.matches_framelayout,new PartnerListFragment()).commit();

        return view;
    }


}
