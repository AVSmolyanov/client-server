package com.company;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    private static int port;
    private static ServerSocket serverSocket;
    private static Socket clientSocket;
    private static PrintWriter out;
    private static BufferedReader in;
    private static String name;

    static String ask(String question, String isFinish) throws IOException {
        String answer = "";
        out.println(String.format(question));
        out.println(String.format(isFinish));
        System.out.println("SERVER: " + question);
        answer = in.readLine();
        System.out.println("CLIENT: " + answer);
        return answer;
    }

    public static void main(String[] args) throws IOException {
        port = 8083;
        serverSocket = new ServerSocket(port);
        System.out.println("Server started.");
        while (true) {
            System.out.println("Awaiting the connection...");
            clientSocket = serverSocket.accept(); // ждем подключения
            out = new PrintWriter(clientSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            System.out.println(String.format("New connection accepted by port %d", clientSocket.getPort()));
            String answer = in.readLine();

            System.out.println("CLIENT: " + answer);
            name = ask("Write your name", "No");
            String data;
            do {
                data = ask("Are you child? (yes/no)", "No");
            } while (!("yes".equals(data) || "no".equals(data)));


            switch (data) {
                case "yes":
                    ask("Welcome to the kids area, " + name + "! Let's play!", "Yes");
                    break;
                default:
                    ask("Welcome to the adult zone, " + name + "! Have a good rest, or a good working day!", "Yes");
                    break;
            }
            System.out.println("Session finished. Connection closed");
            clientSocket.close();
        }
    }
}
