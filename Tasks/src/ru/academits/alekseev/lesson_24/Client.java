package ru.academits.alekseev.lesson_24;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {
    public static void main(String[] args) {
        try (Socket socket = new Socket("127.0.0.1", 34521);
             PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
            String[] strings = {"HELLO", "MY", "NAME", "iS", "joNas"};

            for (String string : strings) {
                System.out.println(string);
                writer.println(string);
            }

            String line;

            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            System.err.println("Accept failed: " + e.getMessage());
            System.exit(1);
        }
    }
}
