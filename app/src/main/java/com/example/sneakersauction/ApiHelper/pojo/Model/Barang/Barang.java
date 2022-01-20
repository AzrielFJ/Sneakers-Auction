package com.example.sneakersauction.ApiHelper.pojo.Model.Barang;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
public class Barang {
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
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;

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

}
