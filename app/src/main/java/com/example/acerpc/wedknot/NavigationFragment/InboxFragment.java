package com.example.acerpc.wedknot.NavigationFragment;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.acerpc.wedknot.InboxFragmentInside.InboxFragmentPagerAdapter;
import com.example.acerpc.wedknot.R;


public class InboxFragment extends Fragment {

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_inbox, container, false);

        ViewPager viewPager = view.findViewById(R.id.inbox_viewpager);
        InboxFragmentPagerAdapter adapter = new InboxFragmentPagerAdapter(getChildFragmentManager());
        viewPager.setAdapter(adapter);
        // Give the TabLayout to ViewPager
        TabLayout tabLayout = (TabLayout) view.findViewById(R.id.inbox_tab_layout);
        tabLayout.setupWithViewPager(viewPager);

        // Find and set empty view on the ListView, so that it only shows when the list has 0 items.
        /*View emptyView = findViewById(R.id.empty_view);
        petListView.setEmptyView(emptyView);*/
        return view;
    }


}
