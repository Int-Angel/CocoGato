/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cocogatoserver;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 *
 * @author Lenovo
 */
public class Partida extends Thread {
    static int contador = 1;
    
    int idpartida;
    int idJugador1;
    int idJugador2;
    int idGanador;
    String status;
    Socket player1;
    Socket player2;
    boolean xTurn;
    boolean over;
    
    DataOutputStream out1;
    DataInputStream in1;
    DataOutputStream out2;
    DataInputStream in2;
    
    String[] tableroEnConsola = new String[9];


    public Partida(){}
    
    public Partida(Socket player1, Socket player2) {
        this.player1 = player1;
        this.player2 = player2;
        this.idpartida = contador ++;
        this.status = "Creada";
        xTurn = true;
        over = false;
        
        try{
                   
            in1 = new DataInputStream(player1.getInputStream());
            out1 = new DataOutputStream(player1.getOutputStream());

            in2 = new DataInputStream(player2.getInputStream());
            out2 = new DataOutputStream(player2.getOutputStream());
        }catch(IOException e){
            System.out.println("Error al crear in y out,id partida: " + idpartida);
        }
        
        InGame();
    }
    @Override
    public void run(){
        while(true){
            if(xTurn){
                recibirTableroJugador(in1);
                enviarTableroJugador(out2);
                xTurn = false;
            }else{
                recibirTableroJugador(in2);
                enviarTableroJugador(out1);
                xTurn = true;
            }
        }
    }
    
    void recibirTableroJugador(DataInputStream jugador){
        for(int i = 0; i<9;i++){
            try{
                tableroEnConsola[i] = jugador.readUTF();
            }catch(IOException e){
                System.out.println("Error al recibir datos, dato: " + i);
            }
        }
    }
    
    void enviarTableroJugador(DataOutputStream jugador){
        for(int i = 0; i<9;i++){
            try{
                jugador.writeUTF(tableroEnConsola[i]);
            }catch(IOException e){
                System.out.println("Error al mandar datos, dato: " + i);
            }
        }
    }
    
    public void InGame()
    {
    }
    
    public int getnId() {
        return idpartida;
    }

    public void setId(int idpartida) {
        this.idpartida = idpartida;
    }

    public int getIdJugador1() {
        return idJugador1;
    }

    public void setIdJugador1(int idJugador1) {
        this.idJugador1 = idJugador1;
    }

    public int getIdJugador2() {
        return idJugador2;
    }

    public void setIdJugador2(int idJugador2) {
        this.idJugador2 = idJugador2;
    }

    public int getIdGanador() {
        return idGanador;
    }

    public void setIdGanador(int idGanador) {
        this.idGanador = idGanador;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
