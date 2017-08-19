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
        PrintWriter out = new PrintWriter(
                connection.getOutputStream(),
                true
        );
        BufferedReader inu = new BufferedReader(
                new InputStreamReader(
                        System.in
                )
        );

        new Thread(new Monitor(connection)).start();

        while ((userInput = inu.readLine())!=null) {
            out.println(userInput);
            if (userInput.equalsIgnoreCase("close")) break;
            if (userInput == "exit") {
                break;
            }
        }

        out.close();
        inu.close();
        connection.close();
    }
}
