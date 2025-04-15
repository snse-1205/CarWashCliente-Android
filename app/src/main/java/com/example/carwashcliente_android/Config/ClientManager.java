package com.example.carwashcliente_android.Config;
import android.content.Context;
import android.content.SharedPreferences;

public class ClientManager {

    private static final String PREF_NAME = "ClientPreferences";
    private static final String CLIENT_ID_KEY = "ClientId";
    private static final String CLIENT_NAME_KEY = "ClientName";
    private static final String CLIENT_TOKEN_KEY = "ClientToken";

    private final SharedPreferences sharedPreferences;
    private final SharedPreferences.Editor editor;

    public ClientManager(Context context) {
        sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public void saveClientData(int id, String name, String token) {
        editor.putInt(CLIENT_ID_KEY, id);
        editor.putString(CLIENT_NAME_KEY, name);
        editor.putString(CLIENT_TOKEN_KEY, token);
        editor.apply();
    }

    public String getClientId() {
        return sharedPreferences.getString(CLIENT_ID_KEY, null);
    }

    public String getClientName() {
        return sharedPreferences.getString(CLIENT_NAME_KEY, null);
    }

    public String getClientToken() {
        return sharedPreferences.getString(CLIENT_TOKEN_KEY, null);
    }

    public void clearClientData() {
        editor.remove(CLIENT_ID_KEY);
        editor.remove(CLIENT_NAME_KEY);
        editor.remove(CLIENT_TOKEN_KEY);
        editor.apply();
    }
}

