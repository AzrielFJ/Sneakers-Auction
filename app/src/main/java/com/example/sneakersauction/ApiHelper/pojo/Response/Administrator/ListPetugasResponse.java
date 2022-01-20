package com.example.sneakersauction.ApiHelper.pojo.Response.Administrator;
import java.util.List;

import com.example.sneakersauction.ApiHelper.pojo.Model.Administrator.ListPetugas;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ListPetugasResponse {

    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("data")
    @Expose
    private List<ListPetugas> data = null;

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public List<ListPetugas> getData() {
        return data;
    }

    public void setData(List<ListPetugas> data) {
        this.data = data;
    }

}