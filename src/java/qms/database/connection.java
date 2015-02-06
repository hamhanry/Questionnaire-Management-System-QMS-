package qms.database;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author admin
 */
import java.sql.*;

public class connection {
    private Connection con = null;
    private Statement st = null;

    public connection() {//constructor
        String driver = "com.mysql.jdbc.Driver";
        try {
            Class.forName(driver);
        } catch (ClassNotFoundException ex) {
            System.out.println(ex);
        }
    }
    
    public Connection connect(){
        String url = "jdbc:mysql://localhost:3306/qms2014";
        String username = "root";
        String password = "";
        
        try {
            con = DriverManager.getConnection(url,username,password);
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        return con;
    }
    
    public void closeConnect(){
        try {
            if(con != null)
                con.close();
        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }
    
    public ResultSet execQuery(String query) throws SQLException{
        st = con.createStatement();
        return st.executeQuery(query);
    }
    
    public void execUpdate(String query){
        try {
            st.executeUpdate(query);
        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }
}

