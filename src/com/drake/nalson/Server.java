package com.drake.nalson;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server {

    private Server _control;

    private final int port;
    private boolean close;
    private int clientId;
    private ArrayList<Client> clientList;
    private ServerSocket server;
    private PrintWriter out;

    Server(int port) throws IOException {
        this.port = port;
        this._control = this;
    }

    public int start() throws IOException {
        this.server = new ServerSocket(port);
        this.clientList = new ArrayList<>();
        while (!close) {
            Socket incomingConnection = server.accept();
            Client client = new Client(incomingConnection,_control,clientId++);
            clientList.add(client);
            //infoClient("Client " + client.getId() + " connected.");
            new Thread(client).start();
        }
        return 0;
    }

    public void infoClient(String msg) {
        System.out.println(msg);
        for(Client client : this.clientList) {
            client.send(msg);
        }
    }

    public int stop() {

        return 0;
    }

    public ArrayList<Client> getClientList() {
        return clientList;
    }

    /*
    public static void main(String[] args) throws IOException {
        System.out.println("Welcome to Server side");

        BufferedReader in;
        PrintWriter out;
        ServerSocket servers = null;
        Socket fromclient = null;

        // create server socket
        try {
            servers = new ServerSocket(3000);
        } catch (IOException e) {
            System.out.println("Couldn't listen to port 4444");
            System.exit(-1);
        }

        try {
            System.out.print("Waiting for a client...");
            fromclient= servers.accept();
            System.out.println("Client connected");
        } catch (IOException e) {
            System.out.println("Can't accept");
            System.exit(-1);
        }

        in  = new BufferedReader(new
                InputStreamReader(fromclient.getInputStream()));
        out = new PrintWriter(fromclient.getOutputStream(),true);
        String input,output;

        System.out.println("Wait for messages");
        while ((input = in.readLine()) != null) {
            if (input.equalsIgnoreCase("exit")) break;
            out.println("S ::: "+input);
            System.out.println(input);
        }
        out.close();
        in.close();
        fromclient.close();
        servers.close();
    }
    */
}