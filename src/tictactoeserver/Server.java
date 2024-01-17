/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tictactoeserver;

import DTO.DTO;
import Database.DataAccessLayer;
import java.io.DataInputStream;
import java.io.DataOutput;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.Vector;
import java.util.logging.*;
import static tictactoeserver.ClientHandler.clients;

/**
 *
 * @author HP
 */
public class Server extends Thread {

    ServerSocket server;
    Socket clientSocket;
    DataInputStream ear;
    PrintStream mouth;

    private boolean serverRun = false;

    public Server(){
        try {
            server = new ServerSocket(5005);
            start();
            System.out.println("Server is listening on port 5005");
            
        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
//    public void Server() {
//
//        try {
//            server = new ServerSocket(5005);
//            serverRun = true;
//            System.out.println("Server is listening on port 5005");
//            while (serverRun) {
//                clientSocket = server.accept();
//            //    new ClientHandler(clientSocket);
////                ClientHandler C= new ClientHandler(clientSocket);
////               C.handleClient(clientSocket);
//
//                this.start();
//
//            }
//
//        } catch (IOException ex) {
//            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }

//    public void startServer() {
//        new Thread(() -> {
//            try {
//                server = new ServerSocket(5005);
//                serverRun = true;
//                System.out.println("Server is listening on port 5005");
//                while (serverRun) {
//                    clientSocket = server.accept();
//                  //  new ClientHandler(clientSocket);
//                    ClientHandler C = new ClientHandler(clientSocket);
//                    C.handleClient(clientSocket);
//
//                    System.out.println("Server has accepted a new client");
////                    InputStream inputStream = clientSocket.getInputStream();
////                    OutputStream outputStream = clientSocket.getOutputStream();
//                    // ClientHandler.handleClient(clientSocket);
//                }
//            } catch (IOException ex) {
//                ex.printStackTrace();
//            } finally {
//                try {
//                    if (server != null && !server.isClosed()) {
//                        server.close();
//                    }
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//                serverRun = false;
//                System.out.println("Server Stopped now !!");
//
//            }
//
//        }).start();
//    }
//
//    public void stopServer() {
//        try {
//            if (server != null && !server.isClosed()) {
//                server.close();
//                serverRun = false;
//                System.out.println("Server Stopped now !!");
//            }
//        } catch (IOException ex) {
//            ex.printStackTrace();
//            System.out.println("thers is a problem on server");
//
//        }
//
//    }
//
//    public boolean serverRunning() {
//        return serverRun;
//    }

    @Override
    public void run() {
        while(true)
        {
            try {
                Socket clientSocket= server.accept();
                new ClientHandler(clientSocket);
            } catch (IOException ex) {
                try {
                    server.close();
                } catch (IOException ex1) {
                    Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex1);
                }
                Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    

}