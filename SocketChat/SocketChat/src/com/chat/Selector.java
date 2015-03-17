package com.chat;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Created by Alexandru on 01/02/2015.
 */
public class Selector extends JFrame {

    public static final String nome = "Selezione Chat - Client/Server";

    public static final int altezza = 150;
    public static final int larghezza = 200;

    private final int margineBordo = 5;
    private final int margineBordoH = 15;

    JTextField insNome;
    JLabel insNomeLab;
    JLabel infoLab;
    JButton client;
    JButton server;
    JSeparator separatore;

    public Selector() {
        init();
    }

    public void init() {
        insNome = new JTextField();
        insNomeLab = new JLabel("Username:");
        infoLab = new JLabel("Accedi come:");
        client = new JButton("Client");
        server = new JButton("Server");
        separatore = new JSeparator(JSeparator.HORIZONTAL);

        new JFrame(nome);
        setPreferredSize(new Dimension(larghezza, altezza));
        setMinimumSize(new Dimension(larghezza, altezza));
        setMaximumSize(new Dimension(larghezza, altezza));
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        SpringLayout layout = new SpringLayout();
        JPanel contenitore = new JPanel(layout);
        contenitore.setBackground(Color.gray);

        contenitore.add(insNomeLab);
        contenitore.add(insNome);
        contenitore.add(infoLab);
        contenitore.add(separatore);
        contenitore.add(client);
        contenitore.add(server);

        layout.putConstraint(SpringLayout.WEST, insNomeLab, margineBordoH, SpringLayout.WEST, this);
        layout.putConstraint(SpringLayout.NORTH, insNomeLab, margineBordo, SpringLayout.NORTH, this);

        layout.putConstraint(SpringLayout.NORTH, insNome, margineBordo, SpringLayout.NORTH, this);
        layout.putConstraint(SpringLayout.WEST, insNome, margineBordoH, SpringLayout.EAST, insNomeLab);
        layout.putConstraint(SpringLayout.EAST, insNome, -margineBordoH, SpringLayout.EAST, this);

        separatore.setBackground(Color.white);
        layout.putConstraint(SpringLayout.WEST, separatore, 0, SpringLayout.WEST, this);
        layout.putConstraint(SpringLayout.NORTH, separatore, 10, SpringLayout.SOUTH, insNomeLab);
        layout.putConstraint(SpringLayout.EAST, separatore, 0, SpringLayout.EAST, this);

        layout.putConstraint(SpringLayout.WEST, infoLab, margineBordoH, SpringLayout.WEST, this);
        layout.putConstraint(SpringLayout.NORTH, infoLab, margineBordo, SpringLayout.SOUTH, separatore);
        layout.putConstraint(SpringLayout.EAST, infoLab, -margineBordoH, SpringLayout.EAST, this);

        layout.putConstraint(SpringLayout.WEST, client, margineBordoH, SpringLayout.WEST, this);
        layout.putConstraint(SpringLayout.NORTH, client, margineBordo, SpringLayout.SOUTH, infoLab);
        layout.putConstraint(SpringLayout.EAST, client, -margineBordoH, SpringLayout.EAST, this);

        layout.putConstraint(SpringLayout.WEST, server, margineBordoH, SpringLayout.WEST, this);
        layout.putConstraint(SpringLayout.NORTH, server, margineBordo, SpringLayout.SOUTH, client);
        layout.putConstraint(SpringLayout.EAST, server, -margineBordoH, SpringLayout.EAST, this);

        JPanel vuoto = new JPanel();
        layout.putConstraint(SpringLayout.WEST, vuoto, margineBordoH, SpringLayout.WEST, this);
        layout.putConstraint(SpringLayout.NORTH, vuoto, margineBordo, SpringLayout.SOUTH, server);
        layout.putConstraint(SpringLayout.EAST, vuoto, -margineBordoH, SpringLayout.EAST, this);
        layout.putConstraint(SpringLayout.SOUTH, vuoto, -margineBordoH, SpringLayout.SOUTH, this);

        layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, contenitore, 0, SpringLayout.HORIZONTAL_CENTER, this);
        layout.putConstraint(SpringLayout.VERTICAL_CENTER, contenitore, 0, SpringLayout.VERTICAL_CENTER, this);

        add(contenitore);
        aggiungiListeners();
        pack();
        setVisible(true);
    }

    private void aggiungiListeners() {
        client.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                String username = insNome.getText();
                if(!username.isEmpty()) {
                    Selector.getFrames()[0].dispose();
                    new com.chat.client.Client(username);
                }
                else {
                    insNomeLab.setForeground(Color.red);
                }
            }
        });
        server.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                String username = insNome.getText();
                if(!username.isEmpty()) {
                    Selector.getFrames()[0].dispose();
                    new com.chat.server.Server(username);
                }
                else {
                    insNomeLab.setForeground(Color.red);
                }
            }
        });
    }

    public static void main(String[] args) {
        new Selector();
    }
}
