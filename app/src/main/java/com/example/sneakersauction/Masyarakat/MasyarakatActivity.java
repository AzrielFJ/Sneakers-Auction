package com.example.sneakersauction.Masyarakat;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.sneakersauction.Administrator.AdminActivity;
import com.example.sneakersauction.Masyarakat.Fragment.EditProfile;
import com.example.sneakersauction.Masyarakat.Fragment.HomeFragment;
import com.example.sneakersauction.Masyarakat.Fragment.ProfileFragment;
import com.example.sneakersauction.Petugas.Fragment.ListBarangFragment;
import com.example.sneakersauction.Petugas.Fragment.ListLelangFragment;
import com.example.sneakersauction.R;
import android.os.Bundle;
import android.util.Log;

import org.jetbrains.annotations.NotNull;

import nl.joery.animatedbottombar.AnimatedBottomBar;

public class MasyarakatActivity extends  AppCompatActivity implements EditProfile.ExampleDialogListener {
        private static final String TAG = MasyarakatActivity.class.getSimpleName();
        AnimatedBottomBar animatedBottomBar;
        FragmentManager fragmentManager;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.activity_masyarakat);
                animatedBottomBar = findViewById(R.id.nav_view_masyarakat);

                animatedBottomBar.selectTabById(R.id.navigation_profile2, true);
                animatedBottomBar.setTabAnimation(AnimatedBottomBar.TabAnimation.SLIDE);
                animatedBottomBar.setIconSize(40);

                animatedBottomBar.setTextAppearance(R.style.CustomText);
                fragmentManager = getSupportFragmentManager();
                ProfileFragment homeFragment = new ProfileFragment();
                fragmentManager.beginTransaction().replace(R.id.nav_host_fragment_masyarakat, homeFragment)
                        .commit();


                animatedBottomBar.setOnTabSelectListener(new AnimatedBottomBar.OnTabSelectListener() {
                        @Override
                        public void onTabReselected(int i, @NotNull AnimatedBottomBar.Tab tab) {

                        }

                        @Override
                        public void onTabSelected(int lastIndex, @Nullable AnimatedBottomBar.Tab lastTab, int newIndex, @NotNull AnimatedBottomBar.Tab newTab) {
                                Fragment fragment = null;
                                switch (newTab.getId()) {
                                        case R.id.navigation_profile2:
                                                fragment = new ProfileFragment();
                                                break;

                                        case R.id.navigation_home_public:
                                                fragment = new HomeFragment();
                                                break;

                                }

                                if (fragment != null) {
                                        fragmentManager = getSupportFragmentManager();
                                        fragmentManager.beginTransaction().replace(R.id.nav_host_fragment_masyarakat, fragment)
                                                .commit();
                                } else {
                                        Log.e(TAG, "Error in creating Fragment");
                                }
                        }
                });
        }

        @Override
        public void applyTexts(String username, String password) {

        }

        @Override
        public void onPointerCaptureChanged(boolean hasCapture) {

        }
}