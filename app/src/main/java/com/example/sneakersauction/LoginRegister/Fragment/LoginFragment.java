package com.example.sneakersauction.LoginRegister.Fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import com.example.sneakersauction.Administrator.AdminActivity;
import com.example.sneakersauction.ApiHelper.ApiService;
import com.example.sneakersauction.ApiHelper.RetrofitClient;
import com.example.sneakersauction.LoginRegister.ForgotPassword;
import com.example.sneakersauction.Masyarakat.MasyarakatActivity;
import com.example.sneakersauction.Petugas.PetugasActivity;
import com.example.sneakersauction.R;
import com.example.sneakersauction.SharedPreference.SharedPreference;
import com.google.android.material.textfield.TextInputLayout;
//import com.vonage.client.VonageClient;

import org.json.JSONObject;

import java.util.Random;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.sneakersauction.SharedPreference.SharedPreference.SP_ADDRES;
import static com.example.sneakersauction.SharedPreference.SharedPreference.SP_CODE;
import static com.example.sneakersauction.SharedPreference.SharedPreference.SP_MASYARAKAT_ID;
import static com.example.sneakersauction.SharedPreference.SharedPreference.SP_NAME;
import static com.example.sneakersauction.SharedPreference.SharedPreference.SP_PHONE;
import static com.example.sneakersauction.SharedPreference.SharedPreference.SP_ROLE;
import static com.example.sneakersauction.SharedPreference.SharedPreference.SP_USERNAME;

public class  LoginFragment extends Fragment {
    EditText azriel_et_username;
    EditText azriel_et_password;
    Button azriel_bt_login;
    ApiService mApiService;
    TextInputLayout azriel_layoutUsername;
    TextInputLayout azriel_layoutPassword;
    ConstraintLayout azriel_constraintLayout;
    ProgressDialog azriel_loading;
    View root;
    SharedPreference sharedPreferences;
    TextView forgot;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_login, container, false);
//        VonageClient client = VonageClient.builder().apiKey("ed2d5a25").apiSecret("dC0W2Bxo2HbNwNdW").build();
        forgot=root.findViewById(R.id.forgot);
        Random random = new Random();
        forgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = String.format("%04d", random.nextInt(10000));
                sharedPreferences.saveSPString(SP_CODE,id);
                Intent intent=new Intent(getContext(), ForgotPassword.class);
                startActivity(intent);
            }
        });
        initComponent();
        functionLogin();
        sharedPreferences=new SharedPreference(getContext());
        mApiService = RetrofitClient.getClient(RetrofitClient.BASE_URL_API).create(ApiService.class);
        return root;
    }

    private void functionLogin() {
        azriel_bt_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validation();
            }
        });
    }

    private void validation() {
        if (azriel_et_username.getText().toString().isEmpty() || azriel_et_password.getText().toString().isEmpty()) {
            Toast.makeText(getContext(), "Fields cannot be empty", Toast.LENGTH_SHORT).show();

            if (azriel_et_username.getText().toString().isEmpty()) {
                azriel_layoutUsername.setError("Fields cannot be empty");
            } else {
                azriel_layoutUsername.setError(null);
                azriel_layoutUsername.setErrorEnabled(false);
            }
            if (azriel_et_password.getText().toString().isEmpty()) {
                azriel_layoutPassword.setError("Fields cannot be empty");
            } else {
                azriel_layoutPassword.setError(null);
                azriel_layoutPassword.setErrorEnabled(false);
            }
        } else {
                azriel_layoutUsername.setError(null);
                azriel_layoutUsername.setErrorEnabled(false);
                azriel_layoutPassword.setError(null);
                azriel_layoutPassword.setErrorEnabled(false);

            requestLogin();
        }
    }

    private void requestLogin() {
        azriel_loading = ProgressDialog.show(getContext(), null, "Please Wait...", true, false);
        String Username=azriel_et_username.getText().toString();
        String Password=azriel_et_password.getText().toString();
        mApiService.logIn(Username,Password).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.isSuccessful()){
                    azriel_loading.dismiss();
                    try{
                        JSONObject jsonObject=new JSONObject(response.body().string());
                        if (jsonObject.getString("status").equals("true")){
                            int user_id=jsonObject.getJSONObject("data").getInt("user_id");
                            int role_id=jsonObject.getJSONObject("data").getInt("role_id");

                            sharedPreferences.saveSPInt(String.valueOf(SharedPreference.SP_ROLE_ID), role_id);
                            if (role_id==1){
                                int petugas_id=jsonObject.getJSONObject("data").getInt("id_petugas");

                                Toast toast=Toast.makeText(getContext(),"Login is Successful",Toast.LENGTH_SHORT);
                                View view=toast.getView();
                                view.setBackgroundResource(R.drawable.bg_toast);
                                toast.show();

                                sharedPreferences.saveSPInt(String.valueOf(SharedPreference.SP_USER_ID), user_id);
                                sharedPreferences.saveSPInt(String.valueOf(SharedPreference.SP_PETUGAS_ID), petugas_id);
                                sharedPreferences.saveSPBoolean(sharedPreferences.SP_SUDAH_LOGIN, true);
                                startActivity(new Intent(getContext(), AdminActivity.class)
                                        .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
                                getActivity().finish();
                            }
                            else if(role_id==2){
                                int petugas_id=jsonObject.getJSONObject("data").getInt("id_petugas");
                                String name=jsonObject.getJSONObject("data").getString("nama_petugas");
                                String username=jsonObject.getJSONObject("data").getString("username");
                                String role=jsonObject.getJSONObject("data").getString("role");
                                Toast toast=Toast.makeText(getContext(),"Login is Successful",Toast.LENGTH_SHORT);
                                View view=toast.getView();
                                view.setBackgroundResource(R.drawable.bg_toast);
                                toast.show();
                                sharedPreferences.saveSPString(SP_NAME, name);
                                sharedPreferences.saveSPString(SP_USERNAME, username);
                                sharedPreferences.saveSPString(SP_ROLE, role);
                                sharedPreferences.saveSPInt(String.valueOf(SharedPreference.SP_PETUGAS_ID), petugas_id);
                                sharedPreferences.saveSPInt(String.valueOf(SharedPreference.SP_USER_ID), user_id);
                                sharedPreferences.saveSPBoolean(sharedPreferences.SP_SUDAH_LOGIN, true);
                                startActivity(new Intent(getContext(), PetugasActivity.class)
                                        .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
                                getActivity().finish();
                            }
                            else if(role_id==3) {
                                Toast toast=Toast.makeText(getContext(),"Login is Successful",Toast.LENGTH_SHORT);
                                String name=jsonObject.getJSONObject("data").getString("nama_lengkap");
                                String username=jsonObject.getJSONObject("data").getString("username");
                                String role=jsonObject.getJSONObject("data").getString("role");
                                String phone=jsonObject.getJSONObject("data").getString("no_telp");
                                String addres=jsonObject.getJSONObject("data").getString("alamat");
                                int id_masyarakat=jsonObject.getJSONObject("data").getInt("id_masyarakat");
                                View view=toast.getView();
                                view.setBackgroundResource(R.drawable.bg_toast);
                                toast.show();
                                sharedPreferences.saveSPInt(String.valueOf(SP_MASYARAKAT_ID),id_masyarakat);
                                sharedPreferences.saveSPString(SP_NAME, name);
                                sharedPreferences.saveSPString(SP_USERNAME, username);
                                sharedPreferences.saveSPString(SP_ROLE, role);
                                sharedPreferences.saveSPString(SP_PHONE,phone);
                                sharedPreferences.saveSPString(SP_ADDRES,addres);
                                sharedPreferences.saveSPInt(String.valueOf(SharedPreference.SP_USER_ID), user_id);
                                sharedPreferences.saveSPBoolean(sharedPreferences.SP_SUDAH_LOGIN, true);
                                startActivity(new Intent(getContext(), MasyarakatActivity.class)
                                        .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
                                getActivity().finish();
                            }


                        }
                        else if(jsonObject.getString("status").equals("false")){
                            Toast toast=Toast.makeText(getContext(),"Wrong Password",Toast.LENGTH_SHORT);
                            View view=toast.getView();
                            view.setBackgroundResource(R.drawable.bg_toast);
                            toast.show();
                        }
                        else if(jsonObject.getString("status").equals("daftar")){
                            Toast toast=Toast.makeText(getContext(),"Your Account is Not Registered Yet",Toast.LENGTH_SHORT);
                            View view=toast.getView();
                            view.setBackgroundResource(R.drawable.bg_toast);
                            toast.show();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast toast=Toast.makeText(getContext(),"Problematic Internet Connection",Toast.LENGTH_SHORT);
                View view=toast.getView();
                view.setBackgroundResource(R.drawable.bg_toast);
                toast.show();
                azriel_loading.dismiss();
            }
        });
    }

    private void initComponent() {
        azriel_constraintLayout=root.findViewById(R.id.ly);
        azriel_et_username=root.findViewById(R.id.et_username);
        azriel_et_password=root.findViewById(R.id.et_password);
        azriel_bt_login=root.findViewById(R.id.bt_login);
        azriel_layoutUsername=root.findViewById(R.id.ly_username);
        azriel_layoutPassword=root.findViewById(R.id.ly_password);
    }

}
