package com.example.sneakersauction.Masyarakat.Fragment;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.example.sneakersauction.Adapter.TabAdapter;
import com.example.sneakersauction.Administrator.Fragment.TambahBarang;
import com.example.sneakersauction.ApiHelper.ApiService;
import com.example.sneakersauction.ApiHelper.RetrofitClient;
import com.example.sneakersauction.ApiHelper.pojo.Model.Administrator.ListPetugas;
import com.example.sneakersauction.ApiHelper.pojo.Response.Administrator.ListPetugasResponse;
import com.example.sneakersauction.LoginRegister.LoginRegisterActivity;
import com.example.sneakersauction.NumberTextWatcherForThousand;
import com.example.sneakersauction.Petugas.Fragment.DibukaFragment;
import com.example.sneakersauction.Petugas.Fragment.DitutupFragment;
import com.example.sneakersauction.R;
import com.example.sneakersauction.SharedPreference.SharedPreference;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.tabs.TabLayout;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LelangFragment  extends AppCompatDialogFragment  {
    private DialogInterface.OnDismissListener onDismissListener;

    public void setOnDismissListener(DialogInterface.OnDismissListener onDismissListener) {
        this.onDismissListener = onDismissListener;
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        if (onDismissListener != null) {
            onDismissListener.onDismiss(dialog);
        }
    }
    private EditText editTextBid;

    Context mContext;
    ApiService mApiService;
    RecyclerView recyclerView;
    private LelangFragment.ExampleDialogListener listener;
    SharedPreference sharedPreference;
    View view;
    int bid;
    String harga;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        mContext=getActivity();
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.bid_fragment, null);
        mApiService = RetrofitClient.getClient(RetrofitClient.BASE_URL_API).create(ApiService.class);
        TextView title = new TextView(getContext());
        sharedPreference=new SharedPreference(getContext());
        title.setTextColor(ContextCompat.getColor(getContext(), R.color.colorPrimary));
        title.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
        title.setTypeface(Typeface.DEFAULT_BOLD);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        lp.setMargins(0, 20, 0, 0);
        title.setPadding(0,30,0,0);
        title.setLayoutParams(lp);
        title.setText("Add Officers");
        title.setGravity(Gravity.CENTER);
        editTextBid = view.findViewById(R.id.et_bid);
        harga = String.format("%,d", Long.parseLong(""+sharedPreference.getSpBid()));
        editTextBid.setText(harga);
        editTextBid.addTextChangedListener(new NumberTextWatcherForThousand(editTextBid));
//        bid= Integer.parseInt(editTextBid.getText().toString());
        builder.setView(view)
                .setCustomTitle(title)
                .setIcon(R.drawable.icon)
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .setPositiveButton("Bid", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, final int i) {
                        if (sharedPreference.getSpBid() >= Integer.parseInt(editTextBid.getText().toString())){
                            Locale localeID = new Locale("in", "ID");
                            NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeID);
                            Toast.makeText(getContext(),"Bid Must be above "+formatRupiah.format((double)sharedPreference.getSpBid()),Toast.LENGTH_LONG).show();
                        }else{
                            mApiService.bidLelang(sharedPreference.getSpIdLelang(),sharedPreference.getSpBarangId(),sharedPreference.getSpUserId(), Integer.parseInt(editTextBid.getText().toString())).
                                    enqueue(new Callback<ResponseBody>() {

                                        @Override
                                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                                            if (response.isSuccessful()) {
                                                Toast.makeText(mContext, "Bid Success", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                        @Override
                                        public void onFailure(Call<ResponseBody> call, Throwable t) {

                                        }
                                    });
                        }



                    }
                });



        return builder.create();
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            listener = (LelangFragment.ExampleDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() +
                    "must implement ExampleDialogListener");
        }
    }

    public interface ExampleDialogListener {

        void applyTexts(String username, String password);
    }
    private TextWatcher enabledBtn = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, final int start, int before, int count) {
        }
        @Override
        public void afterTextChanged(Editable s) {
//            String number=null;
//            try {
//                // The comma in the format specifier does the trick
//                number = String.format("%,d", Long.parseLong(s.toString()));
//            } catch (NumberFormatException e) {
//            }
//            editTextBid.setText(number);
        }

    };
}
