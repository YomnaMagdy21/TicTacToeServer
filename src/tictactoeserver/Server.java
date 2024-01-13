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
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;
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

    private boolean serverRun = false;

    private Map<Socket, String> clientIPs = new HashMap<>();

    public void startServer() {
        new Thread(() -> {
            try {
                server = new ServerSocket(5005);
                serverRun = true;
                System.out.println("Server is listening on port 5005");

                while (true) {

                    while (serverRun) {
                        clientSocket = server.accept();
                        ear = new DataInputStream(clientSocket.getInputStream());
                        mouth = new PrintStream(clientSocket.getOutputStream());
                        String clientIP = ear.readLine();
                        clientIPs.put(clientSocket, clientIP);
                        System.out.println("Server has accepted a new client with IP: " + clientIP);
                        System.out.println("Server has accepted a new client");
                        InputStream inputStream = clientSocket.getInputStream();
                        OutputStream outputStream = clientSocket.getOutputStream();
                        byte[] buffer = new byte[1024];
                        int bytesRead = inputStream.read(buffer);
                        String receivedMessage = new String(buffer, 0, bytesRead);
                        System.out.println("Received message from client: " + receivedMessage);
                        StringTokenizer tokenizer = new StringTokenizer(receivedMessage);
                        String command = tokenizer.nextToken(); // "login"
                        String enteredUsername = tokenizer.nextToken();
                        String enteredPassword = tokenizer.nextToken();
                        System.out.println("Command: " + command);
                        System.out.println("Entered Password: " + enteredUsername);
                        System.out.println("Entered Username: " + enteredPassword);
                        switch (command) {
                            case "login":
                                DataAccessLayer.isValidUser(enteredUsername, enteredPassword);
                                DataAccessLayer.updateStatusnewtoOnline(enteredUsername);
                                System.out.println("LOGIN");
                                String successMessage = "login succeed";
                                outputStream.write(successMessage.getBytes());
                                outputStream.flush();

                                break;
                            case "signup":
                                DataAccessLayer.addContact(new DTO(enteredUsername, enteredPassword, 1, "offline"));
                                String successMessagesignup = "signup succeed";
                                outputStream.write(successMessagesignup.getBytes());
                                outputStream.flush();
                                System.out.println("SIGNUP");
                                break;
                            case "LOGOUT":
                                //DataAccessLayer.logout(new DTO(enteredUsername, enteredPassword, 1, "offline"));
                                DataAccessLayer.updateStatusnewtoOffline(enteredUsername);
                                System.out.println("LOGOUT");
                                String successMessageLOGOUT = "LOGOUT succeed";
                                outputStream.write(successMessageLOGOUT.getBytes());
                                outputStream.flush();
                                System.out.println("LOGOUT");
                                break;

                        }
                    }

                }
            } catch (IOException ex) {
                ex.printStackTrace();
            } catch (SQLException ex) {
                Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);

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

}

class ChatHandler extends Thread {

    DataInputStream dis;
    PrintStream ps;
    static Vector<ChatHandler> clientsVector = new Vector<ChatHandler>();

    public ChatHandler(Socket cs) throws IOException {
        try {
            dis = new DataInputStream(new DataInputStream(cs.getInputStream()));
            ps = new PrintStream(cs.getOutputStream());
            clientsVector.add(this);
            this.start();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void run() {
        while (true) {
            try {
                String msg = dis.readLine();
                for (ChatHandler ch : clientsVector) {
                    ch.ps.println(msg);
                }
            } catch (SocketException se) {
                // Handle the SocketException, e.g., log it
                System.err.println("Connection reset by client");
                break;
            } catch (Exception ex) {
                ex.printStackTrace();
            }

        }
    }
}
