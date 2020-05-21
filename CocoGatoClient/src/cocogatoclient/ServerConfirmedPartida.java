package cocogatoclient;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 *
 * @author Propietario
 */
public class ServerConfirmedPartida extends Thread { 
    Socket socket;
    DataInputStream in;
    DataOutputStream out;
    TicTacToeTablero tablero;
    int id;
    boolean stop = false;
    
    public ServerConfirmedPartida(TicTacToeTablero tablero, Socket socket){
        this.socket = socket;
        this.tablero = tablero;
        try{
            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());
        }catch(IOException e){
        System.out.println("Error ServerConfirmedPartida");}
    }
    
    @Override
    public void run(){
       while(!stop){
          try{
              String msg = in.readUTF();
              if(msg.equals("A")){ //jugador acepto la partida
                  tablero.Show();
                  stop = true;
                  System.out.println("Jugador acepto la partida");
              }
          }catch(IOException e){
          System.out.println("Error al aceptar la partida la partida");
          }
       }
    }
}
