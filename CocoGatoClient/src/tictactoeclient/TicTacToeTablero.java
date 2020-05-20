/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tictactoeclient;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 *
 * @author DELL
 */
public class TicTacToeTablero extends JFrame{
    
    JFrame ventanaTablero = new JFrame("Tic Tac Toe Btich");
    JButton botonesTablero[] = new JButton[9];

    String letter = "";
    ImageIcon X;
    ImageIcon O;
    ImageIcon ltr;
    int value = 0;
    
    boolean victoria = false;
    String[] letters = new String[9];
    
    public TicTacToeTablero() {
        // Initialize Array
        for (int i = 0; i < 9; i++) {
            letters[i] = "";
        }

        // Assign images
        X = new ImageIcon(getClass().getResource("X.png"));
        O = new ImageIcon(getClass().getResource("O.png"));
        
        // Create the Window
        ventanaTablero.setSize(500,500);
        ventanaTablero.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ventanaTablero.setLayout(new GridLayout(3,3));

        // Agregamos los botoncitos
        for (int i = 0; i < 9; i++) {
            botonesTablero[i] = new JButton();
            ventanaTablero.add(botonesTablero[i]);
        }

        // Add ActionListener
        for (int i = 0; i < 9; i++) {
            botonesTablero[i].addActionListener((ActionListener) this);
        }

        ventanaTablero.setVisible(true);
    }
    
    public void actionPerformed(ActionEvent a) {
        value++;
        // Who's Turn
        if (value % 2 == 1) {
            ltr = X;
            letter = "X";
        }
        if (value % 2 == 0) {
            ltr = O;
            letter = "O";
        }

        // Display Letters
        for (int i = 0; i < 9; i++) {
            if (a.getSource() == botonesTablero[i]) {
                botonesTablero[i].setIcon(ltr);
                botonesTablero[i].setDisabledIcon(ltr);
                botonesTablero[i].setEnabled(false);
                letters[i] = letter;
            }
        }

        // Who Won

        // Horizontal
        if (letters[0].equals(letters[1]) && letters[1].equals(letters[2]) && !letters[0].equals("")) {
            victoria = true;
        } else if (letters[3].equals(letters[4]) && letters[4].equals(letters[5]) && !letters[3].equals("")) {
            victoria = true;
        } else if (letters[6].equals(letters[7]) && letters[7].equals(letters[8]) && !letters[6].equals("")) {
            victoria = true;
        }

        // Vertical
        if (letters[0].equals(letters[3]) && letters[3].equals(letters[6]) && !letters[0].equals("")) {
            victoria = true;
        } else if (letters[1].equals(letters[4]) && letters[4].equals(letters[7]) && !letters[1].equals("")) {
            victoria = true;
        } else if (letters[2].equals(letters[5]) && letters[5].equals(letters[8]) && !letters[2].equals("")) {
            victoria = true;
        }

        // Diagonal
        if (letters[0].equals(letters[4]) && letters[4].equals(letters[8]) && !letters[0].equals("")) {
            victoria = true;
        } else if (letters[2].equals(letters[4]) && letters[4].equals(letters[6]) && !letters[2].equals("")) {
            victoria = true;
        }

        if (victoria) {
            JOptionPane.showMessageDialog(null, "Player " + letter + " wins!");
            for (JButton i : botonesTablero) {
                i.setEnabled(false);
            }
        } else if (!victoria && value == 9) {
            JOptionPane.showMessageDialog(null, "The game ended in a tie.");
        }
    }
}