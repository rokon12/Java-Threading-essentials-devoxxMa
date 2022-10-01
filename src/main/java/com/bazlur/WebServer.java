package com.bazlur;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class WebServer {
    public static void main(String[] args) throws IOException {
        try (var server = new ServerSocket(8080)) {
            while (true) {
                var socket = server.accept();
                handleRequest(socket);
            }
        }
    }

    private static void handleRequest(Socket socket) {
        try (var in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             var out = new PrintWriter(socket.getOutputStream(), true)) {
            String line;
            while ((line = in.readLine()) != null) {
                if (line.endsWith("quit")) {
                    out.println("Goodbye!");
                    socket.close();
                }

                String upperCase = line.toUpperCase();
                out.println(upperCase);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
