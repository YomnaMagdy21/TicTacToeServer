///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package tictactoeserver;
//
///**
// *
// * @author HP
// */
//import java.io.*;
//import java.net.Socket;
//
//public class GameHandler extends Thread {
//    private Socket player1Socket;
//    private Socket player2Socket;
//    private BufferedReader player1In;
//    private PrintWriter player1Out;
//    // Add similar variables for player 2
//
//    public GameHandler(Socket player1Socket, Socket player2Socket) {
//        this.player1Socket = player1Socket;
//        this.player2Socket = player2Socket;
//
//        try {
//            player1In = new BufferedReader(new InputStreamReader(player1Socket.getInputStream()));
//            player1Out = new PrintWriter(player1Socket.getOutputStream(), true);
//            // Initialize input and output streams for player 2
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    @Override
//    public void run() {
//        player1Out.println("You are Player 1");
//        // Send a similar message to Player 2
//        
//        // Handle game logic here
//        // You can use a loop to receive moves from players and send updates to both players
//        try {
//            player1Socket.close();
//            // Close player 2's socket
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//}
//
