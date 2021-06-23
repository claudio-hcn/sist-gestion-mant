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
import java.util.Date;
import javax.swing.table.DefaultTableModel;
import vista.VistaSolicitud2;

/**
 *
 * @author Claudio
 */
public class ControladorSolicitud implements ActionListener, MouseListener {

    public VistaSolicitud vistaS;
    public DAOSolicitud daos;
    public Solicitud solicitud;
    private VistaSolicitud2 vista2;

    String[] columnas = {"ID", "MAQUINA", "DESCRIPCION"};
    ArrayList<Object[]> datos = new ArrayList<>();
    DefaultTableModel modelo = new DefaultTableModel(columnas, 0) {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };

    public ControladorSolicitud(VistaSolicitud vistaS, DAOSolicitud daos, Solicitud solicitud) {
        this.vistaS = vistaS;
        this.daos = daos;
        this.solicitud = solicitud;
        this.vistaS.btnAgregar.addActionListener(this);
        this.vistaS.jTable1.addMouseListener(this);
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

    }

    public void limpiar() {

        vistaS.cbMaquina.setSelectedIndex(0);
        vistaS.txtDescripcion.setText("");
        vistaS.txtObservaciones.setText("");
        vistaS.txtNombreSolicitante.setText("");
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
                Date objfecha = new Date();
                solicitud.setHoraSolicitud(objfecha.toString());
                if (daos.Agregar(solicitud)) {
                    JOptionPane.showMessageDialog(null, "Guardado");
                    cargar();
                    limpiar();
                } else {
                    JOptionPane.showMessageDialog(null, "Error al guardar");
                }

            }
        }

    }

    @Override
    public void mouseClicked(MouseEvent e) {
        VistaSolicitud2 vista2 = new VistaSolicitud2();
        ProgramarSolicitud programar = new ProgramarSolicitud(vista2, solicitud);
        programar.mostrarForm();
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
