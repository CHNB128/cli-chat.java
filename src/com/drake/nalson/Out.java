package com.drake.nalson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Out {

    public static void main(String[] args) throws IOException {

        Socket connection = new Socket(
                "localhost",
                8080
        );
        BufferedReader in  = new BufferedReader(
                new InputStreamReader(
                        connection.getInputStream()
                )
        );
        PrintWriter out = new PrintWriter(
                connection.getOutputStream(),
                true
        );
        BufferedReader inu = new BufferedReader(
                new InputStreamReader(
                        System.in
                )
        );

        String userInput,serverOutput;

        while ((userInput = inu.readLine())!=null) {
            out.println(userInput);
            serverOutput = in.readLine();
            System.out.println(serverOutput);
            if (userInput.equalsIgnoreCase("close")) break;
            if (userInput.equalsIgnoreCase("exit")) break;
        }

        out.close();
        in.close();
        inu.close();
        connection.close();
    }
}
