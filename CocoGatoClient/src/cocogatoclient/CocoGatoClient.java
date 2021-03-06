package cocogatoclient;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import vistas.Login;

/**
 * CocoGatoCliente
 * 
 * cliente de gato, permite conectarse al server para juagdr gato con otras 
 * personas
 * 
 * @author Equipo
 */
public class CocoGatoClient {

    static String host = "25.93.46.49";
    final static int puerto = 480;
    public static Socket socket;
    public static DataOutputStream out;
    public static DataInputStream in;
    static int id;
    public static Login frame;
    public Thread serverListener;
    
    
    /**
     * main
     * 
     * es el main del cliente, crea las vistas y se conecta al server
     * 
     * @param args 
     */
    public static void main(String[] args) {


        try{
            socket = new Socket(host, puerto);   

            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());

            System.out.println(in.readUTF());

            Thread serverListener = new ServerListener(socket);
            serverListener.start();

            frame = new Login();
            frame.setVisible(true);
            
            
        } catch (UnknownHostException e) {
        } catch (IOException a) {
            System.out.println("Error al conectarse con el servidor...");
        }
    }

    /**
     * launchInvitation
     * lanza una notificacion con un mensaje que le indica al jugador
     * lo invito a jugar una partida de gato, si la acepta empieza el juego
     * si no la acepta no empieza el juego
     * @param playerName es el id del jugador que invito a jugar a este jugador
     */
    static void launchInvitation(String playerName) {
        int dialogResult = JOptionPane.showConfirmDialog(null, "Aceptar Invitación?", "Invitación de " + playerName,
                JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE);
        System.out.println("Invitation");
        
        
        if (dialogResult == 0)//Le puchas en si
        {
            try {
                out.writeUTF("INVITACIONACEPTADA"+":"+playerName+":"+Jugador.id);
                System.out.println("INVITACIONACEPTADA"+":"+playerName+":"+Jugador.id);
            } catch (IOException ex) {
                Logger.getLogger(CocoGatoClient.class.getName()).log(Level.SEVERE, null, ex);
            }
             TicTacToeTablero.Start(false);

        } else if (dialogResult == 1)//Le puchas en no
        {

        } else if (dialogResult == 2)//Le puchas en cancelar
        {
            System.exit(0);
        } else if (dialogResult == -1)//Le puchas en la x
        {
            System.exit(0);
        }
    }

    /**
     * Inicialitation
     * esta funcion crea el tablero principal
     */
    static void Inicialitation() {
        TicTacToeTablero tablero = new TicTacToeTablero(true, true);
        
        tablero.ventanaTablero.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                try {
                    out.writeUTF("x:"+Jugador.id);
                } catch (IOException ex) {
                    Logger.getLogger(CocoGatoClient.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }
}
