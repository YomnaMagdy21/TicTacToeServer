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

        } catch (IOException ex) {
            Logger.getLogger(ClientHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void run() {
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
                    System.out.println("Server has accepted a new client");
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
            String symol = tokenizer.nextToken();

            this.userString = enteredUsername;

            switch (command) {
                case "login":
                    if (DataAccessLayer.isValidUser(enteredUsername, enteredPassword)) {

                        DataAccessLayer.updateStatusnewtoOnline(enteredUsername);
                        System.out.println("LOGIN");
                        String successMessage = "login succeed";
                        outputStream.write(successMessage.getBytes());
                        for (ClientHandler client : clients) {
                            System.out.println("UserNAAAMe:::" + client.getUsername());
                        }
                        ArrayList<String> onlinePlayers = DataAccessLayer.getOnlineUsers();
                        String onlineUsersString = String.join(",", onlinePlayers);
                        System.out.println(onlineUsersString);

                        outputStream.write(onlineUsersString.getBytes());
                        outputStream.flush();
                    } else {
                        String failureMessage = "login failed";
                        outputStream.write(failureMessage.getBytes());
                        outputStream.flush();
                    }
                    break;

                case "signup":
                    DataAccessLayer.addContact(new DTO(enteredUsername, enteredPassword, 0, "offline"));
                    String successMessagesignup = "signup succeed";
                    outputStream.write(successMessagesignup.getBytes());
                    outputStream.flush();
                    System.out.println("SIGNUP");
                    break;

                case "LOGOUT":
                    System.out.println("LOGOUT");

                    DataAccessLayer.updateStatusnewtoOffline(enteredUsername);
                    String successMessageLOGOUT = "LOGOUT succeed";
                    outputStream.write(successMessageLOGOUT.getBytes());
                    outputStream.flush();
                    System.out.println("LOGOUT");
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

                        }
                    }

                    break;

                case "accept":
                    System.out.println("accccccept");
                    for (ClientHandler client : clients) {

                        String successMessageREQ = "UserAccpeted" + " " + enteredUsername + " " + "111";
                        client.outputStream.write(successMessageREQ.getBytes());
                        outputStream.flush();
                    }

                    break;

                case "MOVEX":
                    System.out.println("this is symbol X " + "  " + symol);
                    for (ClientHandler client : clients) {
                        if (client.getUsername().equalsIgnoreCase(enteredUsername)) {
                            String moveMessage = "MOVEXTO" + " " + enteredUsername + " " + enteredPassword + " " + symol;
                            client.outputStream.write(moveMessage.getBytes());
                            outputStream.flush();
                            System.out.println("MOVEXTO" + " " + enteredUsername);
                        }
                    }

                    break;
                case "MOVEO":
                    System.out.println("this is symbol O" + "  " + symol);
                    for (ClientHandler client : clients) {
                        if (client.getUsername().equalsIgnoreCase(enteredUsername)) {
                            String moveMessage = "MOVEOTO" + " " + enteredUsername + " " + enteredPassword + " " + symol;
                            client.outputStream.write(moveMessage.getBytes());
                            outputStream.flush();
                            System.out.println("MOVEOTO" + " " + enteredUsername);
                        }
                    }

                    break;

                case "MOVE":
                    for (ClientHandler client : clients) {
                        String moveMessage = "MOVE " + enteredUsername + " " + enteredPassword;
                        client.outputStream.write(moveMessage.getBytes());
                        outputStream.flush();
                    }

                default:
                    System.out.println("Unknown command: " + command);
                    break;
            }
        } catch (IOException ex) {
            System.err.println(ex);
        } catch (SQLException ex) {
            System.err.println(ex);
        }
    }

    public void setUsername(String username) {
        this.userString = username;
    }

    public String getUsername() {
        return userString;
    }

    private void sendToAllClients(String message) {
        for (ClientHandler client : clients) {
            try {
                client.outputStream.write(message.getBytes());
                client.outputStream.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
