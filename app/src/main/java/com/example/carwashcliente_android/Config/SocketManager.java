package com.example.carwashcliente_android.Config;

import android.util.Log;

import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

public class SocketManager {
    private static Socket mSocket;
    private static final Map<String, Emitter.Listener> registeredListeners = new HashMap<>();

    public static void initSocket() {
        if (mSocket == null) {
            try {
                mSocket = IO.socket("https://hmqphld5-3000.use2.devtunnels.ms");
            } catch (URISyntaxException e) {
                Log.e("SOCKET", "Error al conectar: " + e.getMessage());
            }
        }
    }

    public static Socket getSocket() {
        return mSocket;
    }

    public static void connect() {
        if (mSocket != null && !mSocket.connected()) {
            mSocket.on(Socket.EVENT_CONNECT, args ->
                    Log.d("SOCKET", "Conectado al servidor"));

            mSocket.on(Socket.EVENT_CONNECT_ERROR, args ->
                    Log.e("SOCKET", "Error de conexión: " + args[0]));

            mSocket.on(Socket.EVENT_DISCONNECT, args ->
                    Log.d("SOCKET", "Desconectado del servidor"));

            mSocket.on("reconnect", args -> {
                Log.d("SOCKET", "Reconectado, re-registrando listeners...");
                for (Map.Entry<String, Emitter.Listener> entry : registeredListeners.entrySet()) {
                    mSocket.on(entry.getKey(), entry.getValue());
                }
            });

            mSocket.connect();
        }
    }

    public static void disconnect() {
        if (mSocket != null && mSocket.connected()) {
            mSocket.disconnect();
        }
    }

    public static void escucharEvento(String evento, Emitter.Listener listener) {
        if (mSocket != null) {
            Log.d("SOCKET", "Conectado al evento "+evento);
            registeredListeners.put(evento, listener);
            mSocket.on(evento, listener);
        }
    }

    public static void dejarDeEscucharEvento(String evento) {
        if (mSocket != null) {
            Emitter.Listener listener = registeredListeners.get(evento);
            if (listener != null) {
                Log.d("SOCKET", "Desconectando al evento "+evento);
                mSocket.off(evento, listener);
                registeredListeners.remove(evento);
            }
        }
    }

    public static void enviarMensaje(String mensaje) {
        if (mSocket != null) {
            mSocket.emit("mensaje", mensaje);
        }
    }

    public static void notifyUserConnected(String userId) {
        if (mSocket != null && mSocket.connected()) {
            mSocket.emit("user_connected", userId);
        }
    }
}
