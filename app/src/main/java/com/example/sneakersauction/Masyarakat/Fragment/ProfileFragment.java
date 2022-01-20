package com.example.sneakersauction.Masyarakat.Fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.sneakersauction.Administrator.Fragment.TambahPetugas;
import com.example.sneakersauction.LoginRegister.LoginRegisterActivity;
import com.example.sneakersauction.R;
import com.example.sneakersauction.SharedPreference.SharedPreference;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

public class ProfileFragment extends Fragment implements EditProfile.ExampleDialogListener,  EditPassword.ExampleDialogListener {
    TextView tv_nama;
    TextView tv_role;
    TextView tv_username;
    TextView tv_alamat;
    TextView tv_phone;
    SharedPreference sharedPreference;
    View root;
    ImageView bt_logout;
    ImageView bt_edit;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_profile_masyarakat, container, false);

        sharedPreference=new SharedPreference(getContext());
        initComponent();
        return root;
    }

    private void initComponent() {
        tv_nama=root.findViewById(R.id.tvNama);
        tv_role=root.findViewById(R.id.tv_role);
        tv_username=root.findViewById(R.id.tvUsername);
        tv_alamat=root.findViewById(R.id.tv_alamat);
        tv_phone=root.findViewById(R.id.tv_telp);

        tv_nama.setText(sharedPreference.getSpName());
        tv_role.setText("Public");
        tv_username.setText(sharedPreference.getSpUsername());
        tv_alamat.setText(sharedPreference.getSpAddres());
        tv_phone.setText(sharedPreference.getSpPhone());
        bt_logout=root.findViewById(R.id.logout);
        bt_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
            }
        });
        bt_edit=root.findViewById(R.id.btnEdit);
        bt_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog();
            }
        });
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
    private void openDialog() {
        EditProfile editProfile=new EditProfile();
        editProfile.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                tv_nama.setText(sharedPreference.getSpName());
                tv_role.setText("Public");
                tv_username.setText(sharedPreference.getSpUsername());
                tv_alamat.setText(sharedPreference.getSpAddres());
                tv_phone.setText(sharedPreference.getSpPhone());
            }
        });
        editProfile.show(getActivity().getSupportFragmentManager(), "EditProfile");
    }
    private void openDialog2() {
        EditPassword editProfile=new EditPassword();
        editProfile.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {

            }
        });
        editProfile.show(getActivity().getSupportFragmentManager(), "EditPassword");
    }

    @Override
    public void applyTexts(String username, String password) {

    }

}


