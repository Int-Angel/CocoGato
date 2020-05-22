package cocogatoclient;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

/**
 *
 * @author DELL
 */
public class TicTacToeTablero implements  ActionListener{
    
    JFrame ventanaTablero = new JFrame("Tic Tac Toe Btich");
    JButton botonesTablero[] = new JButton[9];
    static JPanel panelTablero = new JPanel(); 
    static JPanel panelLista = new JPanel();
    ArrayList<Jugador> conectedPlayers = new ArrayList();
    static JButton boton;
    static ArrayList<JButton> usersButtons;
    
    JLabel listaUsuario = new JLabel("SOY YO NIGGA");

    
    String letrita = "";
    ImageIcon imagenX;
    ImageIcon imagenO;
    ImageIcon iconoActual;
    int casillasMarcadas = 0;
    boolean isX;
    boolean victoria = false;
    String[] tableroEnConsola = new String[9];

    //static Socket socket;
  
    ServerListener listener;

    public TicTacToeTablero(boolean isX, boolean active) {

        // Initialize Array
        for (int i = 0; i < 9; i++) {
            tableroEnConsola[i] = "";
        }

        // Assign images
        imagenX = new ImageIcon(getClass().getResource("X.png"));
        imagenO = new ImageIcon(getClass().getResource("O.png"));
        
        // Creamos la ventana    
        ventanaTablero.setSize(1000,500);
        ventanaTablero.setMinimumSize(new Dimension(1000,500));
        ventanaTablero.setExtendedState(ventanaTablero.MAXIMIZED_BOTH);
        ventanaTablero.setLocationRelativeTo(null);
        ventanaTablero.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ventanaTablero.setLayout(new GridLayout(0,2));
        
        ventanaTablero.addWindowListener(new java.awt.event.WindowAdapter() {
                
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {

            }
        });
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
           botonesTablero[i].setBackground(new Color(22,203,194));
           botonesTablero[i].setBorder(new LineBorder(Color.BLACK));
           panelTablero.add(botonesTablero[i]);
        }

        // Action listener pa los botones
        for (int i = 0; i < 9; i++) {
            botonesTablero[i].addActionListener(this);
        }
        
        panelTablero.setBackground(Color.black);
        panelLista.setBackground(Color.black);        
        
        
        //Label de lista de jugadores
        
        JLabel tituloLista = new JLabel("LISTA DE JUGADORES CONECTADOS:");
        tituloLista.setFont(new Font("Calibri", Font.PLAIN, 30));
        tituloLista.setForeground(new Color(22,203,194));
        tituloLista.setHorizontalAlignment(SwingConstants.CENTER);
        tituloLista.setVerticalAlignment(SwingConstants.CENTER);
        panelLista.add(tituloLista);
        
        
        // CREAMOS EL PUTO REFRESH BUTTON
        JButton refrescarLista = new JButton();
        refrescarLista.setText("REFRESCAR LISTA");
        refrescarLista.setFont(new Font("Arial", Font.PLAIN, 40));
        refrescarLista.setBorder(new LineBorder(Color.BLACK));
        refrescarLista.setBackground(new Color(22, 43, 194));
        //boton.setBounds(0, 0, 300, 50);
        //boton.setSize(300, 50);
        refrescarLista.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Refresca mesta");
                deleteButtons();
                try {
                    CocoGatoClient.out.writeUTF("p:0");
                } catch (IOException ex) {
                    Logger.getLogger(TicTacToeTablero.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        panelLista.add(refrescarLista);
        
        
        //Añadimos los Paneles a la ventana

        ventanaTablero.getContentPane().add( panelTablero );
        ventanaTablero.getContentPane().add( panelLista );



        this.isX = isX; 


        ventanaTablero.setVisible(true);
        panelTablero.setVisible(false);
   
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
            
        //isX = !isX;
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
        
        bloquearBotones();
        

        //AQUI SE DEBE MANDAR AL SERVIDOR EL ARREGLO DE POSICIONES
        for(int i = 0; i<9;i++){
          try{
            CocoGatoClient.out.writeUTF(tableroEnConsola[i]);
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
    
    
    public static void agregarBotones(ArrayList<Jugadores> conectedPlayers)
    {   
        JLabel tituloLista = new JLabel("LISTA DE JUGADORES CONECTADOS:");
        for(int i = 0; i < conectedPlayers.size(); i++)
        {
            boton = new JButton();
            boton.setText(conectedPlayers.get(i).usuario);
            boton.setFont(new Font("Arial", Font.PLAIN, 40));
            boton.setBorder(new LineBorder(Color.BLACK));
            boton.setBackground(new Color(22,203,194));
            //boton.setBounds(0, 0, 300, 50);
            //boton.setSize(300, 50);
            boton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println(boton.getText());
            }
            });
            panelLista.add(boton);
        }
        panelLista.repaint();
    }
    public static void agregarBoton(Jugadores jugador)
    {   
        JLabel tituloLista = new JLabel("LISTA DE JUGADORES CONECTADOS:");
        
        boton = new JButton();
        boton.setText(jugador.usuario);
        boton.setFont(new Font("Arial", Font.PLAIN, 40));
        boton.setBorder(new LineBorder(Color.BLACK));
        boton.setBackground(new Color(22,203,194));
        boton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            try{
               CocoGatoClient.out.writeUTF("INVITAR:"+Jugador.id+":"+jugador.id);
               System.out.println("INVITAR:"+Jugador.id+":"+jugador.id);
            }catch(IOException ea){
                System.out.println("Error al crear la partida");
            }
        }
        });
        panelLista.add(boton);
        panelLista.revalidate();
        panelLista.repaint();
    }
    
    public static void deleteButton(String buttonText){
        for(int i = 0; i < usersButtons.size(); i++){
            if(buttonText.equals(usersButtons.get(i).getText()))
            {
                panelLista.remove(usersButtons.get(i));
            }
        }
    }
    
    public static void deleteButtons(){
        panelLista.removeAll();
        
        JLabel tituloLista = new JLabel("LISTA DE JUGADORES CONECTADOS:");
        tituloLista.setFont(new Font("Calibri", Font.PLAIN, 30));
        tituloLista.setForeground(new Color(22,203,194));
        tituloLista.setHorizontalAlignment(SwingConstants.CENTER);
        tituloLista.setVerticalAlignment(SwingConstants.CENTER);
        panelLista.add(tituloLista);
        
        JButton refrescarLista = new JButton();
        refrescarLista.setText("REFRESCAR LISTA");
        refrescarLista.setFont(new Font("Arial", Font.PLAIN, 40));
        refrescarLista.setBorder(new LineBorder(Color.BLACK));
        refrescarLista.setBackground(new Color(22, 43, 194));

        refrescarLista.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Refrescado");
                deleteButtons();
                try {
                    CocoGatoClient.out.writeUTF("p:0");
                } catch (IOException ex) {
                    Logger.getLogger(TicTacToeTablero.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        panelLista.add(refrescarLista);
        panelLista.repaint();
    }
}