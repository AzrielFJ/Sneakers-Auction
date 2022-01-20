package com.example.sneakersauction.Administrator.Fragment;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sneakersauction.Administrator.Adapter.ListPetugasAdapter;
import com.example.sneakersauction.Administrator.AdminActivity;
import com.example.sneakersauction.ApiHelper.ApiService;
import com.example.sneakersauction.ApiHelper.FileUtil;
import com.example.sneakersauction.ApiHelper.RetrofitClient;
import com.example.sneakersauction.ApiHelper.pojo.Model.Administrator.ListPetugas;
import com.example.sneakersauction.ApiHelper.pojo.Response.Administrator.ListPetugasResponse;
import com.example.sneakersauction.MainActivity;
import com.example.sneakersauction.NumberTextWatcherForThousand;
import com.example.sneakersauction.R;
import com.example.sneakersauction.SharedPreference.SharedPreference;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;

public class TambahBarang extends AppCompatDialogFragment {
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
    private TambahBarang.ExampleDialogListener listener;
    EditText editTextNama;
    EditText editTextDesc;
    EditText editTextHarga;

    Context mContext;
    ApiService mApiService;



    String Date;
    View view;
    ImageView imageView;
    Uri filePath;
    Bitmap bitmap;
    String path;
    String namaFoto;
    File file;
    SharedPreference sharedPreference;

    String harga,s;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        mContext=getActivity();
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_add_barang, null);
        TextView title = new TextView(getContext());
        mApiService = RetrofitClient.getClient(RetrofitClient.BASE_URL_API).create(ApiService.class);
        sharedPreference=new SharedPreference(getContext());
        SimpleDateFormat DateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Calendar date =Calendar.getInstance();
        Date= DateFormat.format(date.getTime());
        editTextNama = view.findViewById(R.id.et_nama_barang);
        editTextHarga = view.findViewById(R.id.et_harga);
        editTextDesc=view.findViewById(R.id.et_desc);
        editTextDesc.setText(sharedPreference.getSpDescBarang());
        editTextNama.setText(sharedPreference.getSpNameBarang());
        harga = String.format("%,d", Long.parseLong(""+sharedPreference.getSpHargaBarang()));
        editTextHarga.setText(harga);
        editTextHarga.addTextChangedListener(new NumberTextWatcherForThousand(editTextHarga));

//        imageView=view.findViewById(R.id.addfoto);
//        imageView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                selectImage(getContext());
//            }
//        });

        title.setTextColor(ContextCompat.getColor(getContext(), R.color.colorPrimary));
        title.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
        title.setTypeface(Typeface.DEFAULT_BOLD);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        lp.setMargins(0, 20, 0, 0);
        title.setPadding(0,30,0,0);
        title.setLayoutParams(lp);
        title.setText("Edit Item");
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

//                        uploadImage();

                    }

                });


        return builder.create();
    }




    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            listener = (TambahBarang.ExampleDialogListener) context;
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

    @Override
    public void  onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode != RESULT_CANCELED) {
            switch (requestCode) {
                case 0:
                    if (resultCode == RESULT_OK && data != null) {
                        bitmap = (Bitmap) data.getExtras().get("data");
                        imageView.setImageBitmap(bitmap);
                        Uri tempUri = getImageUri(getContext().getApplicationContext(), bitmap);
                        file = new File(getRealPathFromURI(tempUri));
                    }

                    break;
                case 1:
                    if (resultCode == RESULT_OK && data != null) {
                        filePath =  data.getData();
                        path = FileUtil.getPath(getContext(), data.getData());
                        file=new File(path);
                        String[] filePathColumn = {MediaStore.Images.Media.DATA};
                        if (filePath != null) {
                            Cursor cursor = getContext().getContentResolver().query(filePath,
                                    filePathColumn, null, null, null);
                            if (cursor != null) {
                                cursor.moveToFirst();

                                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                                String picturePath = cursor.getString(columnIndex);
                                imageView.setImageBitmap(BitmapFactory.decodeFile(picturePath));
                                cursor.close();
                            }
                        }

                    }
                    break;
            }
        }
    }
    private void uploadImage() {


        RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part parts = MultipartBody.Part.createFormData("image", file.getName(), requestBody);


        ApiService uploadApis = RetrofitClient.getClient(RetrofitClient.BASE_URL_API).create(ApiService.class);
        Call<RequestBody> call = uploadApis.uploadImage(parts);

        call.enqueue(new Callback<RequestBody>() {
            @Override
            public void onResponse(Call<RequestBody> call, Response<RequestBody> response) {
                if (response.isSuccessful()){
                    Toast.makeText(getContext(),"Add Item Succeeded",Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call <RequestBody> call, Throwable t) {

            }
        });
    }
    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Camera", null);
        return Uri.parse(path);
    }
    public String getRealPathFromURI(Uri uri) {
        String path = "";
        if (getContext().getContentResolver() != null) {
            Cursor cursor = getContext().getContentResolver().query(uri, null, null, null, null);
            if (cursor != null) {
                cursor.moveToFirst();
                int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
                path = cursor.getString(idx);
                cursor.close();
            }
        }
        return path;
    }
    private void validation() {

        if (editTextHarga.getText().toString().isEmpty() || editTextNama.getText().toString().isEmpty() || editTextDesc.getText().toString().isEmpty()) {
            Toast.makeText(getContext(), "Fields cannot be empty", Toast.LENGTH_SHORT).show();


        }else{

                    requestRegister();



        }
    }

    private void requestRegister() {
        mApiService.editBarang(sharedPreference.getSpBarangId(),editTextNama.getText().toString(),editTextDesc.getText().toString(),Integer.parseInt(editTextHarga.getText().toString())).
                enqueue(new Callback<ResponseBody>() {

                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                        if (response.isSuccessful()) {

                            Intent i= new Intent(mContext,AdminActivity.class);

                            mContext.startActivity(i);
//                                            Toast.makeText(mContext, "Edit Item Success", Toast.LENGTH_   SHORT).show();
                        } else {

                        }
                    }
                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {

                    }
                });
    }
}
