package com.chat.client;

import java.io.*;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Alexandru on 01/02/2015.
 */
public class SocketData {
    public Client client;
    private Socket connessione=null;   // socket per la connessione
    public ObjectOutputStream OOS =null;  //stream di output
    public ObjectInputStream OIS =null;   // stream di input

    public SocketData(Client client) {
        this.client = client;
    }

    public String comunica() {
        String mess;
        String nome;
        String outputStr;
        try {
            nome = OIS.readObject().toString();
            mess = OIS.readObject().toString();
            client.hostName = nome;
            outputStr = nome+": "+mess;
            return outputStr;
        } catch (IOException e) {
            client.scrivi(client.hostName+" è uscito!");
            client.scrivi("SERVER NON DISPONIBILE");
            client.hostConnesso = false;
        } catch (ClassNotFoundException e) {
            client.scrivi("C'è stato un errore!");
            client.hostConnesso = false;
        }
        return null;
    }

    boolean setConnessione() {
        try {
            connessione=new Socket (client.serverIP,8084);
            client.scrivi("Connessione...");
            OOS=new ObjectOutputStream(connessione.getOutputStream());
            OIS=new ObjectInputStream(connessione.getInputStream());
            client.scrivi("Connesso!");
            client.scrivi("-----------------------------");
            return true;
        } catch (IOException ex) {
            client.scrivi("Server non acceso...\nRiprova!");
        }
        return false;
    }
}
