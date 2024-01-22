/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tictactoeserver;

import DTO.DTO;
import Database.DataAccessLayer;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.StringTokenizer;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author HP
 */
public class ClientHandler extends Thread {

    ServerSocket server;
    Socket clientSocket;
    DataInputStream ear;
    PrintStream mouth;
    InputStream inputStream;
    OutputStream outputStream;

    private boolean serverRun = false;
    static Vector<ClientHandler> clients = new Vector<>();
    String userString;
    

    public ClientHandler(Socket clientSocket) {
        try {
            this.clientSocket = clientSocket;
            this.server = server;
            inputStream = clientSocket.getInputStream();
            outputStream = clientSocket.getOutputStream();
            clients.add(this);
            System.out.println(clients);

            this.start();
            // Initialize input and output streams
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void run() {
//        startServer();
        handleClient(clientSocket);
    }

    public void startServer() {
        new Thread(() -> {
            try {

                server = new ServerSocket(5005);
                serverRun = true;
                System.out.println("Server is listening on port 5005");
                while (serverRun) {
                    clientSocket = server.accept();
                    //   new ClientHandler(clientSocket);
                    System.out.println("Server has accepted a new client");
//                    InputStream inputStream = clientSocket.getInputStream();
//                    OutputStream outputStream = clientSocket.getOutputStream();
                    handleClient(clientSocket);

                }
            } catch (IOException ex) {
                ex.printStackTrace();
            } finally {
                try {
                    if (server != null && !server.isClosed()) {
                        server.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                serverRun = false;
                System.out.println("Server Stopped now !!");

            }

        }).start();
    }

    public void stopServer() {
        try {
            if (server != null && !server.isClosed()) {
                server.close();
                serverRun = false;
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

//    private void sendMoveToOpponent(String senderUsername, String move) {
//        for (ClientHandler client : clients) {
//            if (!client.getUsername().equalsIgnoreCase(senderUsername)) {
//                try {
//                    String moveMessage = "move"+" "+ move +" "+ "123";
//                    client.outputStream.write(moveMessage.getBytes());
//                    client.outputStream.flush();
//                } catch (IOException ex) {
//                    Logger.getLogger(ClientHandler.class.getName()).log(Level.SEVERE, null, ex);
//                }
//            }
//        }
//    }
//    
    public void handleClient(Socket clientSocket) {

        try {
            InputStream inputStream = clientSocket.getInputStream();
            OutputStream outputStream = clientSocket.getOutputStream();

            System.out.println(clients);

            byte[] buffer = new byte[1024];
            int bytesRead = inputStream.read(buffer);
            String receivedMessage = new String(buffer, 0, bytesRead);
            System.out.println("Received message from client: " + receivedMessage);

            StringTokenizer tokenizer = new StringTokenizer(receivedMessage);
            String command = tokenizer.nextToken();
            String enteredUsername = tokenizer.nextToken();
            String enteredPassword = tokenizer.nextToken();
            this.userString = enteredUsername;

            switch (command) {
                case "login":
                    //check if true 
                    DataAccessLayer.isValidUser(enteredUsername, enteredPassword);

                    DataAccessLayer.updateStatusnewtoOnline(enteredUsername);
                    System.out.println("LOGIN");
                    String successMessage = "login succeed";
                    outputStream.write(successMessage.getBytes());
                   /// ClientHandler clientHandler = new ClientHandler(clientSocket);
                  //  clientHandler.setUsername(enteredUsername);
                    for (ClientHandler client : clients) {
                        System.out.println("UserNAAAMe:::" + client.getUsername());
                    }
                    ArrayList<String> onlinePlayers = DataAccessLayer.getOnlineUsers();
                    String onlineUsersString = String.join(",", onlinePlayers);
                    System.out.println(onlineUsersString);

                    outputStream.write(onlineUsersString.getBytes());
                    outputStream.flush();
                    break;

                case "signup":
                    DataAccessLayer.addContact(new DTO(enteredUsername, enteredPassword, 0, "offline"));
                    String successMessagesignup = "signup succeed";
                    outputStream.write(successMessagesignup.getBytes());
                    outputStream.flush();
                    System.out.println("SIGNUP");
                    break;

                case "LOGOUT":
                    DataAccessLayer.updateStatusnewtoOffline(enteredUsername);
                    System.out.println("LOGOUT");
                    String successMessageLOGOUT = "LOGOUT succeed";
                    outputStream.write(successMessageLOGOUT.getBytes());
                    outputStream.flush();
                    System.out.println("LOGOUT");
                    break;
                case "invite":
                    System.out.println("inviiiiite");
                    for (ClientHandler client : clients) {
                        String clientUsername = client.getName();
                        String clientUsernamee = client.getUsername();
                        System.out.println("clientUsernamee" + clientUsernamee);

                        System.out.println(clientUsername);
                        System.out.println("tictactoeserver.ClientHandler.handleClient()" + client);
                        System.out.println("");
                        if (client.getUsername().equalsIgnoreCase(enteredUsername)) {
                            String successMessageREQ = "userfound" + " " + enteredUsername + " " + "111";
                            client.outputStream.write(successMessageREQ.getBytes());
                            outputStream.flush();
                            System.out.println("reeeeeeeeeeeeqfor" + enteredUsername);

                            System.out.println("successMessageREQ");
                            break;
                        }

//                        String successMessageREQ = "req";
//                        outputStream.write(successMessageREQ.getBytes());
//                        outputStream.flush();
                    }

                    break;
//                    case "move":
//                        // Handle the move received from the client
//                        //String move = tokenizer.nextToken();
//                        sendMoveToOpponent(enteredUsername, enteredUsername);
//                        break;
//                    case "accept":
//                        handleInvitationAcceptance(enteredUsername, tokenizer.nextToken());
//                        break;
//                case "req":
//                    System.out.println("REQ");
//                    String successMessageREQ = "REQ succeed";
//                    outputStream.write(successMessageREQ.getBytes());
//                    outputStream.flush();
//                    System.out.println("REQ");
//                    break;

                default:
                    System.out.println("Unknown command: " + command);
                    break;
            }
        } catch (IOException ex) {
//            Logger.getLogger(.class.getName()).log(Level.SEVERE, null, ex);
                ex.printStackTrace();
        } catch (SQLException ex) {
            ex.printStackTrace();
//            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
//    private void handleInvitationAcceptance(String acceptedUsername, String senderUsername) {
//        // Send a notification to the sender about the acceptance
//        notifySender(senderUsername, acceptedUsername);
//    }

//    private void notifySender(String senderUsername, String acceptedUsername) {
//        for (ClientHandler client : clients) {
//            if (client.getUsername().equalsIgnoreCase(senderUsername)) {
//                try {
//                    String notificationMessage = "accept"+" "+ acceptedUsername+" "+"123";
//                    client.outputStream.write(notificationMessage.getBytes());
//                    client.outputStream.flush();
//                } catch (IOException ex) {
//                    Logger.getLogger(ClientHandler.class.getName()).log(Level.SEVERE, null, ex);
//                }
//                break;
//            }
//        }
//    }

// Add a method to send moves to a specific client
    public void sendMove(String move) {
        // Send move to the client
    }

    public void setUsername(String username) {
        this.userString = username;
    }

    public String getUsername() {
        return userString;
    }
}
