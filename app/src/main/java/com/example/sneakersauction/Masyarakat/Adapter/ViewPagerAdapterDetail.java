package com.example.sneakersauction.Masyarakat.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.example.sneakersauction.ApiHelper.RetrofitClient;
import com.example.sneakersauction.ApiHelper.pojo.Model.Barang.Foto;
import com.example.sneakersauction.R;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class ViewPagerAdapterDetail extends PagerAdapter {

    private Context context;
    private LayoutInflater layoutInflater;
    private List<Foto> arrayList;
    String foto;
    public ViewPagerAdapterDetail(Context context, List<Foto> arrayLists) {
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
        Foto fotos=arrayList.get(position);
        Bitmap setFoto=null;
        ImageView imageView = view.findViewById(R.id.imageView);
        if(fotos.getFoto().isEmpty()) {

            foto="assets/foto/user.png";
        }
        else {
            foto = ""+fotos.getFoto();
        }
        try{
            URL url=new URL(RetrofitClient.BASE_URL_FILE + foto);
            setFoto= BitmapFactory.decodeStream(url.openConnection().getInputStream());
        }catch (IOException e){
            e.printStackTrace();
        }
        imageView.setImageBitmap(setFoto);


//        ImageView button=view.findViewById(R.id.bt_delete);
//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                arrayList.remove(position);
//                notifyDataSetChanged();
//
//            }
//        });
//        Glide.with(context)
//                .load(arrayList.get(position))
//                .into(imageView);


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
    public int getItemPosition(Object object) {
        int res = super.getItemPosition(object);

        res = PagerAdapter.POSITION_NONE;

        return res;
    }
}