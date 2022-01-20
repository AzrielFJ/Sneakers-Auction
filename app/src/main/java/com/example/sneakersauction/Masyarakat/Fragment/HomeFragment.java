package com.example.sneakersauction.Masyarakat.Fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;

import com.example.sneakersauction.Adapter.TabAdapter;
import com.example.sneakersauction.Administrator.AdminActivity;
import com.example.sneakersauction.LoginRegister.LoginRegisterActivity;
import com.example.sneakersauction.Petugas.Fragment.DibukaFragment;
import com.example.sneakersauction.Petugas.Fragment.DitutupFragment;
import com.example.sneakersauction.Petugas.Fragment.ListLelangFragment;
import com.example.sneakersauction.R;
import com.example.sneakersauction.SharedPreference.SharedPreference;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.tabs.TabLayout;

import org.jetbrains.annotations.NotNull;

import nl.joery.animatedbottombar.AnimatedBottomBar;

public class HomeFragment extends Fragment {
    View view;
    private static final String TAG = HomeFragment.class.getSimpleName();
    AnimatedBottomBar animatedBottomBar;
    FragmentManager fragmentManager;
    ImageView exit;
    TextView tv_menu;
    SharedPreference sharedPrefManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_list_lelang, container, false);

        sharedPrefManager=new SharedPreference(getContext());
        exit=view.findViewById(R.id.menu);
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog();
            }
        });

        animatedBottomBar = view.findViewById(R.id.nav_view_lelang);

        animatedBottomBar.selectTabById(R.id.navigation_open, true);
        animatedBottomBar.setTabAnimation(AnimatedBottomBar.TabAnimation.SLIDE);
        animatedBottomBar.setIconSize(40);

        animatedBottomBar.setTextAppearance(R.style.CustomText);
        fragmentManager = getActivity().getSupportFragmentManager();
        DibukaFragment profileFragment = new DibukaFragment();
        fragmentManager.beginTransaction().replace(R.id.nav_host_fragment_lelang, profileFragment)
                .commit();

        animatedBottomBar.setOnTabSelectListener(new AnimatedBottomBar.OnTabSelectListener() {
            @Override
            public void onTabReselected(int i, @NotNull AnimatedBottomBar.Tab tab) {

            }

            @Override
            public void onTabSelected(int lastIndex, @Nullable AnimatedBottomBar.Tab lastTab, int newIndex, @NotNull AnimatedBottomBar.Tab newTab) {
                Fragment fragment = null;
                switch (newTab.getId()) {
                    case R.id.navigation_open:
                        fragment = new DibukaFragment();
                        break;
                    case R.id.navigation_close:
                        fragment = new DitutupFragment();
                        break;


                }

                if (fragment != null) {
                    fragmentManager = getActivity().getSupportFragmentManager();
                    fragmentManager.beginTransaction().replace(R.id.nav_host_fragment_lelang, fragment)
                            .commit();
                } else {
                    Log.e(TAG, "Error in creating Fragment");
                }
            }
        });

        return view;

    }

    private void showDialog(){
        new MaterialAlertDialogBuilder(getContext(),
                R.style.MyThemeOverlay_MaterialComponents_MaterialAlertDialog)
                .setIcon(R.drawable.icon)
                .setTitle("Log out from App?")
                .setCancelable(false)
                .setPositiveButton("Log out",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        sharedPrefManager.saveSPBoolean(SharedPreference.SP_SUDAH_LOGIN, false);
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
