package com.example.sneakersauction.Alarm;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TaskInfo;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.example.sneakersauction.Administrator.Adapter.ListBarangAdapter;
import com.example.sneakersauction.ApiHelper.ApiService;
import com.example.sneakersauction.ApiHelper.RetrofitClient;
import com.example.sneakersauction.ApiHelper.pojo.Model.Lelang.Lelang;
import com.example.sneakersauction.ApiHelper.pojo.Response.Lelang.PenawarResponse;
import com.example.sneakersauction.Masyarakat.DetailActivity;
import com.example.sneakersauction.Petugas.Adapter.ListLelangAdapter;
import com.example.sneakersauction.SharedPreference.SharedPreference;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Set;
import java.util.logging.Logger;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.ALARM_SERVICE;


public class AlarmReceiver extends BroadcastReceiver {
    SharedPreference sharedPreference;
    ArrayList<String> tanggal = new ArrayList<>();
    ArrayList<Integer> id_lelang = new ArrayList<>();
    Date now;
    Date date2;
    ApiService apiService;
    @Override
    public void onReceive(Context context, Intent intent) {
     sharedPreference=new SharedPreference(context);
     apiService=RetrofitClient.getClient(RetrofitClient.BASE_URL_API).create(ApiService.class);
     now = new Date();
     now.getTime();
    tanggal = (ArrayList<String>) intent.getSerializableExtra("tanggal");
    id_lelang=(ArrayList<Integer>) intent.getSerializableExtra("id_lelang");
//        Toast.makeText(context,""+tanggal,Toast.LENGTH_SHORT).show();
        for(int i=0;i<tanggal.size();i++){
            SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            try {
                date2= sdf2.parse(tanggal.get(i));

            } catch (ParseException e) {
                e.printStackTrace();
            }
            int result = now.compareTo(date2);
            if (result == 0) {
                Log.d("tanggal","Date1 is equal to Date2");
//                Toast.makeText(context,"",Toast.LENGTH_SHORT).show();
            } if (result > 0) {
                Log.d("tanggal_setelah","Date1 is after to Date2");
//                Toast.makeText(context,"Date1 is after Date2",Toast.LENGTH_SHORT).show();

                apiService.tutupLelang(id_lelang.get(i)).enqueue(new Callback<PenawarResponse>() {
                    @Override
                    public void onResponse(Call<PenawarResponse> call, Response<PenawarResponse> response) {
                        if (response.isSuccessful()){
                            if (response.body().getStatus().equals(true)){
//                                Toast.makeText(context,"Success",Toast.LENGTH_SHORT).show();
                                tanggal.clear();
                                id_lelang.clear();

                            }else{

                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<PenawarResponse> call, Throwable t) {

                    }
                });
            } if (result < 0) {
//                Log.d("tanggal_sebelum","Date1 is before to Date2");
//                Toast.makeText(context,"Date1 is before Date2",Toast.LENGTH_SHORT).show();
            }
        }
    }
}
