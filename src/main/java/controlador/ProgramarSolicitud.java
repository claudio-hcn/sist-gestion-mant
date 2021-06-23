/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import modelo.Solicitud;
import vista.VistaSolicitud2;

/**
 *
 * @author Claudio
 */
public class ProgramarSolicitud implements ActionListener{
    private VistaSolicitud2 vista;
    private Solicitud solicitud;

    public ProgramarSolicitud(VistaSolicitud2 vista, Solicitud solicitud) {
        this.vista = vista;
        this.solicitud = solicitud;
    }
    public void mostrarForm(){
        vista.setLocationRelativeTo(null);
        vista.setVisible(true);
        vista.setTitle("PROCESAR SOLICITUD");
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }


    
}
