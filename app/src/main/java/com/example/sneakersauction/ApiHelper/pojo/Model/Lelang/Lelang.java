package com.example.sneakersauction.ApiHelper.pojo.Model.Lelang;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Lelang {

    @SerializedName("id_lelang")
    @Expose
    private Integer idLelang;
    @SerializedName("tanggal_dibuka")
    @Expose
    private String tanggalDibuka;
    @SerializedName("tanggal_ditutup")
    @Expose
    private String tanggalDitutup;
    @SerializedName("user_id")
    @Expose
    private Integer userId;
    @SerializedName("harga_akhir")
    @Expose
    private Integer hargaAkhir;
    @SerializedName("id_barang")
    @Expose
    private Integer idBarang;
    @SerializedName("nama_barang")
    @Expose
    private String namaBarang;
    @SerializedName("tanggal")
    @Expose
    private String tanggal;
    @SerializedName("harga_awal")
    @Expose
    private Integer hargaAwal;
    @SerializedName("deskripsi_barang")
    @Expose
    private String deskripsiBarang;
    @SerializedName("foto")
    @Expose
    private String foto;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("id_petugas")
    @Expose
    private Integer idPetugas;
    @SerializedName("nama_petugas")
    @Expose
    private String namaPetugas;
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

    public Integer getIdLelang() {
        return idLelang;
    }

    public void setIdLelang(Integer idLelang) {
        this.idLelang = idLelang;
    }

    public String getTanggalDibuka() {
        return tanggalDibuka;
    }

    public void setTanggalDibuka(String tanggalDibuka) {
        this.tanggalDibuka = tanggalDibuka;
    }

    public String getTanggalDitutup() {
        return tanggalDitutup;
    }

    public void setTanggalDitutup(String tanggalDitutup) {
        this.tanggalDitutup = tanggalDitutup;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getHargaAkhir() {
        return hargaAkhir;
    }

    public void setHargaAkhir(Integer hargaAkhir) {
        this.hargaAkhir = hargaAkhir;
    }

    public Integer getIdBarang() {
        return idBarang;
    }

    public void setIdBarang(Integer idBarang) {
        this.idBarang = idBarang;
    }

    public String getNamaBarang() {
        return namaBarang;
    }

    public void setNamaBarang(String namaBarang) {
        this.namaBarang = namaBarang;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

    public Integer getHargaAwal() {
        return hargaAwal;
    }

    public void setHargaAwal(Integer hargaAwal) {
        this.hargaAwal = hargaAwal;
    }

    public String getDeskripsiBarang() {
        return deskripsiBarang;
    }

    public void setDeskripsiBarang(String deskripsiBarang) {
        this.deskripsiBarang = deskripsiBarang;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public Integer getIdPetugas() {
        return idPetugas;
    }

    public void setIdPetugas(Integer idPetugas) {
        this.idPetugas = idPetugas;
    }

    public String getNamaPetugas() {
        return namaPetugas;
    }

    public void setNamaPetugas(String namaPetugas) {
        this.namaPetugas = namaPetugas;
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