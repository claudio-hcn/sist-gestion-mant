/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.sql.Connection;
import java.sql.DriverManager;
import javax.swing.JOptionPane;
import java.sql.SQLException;
/**
 *
 * @author acer
 */
public class Conexion {
    private static Connection conexion;
    private static Conexion instancia;
    
    private static final String URL = "jdbc:mysql://localhost/aserradero";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "oi55oi17";
    
    private Conexion(){};
    
    public Connection conectar(){
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            //com.mysql.cj.jdbc.Driver
            conexion = DriverManager.getConnection(URL, USERNAME, PASSWORD);
           // JOptionPane.showMessageDialog(null,"Conexion Exitosa");
            
            //return conexion;
        }catch(Exception e){
            JOptionPane.showMessageDialog(null,"Error: " + e);
        }
        
        return conexion;
    
    }
    
    public void cerrarConexion(){
        try{
            conexion.close();
            //JOptionPane.showMessageDialog(null,"Desconexion Exitosa");
        }catch(Exception e){
            JOptionPane.showMessageDialog(null,"Error: " + e);
        }
    }
    
    public static Conexion getInstance(){
        if(instancia==null){
            instancia = new Conexion();
        }
        return instancia;
    }
    
}

