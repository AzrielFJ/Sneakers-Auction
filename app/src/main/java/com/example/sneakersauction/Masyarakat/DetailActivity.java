package com.example.sneakersauction.Masyarakat;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.example.sneakersauction.Administrator.Adapter.ListBarangAdapter;
import com.example.sneakersauction.ApiHelper.ApiService;
import com.example.sneakersauction.ApiHelper.RetrofitClient;
import com.example.sneakersauction.ApiHelper.pojo.Model.Barang.Barang;
import com.example.sneakersauction.ApiHelper.pojo.Model.Barang.Foto;
import com.example.sneakersauction.ApiHelper.pojo.Model.Lelang.Penawar;
import com.example.sneakersauction.ApiHelper.pojo.Response.Barang.BarangResponse;
import com.example.sneakersauction.ApiHelper.pojo.Response.Barang.FotoResponse;
import com.example.sneakersauction.ApiHelper.pojo.Response.Lelang.PenawarResponse;
import com.example.sneakersauction.Masyarakat.Adapter.ListPenawarAdapter;
import com.example.sneakersauction.Masyarakat.Adapter.ViewPagerAdapterDetail;
import com.example.sneakersauction.Masyarakat.Fragment.LelangFragment;
import com.example.sneakersauction.Petugas.DetailBarang;
import com.example.sneakersauction.R;
import com.example.sneakersauction.SharedPreference.SharedPreference;

import java.io.IOException;
import java.net.URL;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailActivity extends AppCompatActivity implements LelangFragment.ExampleDialogListener {
        TextView nama,harga,desc;
        ImageView gambar;
        String Nama;
        Toolbar mToolbar;
        Button bs;
        int Harga;
        String Desc;
        String Gambar;
        RecyclerView recyclerView;
        ApiService mApiInterface;
        Context mContext;
        SharedPreference sharedPrefManager;
        int id_petugas,id_barang,id_lelang;
        ListPenawarAdapter listPenawarAdapter;
        private List<Penawar> penawarList=new ArrayList<>();
        TextView tv;
        View view,view2;
        TextView[] dot;
        ViewPager viewPager;
        int kode;
        private List<Foto> arrayList=new ArrayList<>();
        LinearLayout sliderDotspanel;
        @Override
        protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        mContext=this;
        mApiInterface= RetrofitClient.getClient(RetrofitClient.BASE_URL_API).create(ApiService.class);
        sharedPrefManager = new SharedPreference(DetailActivity.this);
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        sliderDotspanel = (LinearLayout) findViewById(R.id.SliderDots);
                kode=getIntent().getIntExtra("kode",1);
        id_lelang=getIntent().getIntExtra("id_lelang",1);
        initComponent();
        parseData();
        toolBar();
        listPenawar();
        functionGetPenawar();
        id_barang=getIntent().getIntExtra("id_barang",1);

        mApiInterface.getFoto(id_barang).enqueue(new Callback<FotoResponse>() {
                @Override
                public void onResponse(Call<FotoResponse> call, Response<FotoResponse> response) {
                        if (response.isSuccessful()){
                                arrayList=response.body().getData();
                                ViewPagerAdapterDetail mAdapter = new ViewPagerAdapterDetail(DetailActivity.this, arrayList);
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

        id_petugas=sharedPrefManager.getSpPetugasId();


        }

        private void functionGetPenawar() {
                mApiInterface.penawar(id_lelang).enqueue(new Callback<PenawarResponse>() {
                        @Override
                        public void onResponse(Call<PenawarResponse> call, Response<PenawarResponse> response) {
                                if (response.isSuccessful()) {
                                        if (response.body().getStatus().equals(true)) {
                                                penawarList = response.body().getData();
                                                recyclerView.setAdapter(new ListPenawarAdapter(DetailActivity.this, penawarList));
                                                listPenawarAdapter.notifyDataSetChanged();
                                                sharedPrefManager.saveSPInt(String.valueOf(SharedPreference.SP_BID), penawarList.get(0).getPenawaranHarga());


                                        }else {
                                                sharedPrefManager.saveSPInt(String.valueOf(SharedPreference.SP_BID), Harga);
                                        }
                                }
                        }

                        @Override
                        public void onFailure(Call<PenawarResponse> call, Throwable t) {

                                Toast.makeText(DetailActivity.this, "Koneksi internet bermasalah", Toast.LENGTH_SHORT).show();
                        }
                });
        }

        private void listPenawar() {

                        recyclerView.setHasFixedSize(true);
                        listPenawarAdapter =new ListPenawarAdapter(DetailActivity.this, penawarList);
                        LinearLayoutManager mLayoutManager = new LinearLayoutManager(DetailActivity.this);
                        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                        listPenawarAdapter.notifyDataSetChanged();
                        recyclerView.setLayoutManager(mLayoutManager);
                        DividerItemDecoration verticalDecoration = new DividerItemDecoration(recyclerView.getContext(),
                                DividerItemDecoration.VERTICAL);
                        Drawable verticalDivider = ContextCompat.getDrawable(this, R.drawable.line_divider);
                        verticalDecoration.setDrawable(verticalDivider);
                        recyclerView.addItemDecoration(verticalDecoration);


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
                nama = findViewById(R.id.tv_namaBarang_detail);
                harga = findViewById(R.id.tv_harga_detail);
                desc = findViewById(R.id.tv_deskripsi_detail);
//                gambar = findViewById(R.id.iv_gambar_detail);
                recyclerView=findViewById(R.id.rv_penawar);
                recyclerView.setVisibility(View.VISIBLE);
                tv=findViewById(R.id.tv_list);
                view=findViewById(R.id.view);
                view2=findViewById(R.id.view2);

                view.setVisibility(View.VISIBLE);
                view2.setVisibility(View.VISIBLE);
                tv.setVisibility(View.VISIBLE);
                bs=findViewById(R.id.bt_lelang);
                if (kode==1){
                        bs.setText("Back");
                        bs.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                        finish();
                                }
                        });
                }
                else{
                        bs.setText("Bid");
                        bs.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                        openDialog();
                                }
                        });
                }
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
//                gambar.setImageBitmap(setFoto);
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
                        public void onDismiss(DialogInterface dialog) {functionGetPenawar();
                        }
                });
                lelangFragment.show(DetailActivity.this.getSupportFragmentManager(), "LelangFragment");
        }
        @Override
        public void applyTexts(String username, String password) {

        }

        @Override
        public void onPointerCaptureChanged(boolean hasCapture) {

        }
    Handler handler = new Handler();
    Runnable runnable;
    int delay = 4*1000; //Delay for 15 seconds.  One second = 1000 milliseconds.


    @Override
    public void onResume() {
        //start handler as activity become visible

        handler.postDelayed( runnable = new Runnable() {
            public void run() {
                //do something
                functionGetPenawar();


                handler.postDelayed(runnable, delay);
            }
        }, delay);

        super.onResume();
    }

// If onPause() is not included the threads will double up when you
// reload the activity

    @Override
    public void onPause() {
        handler.removeCallbacks(runnable); //stop handler when activity not visible
        super.onPause();
    }

        private void dots(int page_position) {
                dot = new TextView[arrayList.size()];
                sliderDotspanel.removeAllViews();

                for (int i = 0; i < dot.length; i++) {;
                        dot[i] = new TextView(DetailActivity.this);
                        dot[i].setText(Html.fromHtml("&#9673;"));
                        dot[i].setTextSize(35);
                        dot[i].setTextColor(Color.rgb(255,255,255));
                        sliderDotspanel.addView(dot[i]);

                }
                //active dot



                dot[page_position].setTextColor(getResources().getColor(R.color.colorPrimary));


        }

}