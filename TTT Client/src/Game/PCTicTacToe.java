package Game;


import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 *
 * @author othmane
 */
public class PCTicTacToe extends JFrame {

    private JFrame window = new JFrame();
    JPanel p = new JPanel(new GridLayout(3, 3));
    XOButton buttons[] = new XOButton[9];
    private int turn = 0;
    private int PCturn = 1;
    private char grid[] = new char[10];
    private Random rand = new Random();
    ArrayList<Integer> free = new ArrayList<Integer>();
    
    JLabel txtTitle;

    public static void main(String[] args) {
        new PCTicTacToe();
    }

    public PCTicTacToe() {
        super("Tic Tac Toe");
        window.setSize(400, 400);
        setResizable(false);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        window.add(p, BorderLayout.CENTER);

        txtTitle = new JLabel("YOU vs Computer", JLabel.CENTER);
        txtTitle.setHorizontalTextPosition(JLabel.CENTER);
        txtTitle.setFont(new Font("Serif", Font.PLAIN, 21));

    
        window.add(txtTitle, BorderLayout.NORTH);

        for (int i = 1; i <= 9; ++i) {
            grid[i] = '.';
            free.add(i);
        }

        for (int i = 1; i <= 9; i++) {
            XOButton btn = new XOButton(i);
            btn.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    //if(turn % 2 == 0) return ;
                    playMove(btn.getNumber());
                    printGrid();

                }

            });
            p.add(btn);
        }

        window.setVisible(true);

    }

    private void printGrid() {
        System.out.println("Turn #" + turn + ": ");
        for (int i = 1; i <= 9; ++i) {
            System.out.print(grid[i] + " ");
            if (i % 3 == 0) {
                System.out.print("\n");
            }
        }
        System.out.print("\n");
        
        System.out.println("Free Moves : ");
        for(int i = 0; i < free.size(); ++i) {
            System.out.print(free.get(i) + " ");
        }
        System.out.println("\n");
    }
    
    Icon x = new ImageIcon(getClass().getResource("X.png"));
    Icon o = new ImageIcon(getClass().getResource("O.png"));
    //Icon xo = new ImageIcon(getClass().getResource("Tie.png"));

    private void showDialogMessageX(String title, String message) {
        JOptionPane.showMessageDialog(null, message, title, JOptionPane.INFORMATION_MESSAGE, x);
    }

    private void showDialogMessageO(String title, String message) {
        JOptionPane.showMessageDialog(null, message, title, JOptionPane.INFORMATION_MESSAGE, o);
    }

    private void showDialogMessageXO(String title, String message) {
        JOptionPane.showMessageDialog(null, message, title, JOptionPane.INFORMATION_MESSAGE);
    }

    private XOButton getButton(int n) {
        Object tmp = p.getComponent(n);
        XOButton Nv = (XOButton) tmp;
        return Nv;
    }

    private char getWinner() {
        char winner = '.';

        // check Lines
        if (grid[1] == grid[2] && grid[2] == grid[3]) {
            if (winner == '.') {
                winner = grid[1];
            }
        }
        if (grid[4] == grid[5] && grid[5] == grid[6]) {
            if (winner == '.') {
                winner = grid[4];
            }
        }
        if (grid[7] == grid[8] && grid[8] == grid[9]) {
            if (winner == '.') {
                winner = grid[7];
            }
        }

        // check Columns
        if (grid[1] == grid[4] && grid[4] == grid[7]) {
            if (winner == '.') {
                winner = grid[1];
            }
        }
        if (grid[2] == grid[5] && grid[5] == grid[8]) {
            if (winner == '.') {
                winner = grid[2];
            }
        }
        if (grid[3] == grid[6] && grid[6] == grid[9]) {
            if (winner == '.') {
                winner = grid[3];
            }
        }

        // check Diagonals
        if (grid[1] == grid[5] && grid[5] == grid[9]) {
            if (winner == '.') {
                winner = grid[5];
            }
        }
        if (grid[3] == grid[5] && grid[5] == grid[7]) {
            if (winner == '.') {
                winner = grid[5];
            }
        }

        return winner;

    }

    private boolean checkDraw() {
        // checking if the grid is full
        for(int i = 1; i <= 9 ; ++i) {
            if(grid[i] == '.') return false;
        }
        return true;
    }

    private void resetGame() {
        free.clear();
        for (int i = 1; i <= 9; ++i) {
            free.add(i);
            grid[i] = '.';
            XOButton Nv = getButton(i - 1);
            Nv.setIcon(null);
            Nv.setUsed(false);
            
        }
        turn = 0;

    }

    private void PCplay() {
        int i, randomFreeIndex;

        do {
            randomFreeIndex = rand.nextInt(free.size() - 1);
            i = free.get(randomFreeIndex); // {1, size - 1]
        } while (grid[i] == 'X' && grid[i] != '.');
        free.remove(new Integer(i));
        System.out.println("PC Played " + i);
        grid[i] = 'O';
        XOButton PC = getButton(i - 1);
        PC.setUsed(true);
        PC.setIcon(XOButton.getOIcon());
    }

    //.X.......
    private void playMove(int x) {

        // select clicked button
        XOButton target = getButton(x - 1);

        // check if the button is already used
        if (target.isUsed()) {
            return;
        }
        
        
        // played a valid move
        
        free.remove(new Integer(x));
        target.setUsed(true);

        // update the GUI and the Grid
        switch (turn % 2) {
            case 0:
                target.setIcon(XOButton.getXIcon());
                grid[x] = 'X';
                break;
            case 1:
                target.setIcon(XOButton.getXIcon());
                grid[x] = 'X';
                break;
        }
        
        // increment turn
        turn++;
        
        if(checkGameState() == true) {
            return ;
        }
        
        // PC turn
        PCplay();
        
        checkGameState();
    }

    private boolean checkGameState() {
        if (getWinner() != '.') {
            if (getWinner() == 'X') {
                showDialogMessageX("Result", "Winner is Player " + getWinner() + " !");
                resetGame();
                return true;
            } else {
                showDialogMessageO("Result", "Winner is Player " + getWinner() + " !");
                resetGame();
                return true;
            }
        } else if (checkDraw()) {
            showDialogMessageXO("Result", "Draw !");
            resetGame();
            return true;
        }
        return false;
    }

}

//import java.awt.*;
//import java.awt.event.*;
//import javax.swing.*;
//
//public class Gui extends JFrame
//{
//    private JFrame window = new JFrame();
//    private JButton but[] = new JButton[9];
//
//    public Gui()
//    {
//        window.setSize(300,400);
//        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
////        window.setLayout(new GridLayout(4, 3));
//        JPanel panel = new JPanel( new GridLayout(3, 3) );
//        window.add(panel, BorderLayout.CENTER);
//
//        JLabel txt = new JLabel("Will you dare?", JLabel.CENTER);
////        txt.setLayout(new GridLayout(1, 1));
//        txt.setHorizontalTextPosition(JLabel.CENTER);
//        txt.setFont(new Font("Serif", Font.PLAIN, 21));
////        window.add(txt);
//        window.add(txt, BorderLayout.NORTH);
//
//        for(int i = 0; i < 9; i++)
//        {
//            but[i] = new JButton();
////            window.add(but[i]);
//            panel.add(but[i]);
//        }
//
//        window.setVisible(true);
//
//    }
//
//    public static void main(String args[]) throws Exception
//    {
//        new Gui();
//    }
//
//}
