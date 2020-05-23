/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.

ESTE ES EL LISTENER QUE ESTA ESPERANDO INVITACION.
 */
package cocogatoserver;

import static cocogatoserver.PlayersLook.inPlayer;
import com.sun.org.apache.xpath.internal.operations.Gte;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;

/**
 * Clase ClientListener
 * Esta clase desprende de hilos. Para cada uno de los clientes conectados al
 * servidor, se crea un hilo de este tipo, y su función es estar recibiendo
 * y enviando mensajes constantemente al cliente, lo cual es necesario para
 * el inicio se sesión, crear la lista de jugadores conectados, enviar 
 * invitación de partida, recibir invitación de partida, crear partida, insertar
 * en la base de datos, etc.
 */
public class ClientListener extends Thread {

    Socket playerSocket;
    DataInputStream in;
    DataOutputStream out;
    DB db = new DB();
    Jugador jugador = new Jugador();

    Socket socketPlayer1 = null;
    Socket socketPlayer2 = null;
    DataOutputStream socketOutput1, socketOutput2;

    public ClientListener(Socket playerSocket) {
        this.playerSocket = playerSocket;
        try {
            in = new DataInputStream(playerSocket.getInputStream());
            out = new DataOutputStream(playerSocket.getOutputStream());
        } catch (IOException e) {
        }
    }

    /**
     * Función run
     * Esta función es la que se manda llamar al crear el hilo, y lo que hace es
     * estar recibiendo mensajes del socket del cliente correspondiente. Al recibir
     * el mensaje se le hace un split, y se llama a la función Decode, mandándole
     * el mensaje completo y el arreglo string obtenido del split.
     */
    @Override
    public void run() {
        try {
            while (true) {
                String msg = in.readUTF();
                String[] splitMsg = msg.split(":");
                Decode(splitMsg, msg);
            }
            //playerSocket = Server;
        } catch (IOException e) {
        }
    }

    /**
     * Función Decode
     * Esta función recibe un arreglo string, el cual corresponde al mensaje
     * recibido al cual se le hizo split por palabra en la función run, asímismo recibe el
     * mensaje completo.
     * En esta función se realiza la verificación del mensaje, primero se 
     * realizan verificaciones para la primer palabra del mensaje, la cual significa
     * la clave de la acción a realizar, por ejemplo "INVITAR", significa invitar 
     * a un jugador a una partida, "i", signigica Iniciar Sesión, "MOVIMIENTO" 
     * segnifica que el jugador realizó un movimiento en el tablero.
     * Para cada caso se realizan las acciones correspondientes.
     * 
     * @param msg mensaje recibido separado por palabras
     * @param completedMsg mensaje recibido
     */
    void Decode(String[] msg, String completedMsg) {
        
        if(msg[0].equals("MOVIMIENTO"))
        {
            try {
                GetOutputStream(msg[2]).writeUTF(completedMsg);
            } catch (IOException ex) {
                Logger.getLogger(ClientListener.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        else if (msg[0].equals("INVITAR")) {
                Invitar(msg[1], msg[2]);
        }
        if (msg[0].equals("INVITACIONACEPTADA")) {
            InvitacionAceptada(msg[1], msg[2]);
        }

        //INICIAR SESION
        if (msg[0].equals("i")) {
            System.out.println("Iniciando sesion entrante");
            Jugador jugador = IniciarSesion.VerificarUsuario(msg[1], msg[2]);

            try {
                if (jugador != null) {
                    System.out.println(jugador.id + jugador.usuario + jugador.contraseña);
                    System.out.println("Iniciando Sesion");
                    out.writeUTF("i:" + jugador.id + ":" + jugador.usuario + ":" + jugador.contraseña);
                    Boolean found = false;
                    for (ConnectedPlayers connectedPlayer : Server.connectedPlayers) {
                        if (connectedPlayer.jugador.id == jugador.id) {
                            connectedPlayer.playerSocket = playerSocket;
                            found = true;
                        }
                    }
                    if(!found)
                        Server.connectedPlayers.add(new ConnectedPlayers(jugador, playerSocket));

                } else {
                    System.out.println("No se que verga paso");
                    out.writeUTF("i:false");
                }
            } catch (IOException ex) {
                Logger.getLogger(ClientListener.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        //ENVIAR LA LISTA DE JUGADORES
        if (msg[0].equals("p")) {
            for (ConnectedPlayers player : Server.connectedPlayers) {
                try {
                    out.writeUTF("p:" + player.jugador.id + ":" + player.jugador.usuario);
                } catch (IOException ex) {
                    Logger.getLogger(ClientListener.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        //INSERTARJUGADOR
        if (msg[0].equals("r")) {
            jugador.setUsuario(msg[1]);
            jugador.setContraseña(msg[2]);
            db.insertAutoincrementPlayer(jugador);
            System.out.println("Jugador insertado con exito");
        }
        //DESCONECTARJUGADOR
        if (msg[0].equals("x")) {
            DisconnectPlayer(Integer.parseInt(msg[1]));
        }
    }

   
    /**
     * Función DisconnectPlayer
     * Se encarga de eliminar al jugador de la lista de connectedPlayers.
     * @param id 
     */
    void DisconnectPlayer(int id) {
        int index = FindPlayer(id);
        if (index == -1) {
            System.out.println("Jugador a eliminar no encontrado");
        } else {
            Server.connectedPlayers.remove(index);
        }
    }

    /**
     * Función FindPlayer
     * Se encarga de buscar la posición del jugador en el arreglo de connectedPlayers.
     * @param id ID del jugador.
     * @return posición en el arreglo connectedPlayers.
     */
    int FindPlayer(int id) {
        int index = 0;

        for (ConnectedPlayers player : Server.connectedPlayers) {
            if (player.jugador.id == id) {
                return index;
            }
            index++;
        }

        return -1;
    }

    /**
     * Función InvitacionAceptada 
     * Se manda llamar cuando se acepta la invitación de partida.
     * Lo que hace es mandarle mensajes a los dos clientes de la partida, que les
     * indique que la partida ya se inicio.
     * @param id1 id cliente 1.
     * @param id2 id cliente 2.
     */
    private void InvitacionAceptada(String id1, String id2) {
        GetSocketsAndStreams(id1, id2);

        if (socketPlayer1 != null && socketPlayer2 != null) {
            try {
                socketOutput1.writeUTF("INICIARPARTIDA:1:"+id1+":"+id2);
                socketOutput2.writeUTF("INICIARPARTIDA:2:"+id2+":"+id1);
                insertarPartida(id1, id2);
            } catch (IOException easd) {
                System.out.println("Error al mandar notificacion al jugador 1 o 2");
            }
        }

    }

    private void Invitar(String id1, String id2) {
        GetSocketsAndStreams(id1, id2);

        if (socketPlayer1 != null && socketPlayer2 != null) {
            try {
                socketOutput1.writeUTF("INVITACIONENVIADA");        
                socketOutput2.writeUTF("TEINVITA:" + id1);
            } catch (IOException ex) {
                Logger.getLogger(ClientListener.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private Socket GetSocket(String id) {
        for (int i = 0; i < Server.connectedPlayers.size(); i++) {
            if (Server.connectedPlayers.get(i).jugador.id == (Integer.parseInt(id))) {
                return (Server.connectedPlayers.get(i).playerSocket);
            }
        }
        return null;
    }

    private DataOutputStream GetOutputStream(String id) {
        for (int i = 0; i < Server.connectedPlayers.size(); i++) {
            if (Server.connectedPlayers.get(i).jugador.id == (Integer.parseInt(id))) {
                try {
                    return (new DataOutputStream(Server.connectedPlayers.get(i).playerSocket.getOutputStream()) );
                } catch (IOException ex) {
                    Logger.getLogger(ClientListener.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return null;
    }

    
    private void GetSocketsAndStreams(String id1, String id2) {
        for (int i = 0; i < Server.connectedPlayers.size(); i++) {
            if (Server.connectedPlayers.get(i).jugador.id == (Integer.parseInt(id1))) {
                socketPlayer1 = Server.connectedPlayers.get(i).playerSocket;
            } else if (Server.connectedPlayers.get(i).jugador.id == (Integer.parseInt(id2))) {
                socketPlayer2 = Server.connectedPlayers.get(i).playerSocket;
            }
        }
        try {
            socketOutput1 = new DataOutputStream(socketPlayer1.getOutputStream());
            socketOutput2 = new DataOutputStream(socketPlayer2.getOutputStream());
        } catch (IOException easd) {
            System.out.println("Error al mandar notificacion al jugador 1 o 2");
        }
    }
    
    private void insertarPartida(String idPlayer1, String idPlayer2){
        String status = "Iniciada";
        db.insertAutoincrementGame(Integer.parseInt(idPlayer1), Integer.parseInt(idPlayer2), status);
        //db.insertGame( Integer.parseInt(id1), Integer.parseInt(id2), MIN_PRIORITY, id2)
    }
}
