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
public class DAOSolicitud implements CRUD{
    Solicitud solicitud = new Solicitud();
    //patron singleton
    Conexion conx = Conexion.getInstance();
    Connection con;

    @Override
    public boolean Agregar(Object obj) {
     solicitud = (Solicitud) obj;
        String sql = "INSERT INTO solicitudes (maquina, descripcion, observaciones,solicitante,fecha,fk_maquina) VALUES(?,?,?,?,?,(select id_maquina FROM maquinas where nombre = ?))";
        PreparedStatement pst;

        try {
            con = (Connection) conx.conectar();
            pst = (PreparedStatement) con.prepareStatement(sql);
            pst.setString(1, solicitud.getMaquina());
            pst.setString(2, solicitud.getDescripcion());
            pst.setString(3, solicitud.getObservacion());
            pst.setString(4, solicitud.getSolicitante());
            pst.setString(5, solicitud.getHoraSolicitud());
            pst.setString(6,solicitud.getMaquina());
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
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean Eliminar(Object obj) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ArrayList<Object[]> consultar() {
//        String sql = "select  s.id_solicitud as id, s.maquina, s.descripcion, s.observaciones, s.solicitante, s.fecha, s.   \n" +
//" from maquinas m INNER JOIN empleado e\n" +
//"on c.id_categoria  = e.id_categoria";
//        Connection con;
//        PreparedStatement pst;
//        ResultSet rs;
//        ResultSetMetaData meta;
//        ArrayList<Object[]> datos = new ArrayList<>();
//
//        try {
//            con = (Connection) conx.conectar();
//            pst = (PreparedStatement) con.prepareStatement(sql);
//
//            rs = pst.executeQuery();
//            meta = rs.getMetaData();
//            while (rs.next()) {
//                Object[] fila = new Object[meta.getColumnCount()];
//                for (int i = 0; i < fila.length; i++) {
//                    fila[i] = rs.getObject(i + 1);
//                }
//                datos.add(fila);
//
//            }
//        } catch (SQLException e) {
//            JOptionPane.showMessageDialog(null, "Ocurrio un errror" + e.getMessage());
//
//        }
//        return datos;

       String sql = "SELECT id_solicitud,maquina,descripcion FROM solicitudes";
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
    public DefaultComboBoxModel ob_maq() throws SQLException{
         con = (Connection) conx.conectar();
        Statement st  = con.createStatement();
        DefaultComboBoxModel listaModelo= new DefaultComboBoxModel();
        listaModelo.addElement("Seleccione Máquina");
        ResultSet rs = st.executeQuery("select  nombre from maquinas");
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
}
