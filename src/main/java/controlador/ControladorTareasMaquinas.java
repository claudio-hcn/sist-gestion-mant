/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;
import modelo.DAOTarea;
import modelo.Tarea;
import vista.DialogCrearTarea;
import vista.VistaPrincipal;
import vista.VistaTareasMaquinas;

/**
 *
 * @author Claudio
 */
public class ControladorTareasMaquinas implements ActionListener{
    private Tarea tarea;
    private DAOTarea dao;
    private VistaTareasMaquinas vista;
    private DialogCrearTarea dialog;
    private VistaPrincipal vistaP;
    
     String[] columnas = { "TAREA","ATRASO","FECHA PROGRAMADA", "TIEMPO ESTIMADO", "PRIORIDAD","TIPO TAREA", "CLASIFICACION 1", "ACTIVADOR"};
    ArrayList<Object[]> datos = new ArrayList<>();
    DefaultTableModel modelo = new DefaultTableModel(columnas, 0) {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };
    public ControladorTareasMaquinas(Tarea tarea, DAOTarea dao, VistaTareasMaquinas vista) {
        this.tarea = tarea;
        this.dao = dao;
        this.vista = vista;
        this.vista.btnCrear.addActionListener(this);
    }
    public void mostrar() throws SQLException{
        vista.setVisible(true);
        vista.setSize(700, 602);
        vista.cbMaquina.setModel(dao.ob_maq());
        cargar();
        
    }
     public void cargar() {
        modelo.setRowCount(0);
        datos = dao.consultarTM();

        // for each
        for (Object[] obj : datos) {
            modelo.addRow(obj);
        }
        vista.jTable1.setModel(modelo);
//        System.out.println(usu.getUsuario());

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(vista.btnCrear==e.getSource()){
            DialogCrearTarea dialog = new DialogCrearTarea(vistaP, true);
            Tarea tarea=new Tarea();
            DAOTarea daoT=new DAOTarea();
            String cosa=vista.cbMaquina.getSelectedItem().toString();
            System.out.println(cosa);
            CrearTareaMaquina crearTM=new CrearTareaMaquina(dialog, dao, tarea,cosa);
            crearTM.mostrar();
            
            
        }
    }
    
}
