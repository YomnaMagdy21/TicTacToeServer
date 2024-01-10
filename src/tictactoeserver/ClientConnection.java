/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tictactoeserver;

import java.io.BufferedReader;
import java.io.*;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

/**
 *
 * @author HP
 */
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientConnection {

    public static void main(String[] args) {
        try {
            Socket socket = new Socket("localhost", 8888); // Connect to the server on localhost and port 8888
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

            // Read and print the welcome message from the server
            String welcomeMessage = in.readLine();
            System.out.println(welcomeMessage);

            // Determine if the client is Player 1 or Player 2 based on the welcome message
            boolean isPlayer1 = welcomeMessage.contains("Player 1");

            // Implement game logic here
            // For example, take turns sending and receiving moves until the game is over
            // Close the resources
            in.close();
            out.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
