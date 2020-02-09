package com.mycompany.nnarm_bci;

/**
 *
 * @author Liliana L Beristain
 */
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Servidor extends Conexion //Se hereda de conexión para hacer uso de los sockets y demás
{
    private BufferedReader entrada = null;
    private DataOutputStream salida = null;
    public int numPesos;
    public Servidor(int numPesos) throws IOException{
        super("servidor");
        this.numPesos = numPesos;
    } //Se usa el constructor para servidor de Conexion 

    void iniciarServer() {
        try{
            System.out.println("Esperando..."); //Esperando conexión
            cs = ss.accept(); //Accept comienza el socket y espera una conexión desde un cliente
            System.out.println("Cliente en línea");
            entrada = new BufferedReader(new InputStreamReader(cs.getInputStream()));
            salida = new DataOutputStream(cs.getOutputStream());
            salida.flush(); //Limpia la salida para evitar enviar basura
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
    }   

    double[] getPesos() {
        double[] w = new double[numPesos];
        String[] s;
        try{
            mensajeServidor = entrada.readLine();
            if(mensajeServidor != null){
              //  System.out.println(mensajeServidor);
                s =  mensajeServidor.split(" ");
                //Transformamos la cadena en valores
                for(int j = 1; j < s.length; j++)
                {            
                    //System.out.println("Valor " + j + ": "+s[j] + ", "+ (j-1));
                    w[j-1]= Double.parseDouble(s[j]);                
                }   
            }
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        return w;
    }

    void sendEval(double[] fx) {
        String msg;
        try{
            //Se le envía un mensaje al cliente usando su flujo de salida
            //Se generan los valores de evaluación
            //System.out.println("Enviando...");
            msg = (String.valueOf(fx[0])) + " " + (String.valueOf(fx[1]));
            salida.writeUTF(msg);
            salida.flush();
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
    }

    void finishConn() {        
        try {
            System.out.println("Fin de la conexión");
            ss.close();//Se finaliza la conexión con el cliente}        
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }    
}
