/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import databasemng.Connect;
import databasemng.Query;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author mike
 */
public class Tables extends JFrame implements ActionListener, ItemListener{
    
    protected Connect con;
    
    public Tables(Connect con){
        super();
        this.con = con;
        this.windowConfig();
        this.items();
    }
    
    public void windowConfig(){
        this.setLayout(null);
        this.setSize(600, 500);
        this.setTitle("DB Manager by Mike");
        this.setLocationRelativeTo(this);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setVisible(true);
        this.setResizable(false);
    }
    
    public void items(){
        //confing db combo box
        this.configDBSelector();
    }
    
    public void configDBSelector(){
        Query q = new Query(con);
        this.dbSelector = null;
        
        if (q.getDBCount() == 0) {
            JOptionPane.showMessageDialog(this, "NO HAY BASES DE DATOS DISPONIBLES DISPONIBLES EN: ");
        } else{
            this.dbSelector = new JComboBox();
            
            for (int i = 0; i < q.getDB().length; i++) {
                this.dbSelector.addItem(q.getDB()[i]);
            }
            this.dbSelector.setSize(100,25);
            this.dbSelector.setLocation(10,10);
            this.add(this.dbSelector);
            this.dbSelector.addActionListener(this);
            
            this.configTableSelector();
        }
        
        this.repaint();
    }
    
    public void configTableSelector(){
        Query q = new Query(con);
        String dbName = this.dbSelector.getSelectedItem().toString();
        int tableCount = q.getTableCount(dbName);
        this.tableSelector = null;
        
        if (tableCount == 0) {
            JOptionPane.showMessageDialog(this, "NO HAY TABLAS DISPONIBLES EN: " + dbName);
            
        } else{
            this.tableSelector = new JComboBox();
            for (int i = 0; i < q.getTableCount(dbName); i++) {
                this.tableSelector.addItem(q.getTableNames(dbName)[i]);
            }
            this.tableSelector.setSize(100,25);
            this.tableSelector.setLocation(120,10);
            this.add(this.tableSelector);
            this.tableSelector.addActionListener(this);
            
            this.configJTable();
        }
        
        this.repaint();
        
    }
    
    public void configJTable(){
        Query q = new Query(con);
        String dbName = this.dbSelector.getSelectedItem().toString(),
                tableName = this.tableSelector.getSelectedItem().toString();
        String [] header = q.getColumnLabels(tableName, dbName);
        String [][] data = q.getRegs(tableName, dbName);
        this.table = null;
        this.model = null;
        
        if (q.getRegisterCount(tableName) == 0) {
            JOptionPane.showMessageDialog(this, "NO SE HAN ENCONTRADO REGISTROS EN " + tableName);
        }else{
            model = new DefaultTableModel(data, header);
            table = new JTable(model);
            table.setRowHeight(25);
            panel = new JScrollPane();
            panel.setViewportView(table);
            panel.setSize(580, 415);
            panel.setLocation(10, 45);
            this.add(panel);
        }
        this.repaint();
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        Query q = new Query(con);
        if (ae.getSource() == this.dbSelector) {
            q.sendQuery("USE " + this.dbSelector.getSelectedItem().toString());
            
            if (this.panel != null) {
                this.remove(this.panel);
            }
            if (this.tableSelector != null) {
                this.remove(this.tableSelector);
            }
            this.configTableSelector();
        }
        if(ae.getSource() == this.tableSelector){
            if (this.panel != null) {
                this.remove(this.panel);
            }
            this.configJTable();
        }
    }

    @Override
    public void itemStateChanged(ItemEvent ie) {
        
    }
    
    //Init Components
    public JComboBox dbSelector, tableSelector;
    public JTable table;
    public DefaultTableModel model;
    public JScrollPane panel;
}
