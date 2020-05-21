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
import javax.swing.JOptionPane;

/**
 *
 * @author ricar
 */
public  class CocoGatoClient {
    
        static String host = "25.93.46.49";
        final static  int puerto = 471;
        public static Socket socket;
        public static DataOutputStream out;
        public static DataInputStream in;
        static int id;

        
    public static void main(String[] args) {
    //CrearPartida();
        try{
            socket = new Socket(host, puerto);   
            
            out = new DataOutputStream(socket.getOutputStream());
            

            id = in.readInt();
            System.out.println("Id: "+id);
            
            Thread serverListener = new ServerListener(socket, id);
            serverListener.start();
            
            CrearPartida();
            


           /* out.writeByte(1);
            out.writeUTF("caca1");

            out.writeByte(1);
            out.writeUTF("rick");
            out.writeUTF("12345");

            out.flush(); // Send off the data
            /*
            // Send the second message
            out.writeByte(2);
            out.writeUTF("caca2");
            out.flush(); // Send off the data
            // Send the third message
            out.writeByte(3);
            out.writeUTF("caca1pt1");
            out.writeUTF("caca1pt2");
            out.flush(); // Send off the data
            //id = in.readInt();
            //System.out.println("Id: "+id);
            //out.writeUTF("c:"+id+":"+(id-1));

            out.close();  */

            
        }catch(UnknownHostException e){}catch(IOException a){
            System.out.println("Error al conectarse con el servidor...");
        
         
        //CrearPartida();

    }
       launchInvitation("Erick Penecito");
       ERICK();

        //ERICK();

    }
    
    static void launchInvitation(String playerName)
    {
        int dialogResult = JOptionPane.showConfirmDialog(null, "Aceptar Invitación?","Invitación de "+playerName,
				JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE);
        if(dialogResult==0)//Le puchas en si
        {
            
        }else
        if(dialogResult==1)//Le puchas en no
        {
            
        }else
        if(dialogResult==2)//Le puchas en cancelar
        {
            System.exit(0);
        }else
        if(dialogResult==-1)//Le puchas en la x
        {
            System.exit(0);
        }
    }
    
    static void ERICK(){
        TicTacToeTablero tablero = new TicTacToeTablero(true,true);
        tablero.Show();

    }
    
    static void CrearPartida(){
        try{
            out.writeUTF("c:"+id+":"+(id-1));
            TicTacToeTablero tablero = new TicTacToeTablero(socket,true,true);
            Thread listener = new ServerConfirmedPartida(tablero,socket);
            listener.start();
        }catch(IOException e){
            System.out.println("Error al crear partida");
        }
    }
    
    static void AceptarPartida(){
        try{
            out.writeUTF("a:"+id);
            TicTacToeTablero tablero = new TicTacToeTablero(socket,false,false);
            tablero.Show();
        }catch(IOException e){
            System.out.println("Error al aceptar la partida");
        }
    }

}