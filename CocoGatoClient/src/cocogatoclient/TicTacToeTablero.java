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
 *Ésta clase permite generar un tablero de juego, así como sus correspondientes
 * validaciones tales como el ganador, el perdedor, etc).
 * 
 * @author ERICKLJ
 */
public class TicTacToeTablero implements ActionListener {

    //Ventana principal del juego, incluye en su interior los paneles
    //de tablero y de jugadores
    static JFrame ventanaTablero = new JFrame("Tic TacOCO Toe");
    //Son todos los botones del tablero de juego
    static JButton botonesTablero[] = new JButton[9];
    //El panel que alberga los botones del tablero
    private static JPanel panelTablero = new JPanel();
    //Este panel se infla con botones correspondientes a cada jugador
    static JPanel panelLista = new JPanel();
    //Éste arreglo obtiene la lista de usuarios
    static ArrayList<Jugador> conectedPlayers = new ArrayList();
    static JButton boton;
    //Estos botones se inflan con los diferentes jugadores
    static ArrayList<JButton> usersButtons;
    //El id del contrincante
    static int idContrincante;
    // Muestra el encabezado de la lista de jugadores
    JLabel listaUsuario = new JLabel("");
    //Guarda la letra que sera concatenada al arreglo, asi como permite validar
    //Quien fue quien gano en funcion a su letra
    static String letrita = "";
    //La imagen correspondiente a las X
    static ImageIcon imagenX;
    //La imagen correspondiente a las O
    static ImageIcon imagenO;
    //La imagen correspondiente a la que se va a colocar, cambia el valor entre
    //X y O
    static ImageIcon iconoActual;
    //Cuenta la cantidad de casillas jugadas
    static int casillasMarcadas = 0;
    //Define si el jugador utilizara X´s o O´s
    static boolean isX;
    //Indica cuando algun jugador gana
    static boolean victoria = false;
    //Guarda el tablero para poderlo enviar al servidor
    static String[] tableroEnConsola = new String[9];

    //Se encarga de escuchar los mensajes del servidor
    ServerListener listener;

    /**
     * Éste constructor genera una ventana, la llena con un panel que incluirá
     * los botones del tablero en su interior y en el otro panel la lidta de ju-
     * gadores conectados.
     * 
     * @param isX Si el jugador usara o no X
     * @param active Si el turno esta activo
     */
    public TicTacToeTablero(boolean isX, boolean active) {

        // Inicializa el arreglo del tablero con campos vacios
        for (int i = 0; i < 9; i++) {
            tableroEnConsola[i] = "";
        }

        // Assignamos imagenes de los recursos a sus variables
        imagenX = new ImageIcon(getClass().getResource("X.png"));
        imagenO = new ImageIcon(getClass().getResource("O.png"));

        // Creamos la ventana    
        ventanaTablero.setSize(1000, 500);
        ventanaTablero.setMinimumSize(new Dimension(1000, 500));
        ventanaTablero.setExtendedState(ventanaTablero.MAXIMIZED_BOTH);
        ventanaTablero.setLocationRelativeTo(null);
        ventanaTablero.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ventanaTablero.setLayout(new GridLayout(0, 2));
        //Le añadimos los listener a la ventana en caso de nuevas operaciones
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
        
        //Definimos las dimensiones y contornos de los dos paneles de la ventana
        //P A N E L   L I S T A
        panelLista.setLayout(new GridLayout(0, 1, 1, 10));
        panelLista.setBorder(new EmptyBorder(10, 10, 10, 10));

        //P A N E L   T A B L E R O
        panelTablero.setLayout(new GridLayout(3, 3));
        panelTablero.setBorder(new EmptyBorder(10, 10, 10, 10));
        
        //Creamos y agregamos los botoncitos al panel
        for (int i = 0; i < 9; i++) {
            botonesTablero[i] = new JButton();
            botonesTablero[i].setBackground(new Color(22, 203, 194));
            botonesTablero[i].setBorder(new LineBorder(Color.BLACK));
            panelTablero.add(botonesTablero[i]);
        }

        // Action listener para los botones
        for (int i = 0; i < 9; i++) {
            botonesTablero[i].addActionListener(this);
        }

        //Definimos los colores de fondo
        panelTablero.setBackground(Color.black);
        panelLista.setBackground(Color.black);

        //Label de lista de jugadores y sus propiedades
        JLabel tituloLista = new JLabel("LISTA DE JUGADORES CONECTADOS:");
        tituloLista.setFont(new Font("Calibri", Font.PLAIN, 30));
        tituloLista.setForeground(new Color(22, 203, 194));
        tituloLista.setHorizontalAlignment(SwingConstants.CENTER);
        tituloLista.setVerticalAlignment(SwingConstants.CENTER);
        panelLista.add(tituloLista);

        // CREAMOS EL REFRESH BUTTON
        JButton refrescarLista = new JButton();
        refrescarLista.setText("REFRESCAR LISTA");
        refrescarLista.setFont(new Font("Arial", Font.PLAIN, 40));
        refrescarLista.setBorder(new LineBorder(Color.BLACK));
        refrescarLista.setBackground(new Color(22, 43, 194));
        //Añadimos un listener que cada que se presione el boton borre los botones
        //y cree nuevos para siempre mostrar solo los jugadores conectados
        refrescarLista.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Refrescando...");
                deleteButtons();
                try {
                    CocoGatoClient.out.writeUTF("p:0");
                } catch (IOException ex) {
                    Logger.getLogger(TicTacToeTablero.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        //Agregamos el boton de refrescar al panel de la lista
        panelLista.add(refrescarLista);
        //Añadimos los dos Paneles a la ventana
        ventanaTablero.getContentPane().add(panelTablero);
        ventanaTablero.getContentPane().add(panelLista);
        this.isX = isX;
        //Mostramos visibles la ventana y ocultamos el tablero de juego
        ventanaTablero.setVisible(true);
        panelTablero.setVisible(false);
        //Si no es turno del jugador se bloquean sus botones
        if (!active) {
            bloquearBotones();
        }
        //listener = new ServerListener(in);
    }
    
    /**
     * Éste metodo muestra y hace visible la ventana del tablero y sus componentes
     */
    public void Show() {
        ventanaTablero.setVisible(true);
    }

    /**
     * Metodo de prueba para llenar el arreglo y comprobar que refresque
     */
    public void pruebaLlenarArreglo() {
        tableroEnConsola[0] = "O";
        tableroEnConsola[1] = "X";
        tableroEnConsola[2] = "O";
        tableroEnConsola[3] = "X";
        tableroEnConsola[4] = "O";
        tableroEnConsola[5] = "O";
        tableroEnConsola[6] = "";
        tableroEnConsola[7] = "O";
        tableroEnConsola[8] = "";

    }

    /**
     * Bloquea todos los botones del juego
     */
    public static void bloquearBotones() {
        for (int i = 0; i < 9; i++) {
            botonesTablero[i].setEnabled(false);
        }
    }

    /**
     * Desbloquea todos los botones del juego que estén disponibles
     */
    public static void desbloquearBotonesDisponibles() {
        for (int i = 0; i < 9; i++) {
            if (tableroEnConsola[i].equals("n")) {
                botonesTablero[i].setEnabled(true);
            } else {
                botonesTablero[i].setEnabled(false);
            }
        }
    }

    /**
     * Se utiliza el string[] del tablero en consola para en base a el
     * actualizar el tablero gráfico de cada jugador, llenando y desactivando
     * los botones que ya estén usados y limpia los vacíos
     */
    public static void actualizarTablero() {
        for (int i = 0; i < 9; i++) {
            if (tableroEnConsola[i].equals("X")) {
                //Muestra la imagen de la letra en el botón
                botonesTablero[i].setIcon(imagenX);
                botonesTablero[i].setDisabledIcon(imagenX);
                botonesTablero[i].setEnabled(false);

            } else if (tableroEnConsola[i].equals("O")) {
                //Muestra la imagen de la letra en el botón
                botonesTablero[i].setIcon(imagenO);
                botonesTablero[i].setDisabledIcon(imagenO);
                botonesTablero[i].setEnabled(false);
            } else if (tableroEnConsola[i].equals("n")) {
                botonesTablero[i].setIcon(null);
            }
        }
        System.out.println("Tablero Actualizado");
        panelTablero.repaint();
    }

    
    /**
     * Al recibir un estimulo, los botones del tablero hacen que se aumente
     * el contador de jugadas, para asi poder determinar si se da un empate
     * 
     * 
     * @param a el objeto que dispara el evento
     */
    public void actionPerformed(ActionEvent a) {

        if (a.getSource() == boton) {
            JOptionPane.showMessageDialog(null, "SOY UN BOTON");
        } else if (a.getSource() == botonesTablero[0] || a.getSource() == botonesTablero[1]
                || a.getSource() == botonesTablero[2] || a.getSource() == botonesTablero[3]
                || a.getSource() == botonesTablero[4] || a.getSource() == botonesTablero[5]
                || a.getSource() == botonesTablero[6] || a.getSource() == botonesTablero[7]
                || a.getSource() == botonesTablero[8]) {

            try {
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
                /*for(int i = 0; i<9;i++){
                try{
                CocoGatoClient.out.writeUTF(tableroEnConsola[i]);
                }catch(IOException e){ }
                }*/
                String tableroinfo = tableroEnConsola[0] + ":" + tableroEnConsola[1] + ":" + tableroEnConsola[2] + ":" + tableroEnConsola[3] + ":" + tableroEnConsola[4] + ":" + tableroEnConsola[5] + ":" + tableroEnConsola[6] + ":" + tableroEnConsola[7] + ":" + tableroEnConsola[8];
                CocoGatoClient.out.writeUTF("MOVIMIENTO:" + Jugador.id + ":" + Jugador.contricanteId + ":" + tableroinfo);
                System.out.println("MOVIMIENTO:" + Jugador.id + ":" + Jugador.contricanteId + ":" + tableroinfo);
                //listener.start();
            } catch (IOException ex) {
                Logger.getLogger(TicTacToeTablero.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }
    
    /**
     * Cuenta las  casillas que se hayan llenado para asi poder llegar a deter-
     * minar un posible empate.
     * @return true si si se llega a las 9 casillas
     *         false si no
     */
    public static boolean contarCasillasLlenas() {
        int contadorCasillas = 0;
        for (int i = 0; i < 9; i++) {
            if (tableroEnConsola[i] == "X" || tableroEnConsola[i] == "O") {
                contadorCasillas++;

            }
        }
        System.out.println(contadorCasillas);
        if (contadorCasillas == 9) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Se validan las posibles formas de ganar, y se comprueban dos casos.
     * Si se da una victoria se lanza una notificacion del jugador ganador
     * y se bloquean los botones. Y si se da un empate muestra un dialogo informando
     * dicho estado.
     */
    public static void corroborarGanacion() {
        //HORIZONTAL
        if ((tableroEnConsola[0].equals(tableroEnConsola[1]) && tableroEnConsola[1].equals(tableroEnConsola[2]) && !tableroEnConsola[0].equals("")) && !tableroEnConsola[0].equals("n")) {
            victoria = true;
            letrita = tableroEnConsola[0];
        } else if ((tableroEnConsola[3].equals(tableroEnConsola[4]) && tableroEnConsola[4].equals(tableroEnConsola[5]) && !tableroEnConsola[3].equals("")) && !tableroEnConsola[3].equals("n")) {
            victoria = true;
            letrita = tableroEnConsola[3];
        } else if ((tableroEnConsola[6].equals(tableroEnConsola[7]) && tableroEnConsola[7].equals(tableroEnConsola[8]) && !tableroEnConsola[6].equals("")) && !tableroEnConsola[6].equals("n")) {
            victoria = true;
            letrita = tableroEnConsola[6];
        }

        // Vertical
        if ((tableroEnConsola[0].equals(tableroEnConsola[3]) && tableroEnConsola[3].equals(tableroEnConsola[6]) && !tableroEnConsola[0].equals("")) && !tableroEnConsola[0].equals("n")) {
            victoria = true;
            letrita = tableroEnConsola[0];
        } else if ((tableroEnConsola[1].equals(tableroEnConsola[4]) && tableroEnConsola[4].equals(tableroEnConsola[7]) && !tableroEnConsola[1].equals("")) && !tableroEnConsola[1].equals("n")) {
            victoria = true;
            letrita = tableroEnConsola[1];
        } else if ((tableroEnConsola[2].equals(tableroEnConsola[5]) && tableroEnConsola[5].equals(tableroEnConsola[8]) && !tableroEnConsola[2].equals("")) && !tableroEnConsola[2].equals("n")) {
            victoria = true;
            letrita = tableroEnConsola[2];
        }

        // Diagonal
        if ((tableroEnConsola[0].equals(tableroEnConsola[4]) && tableroEnConsola[4].equals(tableroEnConsola[8]) && !tableroEnConsola[0].equals("")) && !tableroEnConsola[0].equals("n")) {
            victoria = true;
            letrita = tableroEnConsola[0];
        } else if ((tableroEnConsola[2].equals(tableroEnConsola[4]) && tableroEnConsola[4].equals(tableroEnConsola[6]) && !tableroEnConsola[2].equals("")) && !tableroEnConsola[2].equals("n")) {
            victoria = true;
            letrita = tableroEnConsola[2];
        }
        //Si se da una victoria se muestra un dialogo y se inserta el estado de
        //la partida en la BD
        if (victoria) {
            JOptionPane.showMessageDialog(null, "El jugador Portador de las " + letrita + " es el ganador!");
            for (JButton i : botonesTablero) {
                i.setEnabled(false);
            }

            if (letrita.equals("X")) {
                if (isX) {
                    try {
                        CocoGatoClient.out.writeUTF("INSERTAR:" + Jugador.id + ":" + idContrincante + ":Finalizada:" + Jugador.id);
                    } catch (Exception e) {

                    }
                } else {
                    if (isX) {
                        try {
                            CocoGatoClient.out.writeUTF("INSERTAR:" + Jugador.id + ":" + idContrincante + ":Finalizada:" + idContrincante);
                        } catch (Exception e) {

                        }
                    }
                }
            }
        //Si se da un empate se muestra el resultado en un dialogo y se
        //inserta en la BD
        } else if (!victoria && contarCasillasLlenas() == true) {
            JOptionPane.showMessageDialog(null, "G A T O");
            try {
                    CocoGatoClient.out.writeUTF("INSERTAR:" + Jugador.id + ":" + idContrincante + ":Finalizada:0");
                } catch (Exception e) 
                {
                        
                }
        }
    }

    public static void agregarBotones(ArrayList<Jugadores> conectedPlayers) {
        JLabel tituloLista = new JLabel("LISTA DE JUGADORES CONECTADOS:");
        for (int i = 0; i < conectedPlayers.size(); i++) {
            boton = new JButton();
            boton.setText(conectedPlayers.get(i).usuario);
            boton.setFont(new Font("Arial", Font.PLAIN, 40));
            boton.setBorder(new LineBorder(Color.BLACK));
            boton.setBackground(new Color(22, 203, 194));
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

    public static void agregarBoton(Jugadores jugador) {
        JLabel tituloLista = new JLabel("LISTA DE JUGADORES CONECTADOS:");

        boton = new JButton();
        boton.setText(jugador.usuario);
        boton.setFont(new Font("Arial", Font.PLAIN, 40));
        boton.setBorder(new LineBorder(Color.BLACK));
        boton.setBackground(new Color(22, 203, 194));
        boton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //SI ES CONTRA LA IA
                if (jugador.id == 9999) {
                    IATicTacToe ticTacToe = new IATicTacToe();
                    ticTacToe.Show();
                } else {
                    try {
                        CocoGatoClient.out.writeUTF("INVITAR:" + Jugador.id + ":" + jugador.id);
                        idContrincante = jugador.id;
                        System.out.println("INVITAR:" + Jugador.id + ":" + jugador.id);
                    } catch (IOException ea) {
                        System.out.println("Error al crear la partida");
                    }
                }

            }
        });
        panelLista.add(boton);
        panelLista.revalidate();
        panelLista.repaint();
    }

    public static void deleteButton(String buttonText) {
        for (int i = 0; i < usersButtons.size(); i++) {
            if (buttonText.equals(usersButtons.get(i).getText())) {
                panelLista.remove(usersButtons.get(i));
            }
        }
    }

    public static void deleteButtons() {
        panelLista.removeAll();

        JLabel tituloLista = new JLabel("LISTA DE JUGADORES CONECTADOS:");
        tituloLista.setFont(new Font("Calibri", Font.PLAIN, 30));
        tituloLista.setForeground(new Color(22, 203, 194));
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

    static void enableTableroIA() {
        panelTablero.setVisible(true);
    }

    static void Start(boolean x) {
        panelTablero.setVisible(true);
        for (int i = 0; i < tableroEnConsola.length; i++) {
            tableroEnConsola[i] = "n";
        }
        isX = x;
        actualizarTablero();
        if (!x) {
            bloquearBotones();
        } else {
            desbloquearBotonesDisponibles();
        }
    }
}
