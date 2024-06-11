/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package databasemng;

import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.table.DefaultTableModel;
/**
 *
 * @author mike
 */
public class Query {
    
    private Connect con;
    //private Statement st;
    // private ResultSet rs;
    
    public Query(Connect con){
        this.con = con;
    }
    
    public ResultSet sendQuery(String query){
        Statement st = null;
        ResultSet rs = null;
        try{
            st = con.conn.createStatement();
            rs = st.executeQuery(query);
            return rs;
        }catch(SQLException ex){
            System.err.println("ERROR AL ENVIAR CONSULTA\n " + ex);
        }
        return rs;
    }
    
    public String[] getDB(){
        
        String[] res = new String[this.getDBCount()];
        ResultSet rs = this.sendQuery("SHOW DATABASES;");
        try{
            for (int i = 0; rs.next(); i++) {
                res[i] = rs.getString(1);
            }
        }catch(SQLException ex){
            System.err.println("ERROR AL OBTENER BASES DE DATOS\n" + ex);
        }
        return res;
    }
    
    public int getDBCount(){
        int res = 0;
        try{
            Statement st = con.conn.createStatement();
            ResultSet rs = st.executeQuery("SHOW DATABASES;");
            while(rs.next()){
                res++;
            }
        }catch(SQLException ex){
            System.err.println("ERROR AL OBTENER NUMERO DE BASES DE DATOS\n" + ex);
        }
        return res;
    }
    
    public int getTableCount(String dbName){
        int res = 0;
        ResultSet rs = this.sendQuery("SELECT COUNT(*) from Information_Schema.Tables WHERE TABLE_TYPE = 'BASE TABLE' AND table_schema = '" + dbName + "'");
        try{
            while(rs.next()){
                res = rs.getInt(1);
            }
        }catch(SQLException ex){
            System.err.println("ERROR AL OBTENER NUMERO DE TABLAS\n" + ex);
        }
        return res;
    }
    
    public String[] getTableNames(String dbName){
        String[] res = new String[this.getTableCount(dbName)];
        ResultSet rs = this.sendQuery("SELECT table_name from Information_Schema.Tables WHERE TABLE_TYPE = 'BASE TABLE' AND table_schema = '" + dbName + "'");
        try{
            for (int i = 0; rs.next(); i++) {
                res[i] = rs.getString(1);
            }
        }catch(SQLException ex){
            System.err.println("ERROR AL OBTENER NOMBRE DE LAS TABLAS\n" + ex);
        }
        return res;
    }
    
    public String[] getColumnLabels(String tableName, String dbName){
        String [] res = new String [getColumnCount(tableName, dbName)];
        ResultSet rs = this.sendQuery("SELECT COLUMN_NAME FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_NAME = '" + tableName + "'AND table_schema = '" + dbName + "' ORDER BY ORDINAL_POSITION;");
        try {
            for (int i = 0; rs.next(); i++) {
                res[i] = rs.getString(1);
            }

        } catch (Exception e) {
            System.err.println("ERROR AL OBTENER NOMBRES DE LAS COLUMNAS\n" + e);
        }
        return res;
    }
    
    public int getColumnCount(String tableName, String dbName){
        String qry = "SELECT COUNT(COLUMN_NAME) FROM INFORMATION_SCHEMA.COLUMNS WHERE table_name = '" + tableName + "' AND table_schema = '" + dbName + "'";
        int result = 0;
        ResultSet rs = this.sendQuery(qry);
        try{
            while(rs.next()){
                result = rs.getInt(1);
            }
        } catch(Exception e){
            System.err.println("ERROR AL OBTENER NUMERO DE COLUMNAS\n" + e);
        }
        return result;
    }
    
    public int getRegisterCount(String tableName){
        String qry = "SELECT COUNT(*) FROM " + tableName;
        int result = 0;
        ResultSet rs = this.sendQuery(qry);
        try{
            while(rs.next()){
                result = rs.getInt(1);
            }
        } catch(Exception e){
            System.err.println("ERROR AL OBTENER EL NUMERO DE REGISTROS\n" + e);
        }
        return result;
    }
    
    public String [][] getRegs(String tableName, String dbName){
        int columnCount = getColumnCount(tableName, dbName),
                rowCount = getRegisterCount(tableName),
                cont = 0;
        
        //String [] row = new String[columnCount];
        String [][] res = new String[rowCount][columnCount];
        String qry = "SELECT * FROM " + tableName;
        ResultSet rs = this.sendQuery(qry);
        try{
            while(rs.next()){
                for (int i = 0; i < columnCount; i++) {
                    res[cont][i] = rs.getString(i + 1);
                }
                cont++;
            }
            
        } catch(Exception e){
            System.err.println("ERROR AL OBTENER REGISTROS DE " + tableName + "\n" + e);
        }
        return res;
    }
    
}
