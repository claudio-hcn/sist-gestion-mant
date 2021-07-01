/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;

/**
 *
 * @author Claudio
 */
public class DAOTrabajador implements CRUD {

    Trabajador trabajador = new Trabajador();
    //patron singleton
    Conexion conx = Conexion.getInstance();
    Connection con;

    @Override
    public boolean Agregar(Object obj) {
        trabajador = (Trabajador) obj;
        String sql = "INSERT INTO trabajadores (nombre,apellido_paterno,apellido_materno,rut,telefono,correo,categoria,fk_categoria) VALUES(?,?,?,?,?,?,?,(select id_categoria FROM categorias where nombre = ?))";
        PreparedStatement pst;

        try {
            con = (Connection) conx.conectar();
            pst = (PreparedStatement) con.prepareStatement(sql);
            pst.setString(1, trabajador.getNombre());
            pst.setString(2, trabajador.getApellidoPaterno());
            pst.setString(3, trabajador.getApellidoMaterno());
            pst.setString(4, trabajador.getRut());
            pst.setInt(5, trabajador.getTelefono());
            pst.setString(6, trabajador.getCorreo());
            pst.setString(7, trabajador.getCategoria());
            pst.setString(8, trabajador.getCategoria());
            int filas = pst.executeUpdate();
            if (filas > 0) {

                //con.close();
                conx.cerrarConexion();
                return true;
            } else {

                con.close();
                return true;
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Ocurrio un errror : " + e.getMessage());
        }
        return false;
    }
//  
    @Override
    public boolean Modificar(Object obj) {
        trabajador = (Trabajador) obj;
        String sql = "UPDATE  trabajadores SET nombre= ?,apellido_paterno = ?,apellido_materno = ?,rut = ?,telefono = ?,correo = ?, categoria=?,fk_categoria = (select id_categoria from categorias where nombre=?) WHERE id_trabajador = ?";
        Connection con;
        PreparedStatement pst;
        try {
             con = (Connection) conx.conectar();
            pst = (PreparedStatement) con.prepareStatement(sql);
            pst.setString(1, trabajador.getNombre());
            pst.setString(2, trabajador.getApellidoPaterno());
            pst.setString(3, trabajador.getApellidoMaterno());
            pst.setString(4, trabajador.getRut());
            pst.setInt(5, trabajador.getTelefono());
            pst.setString(6, trabajador.getCorreo());
            pst.setString(7, trabajador.getCategoria());
            pst.setString(8, trabajador.getCategoria());
            pst.setInt(9, trabajador.getIdTrabajador());
            int filas = pst.executeUpdate();
            if (filas > 0) {
                con.close();
                return true;
            } else {
                return false;
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Ocurrio un error \n" + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean Eliminar(Object obj) {
        trabajador = (Trabajador) obj;
        String sql = "DELETE FROM trabajadores where id_trabajador=?";
        Connection con;
        PreparedStatement pst;
        try {
               con = (Connection) conx.conectar();
            pst = (PreparedStatement) con.prepareStatement(sql);
            
            pst.setInt(1, trabajador.getIdTrabajador());
            int filas = pst.executeUpdate();
            con.close();
            if (filas > 0) {
                con.close();
                return true;
            } else {
                return false;
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Ocurrio un errror :" + e.getMessage());
            return false;
    }
    }

    @Override
    public ArrayList<Object[]> consultar() {
        String sql = "SELECT CONCAT(nombre,' ',apellido_paterno,' ', apellido_materno), rut,telefono,categoria FROM trabajadores";
        Connection con;
        PreparedStatement pst;
        ResultSet rs;
        ResultSetMetaData meta;
        ArrayList<Object[]> datos = new ArrayList<>();

        try {
            con = (Connection) conx.conectar();
            pst = (PreparedStatement) con.prepareStatement(sql);

            rs = pst.executeQuery();
            meta = rs.getMetaData();
            while (rs.next()) {
                Object[] fila = new Object[meta.getColumnCount()];
                for (int i = 0; i < fila.length; i++) {
                    fila[i] = rs.getObject(i + 1);
                }
                datos.add(fila);

            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Ocurrio un errror" + e.getMessage());

        }
        return datos;
    }
   public DefaultComboBoxModel ob_categoria() throws SQLException{
         con = (Connection) conx.conectar();
        Statement st  = con.createStatement();
        DefaultComboBoxModel listaModelo= new DefaultComboBoxModel();
        listaModelo.addElement("Seleccione Categoria");
        ResultSet rs = st.executeQuery("select  nombre from categorias");
          try {
            while (rs.next()) {
                listaModelo.addElement(rs.getString(1));
            }
            rs.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Ocurrio un errror" + ex.getMessage());
        }
        return listaModelo;
} 
    public String [] ob_trabajador(String lala) throws SQLException{
        String datos []=new String[8];
         con = (Connection) conx.conectar();
        Statement st  = con.createStatement();      
      ResultSet rs = st.executeQuery("select  * from trabajadores where rut='"+lala+"'");
          try {
            while (rs.next()) {
                datos[0]=(rs.getString(1));
                datos[1]=(rs.getString(2));
                datos[2]=(rs.getString(3));
                datos[3]=(rs.getString(4));
                datos[4]=(rs.getString(5));
                datos[5]=(rs.getString(6));
                datos[6]=(rs.getString(7));
                datos[7]=(rs.getString(8));
            }
            rs.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Ocurrio un error" + ex.getMessage());
        }
        
        return datos;
    }
}

   

