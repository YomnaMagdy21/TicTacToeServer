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
        return result; 

    }
    
}
