/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tictactoeserver;

/**
 *
 * @author HP
 */
import java.io.*;
import java.net.Socket;


public class GameHandler implements Runnable {

    private Socket playerX;
    private Socket playerO;
    private BufferedReader readerX;
    private PrintStream writerX;
    private BufferedReader readerO;
    private PrintStream writerO;

    public GameHandler(Socket playerX, Socket playerO) {
        try {
            this.playerX = playerX;
            this.playerO = playerO;
            this.readerX = new BufferedReader(new InputStreamReader(playerX.getInputStream()));
            this.writerX = new PrintStream(playerX.getOutputStream());
            this.readerO = new BufferedReader(new InputStreamReader(playerO.getInputStream()));
            this.writerO = new PrintStream(playerO.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }

        new Thread(this).start();
    }

    @Override
    public void run() {
        try {
            // Inform players about their symbols
            writerX.println("You are Player X");
            writerO.println("You are Player O");

            // Game loop
            while (true) {
                // Player X's turn
                makeMove(playerX, readerX, writerX, 'X');

                // Check for a win or draw
                if (checkWin()) {
                    broadcast("Player X wins!");
                    break;
                } else if (checkDraw()) {
                    broadcast("It's a draw!");
                    break;
                }

                // Player O's turn
                makeMove(playerO, readerO, writerO, 'O');

                // Check for a win or draw
                if (checkWin()) {
                    broadcast("Player O wins!");
                    break;
                } else if (checkDraw()) {
                    broadcast("It's a draw!");
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                playerX.close();
                playerO.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void makeMove(Socket player, BufferedReader reader, PrintStream writer, char symbol) throws IOException {
        // Communicate with the player to get their move
        writer.println("It's your turn. Enter your move (1-9): ");
        String move = reader.readLine();
        broadcast("Player " + symbol + " moves to position " + move);
        // You should implement the logic to update the Tic Tac Toe board here
    }

    private void broadcast(String message) {
        // Send a message to both players
        writerX.println(message);
        writerO.println(message);
    }

    private boolean checkWin() {
        // Implement your logic to check for a win
        return false;
    }

    private boolean checkDraw() {
        // Implement your logic to check for a draw
        return false;
    }
}


///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package tictactoeserver;
//
///**
// *
// * @author HP
// */
//import java.io.*;
//import java.net.Socket;
//
//public class GameHandler extends Thread {
//    private Socket player1Socket;
//    private Socket player2Socket;
//    private BufferedReader player1In;
//    private PrintWriter player1Out;
//    // Add similar variables for player 2
//
//    public GameHandler(Socket player1Socket, Socket player2Socket) {
//        this.player1Socket = player1Socket;
//        this.player2Socket = player2Socket;
//
//        try {
//            player1In = new BufferedReader(new InputStreamReader(player1Socket.getInputStream()));
//            player1Out = new PrintWriter(player1Socket.getOutputStream(), true);
//            // Initialize input and output streams for player 2
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    @Override
//    public void run() {
//        player1Out.println("You are Player 1");
//        // Send a similar message to Player 2
//        
//        // Handle game logic here
//        // You can use a loop to receive moves from players and send updates to both players
//        try {
//            player1Socket.close();
//            // Close player 2's socket
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//}
//

