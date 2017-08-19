package com.drake.nalson;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

class ChatServer {

    private final ServerSocket server;
    private final int port;
    private int clientId;
    private ArrayList clientList;
    private boolean drop;

    private Object _control;

    ChatServer(int port) throws IOException{
        this._control = this;
        this.port = port;
        this.server = new ServerSocket(port);
        this.clientId = 0;
        this.clientList = new ArrayList();

        this.start();
    }

    public void start() throws IOException {
        out("Listen on http://localhost:" + this.port + "\n");
        out("Connection wait...");
        while (!drop) {
            Socket incoming = server.accept();
            out("Client " + clientId + " connected.");
            ChatClient client = new ChatClient(incoming, this.clientId++);
            clientList.add(client);
            Thread t = new Thread(client);
            t.start();
        }
    }

    private void out(String string) {
        System.out.print("Info : " + string + "\n");
    }

    private void err(String string) {
        System.err.print("Err : " + string + "\n");
    }

}

