package com.company;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    private static String name;

    static String ask(BufferedReader in, PrintWriter out, String question, String isFinish) throws IOException {
        String answer = "";
        out.println(String.format(question));
        out.println(String.format(isFinish));
        System.out.println("SERVER: " + question);
        answer = in.readLine();
        System.out.println("CLIENT: " + answer);
        return answer;
    }

    public static void main(String[] args) throws IOException {
        int port = 8083;
        try (ServerSocket serverSocket = new ServerSocket(port);) {
            System.out.println("Server started.");
            while (true) {
                System.out.println("Awaiting the connection...");
                try (
                        Socket clientSocket = serverSocket.accept(); // ждем подключения
                        PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                        BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));) {

                    System.out.println(String.format("New connection accepted by port %d", clientSocket.getPort()));
                    String answer = in.readLine();

                    System.out.println("CLIENT: " + answer);
                    name = ask(in, out, "Write your name", "No");
                    String data;
                    do {
                        data = ask(in, out, "Are you child? (yes/no)", "No");
                        if (data == null) {
                            System.out.println("Incorrect answer from Client or it's session terminated.");
                            break;
                        }
                    } while (!("yes".equals(data) || "no".equals(data)));

                    switch (data) {
                        case "yes":
                            ask(in, out, "Welcome to the kids area, " + name + "! Let's play!", "Yes");
                            break;
                        default:
                            ask(in, out, "Welcome to the adult zone, " + name + "! Have a good rest, or a good working day!", "Yes");
                            break;
                    }
                    System.out.println("Session finished. Connection closed");
                    clientSocket.close();
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
