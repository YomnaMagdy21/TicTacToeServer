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
    
    private boolean serverRun=false;
    
    private Map<Socket, String> clientIPs = new HashMap<>();

//    public static void main(String[] args) throws IOException {
//        new Server();
//    }

//    public Server() throws IOException {
//        System.out.println("creating new instance of the server");
//        try {
//            server = new ServerSocket(5005);
//            serverRun=true;
//            System.out.println("Server is listening on port 5005");
//            // ear = new DataInputStream(server.getInputStream());
//
//            while (serverRun) {
//                clientSocket = server.accept(); //number of clients return socket then create chat handler
//                ear = new DataInputStream(clientSocket.getInputStream());
//                mouth = new PrintStream(clientSocket.getOutputStream());
//                //  String clientIP = clientSocket.getInetAddress().getHostAddress();
//                String clientIP = ear.readLine();
//                clientIPs.put(clientSocket, clientIP);
//                System.out.println("Server has accepted a new client with IP: " + clientIP);
//
//                System.out.println("Server has accepted a new client");
//                //new ChatHandler(s);
//            }
//        } catch (IOException ex) {
//            ex.printStackTrace();
//        }
//    }
    
    public void startServer(){
        new Thread(() -> {
            try {
            server = new ServerSocket(5005);
            serverRun = true;
            System.out.println("Server is listening on port 5005");
                while (serverRun) {
                    clientSocket = server.accept();
                    ear = new DataInputStream(clientSocket.getInputStream());
                    mouth = new PrintStream(clientSocket.getOutputStream());
                    String clientIP = ear.readLine();
                    clientIPs.put(clientSocket, clientIP);
                    System.out.println("Server has accepted a new client with IP: " + clientIP);
                    System.out.println("Server has accepted a new client");
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }).start();
    }
    
    public void stopServer(){
         try {
            if(server != null && !server.isClosed()){
                server.close();
                serverRun=false;
                System.out.println("Server Stopped now !!");
            }
        } catch (IOException ex) {
            ex.printStackTrace();
            System.out.println("thers is a problem on server");
        }
        
    }
    public boolean serverRunning() {
        return serverRun;
    }

}
