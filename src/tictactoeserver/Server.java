/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tictactoeserver;

import Database.DataAccessLayer;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutput;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.logging.*;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.text.Text;

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
                new XOClientHandler(clientSocket);
                System.out.println("Client connected: " + clientSocket);

              //  ear = new DataInputStream(clientSocket.getInputStream());
              //  mouth = new PrintStream(clientSocket.getOutputStream());
                
                
                //  String clientIP = clientSocket.getInetAddress().getHostAddress();
//                String clientIP = ear.readLine();
//                clientIPs.put(clientSocket, clientIP);
//                System.out.println("Server has accepted a new client with IP: " + clientIP);
//
//                System.out.println("Server has accepted a new client");
//                //new ChatHandler(s);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    

}

class XOClientHandler extends Thread {
    private BufferedReader bufferedReader;
    private PrintStream printStream;
    private static Vector<XOClientHandler> clientsVector = new Vector<>();
    private char[][] board = new char[3][3];
BufferedReader reader;
    public XOClientHandler(Socket clientSocket) {
        try {
            bufferedReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            printStream = new PrintStream(clientSocket.getOutputStream());
            clientsVector.add(this);
// reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
//            PrintStream printStream = new PrintStream(clientSocket.getOutputStream());

            // Add the client's output stream to the list
            //outputStreams.add(printStream);

            // Send the initial state of the board to the client
            //sendBoardState(printStream);
            start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            
            while (true) {
                String move = bufferedReader.readLine();
                sendMessageToAll(move);
                if (move == null) {
                    break;
                }
                 System.out.println("Received from client " + move);
                
                
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
  
    private void sendMessageToAll(String msg) {
        System.out.println("Broadcasting message to all clients: " + msg);
        for(int i=0 ; i<clientsVector.size() ; i++)
      {
       clientsVector.get(i).printStream.println(msg);
      }
    }
}


