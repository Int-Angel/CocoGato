package cocogatoclient;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import static java.lang.System.in;
import java.net.Socket;
import static java.nio.file.Files.size;
import java.util.ArrayList;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

/**
 *
 * @author DELL
 */
public class TicTacToeTablero implements  ActionListener{
    
    JFrame ventanaTablero = new JFrame("Tic Tac Toe Btich");
    JButton botonesTablero[] = new JButton[9];
    JPanel panelTablero = new JPanel(); 
    JPanel panelLista = new JPanel();
    ArrayList<Jugador> conectedPlayers = new ArrayList();
    JButton boton;
    
    JLabel listaUsuario = new JLabel("SOY YO NIGGA");

    
    String letrita = "";
    ImageIcon imagenX;
    ImageIcon imagenO;
    ImageIcon iconoActual;
    int casillasMarcadas = 0;
    boolean isX;
    boolean victoria = false;
    String[] tableroEnConsola = new String[9];

    Socket socket;
    DataInputStream in;
    DataOutputStream out;
    ServerListener listener;

    public TicTacToeTablero(Socket socket,boolean isX, boolean active) {

        // Initialize Array
        for (int i = 0; i < 9; i++) {
            tableroEnConsola[i] = "";
        }

        // Assign images
        imagenX = new ImageIcon(getClass().getResource("X.png"));
        imagenO = new ImageIcon(getClass().getResource("O.png"));
        
        // Creamos la ventana    
        ventanaTablero.setSize(1000,500);
        ventanaTablero.setExtendedState(ventanaTablero.MAXIMIZED_BOTH);
        ventanaTablero.setLocationRelativeTo(null);
        ventanaTablero.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ventanaTablero.setLayout(new GridLayout(0,2));
        

        //P A N E L   L I S T A

        /*
        panelLista.setLayout(null);
        panelLista.setPreferredSize(new Dimension(1000, 1000)); 
        No jala, era para hacero scrollable
        */
           
        panelLista.setLayout(new GridLayout(0,1,1,10));
        panelLista.setBorder(new EmptyBorder(10, 10, 10, 10));
        
        //P A N E L   T A B L E R O
        panelTablero.setLayout(new GridLayout(3,3));   
        panelTablero.setBorder(new EmptyBorder(10, 10, 10, 10));
        // Agregamos los botoncitos
        for (int i = 0; i < 9; i++) {
            botonesTablero[i] = new JButton();
            panelTablero.add(botonesTablero[i]);
        }

        // Action listener pa los botones
        for (int i = 0; i < 9; i++) {
            botonesTablero[i].addActionListener(this);
        }
        
        //Añadimos los Paneles a la ventana
        ventanaTablero.getContentPane().add( panelTablero );

        ventanaTablero.getContentPane().add( panelLista );

        
        agregarBotones();


        this.isX = isX; 
        this.socket = socket;

      /*try{
            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());
        }catch(IOException e){}*/

        ventanaTablero.setVisible(true);

        this.socket = socket;
        
        try{
            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());
        }catch(IOException e){}
   
        if(!active)
            bloquearBotones();
        
        
        //listener = new ServerListener(in);
    }
    
    public void Show(){
        ventanaTablero.setVisible(true);
    }
    
    public void pruebaLlenarArreglo()
    {
        tableroEnConsola[0] = "O"; tableroEnConsola[1] = "X"; tableroEnConsola[2] = "O";
        tableroEnConsola[3] = "X"; tableroEnConsola[4] = "O"; tableroEnConsola[5] = "O";
        tableroEnConsola[6] = ""; tableroEnConsola[7] = "O"; tableroEnConsola[8] = "";
        
    }
    public void bloquearBotones()
    {
        for (int i = 0; i < 9; i++)
        {        
            botonesTablero[i].setEnabled(false);
        }
    }
    
    public void desbloquearBotonesDisponibles()
    {
        for (int i = 0; i < 9; i++)
        {      
            if(tableroEnConsola[i]=="X"||tableroEnConsola[i]!="O")
            {
                botonesTablero[i].setEnabled(true);
            }
        }
    }
    
    public void actualizarTablero()
    {
        for (int i = 0; i < 9; i++) {
            if (tableroEnConsola[i]=="X") {
                //Muestra la imagen de la letra en el botón
                botonesTablero[i].setIcon(imagenX);
                botonesTablero[i].setDisabledIcon(imagenX); 
                botonesTablero[i].setEnabled(false);
            
            }
            else if(tableroEnConsola[i]=="O") {
                //Muestra la imagen de la letra en el botón
                botonesTablero[i].setIcon(imagenO);
                botonesTablero[i].setDisabledIcon(imagenO); 
                botonesTablero[i].setEnabled(false);
            }
        }
    }
    
   
    
    public void actionPerformed(ActionEvent a) {
        
        if(a.getSource()==boton){
            JOptionPane.showMessageDialog(null, "SOY UN BOTON");
        }
        
        else
        if(a.getSource()==botonesTablero[0]||a.getSource()==botonesTablero[1]
         ||a.getSource()==botonesTablero[2]||a.getSource()==botonesTablero[3]
         ||a.getSource()==botonesTablero[4]||a.getSource()==botonesTablero[5]
         ||a.getSource()==botonesTablero[6]||a.getSource()==botonesTablero[7]
         ||a.getSource()==botonesTablero[8]){
            
        isX = !isX;
        casillasMarcadas++;
        // Definimos los turnos
        if (isX == true) {
            iconoActual = imagenX;
            letrita = "X";
        }
        if (isX == false) {
            iconoActual = imagenO;
            letrita = "O";
        }

        // Mostrar casillas en el tablero
        for (int i = 0; i < 9; i++) {
            if (a.getSource() == botonesTablero[i]) {
                
                //Muestra la imagen de la letra en el botón
                botonesTablero[i].setIcon(iconoActual);
                
                //Desactivamos el botón presionado
                botonesTablero[i].setDisabledIcon(iconoActual); 
                botonesTablero[i].setEnabled(false);
                tableroEnConsola[i] = letrita;
            }
        }
        contarCasillasLlenas();
        corroborarGanacion();
        
        //bloquearBotones();
        

        //AQUI SE DEBE MANDAR AL SERVIDOR EL ARREGLO DE POSICIONES
        for(int i = 0; i<9;i++){
          try{
            out.writeUTF(tableroEnConsola[i]);
          }catch(IOException e){ }
        }
        
        //listener.start();
        }

    }
    
    public boolean contarCasillasLlenas()
    {
        int contadorCasillas = 0;
        for(int i = 0; i < 9; i++)
        {
            if(tableroEnConsola[i]=="X"||tableroEnConsola[i]=="O")
            {
                contadorCasillas++;
                
            }
        }
        System.out.println(contadorCasillas);
        if(contadorCasillas == 9){
            return true;
        }
        else return false;
    }
    
    public void corroborarGanacion()
    {
        if (tableroEnConsola[0].equals(tableroEnConsola[1]) && tableroEnConsola[1].equals(tableroEnConsola[2]) && !tableroEnConsola[0].equals("")) {
            victoria = true; letrita = tableroEnConsola[0];
        } else if (tableroEnConsola[3].equals(tableroEnConsola[4]) && tableroEnConsola[4].equals(tableroEnConsola[5]) && !tableroEnConsola[3].equals("")) {
            victoria = true; letrita = tableroEnConsola[3];
        } else if (tableroEnConsola[6].equals(tableroEnConsola[7]) && tableroEnConsola[7].equals(tableroEnConsola[8]) && !tableroEnConsola[6].equals("")) {
            victoria = true; letrita = tableroEnConsola[6];
        }

        // Vertical
        if (tableroEnConsola[0].equals(tableroEnConsola[3]) && tableroEnConsola[3].equals(tableroEnConsola[6]) && !tableroEnConsola[0].equals("")) {
            victoria = true; letrita = tableroEnConsola[0];
        } else if (tableroEnConsola[1].equals(tableroEnConsola[4]) && tableroEnConsola[4].equals(tableroEnConsola[7]) && !tableroEnConsola[1].equals("")) {
            victoria = true; letrita = tableroEnConsola[1];
        } else if (tableroEnConsola[2].equals(tableroEnConsola[5]) && tableroEnConsola[5].equals(tableroEnConsola[8]) && !tableroEnConsola[2].equals("")) {
            victoria = true; letrita = tableroEnConsola[2];
        }

        // Diagonal
        if (tableroEnConsola[0].equals(tableroEnConsola[4]) && tableroEnConsola[4].equals(tableroEnConsola[8]) && !tableroEnConsola[0].equals("")) {
            victoria = true; letrita = tableroEnConsola[0];
        } else if (tableroEnConsola[2].equals(tableroEnConsola[4]) && tableroEnConsola[4].equals(tableroEnConsola[6]) && !tableroEnConsola[2].equals("")) {
            victoria = true; letrita = tableroEnConsola[2];
        }

        if (victoria) {
            JOptionPane.showMessageDialog(null, "El jugador Portador de las " + letrita + " es el ganador!");
            for (JButton i : botonesTablero) {
                i.setEnabled(false);
            }
        } else if (!victoria && contarCasillasLlenas() == true) {
            JOptionPane.showMessageDialog(null, "G A T O B I T C H");
        }
    }
    
    private void agregarBotones()
    {
        
        
        /*
        for(int i = 0; i < conectedPlayers.size(); i++)
        {
            boton = new JButton();
            boton.setText(conectedPlayers.get(i).usuario);
            boton.setBounds(0, (i * 50) + 1, 250, 50);
            boton.addActionListener(this);
            
        }
        */
        JLabel tituloLista = new JLabel("Lista de Jugadores:");
        panelLista.add(tituloLista);
        
        for(int i = 0; i < 5; i++)
        {
            boton = new JButton();
            boton.setText("Hola");

            //boton.setBounds(0, 0, 300, 50);
            //boton.setSize(300, 50);
            boton.addActionListener(this);

            boton.setBounds(0, 0, 300, 50);
            boton.setSize(300, 50);
            boton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                
            }
            });
            panelLista.add(boton);
            
        }
    }
}