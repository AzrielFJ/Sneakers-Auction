package com.example.sneakersauction.Petugas;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.sneakersauction.Administrator.AdminActivity;

import com.example.sneakersauction.Administrator.Fragment.TambahBarang;
import com.example.sneakersauction.Alarm.AlarmReceiver;
import com.example.sneakersauction.ApiHelper.ApiService;
import com.example.sneakersauction.ApiHelper.RetrofitClient;
import com.example.sneakersauction.ApiHelper.pojo.Model.Lelang.Lelang;
import com.example.sneakersauction.ApiHelper.pojo.Response.Lelang.LelangResponse;
import com.example.sneakersauction.LoginRegister.LoginRegisterActivity;
import com.example.sneakersauction.Petugas.Fragment.ListBarangFragment;
import com.example.sneakersauction.Petugas.Fragment.ListLelangFragment;
import com.example.sneakersauction.Petugas.Fragment.ProfileFragment;
import com.example.sneakersauction.R;
import com.example.sneakersauction.SharedPreference.SharedPreference;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import nl.joery.animatedbottombar.AnimatedBottomBar;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PetugasActivity extends AppCompatActivity implements TambahBarang.ExampleDialogListener {
    private static final String TAG = AdminActivity.class.getSimpleName();
    AnimatedBottomBar animatedBottomBar;
    FragmentManager fragmentManager;
    ApiService mApiService;
    private List<Lelang> lelangList2=new ArrayList<>();
    SharedPreference sharedPreference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_petugas);
        mApiService= RetrofitClient.getClient(RetrofitClient.BASE_URL_API).create(ApiService.class);
        sharedPreference=new SharedPreference(this);
        mApiService.listLelang("dibuka2",sharedPreference.getSpRoleId(),sharedPreference.getSpUserId()).enqueue(new Callback<LelangResponse>() {
            @Override
            public void onResponse(Call<LelangResponse> call, Response<LelangResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body().getStatus().equals("true")) {
                        lelangList2 = response.body().getData();
                        ArrayList<String> list=new ArrayList<>();
                        ArrayList<Integer> id_lelang=new ArrayList<>();
                        for (int i = 0; i < lelangList2.size(); i++) {

                            list.add(lelangList2.get(i).getTanggalDitutup());
                            id_lelang.add(lelangList2.get(i).getIdLelang());
                            Intent myIntent = new Intent(PetugasActivity.this, AlarmReceiver.class);
                            AlarmManager alarmManagers = (AlarmManager) PetugasActivity.this.getApplicationContext().getSystemService(Activity.ALARM_SERVICE);
                            myIntent.putExtra("tanggal",list);
                            myIntent.putExtra("id_lelang",id_lelang);
                            PendingIntent pendingIntent = PendingIntent.getBroadcast(PetugasActivity.this.getApplicationContext(), i, myIntent, 0);
                            Calendar calendar = Calendar.getInstance();
                            calendar.set(Calendar.SECOND,i);
                            alarmManagers = (AlarmManager)PetugasActivity.this.getApplicationContext().getSystemService(ALARM_SERVICE);
                            alarmManagers.set(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),pendingIntent);
                        }



                    }
                }


            }

            @Override
            public void onFailure(Call<LelangResponse> call, Throwable t) {

                Toast.makeText(PetugasActivity.this, "Koneksi internet bermasalah", Toast.LENGTH_SHORT).show();
            }
        });
        animatedBottomBar = findViewById(R.id.nav_view_petugas);

            animatedBottomBar.selectTabById(R.id.navigation_home2, true);
            animatedBottomBar.setTabAnimation(AnimatedBottomBar.TabAnimation.SLIDE);
            animatedBottomBar.setIconSize(40);

            animatedBottomBar.setTextAppearance(R.style.CustomText);
            fragmentManager = getSupportFragmentManager();
             ListBarangFragment profileFragment = new ListBarangFragment();
            fragmentManager.beginTransaction().replace(R.id.nav_host_fragment_petugas, profileFragment)
                    .commit();

        animatedBottomBar.setOnTabSelectListener(new AnimatedBottomBar.OnTabSelectListener() {
            @Override
            public void onTabReselected(int i, @NotNull AnimatedBottomBar.Tab tab) {

            }

            @Override
            public void onTabSelected(int lastIndex, @Nullable AnimatedBottomBar.Tab lastTab, int newIndex, @NotNull AnimatedBottomBar.Tab newTab) {
                Fragment fragment = null;
                switch (newTab.getId()) {
                    case R.id.navigation_home2:
                        fragment = new ListBarangFragment();
                        break;
                    case R.id.navigation_lelang:
                        fragment = new ListLelangFragment();
                        break;
                    case R.id.navigation_profile:
                        fragment = new ProfileFragment();
                        break;



                }

                if (fragment != null) {
                    fragmentManager = getSupportFragmentManager();
                    fragmentManager.beginTransaction().replace(R.id.nav_host_fragment_petugas, fragment)
                            .commit();
                } else {
                    Log.e(TAG, "Error in creating Fragment");
                }
            }
        });
    }
    @Override
    public void applyTexts(String username, String password) {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }


}
