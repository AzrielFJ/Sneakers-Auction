package com.example.sneakersauction.Petugas.Fragment;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.example.sneakersauction.Alarm.AlarmReceiver;
import com.example.sneakersauction.ApiHelper.ApiService;
import com.example.sneakersauction.ApiHelper.RetrofitClient;
import com.example.sneakersauction.ApiHelper.pojo.Model.Lelang.Lelang;
import com.example.sneakersauction.ApiHelper.pojo.Response.Lelang.LelangResponse;
import com.example.sneakersauction.Petugas.Adapter.ListLelangAdapter;
import com.example.sneakersauction.R;
import com.example.sneakersauction.SharedPreference.SharedPreference;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.ALARM_SERVICE;

public class DitutupFragment extends Fragment {
    View view;
    private RecyclerView mRecyclerView;
    private ListLelangAdapter mAdapter;
    ApiService mApiService;
    private List<Lelang> lelangList=new ArrayList<>();
    SharedPreference sharedPreference;
    Date now,date2;
    TextView lottieAnimationView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_lelang, container, false);
        sharedPreference=new SharedPreference(getContext());
        mRecyclerView = view.findViewById(R.id.rv_listpetugas);
        lottieAnimationView=view.findViewById(R.id.lottieAnimationView);
        initComponent();
        initRetrofit();
        listItemLelang();
        functionGetLelang();


        return view;
    } private void initComponent() {
        mRecyclerView=view.findViewById(R.id.rv_list_lelang);
    }
    private void initRetrofit() {
        mApiService = RetrofitClient.getClient(RetrofitClient.BASE_URL_API).create(ApiService.class);
    }


    private void functionGetLelang() {

        mApiService.listLelang("ditutup",sharedPreference.getSpRoleId(),sharedPreference.getSpUserId()).enqueue(new Callback<LelangResponse>() {
            @Override
            public void onResponse(Call<LelangResponse> call, Response<LelangResponse> response) {
                if (response.isSuccessful()) {

                    if (response.body().getStatus().equals(true)) {
                        lelangList = response.body().getData();
                        mRecyclerView.setAdapter(new ListLelangAdapter(getContext(), lelangList,"ditutup"));


//                            view.setBackgroundColor(Color.parseColor("#EBEBEB"));
//                            lottieAnimationView.setVisibility(View.GONE);

                            mRecyclerView.setAdapter(new ListLelangAdapter(getContext(), lelangList,"ditutup"));
                    }else{
                        lottieAnimationView.setText("There are no items at auction");
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
        mAdapter =new ListLelangAdapter(getContext(), lelangList,"ditutup");
        GridLayoutManager mLayoutManager = new GridLayoutManager(getContext(),1);
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mAdapter.notifyDataSetChanged();
        mRecyclerView.setLayoutManager(mLayoutManager);

    }
    public void showMessage(String title,String Message){
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
}