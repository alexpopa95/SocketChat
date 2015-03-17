package com.chat.client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;

/**
 * Created by alexpopa95 on 01/02/15.
 */
public class Client extends JFrame {

    private final String messaggioBenvenuto =
            "BENVENUTO NELLA CHAT" +
                    "\nCreato da Alexandru Popa" +
                    "\nClasse 5G" +
                    "\nIIS N. Copernico A Carpeggaini";

    public static final String nome = "SocketChat by Alex Popa (SERVER)";
    public static final int larghezza = 200;
    public static final int altezza = 300;
    private final int margineBordo = 5;

    public static boolean hostConnesso;

    SocketData connessioneDati = null;
    RicezioneMessaggi controller = null;
    InvioMessagi sender = null;

    String username;
    String hostName;
    String serverIP;

    JTextArea convPanel;
    JTextField messPanel;
    JButton inviaButton;

    public Client(String username){
        this.username = username;
        init(username);
    }

    public void init(final String username) {
        convPanel = new JTextArea(messaggioBenvenuto);
        messPanel = new JTextField();
        inviaButton = new JButton("Invia");

        setPreferredSize(new Dimension(larghezza+50, altezza+50));
        setMinimumSize(new Dimension(larghezza, altezza));
        setResizable(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        SpringLayout layout = new SpringLayout();
        JPanel contenitore = new JPanel(layout);
        contenitore.setBackground(Color.gray);

        JScrollPane convScrollPanel = new JScrollPane(convPanel);

        contenitore.add(convScrollPanel);
        contenitore.add(messPanel);
        contenitore.add(inviaButton);

        //Posizione conversazioni
        convPanel.setBackground(Color.darkGray);
        convPanel.setForeground(Color.white);
        convPanel.setMargin(new Insets(10, 10, 10, 10));
        convPanel.setEditable(false);
        layout.putConstraint(SpringLayout.WEST, convScrollPanel, margineBordo, SpringLayout.WEST, this);
        layout.putConstraint(SpringLayout.NORTH, convScrollPanel, margineBordo, SpringLayout.NORTH, this);
        layout.putConstraint(SpringLayout.EAST, convScrollPanel, -margineBordo, SpringLayout.EAST, this);
        layout.putConstraint(SpringLayout.SOUTH, convScrollPanel, -margineBordo, SpringLayout.NORTH, messPanel);
        layout.putConstraint(SpringLayout.SOUTH, convScrollPanel, -margineBordo, SpringLayout.NORTH, inviaButton);

        //Posiozione messaggio da inviare
        messPanel.setPreferredSize(new Dimension(0, 30));
        messPanel.setMargin(new Insets(0, 10, 0, 10));
        layout.putConstraint(SpringLayout.WEST, messPanel, margineBordo, SpringLayout.WEST, this);
        layout.putConstraint(SpringLayout.EAST, messPanel, -margineBordo, SpringLayout.WEST, inviaButton);
        layout.putConstraint(SpringLayout.SOUTH, messPanel, -margineBordo, SpringLayout.SOUTH, this);
        messPanel.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                int keyCode = e.getKeyCode();
                if(keyCode == KeyEvent.VK_ENTER) {
                    if(!messPanel.getText().isEmpty()) {
                        inviaMessaggio(username, messPanel.getText());
                    }
                }
            }
        });

        //Posizione pulsante
        inviaButton.setSize(100, 30);
        inviaButton.setPreferredSize(new Dimension(100, 30));
        layout.putConstraint(SpringLayout.EAST, inviaButton, -margineBordo, SpringLayout.EAST, this);
        layout.putConstraint(SpringLayout.SOUTH, inviaButton, -margineBordo, SpringLayout.SOUTH, this);
        inviaButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(!messPanel.getText().isEmpty()) {
                    inviaMessaggio(username, messPanel.getText());
                }
            }
        });

        layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, contenitore, 0, SpringLayout.HORIZONTAL_CENTER, this);
        layout.putConstraint(SpringLayout.VERTICAL_CENTER, contenitore, 0, SpringLayout.VERTICAL_CENTER, this);

        this.add(contenitore);

        this.pack();
        this.setVisible(true);

        //Messaggi all utente
        scrivi("\nBenvenuto " + username);
        scrivi("Per accedere scrivi nella chat 'localhost' oppure l'IP del server host.");
    }

    public void scrivi(String messaggio) {
        convPanel.setText(convPanel.getText() + messaggio + "\n");
    }

    public void inviaMessaggio(String nome, String messaggio) {
        if(serverIP == null) {
            serverIP = messaggio;
            connessioneDati = new SocketData(this);
            controller = new RicezioneMessaggi(connessioneDati, nome);
            sender = new InvioMessagi(connessioneDati, nome, null);
            sender.start();
            messPanel.setText("");
        }
        else if(connessioneDati != null && controller != null) {
            String msg = messaggio;
            scrivi("Io: "+msg);
            sender.invioMessaggio(msg);
            messPanel.setText("");
        }
    }
}
