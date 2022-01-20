package com.example.sneakersauction.ApiHelper;


import com.example.sneakersauction.ApiHelper.pojo.Response.Administrator.ListPetugasResponse;
import com.example.sneakersauction.ApiHelper.pojo.Response.Barang.BarangResponse;
import com.example.sneakersauction.ApiHelper.pojo.Response.Barang.FotoResponse;
import com.example.sneakersauction.ApiHelper.pojo.Response.Lelang.LelangResponse;
import com.example.sneakersauction.ApiHelper.pojo.Response.Lelang.PenawarResponse;

import java.io.File;
import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface ApiService {
//Login Register
@FormUrlEncoded
@POST("login")
Call<ResponseBody> logIn(@Field("username") String username,
                                @Field("password") String password);
@FormUrlEncoded
@POST("masyarakat/register")
Call<ResponseBody> register(@Field("nama_lengkap") String nama_lengkap,
                                    @Field("no_telp") String no_telp,
                                    @Field("alamat") String alamat,
                                    @Field("username") String username,
                                    @Field("password") String password);

//Admin/Petugas
@GET("petugas/getPetugas/{id_petugas}")
Call<ListPetugasResponse> getPetugas(@Path("id_petugas") int id_petugas);

@GET("petugas/deletePetugas/{id_petugas}")
Call<ResponseBody> deletPetugas(@Path("id_petugas") int id_petugas);

@FormUrlEncoded
@POST("petugas/register/")
Call<ListPetugasResponse> registerPetugas(@Field("nama_petugas") String nama_petugas,
                                @Field("username") String username,
                                @Field("password") String password,
                                @Field("role_id") int role_id

);

//Barang
@FormUrlEncoded
@POST("petugas/addBarang")
Call<ResponseBody> addBarang(@Field("nama_barang") String nama_barang,
                             @Field("tanggal") String tanggal,
                             @Field("deskripsi") String deskripsi,
                             @Field("foto") String foto,
                             @Field("harga_awal") int harga_awal);
@FormUrlEncoded
@POST("barang/editBarang/{id_barang}")
Call<ResponseBody> editBarang(@Path("id_barang") int id_barang,
                                          @Field("nama_barang") String nama_barang,
                                          @Field("deskripsi_barang") String deskripsi_barang,
                                          @Field("harga_awal") int harga_awal);

@GET("barang/listbarang/{status}")
Call<BarangResponse> listBarang(@Path("status") String status);

@GET("barang/deleteBarang/{id_barang}")
Call<ResponseBody> deleteBarang(@Path("id_barang") int id_barang);
@GET("barang/getFoto/{id_barang}")
Call<FotoResponse> getFoto(@Path("id_barang") int id_barang);

@Multipart
@POST("petugas/addFotoBarang")
Call<RequestBody> uploadImage(@Part MultipartBody.Part part);
@Multipart
@POST("petugas/addFotoBarang2/{id_barang}")
Call<ResponseBody> uploadMultiple(
            @Path("id_barang") int id_barang,
            @Part MultipartBody.Part files);
//Masyarakat
@FormUrlEncoded
@POST("masyarakat/editProfile/{id_masyarakat}/{id_user}")

Call<ListPetugasResponse> editProfile(@Path("id_masyarakat") int id_masyarakat,
                                      @Path("id_user") int id_user,
                                      @Field("nama_lengkap") String nama_lengkap,
                                      @Field("alamat") String alamat,
                                      @Field("no_telp") String no_telp,
                                      @Field("username") String username
);
    @FormUrlEncoded
    @POST("masyarakat/editPassword/{id_user}")

    Call<ListPetugasResponse> editPassword(@Path("id_user") int id_user,
                                          @Field("password") String password,
                                           @Field("passwordnew") String passwordnew
    );
    @FormUrlEncoded
    @POST("masyarakat/editPassword2/{username}")

    Call<ListPetugasResponse> editPassword2(@Path("username") String username,
                                           @Field("password") String password
    );
//Lelang
@FormUrlEncoded
@POST("lelang/bukaLelang/{id_petugas}/{id_barang}")
Call<ResponseBody> bukaLelang(@Path("id_petugas") int id_petugas,
                             @Path("id_barang") int id_barang,
                             @Field("tanggal_dibuka") String tanngal_dibuka,
                             @Field("tanggal_ditutup") String tanngal_ditutup);
    @FormUrlEncoded
    @POST("lelang/bidLelang/{id_lelang}/{id_barang}/{id_user}")
    Call<ResponseBody> bidLelang(@Path("id_lelang") int id_lelang,
                                  @Path("id_barang") int id_barang,
                                  @Path("id_user")int id_user,
                                  @Field("harga") int harga);
@FormUrlEncoded
@POST("lelang/listLelang/{status}")
Call<LelangResponse> listLelang(@Path("status") String status,
                                @Field("id_role") int id_role,
                                @Field("id_user") int id_user
);

@GET("lelang/penawarTertinggi/{id_lelang}")
Call<ResponseBody> penawarTertinggi(@Path("id_lelang") int id_lelang);

@GET("lelang/penawar/{id_lelang}")
Call<PenawarResponse> penawar(@Path("id_lelang") int id_lelang);

@GET("lelang/tutupLelang/{id_lelang}")
Call<PenawarResponse> tutupLelang(@Path("id_lelang") int id_lelang);
    @GET("send-sms/{code}")
    Call<PenawarResponse> resetpassword(@Path("code") String code);
}





