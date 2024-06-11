/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package databasemng;
/**
 *
 * @author mike
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Connect {
    //URL's para conectar a su respectivo Driver
    private String SQLServDriver = "com.microsoft.sqlserver.jdbc.SQLServerDriver",
                     MySQLDriver = "com.mysql.cj.jdbc.Driver",
                     SQLServConn = "jdbc:sqlserver//localhost:1433;databaseName=",
                     MySQLConn = "jdbc:mysql://localhost:3306/",
                     usr, pwd, db_name;
    public Connection conn;
    
    public Connect(){
        
    }
    
    protected boolean ConnectSQLServ(String dbName){
        try{
            Class.forName(SQLServDriver);
            conn = DriverManager.getConnection(SQLServConn + dbName + ";integratedSecurity=true;");
            return true;
        } catch(ClassNotFoundException | SQLException ex){
            System.err.println(ex.toString());
            return false;
        }
    }
    
    public boolean ConnectMySQL(String dbName){
        try{
            Class.forName(MySQLDriver);
            conn = DriverManager.getConnection(MySQLConn + dbName);
            return true;
        } catch(ClassNotFoundException | SQLException ex){
            System.err.println(ex.toString());
            return false;
        }
    }
    
    public boolean ConnectMySQL(String usr, String pwd){
        try{
            Class.forName(MySQLDriver);
            conn = DriverManager.getConnection(MySQLConn, usr, pwd);
            return true;
        } catch(ClassNotFoundException | SQLException ex){
            System.err.println(ex.toString());
            return false;
        }
    }
    
}
