package com.drake.nalson;

import java.io.IOException;

public class In {

    public static void main(String args[]) throws IOException {
        Server server = new Server(8080);
        server.start();
    }
}
