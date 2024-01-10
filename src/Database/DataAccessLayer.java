/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Database;
import dto.DTO;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import org.apache.derby.jdbc.ClientDriver ;
import dto.Player;
import dto.PlayerDTO;
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
        public static int addContact(DTO contact) throws SQLException{
        int result = 0 ; 
        DriverManager.registerDriver(new ClientDriver());
        Connection con = DriverManager.getConnection("jdbc:derby://localhost:1527/TicTacToe", "habiba", "habiba");
        PreparedStatement s1 = con.prepareStatement( "INSERT INTO Player (USERNAME, PASSWORD, SCORE, STATUS) VALUES ( ? , ? , ? , ? )" );
        s1.setString(1, contact.getUsername());
        s1.setString(2, contact.getPassword());
        s1.setString(3, contact.getStatus());
        s1.setInt(4, contact.getScore());
        result  = s1.executeUpdate();
        s1.close();
        con.close();
        return result; } 
    public static boolean checkIfPlayerExist(PlayerDTO player) throws SQLException{
        boolean exist;
        DriverManager.registerDriver(new ClientDriver());
        Connection connection = DriverManager.getConnection("jdbc:derby://localhost:1527/TicTacToe","root","root");
        PreparedStatement prepareStatement = connection.prepareStatement("SELECT * FROM Player WHERE USERNAME=? " , ResultSet.TYPE_SCROLL_SENSITIVE , ResultSet.CONCUR_READ_ONLY);
        prepareStatement.setString(1, player.getUsername());
        ResultSet result = prepareStatement.executeQuery();
        if(result != null){
            exist = true;
        }else{
            exist=false;
        }
        prepareStatement.close();
        connection.close();
        return exist;
    }
    
 
    public static int updateStatus(PlayerDTO player) throws SQLException{
        int result;
        DriverManager.registerDriver(new ClientDriver());
        Connection connection = DriverManager.getConnection("jdbc:derby://localhost:1527/TicTacToe","root","root");
        PreparedStatement prepareStatement = connection.prepareStatement("UPDATE Player SET STATUS=online WHERE USERNAME=?");
        prepareStatement.setString(1, player.getUsername());
        result = prepareStatement.executeUpdate();
        prepareStatement.close();
        connection.close();
        return result;
    }

    public static int updateScore(String username, int score) throws SQLException{
        int result=0;
          DriverManager.registerDriver(new ClientDriver());
          Connection con = DriverManager.getConnection("jdbc:derby://localhost:1527/TicTacToe","root","root");
          PreparedStatement ps = con.prepareStatement("UPDATE Player SET Score = ? WHERE Username = ?");
          ps.setInt(1, score);
          ps.setString(2, username);
          result=ps.executeUpdate();
          ps.close();
          con.close();
          
        return result;
    }
    
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
   public static int onlinePlayersNumber() throws SQLException{
        int onlineNumbers=0;
        DriverManager.registerDriver(new ClientDriver());
        Connection connection = DriverManager.getConnection("jdbc:derby://localhost:1527/TicTacToe","root","root");
        PreparedStatement prepareStatement = connection.prepareStatement("SELECT * FROM Player WHERE STATUS='online'" , ResultSet.TYPE_SCROLL_SENSITIVE , ResultSet.CONCUR_READ_ONLY);
        ResultSet result = prepareStatement.executeQuery();
        result.beforeFirst();
        while(result.next()){
            onlineNumbers++;
        }
        prepareStatement.close();
        connection.close();
        return onlineNumbers;
    }
    public static int offlinePlayersNumber() throws SQLException{
        int offlineNumbers=0;
        DriverManager.registerDriver(new ClientDriver());
        Connection connection = DriverManager.getConnection("jdbc:derby://localhost:1527/TicTacToe","root","root");
        PreparedStatement prepareStatement = connection.prepareStatement("SELECT * FROM Player WHERE STATUS='offline'" , ResultSet.TYPE_SCROLL_SENSITIVE , ResultSet.CONCUR_READ_ONLY);
        ResultSet result = prepareStatement.executeQuery();
        result.beforeFirst();
        while(result.next()){
            offlineNumbers++;
        }
        prepareStatement.close();
        connection.close();
        return offlineNumbers;
    }
    public static boolean logout(PlayerDTO player) throws SQLException{
        boolean exit;
        int result;
        DriverManager.registerDriver(new ClientDriver());
        Connection connection = DriverManager.getConnection("jdbc:derby://localhost:1527/TicTacToe","root","root");
        PreparedStatement prepareStatement = connection.prepareStatement("UPDATE Player SET STATUS='offline' WHERE USERNAME=?");
        prepareStatement.setString(1, player.getUsername());
        result = prepareStatement.executeUpdate();
        if(result==0){
            exit=false;
        }else{
            exit=true;
        }
        prepareStatement.close();
        connection.close();
        return exit;
    }
}
