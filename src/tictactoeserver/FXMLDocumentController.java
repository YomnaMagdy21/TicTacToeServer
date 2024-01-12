/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tictactoeserver;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.text.Text;

/**
 *
 * @author HP
 */
public class FXMLDocumentController implements Initializable {
    @FXML
    private Text txtServer;

    @FXML
    private Button startBtn;

    @FXML
    private Button stopBtn;
    
    @FXML
    private Label label;
    
    @FXML
    private PieChart pieChart;
    private Server server;
    private Service<Void> stopService;
    
//    @FXML
//    private void handleButtonAction(ActionEvent event) {
//        System.out.println("You clicked me!");
//        label.setText("Hello World!");
//    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        ObservableList<PieChart.Data> pChartData=
            FXCollections.observableArrayList(
            new PieChart.Data("offline",50),
            new PieChart.Data("ongaming",30),
            new PieChart.Data("online",50));
            pieChart.setData(pChartData);
            pieChart.setStartAngle(90);         
    } 
    
    @FXML
    private void startServer(ActionEvent event){
        if (server == null || !server.serverRunning()) {
            if (server == null) {
                server = new Server();
            }
            new Thread(() -> {
                server.startServer();
                System.out.println("Server is Running Now");
                txtServer.setText("Server is Running Now");
                Platform.runLater(() -> startBtn.setText("Stop"));
            }).start();
        } else {
            new Thread(() -> {
                server.stopServer();
                Platform.runLater(() -> {
                    txtServer.setText("Server is Stopped Now");
                    startBtn.setText("Start");
                });
            }).start();
        }
    }
    
//    @FXML
//    private void stopServer(ActionEvent event){
//        if(server!=null && server.serverRunning()){
//            server.stopServer();
//            txtServer.setText("Server is Stopped Now");
//        }else {
//            txtServer.setText("Server is not running");
//            System.out.println("server already not running");
//        }
//    }
    public Server getServer() {
        return server;
    }
}
