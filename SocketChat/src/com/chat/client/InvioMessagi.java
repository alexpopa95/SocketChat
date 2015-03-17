package com.chat.client;

import java.io.IOException;

/**
 * Created by Alexandru on 01/02/2015.
 */
public class InvioMessagi extends Thread {

    private SocketData connessioneDati;
    private String nome, messaggio;
    public InvioMessagi(SocketData stream, String nome, String messaggio) {
        connessioneDati = stream;
        this.nome = nome;
        this.messaggio = messaggio;
    }

    public void invioMessaggio(String messaggio){
        this.messaggio = messaggio;
    }

    public void run() {
        if(connessioneDati.setConnessione()) {
            connessioneDati.client.hostConnesso = true;
            connessioneDati.client.controller.start();
        }
        else {
            connessioneDati.client.hostConnesso = false;
            connessioneDati.client.serverIP = null;
        }
        while (connessioneDati.client.hostConnesso) {
            try {
                if (messaggio!=null) {
                    connessioneDati.OOS.writeObject(nome);
                    connessioneDati.OOS.writeObject(messaggio);
                    connessioneDati.OOS.flush();
                    messaggio=null;
                }
            }catch(IOException e) {
                connessioneDati.client.scrivi("C'è stato un errore, connessione persa.");
            }
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
