package com.example.sneakersauction.Administrator.Fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sneakersauction.Administrator.Adapter.ListBarangAdapter;
import com.example.sneakersauction.Administrator.TambahBarangActivity;
import com.example.sneakersauction.ApiHelper.ApiService;
import com.example.sneakersauction.ApiHelper.RetrofitClient;
import com.example.sneakersauction.ApiHelper.pojo.Model.Barang.Barang;
import com.example.sneakersauction.ApiHelper.pojo.Response.Barang.BarangResponse;
import com.example.sneakersauction.LoginRegister.LoginRegisterActivity;
import com.example.sneakersauction.R;
import com.example.sneakersauction.SharedPreference.SharedPreference;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListBarangFragment extends Fragment  implements TambahBarang.ExampleDialogListener{
    View root;
    private ListBarangAdapter listBarangAdapter;
    private RecyclerView rv_listbarang;
    ApiService mApiService;
    private List<Barang> barangList=new ArrayList<>();
    SharedPreference sharedPreference;
    ImageButton imageButton;
    ImageButton bt_menu;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_admin_home, container, false);
        sharedPreference=new SharedPreference(getContext());
        StrictMode.ThreadPolicy policy=new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        initComponent();
        initRetrofit();
        listItemBarang();
        functionGetBarang();

        return root;
    }

    private void functionGetBarang() {
        mApiService.listBarang("tersedia").enqueue(new Callback<BarangResponse>() {
            @Override
            public void onResponse(Call<BarangResponse> call, Response<BarangResponse> response) {
                if (response.isSuccessful()) {
                    final List<Barang> petugasList = response.body().getData();
                    rv_listbarang.setAdapter(new ListBarangAdapter(getContext(), petugasList));
                    listBarangAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<BarangResponse> call, Throwable t) {

                Toast.makeText(getContext(), "Koneksi internet bermasalah", Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void listItemBarang() {
        rv_listbarang.setHasFixedSize(true);
        listBarangAdapter =new ListBarangAdapter(getContext(), barangList);
        GridLayoutManager mLayoutManager = new GridLayoutManager(getContext(),2);
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        listBarangAdapter.notifyDataSetChanged();
        rv_listbarang.setLayoutManager(mLayoutManager);

    }

    private void initRetrofit() {
        mApiService = RetrofitClient.getClient(RetrofitClient.BASE_URL_API).create(ApiService.class);
    }

    private void initComponent() {
        rv_listbarang=root.findViewById(R.id.rv_listBarang);
        imageButton=root.findViewById(R.id.add_barang);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(), TambahBarangActivity.class);
                intent.putExtra("role_id",1);
                startActivity(intent);
                getActivity().finish();
            }
        });
        bt_menu=root.findViewById(R.id.menu);
        bt_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showdialog();
            }
        });
    
    }

    private void openDialog() {
        TambahBarang tambahBarang=new TambahBarang();
        tambahBarang.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
            functionGetBarang();
            }
        });
        tambahBarang.show(getActivity().getSupportFragmentManager(), "TambahFragment");
    }

    @Override
    public void applyTexts(String username, String password) {

    }
    private void showdialog() {
        new MaterialAlertDialogBuilder(getContext(),
                R.style.MyThemeOverlay_MaterialComponents_MaterialAlertDialog)
                .setIcon(R.drawable.icon)
                .setTitle("Log out from App?")
                .setCancelable(false)
                .setPositiveButton("Log out",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        sharedPreference.saveSPBoolean(SharedPreference.SP_SUDAH_LOGIN, false);
                        startActivity(new Intent(getContext(), LoginRegisterActivity.class)
                                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
                        getActivity().finish();
                    }
                })
                .setNegativeButton("Cancel",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                }).show();
    }

}
