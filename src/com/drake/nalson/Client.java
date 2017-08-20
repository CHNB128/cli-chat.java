package com.drake.nalson;

import java.io.*;
import java.net.*;
import java.util.Date;
import java.util.HashMap;

public class Client implements Runnable {

    private final int id;
    private String name;
    private final String time;
    private int groupId;
    private final Socket connection;
    private final Server server;
    private BufferedReader serverIn;
    private PrintWriter clientOut;
    private String cmd;

    Client(Socket incomingConnection,Server server, int clientId) {
        this.connection = incomingConnection;
        this.server = server;
        this.id = clientId;
        this.name = "";
        this.time = new Date().toString();
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
                clientOut = new PrintWriter(
                        connection.getOutputStream(),
                        true
                );
                while ((cmd = serverIn.readLine()) != null) {
                    if(cmd.startsWith("@")) {
                        String[] tempFormat = cmd.split(" ");
                        switch (tempFormat[0]) {
                            case "@setname":
                                setName(tempFormat[1]);
                                break;
                            case "@setgroup":
                                setGroupId(Integer.parseInt(tempFormat[1]));
                                break;
                        }
                        continue;
                    }
                    sendAll(getName() + " : " + cmd);
                }
            } catch (IOException e) {
                System.out.println("Err : " + e.getMessage());
            } finally {
                try {
                    serverIn.close();
                    connection.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                //server.infoClient("Info : client (id : " + id + ") disconnected");
            }
        }
    }

    public void send(String msg){
        try {
            this.clientOut.println(msg);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    public void sendAll(String msg) {
        for(HashMap.Entry<Client, Thread> entry : server.getClientList().entrySet()) {
            if(entry.getKey().id != this.id && entry.getKey().groupId == this.groupId) {
                entry.getKey().send(msg);
            }
        }

    }

    private void setName(String newName) {
        this.name = newName;
    }

    private void setGroupId(int newGroupId) {
        this.groupId = newGroupId;
    }

    public String getName() {
        if(this.name.length() == 0) {
            return String.valueOf(this.id);
        } else {
            return name;
        }
    }

    public String getTime() {
        return time;
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