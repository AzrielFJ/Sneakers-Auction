package com.example.sneakersauction.Petugas.Fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.sneakersauction.LoginRegister.LoginRegisterActivity;
import com.example.sneakersauction.R;
import com.example.sneakersauction.SharedPreference.SharedPreference;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

public class ProfileFragment extends Fragment {
    TextView tv_nama_petugas;
    TextView tv_role;
    TextView tv_username;
    SharedPreference sharedPreference;
    View root;
    ImageView logout;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        root = inflater.inflate(R.layout.fragment_profile_petugas, container, false);
        sharedPreference=new SharedPreference(getContext());
        tv_nama_petugas=root.findViewById(R.id.tvNama);
        tv_role=root.findViewById(R.id.tv_role);
        tv_username=root.findViewById(R.id.tvUsername);
        tv_nama_petugas.setText(sharedPreference.getSpName());
        tv_role.setText("Officer");
        tv_username.setText(sharedPreference.getSpUsername());

        logout=root.findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
            }
        });

        return root;
    }

    private void showDialog() {
        new MaterialAlertDialogBuilder(getContext(),
                R.style.MyThemeOverlay_MaterialComponents_MaterialAlertDialog)
                .setIcon(R.drawable.icon)
                .setTitle("Log out from App?")
                .setCancelable(false)
                .setPositiveButton("Log out",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        sharedPreference.saveSPBoolean(SharedPreference.SP_SUDAH_LOGIN, false);
                        startActivity(new Intent(getContext(), LoginRegisterActivity.class)
                                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
                        getActivity().finish();
                    }
                })
                .setNegativeButton("Cancel",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                }).show();
    }
}
