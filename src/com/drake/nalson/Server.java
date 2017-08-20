package com.drake.nalson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

public class Server {

    public static void main(String args[]) throws IOException {
        new Server(8080,true).start();
    }

    private final int port;
    private boolean close;
    private boolean debug;

    private ServerSocket server;

    private HashMap<Client, Thread> clientList;

    private BufferedReader serverIn;

    Server(int port, boolean debug) throws IOException {
        this.port = port;
        this.debug = debug;
        this.serverIn = new BufferedReader(
                new InputStreamReader(System.in)
        );
    }

    public void start() throws IOException {
        this.server = new ServerSocket(port);
        this.clientList = new HashMap<>();

        debug("Listen on : 127.0.0.1:" + this.port, System.out);

        new Thread(serverListen()).start();

        debug("Start listen keyboard input ", System.out);

        while (!close) {
            inputListen();
        }
    }

    public void status() {
        if(this.server == null) {
            System.out.println("Server : Off");
        } else {
            System.out.println("Server : On");
        }
    }

    public void stop() throws IOException {
        this.close = true;
        this.server.close();
        this.serverIn.close();
        this.clientList.clear();

        debug("Server shutdown...",System.out);

        System.exit(0);
    }

    private void debug(String msg, PrintStream method) {
        if(debug) {
            method.println(msg);
        }
    }

    private String clientList() {
        String result = "";
        for(HashMap.Entry<Client, Thread> entry : clientList.entrySet()) {
            result += entry.getKey().getName() + "\n";
        }
        return result;
    }

    private int newClientId() {
        String newId = "" + (int) Math.floor(10000 * Math.random());
        while (newId.length() < 4) {
            newId += "0";
        }
        return Integer.parseInt(newId);
    }

    private Runnable serverListen() {
        return () -> {

           debug("Server listen success",System.out);

           while (!this.close) {
               try {
                   Socket incomingConnection = this.server.accept();
                   int newId = this.newClientId();
                   Client client = new Client(incomingConnection, this,newId);
                   Thread thread = new Thread(client);
                   this.clientList.put(client, thread);
                   thread.start();

                   debug(newId + " : New connection success initial.",System.out);

               } catch (IOException e) {
                   e.printStackTrace();
               }
           }

           debug("Server listen close", System.out);
       };
    }

    public void inputListen() {
        try {
            String input = this.serverIn.readLine();
            switch (input) {
                case "status":
                    this.status();
                    break;
                case "list":
                    System.out.println(clientList());
                    break;
                case "close":
                    this.stop();
                    break;
                case "echo":
                    System.out.println("echo");
                    break;
                default:
                    System.out.println(input + " : not found");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    };

    public HashMap<Client, Thread> getClientList() {
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