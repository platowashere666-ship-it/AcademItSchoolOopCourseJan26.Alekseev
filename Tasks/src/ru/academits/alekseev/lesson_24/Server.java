package ru.academits.alekseev.lesson_24;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(34521)) {
            while (true) {
                Socket socket = serverSocket.accept();
                Thread thread = new Thread(() -> {
                    try (PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
                         BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
                        String line;

                        while ((line = reader.readLine()) != null) {
                            writer.println(line.toLowerCase());
                        }
                    } catch (IOException e) {
                        System.out.println("Input/Output error: " + e.getMessage());
                        System.exit(1);
                    }
                });

                thread.start();
            }
        } catch (IOException e) {
            System.err.println("Accept failed: " + e.getMessage());
            System.exit(1);
        }
    }
}
