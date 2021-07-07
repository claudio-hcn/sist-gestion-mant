package controlador;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import modelo.DAOTarea;
import modelo.Tarea;
import vista.VistaTareasPendientes;


public class ControladorTareasPendientes implements ActionListener {
    private VistaTareasPendientes vista;
    private Tarea tarea;
    private DAOTarea dao;
    
    
    String[] columnas = {"MAQUINA", "TAREA","ATRASO","FECHA PROGRAMADA", "TIEMPO ESTIMADO", "PRIORIDAD","TIPO TAREA", "CLASIFICACION 1", "ACTIVADOR"};
    ArrayList<String[]> datos = new ArrayList<>();
    DefaultTableModel modelo = new DefaultTableModel(columnas, 0) {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };

    public ControladorTareasPendientes(VistaTareasPendientes vista, Tarea tarea, DAOTarea dao) {
        this.vista = vista;
        this.tarea = tarea;
        this.dao = dao;
    }
    public void mostrar() throws SQLException {
        vista.setVisible(true);
        vista.setSize(1200, 500);
        cargar();

    }

    public void cargar() {
        modelo.setRowCount(0);
        datos = dao.consultar1();

        // for each
        for (Object[] obj : datos) {
            modelo.addRow(obj);
        }
        vista.jTable1.setModel(modelo);
       


    }

    public void limpiar() {

      
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        
    
}


}