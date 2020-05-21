/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cocogatoclient;

import java.io.DataInputStream;

/**
 *
 * @author Propietario
 */
public class ServerListener extends Thread{
    DataInputStream in;
    public ServerListener(DataInputStream in){
        this.in = in;
    }
    
    @Override
    public void run(){
        
    }
}
