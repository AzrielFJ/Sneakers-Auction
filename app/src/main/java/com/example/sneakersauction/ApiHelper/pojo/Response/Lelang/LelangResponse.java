package com.example.sneakersauction.ApiHelper.pojo.Response.Lelang;
import java.util.List;

import com.example.sneakersauction.ApiHelper.pojo.Model.Lelang.Lelang;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LelangResponse {

    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("data")
    @Expose
    private List<Lelang> data = null;

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public List<Lelang> getData() {
        return data;
    }

    public void setData(List<Lelang> data) {
        this.data = data;
    }

}