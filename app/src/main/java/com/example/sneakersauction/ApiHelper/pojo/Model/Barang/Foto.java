package com.example.sneakersauction.ApiHelper.pojo.Model.Barang;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Foto {
    @SerializedName("foto")
    @Expose
    private String foto;

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

}
