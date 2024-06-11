/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

/**
 *
 * @author mike
 */
import java.util.Scanner;
import java.sql.Statement;
import java.sql.ResultSet;
import javax.swing.*;
import databasemng.Connect;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import databasemng.Query;

public class Index extends JFrame implements ActionListener{

    public Index(){
        super();
        this.windowConfig();
        this.items();
    }
    
    protected void windowConfig(){
        this.setLayout(null);
        this.setSize(300, 500);
        this.setTitle("Log In");
        this.setLocationRelativeTo(this);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setVisible(true);
        this.setResizable(false);
    }
    
    protected void items(){
        this.selSGBD.setSize(100, 25);
        this.selSGBD.addItem("MySQL");
        this.selSGBD.addItem("SQLServer");
        this.selSGBD.setLocation(0,0);
        this.add(selSGBD);
        this.selSGBD.setVisible(true);
        
        //Boton para conectar
        this.btnConnect.setSize(100,25);
        this.add(this.btnConnect);
        this.btnConnect.setLocation(100,300);
        this.btnConnect.setVisible(true);
        this.btnConnect.addActionListener(this);
        
        //Campos de texto
        this.usr.setSize(150, 30);
        this.usr.setLocation(75, 200);
        this.usr.setVisible(true);
        this.add(this.usr);
        
        this.pwd.setSize(150, 30);
        this.pwd.setLocation(75, 250);
        this.pwd.setVisible(true);
        this.add(this.pwd);
        
        this.repaint();
    }
    
    public static void main(String[] args) {
        new Index();
        
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == this.btnConnect) {
            String pwd = "";
                for (int i = 0; i < this.pwd.getPassword().length; i++) {
                    pwd = pwd + String.valueOf(this.pwd.getPassword()[i]);
                }
            Connect con = new Connect();
           
            if (this.selSGBD.getSelectedIndex() == 0) {
                if (con.ConnectMySQL(usr.getText(), pwd)) {
                    JOptionPane.showMessageDialog(this, "Connectado a MySQL :)");
                    this.dispose();
                    new Tables(con);
                } else{
                    JOptionPane.showMessageDialog(this, "ERROR DE CONEXION :(");
                }
            }
            
        }
    }
    //Init Components
    JButton btnConnect = new JButton("Conectar");
    JComboBox selSGBD = new JComboBox();
    JTextField usr = new JTextField();
    JPasswordField pwd = new JPasswordField();
}
