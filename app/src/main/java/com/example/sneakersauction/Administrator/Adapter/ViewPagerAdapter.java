package com.example.sneakersauction.Administrator.Adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.example.sneakersauction.ApiHelper.FileUtil;
import com.example.sneakersauction.R;

import java.util.ArrayList;

public class ViewPagerAdapter extends PagerAdapter {

    private Context context;
    private LayoutInflater layoutInflater;
    private ArrayList<Uri> arrayList;

    public ViewPagerAdapter(Context context ,ArrayList<Uri> arrayLists) {
        this.context = context;
        this.arrayList = arrayLists;
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {

        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.custom_layout, null);


        ImageView imageView = view.findViewById(R.id.imageView);
//        ImageView button=view.findViewById(R.id.bt_delete);
//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                arrayList.remove(position);
//                notifyDataSetChanged();
//
//            }
//        });
        Glide.with(context)
                .load(arrayList.get(position))
                .into(imageView);


        ViewPager vp = (ViewPager) container;
        vp.addView(view, 0);
        return view;

    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {

        ViewPager vp = (ViewPager) container;
        View view = (View) object;
        vp.removeView(view);

    }
    @Override
    public int getItemPosition(Object object){
        int res = super.getItemPosition(object);

            res = PagerAdapter.POSITION_NONE;

        return res;
    }
}