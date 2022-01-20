package com.example.sneakersauction.LoginRegister;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.Manifest;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sneakersauction.Administrator.AdminActivity;
import com.example.sneakersauction.Alarm.AlarmReceiver;
import com.example.sneakersauction.ApiHelper.ApiService;
import com.example.sneakersauction.ApiHelper.RetrofitClient;
import com.example.sneakersauction.ApiHelper.pojo.Model.Lelang.Lelang;
import com.example.sneakersauction.ApiHelper.pojo.Response.Lelang.LelangResponse;
import com.example.sneakersauction.LoginRegister.Fragment.LoginFragment;
import com.example.sneakersauction.LoginRegister.Fragment.RegisterFragment;
import com.example.sneakersauction.MainActivity;
import com.example.sneakersauction.Masyarakat.MasyarakatActivity;
import com.example.sneakersauction.Notif.AlarmNotif;
import com.example.sneakersauction.Petugas.PetugasActivity;
import com.example.sneakersauction.R;
import com.example.sneakersauction.SharedPreference.SharedPreference;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginRegisterActivity extends AppCompatActivity {
    SharedPreference sharedPreference;
    int role_id;
    ApiService mApiService;
    private List<Lelang> lelangList2=new ArrayList<>();
    PendingIntent pendingIntent;
    TextView textView;
    public static final int REQUEST_ID_MULTIPLE_PERMISSIONS= 7;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_register);
        checkAndroidVersion();
        sharedPreference=new SharedPreference(this);

        mApiService= RetrofitClient.getClient(RetrofitClient.BASE_URL_API).create(ApiService.class);
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
                            Intent myIntent = new Intent(LoginRegisterActivity.this, AlarmReceiver.class);
                            AlarmManager alarmManagers = (AlarmManager) LoginRegisterActivity.this.getApplicationContext().getSystemService(Activity.ALARM_SERVICE);
                            myIntent.   putExtra("tanggal",list);
                            myIntent.putExtra("id_lelang",id_lelang);
                            PendingIntent pendingIntent = PendingIntent.getBroadcast(LoginRegisterActivity.this.getApplicationContext(), i, myIntent, 0);
                            Calendar calendar = Calendar.getInstance();
                            calendar.set(Calendar.SECOND,i);
                            alarmManagers = (AlarmManager)LoginRegisterActivity.this.getApplicationContext().getSystemService(ALARM_SERVICE);
                            alarmManagers.set(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),pendingIntent);
                        }



                    }else{

                    }
                }


            }

            @Override
            public void onFailure(Call<LelangResponse> call, Throwable t) {

                Toast.makeText(LoginRegisterActivity.this, "Internet connection problem", Toast.LENGTH_SHORT).show();
            }
        });

        role_id=sharedPreference.getSpRoleId();
        if (role_id==1){
            if (sharedPreference.getSPSudahLogin()){
                startActivity(new Intent(LoginRegisterActivity.this, AdminActivity.class)
                        .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
                finish();
            }
        }
        else if (role_id==2){
            if (sharedPreference.getSPSudahLogin()){
                startActivity(new Intent(LoginRegisterActivity.this, PetugasActivity.class)
                        .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
                finish();
            }
        }
        else if (role_id==3){
            if (sharedPreference.getSPSudahLogin()){
                startActivity(new Intent(LoginRegisterActivity.this, MasyarakatActivity.class)
                        .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
                finish();
            }
        }


        ViewPager viewPager = findViewById(R.id.viewpager);

        AuthenticationPagerAdapter pagerAdapter = new AuthenticationPagerAdapter(getSupportFragmentManager());
        pagerAdapter.addFragmet(new LoginFragment());
        pagerAdapter.addFragmet(new RegisterFragment());
        viewPager.setAdapter(pagerAdapter);
    }
    class AuthenticationPagerAdapter extends FragmentPagerAdapter {
        private ArrayList<Fragment> fragmentList = new ArrayList<>();

        public AuthenticationPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int i) {
            return fragmentList.get(i);
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }

        void addFragmet(Fragment fragment) {
            fragmentList.add(fragment);
        }
}
    private void checkAndroidVersion() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkAndRequestPermissions();

        } else {
            // code for lollipop and pre-lollipop devices
        }

    }


    private boolean checkAndRequestPermissions() {
        int camera = ContextCompat.checkSelfPermission(LoginRegisterActivity.this,
                Manifest.permission.CAMERA);
        int wtite = ContextCompat.checkSelfPermission(LoginRegisterActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int read = ContextCompat.checkSelfPermission(LoginRegisterActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE);
        List<String> listPermissionsNeeded = new ArrayList<>();
        if (wtite != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if (camera != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.CAMERA);
        }
        if (read != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.READ_EXTERNAL_STORAGE);
        }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(LoginRegisterActivity.this, listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), REQUEST_ID_MULTIPLE_PERMISSIONS);
            return false;
        }
        return true;
    }


    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        Log.d("in fragment on request", "Permission callback called-------");
        switch (requestCode) {
            case REQUEST_ID_MULTIPLE_PERMISSIONS: {

                Map<String, Integer> perms = new HashMap<>();
                // Initialize the map with both permissions
                perms.put(Manifest.permission.WRITE_EXTERNAL_STORAGE, PackageManager.PERMISSION_GRANTED);
                perms.put(Manifest.permission.CAMERA, PackageManager.PERMISSION_GRANTED);
                perms.put(Manifest.permission.READ_EXTERNAL_STORAGE, PackageManager.PERMISSION_GRANTED);
                // Fill with actual results from user
                if (grantResults.length > 0) {
                    for (int i = 0; i < permissions.length; i++)
                        perms.put(permissions[i], grantResults[i]);
                    // Check for both permissions
                    if (perms.get(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
                            && perms.get(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED && perms.get(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                        Log.d("in fragment on request", "CAMERA & WRITE_EXTERNAL_STORAGE READ_EXTERNAL_STORAGE permission granted");
                        // process the normal flow
                        //else any one or both the permissions are not granted
                    } else {
                        Log.d("in fragment on request", "Some permissions are not granted ask again ");
                        //permission is denied (this is the first time, when "never ask again" is not checked) so ask again explaining the usage of permission
//                        // shouldShowRequestPermissionRationale will return true
                        //show the dialog or snackbar saying its necessary and try again otherwise proceed with setup.
                        if (ActivityCompat.shouldShowRequestPermissionRationale(LoginRegisterActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) || ActivityCompat.shouldShowRequestPermissionRationale(LoginRegisterActivity.this, Manifest.permission.CAMERA) || ActivityCompat.shouldShowRequestPermissionRationale(LoginRegisterActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
                            showDialogOK("Camera and Storage Permission required for this app",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            switch (which) {
                                                case DialogInterface.BUTTON_POSITIVE:
                                                    checkAndRequestPermissions();
                                                    break;
                                                case DialogInterface.BUTTON_NEGATIVE:
                                                    // proceed with logic by disabling the related features or quit the app.
                                                    break;
                                            }
                                        }
                                    });
                        }
                        //permission is denied (and never ask again is  checked)
                        //shouldShowRequestPermissionRationale will return false
                        else {
                            Toast.makeText(LoginRegisterActivity.this, "Go to settings and enable permissions", Toast.LENGTH_LONG)
                                    .show();
                            //                            //proceed with logic by disabling the related features or quit the app.
                        }
                    }
                }
            }
        }

    }

    private void showDialogOK(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(LoginRegisterActivity.this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", okListener)
                .create()
                .show();
    }
}