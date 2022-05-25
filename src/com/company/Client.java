package com.company;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client {

    public static void main(String[] args) throws IOException {

        try {
            Socket socket = new Socket("127.0.0.1", 8083);
            try (BufferedReader in = new BufferedReader(
                    new InputStreamReader(socket.getInputStream()));
                 PrintWriter out = new PrintWriter(
                         new OutputStreamWriter(socket.getOutputStream()), true);
                 Scanner scanner = new Scanner(System.in)) {

                System.out.print("Write something to start conversation: ");
                out.println(String.format(scanner.nextLine()));
                String answer;
                while (true) {
                    String question = in.readLine();
                    String isFinish = in.readLine();
                    System.out.println("SERVER: "+question);
                    switch (isFinish) {
                        case "Yes":
                            answer = "end";
                            break;
                        default:
                            System.out.print("YOU: ");
                            answer = scanner.nextLine();
                    }
                    if ("end".equals(answer)) {
                        break;
                    }
                    out.println(String.format(answer));
                }


//            String msg;
//            System.out.println("Write something to start conversationYou ('end' to finish):");
//            msg = scanner.nextLine();
//            out.println(msg);
//
//            while (true) {
//
//                System.out.println("You ('end' to finish):");
//                msg = scanner.nextLine();
//                out.println(msg);
//                if ("end".equals(msg)) {
//                    break;
//                }
//                System.out.println("SERVER: " + in.readLine());
//            }


            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
