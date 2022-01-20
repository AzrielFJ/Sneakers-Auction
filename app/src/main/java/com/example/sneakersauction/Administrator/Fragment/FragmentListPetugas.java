package com.example.sneakersauction.Administrator.Fragment;


import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sneakersauction.Administrator.Adapter.ListPetugasAdapter;
import com.example.sneakersauction.ApiHelper.ApiService;
import com.example.sneakersauction.ApiHelper.RetrofitClient;
import com.example.sneakersauction.ApiHelper.pojo.Model.Administrator.ListPetugas;
import com.example.sneakersauction.ApiHelper.pojo.Response.Administrator.ListPetugasResponse;
import com.example.sneakersauction.LoginRegister.LoginRegisterActivity;
import com.example.sneakersauction.R;
import com.example.sneakersauction.SharedPreference.SharedPreference;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentListPetugas extends Fragment implements TambahPetugas.ExampleDialogListener{

    View root;
    private ListPetugasAdapter listPetugasAdapter;
    private RecyclerView rv_listpetugas;
    ApiService mApiService;
    private List<ListPetugas> petugasList=new ArrayList<>();
    SharedPreference sharedPreference;
    ImageButton imageButton;
    ImageButton bt_menu;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_admin_listpetugas, container, false);
        sharedPreference=new SharedPreference(getContext());
        initComponent();
        initRetrofit();
        listItemPetugas();
        functionGetPetugas();


        return root;
    }

    private void functionGetPetugas() {
        mApiService.getPetugas(sharedPreference.getSpPetugasId()).enqueue(new Callback<ListPetugasResponse>() {
            @Override
            public void onResponse(Call<ListPetugasResponse> call, Response<ListPetugasResponse> response) {
                if (response.isSuccessful()) {
                    final List<ListPetugas> petugasList = response.body().getData();
                    rv_listpetugas.setAdapter(new ListPetugasAdapter(getContext(), petugasList));
                    listPetugasAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<ListPetugasResponse> call, Throwable t) {

                Toast.makeText(getContext(), "Koneksi internet bermasalah", Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void listItemPetugas() {
        rv_listpetugas.setHasFixedSize(true);
        listPetugasAdapter =new ListPetugasAdapter(getContext(),petugasList);
        GridLayoutManager mLayoutManager = new GridLayoutManager(getContext(),1);
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        listPetugasAdapter.notifyDataSetChanged();
        rv_listpetugas.setLayoutManager(mLayoutManager);
    }

    private void initRetrofit() {
        mApiService = RetrofitClient.getClient(RetrofitClient.BASE_URL_API).create(ApiService.class);
    }

    private void initComponent() {
        rv_listpetugas=root.findViewById(R.id.rv_listpetugas);
        imageButton=root.findViewById(R.id.add_petugas);
        bt_menu=root.findViewById(R.id.menu);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog();
            }
        });
        bt_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showdialog();
            }
        });
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

    private void openDialog() {
        TambahPetugas tambahPetugas=new TambahPetugas();
        tambahPetugas.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
            functionGetPetugas();
            }
        });
        tambahPetugas.show(getActivity().getSupportFragmentManager(), "TambahFragment");
    }


    @Override
    public void applyTexts(String username, String password) {

    }

}
