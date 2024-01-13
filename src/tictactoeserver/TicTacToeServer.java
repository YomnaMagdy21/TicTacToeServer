/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tictactoeserver;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author HP
 */
public class TicTacToeServer extends Application {
    private Server server;

    @Override
    public void start(Stage stage) throws Exception {
//        FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLDocument.fxml"));
//        Parent root = loader.load();
//        
//        FXMLDocumentController controller = loader.getController();
          Parent root = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
//        controller.startBtn.setOnAction(event -> startServer());
//        controller.stopBtn.setOnAction(event -> stopServer());
            //server = controller.getServer();


        Scene scene = new Scene(root);
        String css = this.getClass().getResource("pieStyle.css").toExternalForm();
        scene.getStylesheets().add(css);

        stage.setScene(scene);
        stage.show();
    }


    /**
     * @param args the command line arguments
     */
   


//    private void startServer() {
//        if (server == null || !server.serverRunning()) {
//            try {
//                server = new Server();
//                System.out.println("start server method!!");
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//    }
//    private void stopServer() {
//        if (server != null && server.serverRunning()) {
//            server.stopServer();
//            System.out.println("stop server method!!");
//        }
//    }
    
    public static void main(String[] args) throws IOException {
       launch(args);
        //new Server();
    }
}


