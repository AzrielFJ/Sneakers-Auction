package com.example.sneakersauction.Masyarakat.Fragment;

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
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sneakersauction.Administrator.Adapter.ListPetugasAdapter;
import com.example.sneakersauction.Administrator.Fragment.TambahBarang;
import com.example.sneakersauction.ApiHelper.ApiService;
import com.example.sneakersauction.ApiHelper.RetrofitClient;
import com.example.sneakersauction.ApiHelper.pojo.Model.Administrator.ListPetugas;
import com.example.sneakersauction.ApiHelper.pojo.Response.Administrator.ListPetugasResponse;
import com.example.sneakersauction.R;
import com.example.sneakersauction.SharedPreference.SharedPreference;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.sneakersauction.SharedPreference.SharedPreference.SP_ADDRES;
import static com.example.sneakersauction.SharedPreference.SharedPreference.SP_NAME;
import static com.example.sneakersauction.SharedPreference.SharedPreference.SP_PHONE;

import static com.example.sneakersauction.SharedPreference.SharedPreference.SP_USERNAME;

public class EditProfile extends AppCompatDialogFragment {
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
    private EditText editTextAddres;
    private EditText editTextFullName;
    private EditText editTextPhone;

    Context mContext;
    ApiService mApiService;
    RecyclerView recyclerView;
    SharedPreference sharedPreference;
    private EditProfile.ExampleDialogListener listener;
    private List<ListPetugas> petugasList=new ArrayList<>();
    View view;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        mContext=getActivity();
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_edit_profile, null);
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
        title.setText("Edit Profile");
        title.setGravity(Gravity.CENTER);
        builder.setView(view)
                .setCustomTitle(title)
                .setIcon(R.drawable.icon)
                .setNeutralButton("Edit Passowrd", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        EditPassword editProfile=new EditPassword();
                        editProfile.setOnDismissListener(new DialogInterface.OnDismissListener() {
                            @Override
                            public void onDismiss(DialogInterface dialog) {

                            }
                        });
                        editProfile.show(getActivity().getSupportFragmentManager(), "EditPassword");
                    }
                })
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .setPositiveButton("Edit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, final int i) {
                        sharedPreference.saveSPString(SP_NAME, editTextFullName.getText().toString());
                        sharedPreference.saveSPString(SP_USERNAME, editTextUsername.getText().toString());
                        sharedPreference.saveSPString(SP_PHONE,editTextPhone.getText().toString());
                        sharedPreference.saveSPString(SP_ADDRES,editTextAddres.getText().toString());
                        validation();


                    }
                });
        editTextUsername = view.findViewById(R.id.et_username);
        editTextAddres = view.findViewById(R.id.et_address);
        editTextFullName=view.findViewById(R.id.et_fullName);
        editTextPhone=view.findViewById(R.id.et_phone);
        editTextUsername.setText(sharedPreference.getSpUsername());
        editTextAddres.setText(sharedPreference.getSpAddres());
        editTextFullName.setText(sharedPreference.getSpName());
        editTextPhone.setText(sharedPreference.getSpPhone());
        return builder.create();
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            listener = (EditProfile.ExampleDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() +
                    "must implement ExampleDialogListener");
        }
    }

    public interface ExampleDialogListener {

        void applyTexts(String username, String password);
    }
    private void validation() {

        if (editTextFullName.getText().toString().isEmpty() || editTextUsername.getText().toString().isEmpty() || editTextPhone.getText().toString().isEmpty() || editTextAddres.getText().toString().isEmpty()) {
            Toast.makeText(getContext(), "Fields cannot be empty", Toast.LENGTH_SHORT).show();


        }else{

            if (editTextUsername.getText().toString().length()<5){
                Toast.makeText(getContext(), "Your Username must be at least 5 characters", Toast.LENGTH_SHORT).show();
            }else{

                    requestRegister();

            }

        }
    }

    private void requestRegister() {
        mApiService.editProfile(sharedPreference.getSpMasyarakatId(),sharedPreference.getSpUserId(),editTextFullName.getText().toString(),editTextAddres.getText().toString(),editTextPhone.getText().toString(),editTextUsername.getText().toString()).
                enqueue(new Callback<ListPetugasResponse>() {

                    @Override
                    public void onResponse(Call<ListPetugasResponse> call, Response<ListPetugasResponse> response) {

                        if (response.isSuccessful()) {
                            if (response.body().getStatus().equals(true)) {
                                Toast.makeText(mContext, "Edit Profile was Successfully Registered", Toast.LENGTH_SHORT).show();


                            }


                        } else {
                            Toast.makeText(mContext, "Edit Profile Fail", Toast.LENGTH_SHORT).show();
                        }
                    }
                    @Override
                    public void onFailure(Call<ListPetugasResponse> call, Throwable t) {

                    }
                });
    }
}
