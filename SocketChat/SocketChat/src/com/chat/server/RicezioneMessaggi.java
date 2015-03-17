package com.chat.server;

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
        while(connessioneDati.server.clientConnesso){
            info = connessioneDati.comunica();
            if(info != null) {
                connessioneDati.server.scrivi(info);
            }
        }
    }
}
