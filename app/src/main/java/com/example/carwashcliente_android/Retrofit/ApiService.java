package com.example.carwashcliente_android.Retrofit;

import android.util.Log;

import com.example.carwashcliente_android.Models.Cotizacion;
import com.example.carwashcliente_android.Models.UbicacionModel;
import com.example.carwashcliente_android.Models.UsuarioModel;
import com.example.carwashcliente_android.Models.VehiculoModel;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Path;

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
            @Part MultipartBody.Part archivo
    );

    @Multipart
    @POST("cliente")
    Call<Void> registroCliente(
            @PartMap Map<String, RequestBody> campos
    );

    @POST("cliente/verificar")
    Call<UsuarioModel> verificar(@Body HashMap<String, String> body);

    @POST("cliente/ubicacion/")
    Call<Void> registroUbicacion(
            @Header("Authorization") String token,
            @Body HashMap<String, String> body
    );

    @GET("cliente/ubicacion/")
    Call<List<UbicacionModel>> listaUbicaciones(
            @Header("Authorization") String token
    );

    @GET("carros/idcliente")
    Call<List<VehiculoModel>> listaVehiculo(@Header("Authorization") String token);

    @POST("carros")
    Call<Void> agregarVehiculo(@Header("Authorization") String token, HashMap<String,String> body);
    @GET("carros/mym")
    Call<List<VehiculoModel.Marca>> listarMarcaModelo();

    @PUT("carros/{id}")
    Call<VehiculoModel> actualizarCarro(@Header("Authorization") String token,
                                        HashMap<String,String> body,
                                        @Path("id") int id);

    @PUT("carros/eliminar/{id}")
    Call<Void> eliminarCarros(@Header("Authorization") String token,
                              @Path("id") int id);

    @Multipart
    @PUT("cliente/")
    Call<UsuarioModel> actualizarCliente(@Header("Authorization") String token,
                                         @PartMap Map<String, RequestBody> campos);

    @Multipart
    @PUT("cliente/")
    Call<UsuarioModel> actualizarCliente(@Header("Authorization") String token,
                                         @PartMap Map<String, RequestBody> campos,
                                         @Part MultipartBody.Part archivo);
    @GET("cliente/:id")
    Call<UsuarioModel> usuarioPorId(@Header("Authorization") String token);

    @PUT("cliente/eliminar")
    Call<Void> eliminarCliente(@Header("Authorization") String token);

    @PUT("cliente/ubicacion/{id}")
    Call<UbicacionModel> eliminarUbicacion(@Header("Authorization") String token, @Path("id") int id);

    @GET("cotizaciones/pendientes")
    Call<List<Cotizacion>> aceptarORechazarCotizacion(@Header("Authorization") String token);

    @PUT("cotizaciones/pendientes/{id}")
    Call<List<Cotizacion>> accionEnCotizacion(@Header("Authorization") String token,
                                              HashMap<String,String> body,
                                              @Path("id") int id);

    @GET("cotizaciones/historial")
    Call<List<Cotizacion>> cotizacionesPasadas(@Header("Authorization") String token);

}
