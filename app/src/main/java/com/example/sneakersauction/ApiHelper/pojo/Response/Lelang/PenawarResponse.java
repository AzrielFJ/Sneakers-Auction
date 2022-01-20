package com.example.sneakersauction.ApiHelper.pojo.Response.Lelang;

import com.example.sneakersauction.ApiHelper.pojo.Model.Lelang.Penawar;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PenawarResponse {
    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("data")
    @Expose
    private List<Penawar> data = null;

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public List<Penawar> getData() {
        return data;
    }

    public void setData(List<Penawar> data) {
        this.data = data;
    }
}
