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
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import modelo.*;
import vista.*;

/**
 *
 * @author Claudio
 */
public class ControladorPrincipal implements ActionListener {

    private VistaPrincipal viewPrincipal;
    private Usuario usu;

    public ControladorPrincipal(VistaPrincipal viewPrincipal, Usuario usu) {
        this.viewPrincipal = viewPrincipal;
        this.viewPrincipal.btnCerrarSesion.addActionListener(this);
        this.viewPrincipal.jmCerrarSesion.addActionListener(this);
        this.viewPrincipal.jmCrearTarea.addActionListener(this);
        this.viewPrincipal.jmTareasPendientes.addActionListener(this);
        this.viewPrincipal.jmOTs.addActionListener(this);
        this.viewPrincipal.jmPlanes.addActionListener(this);
        this.viewPrincipal.jmMaquinas.addActionListener(this);
        this.viewPrincipal.jmMaq.addActionListener(this);
        this.viewPrincipal.jmCrearSolicitud.addActionListener(this);
        this.viewPrincipal.jmTrabajadores.addActionListener(this);
        this.viewPrincipal.jmTareas.addActionListener(this);
        this.viewPrincipal.jmVerSolicitudes.addActionListener(this);
        this.usu=usu;
    }

    public ControladorPrincipal() {

    }

    public void iniciarPantallaPrincipal() {
        viewPrincipal.setVisible(true);
        viewPrincipal.setTitle("SISTEMA DE MANTENIMIENTO PLANTA XXX");
        viewPrincipal.setExtendedState(6);
        viewPrincipal.jLabel1.setText(usu.getNombre());
        System.out.println(usu.getNombre());
    }

    public void despejar(Object obj) {
        viewPrincipal.panel.removeAll();
        viewPrincipal.panel.add((Component) obj, BorderLayout.CENTER);
        viewPrincipal.panel.revalidate();
        viewPrincipal.panel.repaint();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (viewPrincipal.btnCerrarSesion == e.getSource()
                || viewPrincipal.jmCerrarSesion == e.getSource()) {
            String[] botones = {"Si", "No"};
            int resp = JOptionPane.showOptionDialog(null,
                    "Desea Salir?",
                    "Sistema de Mantenimiento",
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.QUESTION_MESSAGE, null,
                    botones, botones[0]);
            if (resp == 0) {
                System.exit(0);
            } else {
                JOptionPane.showMessageDialog(null, "buuuu el compadre indeciso!!!");
            }
        }
        if (viewPrincipal.jmMaquinas == e.getSource()) {
            VistaMaquina vista = new VistaMaquina();
            Maquina maq = new Maquina();
            DAOMaquina daom = new DAOMaquina();
            ControladorMaquina ctrlM = new ControladorMaquina(vista, daom, maq);
            despejar(vista);
            ctrlM.iniciarFormMaquina();
        }
        if (viewPrincipal.jmCrearSolicitud == e.getSource()) {
            VistaSolicitud vistaSol = new VistaSolicitud();
            Solicitud sol = new Solicitud();
            DAOSolicitud daos = new DAOSolicitud();
            despejar(vistaSol);
            ControladorSolicitud ctrlS = new ControladorSolicitud(vistaSol, daos, sol,usu, viewPrincipal);
            try {
                ctrlS.mostrarFormularioSolicitud();
            } catch (SQLException ex) {
                Logger.getLogger(ControladorPrincipal.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (viewPrincipal.jmTrabajadores == e.getSource()) {
            VistaTrabajador vista = new VistaTrabajador();
            Trabajador trabajador = new Trabajador();
            DAOTrabajador dao = new DAOTrabajador();
            despejar(vista);
            ControladorTrabajador ctrlT = new ControladorTrabajador(vista, dao, trabajador, viewPrincipal);
            try {
                ctrlT.mostrar();
            } catch (SQLException ex) {
                Logger.getLogger(ControladorPrincipal.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
        if (viewPrincipal.jmTareasPendientes == e.getSource()) {
            VistaTarea vista=new VistaTarea();
            Tarea tarea=new Tarea();
            DAOTarea dao=new DAOTarea();
            despejar(vista);
            ControladorTarea ctrlT=new ControladorTarea(vista, tarea, dao);
            try {
                ctrlT.mostrar();
            } catch (SQLException ex) {
                Logger.getLogger(ControladorPrincipal.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if(viewPrincipal.jmVerSolicitudes==e.getSource()){
            VistaVerSolicitudes vistaVS=new VistaVerSolicitudes();
            Solicitud solicitud=new Solicitud();
            DAOSolicitud daoS=new DAOSolicitud();
            despejar(vistaVS);
            VerSolicitudes ctrlVS=new VerSolicitudes(vistaVS,daoS,solicitud);
            ctrlVS.mostrar();
        }

    }
}
