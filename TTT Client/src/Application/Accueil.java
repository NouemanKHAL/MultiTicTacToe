/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Application;

import Game.TicTacToe;
import Model.MonModele;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.Timer;
import javax.swing.table.TableModel;
import javax.swing.text.DefaultCaret;
import static javax.swing.text.DefaultCaret.ALWAYS_UPDATE;

/**
 *
 * @author nouemankhal
 */
public class Accueil extends javax.swing.JFrame {

    private ObjectInputStream input;
    private ObjectOutputStream output;
    private String username;

    public Accueil(String Username) {
        
        input = Connection.getInput();
        output = Connection.getOutput();

        this.username = Username;
        initComponents();
        getContentPane().setBackground(new java.awt.Color(247, 247, 247));

        labelUsername.setText(username);

        this.setLocationRelativeTo(null);
        

        chatArea.getCaret().setBlinkRate(0);
        
        DefaultCaret caret = (DefaultCaret) chatArea.getCaret();
        caret.setUpdatePolicy(ALWAYS_UPDATE);
        
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                try {
                    output.writeObject("4");
                    output.writeObject(username);

                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                new SettingNetwork().setVisible(true);
                try {
                    Connection.getSocket().close();
                    Connection.getInput().close();
                    Connection.getOutput().close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }

                dispose();
            }
        });

        Refresh();
        refreshUserInfo();
        refreshChatArea();
    }

    public static void main(String[] args) {
        new Accueil("Noueman").setVisible(true);
    }

    public void Refresh() {
        {
            ActionListener task = new ActionListener() {
                public void actionPerformed(ActionEvent evt) {
                    try {
                        output.writeObject("2");
                        output.writeObject(username);

                        String[] titres = (String[]) input.readObject();
                        ArrayList<Vector<String>> mesLignes = (ArrayList<Vector<String>>) input.readObject();
                        output.flush();
                        chatArea.setContentType("text/html");

                        tabUsers.setModel(new MonModele(titres, mesLignes));

                    } catch (IOException ex) {
                        ex.printStackTrace();
                    } catch (ClassNotFoundException ex) {
                        ex.printStackTrace();
                    }
                }

            };

            Timer timer = new Timer(500, task);
            timer.start();

        }

    }

    public void refreshUserInfo() {

        try {
            output.writeObject("3");
            output.writeObject(username);

            String wins = (String) input.readObject();
            String draws = (String) input.readObject();
            String losses = (String) input.readObject();
            
            System.out.println("Wins : " + wins);
            System.out.println("Draws : " + draws);
            System.out.println("Losses : " + losses);
            
            labelWins.setText(wins);
            labelDraws.setText(draws);
            labelLosses.setText(losses);

        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();

        }

        ActionListener task = new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                try {
                    output.writeObject("3");
                    output.writeObject(username);

                    String wins = (String) input.readObject();
                    String draws = (String) input.readObject();
                    String losses = (String) input.readObject();

                    labelWins.setText(wins);
                    labelDraws.setText(draws);
                    labelLosses.setText(losses);
                    
                } catch (IOException ex) {
                    ex.printStackTrace();
                } catch (ClassNotFoundException ex) {
                    ex.printStackTrace();

                }
            }

        };

        Timer timer = new Timer(1000, task);
        timer.start();

    }

    

    public void refreshChatArea() {
        
        ActionListener task = new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                try {
                    output.writeObject("6");

                    String[] titres = (String[]) input.readObject();
                    ArrayList<Vector<String>> mesLignes = (ArrayList<Vector<String>>) input.readObject();
                    String oldText = "";
                    String chatText = "<html>";

                    for (int i = 0; i < mesLignes.size(); ++i) {
                        String line = mesLignes.get(i).get(2);
                        line = "<i>" + line + "</i> - ";
                        line += "<strong>" + mesLignes.get(i).get(0) + " : </strong>";
                        line += mesLignes.get(i).get(1);
                        chatText += line + "<br>";
                    }
                    chatText += "</html>";

                    chatArea.setText(chatText);

                } catch (IOException ex) {
                    Logger.getLogger(Accueil.class
                            .getName()).log(Level.SEVERE, null, ex);

                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(Accueil.class
                            .getName()).log(Level.SEVERE, null, ex);

                }
            }

        };

        Timer timer = new Timer(300, task);
        timer.start();
    }

    public void playGameHost(int gameID) {

        // launching tic tac toe window
        System.out.println("HOST Game ID : " + gameID);
        String title = getPlayerX(gameID) + " VS " + getPlayerO(gameID);
        TicTacToe game = new TicTacToe(title, 'X', gameID, username);
        game.setVisible(true);
    }

    public void playGameClient(int gameID) {

        // launching tic tac toe window
        System.out.println("CLIENT Game ID : " + gameID);
        String title = getPlayerO(gameID) + " VS " + getPlayerX(gameID);
        TicTacToe game = new TicTacToe(title, '0', gameID, username);
        game.setVisible(true);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jRadioButton1 = new javax.swing.JRadioButton();
        jScrollPane = new javax.swing.JScrollPane();
        tabUsers = new javax.swing.JTable();
        labelTitle = new javax.swing.JLabel();
        btnDisconnect = new javax.swing.JButton();
        labelUsername = new javax.swing.JLabel();
        labelUsername1 = new javax.swing.JLabel();
        labelIndication = new javax.swing.JLabel();
        labelUsername2 = new javax.swing.JLabel();
        labelUsername3 = new javax.swing.JLabel();
        labelUsername4 = new javax.swing.JLabel();
        labelUsername5 = new javax.swing.JLabel();
        labelWins = new javax.swing.JLabel();
        labelDraws = new javax.swing.JLabel();
        labelLosses = new javax.swing.JLabel();
        btnPlay = new javax.swing.JButton();
        txtChat = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        chatArea = new javax.swing.JTextPane();
        jLabel1 = new javax.swing.JLabel();

        jRadioButton1.setText("jRadioButton1");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("TicTacToe - Multiplayer !");
        setBackground(new java.awt.Color(255, 0, 0));

        tabUsers.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        tabUsers.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabUsersMouseClicked(evt);
            }
        });
        jScrollPane.setViewportView(tabUsers);

        labelTitle.setBackground(new java.awt.Color(14, 121, 183));
        labelTitle.setFont(new java.awt.Font("Trebuchet MS", 1, 22)); // NOI18N
        labelTitle.setForeground(new java.awt.Color(14, 121, 183));
        labelTitle.setText("PLAYERS LOOKING FOR AN OPPONENT");

        btnDisconnect.setBackground(new java.awt.Color(14, 121, 183));
        btnDisconnect.setForeground(new java.awt.Color(204, 255, 255));
        btnDisconnect.setText("Disconnect");
        btnDisconnect.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDisconnectActionPerformed(evt);
            }
        });

        labelUsername.setFont(new java.awt.Font("Gill Sans MT", 2, 24)); // NOI18N
        labelUsername.setForeground(new java.awt.Color(102, 102, 102));
        labelUsername.setText("UserName");

        labelUsername1.setBackground(new java.awt.Color(14, 121, 183));
        labelUsername1.setFont(new java.awt.Font("Trebuchet MS", 1, 36)); // NOI18N
        labelUsername1.setForeground(new java.awt.Color(14, 121, 183));
        labelUsername1.setText("WELCOME !");

        labelIndication.setFont(new java.awt.Font("Ubuntu", 2, 12)); // NOI18N
        labelIndication.setForeground(new java.awt.Color(102, 102, 102));
        labelIndication.setText("* Double Clic on a player to start a game");

        labelUsername2.setBackground(new java.awt.Color(14, 121, 183));
        labelUsername2.setFont(new java.awt.Font("Trebuchet MS", 1, 22)); // NOI18N
        labelUsername2.setForeground(new java.awt.Color(14, 121, 183));
        labelUsername2.setText("YOUR CURRENT SCORES :");

        labelUsername3.setFont(new java.awt.Font("Ubuntu", 1, 18)); // NOI18N
        labelUsername3.setForeground(new java.awt.Color(72, 77, 78));
        labelUsername3.setText("Draws  :");

        labelUsername4.setFont(new java.awt.Font("Ubuntu", 1, 18)); // NOI18N
        labelUsername4.setForeground(new java.awt.Color(72, 77, 78));
        labelUsername4.setText("Wins     :");

        labelUsername5.setFont(new java.awt.Font("Ubuntu", 1, 18)); // NOI18N
        labelUsername5.setForeground(new java.awt.Color(72, 77, 78));
        labelUsername5.setText("Losses :");

        labelWins.setFont(new java.awt.Font("Ubuntu", 0, 18)); // NOI18N
        labelWins.setForeground(new java.awt.Color(72, 77, 78));
        labelWins.setText("XXX");

        labelDraws.setFont(new java.awt.Font("Ubuntu", 0, 18)); // NOI18N
        labelDraws.setForeground(new java.awt.Color(72, 77, 78));
        labelDraws.setText("XXX");

        labelLosses.setFont(new java.awt.Font("Ubuntu", 0, 18)); // NOI18N
        labelLosses.setForeground(new java.awt.Color(72, 77, 78));
        labelLosses.setText("XXX");

        btnPlay.setBackground(new java.awt.Color(14, 121, 183));
        btnPlay.setFont(new java.awt.Font("Ubuntu", 0, 18)); // NOI18N
        btnPlay.setForeground(new java.awt.Color(204, 255, 255));
        btnPlay.setText("PLAY");
        btnPlay.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPlayActionPerformed(evt);
            }
        });

        txtChat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtChatActionPerformed(evt);
            }
        });

        jButton1.setBackground(new java.awt.Color(14, 121, 183));
        jButton1.setForeground(new java.awt.Color(204, 255, 255));
        jButton1.setText("OK");
        jButton1.setMargin(new java.awt.Insets(4, 4, 4, 4));
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jScrollPane3.setDoubleBuffered(true);

        chatArea.setEditable(false);
        chatArea.setDoubleBuffered(true);
        jScrollPane3.setViewportView(chatArea);

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Application/images/LOGO_TTT.png"))); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(txtChat, javax.swing.GroupLayout.PREFERRED_SIZE, 205, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 65, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(labelTitle)
                    .addComponent(jScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnPlay, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addGap(13, 13, 13)
                        .addComponent(labelIndication)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 36, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(labelUsername2)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(78, 78, 78)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnDisconnect, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(labelUsername4)
                                    .addComponent(labelUsername5)
                                    .addComponent(labelUsername3))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                                    .addComponent(labelWins)
                                    .addComponent(labelDraws)
                                    .addComponent(labelLosses)))))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                            .addComponent(labelUsername)
                            .addComponent(labelUsername1)
                            .addComponent(jLabel1))
                        .addGap(47, 47, 47)))
                .addGap(25, 25, 25))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 541, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButton1)
                            .addComponent(txtChat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(labelTitle)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(labelUsername1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(labelUsername)
                                .addGap(35, 35, 35)
                                .addComponent(labelUsername2)
                                .addGap(27, 27, 27)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(labelUsername4)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(labelUsername3)
                                        .addGap(15, 15, 15)
                                        .addComponent(labelUsername5))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(labelWins)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(labelDraws)
                                        .addGap(15, 15, 15)
                                        .addComponent(labelLosses)))
                                .addGap(24, 24, 24)
                                .addComponent(btnDisconnect, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 477, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(labelIndication)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnPlay, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(16, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private String getPlayerX(int gameID) {
        String playerX = "";
        try {
            output.writeObject("16");
            output.writeObject(gameID);
            playerX = (String) input.readObject();
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }

        return playerX;
    }

    private String getPlayerO(int gameID) {
        String playerX = "";
        try {
            output.writeObject("17");
            output.writeObject(gameID);
            playerX = (String) input.readObject();
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }

        return playerX;
    }

    private void btnDisconnectActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDisconnectActionPerformed
        try {
            // TODO add your handling code here:
            output.writeObject("4");
            output.writeObject(username);

        } catch (IOException ex) {
            ex.printStackTrace();
        }
        new SettingNetwork().setVisible(true);
        try {
            Connection.getSocket().close();
            Connection.getInput().close();
            Connection.getOutput().close();

        } catch (IOException ex) {
            ex.printStackTrace();
        }

        dispose();

    }//GEN-LAST:event_btnDisconnectActionPerformed

    private void tabUsersMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabUsersMouseClicked
        // TODO add your handling code here:
        if (evt.getClickCount() == 2) {
            TableModel model = tabUsers.getModel();
            if (tabUsers.getSelectedRow() >= 0) {
                String target = model.getValueAt(tabUsers.getSelectedRow(), 0).toString();
                System.out.println("Username Clicked : " + target);

                try {
                    // start a game
                    output.writeObject("8");
                    output.writeObject(this.username);
                    output.writeObject(target);

                    // get the gameID
                    int gameID = -1;
                    do {
                        output.writeObject("9");
                        output.writeObject(username);
                        System.out.println("Username : " + username + " Type : O");
                        output.writeObject("O");
                        try {
                            gameID = (int) input.readObject();

                        } catch (ClassNotFoundException ex) {
                            ex.printStackTrace();
                        }
                        
                        Thread.sleep(200);
                        
                    } while (gameID == -1);

                    playGameClient(gameID);
                    
                } catch (IOException ex) {
                    ex.printStackTrace();
                } catch (InterruptedException ex) {
                    Logger.getLogger(Accueil.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        }
    }//GEN-LAST:event_tabUsersMouseClicked

    private void btnPlayActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPlayActionPerformed
        ActionListener task = new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                try {
                    output.writeObject("5");
                    output.writeObject(username);

                } catch (IOException ex) {
                    Logger.getLogger(Accueil.class
                            .getName()).log(Level.SEVERE, null, ex);
                }

                int gameID = -1;
                System.out.println("Waiting for games ! ");
                try {
                    output.writeObject("9");
                    output.writeObject(username);
                    output.writeObject("X");
                    gameID = (int) input.readObject();
                    System.out.println("User : " + username + " game id : " + gameID);

                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(Accueil.class
                            .getName()).log(Level.SEVERE, null, ex);

                } catch (IOException ex) {
                    Logger.getLogger(Accueil.class
                            .getName()).log(Level.SEVERE, null, ex);
                }

                if (gameID == -1) {
                    System.out.println("Game Found ID : " + gameID + ")");
                } else {
                    playGameHost(gameID);
                    ((Timer) evt.getSource()).stop();
                }

                // Start Game
                // Stop looking for game
            }
        };

        Timer timer = new Timer(500, task);

        timer.start();

    }//GEN-LAST:event_btnPlayActionPerformed

    private void txtChatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtChatActionPerformed
        try {
            // TODO add your handling code here:
            output.writeObject("7");
            output.writeObject(username);
            output.writeObject(txtChat.getText());
            refreshChatArea();
            txtChat.setText("");

        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }//GEN-LAST:event_txtChatActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        try {
            // TODO add your handling code here:
            output.writeObject("7");
            output.writeObject(username);
            output.writeObject(txtChat.getText());
            refreshChatArea();
            txtChat.setText("");

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }//GEN-LAST:event_jButton1ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnDisconnect;
    private javax.swing.JButton btnPlay;
    private javax.swing.JTextPane chatArea;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JRadioButton jRadioButton1;
    private javax.swing.JScrollPane jScrollPane;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JLabel labelDraws;
    private javax.swing.JLabel labelIndication;
    private javax.swing.JLabel labelLosses;
    private javax.swing.JLabel labelTitle;
    private javax.swing.JLabel labelUsername;
    private javax.swing.JLabel labelUsername1;
    private javax.swing.JLabel labelUsername2;
    private javax.swing.JLabel labelUsername3;
    private javax.swing.JLabel labelUsername4;
    private javax.swing.JLabel labelUsername5;
    private javax.swing.JLabel labelWins;
    private javax.swing.JTable tabUsers;
    private javax.swing.JTextField txtChat;
    // End of variables declaration//GEN-END:variables
}
