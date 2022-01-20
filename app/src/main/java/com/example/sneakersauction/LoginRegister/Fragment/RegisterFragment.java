package com.example.sneakersauction.LoginRegister.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.sneakersauction.ApiHelper.ApiService;
import com.example.sneakersauction.ApiHelper.RetrofitClient;
import com.example.sneakersauction.Masyarakat.MasyarakatActivity;
import com.example.sneakersauction.R;
import com.example.sneakersauction.SharedPreference.SharedPreference;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.sneakersauction.SharedPreference.SharedPreference.SP_ADDRES;
import static com.example.sneakersauction.SharedPreference.SharedPreference.SP_MASYARAKAT_ID;
import static com.example.sneakersauction.SharedPreference.SharedPreference.SP_NAME;
import static com.example.sneakersauction.SharedPreference.SharedPreference.SP_PHONE;
import static com.example.sneakersauction.SharedPreference.SharedPreference.SP_ROLE;
import static com.example.sneakersauction.SharedPreference.SharedPreference.SP_USERNAME;

public class RegisterFragment extends Fragment {
    View root;
    ApiService mApiService;
    Context mContext;
    EditText azriel_et_nama;
    EditText azriel_et_username;
    EditText azriel_et_notelp;
    EditText azriel_et_alamat;
    EditText azriel_et_password;
    EditText azriel_et_password2;
    TextInputLayout azriel_ti_nama;
    TextInputLayout azriel_ti_phone;
    TextInputLayout azriel_ti_alamat;
    TextInputLayout azriel_ti_username;
    TextInputLayout azriel_ti_password;
    TextInputLayout azriel_ti_password2;
    SharedPreference sharedPrefManager;
    Button azriel_btn_reg;
    ProgressDialog azriel_loading;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_register, container, false);
        initComponent();
        sharedPrefManager = new SharedPreference(getContext());
        mApiService = RetrofitClient.getClient(RetrofitClient.BASE_URL_API).create(ApiService.class);
        functionRegister();

        return root;
    }

    private void functionRegister() {
        azriel_btn_reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validation();
            }
        });
    }

    private void validation() {

            if (azriel_et_nama.getText().toString().isEmpty() || azriel_et_alamat.getText().toString().isEmpty() || azriel_et_notelp.getText().toString().isEmpty() || azriel_et_username.getText().toString().isEmpty() || azriel_et_password.getText().toString().isEmpty()) {
                Toast.makeText(getContext(), "Fields cannot be empty", Toast.LENGTH_SHORT).show();
                if (azriel_et_nama.getText().toString().isEmpty()) {
                    azriel_ti_nama.setError("Fields cannot be empty");
                } else {
                    azriel_ti_nama.setError(null);
                    azriel_ti_nama.setErrorEnabled(false);
                }
                if (azriel_et_notelp.getText().toString().isEmpty()) {
                    azriel_ti_phone.setError("Fields cannot be empty");
                } else {
                    azriel_ti_phone.setError(null);
                    azriel_ti_phone.setErrorEnabled(false);
                }
                if (azriel_et_alamat.getText().toString().isEmpty()) {
                    azriel_ti_alamat.setError("Fields cannot be empty");
                } else {
                    azriel_ti_alamat.setError(null);
                    azriel_ti_alamat.setErrorEnabled(false);
                }
                if (azriel_et_username.getText().toString().isEmpty()) {
                    azriel_ti_username.setError("Fields cannot be empty");
                } else {
                    azriel_ti_username.setError(null);
                    azriel_ti_username.setErrorEnabled(false);
                }
                if (azriel_et_password.getText().toString().isEmpty()) {
                    azriel_ti_password.setError("Fields cannot be empty");
                } else {
                    azriel_ti_password.setError(null);
                    azriel_ti_password.setErrorEnabled(false);
                }
                if (azriel_et_password2.getText().toString().isEmpty()) {
                    azriel_ti_password2.setError("Fields cannot be empty");
                } else {
                    azriel_ti_password2.setError(null);
                    azriel_ti_password2.setErrorEnabled(false);
                }

            }else{
                azriel_ti_nama.setError(null);
                azriel_ti_nama.setErrorEnabled(false);
                azriel_ti_phone.setError(null);
                azriel_ti_phone.setErrorEnabled(false);
                azriel_ti_alamat.setError(null);
                azriel_ti_alamat.setErrorEnabled(false);
                azriel_ti_username.setError(null);
                azriel_ti_username.setErrorEnabled(false);
                azriel_ti_password.setError(null);
                azriel_ti_password.setErrorEnabled(false);
                azriel_ti_password2.setError(null);
                azriel_ti_password2.setErrorEnabled(false);
                if (azriel_et_username.getText().toString().length()<5){

                    azriel_ti_username.setError("Your Username must be at least 5 characters");
                }else{
                    azriel_ti_username.setError(null);
                    azriel_ti_username.setErrorEnabled(false);
                }
                if (azriel_et_notelp.getText().toString().length()<11){
                    azriel_ti_phone.setError("Your Phone Number must be at least 11 digits long");
                }else {
                    azriel_ti_phone.setError(null);
                    azriel_ti_phone.setErrorEnabled(false);
                }
                if(azriel_et_password.getText().toString().length()<8 && azriel_et_password2.getText().toString().length()<8  &&!isValidPassword(azriel_et_password.getText().toString()) && !isValidPassword(azriel_et_password2.getText().toString())){
                        azriel_ti_password.setError("Your Password must be at least 8 characters");
                    azriel_ti_password2.setError("Your Password must be at least 8 characters");

                }else{
                    azriel_ti_password.setError(null);
                    azriel_ti_password.setErrorEnabled(false);
                    azriel_ti_password2.setError(null);
                    azriel_ti_password2.setErrorEnabled(false);


                    if(!azriel_et_password.getText().toString().equals(azriel_et_password2.getText().toString())){
                        azriel_ti_password.setError("Your Password and Confirmation Password do not match");
                        azriel_ti_password2.setError("Your Password and Confirmation Password do not match");
                    }else{
                        azriel_ti_password.setError(null);
                        azriel_ti_password.setErrorEnabled(false);
                        azriel_ti_password2.setError(null);
                        azriel_ti_password2.setErrorEnabled(false);
                        requestRegister();
                    }
                }

            }





    }

    private void initComponent() {
        azriel_et_nama = root.findViewById(R.id.et_fullName);
        azriel_et_notelp = root.findViewById(R.id.et_phone);
        azriel_et_username = root.findViewById(R.id.et_username);
        azriel_et_password = root.findViewById(R.id.et_password);
        azriel_et_password2 = root.findViewById(R.id.et_password2);
        azriel_ti_nama = root.findViewById(R.id.ly_fullName);
        azriel_ti_phone = root.findViewById(R.id.ly_phone);
        azriel_ti_username = root.findViewById(R.id.ly_username);
        azriel_ti_password = root.findViewById(R.id.ly_password);
        azriel_ti_password2 = root.findViewById(R.id.ly_password2);
        azriel_et_alamat=root.findViewById(R.id.et_address);
        azriel_ti_alamat=root.findViewById(R.id.ly_address);
        azriel_btn_reg = root.findViewById(R.id.bt_register);
    }

    private void requestRegister() {
        azriel_loading = ProgressDialog.show(getContext(), null, "Please Wait...", true, false);
        mApiService.register(azriel_et_nama.getText().toString(),
                azriel_et_notelp.getText().toString(),
                azriel_et_alamat.getText().toString(),
                azriel_et_username.getText().toString(),
                azriel_et_password.getText().toString()
        ).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    azriel_loading.dismiss();
                    try {
                        JSONObject jsonRESULTS = new JSONObject(response.body().string());
                        if (jsonRESULTS.getString("status").equals("true")){
                            int user_id=jsonRESULTS.getJSONObject("data").getInt("user_id");
                            int role_id=jsonRESULTS.getJSONObject("data").getInt("role_id");
                            String name=jsonRESULTS.getJSONObject("data").getString("nama_lengkap");
                            String username=jsonRESULTS.getJSONObject("data").getString("username");
                            String role=jsonRESULTS.getJSONObject("data").getString("role");
                            String phone=jsonRESULTS.getJSONObject("data").getString("no_telp");
                            String addres=jsonRESULTS.getJSONObject("data").getString("alamat");
                            int id_masyarakat=jsonRESULTS.getJSONObject("data").getInt("id_masyarakat");
                            sharedPrefManager.saveSPInt(String.valueOf(SharedPreference.SP_ROLE_ID), role_id);
                            Toast toast=Toast.makeText(getContext(),"Regsiter is Successful",Toast.LENGTH_SHORT);
                            View view=toast.getView();
                            view.setBackgroundResource(R.drawable.bg_toast);
                            toast.show();
                            sharedPrefManager.saveSPInt(String.valueOf(SP_MASYARAKAT_ID),id_masyarakat);
                            sharedPrefManager.saveSPString(SP_NAME, name);
                            sharedPrefManager.saveSPString(SP_USERNAME, username);
                            sharedPrefManager.saveSPString(SP_ROLE, role);
                            sharedPrefManager.saveSPString(SP_PHONE,phone);
                            sharedPrefManager.saveSPString(SP_ADDRES,addres);
                            sharedPrefManager.saveSPInt(String.valueOf(SharedPreference.SP_USER_ID), user_id);
                            sharedPrefManager.saveSPBoolean(sharedPrefManager.SP_SUDAH_LOGIN, true);
                            startActivity(new Intent(getContext(), MasyarakatActivity.class)
                                    .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
                            getActivity().finish();

                        }
                        else {
                            Toast toast=Toast.makeText(getContext(),"Username Has Been Registered",Toast.LENGTH_SHORT);
                            View view=toast.getView();
                            view.setBackgroundResource(R.drawable.bg_toast);
                            toast.show();
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e("debug", "onFailure: ERROR > " + t.getMessage());
                Toast.makeText(getContext(), "Koneksi Internet Bermasalah", Toast.LENGTH_SHORT).show();
            }
        });

    }
    public static boolean isValidPassword(final String password) {

        Pattern pattern;
        Matcher matcher;
        final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{4,}$";
        pattern = Pattern.compile(PASSWORD_PATTERN);
        matcher = pattern.matcher(password);

        return matcher.matches();

    }
}