/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;
import modelo.DAOSolicitud;
import modelo.Solicitud;
import vista.VistaVerSolicitudes;

/**
 *
 * @author Claudio
 */
public class VerSolicitudes implements ActionListener{
    private VistaVerSolicitudes vista;
    private DAOSolicitud dao;
    private Solicitud solicitud;
    
    
      String[] columnas = {"ID", "MAQUINA", "DESCRIPCION", "ESTADO SOLICITUD", "FECHA"};
    ArrayList<Object[]> datos = new ArrayList<>();
    DefaultTableModel modelo = new DefaultTableModel(columnas, 0) {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };

    public VerSolicitudes(VistaVerSolicitudes vista, DAOSolicitud dao, Solicitud solicitud) {
        this.vista = vista;
        this.dao = dao;
        this.solicitud = solicitud;
    }
    public void mostrar(){
        vista.setVisible(true);
         vista.setSize(1000, 602);
        cargar();
    }
     public void cargar() {
        modelo.setRowCount(0);
        datos = dao.verTodo();

        // for each
        for (Object[] obj : datos) {
            modelo.addRow(obj);
        }
        vista.jTable1.setModel(modelo);


    }
    @Override
    public void actionPerformed(ActionEvent e) {
        
    }
    
    
    
}
