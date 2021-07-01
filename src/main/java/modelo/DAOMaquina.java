
package modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JOptionPane;


public class DAOMaquina implements CRUD {
    Maquina maq = new Maquina();
    //patron singleton
    Conexion conx = Conexion.getInstance();
    Connection con;

    @Override
    public boolean Agregar(Object obj) {
        maq = (Maquina) obj;
        String sql = "INSERT INTO maquinas (nombre, ubicacion, codigo, centro_costo) VALUES(?,?,?,?)";
        PreparedStatement pst;

        try {
            con = (Connection) conx.conectar();
            pst = (PreparedStatement) con.prepareStatement(sql);
            pst.setString(1, maq.getNombreMaquina());
            pst.setString(2, maq.getUbicacion());
            pst.setString(3, maq.getCodigo());
            pst.setString(4, maq.getCentroCosto());
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

    @Override
    public boolean Modificar(Object obj) {
        maq = (Maquina) obj;
        String sql = "UPDATE  maquinas SET nombre= ?,ubicacion = ?,codigo = ?,centro_costo=? WHERE id_maquina = ?";
        Connection con;
        PreparedStatement pst;
        try {
             con = (Connection) conx.conectar();
            pst = (PreparedStatement) con.prepareStatement(sql);
            pst.setString(1, maq.getNombreMaquina());
            pst.setString(2, maq.getUbicacion());
            pst.setString(3, maq.getCodigo());
            pst.setInt(5, maq.getIdMaquina());
            pst.setString(4, maq.getCentroCosto());
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
        System.out.println(maq.getIdMaquina());
        maq = (Maquina) obj;
        String sql = "DELETE FROM maquinas where id_maquina=?";
        Connection con;
        PreparedStatement pst;
        try {
               con = (Connection) conx.conectar();
            pst = (PreparedStatement) con.prepareStatement(sql);
            
            pst.setInt(1, maq.getIdMaquina());
            System.out.println(maq.getIdMaquina());
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
        String sql = "SELECT * FROM maquinas";
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
    
}
