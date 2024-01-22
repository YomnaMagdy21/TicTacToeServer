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
            Logger.getLogger(ClientHandler.class.getName()).log(Level.SEVERE, null, ex);
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

    public void handleClient(Socket clientSocket) {
       
        try {
            InputStream inputStream = clientSocket.getInputStream();
            OutputStream outputStream = clientSocket.getOutputStream();

            System.out.println(clients);

            byte[] buffer = new byte[1024];
            int bytesRead = inputStream.read(buffer);
            String receivedMessage;
if (bytesRead != -1) {
     receivedMessage= new String(buffer, 0, bytesRead);
                 System.out.println("Received message from client: " + receivedMessage);
                 
            StringTokenizer tokenizer = new StringTokenizer(receivedMessage);
            String command = tokenizer.nextToken();
            String enteredUsername = tokenizer.nextToken();
            String enteredPassword = tokenizer.nextToken();
            String addInfo = tokenizer.nextToken();

    // Further processing of receivedMessage


            
            this.userString = enteredUsername;

            switch (command) {
                case "login":
                    //check if true 
                    if (DataAccessLayer.isValidUser(enteredUsername, enteredPassword)) {

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
                            String successMessageREQ = "userfound" + " " + enteredUsername + " " + "111"+" "+"444";
                            client.outputStream.write(successMessageREQ.getBytes());
                            outputStream.flush();

                            System.out.println("reeeeeeeeeeeeqfor" + enteredUsername);

                            System.out.println("successMessageREQ");

                        }

//                        String successMessageREQ = "req";
//                        outputStream.write(successMessageREQ.getBytes());
//                        outputStream.flush();
                    }

                    break;
                case "MOVE":
                    
                    System.out.println("movvvvve");
                      for (ClientHandler client : clients) {
                                           //    if (!client.getUsername().equalsIgnoreCase(enteredUsername)) {

                        
                            String str = "MOVE" + " " + enteredUsername + " " + enteredPassword+" "+addInfo;
                            client.outputStream.write(str.getBytes());
                            outputStream.flush();

//                                               }

                        

//                        String successMessageREQ = "req";
//                        outputStream.write(successMessageREQ.getBytes());
//                        outputStream.flush();
                    }
                    

                    
                    break;
//                case "req":
//                    System.out.println("REQ");
//                    String successMessageREQ = "REQ succeed";
//                    outputStream.write(successMessageREQ.getBytes());
//                    outputStream.flush();
//                    System.out.println("REQ");
//                    break;
                case "accept":
                    System.out.println("accccccept");
                    for (ClientHandler client : clients) {
//                        if (client.getUsername().equalsIgnoreCase(enteredUsername)) {
//                            String successMessageREQ = "UserAccpeted" + " " + enteredUsername + " " + "111";
//                            client.outputStream.write(successMessageREQ.getBytes());
//                            outputStream.flush();
//                            System.out.println("UserAccpeted" + enteredUsername);
//                        }
                        String successMessageREQ = "UserAccpeted" + " " + enteredUsername + " " + "111"+" "+"444";
                        client.outputStream.write(successMessageREQ.getBytes());
                        outputStream.flush();
                    }

                    break;
//                case "MOVE":
//                    System.out.println("UserX");
//                    for (ClientHandler client : clients) {
//                        if (client.getUsername().equalsIgnoreCase(enteredUsername)) {
//                            String successMessageREQ = "X" + " " + enteredUsername + " " + "111";
//                            client.outputStream.write(successMessageREQ.getBytes());
//                            outputStream.flush();
//                            System.out.println("X" + enteredUsername);
//                        }
//                        String successMessageREQ = "X" + " " + enteredUsername + " " + "111";
//                        client.outputStream.write(successMessageREQ.getBytes());
//                        outputStream.flush();
//                    }
//
//                    break;
//                    case "Accept":
//                    
//                    System.out.println("accccccceptttt");
//                    
//                    sendGameStartMessage(enteredUsername);
//                    for (ClientHandler client : clients) {
//                           String acceptMessage = "on game" + " " + enteredUsername + " " + "rawan";
//                            client.outputStream.write(acceptMessage.getBytes());
//                            outputStream.flush();
//                            System.out.println("accept " + enteredUsername);
//
//                            System.out.println("acceptMessage");
//
//                        
//                    }
//                    //set player on gaming
//                    break;
//                case "Reject": 
//                    for (ClientHandler client : clients) {
//                            String acceptMessage = "online" + " " + enteredUsername + " " + "111";
//                            client.outputStream.write(acceptMessage.getBytes());
//                            outputStream.flush();
//                            System.out.println("reject " + enteredUsername);
//
//                            System.out.println("rejectMessage");
//
//                        
//                    }
//                   break;
//                case "updateBoard":  
//                     for (ClientHandler client : clients) {
//                   if (client.getUsername().equalsIgnoreCase(enteredUsername)) {                
//                       try {
//                        String update = "updateBoard" + " " + enteredUsername + " " + "rawan";
//                         client.outputStream.write(update.getBytes());
//                         client.outputStream.flush();
//                          System.out.println("updateBoard");
//
//
//                     } catch (IOException ex) {
//                    Logger.getLogger(ClientHandler.class.getName()).log(Level.SEVERE, null, ex);
//                }
//
//            }
//        }         break;
//                case "response":
//                   // updateBoard();
//                      for (ClientHandler client : clients) {
//                   if (client.getUsername().equalsIgnoreCase(enteredUsername)) {                
//                       try {
//                        String update = "response" + " " + "X" + " " + "O";
//                         client.outputStream.write(update.getBytes());
//                         client.outputStream.flush();
//                          System.out.println("response");
//
//
//                     } catch (IOException ex) {
//                    Logger.getLogger(ClientHandler.class.getName()).log(Level.SEVERE, null, ex);
//                }
//
//            }
//        }       
//                    break;
//                case "twoPlayers":
//                    playGame(command);
//                     System.out.println("twoPlayers");
//
//                    break;
//                case "videoForPlayerX":
//                    playVideo(command);
//                    break;
//                case "videoForPlayerO":
//                    playVideo(command);
//                    break;
//                case "updateScore":
//                    DataAccessLayer.updateScore(new DTO(enteredUsername, enteredPassword, 0, "offline"));
//                    String updateScore = "update successfully";
//                    outputStream.write(updateScore.getBytes());
//                    outputStream.flush();
//                    System.out.println("updateScore");
//                    
//                    break;

//                case "MOVE":
//                    String buttonId = tokenizer.nextToken();
//                    String moveMessage = "MOVE " + this.getUsername() + " " + buttonId;
//                    // Send the move message to all clients
//                    sendToAllClients(moveMessage);
//                    break;

                default:
                    System.out.println("Unknown command: " + command);
                    break;
            }
            } else {
    System.out.println("can't receive agaiiiiiiiin");
    // Handle the case where bytesRead is -1 (end of stream)
}
        } catch (IOException ex) {
//            Logger.getLogger(.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
//            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

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

    private void sendToAllClients(String message) {
        for (ClientHandler client : clients) {
            try {
                client.outputStream.write(message.getBytes());
                client.outputStream.flush();
            } catch (IOException e) {
                // Handle the exception appropriately
                e.printStackTrace();
            }
        }
    }
     public void sendGameStartMessage(String username) {
    try {
        String gameStartMessage = "on game " + username+" 321";
        
        // Assuming you have outputStream as a field in your ClientHandler
        outputStream.write(gameStartMessage.getBytes());
        outputStream.flush();
    } catch (IOException e) {
        e.printStackTrace();
    }
}
    
    public void playGame(String str) throws IOException {
    
      InputStream inputStream = clientSocket.getInputStream();
            OutputStream outputStream = clientSocket.getOutputStream();

        byte[] buffer = new byte[1024];
            int bytesRead = inputStream.read(buffer);
            String receivedMessage = new String(buffer, 0, bytesRead);
            System.out.println("Received message from client: " + receivedMessage);

            StringTokenizer tokenizer = new StringTokenizer(receivedMessage);
            String command = tokenizer.nextToken(); 
            String enteredUsername = tokenizer.nextToken();

        if (command.equals("twoPlayers")) {

            for (ClientHandler handler : clients) {
                if (handler.getUsername().equals(enteredUsername)) {
                    try {
                       // handler.isPlaying = 1;
                       String playerX="twoPlayers"+" "+enteredUsername+" "+"X";
                      outputStream.write(playerX.getBytes());
                        //added "1" at the end of the first player to indicate that this is the first player to play.
                    } catch (IOException ex) {
                        Logger.getLogger(ClientHandler.class.getName()).log(Level.SEVERE, null, ex);
                    }

                }
                if (handler.getUsername().equals(enteredUsername)) {
                    try {
                       // handler.isPlaying = 1;
                       String playerO="twoPlayers"+" "+enteredUsername+" "+"O";
                           outputStream.write(playerO.getBytes());                      
                           //added "0" at the end of the first player to indicate that this is the second player to play.
                    } catch (IOException ex) {
                        Logger.getLogger(ClientHandler.class.getName()).log(Level.SEVERE, null, ex);
                    }

                }
              
            }
        } 
    }
    public void updateBoard() throws IOException{
         InputStream inputStream = clientSocket.getInputStream();
            OutputStream outputStream = clientSocket.getOutputStream();

        byte[] buffer = new byte[1024];
            int bytesRead = inputStream.read(buffer);
            String receivedMessage = new String(buffer, 0, bytesRead);
            System.out.println("Received message from client: " + receivedMessage);

            StringTokenizer tokenizer = new StringTokenizer(receivedMessage);
            String command = tokenizer.nextToken(); 
            String enteredUsername = tokenizer.nextToken();

        if (command.equals("response")) {

            for (ClientHandler handler : clients) {
                if (handler.getUsername().equals(enteredUsername)) {
                    try {
                       // handler.isPlaying = 1;
                       String playerX="response"+" "+enteredUsername+" "+" X";
                      outputStream.write(playerX.getBytes());
                        //added "1" at the end of the first player to indicate that this is the first player to play.
                    } catch (IOException ex) {
                        Logger.getLogger(ClientHandler.class.getName()).log(Level.SEVERE, null, ex);
                    }

                }
               
              
            }
        } 
        
    }
    
     public void playVideo(String str) throws IOException {
        InputStream inputStream = clientSocket.getInputStream();
            OutputStream outputStream = clientSocket.getOutputStream();

        byte[] buffer = new byte[1024];
            int bytesRead = inputStream.read(buffer);
            String receivedMessage = new String(buffer, 0, bytesRead);
            System.out.println("Received message from client: " + receivedMessage);

            StringTokenizer tokenizer = new StringTokenizer(receivedMessage);
            String command = tokenizer.nextToken(); 
            String enteredUsername = tokenizer.nextToken();
        System.out.println(str);
       // String[] arrString = str.split(" ");
        for (ClientHandler handler : clients) {
            System.out.println(handler.getUsername());
            if (handler.getUsername().equals(enteredUsername) && "videoForPlayerX".equals(enteredUsername)) {
                try {
                          outputStream.write("videoForWinner".getBytes());                      
                        } catch (IOException ex) {
                    Logger.getLogger(ClientHandler.class.getName()).log(Level.SEVERE, null, ex);
                }

            } else if (handler.getUsername().equals(enteredUsername) && "videoForPlayerO".equals(enteredUsername)) {
                try {
                        outputStream.write("videoForLooser".getBytes());                      

                } catch (IOException ex) {
                    Logger.getLogger(ClientHandler.class.getName()).log(Level.SEVERE, null, ex);
                }

            } else if (handler.getUsername().equals(enteredUsername) && "videoForPlayerX".equals(enteredUsername)) {
                try {
                        outputStream.write("videoForWinner".getBytes());                      

                } catch (IOException ex) {
                    Logger.getLogger(ClientHandler.class.getName()).log(Level.SEVERE, null, ex);
                }

            } else if (handler.getUsername().equals(enteredUsername) && "videoForPlayerO".equals(enteredUsername)) {
                try {
                        outputStream.write("videoForLooser".getBytes());                      

                } catch (IOException ex) {
                    Logger.getLogger(ClientHandler.class.getName()).log(Level.SEVERE, null, ex);
                }

            }

        }

    }

}
