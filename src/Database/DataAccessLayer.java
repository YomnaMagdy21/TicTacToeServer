/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Database;

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
