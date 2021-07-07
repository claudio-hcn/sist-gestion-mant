/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.sql.Connection;
import java.sql.Date;
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
public class DAOTarea implements CRUD{
    Tarea tarea=new Tarea();
    Conexion conx = Conexion.getInstance();
    Connection con;

    @Override
    public boolean Agregar(Object obj) {
         tarea = (Tarea) obj;
        String sql = "INSERT INTO tareas (fk_maquina,nombre_tarea,duracion_estimada,"
                + "prioridad,tipo_tarea,clasificacion1,clasificacion2,activador,fecha_programada,frecuencia ) VALUES((select id_maquina FROM maquinas where nombre = ?),?,?,?,?,?,?,?,?,?)";
        PreparedStatement pst;

        try {
            con = (Connection) conx.conectar();
            pst = (PreparedStatement) con.prepareStatement(sql);
            pst.setString(1, tarea.getMaquina());
            pst.setString(2, tarea.getNombreTarea());
            pst.setString(3, tarea.getDuracionEstimada());
            pst.setString(4, tarea.getPrioridad());
            pst.setString(5, tarea.getTipoTarea());
            pst.setString(6, tarea.getClasificacion1());
            pst.setString(7, tarea.getClasificacion2());
            pst.setString(8, tarea.getActivador());
            pst.setString(9, tarea.getFechaProgramada());
            pst.setString(10, tarea.getFrecuencia());
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
            JOptionPane.showMessageDialog(null, "Ocurrio un error : " + e.getMessage());
        }
        return false;
    }

    @Override
    public boolean Modificar(Object obj) {
        return true;
    }

    @Override
    public boolean Eliminar(Object obj) {
        return true;
    }

    @Override
    public ArrayList<Object[]> consultar() {
        String sql = "SELECT (select nombre from maquinas where id_maquina=fk_maquina),nombre_tarea, "
                + "CONCAT(datediff(CURDATE(),fecha_programada)*IF(CURDATE()>fecha_programada,1,0),' días') as atras,"
                + "fecha_programada, duracion_estimada,prioridad,tipo_tarea,clasificacion1,activador FROM tareas ORDER BY fecha_programada";
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
    public ArrayList<String[]> consultar1() {
        String sql = "SELECT (select nombre from maquinas where id_maquina=fk_maquina),nombre_tarea, "
                + "CONCAT(datediff(CURDATE(),fecha_programada)*IF(CURDATE()>fecha_programada,1,0),' días') as atras,"
                + "fecha_programada, duracion_estimada,prioridad,tipo_tarea,clasificacion1,activador FROM tareas ORDER BY fecha_programada";
        Connection con;
        PreparedStatement pst;
        ResultSet rs;
        ResultSetMetaData meta;
        ArrayList<String[]> datos = new ArrayList<>();

        try {
            con = (Connection) conx.conectar();
            pst = (PreparedStatement) con.prepareStatement(sql);

            rs = pst.executeQuery();
            meta = rs.getMetaData();
            while (rs.next()) {
                String[] fila = new String[meta.getColumnCount()];
                for (int i = 0; i < fila.length; i++) {
                    fila[i] = rs.getString(i + 1);
                }
                datos.add(fila);

            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Ocurrio un errror" + e.getMessage());

        }
        return datos;
    }
    // and fk_maquina=(select id_maquina from maquinas where nombre='"+maquina+"')
    public ArrayList<String[]> consultarTM(String maquina) {
        String sql = "SELECT nombre_tarea, duracion_estimada,prioridad,tipo_tarea,activador,frecuencia,id_tarea FROM tareas WHERE activador='Tarea Planificada' and fk_maquina=(select id_maquina from maquinas where nombre='"+maquina+"')";
        Connection con;
        PreparedStatement pst;
        ResultSet rs;
        ResultSetMetaData meta;
        ArrayList<String[]> datos = new ArrayList<>();

        try {
            con = (Connection) conx.conectar();
            pst = (PreparedStatement) con.prepareStatement(sql);

            rs = pst.executeQuery();
            meta = rs.getMetaData();
            while (rs.next()) {
                String[] fila = new String[meta.getColumnCount()];
                for (int i = 0; i < fila.length; i++) {
                    fila[i] = rs.getString(i + 1);
                }
                datos.add(fila);

            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Ocurrio un error y wea ..." + e.getMessage());

        }
        return datos;
    }
    public DefaultComboBoxModel ob_maq() throws SQLException {
        con = (Connection) conx.conectar();
        Statement st = con.createStatement();
        DefaultComboBoxModel listaModelo = new DefaultComboBoxModel();
        listaModelo.addElement("Seleccione Máquina");
        ResultSet rs = st.executeQuery("select  nombre from maquinas");
        try {
            while (rs.next()) {
                listaModelo.addElement(rs.getString(1));
            }
            rs.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Ocurrio un error " + ex.getMessage());
        }
        return listaModelo;
    }
//     public String[] ob_tareas(String lala) throws SQLException {
//        String datos[] = new String[7];
//        con = (Connection) conx.conectar();
//        Statement st = con.createStatement();
//        ResultSet rs = st.executeQuery("select  * from solicitudes where id_solicitud='" + lala + "'");
//        try {
//            while (rs.next()) {
//                datos[0] = (rs.getString(1));
//                datos[1] = (rs.getString(2));
//                datos[2] = (rs.getString(3));
//                datos[3] = (rs.getString(4));
//                datos[4] = (rs.getString(5));
//                datos[5] = (rs.getString(6));
//                datos[6] = (rs.getString(7));
//                datos[7] = (rs.getString(8));
//                datos[8] = (rs.getString(9));
//            }
//            rs.close();
//        } catch (SQLException ex) {
//            JOptionPane.showMessageDialog(null, "Ocurrio un error" + ex.getMessage());
//        }
//
//        return datos;
//    }
}
