package com.example.sneakersauction.Administrator.Fragment;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;


import com.example.sneakersauction.Administrator.Adapter.ListPetugasAdapter;
import com.example.sneakersauction.ApiHelper.ApiService;
import com.example.sneakersauction.ApiHelper.RetrofitClient;
import com.example.sneakersauction.ApiHelper.pojo.Model.Administrator.ListPetugas;
import com.example.sneakersauction.ApiHelper.pojo.Response.Administrator.ListPetugasResponse;
import com.example.sneakersauction.R;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TambahPetugas extends AppCompatDialogFragment  {
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
    private EditText editTextUsername;
    private EditText editTextPassword;
    private EditText editTextPassword2;
    private EditText editTextFullName;
    TextInputLayout azriel_ti_username;
    TextInputLayout azriel_ti_password;
    TextInputLayout azriel_ti_password2;
    TextInputLayout azriel_ti_nama;
    Context mContext;
    ApiService mApiService;
    RecyclerView recyclerView;
    private TambahBarang.ExampleDialogListener listener;
    private List<ListPetugas> petugasList=new ArrayList<>();
    View view;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        mContext=getActivity();
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_add_petugas, null);
        mApiService = RetrofitClient.getClient(RetrofitClient.BASE_URL_API).create(ApiService.class);
        TextView title = new TextView(getContext());
        title.setTextColor(ContextCompat.getColor(getContext(), R.color.colorPrimary));
        title.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
        title.setTypeface(Typeface.DEFAULT_BOLD);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        lp.setMargins(0, 20, 0, 0);
        title.setPadding(0,30,0,0);
        title.setLayoutParams(lp);
        title.setText("Add Officers");
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
        editTextUsername = view.findViewById(R.id.et_username);
        editTextPassword = view.findViewById(R.id.et_password);
        editTextPassword2 = view.findViewById(R.id.et_password2);
        editTextFullName=view.findViewById(R.id.et_fullName);
        azriel_ti_nama = view.findViewById(R.id.ly_fullName);
        azriel_ti_username = view.findViewById(R.id.ly_username);
        azriel_ti_password = view.findViewById(R.id.ly_password);
        azriel_ti_password2 = view.findViewById(R.id.ly_password2);
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
    private void validation() {

        if (editTextFullName.getText().toString().isEmpty() || editTextUsername.getText().toString().isEmpty() || editTextPassword.getText().toString().isEmpty()) {
            Toast.makeText(getContext(), "Fields cannot be empty", Toast.LENGTH_SHORT).show();


        }else{

            if (editTextUsername.getText().toString().length()<5){
                Toast.makeText(getContext(), "Your Username must be at least 5 characters", Toast.LENGTH_SHORT).show();
            }else{

            }

            if(editTextPassword.getText().toString().length()<8 && editTextPassword2.getText().toString().length()<8  &&!isValidPassword(editTextPassword.getText().toString()) && !isValidPassword(editTextPassword2.getText().toString())){

                Toast.makeText(getContext(), "Your Password must be at least 8 characters", Toast.LENGTH_SHORT).show();

            }else{

                if(!editTextPassword.getText().toString().equals(editTextPassword2.getText().toString())){

                    Toast.makeText(getContext(), "Your Password and Confirmation Password do not match", Toast.LENGTH_SHORT).show();
                }else{

                    requestRegister();
                }
            }

        }
    }

    private void requestRegister() {
        mApiService.registerPetugas(editTextFullName.getText().toString(),editTextUsername.getText().toString(),editTextPassword.getText().toString(),2).
                enqueue(new Callback<ListPetugasResponse>() {

                    @Override
                    public void onResponse(Call<ListPetugasResponse> call, Response<ListPetugasResponse> response) {

                        if (response.isSuccessful()) {
                            if (response.body().getStatus().equals(true)) {
                                Toast.makeText(mContext, "The Officer was Successfully Registered", Toast.LENGTH_SHORT).show();


                            } else {
                                Toast toast = Toast.makeText(mContext, "Username Has Been Registered", Toast.LENGTH_SHORT);
                                View view = toast.getView();
                                view.setBackgroundResource(R.drawable.bg_toast);
                                toast.show();
                            }


                        } else {
                            Toast.makeText(mContext, "Make the Team Fail", Toast.LENGTH_SHORT).show();
                        }
                    }
                    @Override
                    public void onFailure(Call<ListPetugasResponse> call, Throwable t) {

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
