package com.example.sneakersauction.Administrator.Adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.sneakersauction.Administrator.AdminActivity;
import com.example.sneakersauction.Administrator.Fragment.TambahBarang;
import com.example.sneakersauction.ApiHelper.ApiService;
import com.example.sneakersauction.ApiHelper.RetrofitClient;
import com.example.sneakersauction.ApiHelper.pojo.Model.Administrator.ListPetugas;
import com.example.sneakersauction.ApiHelper.pojo.Model.Barang.Barang;
import com.example.sneakersauction.ApiHelper.pojo.Model.Barang.Foto;
import com.example.sneakersauction.ApiHelper.pojo.Response.Barang.FotoResponse;
import com.example.sneakersauction.Petugas.DetailBarang;
import com.example.sneakersauction.R;
import com.example.sneakersauction.SharedPreference.SharedPreference;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.io.IOException;
import java.net.URL;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.sneakersauction.SharedPreference.SharedPreference.SP_DESC_BARANG;
import static com.example.sneakersauction.SharedPreference.SharedPreference.SP_NAME;
import static com.example.sneakersauction.SharedPreference.SharedPreference.SP_NAME_BARANG;

public class ListBarangAdapter extends  RecyclerView.Adapter<ListBarangAdapter.GridViewHolder>  {
    Context mContext;
    List<Barang> baranglist;
    SharedPreference sharedPreference;
    ApiService apiService;
    String foto;

    private ArrayList<String> arrayList=new ArrayList<>();
    public ListBarangAdapter(Context context, List<Barang> barangList) {
        this.mContext = context;
        baranglist = barangList;
    }
    @Override
    public ListBarangAdapter.GridViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_barang,parent,false);
        sharedPreference=new SharedPreference(mContext);
        apiService = RetrofitClient.getClient(RetrofitClient.BASE_URL_API).create(ApiService.class);

        return new ListBarangAdapter.GridViewHolder(view);

    }


    @Override
    public void onBindViewHolder(@NonNull ListBarangAdapter.GridViewHolder holder, int position) {
        final Barang barang = baranglist.get(position);

            holder.tv_nama.setText(""+barang.getNamaBarang());
        Locale localeID = new Locale("in", "ID");
        NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeID);
        holder.tv_harga.setText(formatRupiah.format((double)barang.getHargaAwal()));
        holder.bt_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sharedPreference.saveSPInt(String.valueOf(SharedPreference.SP_BARANG_ID), barang.getIdBarang());
                sharedPreference.saveSPInt(String.valueOf(SharedPreference.SP_HARGA_BARANG), barang.getHargaAwal());
                sharedPreference.saveSPString(SP_NAME_BARANG, barang.getNamaBarang());
                sharedPreference.saveSPString(SP_DESC_BARANG,barang.getDeskripsiBarang());
                TambahBarang tambahBarang=new TambahBarang();

                FragmentActivity activity = (FragmentActivity)(mContext);
                tambahBarang.show(activity.getSupportFragmentManager(), "TambahFragment");
            }
        });
        Bitmap setFoto=null;

        if(barang.getFoto().equals("")) {

            foto="assets/foto/user.png";
        }
        else {
            foto = ""+barang.getFoto();
        }
        try{
            URL url=new URL(RetrofitClient.BASE_URL_FILE + foto);
            setFoto= BitmapFactory.decodeStream(url.openConnection().getInputStream());
        }catch (IOException e){
            e.printStackTrace();
        }
        holder.imageView.setImageBitmap(setFoto);
        holder.imageView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new MaterialAlertDialogBuilder(mContext,
                        R.style.MyThemeOverlay_MaterialComponents_MaterialAlertDialog)
                        .setTitle("Remove Item?")
                        .setPositiveButton("Remove", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                apiService.deleteBarang(barang.getIdBarang()     ).enqueue(new Callback<ResponseBody>() {
                                    @Override
                                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                        if (response.isSuccessful()){
                                            Toast toast=Toast.makeText(mContext,"Delete Item Successfully",Toast.LENGTH_SHORT);
                                            View view=toast.getView();
                                            view.setBackgroundResource(R.drawable.bg_toast);
                                            baranglist.remove(position);
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
        if(sharedPreference.getSpRoleId()==2){
            holder.cv_main.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    sharedPreference.saveSPInt(String.valueOf(SharedPreference.SP_BARANG_ID), barang.getIdBarang());
                    sharedPreference.saveSPInt(String.valueOf(SharedPreference.SP_HARGA_BARANG), barang.getHargaAwal());
                    sharedPreference.saveSPString(SP_NAME_BARANG, barang.getNamaBarang());
                    Intent intent=new Intent(mContext, DetailBarang.class);
                    intent.putExtra("id_barang",barang.getIdBarang());
                    intent.putExtra("nama",barang.getNamaBarang());
                    intent.putExtra("kode",0);
//                    intent.putExtra("foto",arrayList);
                    intent.putExtra("harga",barang.getHargaAwal());
                    intent.putExtra("gambar",barang.getFoto());
                    intent.putExtra("desc",barang.getDeskripsiBarang());
                    mContext.startActivity(intent);


                }
            });
        }

    }
    @Override
    public int getItemCount() {
        return baranglist.size();
    }
    public class GridViewHolder extends RecyclerView.ViewHolder{
        TextView tv_nama;
        TextView tv_harga;
//        TextView tv_desc;
        ImageView imageView;
        ImageView imageView2;
        CardView cv_main;
        ImageView bt_edit;
        public GridViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_nama=itemView.findViewById(R.id.tv_nama_barang);
            tv_harga=itemView.findViewById(R.id.tv_harga);
//            tv_desc=itemView.findViewById(R.id.tv_desc);
            imageView=itemView.findViewById(R.id.iv_foto);
            imageView2=itemView.findViewById(R.id.bt_delete);
            cv_main=itemView.findViewById(R.id.cardview);
            bt_edit=itemView.findViewById(R.id.bt_edit);
        }
    }
}


