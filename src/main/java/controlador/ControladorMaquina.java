package controlador;

import java.awt.BorderLayout;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import modelo.DAOMaquina;
import modelo.Maquina;
import vista.VistaMaquina;

public class ControladorMaquina implements ActionListener, MouseListener {
   private VistaMaquina vista;
    private DAOMaquina daom;
    private Maquina maquina;

    String[] columnas = {"ID", "NOMBRE", "UBICACION", "CODIGO"};
    ArrayList<Object[]> datos = new ArrayList<>();
    DefaultTableModel modelo = new DefaultTableModel(columnas, 0) {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };

    public ControladorMaquina(VistaMaquina vista, DAOMaquina daom, Maquina maquina) {
        this.vista = vista;
        this.daom = daom;
        this.maquina = maquina;
        vista.btnGuardar.addActionListener(this);
        vista.btnEliminar.addActionListener(this);
        vista.btnModificar.addActionListener(this);

    }

    public void iniciarFormMaquina() {
        vista.setVisible(true);
        vista.setSize(598, 656);
        vista.txtID.enable(false);
        vista.jTable1.setModel(modelo);
        cargar();
        vista.jTable1.addMouseListener(this);
    }

    public void cargar() {
        modelo.setRowCount(0);
        datos = daom.consultar();

        // for each
        for (Object[] obj : datos) {
            modelo.addRow(obj);
        }
        vista.jTable1.setModel(modelo);
        

    }

    public void limpiar() {

        vista.txtNombre.setText("");
        vista.txtUbicacion.setText("");
        vista.txtCodigo.setText("");
        vista.txtID.setText("");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (vista.btnGuardar == e.getSource()) {
            boolean verificar=true;
            if(comprobarFormulario()){
            maquina.setNombreMaquina(vista.txtNombre.getText());
            maquina.setUbicacion(vista.txtUbicacion.getText());
            maquina.setCodigo(vista.txtCodigo.getText());
            System.out.println("wena");
            if (daom.Agregar(maquina)) {
                JOptionPane.showMessageDialog(null, "Guardado");
                cargar();
                limpiar();
            } else {
                JOptionPane.showMessageDialog(null, "Error al Guardar");
            }
        }
        }
        if (e.getSource() == vista.btnEliminar) {
            maquina.setIdMaquina(Integer.parseInt(vista.txtID.getText()));
              if(daom.Eliminar(maquina))
              {JOptionPane.showMessageDialog(null, "Registro Eliminado");
                cargar();
                limpiar();
            } else {
                JOptionPane.showMessageDialog(null, "Error al Eliminar");
                limpiar();
            }
        }
         if (e.getSource() == vista.btnModificar) {
            maquina.setIdMaquina(Integer.parseInt(vista.txtID.getText()));
            maquina.setNombreMaquina(vista.txtNombre.getText());
            maquina.setUbicacion(vista.txtUbicacion.getText());
            maquina.setCodigo(vista.txtCodigo.getText());
              if(daom.Modificar(maquina))
              {JOptionPane.showMessageDialog(null, "Modificado");
                cargar();
                limpiar();
            } else {
                JOptionPane.showMessageDialog(null, "Error al Modificar");
                limpiar();
            }
        }  
        }
    public boolean comprobarFormulario(){
        boolean validacion=false;
        try{
            if(!vista.txtNombre.getText().isEmpty()){
                if(!vista.txtUbicacion.getText().isEmpty()){
                    if(!vista.txtCodigo.getText().isEmpty()){
                       validacion=true;
                    }else{
                        JOptionPane.showMessageDialog(null, "por favor ingrese el código de la máquina", "mensaje de error", JOptionPane.ERROR_MESSAGE);
                                                validacion = false;
                    }
                }else{
                    JOptionPane.showMessageDialog(null, "por favor ingrese la ubicacion de la máquina", "mensaje de error", JOptionPane.ERROR_MESSAGE);
                                                validacion = false;
                }
            }else{
                JOptionPane.showMessageDialog(null, "por favor ingrese el nombre de la máquina", "mensaje de error", JOptionPane.ERROR_MESSAGE);
                                                validacion = false;
            }
            
        }catch(HeadlessException e){
          JOptionPane.showMessageDialog(null, e.getMessage());  
        }
        return validacion;
    }
    @Override
    public void mouseClicked(MouseEvent e) {    
    }

    @Override
    public void mousePressed(MouseEvent e) {
        vista.txtNombre.setText(String.valueOf(vista.jTable1.getValueAt(vista.jTable1.getSelectedRow(), 1)));
        vista.txtUbicacion.setText(String.valueOf(vista.jTable1.getValueAt(vista.jTable1.getSelectedRow(), 2)));
        vista.txtCodigo.setText(String.valueOf(vista.jTable1.getValueAt(vista.jTable1.getSelectedRow(), 3)));
        vista.txtID.setText(String.valueOf(vista.jTable1.getValueAt(vista.jTable1.getSelectedRow(), 0)));
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
