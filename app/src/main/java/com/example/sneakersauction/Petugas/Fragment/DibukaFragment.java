package com.example.sneakersauction.Petugas.Fragment;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.example.sneakersauction.Administrator.Adapter.ListBarangAdapter;
import com.example.sneakersauction.Alarm.AlarmReceiver;
import com.example.sneakersauction.ApiHelper.ApiService;
import com.example.sneakersauction.ApiHelper.RetrofitClient;
import com.example.sneakersauction.ApiHelper.pojo.Model.Barang.Barang;
import com.example.sneakersauction.ApiHelper.pojo.Model.Lelang.Lelang;
import com.example.sneakersauction.ApiHelper.pojo.Response.Barang.BarangResponse;
import com.example.sneakersauction.ApiHelper.pojo.Response.Lelang.LelangResponse;
import com.example.sneakersauction.Masyarakat.Adapter.ListPenawarAdapter;
import com.example.sneakersauction.Masyarakat.DetailActivity;
import com.example.sneakersauction.Petugas.Adapter.ListLelangAdapter;
import com.example.sneakersauction.R;
import com.example.sneakersauction.SharedPreference.SharedPreference;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.ALARM_SERVICE;
import static android.graphics.Color.WHITE;

public class DibukaFragment  extends Fragment {
    View view;
    private RecyclerView mRecyclerView;
    private ListLelangAdapter mAdapter;
    ApiService mApiService;
    private List<Lelang> lelangList=new ArrayList<>();
    private List<Lelang> lelangList2=new ArrayList<>();
    SharedPreference sharedPreference;
    TextView  lottieAnimationView;
    RelativeLayout rl;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_lelang, container, false);

        StrictMode.ThreadPolicy policy=new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        sharedPreference=new SharedPreference(getContext());
        mRecyclerView = view.findViewById(R.id.rv_listpetugas);
        lottieAnimationView=view.findViewById(R.id.lottieAnimationView);
        rl=view.findViewById(R.id.rl);
        initComponent();
        initRetrofit();
        listItemLelang();
        functionGetLelang();
        functionGetLelang2();

//        if(res.getCount() == 0) {
//            showMessage("No Orders Yet","Let's Order Now");
//        }


        return view;
    }



    private void initComponent() {
        mRecyclerView=view.findViewById(R.id.rv_list_lelang);
    }
    private void initRetrofit() {
        mApiService = RetrofitClient.getClient(RetrofitClient.BASE_URL_API).create(ApiService.class);
    }


    private void functionGetLelang() {
        mApiService.listLelang("dibuka",sharedPreference.getSpRoleId(),sharedPreference.getSpUserId()).enqueue(new Callback<LelangResponse>() {
            @Override
            public void onResponse(Call<LelangResponse> call, Response<LelangResponse> response) {
                if (response.isSuccessful()) {

                    if (response.body().getStatus().equals(true)) {
                        lelangList = response.body().getData();



//                            view.setBackgroundColor(Color.parseColor("#EBEBEB"));
//                            lottieAnimationView.setVisibility(View.GONE);

                            mRecyclerView.setAdapter(new ListLelangAdapter(getContext(), lelangList,"dibuka"));


                        ArrayList<String> list=new ArrayList<>();
                        ArrayList<Integer> id_lelang=new ArrayList<>();
                        for (int i = 0; i < lelangList.size(); i++) {

                            list.add(lelangList.get(i).getTanggalDitutup());
                            id_lelang.add(lelangList.get(i).getIdLelang());
                            Intent myIntent = new Intent(getContext(), AlarmReceiver.class);
                            AlarmManager alarmManagers = (AlarmManager)getContext().getApplicationContext().getSystemService(Activity.ALARM_SERVICE);
                            myIntent.putExtra("tanggal",list);
                            myIntent.putExtra("id_lelang",id_lelang);
                            PendingIntent pendingIntent = PendingIntent.getBroadcast(getContext().getApplicationContext(), i, myIntent, 0);
                            Calendar calendar = Calendar.getInstance();
                            calendar.set(Calendar.SECOND,i);
                            alarmManagers = (AlarmManager)getContext().getApplicationContext().getSystemService(ALARM_SERVICE);
                            alarmManagers.set(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),pendingIntent);
                        }



                    }else{
                        lottieAnimationView.setText("There are no items at auction");
                    }
                }


            }

            @Override
            public void onFailure(Call<LelangResponse> call, Throwable t) {

                Toast.makeText(getContext(), "Internet connection problem", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void functionGetLelang2() {
        mApiService.listLelang("dibuka2",sharedPreference.getSpRoleId(),sharedPreference.getSpUserId()).enqueue(new Callback<LelangResponse>() {
            @Override
            public void onResponse(Call<LelangResponse> call, Response<LelangResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body().getStatus().equals(true)) {
                        lelangList2 = response.body().getData();
                        ArrayList<String> list=new ArrayList<>();
                        ArrayList<Integer> id_lelang=new ArrayList<>();
                        for (int i = 0; i < lelangList2.size(); i++) {

                            list.add(lelangList2.get(i).getTanggalDitutup());
                            id_lelang.add(lelangList2.get(i).getIdLelang());
                            Intent myIntent = new Intent(getContext(), AlarmReceiver.class);
                            AlarmManager alarmManagers = (AlarmManager)getContext().getApplicationContext().getSystemService(Activity.ALARM_SERVICE);
                            myIntent.putExtra("tanggal",list);
                            myIntent.putExtra("id_lelang",id_lelang);
                            PendingIntent pendingIntent = PendingIntent.getBroadcast(getContext().getApplicationContext(), i, myIntent, 0);
                            Calendar calendar = Calendar.getInstance();
                            calendar.set(Calendar.SECOND,i);
                            alarmManagers = (AlarmManager)getContext().getApplicationContext().getSystemService(ALARM_SERVICE);
                            alarmManagers.set(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),pendingIntent);
                        }



                    }else{

                    }
                }


            }

            @Override
            public void onFailure(Call<LelangResponse> call, Throwable t) {

                Toast.makeText(getContext(), "Koneksi internet bermasalah", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void listItemLelang() {


            mRecyclerView.setHasFixedSize(true);
            mAdapter =new ListLelangAdapter(getContext(), lelangList,"dibuka");
            GridLayoutManager mLayoutManager = new GridLayoutManager(getContext(),1);
            mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            mAdapter.notifyDataSetChanged();
            mRecyclerView.setLayoutManager(mLayoutManager);

    }
    public void showMessage(String title, String Message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.setMessage(Message);
        builder.show();
    }
    Handler handler = new Handler();
    Runnable runnable;
    int delay = 15*1000; //Delay for 15 seconds.  One second = 1000 milliseconds.


    @Override
    public void onResume() {
        //start handler as activity become visible

        handler.postDelayed( runnable = new Runnable() {
            public void run() {

                functionGetLelang();
                listItemLelang();

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
}
