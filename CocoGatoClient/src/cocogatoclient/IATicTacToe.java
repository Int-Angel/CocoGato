/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cocogatoclient;
    
import static cocogatoclient.TicTacToeTablero.boton;
import static cocogatoclient.TicTacToeTablero.idContrincante;
import static cocogatoclient.TicTacToeTablero.letrita;
import static cocogatoclient.TicTacToeTablero.tableroEnConsola;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

/**
 * IATicTacToe
 * 
 * Esta clase es la encargada de jugar como la inteligencia artifial contra el
 * jugador
 * 
 * @author Equipo
 */
public class IATicTacToe implements ActionListener{
    

    public JFrame ventanaTablero = new JFrame("IA de practica");
    JButton botonesTablero[] = new JButton[9];
    JPanel panelTablero = new JPanel(); 
    
    String letrita = "";
    ImageIcon imagenX;
    ImageIcon imagenO;
    ImageIcon iconoActual;
    int casillasMarcadas = 0;
    boolean isX = true;
    boolean victoria = false;
    String[] tableroEnConsola = new String[9];
    
    /**
     * IATicTacToe
     * Constructor de la partida contra la IA, crea un tablero aparte
     * en el que juega el jugador y esta IA
     */
    public IATicTacToe(){
        
        ventanaTablero.getComponents();
        
        for (int i = 0; i < 9; i++) {
                tableroEnConsola[i] = "";
        }

            // Assign images
            imagenX = new ImageIcon(getClass().getResource("X.png"));
            imagenO = new ImageIcon(getClass().getResource("O.png"));

            // Creamos la ventana    
            ventanaTablero.setSize(500, 500);
        ventanaTablero.setMinimumSize(new Dimension(500, 500));
        ventanaTablero.setExtendedState(ventanaTablero.MAXIMIZED_BOTH);
        ventanaTablero.setLocationRelativeTo(null);
        // ventanaTablero.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //ventanaTablero.setLayout(new GridLayout(0,2));

        ventanaTablero.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                ventanaTablero.setState(JFrame.ICONIFIED); // To minimize a frame
            }
        });
        //P A N E L   L I S T A

        /*
            panelLista.setLayout(null);
            panelLista.setPreferredSize(new Dimension(1000, 1000)); 
            No jala, era para hacero scrollable
            */
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
            //Añadimos los Paneles a la ventana

            ventanaTablero.getContentPane().add( panelTablero );
            
            this.isX = isX; 
            
            ventanaTablero.setVisible(true);
            panelTablero.setVisible(true);
    }
    
    /**
     * actionPerformed
     * Accion que captura cuando se le dio click a un boton
     * coloca la X o O en el tablero.
     * @param a evento que se ejecuta
     */
    public void actionPerformed(ActionEvent a) {
        System.out.println("Jelou da");
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
        actualizarTablero();
        contarCasillasLlenas();
        corroborarGanacion();
        
        if(!victoria && (casillasMarcadas < 9)){
            boolean puesto = false;
            while(!puesto){
                Random r = new Random();
                int valorDado = r.nextInt(tableroEnConsola.length);
                if(!tableroEnConsola[valorDado].equals("X")&&!tableroEnConsola[valorDado].equals("O")){
                    tableroEnConsola[valorDado] = "O";
                    puesto = true;
                }
            }
        }
        if(casillasMarcadas < 9 && !victoria){
            actualizarTablero();
            contarCasillasLlenas();
            corroborarGanacion(); 
        }

        //AQUI SE DEBE MANDAR AL SERVIDOR EL ARREGLO DE POSICIONES
        for(int i = 0; i<9;i++){
          try{
            CocoGatoClient.out.writeUTF(tableroEnConsola[i]);
          }catch(IOException e){ }
        }    
        //listener.start();
        }
    }
    
    /**
     * contarCasillasLlenas
     * cuanta si todas las casillas del tablero estan llenas o no
     * @return si todas las casillas estan llenas retorna true, retorna false
     * en el caso contrario
     */
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
    
    /**
     * corraoborarGanacion
     * 
     * este metodo verifica si un ganador ya es le ganador de la partida 
     * y manda los mensajes a los juagadores y guarda los resultados en el 
     * servidor mandandole un mensaje al server de que guarde el resultado
     */
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
                if (letrita.equals("X")) {
                if (isX) {
                    try {
                        CocoGatoClient.out.writeUTF("INSERTAR:" + Jugador.id + ":9999:Finalizada:" + Jugador.id);
                    } catch (Exception e) {

                    }
                } else {
                    if (isX) {
                        try {
                            CocoGatoClient.out.writeUTF("INSERTAR:" + Jugador.id + ":9999:Finalizada:9999");
                        } catch (Exception e) {

                        }
                    }
                }
            }
            }
        } else if (!victoria && contarCasillasLlenas() == true) {
            JOptionPane.showMessageDialog(null, "G A T O");
            try {
                    CocoGatoClient.out.writeUTF("INSERTAR:" + Jugador.id + ":9999:Finalizada:0");
                } catch (Exception e) 
                {
                        
                }
        }
    }
    
    /**
     * Show
     * este metodo muestra el tablero 
     */
    public void Show(){
        ventanaTablero.setVisible(true);
    }
    /**
     * actualizarTablero
     * este metodo actualiza el tablero dependiendo de los datos guardados en
     * el arreglo de tableroEnConsola y coloca X o O en sus respectivos lugares
     */
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
 
    /**
     * desbloquearBotonesDisponibles
     * este metodo desbloquea los botones que no estan en uso del tablero para
     * que el juagdor pueda cliquearlos
     */
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
}
