package com.mycompany.nnarm_bci;

/**
 *
 * @author Liliana L Beristain
 */
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Conexion
{
    private final int PUERTO = 7778; //Puerto para la conexión
    private final String HOST = "127.0.0.1"; //Host para la conexión
    protected static String mensajeServidor; //Mensajes entrantes (recibidos) en el servidor
    protected static ServerSocket ss; //Socket del servidor
    protected static Socket cs; //Socket del cliente
    protected DataOutputStream salidaServidor, salidaCliente; //Flujo de datos de salida

    public Conexion(String tipo) throws IOException //Constructor
    {
        if(tipo.equalsIgnoreCase("servidor"))
        {
            ss = new ServerSocket(PUERTO);//Se crea el socket para el servidor en puerto 1234
            cs = new Socket(); //Socket para el cliente
        }
        else
        {
            cs = new Socket(HOST, PUERTO); //Socket para el cliente en localhost en puerto 1234
        }
    }
}
