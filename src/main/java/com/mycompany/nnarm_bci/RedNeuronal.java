package com.mycompany.nnarm_bci;

/**
 *
 * @author Liliana L Beristain
 */

import org.neuroph.core.Layer;
import org.neuroph.core.NeuralNetwork;
import org.neuroph.core.Neuron;
import org.neuroph.core.transfer.Sigmoid;
import org.neuroph.core.transfer.Tanh;
import org.neuroph.core.transfer.TransferFunction;
import org.neuroph.util.ConnectionFactory;
import org.neuroph.util.TransferFunctionType;


public class RedNeuronal {

    private NeuralNetwork ann;
    
    public RedNeuronal()
    {
        //Se crea la red
    //   ann = new NeuralNetwork(TransferFunctionType.SIGMOID, 1, 20, 1);
       ann = new NeuralNetwork();
       
       //Se crean las capas con sus respectivas neuronas
       Layer capaEntrada = new Layer();
       capaEntrada.addNeuron(new Neuron());
       capaEntrada.addNeuron(new Neuron());
       capaEntrada.addNeuron(new Neuron());
       for(int i=0; i < capaEntrada.getNeuronsCount(); i++)
       {
           capaEntrada.getNeuronAt(i).setTransferFunction(new Tanh());
       }
       
       Layer capaOculta = new Layer();
       capaOculta.addNeuron(new Neuron());
       capaOculta.addNeuron(new Neuron());
       capaOculta.addNeuron(new Neuron());
       capaOculta.addNeuron(new Neuron());
       capaOculta.addNeuron(new Neuron());
       capaOculta.addNeuron(new Neuron());
       for(int i=0; i < capaOculta.getNeuronsCount(); i++)
       {
           capaOculta.getNeuronAt(i).setTransferFunction(new Tanh());
       }
       
       Layer capaSalida = new Layer();
       capaSalida.addNeuron(new Neuron());
       capaSalida.addNeuron(new Neuron());
       capaSalida.addNeuron(new Neuron());
       capaSalida.addNeuron(new Neuron());
       capaSalida.addNeuron(new Neuron());     
       for(int i=0; i < capaSalida.getNeuronsCount(); i++)
       {
           capaSalida.getNeuronAt(i).setTransferFunction(new Tanh());
       }
        
       
//Se agregan a la red neuronal
       ann.addLayer(0,capaEntrada);
       ann.addLayer(1,capaOculta);
       ann.addLayer(2, capaSalida);
       //Se genera la conexión entre capas
       ConnectionFactory.fullConnect(capaEntrada, capaOculta, 0);
       ConnectionFactory.fullConnect(capaOculta, capaSalida, 0);
       //ConnectionFactory.fullConnect(capaEntrada, capaSalida, 0);
       //Se indican la capa de entrada y salida
       ann.setInputNeurons(capaEntrada.getNeurons());
       ann.setOutputNeurons(capaSalida.getNeurons());
        
       //Por defecto, la función de activación es de step, cambiamos a tangente hiperbólica
    /*   for(int i = 0; i < ann.getLayersCount(); i++)
       {
           for(int j = 0; j < ann.getLayerAt(i).getNeuronsCount(); j++)
           {
               ann.getLayerAt(i).getNeuronAt(j).setTransferFunction(new Tanh());
               System.out.println("NEURONA: " + (ann.getLayerAt(i).getNeuronAt(j).getTransferFunction().getOutput(-0.29)));
           }
       }     */ 
    }

    double[] iniciar(double[] data, double[] w) {
        //Asignar valores de entrada
        ann.setInput(data);
       
        //Asignar pesos
        ann.setWeights(w);
        //calcular los valores de la red
        ann.calculate();
        //Obtener los valores de la red        
        double[] result = ann.getOutput();
        
        return result;
    }

    void setNN(double[] objCoord, double[] pesos) {
        //Normalizar los valores de entrada
        
        //Asignar valores de esntrada
        ann.setInput(normalizar(objCoord));
        //Asignar pesoss
        ann.setWeights(pesos);
    }

    double[] getAngles() {
        //calcular los valores de la red
        ann.calculate();
        //Obtener los valores de la red        
        double[] result = ann.getOutput();
        for(int i = 0; i < result.length; i++)
        {
            result[i] = (result[i]*180)/Math.PI;
        }       
        return result;
    }

    private double[] normalizar(double[] objCoord) {
        double[] norm = new double[objCoord.length];
        int xMax = 1000;
        for(int i = 0; i < objCoord.length; i++)
        {
            if(objCoord[i] >= 0)
            {
                objCoord[i] = objCoord[i]*(-1);
                norm[i] = (objCoord[i]) / (xMax);
                norm[i] = norm[i]*(-1);
            }
            else
            {
                norm[i] = (objCoord[i]) / (xMax);
            }
            System.out.println("Entrada: " + norm[i]);
        }
        
        return norm;
    }
}
