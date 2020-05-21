/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cocogatoserver;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ricar
 */
public class PlayersLook extends Thread {
    static DataOutputStream outPlayer;

    @Override
    public void run() {
        Socket player;
        Server.playerSockets = new ArrayList<>();
        while (true) {
            try {
                player = Server.server.accept();
                outPlayer = new DataOutputStream(player.getOutputStream());
                outPlayer.writeUTF("Conectado al Servidor");
                System.out.println("Cliente Conectado");
                Server.playerSockets.add(player);
            } catch (IOException ex) {
                Logger.getLogger(PlayersLook.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
