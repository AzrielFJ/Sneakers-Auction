package com.example.sneakersauction.ApiHelper.pojo.Model.Administrator;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ListPetugas {
    @SerializedName("id_petugas")
    @Expose
    private Integer idPetugas;
    @SerializedName("user_id")
    @Expose
    private Integer userId;
    @SerializedName("nama_petugas")
    @Expose
    private String namaPetugas;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("role_id")
    @Expose
    private Integer roleId;
    @SerializedName("role")
    @Expose
    private String role;

    public Integer getIdPetugas() {
        return idPetugas;
    }

    public void setIdPetugas(Integer idPetugas) {
        this.idPetugas = idPetugas;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getNamaPetugas() {
        return namaPetugas;
    }

    public void setNamaPetugas(String namaPetugas) {
        this.namaPetugas = namaPetugas;
    }
    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
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

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

}
