package com.drake.nalson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ConnectException;
import java.net.Socket;

public class Out {

    public static void main(String[] args) throws IOException {

        String userInput;

        String host = "localhost";
        int port = 8080;

        if(args.length > 0) {
            host = args[1];
            port = Integer.parseInt(args[2]);
        }

        Socket connection = null;

        try {
            connection = new Socket(
                    host,
                    port
            );
        } catch (ConnectException e) {
            System.out.println("Cannot connect to the server : " + host + ":" + port + "\n" + e.getMessage());
            System.exit(1);
        }
        PrintWriter serverOut = new PrintWriter(
                connection.getOutputStream(),
                true
        );
        BufferedReader clientIn = new BufferedReader(
                new InputStreamReader(
                        System.in
                )
        );

        new Thread(new Monitor(connection)).start();

        while ((userInput = clientIn.readLine())!=null) {
            serverOut.println(userInput);
        }

        serverOut.close();
        clientIn.close();
        connection.close();
    }
}
