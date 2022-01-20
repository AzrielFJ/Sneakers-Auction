package com.example.sneakersauction.Administrator;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sneakersauction.Administrator.Adapter.ListBarangAdapter;
import com.example.sneakersauction.Administrator.Adapter.ViewPagerAdapter;
import com.example.sneakersauction.ApiHelper.ApiService;
import com.example.sneakersauction.ApiHelper.RetrofitClient;
import com.example.sneakersauction.MultipleImage.FileUtils;
import com.example.sneakersauction.MultipleImage.InternetConnection;
import com.example.sneakersauction.MultipleImage.MainActivity;
import com.example.sneakersauction.MultipleImage.MyAdapter;
import com.example.sneakersauction.NumberTextWatcherForThousand;
import com.example.sneakersauction.Petugas.PetugasActivity;
import com.example.sneakersauction.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONException;
import org.json.JSONObject;

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

public class TambahBarangActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();

    private ListView listView;
    private ProgressBar mProgressBar;
    private ImageView btnChoose;
    Button btnUpload;
    Button cancel;
    ImageView delete;
    private ArrayList<Uri> arrayList=new ArrayList<>();
    LinearLayout sliderDotspanel;
    ViewPagerAdapter viewPagerAdapter;
    private final int REQUEST_CODE_PERMISSIONS  = 1;
    private final int REQUEST_CODE_READ_STORAGE = 2;
    ViewPager viewPager;
    private int dotscount;
    TextView[] dot;
    EditText editTextNama;
    EditText editTextDesc;
    EditText editTextHarga;
    TextInputLayout azriel_ti_nama;
    TextInputLayout azriel_ti_phone;
    TextInputLayout azriel_ti_alamat;
    Context mContext;
    ApiService mApiService;
    String Date;
    ImageButton back;
    ImageView imageView;
    Uri filePath;
    Bitmap bitmap;
    String path;
    String namaFoto;
    File file;
    int id_role;
    List<MultipartBody.Part> parts = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_barang);
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        delete=findViewById(R.id.btnDelete);
        sliderDotspanel = (LinearLayout) findViewById(R.id.SliderDots);
        mProgressBar = findViewById(R.id.progressBar);
        mApiService = RetrofitClient.getClient(RetrofitClient.BASE_URL_API).create(ApiService.class);
        SimpleDateFormat DateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Calendar date =Calendar.getInstance();
        Date= DateFormat.format(date.getTime());
        editTextNama = findViewById(R.id.et_nama_barang);
        editTextHarga = findViewById(R.id.et_harga);
        cancel=findViewById(R.id.cancel);
        azriel_ti_nama=findViewById(R.id.ly_name);
        azriel_ti_alamat=findViewById(R.id.ly_desc);
        azriel_ti_phone=findViewById(R.id.ly_harga);
        editTextHarga.addTextChangedListener(new NumberTextWatcherForThousand(editTextHarga));
        id_role=getIntent().getIntExtra("role_id",1);
        if (id_role==1){
            cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent =new Intent(TambahBarangActivity.this,AdminActivity.class);
                    startActivity(intent);
                    finish();
                }
            });
        }else{
            cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent =new Intent(TambahBarangActivity.this, PetugasActivity.class);
                    startActivity(intent);
                    finish();
                }
            });
        }


        editTextDesc=findViewById(R.id.et_desc);
        btnChoose = findViewById(R.id.btnChoose);
        btnChoose.setOnClickListener(v -> {
            // Display the file chooser dialog
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                askForPermission();
            } else {
                showChooser();
            }
        });

        btnUpload = findViewById(R.id.add_barang);
        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validation();
            }
        });





    }

    private void showChooser() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        startActivityForResult(intent, REQUEST_CODE_READ_STORAGE);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent resultData) {
        super.onActivityResult(requestCode, resultCode, resultData);
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_CODE_READ_STORAGE) {
                if (resultData != null) {
                    if (resultData.getClipData() != null) {
                        int count = resultData.getClipData().getItemCount();
                        int currentItem = 0;
                        while (currentItem < count) {
                            Uri imageUri = resultData.getClipData().getItemAt(currentItem).getUri();
                            currentItem = currentItem + 1;

                            Log.d("Uri Selected", imageUri.toString());

                            try {
                                arrayList.add(imageUri);
                                ViewPagerAdapter mAdapter = new ViewPagerAdapter(TambahBarangActivity.this, arrayList);
                                viewPager.setAdapter(mAdapter);
                                delete.setVisibility(View.VISIBLE);
                                dots(0);
                                delete.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        arrayList.remove(arrayList.get(0));
                                        mAdapter.notifyDataSetChanged();
                                        if (arrayList.size()>0){
                                            dots(0);
                                        }
                                        if (arrayList.isEmpty()) {
                                            delete.setVisibility(View.GONE);

                                            sliderDotspanel.removeAllViews();

                                        }
                                    }
                                });
                                    viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                                        @Override
                                        public void onPageScrolled(int i, float v, int i1) {

                                        }

                                        @Override
                                        public void onPageSelected(int i) {
                                            dots(i);
                                            delete.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View v) {
                                                    arrayList.remove(arrayList.get(i));
                                                    mAdapter.notifyDataSetChanged();
                                                    if (arrayList.size()>0){
                                                        dots(0);
                                                    }
                                                    if (arrayList.isEmpty()) {
                                                        delete.setVisibility(View.GONE);
                                                        sliderDotspanel.removeAllViews();
                                                    }
                                                }
                                            });
                                        }

                                        @Override
                                        public void onPageScrollStateChanged(int i) {

                                        }
                                    });

                            } catch (Exception e) {
                                Log.e(TAG, "File select error", e);
                            }
                        }
                    } else if (resultData.getData() != null) {

                        final Uri uri = resultData.getData();
                        Log.i(TAG, "Uri = " + uri.toString());

                        try {
                            arrayList.add(uri);
                            ViewPagerAdapter mAdapter = new ViewPagerAdapter(TambahBarangActivity.this, arrayList);
                            viewPager.setAdapter(mAdapter);
                            dots(0);
                            delete.setVisibility(View.VISIBLE);

                                delete.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        arrayList.remove(arrayList.get(0));
                                        mAdapter.notifyDataSetChanged();

                                        if (arrayList.isEmpty()) {
                                            delete.setVisibility(View.GONE);

                                            sliderDotspanel.removeAllViews();

                                        }
                                    }
                                });

                                viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                                    @Override
                                    public void onPageScrolled(int i, float v, int i1) {

                                    }

                                    @Override
                                    public void onPageSelected(int i) {
                                        dots(i);
                                        delete.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                arrayList.remove(arrayList.get(i));
                                                mAdapter.notifyDataSetChanged();

                                                if (arrayList.isEmpty()) {
                                                    delete.setVisibility(View.GONE);

                                                    sliderDotspanel.removeAllViews();

                                                }
                                            }
                                        });

                                    }

                                    @Override
                                    public void onPageScrollStateChanged(int i) {

                                    }
                                });




                        } catch (Exception e) {
                            Log.e(TAG, "File select error", e);
                        }
                    }
                }
            }
        }
    }

    private void uploadImagesToServer(int id_barang) {
        if (InternetConnection.checkConnection(TambahBarangActivity.this)) {
//            showProgress();
            List<MultipartBody.Part> parts = new ArrayList<>();
            ApiService service = RetrofitClient.getClient(RetrofitClient.BASE_URL_API).create(ApiService.class);

            if (arrayList != null) {
                // create part for file (photo, video, ...)
                for (int i = 0; i < arrayList.size(); i++) {
                    parts.add(prepareFilePart("image", arrayList.get(i)));

                    Call<ResponseBody> call = service.uploadMultiple(id_barang,parts.get(i));

                    call.enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
//                            hideProgress();
                            if(response.isSuccessful()) {
                                Toast.makeText(TambahBarangActivity.this,
                                        "Add Item Success!", Toast.LENGTH_SHORT).show();
                                Intent intent =new Intent(TambahBarangActivity.this, PetugasActivity.class);
                                startActivity(intent);
                                finish();
                            } else {
                                Snackbar.make(findViewById(android.R.id.content),
                                        R.string.string_some_thing_wrong, Snackbar.LENGTH_LONG).show();
                            }
                        }

                        @Override
                        public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
//                            hideProgress();
                            Log.e(TAG, "Image upload failed!", t);
                            Snackbar.make(findViewById(android.R.id.content),
                                    "Image upload failed!", Snackbar.LENGTH_LONG).show();
                        }
                    });
                }
            }







        } else {
//            hideProgress();
            Toast.makeText(TambahBarangActivity.this,
                    R.string.string_internet_connection_not_available, Toast.LENGTH_SHORT).show();
        }
    }

    private void showProgress() {
        mProgressBar.setVisibility(View.VISIBLE);
        btnChoose.setEnabled(false);
        btnUpload.setVisibility(View.GONE);
    }

    private void hideProgress() {
        mProgressBar.setVisibility(View.GONE);
        btnChoose.setEnabled(true);
        btnUpload.setVisibility(View.VISIBLE);
    }



    @NonNull
    private MultipartBody.Part prepareFilePart(String partName, Uri fileUri) {

        file = FileUtils.getFile(this, fileUri);
        RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);

        return MultipartBody.Part.createFormData(partName, file.getName(), requestBody);
    }

    private void askForPermission() {
        if ((ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE) +
                ContextCompat.checkSelfPermission(this,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE))
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.READ_EXTERNAL_STORAGE) ||
                    ActivityCompat.shouldShowRequestPermissionRationale(this,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

                Snackbar.make(this.findViewById(android.R.id.content),
                        "Please grant permissions to write data in sdcard",
                        Snackbar.LENGTH_INDEFINITE).setAction("ENABLE",
                        v -> ActivityCompat.requestPermissions(TambahBarangActivity.this,
                                new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,
                                        Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                REQUEST_CODE_PERMISSIONS)).show();
            } else {

                ActivityCompat.requestPermissions(TambahBarangActivity.this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,
                                Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        REQUEST_CODE_PERMISSIONS);
            }

        } else {
            showChooser();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission Granted
                showChooser();
            } else {
                // Permission Denied
                Toast.makeText(TambahBarangActivity.this, "Permission Denied!", Toast.LENGTH_SHORT).show();
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }
    private void dots(int page_position) {
        dot = new TextView[arrayList.size()];
        sliderDotspanel.removeAllViews();

        for (int i = 0; i < dot.length; i++) {;
            dot[i] = new TextView(TambahBarangActivity.this);
            dot[i].setText(Html.fromHtml("&#9673;"));
            dot[i].setTextSize(35);
            dot[i].setTextColor(Color.rgb(255,255,255));
            sliderDotspanel.addView(dot[i]);

        }
        //active dot



            dot[page_position].setTextColor(getResources().getColor(R.color.colorPrimary));


    }

    @Override
    public void onBackPressed() {
        if (id_role==1) {
            Intent intent = new Intent(TambahBarangActivity.this, AdminActivity.class);
            startActivity(intent);
            finish();
        }
        if (id_role==2) {
            Intent intent = new Intent(TambahBarangActivity.this, PetugasActivity.class);
            startActivity(intent);
            finish();
        }
    }
    private void validation() {

        if (editTextNama.getText().toString().isEmpty() || editTextDesc.getText().toString().isEmpty() || editTextHarga.getText().toString().isEmpty()) {
            Toast.makeText(TambahBarangActivity.this, "Fields cannot be empty", Toast.LENGTH_SHORT).show();
            if (arrayList.isEmpty()) {
                Toast.makeText(TambahBarangActivity.this, "Add Image", Toast.LENGTH_SHORT).show();
            }
            if (editTextNama.getText().toString().isEmpty()) {
                azriel_ti_nama.setError("Fields cannot be empty");
            } else {
                azriel_ti_nama.setError(null);
                azriel_ti_nama.setErrorEnabled(false);
            }
            if (editTextDesc.getText().toString().isEmpty()) {
                azriel_ti_alamat.setError("Fields cannot be empty");
            } else {
                azriel_ti_alamat.setError(null);
                azriel_ti_alamat.setErrorEnabled(false);
            }
            if (editTextHarga.getText().toString().isEmpty()) {
                azriel_ti_phone.setError("Fields cannot be empty");
            } else {
                azriel_ti_phone.setError(null);
                azriel_ti_phone.setErrorEnabled(false);
            }

        }else{
            azriel_ti_nama.setError(null);
            azriel_ti_nama.setErrorEnabled(false);
            azriel_ti_phone.setError(null);
            azriel_ti_phone.setErrorEnabled(false);
            azriel_ti_alamat.setError(null);
            azriel_ti_alamat.setErrorEnabled(false);
            if (arrayList.isEmpty()) {
                Toast.makeText(TambahBarangActivity.this, "Add Image", Toast.LENGTH_SHORT).show();
            }else {
                addBarang();
            }
            }

        }

        public void addBarang(){
            file = FileUtils.getFile(TambahBarangActivity.this, arrayList.get(0));
            mApiService.addBarang(editTextNama.getText().toString(),Date,editTextDesc.getText().toString(),file.getName(),Integer.parseInt(editTextHarga.getText().toString())).
                    enqueue(new Callback<ResponseBody>() {

                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                            if (response.isSuccessful()) {
                                try {
                                    JSONObject jsonObject = new JSONObject(response.body().string());
                                    if (jsonObject.getString("status").equals("true")){
                                        int id_barang=jsonObject.getJSONObject("data").getInt("id");

                                        uploadImagesToServer(id_barang);
                                    }

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            } else {
                                Toast.makeText(TambahBarangActivity.this, "Add Item Fail", Toast.LENGTH_SHORT).show();
                            }
                        }
                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) {

                        }
                    });

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

        }

    };


}
