/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JOptionPane;
import modelo.DAOSolicitud;
import modelo.DAOTrabajador;
import modelo.Solicitud;
import modelo.Trabajador;
import vista.DialogModificarTrabajador;
import vista.VistaPrincipal;
import vista.VistaSolicitud;
import vista.VistaTrabajador;

/**
 *
 * @author Claudio
 */
public class ModificarTrabajador implements ActionListener,KeyListener {
    private Trabajador trabajador;
    private DAOTrabajador dao;
    private DialogModificarTrabajador dialog;
    private VistaPrincipal vistaP;
    private String rut;

    public ModificarTrabajador(Trabajador trabajador, DAOTrabajador dao, DialogModificarTrabajador dialog, 
            VistaPrincipal vistaP,String rut) {
        this.trabajador = trabajador;
        this.dao = dao;
        this.dialog = dialog;
        this.vistaP = vistaP;
        this.rut=rut;
        dialog.btnGuardar.addActionListener(this);
        dialog.txtNombre.addKeyListener(this);
        dialog.txtApellidoM.addKeyListener(this);
        dialog.txtApellidoM.addKeyListener(this);
        dialog.txtTelefono.addKeyListener(this);
    }
    public void mostrar() throws SQLException{
        
        dialog.cbCategoria.setModel(dao.ob_categoria());
         try {
            String [] datos=dao.ob_trabajador(rut);
             dialog.txtNombre.setText(datos[1]);
             dialog.txtApellidoP.setText(datos[2]);
             dialog.txtApellidoM.setText(datos[3]);
             dialog.txtRut.setText(datos[4]);
             dialog.txtTelefono.setText(datos[5]);
             dialog.txtCorreo.setText(datos[6]);
             dialog.cbCategoria.setSelectedItem(datos[7]);
             
             
        } catch (SQLException ex) {
            Logger.getLogger(ControladorTrabajador.class.getName()).log(Level.SEVERE, null, ex);
        }
         dialog.setLocationRelativeTo(null);
      dialog.setVisible(true); 
      dialog.setTitle("Procesar Solicitud");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(dialog.btnGuardar==e.getSource()){
            if (comprobarFormulario()) {
                trabajador.setNombre(dialog.txtNombre.getText());
                trabajador.setApellidoPaterno(dialog.txtApellidoP.getText());
                trabajador.setApellidoMaterno(dialog.txtApellidoM.getText());
                trabajador.setRut(dialog.txtRut.getText());
                trabajador.setTelefono(Integer.parseInt(dialog.txtTelefono.getText()));
                trabajador.setCorreo(dialog.txtCorreo.getText());
                trabajador.setCategoria(dialog.cbCategoria.getSelectedItem().toString());
            if (validarRut(trabajador.getRut()) && validarCorreo(trabajador.getCorreo())){
                if (dao.Modificar(trabajador)) {
                    JOptionPane.showMessageDialog(null, "Modificado");
                   volver();
                } else {
                    JOptionPane.showMessageDialog(null, "Error al Guardar");
                    volver();
                }
            }
            }
        }
    }
     public boolean comprobarFormulario() {
        boolean validacion = false;
        try {
            if (!dialog.txtNombre.getText().isEmpty()
                    && !dialog.txtApellidoP.getText().isEmpty()
                    && !dialog.txtApellidoM.getText().isEmpty()) {
                if (!dialog.txtRut.getText().isEmpty()) {
                    if (!dialog.txtTelefono.getText().isEmpty()) {
                        if (!dialog.txtCorreo.getText().isEmpty()) {
                            if (dialog.cbCategoria.getSelectedIndex() != 0) {
                                validacion = true;
                            } else {
                                JOptionPane.showMessageDialog(null, "por favor seleccione una categoría", "mensaje de error", JOptionPane.ERROR_MESSAGE);
                                validacion = false;
                            }
                        } else {
                            JOptionPane.showMessageDialog(null, "por favor ingrese el correo", "mensaje de error", JOptionPane.ERROR_MESSAGE);
                            validacion = false;
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "por favor ingrese el teléfono", "mensaje de error", JOptionPane.ERROR_MESSAGE);
                        validacion = false;
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "por favor ingrese el RUT", "mensaje de error", JOptionPane.ERROR_MESSAGE);
                    validacion = false;
                }

            } else {
                JOptionPane.showMessageDialog(null, "por favor ingrese el nombre completo", "mensaje de error", JOptionPane.ERROR_MESSAGE);
                validacion = false;
            }

        } catch (HeadlessException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        return validacion;
    }

    public boolean validarRut(String rut) {
        boolean validacion = false;
        try {
            rut = rut.toUpperCase();
            rut = rut.replace(".", "");
            rut = rut.replace("-", "");
            int rutAux = Integer.parseInt(rut.substring(0, rut.length() - 1));

            char dv = rut.charAt(rut.length() - 1);

            int m = 0, s = 1;
            for (; rutAux != 0; rutAux /= 10) {
                s = (s + rutAux % 10 * (9 - m++ % 6)) % 11;
            }
            if (dv == (char) (s != 0 ? s + 47 : 75)) {
                validacion = true;
            } else {
                JOptionPane.showMessageDialog(null, "por favor ingrese un RUT VÁLIDO", "mensaje de error", JOptionPane.ERROR_MESSAGE);
            }

        } catch (java.lang.NumberFormatException e) {
        } catch (Exception e) {
        }
        return validacion;
    }
    public boolean validarCorreo(String correo){
        boolean validacion=true;
            Pattern pattern = Pattern
                    .compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                            + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
            Matcher mather = pattern.matcher(correo);
            if (mather.find() == true) {
                
                validacion=true;

            } else {
                JOptionPane.showMessageDialog(null, "por favor ingrese un CORREO VÁLIDO", "mensaje de error", JOptionPane.ERROR_MESSAGE);
                validacion=false;
            }
        return validacion;
    }
    public void volver() {
        dialog.dispose();
        VistaTrabajador vistaT = new VistaTrabajador();
            Solicitud sol = new Solicitud();
            DAOSolicitud daos = new DAOSolicitud();
            vistaP.panel.removeAll();
        vistaP.panel.add((Component) vistaT, BorderLayout.CENTER);
        vistaP.panel.revalidate();
        vistaP.panel.repaint();
            ControladorTrabajador ctrlT = new ControladorTrabajador(vistaT, dao, trabajador,vistaP);
            
            try {
                ctrlT.mostrar();
                ctrlT.cargar();
                ctrlT.limpiar();
            } catch (SQLException ex) {
                Logger.getLogger(RechazarSolicitud.class.getName()).log(Level.SEVERE, null, ex);
            }
        
    }

    @Override
    public void keyTyped(KeyEvent e) {
        if (e.getSource() == dialog.txtNombre
                || e.getSource() == dialog.txtApellidoP
                || e.getSource() == dialog.txtApellidoM) {
            if (!Character.isLetter(e.getKeyChar())
                    && !(e.getKeyChar() == KeyEvent.VK_BACK_SPACE)) {
                e.consume();
                dialog.lblError.setText("Escribe solo letras");
            } else {
                dialog.lblError.setText("");
            }
        }

        if (e.getSource() == dialog.txtTelefono) {
            if (dialog.txtTelefono.getText().length() == 9) {
                e.consume();
            }
            char c = e.getKeyChar();
            if (c < '0' || c > '9') {
                e.consume();
            }
        }

    }

    

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
    
}

