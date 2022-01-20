package com.example.sneakersauction.LoginRegister.Fragment;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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

import com.example.sneakersauction.ApiHelper.ApiService;
import com.example.sneakersauction.ApiHelper.RetrofitClient;
import com.example.sneakersauction.ApiHelper.pojo.Model.Administrator.ListPetugas;
import com.example.sneakersauction.ApiHelper.pojo.Response.Administrator.ListPetugasResponse;
import com.example.sneakersauction.LoginRegister.LoginRegisterActivity;
import com.example.sneakersauction.Masyarakat.Fragment.EditProfile;
import com.example.sneakersauction.R;
import com.example.sneakersauction.SharedPreference.SharedPreference;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForgotPassword extends AppCompatDialogFragment {
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
    private EditText editPassword2;


    Context mContext;
    ApiService mApiService;
    RecyclerView recyclerView;
    SharedPreference sharedPreference;
    private ForgotPassword.ExampleDialogListener listener;
    private List<ListPetugas> petugasList=new ArrayList<>();
    View view;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        mContext=getActivity();
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.forgot_password, null);
        mApiService = RetrofitClient.getClient(RetrofitClient.BASE_URL_API).create(ApiService.class);
        sharedPreference=new SharedPreference(getContext());

        TextView title = new TextView(getContext());
        title.setTextColor(ContextCompat.getColor(getContext(), R.color.colorPrimary));
        title.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
        title.setTypeface(Typeface.DEFAULT_BOLD);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        lp.setMargins(0, 20, 0, 0);
        title.setPadding(0,30,0,0);
        title.setLayoutParams(lp);
        title.setText("Edit Password");
        title.setGravity(Gravity.CENTER);
        builder.setView(view)
                .setCustomTitle(title)
                .setIcon(R.drawable.icon)
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .setPositiveButton("Reset Password", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, final int i) {
                        validation();

                    }
                });

        editPassword2=view.findViewById(R.id.et_password2);
        return builder.create();
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            listener = (ForgotPassword.ExampleDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() +
                    "must implement ExampleDialogListener");
        }
    }

    public interface ExampleDialogListener {

        void applyTexts(String username, String password);
    }
    public void editpassword(){
        mApiService.editPassword2(sharedPreference.getSpUsername(),editPassword2.getText().toString()).
                enqueue(new Callback<ListPetugasResponse>() {

                    @Override
                    public void onResponse(Call<ListPetugasResponse> call, Response<ListPetugasResponse> response) {

                        if (response.isSuccessful()) {
                            if (response.body().getStatus().equals(true)) {
                                Toast.makeText(mContext, "Reset Password was Successfully ", Toast.LENGTH_SHORT).show();
                                Intent intent=new Intent(getActivity(), LoginRegisterActivity.class);
                                startActivity(intent);

                            }else {


                            }


                        } else {
                            Toast.makeText(mContext, "Edit Password Fail", Toast.LENGTH_SHORT).show();
                        }
                    }
                    @Override
                    public void onFailure(Call<ListPetugasResponse> call, Throwable t) {

                    }
                });

    }
    private void validation() {

        if(editPassword2.getText().toString().isEmpty()) {
            Toast.makeText(getContext(), "Fields cannot be empty", Toast.LENGTH_SHORT).show();


        }else{


            if(editPassword2.getText().toString().length()<8 && !isValidPassword(editPassword2.getText().toString())){

                Toast.makeText(getContext(), "Your Password must be at least 8 characters", Toast.LENGTH_SHORT).show();

            }else{

                editpassword();
            }

        }
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
