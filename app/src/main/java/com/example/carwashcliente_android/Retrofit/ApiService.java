package com.example.carwashcliente_android.Retrofit;

import com.example.carwashcliente_android.Models.UsuarioModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;

public interface ApiService {

    @POST("auth/cliente")
    Call<UsuarioModel> login(
            @Body HashMap<String,String> body
    );
    @GET("auth/verificar")
    Call<Void> verifiarSesion(  @Header("Authorization") String token);

    @Multipart
    @POST("cliente")
    Call<Void> registroCliente(
            @PartMap Map<String, RequestBody> campos,
            @Part MultipartBody.Part archivo // Aquí va el archivo (por ejemplo, imagen)
    );

    @Multipart
    @POST("cliente")
    Call<Void> registroCliente(
            @PartMap Map<String, RequestBody> campos
    );

    @POST("cliente/verificar")
    Call<UsuarioModel> verificar(@Body HashMap<String, String> body);

}
