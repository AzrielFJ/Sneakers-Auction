package com.example.sneakersauction.Masyarakat.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sneakersauction.ApiHelper.ApiService;
import com.example.sneakersauction.ApiHelper.RetrofitClient;
import com.example.sneakersauction.ApiHelper.pojo.Model.Lelang.Penawar;
import com.example.sneakersauction.R;
import com.example.sneakersauction.SharedPreference.SharedPreference;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class ListPenawarAdapter extends  RecyclerView.Adapter<ListPenawarAdapter.ListViewHolder>  {
    Context mContext;
    List<Penawar> penawarlist;
    SharedPreference sharedPreference;
    ApiService apiService;
    int tawaran_tertinggi;
    public ListPenawarAdapter(Context context, List<Penawar> penawarList) {
        this.mContext = context;
        penawarlist = penawarList;
    }
    @Override
    public ListPenawarAdapter.ListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_penawar, parent, false);
        sharedPreference = new SharedPreference(mContext);
        apiService = RetrofitClient.getClient(RetrofitClient.BASE_URL_API).create(ApiService.class);
        return new ListPenawarAdapter.ListViewHolder(view);


    }

    @Override
    public void onBindViewHolder(@NonNull ListPenawarAdapter.ListViewHolder holder, int position) {
        final Penawar penawar = penawarlist.get(position);
        String[] no={"1"
                ,"2"
                ,"3"
        };
        holder.tv_nama.setText(penawar.getNamaLengkap());
        Locale localeID = new Locale("in", "ID");
        NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeID);
        holder.tv_harga.setText(""+formatRupiah.format((double)penawar.getPenawaranHarga()));
        holder.tv_no.setText(no[position]);
//        if(position %2 == 1)
//        {
//            holder.relativeLayout.setBackgroundColor(Color.parseColor("#FFFFFF"));
//
//        }
//        else
//        {
//            holder.relativeLayout.setBackgroundColor(Color.parseColor("#FAFAFA"));
//
//        }




    }
    @Override
    public int getItemCount() {
        return penawarlist.size();
    }
    public class ListViewHolder extends RecyclerView.ViewHolder{
        TextView tv_nama;
        TextView tv_harga;
        TextView tv_no;
        CardView relativeLayout;
        public ListViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_nama=itemView.findViewById(R.id.tv_name2);
            tv_harga=itemView.findViewById(R.id.tv_harga2);
            tv_no=itemView.findViewById(R.id.tv_no);
            relativeLayout=itemView.findViewById(R.id.cardview2);
        }
    }
}