package com.example.sneakersauction.ApiHelper.pojo.Response.Barang;

import com.example.sneakersauction.ApiHelper.pojo.Model.Barang.Barang;
import com.example.sneakersauction.ApiHelper.pojo.Model.Barang.Foto;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class FotoResponse {
    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("data")
    @Expose
    private List<Foto> data = null;

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public List<Foto> getData() {
        return data;
    }

    public void setData(List<Foto> data) {
        this.data = data;
    }


}
