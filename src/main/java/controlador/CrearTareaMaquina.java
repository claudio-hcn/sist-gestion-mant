package controlador;

import modelo.DAOTarea;
import modelo.Tarea;
import vista.DialogCrearTarea;

public class CrearTareaMaquina {
    private DialogCrearTarea dialog;
    private DAOTarea dao;
    private Tarea tarea;
    private String lala;

    public CrearTareaMaquina(DialogCrearTarea dialog, DAOTarea dao, Tarea tarea, String lala) {
        this.dialog = dialog;
        this.dao = dao;
        this.tarea = tarea;
        this.lala=lala;
    }
    
    public void mostrar(){
        dialog.jLabel1.setText(lala);
        dialog.setVisible(true);
    }
    
}
