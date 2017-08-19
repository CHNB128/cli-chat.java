package com.drake.nalson;

import java.io.*;
import java.net.*;

public class Client implements Runnable {

    private final int id;
    private String name;
    private Socket connection;
    private BufferedReader serverIn;
    private BufferedReader clientIn;
    private PrintWriter clientOut;
    private String cmd;
    private String msg;

    Client(Socket incoming, int clientId) {
        this.connection = incoming;
        this.id = clientId;
    }

    @Override
    public void run() {
        while (connection != null) {
            try {
                serverIn = new BufferedReader(
                        new InputStreamReader(
                                connection.getInputStream(),
                                "UTF-8"
                        )
                );
                clientIn = new BufferedReader(
                        new InputStreamReader(System.in)
                );
                clientOut = new PrintWriter(
                        new OutputStreamWriter(
                                connection.getOutputStream(),
                                "UTF-8"
                        ),
                        true
                );
                while ((cmd = serverIn.readLine()) != null) {
                    if (cmd.equalsIgnoreCase("exit")) break;
                    clientOut.println(cmd);
                    System.out.print(cmd);
                }
            } catch (IOException e) {
                System.out.println("Err : cannot initialize client stream \n :: " + e.getMessage());
            } finally {
                clientIn = null;
                serverIn = null;
                connection = null;
                System.out.println("Info : client (id : " + id + ") disconnected");
            }
        }
    }

    /*
    public static void main(String[] args) throws IOException {

        String host = "localhost";

        System.out.println("Welcome to Client side");

        Socket fromserver = null;

        if (args.length==0) {
            System.out.println("use: client hostname");
            //System.exit(-1);
        }

        System.out.println("Connecting to... "+ host);

        fromserver = new Socket(
                host,
                3000
        );
        BufferedReader in  = new BufferedReader(
                new InputStreamReader(fromserver.getInputStream())
        );
        PrintWriter out = new PrintWriter(
                fromserver.getOutputStream(),
                true
        );
        BufferedReader inu = new BufferedReader(
                new InputStreamReader(System.in)
        );

        String fuser,fserver;

        while ((fuser = inu.readLine())!=null) {
            out.println(fuser);
            fserver = in.readLine();
            System.out.println(fserver);
            if (fuser.equalsIgnoreCase("close")) break;
            if (fuser.equalsIgnoreCase("exit")) break;

        }

        out.close();
        in.close();
        inu.close();
        fromserver.close();
    }
    */
}