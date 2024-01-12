///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package tictactoeserver;
//
//import java.io.IOException;
//import java.net.ServerSocket;
//import java.net.Socket;
//import java.util.ArrayList;
//import java.util.List;
//
///**
// *
// * @author HP
// */
//public class servertic {
//    private ServerSocket server;
//    private List<ClientHandler> clients = new ArrayList<>();
//
//    public static void main(String[] args) throws IOException {
//        new servertic();
//    }
//
//    public servertic() throws IOException {
//        try {
//            server = new ServerSocket(5005);
//            System.out.println("Server is listening on port 5005");
//
//            while (true) {
//                Socket clientSocket = server.accept();
//                ClientHandler handler = new ClientHandler(clientSocket,this);
//                clients.add(handler);
//                new Thread(handler).start();
//            }
//        } catch (IOException ex) {
//            ex.printStackTrace();
//        } finally {
//            // Handle server cleanup
//        }
//    }
//
//    // Add a method to broadcast moves to all connected clients
//    public void broadcastMove(String move) {
//        for (ClientHandler client : clients) {
//            client.sendMove(move);
//        }
//    }
//}
//
