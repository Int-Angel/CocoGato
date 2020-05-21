/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cocogatoclient;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 *
 * @author Propietario
 */
public class ServerListener extends Thread{
    
    Socket socket;
    DataOutputStream out;
    DataInputStream in;
    TicTacToeTablero tablero;
    int id;
    
    public ServerListener(Socket socket){
        this.socket = socket;
        try{
            out = new DataOutputStream(socket.getOutputStream());
            in = new DataInputStream(socket.getInputStream());
        }catch(IOException e){}
    }
    
    public ServerListener(Socket socket, int id){
        this.socket = socket;
        this.id = id;
        try{
            out = new DataOutputStream(socket.getOutputStream());
            in = new DataInputStream(socket.getInputStream());
        }catch(IOException e){}
    }
    
    
    @Override
    public void run(){
        while(true){
            try{
                String msg = in.readUTF();
                if(msg.equals("nose")){
                    //Crear notificacion de partida
                    AceptarPartida();
                }
            }catch(IOException e){}
        }
    }
    
    void AceptarPartida(){
        try{
            out.writeUTF("a:"+id);
            TicTacToeTablero tablero = new TicTacToeTablero(socket,false,false);
            tablero.Show();
        }catch(IOException e){
            System.out.println("Error al aceptar la partida");
        }
    }
}
