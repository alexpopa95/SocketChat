package com.chat.client;

/**
 * Created by Alexandru on 01/02/2015.
 */
public class RicezioneMessaggi extends Thread {

    private SocketData connessioneDati;
    private String nome;
    public RicezioneMessaggi(SocketData stream, String nome) {
        connessioneDati = stream;
        this.nome = nome;
    }

    public void run() {
        String info;
        while(connessioneDati.client.hostConnesso){
            info = connessioneDati.comunica();
            if(info != null) {
                connessioneDati.client.scrivi(info);
            }
        }
    }
}
