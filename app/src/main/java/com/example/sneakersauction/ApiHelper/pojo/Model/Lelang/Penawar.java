package com.example.sneakersauction.ApiHelper.pojo.Model.Lelang;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Penawar {
    @SerializedName("id_history")
    @Expose
    private Integer idHistory;
    @SerializedName("lelang_id")
    @Expose
    private Integer lelangId;
    @SerializedName("barang_id")
    @Expose
    private Integer barangId;
    @SerializedName("user_id")
    @Expose
    private Integer userId;
    @SerializedName("penawaran_harga")
    @Expose
    private Integer penawaranHarga;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("id_user")
    @Expose
    private Integer idUser;
    @SerializedName("id_masyarakat")
    @Expose
    private Integer idMasyarakat;
    @SerializedName("nama_lengkap")
    @Expose
    private String namaLengkap;
    @SerializedName("no_telp")
    @Expose
    private String noTelp;
    @SerializedName("alamat")
    @Expose
    private String alamat;

    public Integer getIdHistory() {
        return idHistory;
    }

    public void setIdHistory(Integer idHistory) {
        this.idHistory = idHistory;
    }

    public Integer getLelangId() {
        return lelangId;
    }

    public void setLelangId(Integer lelangId) {
        this.lelangId = lelangId;
    }

    public Integer getBarangId() {
        return barangId;
    }

    public void setBarangId(Integer barangId) {
        this.barangId = barangId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getPenawaranHarga() {
        return penawaranHarga;
    }

    public void setPenawaranHarga(Integer penawaranHarga) {
        this.penawaranHarga = penawaranHarga;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Integer getIdUser() {
        return idUser;
    }

    public void setIdUser(Integer idUser) {
        this.idUser = idUser;
    }

    public Integer getIdMasyarakat() {
        return idMasyarakat;
    }

    public void setIdMasyarakat(Integer idMasyarakat) {
        this.idMasyarakat = idMasyarakat;
    }

    public String getNamaLengkap() {
        return namaLengkap;
    }

    public void setNamaLengkap(String namaLengkap) {
        this.namaLengkap = namaLengkap;
    }

    public String getNoTelp() {
        return noTelp;
    }

    public void setNoTelp(String noTelp) {
        this.noTelp = noTelp;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

}

