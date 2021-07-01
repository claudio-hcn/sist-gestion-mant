/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;

/**
 *
 * @author acer
 */
public class DAOUsuario implements Operacion {

    Usuario usu = new Usuario();
    //patron singleton
    Conexion conx = Conexion.getInstance();
    Connection con;

    @Override
    public boolean validarUsuario(Object obj) {
        usu = (Usuario) obj;
        //String sql = "UPDATE  persona SET Rut =?,Nombre = ?,Apellidos =?  WHERE Id =?";
        String sql = "SELECT * FROM usuarios WHERE usuario = ? AND clave = ?";
        Connection con;
        PreparedStatement pst;
        ResultSet rs;
        boolean usuarioValido = false;
        try {
            con = (Connection) conx.conectar();
            pst = (PreparedStatement) con.prepareStatement(sql);

            pst.setString(1, usu.getUsuario());
            pst.setString(2, usu.getClave());

            rs = pst.executeQuery();

            while (rs.next()) {
                usuarioValido = true;
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Ocurrió un error" + e.getMessage());

        }
        return usuarioValido;

    }
    public String traerNombre(String usuario) throws SQLException{
        String nombre="";
        con = (Connection) conx.conectar();
        Statement st  = con.createStatement();
        ResultSet rs = st.executeQuery("select concat(nombre,' ',apellido_paterno) from trabajadores where id_trabajador=(select fk_trabajadores from usuarios where usuario='"+usuario+"')");
                 //"concat(nombre,' ',apellido_paterno) as nombre from trabajadores where id_trabajador=(select fk_trabajador from usuarios where usuario=?)");
          try {
            while (rs.next()) {
                nombre=rs.getString(1);
               
            }
            rs.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Ocurrio un errror" + ex.getMessage());
        }
        System.out.println(nombre);
        return nombre;
        
    }
}
