package com.example.acerpc.wedknot;

import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


public class ImageSliderMainActivity extends PagerAdapter {

    LayoutInflater inflater;
    Context context;

    public ImageSliderMainActivity(Context context) {
        this.context = context;
    }

    private int[] imageid = {R.drawable.myicon,R.drawable.couple,R.drawable.couplecar};
    //WEDKNOT
    private int[] textid = {R.string.first,R.string.second,R.string.third};

    @Override
    public int getCount() {
        return imageid.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return (view==(LinearLayout)object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.slider_main_activity,container,false);
        LinearLayout linearLayout = (LinearLayout) view.findViewById(R.id.linearslider);
        ImageView imageView = (ImageView) view.findViewById(R.id.sliderImage);
        TextView textView = (TextView) view.findViewById(R.id.slidertext);
        textView.setTypeface(null,Typeface.BOLD);
        textView.setText(textid[position]);
        imageView.setImageResource(imageid[position]);
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((LinearLayout) object);
    }
}
