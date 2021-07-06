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
import java.util.ArrayList;
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
                + "prioridad,tipo_tarea,clasificacion1,clasificacion2,activador,fecha_programada ) VALUES((select id_maquina FROM maquinas where nombre = ?),?,?,?,?,?,?,?,?)";
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
    
}
