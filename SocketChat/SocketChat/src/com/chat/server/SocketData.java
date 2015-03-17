package com.chat.server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by Alexandru on 01/02/2015.
 */
public class SocketData {
    public Server server;
    private ServerSocket connessione=null;   // socket per la connessione
    private Socket con;
    public ObjectOutputStream OOS =null;  //stream di output
    public ObjectInputStream OIS =null;   // stream di input

    public SocketData(Server server) {
        this.server = server;
    }

    public String comunica() {
        String mess;
        String nome;
        String outputStr;
        try {
            System.out.println("Server Communico...");
            nome = OIS.readObject().toString();
            mess = OIS.readObject().toString();
            server.clientName = nome;
            outputStr = nome+": "+mess;
            System.out.println("Server Ricevuto: "+outputStr);
            return outputStr;
        } catch (IOException e) {
            server.scrivi(server.clientName +" è uscito!");
            server.clientConnesso = false;
        } catch (ClassNotFoundException e) {
            server.scrivi("C'è stato un errore!");
            server.clientConnesso = false;
        }
        return null;
    }

    boolean setConnessione() {
        try {
            server.scrivi("Connessione OIS corso...");
            connessione = new ServerSocket(8084);
            this.server.scrivi("In attesa di utenti...");
            con = connessione.accept();
            OIS = new ObjectInputStream(con.getInputStream());
            OOS = new ObjectOutputStream(con.getOutputStream());
            server.scrivi("Connesso!");
            server.scrivi("-----------------------------");
            return true;
        } catch (IOException ex) {
            server.scrivi("Nessuno si è connesso oppure c'è stato un errore.\nChiudi e riprova!");
        }
        return false;
    }
}
