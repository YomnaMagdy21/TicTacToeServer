/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Database;

import dto.Player;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.derby.jdbc.ClientDriver;

/**
 *
 * @author HP
 */
public class DataAccessLayer {
    
   public List<Player> getAll(){
       List<Player> players = new ArrayList<Player>();
       Connection connection = null;
       try {
            DriverManager.registerDriver(new ClientDriver());
            connection = DriverManager.getConnection("jdbc:derby://localhost:1527/TicTacToe","root","root");
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM PLAYER");
            ResultSet rst = preparedStatement.executeQuery();
            while (rst.next()) {
               String username = rst.getString(1);
               String password = rst.getString(2);
               int score = rst.getInt(3);
               String status = rst.getString(4);
               Player cont = new Player(username, password, score, status);
               players.add(cont);
            }
       } catch (SQLException ex) {
           Logger.getLogger(DataAccessLayer.class.getName()).log(Level.SEVERE, null, ex);
       } finally{
           try {
               connection.close();
           } catch (SQLException ex) {
               Logger.getLogger(DataAccessLayer.class.getName()).log(Level.SEVERE, null, ex);
           }
       }
       return players;
   } 
}
