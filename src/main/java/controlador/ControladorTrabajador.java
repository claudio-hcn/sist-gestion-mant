/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import modelo.DAOTrabajador;
import modelo.Trabajador;
import vista.VistaTrabajador;

/**
 *
 * @author Claudio
 */
public class ControladorTrabajador implements ActionListener, MouseListener, KeyListener {
    
    private VistaTrabajador vista;
    private DAOTrabajador dao;
    private Trabajador trabajador;
    
    String[] columnas = {"ID", "NOMBRE", "APELLIDO PATERNO", "APELLIDO MATERNO", "RUT", "TELEFONO", "CORREO", "CATEGORIA"};
    ArrayList<Object[]> datos = new ArrayList<>();
    DefaultTableModel modelo = new DefaultTableModel(columnas, 0) {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };
    
    public ControladorTrabajador(VistaTrabajador vista, DAOTrabajador dao, Trabajador trabajador) {
        this.vista = vista;
        this.dao = dao;
        this.trabajador = trabajador;
        vista.btnGuardar.addActionListener(this);
        vista.btnEliminar.addActionListener(this);
        vista.btnModificar.addActionListener(this);
        vista.jTable1.addMouseListener(this);
        vista.txtNombre.addKeyListener(this);
        vista.txtApellidoP.addKeyListener(this);
        vista.txtApellidoM.addKeyListener(this);
        vista.txtTelefono.addKeyListener(this);
        vista.txtCorreo.addKeyListener(this);
    }
    
    public void mostrar() throws SQLException {
        vista.setVisible(true);
        vista.setSize(800, 448);
        vista.txtID.enable(false);
        vista.cbCategoria.setModel(dao.ob_categoria());
        vista.jTable1.addMouseListener(this);
        cargar();
        
    }
    
    public void cargar() {
        modelo.setRowCount(0);
        datos = dao.consultar();

        // for each
        for (Object[] obj : datos) {
            modelo.addRow(obj);
        }
        //       vista.jTable1.addMouseListener(this);
        vista.jTable1.setModel(modelo);
        
    }
    
    public void limpiar() {
        
        vista.txtNombre.setText("");
        vista.txtApellidoP.setText("");
        vista.txtApellidoM.setText("");
        vista.txtRut.setText("");
        vista.txtTelefono.setText("");
        vista.txtCorreo.setText("");
        vista.cbCategoria.setSelectedIndex(0);
        
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if (vista.btnGuardar == e.getSource()) {
            trabajador.setNombre(vista.txtNombre.getText());
            trabajador.setApellidoPaterno(vista.txtApellidoP.getText());
            trabajador.setApellidoMaterno(vista.txtApellidoM.getText());
            trabajador.setRut(vista.txtRut.getText());
            trabajador.setTelefono(Integer.parseInt(vista.txtTelefono.getText()));
            trabajador.setCorreo(vista.txtCorreo.getText());
            trabajador.setCategoria(vista.cbCategoria.getSelectedItem().toString());
            if (comprobarFormulario() && validarRut(trabajador.getRut())) {
                if (dao.Agregar(trabajador)) {
                    JOptionPane.showMessageDialog(null, "Guardado");
                    cargar();
                    limpiar();
                } else {
                    JOptionPane.showMessageDialog(null, "Error al Guardar");
                }
            }
        }
        if (e.getSource() == vista.btnEliminar) {
            trabajador.setIdTrabajador(Integer.parseInt(vista.txtID.getText()));
            if (dao.Eliminar(trabajador)) {
                JOptionPane.showMessageDialog(null, "Registro Eliminado");
                cargar();
                limpiar();
            } else {
                JOptionPane.showMessageDialog(null, "Error al Eliminar");
                limpiar();
            }
        }
        if (e.getSource() == vista.btnModificar) {
            trabajador.setIdTrabajador(Integer.parseInt(vista.txtID.getText()));
            trabajador.setNombre(vista.txtNombre.getText());
            trabajador.setApellidoPaterno(vista.txtApellidoP.getText());
            trabajador.setApellidoMaterno(vista.txtApellidoM.getText());
            trabajador.setRut(vista.txtRut.getText());
            trabajador.setTelefono(Integer.parseInt(vista.txtTelefono.getText()));
            trabajador.setCorreo(vista.txtCorreo.getText());
            trabajador.setCategoria(vista.cbCategoria.getSelectedItem().toString());
            if (comprobarFormulario() && validarRut(trabajador.getRut())) {
                if (dao.Modificar(trabajador)) {
                    JOptionPane.showMessageDialog(null, "Modificado");
                    cargar();
                    limpiar();
                } else {
                    JOptionPane.showMessageDialog(null, "Error al Modificar");
                    limpiar();
                }
            }
        }
    }
    
    public boolean comprobarFormulario() {
        boolean validacion = false;
        try {
            if (!vista.txtNombre.getText().isEmpty()
                    && !vista.txtApellidoP.getText().isEmpty()
                    && !vista.txtApellidoM.getText().isEmpty()) {
                if (!vista.txtRut.getText().isEmpty()) {
                    if (!vista.txtTelefono.getText().isEmpty()) {
                        if (!vista.txtCorreo.getText().isEmpty()) {
                            if (vista.cbCategoria.getSelectedIndex() != 0) {
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
    
    @Override
    public void mouseClicked(MouseEvent e) {
        vista.txtNombre.setText(String.valueOf(vista.jTable1.getValueAt(vista.jTable1.getSelectedRow(), 1)));
        vista.txtApellidoP.setText(String.valueOf(vista.jTable1.getValueAt(vista.jTable1.getSelectedRow(), 2)));
        vista.txtApellidoM.setText(String.valueOf(vista.jTable1.getValueAt(vista.jTable1.getSelectedRow(), 3)));
        vista.txtRut.setText(String.valueOf(vista.jTable1.getValueAt(vista.jTable1.getSelectedRow(), 4)));
        vista.txtTelefono.setText(String.valueOf(vista.jTable1.getValueAt(vista.jTable1.getSelectedRow(), 5)));
        vista.txtCorreo.setText(String.valueOf(vista.jTable1.getValueAt(vista.jTable1.getSelectedRow(), 6)));
        vista.cbCategoria.setSelectedItem(String.valueOf(vista.jTable1.getValueAt(vista.jTable1.getSelectedRow(), 7)));
        vista.txtID.setText(String.valueOf(vista.jTable1.getValueAt(vista.jTable1.getSelectedRow(), 0)));
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
    
    @Override
    public void keyTyped(KeyEvent e) {
        if (e.getSource() == vista.txtNombre
                || e.getSource() == vista.txtApellidoP
                || e.getSource() == vista.txtApellidoM) {
            if (!Character.isLetter(e.getKeyChar())
                    && !(e.getKeyChar() == KeyEvent.VK_BACK_SPACE)) {
                e.consume();
                vista.lblError.setText("Escribe solo letras");
            } else {
                vista.lblError.setText("");
            }
        }
        
        if (e.getSource() == vista.txtTelefono) {
            if (vista.txtTelefono.getText().length() == 9) {
                e.consume();
            }
            char c = e.getKeyChar();
            if (c < '0' || c > '9') {
                e.consume();
            }            
        }
        if (e.getSource() == vista.txtCorreo){
            Pattern pattern = Pattern
                .compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                        + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
        Matcher mather = pattern.matcher(vista.txtCorreo.getText());
        if (mather.find() == true) {
            vista.lblError.setText("");

        } else {
            vista.lblError.setText("ingresa un correo Vàlido");
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
