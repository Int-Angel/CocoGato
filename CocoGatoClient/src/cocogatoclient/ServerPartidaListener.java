/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cocogatoclient;

import java.io.IOException;

/**
 *
 * @author Propietario
 */
public class ServerPartidaListener extends Thread{
    
    boolean yourTurn = false;
    String[] tableroEnConsola = new String[9];
    
    public ServerPartidaListener(boolean turn){
        yourTurn = turn;
         System.out.println("Partida creada");
     
    }
    
    @Override
    public void run(){
        while(true){
            if(!yourTurn){
                leer();
                actualizar();
                yourTurn = false;
            }
        }
    }
    
    void leer(){
        System.out.println("Leyendo datos de partida");
        for(int i = 0; i<9;i++){
            try{
                tableroEnConsola[i] = CocoGatoClient.in.readUTF();
            }catch(IOException e){
                System.out.println("Error al leer datos del server, dato: "+ i);
            }
        }
         System.out.println("Fin lectura de datos");
    }
    
    void actualizar(){
         System.out.println("Actualizando datos de partida");
        for(int i = 0; i<9; i++){
            TicTacToeTablero.tableroEnConsola[i] = tableroEnConsola[i];
        }
        TicTacToeTablero.actualizarTablero();
        TicTacToeTablero.desbloquearBotonesDisponibles();
         System.out.println("Fin de actualizacion");
        
    }
    
}
