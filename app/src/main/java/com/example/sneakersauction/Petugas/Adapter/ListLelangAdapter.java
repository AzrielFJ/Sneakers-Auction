package com.example.sneakersauction.Petugas.Adapter;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sneakersauction.Administrator.Adapter.ListBarangAdapter;
import com.example.sneakersauction.Alarm.AlarmReceiver;
import com.example.sneakersauction.ApiHelper.ApiService;
import com.example.sneakersauction.ApiHelper.RetrofitClient;
import com.example.sneakersauction.ApiHelper.pojo.Model.Barang.Barang;
import com.example.sneakersauction.ApiHelper.pojo.Model.Lelang.Lelang;
import com.example.sneakersauction.ApiHelper.pojo.Response.Lelang.LelangResponse;
import com.example.sneakersauction.Masyarakat.DetailActivity;
import com.example.sneakersauction.Petugas.DetailBarang;
import com.example.sneakersauction.R;
import com.example.sneakersauction.SharedPreference.SharedPreference;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.Stack;

import cn.iwgang.countdownview.CountdownView;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.ALARM_SERVICE;
import static com.example.sneakersauction.SharedPreference.SharedPreference.SP_NAME_BARANG;

public class ListLelangAdapter extends  RecyclerView.Adapter<ListLelangAdapter.GridViewHolder>  {
    Context mContext;
    List<Lelang> lelangist;
    SharedPreference sharedPreference;
    ApiService apiService;
    int tawaran_tertinggi;
    long countDownToNewYear;
    Date date2;
    String Status;
    public ListLelangAdapter(Context context, List<Lelang> lelangList,String status) {
        this.mContext = context;
        lelangist = lelangList;
        Status=status;
    }
    @Override
    public ListLelangAdapter.GridViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_lelang, parent, false);
        sharedPreference = new SharedPreference(mContext);
        apiService = RetrofitClient.getClient(RetrofitClient.BASE_URL_API).create(ApiService.class);

        return new ListLelangAdapter.GridViewHolder(view);


    }

    @Override
    public void onBindViewHolder(@NonNull ListLelangAdapter.GridViewHolder holder, int position) {
        final Lelang lelang = lelangist.get(position);
        Date now = new Date();
        now.getTime();

        holder.tv_nama.setText(lelang.getNamaBarang());

//        Calendar cal = Calendar.getInstance();
//
//        cal.setTimeInMillis(System.currentTimeMillis());
//        cal.clear();
//
//        cal.setTime(date2);
//        Toast.makeText(mContext,""+cal,Toast.LENGTH_LONG).show();

//        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");


//        ArrayList<Lelang> tanggal = new ArrayList<>();
//        tanggal.addAll(lelangist);
//        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//
//        try {
//            date2= sdf2.parse(tanggal.get(position).getTanggalDitutup());
//
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//        Set<String> set = new HashSet<String>();
//        set.add(tanggal.get(position).getTanggalDitutup());
//        sharedPreference.saveSPArray(SharedPreference.SP_TANGGAL,set);
//        Intent myIntent = new Intent(mContext, AlarmReceiver.class);
//        AlarmManager alarmManagers = (AlarmManager)mContext.getApplicationContext().getSystemService(Activity.ALARM_SERVICE);
//
//            PendingIntent pendingIntent = PendingIntent.getBroadcast(mContext.getApplicationContext(), 0, myIntent, 0);
//            Calendar calendar = Calendar.getInstance();
//            calendar.set(Calendar.SECOND,1);
//            alarmManagers = (AlarmManager)mContext.getApplicationContext().getSystemService(ALARM_SERVICE);
//            alarmManagers.set(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),pendingIntent);

        apiService.penawarTertinggi(lelang.getIdLelang()).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {

                    if (response.isSuccessful()) {
                        JSONObject jsonObject = new JSONObject(response.body().string());
                        if (jsonObject.getString("status").equals("true")) {
                            tawaran_tertinggi = jsonObject.getJSONObject("data").getInt("penawaran_harga");

                            Locale localeID = new Locale("in", "ID");
                            NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeID);
                            holder.tv_bid.setText("" + formatRupiah.format((double) tawaran_tertinggi));
                        } else{

                            Locale localeID = new Locale("in", "ID");
                            NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeID);
                            holder.tv_bid.setText("" + formatRupiah.format((double) lelang.getHargaAwal()));
                        }

                    }
                }catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
        Locale localeID = new Locale("in", "ID");
        NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeID);
        holder.tv_harga.setText(""+formatRupiah.format((double)lelang.getHargaAwal()));

        holder.tv_officer.setText(lelang.getNamaPetugas());
        Bitmap setFoto=null;
        String foto;
        if(lelang.getFoto().equals("")) {

            foto="assets/foto/user.png";
        }
        else {
            foto = ""+lelang.getFoto();
        }
        try{
            URL url=new URL(RetrofitClient.BASE_URL_FILE + foto);
            setFoto= BitmapFactory.decodeStream(url.openConnection().getInputStream());
        }catch (IOException e){
            e.printStackTrace();
        }
        holder.imageView.setImageBitmap(setFoto);
        if (Status.equals("dibuka")){
            if(sharedPreference.getSpRoleId()==3) {
                holder.cv_main2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        sharedPreference.saveSPInt(String.valueOf(SharedPreference.SP_BARANG_ID), lelang.getIdBarang());
                        sharedPreference.saveSPInt(String.valueOf(SharedPreference.SP_HARGA_BARANG), lelang.getHargaAwal());
                        sharedPreference.saveSPInt(String.valueOf(SharedPreference.SP_ID_LELANG),lelang.getIdLelang());

                        sharedPreference.saveSPString(SP_NAME_BARANG, lelang.getNamaBarang());

                        Intent intent = new Intent(mContext, DetailActivity.class);
                        intent.putExtra("id_lelang",lelang.getIdLelang());
                        intent.putExtra("id_barang", lelang.getIdBarang());
                        intent.putExtra("nama", lelang.getNamaBarang());
                        intent.putExtra("kode",0);
                        intent.putExtra("harga", lelang.getHargaAwal());
                        intent.putExtra("gambar", lelang.getFoto());
                        intent.putExtra("desc", lelang.getDeskripsiBarang());
                        mContext.startActivity(intent);
                    }
                });
            }
        }


        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String countDate = lelang.getTanggalDitutup();
        String countDate2 = lelang.getTanggalDibuka();



        try {
            //Formatting from String to Date
            Date date = sdf.parse(countDate);
            Date date2=sdf.parse(countDate2);
            long currentTime = now.getTime();
            long newYearDate = date.getTime();
             countDownToNewYear= newYearDate - currentTime;
            holder.mCvCountdownView.start(countDownToNewYear);

        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (Status.equals("ditutup")){
            holder.mCvCountdownView.setVisibility(View.GONE);
            holder.tv_win.setVisibility(View.VISIBLE);
            holder.batas_win.setVisibility(View.VISIBLE);
            holder.tv_win2.setVisibility(View.VISIBLE);
            holder.tv_win2.setText(""+lelang.getNamaLengkap());
            if (sharedPreference.getSpRoleId()==2){
                holder.cv_main2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        sharedPreference.saveSPInt(String.valueOf(SharedPreference.SP_BARANG_ID), lelang.getIdBarang());
                        sharedPreference.saveSPInt(String.valueOf(SharedPreference.SP_HARGA_BARANG), lelang.getHargaAwal());
                        sharedPreference.saveSPInt(String.valueOf(SharedPreference.SP_ID_LELANG),lelang.getIdLelang());

                        sharedPreference.saveSPString(SP_NAME_BARANG, lelang.getNamaBarang());

                        Intent intent = new Intent(mContext, DetailBarang.class);
                        intent.putExtra("id_lelang",lelang.getIdLelang());
                        intent.putExtra("id_barang", lelang.getIdBarang());
                        intent.putExtra("nama", lelang.getNamaBarang());
                        intent.putExtra("kode",1);
                        intent.putExtra("id_user",lelang.getUserId());
                        intent.putExtra("nama_win",lelang.getNamaLengkap());
                        intent.putExtra("harga", lelang.getHargaAwal());
                        intent.putExtra("gambar", lelang.getFoto());
                        intent.putExtra("desc", lelang.getDeskripsiBarang());
                        mContext.startActivity(intent);
                    }
                });
            }else{
                holder.cv_main2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        sharedPreference.saveSPInt(String.valueOf(SharedPreference.SP_BARANG_ID), lelang.getIdBarang());
                        sharedPreference.saveSPInt(String.valueOf(SharedPreference.SP_HARGA_BARANG), lelang.getHargaAwal());
                        sharedPreference.saveSPInt(String.valueOf(SharedPreference.SP_ID_LELANG),lelang.getIdLelang());

                        sharedPreference.saveSPString(SP_NAME_BARANG, lelang.getNamaBarang());

                        Intent intent = new Intent(mContext, DetailActivity.class);
                        intent.putExtra("id_lelang",lelang.getIdLelang());
                        intent.putExtra("id_barang", lelang.getIdBarang());
                        intent.putExtra("nama", lelang.getNamaBarang());
                        intent.putExtra("kode",1);
                        intent.putExtra("harga", lelang.getHargaAwal());
                        intent.putExtra("gambar", lelang.getFoto());
                        intent.putExtra("desc", lelang.getDeskripsiBarang());
                        mContext.startActivity(intent);
                    }
                });
            }
        }
//        if (Status.equals("dibuka")) {
//
//
//
//            SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//            try {
//                date2 = sdf2.parse(lelang.getTanggalDitutup());
//
//            } catch (ParseException e) {
//                e.printStackTrace();
//            }
//            int result = now.compareTo(date2);
//            if (result > 0) {
//
//            }
//        }

    }
    @Override
    public int getItemCount() {
        return lelangist.size();
    }
    public class GridViewHolder extends RecyclerView.ViewHolder{
        TextView tv_nama;
        TextView tv_harga;
        TextView tv_bid;
        TextView tv_officer;
        TextView tv_win;
        ImageView imageView;
        CountdownView mCvCountdownView;
        CardView cv_main2;
        View batas_win;
        TextView tv_win2;
        public GridViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_nama=itemView.findViewById(R.id.tv_nama_barang2);
            tv_harga=itemView.findViewById(R.id.tv_harga2);
            tv_bid=itemView.findViewById(R.id.tv_bid2);
            imageView=itemView.findViewById(R.id.iv_gambar);
            tv_officer=itemView.findViewById(R.id.tv_officer2);
            cv_main2=itemView.findViewById(R.id.cardview2);
            mCvCountdownView=itemView.findViewById(R.id.mycountdown);
            tv_win=itemView.findViewById(R.id.tv_win);
            batas_win=itemView.findViewById(R.id.batas_win);
            tv_win2=itemView.findViewById(R.id.tv_win2);
        }
    }

}


