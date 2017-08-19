package com.drake.nalson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class Monitor implements Runnable {

    private Socket connection;
    private BufferedReader inputStream;

    Monitor(Socket connectionLine) throws IOException {
        this.connection = connectionLine;
        this.inputStream = new BufferedReader(
                new InputStreamReader(
                        connectionLine.getInputStream()
                )
        );
    }

    @Override
    public void run() {
        while (true) {
            try {
                System.out.println(inputStream.readLine());
            } catch (IOException ignored) {

            }
        }
    }

}
