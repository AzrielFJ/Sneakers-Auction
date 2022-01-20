package com.example.sneakersauction.Petugas.Fragment;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sneakersauction.Administrator.Fragment.TambahBarang;
import com.example.sneakersauction.ApiHelper.ApiService;
import com.example.sneakersauction.ApiHelper.FileUtil;
import com.example.sneakersauction.ApiHelper.RetrofitClient;
import com.example.sneakersauction.R;
import com.example.sneakersauction.SharedPreference.SharedPreference;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LelangFragment extends AppCompatDialogFragment  {
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
    EditText editTextNama;
    EditText editTextTanggalBuka;
    EditText editTextHarga;
    EditText editTextTanggalTutup;
    EditText editTextJamBuka;
    EditText editTextJamTutup;
    Context mContext;
    ApiService mApiService;
    RecyclerView recyclerView;
    private LelangFragment.ExampleDialogListener listener;
    String Date;
    View view;
    String Tanggal_dibuka;
    String Tanggal_ditutup;
    SharedPreference sharedPreference;
    private int mYear, mMonth, mDay, mHour, mMinute;
    Calendar c;
    String harga;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        mContext=getActivity();
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_buka_lelang, null);

        mApiService = RetrofitClient.getClient(RetrofitClient.BASE_URL_API).create(ApiService.class);
        sharedPreference=new SharedPreference(getContext());
        TextView title = new TextView(getContext());
        SimpleDateFormat DateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Calendar date =Calendar.getInstance();
        Date= DateFormat.format(date.getTime());
        editTextNama = view.findViewById(R.id.et_nama_barang);
        editTextNama.setText(sharedPreference.getSpNameBarang());
        editTextHarga = view.findViewById(R.id.et_harga);
        Locale localeID = new Locale("in", "ID");
        NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeID);
        harga = String.format("%,d", Long.parseLong(""+sharedPreference.getSpHargaBarang()));
        editTextHarga.setText(harga);
        editTextTanggalBuka=view.findViewById(R.id.et_tanggal_dibuka);
        editTextTanggalTutup=view.findViewById(R.id.et_tanggal_ditutup);
        editTextJamBuka=view.findViewById(R.id.et_jam_dibuka);
        editTextJamTutup=view.findViewById(R.id.et_jam_ditutup);

        editTextJamBuka.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                c = Calendar.getInstance();
                mHour = c.get(Calendar.HOUR_OF_DAY);
                mMinute = c.get(Calendar.MINUTE);

                TimePickerDialog timePickerDialog = new TimePickerDialog(getContext(),
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {


                                editTextJamBuka.setText(String.format("%02d:%02d", hourOfDay, minute));
                            }
                        }, mHour, mMinute, false);
                timePickerDialog.show();
            }
        });
        editTextJamTutup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                c = Calendar.getInstance();
                mHour = c.get(Calendar.HOUR_OF_DAY);
                mMinute = c.get(Calendar.MINUTE);

                TimePickerDialog timePickerDialog = new TimePickerDialog(getContext(),
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                                editTextJamTutup.setText(String.format("%02d:%02d", hourOfDay, minute));
                            }
                        }, mHour, mMinute, false);
                timePickerDialog.show();
            }
        });
        editTextTanggalBuka.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(),
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                                editTextTanggalBuka.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);

                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();

            }
        });
        editTextTanggalTutup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(),
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                                editTextTanggalTutup.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);

                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });


        title.setTextColor(ContextCompat.getColor(getContext(), R.color.colorPrimary));
        title.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
        title.setTypeface(Typeface.DEFAULT_BOLD);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        lp.setMargins(0, 20, 0, 0);
        title.setPadding(0,30,0,0);
        title.setLayoutParams(lp);
        title.setText("Open Auction");
        title.setGravity(Gravity.CENTER);
        builder.setView(view)
                .setCustomTitle(title)
                .setIcon(R.drawable.icon)
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, final int i) {
validation();



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
    private void selectImage(Context context) {
        final CharSequence[] options = { "Take Photo", "Choose from Gallery","Cancel" };

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Add Photo");

        builder.setItems(options, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int item) {

                if (options[item].equals("Take Photo")) {
                    Intent takePicture = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(takePicture, 0);

                } else if (options[item].equals("Choose from Gallery")) {
                    Intent pickPhoto = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(pickPhoto , 1);

                } else if (options[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }


    private void validation() {

        if (editTextTanggalBuka.getText().toString().isEmpty() && editTextTanggalTutup.getText().toString().isEmpty() && editTextJamBuka.getText().toString().isEmpty() && editTextJamTutup.getText().toString().isEmpty()) {
            Toast.makeText(getContext(), "Fields cannot be empty", Toast.LENGTH_SHORT).show();
            if (editTextTanggalBuka.getText().toString().isEmpty() || editTextTanggalTutup.getText().toString().isEmpty()){
                Toast.makeText(getContext(), "Select a Date", Toast.LENGTH_SHORT).show();
            }

            if (editTextJamBuka.getText().toString().isEmpty() || editTextJamTutup.getText().toString().isEmpty()){
                Toast.makeText(getContext(), "Select a Time", Toast.LENGTH_SHORT).show();
            }

        }else{
            bukalelang();
            }







    }

    private void bukalelang() {
        mApiService.bukaLelang(sharedPreference.getSpPetugasId(),sharedPreference.getSpBarangId(),editTextTanggalBuka.getText().toString()+" "+editTextJamBuka.getText().toString(),editTextTanggalTutup.getText().toString()+" "+editTextJamTutup.getText().toString()).
                enqueue(new Callback<ResponseBody>() {

                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                        if (response.isSuccessful()) {
                            try {
                                JSONObject jsonObject = new JSONObject(response.body().string());
                                if (jsonObject.getString("status").equals("true")) {
                                    Toast.makeText(mContext, "Successfully", Toast.LENGTH_SHORT).show();
                                }
                                else if(jsonObject.getString("status").equals("false")){
                                    Toast.makeText(mContext, "The auction has opened", Toast.LENGTH_SHORT).show();
                                }else{
                                    Toast.makeText(mContext, "The Closing Date Must be More than The Open Date", Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                        else {

                        }
                    }
                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {

                    }
                });
    }


}

