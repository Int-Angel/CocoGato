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
 *
 * @author ricar
 */
public class CocoGatoClient {

    static String host = "25.93.46.49";
    final static int puerto = 480;
    public static Socket socket;
    public static DataOutputStream out;
    public static DataInputStream in;
    static int id;
    public Thread serverListener;
    
    public static void main(String[] args) {


    //CrearPartida();
        try{
            socket = new Socket(host, puerto);   

            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());

            System.out.println(in.readUTF());

            Thread serverListener = new ServerListener(socket);
            serverListener.start();

            Login frame = new Login();
            frame.setVisible(true);
            
            
        } catch (UnknownHostException e) {
        } catch (IOException a) {
            System.out.println("Error al conectarse con el servidor...");
        }

   
        //CrearPartida();
      // launchInvitation("Erick Penecito");
      // ERICK();


        // CrearPartida();
        // launchInvitation("Erick Penecito");
        // ERICK();
        //ERICK();
    }

    static void launchInvitation(String playerName) {
        int dialogResult = JOptionPane.showConfirmDialog(null, "Aceptar Invitación?", "Invitación de " + playerName,
                JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE);
        System.out.println("Invitation");
        
        
        if (dialogResult == 0)//Le puchas en si
        {

            TableroDeGato tablero = new TableroDeGato(host);
            tablero.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

           // TableroDeGato tablero = new TableroDeGato(socket);

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

    static void ERICK() {
        TicTacToeTablero tablero = new TicTacToeTablero(true,true);
        tablero.Show();

    }

    static void CloseLogin() {

    }
    
    static void RICK() {
        TicTacToeTablero tablero = new TicTacToeTablero(true, true);
        
        tablero.ventanaTablero.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                try {
                    out.writeUTF("x:"+Jugador.id);
                    /*if (JOptionPane.showConfirmDialog(tablero.ventanaTablero,
                    "Are you sure you want to close this window?", "Close Window?",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
                    System.exit(0);
                    }*/
                } catch (IOException ex) {
                    Logger.getLogger(CocoGatoClient.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    static void CrearPartida() {
        try {
            out.writeUTF("c:" + id + ":" + (id - 1));
            TicTacToeTablero tablero = new TicTacToeTablero( true, true);
            Thread listener = new ServerConfirmedPartida(tablero, socket);
            listener.start();
            System.out.println("Crear partida entre jugador: " + id + " y " + (id - 1));
        } catch (IOException e) {
            System.out.println("Error al crear partida");
        }
    }

    static void AceptarPartida() {
        try {
            out.writeUTF("a:" + id);
            TicTacToeTablero tablero = new TicTacToeTablero( false, false);
            tablero.Show();
        } catch (IOException e) {
            System.out.println("Error al aceptar la partida");
        }
    }
}
