
package cocogatoserver;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

<<<<<<< HEAD:CocoGato/src/cocogatoserver/CocoGatoServer.java
public class CocoGatoServer {

=======
public class CocoGato {
>>>>>>> e84dd4f2bea2c2f34b86e30a718deeefd068d9bf:CocoGato/src/cocogato/CocoGato.java
    public static void main(String[] args) throws Exception {
        ServerSocket server;
        final int port = 471;
        DataInputStream in;
        DataOutputStream out;
        Socket sc = null;
        
         try {
            server = new ServerSocket(port);
            
            System.out.println("Server iniciado");
            
            while(true)
            {
                sc = server.accept();
                
                in = new DataInputStream(sc.getInputStream());
                out = new DataOutputStream(sc.getOutputStream());
                
                String message = in.readUTF();
                System.out.println(message);
                out.writeUTF("Server: conexión exitosa.");
                sc.close();
                System.out.println("Cliente desconectado");
            }
        } catch (IOException ex) {
            Logger.getLogger(CocoGatoServer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

