package com.mycompany.nnarm_bci;

import java.io.IOException;
import java.util.Random;

/**
 *
 * @author Liliana L Beristain
 */
public class Main {
    
    public static void main(String[] args) throws IOException {
        
        int poblacion = 4, generacion = 10000, i = 0, j = 0, lim = 3;
        int numPesos = 48;
        double[] objCoord = new double[3];
        double[] pesos = new double[numPesos];
        double[] angles = new double[5];
        double[] fx = new double[2];
        Random rd = new Random(); // creating Random object   
        Servidor server = new Servidor(numPesos);
        RedNeuronal ann = new RedNeuronal();
        System.out.println("Iniciando servidor\n");
        server.iniciarServer();
        iniciarSimulador();
        //Iniciar while
        while(i < generacion*poblacion)
            {
                //obtiene los datos
                objCoord = getSimData();
                //recibe los pesos del Optimizador
                pesos = server.getPesos();
                //actualiza los datos de la red
                ann.setNN(objCoord, pesos);
                //SEGUNDO WHILE Inicia la prueba de los pesos 
                while(j < lim)
                {
                    //HASTA AQUÍ 
                    //Calcula nn y obtiene ángulos
                    angles = ann.getAngles();
                    //Envía resultados al simulador y obtiene los valores de coord de muñón
                    fx = evaluarSim(angles);
                    
                    for(int k = 0; k < angles.length; k++)
                    {
                        System.out.println("Ángulo " + k + " : " + angles[k]);
                    }
                    j++;
                }
                j = 0;
                //regresa la evaluación al Optimizador
                
                server.sendEval(fx);
                System.out.println(i);
                i++;
            }
        server.finishConn();
    }

    private static void iniciarSimulador() {
        System.out.println("Iniciando simulador...");
    }

    private static double[] getSimData() {
        double[] coord = new double[3];
        coord[0] = 35;
        coord[1] = 87;
        coord[2] = 29;
        return coord;
    }    

    private static double[] evaluarSim(double[] angles) {
        double[] fx = new double[2];
        fx[1] = 0;
        for(int i = 0; i < angles.length; i++)
        {
            fx[1] = fx[1] + angles[i];
        }
        fx[0] = fx[1];        
        return fx;
    }
}
