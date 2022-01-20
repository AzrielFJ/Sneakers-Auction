package com.example.sneakersauction.ApiHelper.pojo.Response.Barang;

import com.example.sneakersauction.ApiHelper.pojo.Model.Barang.Barang;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BarangResponse {
    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("data")
    @Expose
    private List<Barang> data = null;

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public List<Barang> getData() {
        return data;
    }

    public void setData(List<Barang> data) {
        this.data = data;
    }


}
