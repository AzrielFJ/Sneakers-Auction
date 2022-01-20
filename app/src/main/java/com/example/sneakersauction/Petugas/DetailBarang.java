package com.example.sneakersauction.Petugas;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.TransitionOptions;
import com.example.sneakersauction.Administrator.Adapter.ViewPagerAdapter;
import com.example.sneakersauction.Administrator.Fragment.TambahBarang;
import com.example.sneakersauction.Administrator.TambahBarangActivity;
import com.example.sneakersauction.ApiHelper.ApiService;
import com.example.sneakersauction.ApiHelper.RetrofitClient;
import com.example.sneakersauction.ApiHelper.pojo.Model.Barang.Foto;
import com.example.sneakersauction.ApiHelper.pojo.Model.Lelang.Lelang;
import com.example.sneakersauction.ApiHelper.pojo.Response.Barang.FotoResponse;
import com.example.sneakersauction.Masyarakat.Adapter.ViewPagerAdapterDetail;
import com.example.sneakersauction.Petugas.Fragment.LelangFragment;
import com.example.sneakersauction.R;
import com.example.sneakersauction.SharedPreference.SharedPreference;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailBarang extends AppCompatActivity implements LelangFragment.ExampleDialogListener {
    TextView nama,harga,qty,desc;
    ImageView gambar;
    String Nama;
    Toolbar mToolbar;
    Button bs;
    int Harga;
    String Desc;
    String Gambar;
    TextView[] dot;
    ViewPager viewPager;
    private List<Foto> arrayList=new ArrayList<>();
    LinearLayout sliderDotspanel;
    ApiService mApiInterface;
    Context mContext;
    SharedPreference sharedPrefManager;
    int id_petugas,id_barang;
    View view;
    View view4;
    TextView batas_win;
    TextView tv_win2;
    TextView tv_win;
    View batas_id;
    TextView tv_id2;
    TextView tv_list;
    TextView tv_id;
    int kode;
    int id_user;
    String nama_win;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_detail);
        mContext=this;
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        sliderDotspanel = (LinearLayout) findViewById(R.id.SliderDots);
        mApiInterface= RetrofitClient.getClient(RetrofitClient.BASE_URL_API).create(ApiService.class);
        sharedPrefManager = new SharedPreference(this);
//        view=findViewById(R.id.view);
//        view.setVisibility(View.GONE);
        id_barang=getIntent().getIntExtra("id_barang",1);
        id_user=getIntent().getIntExtra("id_user",1);
        nama_win=getIntent().getStringExtra("nama_win");
        kode=getIntent().getIntExtra("kode",1);
        mApiInterface.getFoto(id_barang).enqueue(new Callback<FotoResponse>() {
            @Override
            public void onResponse(Call<FotoResponse> call, Response<FotoResponse> response) {
                if (response.isSuccessful()){
                    arrayList=response.body().getData();
                    ViewPagerAdapterDetail mAdapter = new ViewPagerAdapterDetail(DetailBarang.this, arrayList);
                    viewPager.setAdapter(mAdapter);
                    dots(0);
                    viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                        @Override
                        public void onPageScrolled(int i, float v, int i1) {

                        }

                        @Override
                        public void onPageSelected(int i) {
                            dots(i);

                        }

                        @Override
                        public void onPageScrollStateChanged(int i) {

                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<FotoResponse> call, Throwable t) {

            }
        });


//        Toast.makeText(this,arrayList.get(0),Toast.LENGTH_SHORT).show();
        initComponent();
        parseData();
        toolBar();



        id_petugas=sharedPrefManager.getSpPetugasId();


    }



    private void toolBar() {
        mToolbar = findViewById(R.id.toolbar);
        mToolbar.setNavigationIcon(R.drawable.ic_back);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("");
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {


        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        return super.onOptionsItemSelected(item);
    }

    private void initComponent() {
        bs=findViewById(R.id.bt_lelang);
        nama = findViewById(R.id.tv_namaBarang_detail);
        harga = findViewById(R.id.tv_harga_detail);
        desc = findViewById(R.id.tv_deskripsi_detail);
        tv_id=findViewById(R.id.tv_id);
        tv_win=findViewById(R.id.tv_win);
        tv_id2=findViewById(R.id.tv_id2);
        tv_win2=findViewById(R.id.tv_win2);
        batas_id=findViewById(R.id.batas_id);
        batas_win=findViewById(R.id.batas_win);
        view4=findViewById(R.id.view4);
        tv_list=findViewById(R.id.tv_list);
        if (kode==0){
            tv_id.setVisibility(View.GONE);
            tv_id2.setVisibility(View.GONE);
            tv_win.setVisibility(View.GONE);
            tv_win2.setVisibility(View.GONE);
            batas_win.setVisibility(View.GONE);
            batas_id.setVisibility(View.GONE);
             view4.setVisibility(View.GONE);
             tv_list.setVisibility(View.GONE);
            bs.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    openDialog();
                }
            });
        }else{
            tv_win2.setText(nama_win);
            tv_id2.setText(""+id_user);
            bs.setText("Back");
            bs.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
        }
//        gambar = findViewById(R.id.iv_gambar_detail);

    }
    private void parseData() {
        Gambar=getIntent().getStringExtra("gambar");
        Bitmap setFoto=null;
        String foto;
        if(Gambar=="") {
            foto="assets/foto/user.png";
        }
        else {
            foto = Gambar;
        }
        try{
            URL url=new URL(RetrofitClient.BASE_URL_FILE + foto);
            setFoto= BitmapFactory.decodeStream(url.openConnection().getInputStream());
        }catch (IOException e){
            e.printStackTrace();
        }
//        gambar.setImageBitmap(setFoto);
        Nama=getIntent().getStringExtra("nama");
        Harga=getIntent().getIntExtra("harga",1);
        Desc=getIntent().getStringExtra("desc");
        nama.setText(Nama);
        Locale localeID = new Locale("in", "ID");
        NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeID);
        harga.setText(formatRupiah.format((double)Harga));
        desc.setText(Desc);


    }
    private void openDialog() {
        LelangFragment lelangFragment=new LelangFragment();
        lelangFragment.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {

            }
        });
        lelangFragment.show(DetailBarang.this.getSupportFragmentManager(), "LelangFragment");
    }
    @Override
    public void applyTexts(String username, String password) {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
    private void dots(int page_position) {
        dot = new TextView[arrayList.size()];
        sliderDotspanel.removeAllViews();

        for (int i = 0; i < dot.length; i++) {;
            dot[i] = new TextView(DetailBarang.this);
            dot[i].setText(Html.fromHtml("&#9673;"));
            dot[i].setTextSize(35);
            dot[i].setTextColor(Color.rgb(255,255,255));
            sliderDotspanel.addView(dot[i]);

        }
        //active dot



        dot[page_position].setTextColor(getResources().getColor(R.color.colorPrimary));


    }


}









