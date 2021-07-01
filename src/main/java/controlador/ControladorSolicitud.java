/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import modelo.DAOSolicitud;
import modelo.Solicitud;
import vista.VistaSolicitud;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;
import modelo.DAOTarea;
import modelo.Tarea;
import modelo.Usuario;
import vista.DialogEliminarSolicitud;
import vista.DialogSolicitud;
import vista.VistaPrincipal;

/**
 *
 * @author Claudio
 */
public class ControladorSolicitud implements ActionListener, MouseListener {

    public VistaSolicitud vistaS;
    private ProgramarSolicitud programar;
    public DAOSolicitud daos;
    public Solicitud solicitud;
    private DialogSolicitud vpSolicitud;
    private Usuario usu;
    private VistaPrincipal vistaP;
    private String id_sol;
    private DialogEliminarSolicitud vistaE;
    private Tarea tarea;
    private DAOTarea daoT;

    String[] columnas = {"ID", "MAQUINA", "DESCRIPCION", "ESTADO SOLICITUD"};
    ArrayList<Object[]> datos = new ArrayList<>();
    DefaultTableModel modelo = new DefaultTableModel(columnas, 0) {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };

    public ControladorSolicitud(VistaSolicitud vistaS, DAOSolicitud daos, Solicitud solicitud, Usuario usu, VistaPrincipal vistaP) {
        this.vistaS = vistaS;
        this.daos = daos;
        this.solicitud = solicitud;
        this.programar = programar;
        this.vpSolicitud = vpSolicitud;
        this.usu = usu;
        this.vistaS.btnAgregar.addActionListener(this);
        this.vistaS.jTable1.addMouseListener(this);
        this.vistaS.btnProgramar.addActionListener(this);
        this.vistaS.btnRechazar.addActionListener(this);
        this.vistaP = vistaP;
    }

    public void mostrarFormularioSolicitud() throws SQLException {
        vistaS.setVisible(true);
        vistaS.setSize(700, 602);
        vistaS.cbMaquina.setModel(daos.ob_maq());
        cargar();

    }

    public void cargar() {
        modelo.setRowCount(0);
        datos = daos.consultar();

        // for each
        for (Object[] obj : datos) {
            modelo.addRow(obj);
        }
        vistaS.jTable1.setModel(modelo);
//        System.out.println(usu.getUsuario());
        vistaS.txtNombreSolicitante.setText(usu.getNombre());

    }

    public void limpiar() {

        vistaS.cbMaquina.setSelectedIndex(0);
        vistaS.txtDescripcion.setText("");
        vistaS.txtObservaciones.setText("");
    }

    public boolean comprobarFormulario() {
        boolean validacion = false;
        try {
            if (vistaS.cbMaquina.getSelectedIndex() != 0) {
                if (!vistaS.txtDescripcion.getText().isEmpty()) {
                    if (!vistaS.txtNombreSolicitante.getText().isEmpty()) {
                        validacion = true;
                    } else {
                        JOptionPane.showMessageDialog(null, "por favor ingrese el nombre del solicitante del trabajo", "mensaje de error", JOptionPane.ERROR_MESSAGE);
                        validacion = false;
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "por favor ingrese una descripcion del trabajo a realizar", "mensaje de error", JOptionPane.ERROR_MESSAGE);
                    validacion = false;
                }
            } else {
                JOptionPane.showMessageDialog(null, "por favor seleccione la máquina", "mensaje de error", JOptionPane.ERROR_MESSAGE);
                validacion = false;
            }

        } catch (HeadlessException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        return validacion;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (vistaS.btnAgregar == e.getSource()) {
            if (comprobarFormulario()) {
                solicitud.setMaquina(vistaS.cbMaquina.getSelectedItem().toString());
                solicitud.setDescripcion(vistaS.txtDescripcion.getText());
                solicitud.setObservacion(vistaS.txtObservaciones.getText());
                solicitud.setSolicitante(vistaS.txtNombreSolicitante.getText());
                solicitud.setEstado("Abierta");
                if (daos.Agregar(solicitud)) {
                    JOptionPane.showMessageDialog(null, "Guardado");
                    cargar();
                    limpiar();
                } else {
                    JOptionPane.showMessageDialog(null, "Error al guardar");
                }

            }
        }
        if (vistaS.btnProgramar == e.getSource()) {
            if (id_sol == null) {
                JOptionPane.showMessageDialog(null, "seleccione una solicitud", "mensaje de error", JOptionPane.ERROR_MESSAGE);
            } else {
                DialogSolicitud dialog = new DialogSolicitud(vistaP, true);
                Tarea tarea = new Tarea();
                DAOTarea daoT = new DAOTarea();
                ProgramarSolicitud programar = new ProgramarSolicitud(dialog, solicitud, daos, usu, tarea, daoT, id_sol, vistaP);
                try {
                    programar.mostrarDialog();
                } catch (SQLException ex) {
                    Logger.getLogger(ControladorSolicitud.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        if (vistaS.btnRechazar == e.getSource()) {
            if (id_sol == null) {
                JOptionPane.showMessageDialog(null, "seleccione una solicitud", "mensaje de error", JOptionPane.ERROR_MESSAGE);

            }else{
            DialogEliminarSolicitud dialogE = new DialogEliminarSolicitud(vistaP, true);
            RechazarSolicitud rechazar = new RechazarSolicitud(dialogE, solicitud, daos, id_sol, usu, vistaP);
            try {
                rechazar.mostrarDialog();
            } catch (SQLException ex) {
                Logger.getLogger(ControladorSolicitud.class.getName()).log(Level.SEVERE, null, ex);
            }
        }}
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        id_sol = (String.valueOf(vistaS.jTable1.getValueAt(vistaS.jTable1.getSelectedRow(), 0)));

    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

}
