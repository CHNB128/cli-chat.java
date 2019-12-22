package com.daren.cli.chat;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

class ChatClient_test implements Runnable {

    public ChatClient_test(Socket incoming, int i) {
    }

    @Override
    public void run() {

    }
/*
    private boolean close;
    private final int id;
    private Socket connection;
    private String name;
    private Scanner scanner;
    private String msg;
    private PrintWriter out;

    public ChatClient_test(Socket incoming, int clientId) {
        this.connection = incoming;
        this.id = clientId;
    }


    @Override
    public void run() {
        while (connection != null) {
            try {
                scanner = new Scanner(
                        new InputStreamReader(
                                connection.getInputStream(),
                                "UTF-8"
                        )
                );
                out = new PrintWriter(
                        new OutputStreamWriter(
                                connection.getOutputStream(),
                                "UTF-8"
                        ),
                        true
                );
                out.println("Welcome!");
                System.out.print("Input : your name ?..");
                this.name = new Scanner(new InputStreamReader(System.in)).nextLine();

                while (!close && scanner.hasNextLine()) {
                    msg = scanner.nextLine();
                    //Control._control.sendAll();
                    System.out.print(msg);
                }
            } catch (IOException e) {
                System.out.print("Err : cannot initialize client stream \n :: " + e.getMessage());
            } finally {
                scanner = null;
                close = true;
                connection = null;
                System.out.print("Info : client (id : " + id + ") disconnected");
            }
        }
    }

    public void Send(String msg) {
        out.print(msg);
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Object getConnection() {
        return connection;
    }
    */
}