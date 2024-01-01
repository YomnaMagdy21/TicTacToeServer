/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Database;

import dto.PlayerDTO;
import DTO.PlayerDTO;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.apache.derby.jdbc.ClientDriver;

/**
 *
 * @author HP
 */
public class DataAccessLayer {
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
        return exist;
    }
    
 
    public static int updateStatus(PlayerDTO player) throws SQLException{
        int result;
        DriverManager.registerDriver(new ClientDriver());
        Connection connection = DriverManager.getConnection("jdbc:derby://localhost:1527/TicTacToe","root","root");
        PreparedStatement prepareStatement = connection.prepareStatement("UPDATE Player SET STATUS=online WHERE USERNAME=?");
        prepareStatement.setString(1, player.getUsername());
        result = prepareStatement.executeUpdate();
        return result;
    }
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
    
}
