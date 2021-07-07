package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JOptionPane;
import modelo.DAOTarea;
import modelo.Tarea;
import vista.DialogCrearTarea;

public class CrearTareaMaquina implements ActionListener{
    private DialogCrearTarea dialog;
    private DAOTarea dao;
    private Tarea tarea;
    private String lala;

    public CrearTareaMaquina(DialogCrearTarea dialog, DAOTarea dao, Tarea tarea, String lala) {
        this.dialog = dialog;
        this.dao = dao;
        this.tarea = tarea;
        this.lala=lala;
        this.dialog.btnTareaPendiente.addActionListener(this);
    }
    
    public void mostrar(){
        dialog.jLabel1.setText(lala);
        dialog.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(dialog.btnTareaPendiente==e.getSource()){
            tarea.setMaquina(dialog.jLabel1.getText());
            tarea.setNombreTarea(dialog.jTextField1.getText());
            tarea.setDuracionEstimada(dialog.spHoras.getValue().toString() + ":" + dialog.spMinutos.getValue().toString());
            tarea.setPrioridad(dialog.cbPrioridad.getSelectedItem().toString());
            tarea.setTipoTarea(dialog.cbTipoTarea.getSelectedItem().toString());
            tarea.setClasificacion1(dialog.cbClasificacion1.getSelectedItem().toString());
            tarea.setClasificacion2(dialog.cbClasificacion2.getSelectedItem().toString());
            tarea.setActivador("Tarea Planificada");
            String formatoFecha= "yyyy-MM-dd";
            Date fecha=dialog.rSDateChooser1.getDatoFecha();
            SimpleDateFormat formateador=new SimpleDateFormat(formatoFecha);
            tarea.setFechaProgramada(formateador.format(fecha));
            tarea.setFrecuencia("cada "+dialog.jSpinner1.getValue().toString()+" "+dialog.jComboBox1.getSelectedItem().toString());
            System.out.println(tarea);
             if (dao.Agregar(tarea)) {
                JOptionPane.showMessageDialog(null, "Tarea Creada, ingresada a plan de "+lala);
            } else {
                JOptionPane.showMessageDialog(null, "Error al Guardar");
            }
        }
       
    }
    
}
