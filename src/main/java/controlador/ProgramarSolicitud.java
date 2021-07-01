/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import modelo.DAOSolicitud;
import modelo.DAOTarea;
import modelo.Solicitud;
import modelo.Tarea;
import modelo.Usuario;
import vista.DialogSolicitud;
import vista.VistaPrincipal;
import vista.VistaSolicitud;

/**
 *
 * @author Claudio
 */
public class ProgramarSolicitud implements ActionListener {

    private DialogSolicitud dialog;
    private Solicitud solicitud;
    private DAOSolicitud dao;
    private String cosa;
    private Tarea tarea;
    private DAOTarea daoT;
    private VistaPrincipal vistaP;
    private Usuario usu;
    
    SimpleDateFormat formatoFecha=new SimpleDateFormat("yyyy-MM-dd");

    public ProgramarSolicitud(DialogSolicitud dialog, Solicitud solicitud, DAOSolicitud dao,
            Usuario usu, Tarea tarea, DAOTarea daoT, String cosa, VistaPrincipal vistaP) {
        this.dialog = dialog;
        this.solicitud = solicitud;
        this.dao = dao;
        this.cosa = cosa;
        this.dialog.btnTareaPendiente.addActionListener(this);
        this.dialog.btnCerrar.addActionListener(this);
        this.tarea = tarea;
        this.daoT = daoT;
        this.usu = usu;
        this.vistaP = vistaP;
    }

    public void mostrarDialog() throws SQLException {

        String[] datos = dao.ob_sol(cosa);
        dialog.jLabel1.setText(datos[1]);
        dialog.jTextField1.setText(datos[2]);
        dao.estadoVista(datos[0]);
        dialog.txtHoras.setText("00");
        dialog.txtMinutos.setText("30");
        dialog.setLocationRelativeTo(null);
        dialog.setVisible(true);
        dialog.setTitle("Procesar Solicitud");

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (dialog.btnTareaPendiente == e.getSource()) {
            if(validarFormulario()){
            tarea.setMaquina(dialog.jLabel1.getText());
            tarea.setNombreTarea(dialog.jTextField1.getText());
            tarea.setDuracionEstimada(dialog.txtHoras.getText() + ":" + dialog.txtMinutos.getText());
            tarea.setPrioridad(dialog.cbPrioridad.getSelectedItem().toString());
            tarea.setTipoTarea(dialog.cbTipoTarea.getSelectedItem().toString());
            tarea.setClasificacion1(dialog.cbClasificacion1.getSelectedItem().toString());
            tarea.setClasificacion2(dialog.cbClasificacion2.getSelectedItem().toString());
            tarea.setActivador("Tarea no Planificada");
            String formatoFecha= "yyyy-MM-dd";
            Date fecha=dialog.rSDateChooser1.getDatoFecha();
            SimpleDateFormat formateador=new SimpleDateFormat(formatoFecha);
            tarea.setFechaProgramada(formateador.format(fecha));
                System.out.println(tarea.getFechaProgramada());

            if (daoT.Agregar(tarea)) {
                JOptionPane.showMessageDialog(null, "Solicitud enviada a Tareas Pendientes para generar OT");
            } else {
                JOptionPane.showMessageDialog(null, "Error al Guardar");
            }
             try {
                dao.estadoProgramada(cosa);
            } catch (SQLException ex) {
                Logger.getLogger(ProgramarSolicitud.class.getName()).log(Level.SEVERE, null, ex);
            }
            volver();
            }
        }
        if (dialog.btnCerrar == e.getSource()) {
            volver();
        }
    }

    public boolean validarFormulario() {
        boolean validacion = true;
        if (!dialog.jTextField1.getText().isEmpty()) {
            if (!(dialog.txtHoras.getText().isEmpty() || dialog.txtMinutos.getText().isEmpty())) {
                if (dialog.cbPrioridad.getSelectedIndex() != 0) {
                    if (dialog.cbTipoTarea.getSelectedIndex() != 0) {
                        if (dialog.cbClasificacion1.getSelectedIndex() != 0) {
                            if (dialog.cbClasificacion2.getSelectedIndex() != 0) {
                                validacion = true;
                            } else {
                                JOptionPane.showMessageDialog(null, "por favor seleccione la clasificacion 2", "mensaje de error", JOptionPane.ERROR_MESSAGE);
                                validacion = false;
                            }
                        }else{
                            JOptionPane.showMessageDialog(null, "por favor la clasificacion2 de la tarea", "mensaje de error", JOptionPane.ERROR_MESSAGE);
                                validacion = false;
                        }
                    }else{
                        JOptionPane.showMessageDialog(null, "por favor seleccione el tipo tarea", "mensaje de error", JOptionPane.ERROR_MESSAGE);
                                validacion = false;
                    }

                }else{
                    JOptionPane.showMessageDialog(null, "por favor la prioridad de la tarea", "mensaje de error", JOptionPane.ERROR_MESSAGE);
                                validacion = false;
                }
            }else{
                JOptionPane.showMessageDialog(null, "por favor ingrese el tiempo estimado de forma correcta", "mensaje de error", JOptionPane.ERROR_MESSAGE);
                                validacion = false;
            }
        }else{
            JOptionPane.showMessageDialog(null, "por favor la tarea a realizar", "mensaje de error", JOptionPane.ERROR_MESSAGE);
                                validacion = false;
        }
        return validacion;
    }

    public void volver() {
        dialog.dispose();
        VistaSolicitud vistaSol = new VistaSolicitud();
        Solicitud sol = new Solicitud();
        DAOSolicitud daos = new DAOSolicitud();
        vistaP.panel.removeAll();
        vistaP.panel.add((Component) vistaSol, BorderLayout.CENTER);
        vistaP.panel.revalidate();
        vistaP.panel.repaint();
        ControladorSolicitud ctrlS1 = new ControladorSolicitud(vistaSol, daos, sol, usu, vistaP);

        try {
            ctrlS1.mostrarFormularioSolicitud();
            ctrlS1.cargar();
            ctrlS1.limpiar();
        } catch (SQLException ex) {
            Logger.getLogger(RechazarSolicitud.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
