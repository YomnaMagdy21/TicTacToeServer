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


    @Override
    public void start(Stage stage) throws Exception {

        Parent root = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
        Scene scene = new Scene(root);
        String css = this.getClass().getResource("pieStyle.css").toExternalForm();
        scene.getStylesheets().add(css);

        stage.setScene(scene);
        stage.show();
    }


    /**
     * @param args the command line arguments
     */
   
    
    public static void main(String[] args) throws IOException {
       launch(args);
    }
}


