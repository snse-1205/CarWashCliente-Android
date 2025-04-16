package com.example.carwashcliente_android.Activities;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import static androidx.core.content.ContextCompat.startActivity;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;

import com.example.carwashcliente_android.Config.ClientManager;
import com.example.carwashcliente_android.Config.SocketManager;
import com.example.carwashcliente_android.Models.UsuarioModel;
import com.example.carwashcliente_android.R;
import com.example.carwashcliente_android.Retrofit.ApiService;
import com.example.carwashcliente_android.Retrofit.RetrofitClient;

import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity  extends AppCompatActivity {

    private EditText etNombre,etEmail, etPhone, etPassword, etConfirmPassword;
    private static final int PICK_IMAGE_REQUEST = 1;
    private Uri imagenUri;
    private String archivoPath=null;
    private UsuarioModel usuario;
    private List<UsuarioModel> contenidoModificar;
    private EditText etCode;
    private final ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);
    private Button btnRegister, btnEmailVerify, btnGaleria, btnTomarFoto;
    private ClientManager clientManager;
    private String nombre, email, phone, password, confirmPassword;
    private ImageView imageView;
    private int accion=0;
    private static int id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //super.clone(savedInstanceState);
        setContentView(R.layout.activity_register);
        clientManager = new ClientManager(this);
        usuario = new UsuarioModel();

        etNombre = findViewById(R.id.etNombre);
        etEmail = findViewById(R.id.etEmail);
        etPhone = findViewById(R.id.etPhone);
        etPassword = findViewById(R.id.etPassword);
        etConfirmPassword = findViewById(R.id.etConfirmPassword);
        btnRegister = findViewById(R.id.btnRegister);
        btnEmailVerify = findViewById(R.id.btnEmailVerificar);
        etCode = findViewById(R.id.etCode);
        imageView = findViewById(R.id.imageView);
        btnTomarFoto = findViewById(R.id.btnTomarFoto);
        btnGaleria = findViewById(R.id.btnGaleria);
        LinearLayout layoutVerification = findViewById(R.id.layoutVerification);

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("accion") && intent.hasExtra("id")) {
            accion = intent.getIntExtra("accion",0);
            if(accion==2){
                Log.e("Pantalla Actualizar", "Estamos en Actualizar");
                llenarParaModificar();
                etPassword.setVisibility(GONE);
                etConfirmPassword.setVisibility(GONE);
                btnRegister.setVisibility(VISIBLE);
            }else{
                etPassword.setVisibility(VISIBLE);
                etConfirmPassword.setVisibility(VISIBLE);
            }
            id = intent.getIntExtra("id",-1);
        } else {
            Log.e("Pantalla Actualizar", "No se recibio el entero");
        }

        btnGaleria.setOnClickListener(v -> AbrirGaleria());

        btnTomarFoto.setOnClickListener(v -> abrirCamara());

        btnEmailVerify.setOnClickListener(v -> {
            email = etEmail.getText().toString();
            if(email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                etEmail.setError("Email inválido");
                return;
            }

            verificarCorreo();
        });


        btnRegister.setOnClickListener(v -> {
            nombre = etNombre.getText().toString();
            email = etEmail.getText().toString();
            phone = etPhone.getText().toString();
            password = etPassword.getText().toString();
            confirmPassword = etConfirmPassword.getText().toString();

            if(imagenUri == null){
                archivoPath = null;
            }else{
            archivoPath = getRealPathFromURI(imagenUri);}

            if(validateRegistration(nombre,email, phone, password, confirmPassword) && accion!=2) {
                String codigoIngresado = etCode.getText().toString();

                if (!codigoIngresado.isEmpty()) {
                    if (codigoIngresado.equals(String.valueOf(usuario.getCodigo()))) {
                        Toast.makeText(getApplicationContext(), "✅ Código correcto", Toast.LENGTH_LONG).show();
                        registroCliente();
                    } else {
                        Toast.makeText(getApplicationContext(), "❌ Código incorrecto", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "⚠️ Ingresa un codigo", Toast.LENGTH_LONG).show();
                }
            } else if (validateUpdate(nombre,email, phone) && accion!=2) {
                actualizarCliente();
            }
        });
    }

    private String getRealPathFromURI(Uri contentUri) {
        String result = null;
        String[] proj = {MediaStore.Video.Media.DATA};
        Cursor cursor = getContentResolver().query(contentUri, proj, null, null, null);
        if (cursor != null) {
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DATA);
            cursor.moveToFirst();
            result = cursor.getString(column_index);
            cursor.close();
        }
        return result;
    }

    private boolean validateUpdate(String nombre, String email, String phone) {
        if(nombre.isEmpty()){
            etNombre.setError("Nombre inválido");
            return false;
        }
        if(email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            etEmail.setError("Email inválido");
            return false;
        }
        if(phone.isEmpty() || phone.length() < 9) {
            etPhone.setError("Teléfono inválido");
            return false;
        }
        return true;
    }

    private boolean validateRegistration(String nombre, String email, String phone,
                                         String password, String confirmPassword) {
        if(nombre.isEmpty()){
            etNombre.setError("Nombre inválido");
            return false;
        }
        if(email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            etEmail.setError("Email inválido");
            return false;
        }
        if(phone.isEmpty() || phone.length() < 9) {
            etPhone.setError("Teléfono inválido");
            return false;
        }
        if(password.isEmpty() || password.length() < 6) {
            etPassword.setError("Mínimo 6 caracteres");
            return false;
        }
        if(!password.equals(confirmPassword)) {
            etConfirmPassword.setError("Las contraseñas no coinciden");
            return false;
        }
        return true;
    }

    private void verificarCorreo() {
        HashMap<String, String> body = new HashMap<>();
        body.put("correo", email);

        Call<UsuarioModel> call = apiService.verificar(body);
        call.enqueue(new Callback<UsuarioModel>() {
            @Override
            public void onResponse(Call<UsuarioModel> call, Response<UsuarioModel> response) {
                LinearLayout layoutVerification = findViewById(R.id.layoutVerification);

                if (response.isSuccessful()) {
                    usuario = response.body();
                    if (usuario != null) {
                        Integer codigo = usuario.getCodigo();
                        Toast.makeText(getApplicationContext(), "✅ Código enviado a tu correo", Toast.LENGTH_SHORT).show();
                        layoutVerification.setVisibility(VISIBLE);
                        Log.d("Respuesta", "Código recibido: " + codigo);
                    }
                } else {
                    try {
                        String errorBody = response.errorBody().string();

                        JSONObject jsonObject = new JSONObject(errorBody);
                        String mensaje = jsonObject.getString("mensaje");

                        Toast.makeText(getApplicationContext(), "⚠️ " + mensaje, Toast.LENGTH_LONG).show();
                        layoutVerification.setVisibility(GONE);

                        Log.e("Retrofit", "Mensaje recibido: " + mensaje);
                    } catch (Exception e) {
                        Log.e("Retrofit", "Error al parsear errorBody: " + e.getMessage());
                    }
                }
            }

            @Override
            public void onFailure(Call<UsuarioModel> call, Throwable t) {
                Log.e("Retrofit", "Fallo en la solicitud: " + t.getMessage());
            }
        });
    }




    private void registroCliente(){
        File videoFile;
        HashMap<String,RequestBody> body = new HashMap<>();
        body.put("nombre", RequestBody.create(MediaType.parse("text/plain"), nombre));
        body.put("correo", RequestBody.create(MediaType.parse("text/plain"), email));
        body.put("telefono", RequestBody.create(MediaType.parse("text/plain"), phone));
        body.put("contrasena", RequestBody.create(MediaType.parse("text/plain"), password));
        if(archivoPath == null){
            videoFile = null;

            Call<Void> call = apiService.registroCliente(body);
            call.enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    if (response.isSuccessful()) {
                        Toast.makeText(getApplicationContext(), "🎉 Registro exitoso", Toast.LENGTH_SHORT).show();
                        Log.d("Retrofit", "Funciono: " + response.body());
                    }else{
                        Log.e("Retrofit", "Error: " + response.message() + ", Código: " + response.code());

                        try {
                            String errorResponse = response.errorBody().string(); // Obtener el cuerpo del error
                            Log.e("Retrofit", "Detalles del error: " + errorResponse);
                        } catch (Exception e) {
                            Log.e("Retrofit", "Error al obtener el cuerpo del error: " + e.getMessage());
                        }
                    }
                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {
                    Log.e("Retrofit", "Fallo en la solicitud: " + t.getMessage());
                }
            });
        }else{
            videoFile = new File(archivoPath);
            RequestBody requestFile = RequestBody.create(MediaType.parse("video/*"), videoFile);
            MultipartBody.Part videoPart = MultipartBody.Part.createFormData("video", videoFile.getName(), requestFile);

            Call<Void> call = apiService.registroCliente(body, videoPart);
            call.enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    if (response.isSuccessful()) {
                        Toast.makeText(getApplicationContext(), "🎉 Registro exitoso", Toast.LENGTH_SHORT).show();
                        Log.d("Retrofit", "Funciono: " + response.body());
                    }else{
                        Log.e("Retrofit", "Error: " + response.message() + ", Código: " + response.code());

                        try {
                            String errorResponse = response.errorBody().string(); // Obtener el cuerpo del error
                            Log.e("Retrofit", "Detalles del error: " + errorResponse);
                        } catch (Exception e) {
                            Log.e("Retrofit", "Error al obtener el cuerpo del error: " + e.getMessage());
                        }
                    }
                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {
                    Log.e("Retrofit", "Fallo en la solicitud: " + t.getMessage());
                }
            });
        }

    }

    private void llenarCamposParaModificar(List<UsuarioModel> usuario){
        int position = 0;
        UsuarioModel contenido = usuario.get(position);

        etNombre.setText(contenido.getUsername());
        etEmail.setText(contenido.getUsername());
        etPhone.setText(contenido.getUsername());
        String path = "https://hmqphld5-3000.use2.devtunnels.ms/api/videos/" ;
        Uri imagenUri = Uri.parse(path);
        imageView.setImageURI(imagenUri);
    }

    private void llenarParaModificar(){
        String token= "Bearer " + clientManager.getClientToken();

        Call<UsuarioModel> call = apiService.usuarioPorId(token);
        call.enqueue(new Callback<UsuarioModel>() {
            @Override
            public void onResponse(Call<UsuarioModel> call, Response<UsuarioModel> response) {
                if(response.isSuccessful() && response.body() != null){
                    contenidoModificar.clear();
                    contenidoModificar.add(response.body()); // 👈 Agrega el único objeto
                    llenarCamposParaModificar(contenidoModificar);
                    Log.e("Retrofit", "Llamado exitoso");
                }else{
                    Log.e("Retrofit", "Error: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<UsuarioModel> call, Throwable t) {
                Log.e("Retrofit", "Fallo en la solicitud: " + t.getMessage());
            }
        });

    }

    private void actualizarCliente(){
        String token= "Bearer " + clientManager.getClientToken();
        File videoFile;
        HashMap<String,RequestBody> body = new HashMap<>();
        body.put("nombre", RequestBody.create(MediaType.parse("text/plain"), nombre));
        body.put("correo", RequestBody.create(MediaType.parse("text/plain"), email));
        body.put("telefono", RequestBody.create(MediaType.parse("text/plain"), phone));
        if(archivoPath == null){
            videoFile = null;

            Call<UsuarioModel> call = apiService.actualizarCliente(token,body);
            call.enqueue(new Callback<UsuarioModel>() {
                @Override
                public void onResponse(Call<UsuarioModel> call, Response<UsuarioModel> response) {
                    if (response.isSuccessful()) {
                        Toast.makeText(getApplicationContext(), "🎉 Registro exitoso", Toast.LENGTH_SHORT).show();
                        Log.d("Retrofit", "Funciono: " + response.body());
                    }else{
                        Log.e("Retrofit", "Error: " + response.message() + ", Código: " + response.code());

                        try {
                            String errorResponse = response.errorBody().string(); // Obtener el cuerpo del error
                            Log.e("Retrofit", "Detalles del error: " + errorResponse);
                        } catch (Exception e) {
                            Log.e("Retrofit", "Error al obtener el cuerpo del error: " + e.getMessage());
                        }
                    }
                }

                @Override
                public void onFailure(Call<UsuarioModel> call, Throwable t) {
                    Log.e("Retrofit", "Fallo en la solicitud: " + t.getMessage());
                }
            });
        }else{
            videoFile = new File(archivoPath);
            RequestBody requestFile = RequestBody.create(MediaType.parse("video/*"), videoFile);
            MultipartBody.Part videoPart = MultipartBody.Part.createFormData("video", videoFile.getName(), requestFile);

            Call<UsuarioModel> call = apiService.actualizarCliente(token,body, videoPart);
            call.enqueue(new Callback<UsuarioModel>() {
                @Override
                public void onResponse(Call<UsuarioModel> call, Response<UsuarioModel> response) {
                    if (response.isSuccessful()) {
                        Toast.makeText(getApplicationContext(), "🎉 Registro exitoso", Toast.LENGTH_SHORT).show();
                        Log.d("Retrofit", "Funciono: " + response.body());
                    }else{
                        Log.e("Retrofit", "Error: " + response.message() + ", Código: " + response.code());

                        try {
                            String errorResponse = response.errorBody().string(); // Obtener el cuerpo del error
                            Log.e("Retrofit", "Detalles del error: " + errorResponse);
                        } catch (Exception e) {
                            Log.e("Retrofit", "Error al obtener el cuerpo del error: " + e.getMessage());
                        }
                    }
                }

                @Override
                public void onFailure(Call<UsuarioModel> call, Throwable t) {
                    Log.e("Retrofit", "Fallo en la solicitud: " + t.getMessage());
                }
            });
        }

    }

    private void AbrirGaleria() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");

        galeriaARL.launch(intent);
    }

    private File crearArchivoImagen() throws IOException {
        String nombreArchivo = "foto_" + System.currentTimeMillis();
        File directorio = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File imagen = File.createTempFile(nombreArchivo, ".jpg", directorio);
        return imagen;
    }


    private void abrirCamara() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (intent.resolveActivity(getPackageManager()) != null) {
            try {
                File fotoArchivo = crearArchivoImagen();
                imagenUri = FileProvider.getUriForFile(
                        this,
                        getPackageName() + ".provider",
                        fotoArchivo
                );
                intent.putExtra(MediaStore.EXTRA_OUTPUT, imagenUri);
                startActivityForResult(intent, 1);
            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(this, "Error al crear archivo de imagen", Toast.LENGTH_SHORT).show();
            }
        }
    }


    private ActivityResultLauncher<Intent> galeriaARL = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                    imagenUri = result.getData().getData(); // Obtener la URI de la imagen seleccionada
                    if (imagenUri != null) {
                        imageView.setImageURI(imagenUri);
                    } else {
                        Toast.makeText(RegisterActivity.this, "Sin seleccionar", Toast.LENGTH_SHORT).show();
                    }
                }
            }
    );

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK) {
            if (imagenUri != null) {
                imageView.setImageURI(imagenUri); // 👈 Mostrar la imagen desde la URI
            } else {
                Toast.makeText(this, "No se obtuvo imagen", Toast.LENGTH_SHORT).show();
            }
        }
    }


}
