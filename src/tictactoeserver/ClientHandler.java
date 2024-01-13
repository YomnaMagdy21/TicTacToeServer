/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tictactoeserver;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;

/**
 *
 * @author HP
 */
public class ClientHandler implements Runnable {
    private Socket clientSocket;
    private Server server;
    private DataInputStream ear;
    private PrintStream mouth;

    public ClientHandler(Socket clientSocket, Server server) {
        this.clientSocket = clientSocket;
        this.server = server;
        // Initialize input and output streams
    }

    @Override
    public void run() {
        // Handle client communication, e.g., sending and receiving moves
        // Handle client cleanup

    }

    // Add a method to send moves to a specific client
    public void sendMove(String move) {
        // Send move to the client
    }
}
