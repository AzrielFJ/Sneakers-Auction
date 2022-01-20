package com.example.sneakersauction.Administrator.Adapter;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import android.app.AlertDialog;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sneakersauction.Administrator.AdminActivity;
import com.example.sneakersauction.ApiHelper.ApiService;
import com.example.sneakersauction.ApiHelper.RetrofitClient;
import com.example.sneakersauction.ApiHelper.pojo.Model.Administrator.ListPetugas;
import com.example.sneakersauction.R;
import com.example.sneakersauction.SharedPreference.SharedPreference;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListPetugasAdapter extends  RecyclerView.Adapter<ListPetugasAdapter.GridViewHolder> {

        Context mContext;
        List<ListPetugas> petugasList;
        SharedPreference sharedPreference;
        ApiService apiService;

public ListPetugasAdapter(Context context, List<ListPetugas> petugasLists) {
        this.mContext = context;
        petugasList = petugasLists;
        }
@Override
public ListPetugasAdapter.GridViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_petugas,parent,false);
        sharedPreference=new SharedPreference(mContext);
        apiService = RetrofitClient.getClient(RetrofitClient.BASE_URL_API).create(ApiService.class);
        return new ListPetugasAdapter.GridViewHolder(view);

        }


@Override
public void onBindViewHolder(@NonNull ListPetugasAdapter.GridViewHolder holder, int position) {
    final ListPetugas listPetugas = petugasList.get(position);
    if (listPetugas.getRoleId()==1) {
        holder.bt_delete.setVisibility(View.GONE);
        holder.tv_nama.setText(listPetugas.getNamaPetugas());
        holder.tv_role.setText(listPetugas.getRole());
    } else {
        holder.tv_nama.setText(listPetugas.getNamaPetugas());
        holder.tv_role.setText(listPetugas.getRole());
        holder.bt_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(mContext);
                alertDialogBuilder.setTitle("Remove Officer?");
                new MaterialAlertDialogBuilder(mContext,
                        R.style.MyThemeOverlay_MaterialComponents_MaterialAlertDialog)
                        .setTitle("Remove Officer?")
                        .setPositiveButton("Remove", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                apiService.deletPetugas(listPetugas.getUserId()     ).enqueue(new Callback<ResponseBody>() {
                                    @Override
                                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                        if (response.isSuccessful()){
                                            Toast toast=Toast.makeText(mContext,"Remove the Officer Successfully",Toast.LENGTH_SHORT);
                                            View view=toast.getView();
                                            view.setBackgroundResource(R.drawable.bg_toast);
                                            petugasList.remove(position);
                                            notifyDataSetChanged();
                                            toast.show();
                                        }
                                    }


                                    @Override
                                    public void onFailure(Call<ResponseBody> call, Throwable t) {

                                    }
                                });

                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        })
                        .show();




            }
        });
    }
}
@Override
public int getItemCount() {
        return petugasList.size();
        }
public class GridViewHolder extends RecyclerView.ViewHolder{
    TextView tv_nama;
    TextView tv_role;
    ImageButton bt_delete;
    public GridViewHolder(@NonNull View itemView) {
        super(itemView);
        tv_nama=itemView.findViewById(R.id.tv_nama);
        tv_role=itemView.findViewById(R.id.tv_role);
        bt_delete=itemView.findViewById(R.id.bt_delete);
    }
}
}


