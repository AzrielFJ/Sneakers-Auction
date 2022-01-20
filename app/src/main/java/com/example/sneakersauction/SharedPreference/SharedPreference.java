package com.example.sneakersauction.SharedPreference;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.sneakersauction.ApiHelper.pojo.Model.Lelang.Lelang;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class SharedPreference {
    public static final String SP_LELANG_APP = "sp_lelang";
    public static final String SP_USERNAME = "sp_username";
    public static final String SP_ADDRES = "sp_addres";
    public static final String SP_PHONE = "sp_phone";
    public static final String SP_ROLE = "sp_role";
    public static final String SP_NAME = "sp_name";
    public static final String SP_NAME_BARANG = "sp_name_barang";
    public static final String SP_DESC_BARANG = "sp_desc_barang";
    public static final String SP_TANGGAL = "sp_tanggal";
    public static final int SP_ROLE_ID = 1;
    public static final int SP_USER_ID = 2;
    public static final int SP_PETUGAS_ID = 3;
    public static final int SP_MASYARAKAT_ID = 4;
    public static final int SP_BARANG_ID = 5;
    public static final int SP_HARGA_BARANG = 6;
    public static final int SP_ID_LELANG = 7;
    public static final int SP_BID=8;
    public static final String SP_CODE="CODE";
    SharedPreferences sp;
    SharedPreferences.Editor spEditor;
    public static final String SP_SUDAH_LOGIN = "spSudahLogin";

    public SharedPreference(Context context){
         sp = context.getSharedPreferences(SP_LELANG_APP,Context.MODE_PRIVATE);
        spEditor = sp.edit();
    }
    public void saveSPString(String keySP, String value){
        spEditor.putString(keySP, value);
        spEditor.commit();
    }
    public void saveSPInt(String keySP, int value){
        spEditor.putInt(keySP, value);
        spEditor.commit();
    }
    public void saveSPArray(String keySP, Set<String> value){
        spEditor.putStringSet(keySP, value);
        spEditor.commit();
    }
    public void saveSPBoolean(String keySP, boolean value){
        spEditor.putBoolean(keySP, value);
        spEditor.commit();
    }
    public int getSpRoleId() {
        return sp.getInt(String.valueOf(SP_ROLE_ID), 1);
    }
    public int getSpUserId() {
        return sp.getInt(String.valueOf(SP_USER_ID), 1);
    }
    public int getSpMasyarakatId() {
        return sp.getInt(String.valueOf(SP_MASYARAKAT_ID), 1);
    }
    public int getSpPetugasId() {
        return sp.getInt(String.valueOf(SP_PETUGAS_ID), 1);
    }
    public int getSpBarangId() {
        return sp.getInt(String.valueOf(SP_BARANG_ID), 1);
    }


    public int getSpHargaBarang() {
        return sp.getInt(String.valueOf(SP_HARGA_BARANG), 1);
    }
    public int getSpIdLelang() {
        return sp.getInt(String.valueOf(SP_ID_LELANG), 1);
    }
    public int getSpBid() {
        return sp.getInt(String.valueOf(SP_BID), 1);
    }
    public Boolean getSPSudahLogin(){
        return sp.getBoolean(SP_SUDAH_LOGIN, false);
    }
    public String getSpUsername(){
        return sp.getString(SP_USERNAME,"");
    }
    public String getSpAddres(){
        return sp.getString(SP_ADDRES,"");
    }
    public Set<String> getTanggal(){return sp.getStringSet(SP_TANGGAL,null);}
    public String getSpPhone(){
        return sp.getString(SP_PHONE,"");
    }
    public String getSpNameBarang(){
        return sp.getString(SP_NAME_BARANG,"");
    }
    public String getSpDescBarang(){
        return sp.getString(SP_DESC_BARANG,"");
    }
    public String getSpRole(){
        return sp.getString(SP_ROLE,"");
    }
    public String getSpName(){
        return sp.getString(SP_NAME,"");
    }

    public String getSpCode(){
        return sp.getString(SP_CODE,"");
    }

}
