/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tictactoeserver;

import Database.DataAccessLayer;
import java.io.DataInputStream;
import java.io.DataOutput;
import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;
import java.util.logging.*;

/**
 *
 * @author HP
 */
public class Server {

    ServerSocket server;
    Socket clientSocket;
    DataInputStream ear;
    PrintStream mouth;
    private Map<Socket, String> clientIPs = new HashMap<>();

    public static void main(String[] args) throws IOException {
        new Server();
    }

    public Server() throws IOException {
        try {
            server = new ServerSocket(5005);
            System.out.println("Server is listening on port 5005");
            // ear = new DataInputStream(server.getInputStream());

            while (true) {

                clientSocket = server.accept(); //number of clients return socket then create chat handler
                ear = new DataInputStream(clientSocket.getInputStream());
                mouth = new PrintStream(clientSocket.getOutputStream());
                //  String clientIP = clientSocket.getInetAddress().getHostAddress();
                String clientIP = ear.readLine();
                clientIPs.put(clientSocket, clientIP);
                System.out.println("Server has accepted a new client with IP: " + clientIP);

                System.out.println("Server has accepted a new client");
                //new ChatHandler(s);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

}
