package com.example.sneakersauction.LoginRegister;

import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.sneakersauction.ApiHelper.ApiService;
import com.example.sneakersauction.ApiHelper.RetrofitClient;
import com.example.sneakersauction.ApiHelper.pojo.Response.Lelang.PenawarResponse;
import com.example.sneakersauction.Masyarakat.Fragment.EditPassword;
import com.example.sneakersauction.R;
import com.example.sneakersauction.SharedPreference.SharedPreference;
import com.google.android.material.textfield.TextInputLayout;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForgotPassword extends AppCompatActivity implements com.example.sneakersauction.LoginRegister.Fragment.ForgotPassword.ExampleDialogListener {
    Button sendcode;
    Button reset;

    ApiService apiService;
    EditText et_username;
    EditText editpassword;
    TextInputLayout azriel_ti_phone;
    TextInputLayout azriel_ti_username;
    EditText editpassword2;
    TextInputLayout azriel_ti_phone2;
    SharedPreference sharedPreference;
    String code;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        apiService= RetrofitClient.getClient(RetrofitClient.BASE_URL_API).create(ApiService.class);
        sharedPreference=new SharedPreference(this);
        et_username=findViewById(R.id.et_username);
        editpassword=findViewById(R.id.et_phone);
        azriel_ti_phone = findViewById(R.id.ly_password);
        editpassword2=findViewById(R.id.et_phone2);
        azriel_ti_phone2 = findViewById(R.id.ly_password2);
        azriel_ti_username = findViewById(R.id.ly_username);

        reset=findViewById(R.id.reset);
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sharedPreference.saveSPString(SharedPreference.SP_USERNAME,et_username.getText().toString());

                validation();
            }
        });
        sendcode=findViewById(R.id.send);
        sendcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editpassword.getText().toString().isEmpty()){
                    azriel_ti_phone.setError("Fields cannot be empty");
                }else {
                    apiService.resetpassword(sharedPreference.getSpCode()).enqueue(new Callback<PenawarResponse>() {
                        @Override
                        public void onResponse(Call<PenawarResponse> call, Response<PenawarResponse> response) {
                            if (response.isSuccessful()) {
                                Toast.makeText(ForgotPassword.this, "Send Code Success check Message", Toast.LENGTH_LONG).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<PenawarResponse> call, Throwable t) {

                        }
                    });
                }
            }
        });
    }
    private void validation() {

        if(et_username.getText().toString().isEmpty() || editpassword.getText().toString().isEmpty()) {
            Toast.makeText(ForgotPassword.this, "Fields cannot be empty", Toast.LENGTH_SHORT).show();
            if (editpassword.getText().toString().isEmpty()) {
                azriel_ti_phone.setError("Fields cannot be empty");
            } else {
                azriel_ti_phone.setError(null);
                azriel_ti_phone.setErrorEnabled(false);
            }
            if (et_username.getText().toString().isEmpty()) {
                azriel_ti_username.setError("Fields cannot be empty");
            } else {
                azriel_ti_username.setError(null);
                azriel_ti_username.setErrorEnabled(false);
            }
        }else{
            azriel_ti_phone.setError(null);
            azriel_ti_phone.setErrorEnabled(false);
            azriel_ti_username.setError(null);
            azriel_ti_username.setErrorEnabled(false);
            code= editpassword2.getText().toString();
            if (code.equals(sharedPreference.getSpCode())){
                com.example.sneakersauction.LoginRegister.Fragment.ForgotPassword editProfile=new com.example.sneakersauction.LoginRegister.Fragment.ForgotPassword();
                editProfile.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {

                    }
                });
                editProfile.show(getSupportFragmentManager(), "EditPassword");
            }else{
                Toast.makeText(ForgotPassword.this,"Code Verification is Wrong",Toast.LENGTH_LONG).show();
            }

        }
    }

    @Override
    public void applyTexts(String username, String password) {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}