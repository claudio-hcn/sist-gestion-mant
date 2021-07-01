
package controlador;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import modelo.DAOSolicitud;
import modelo.Solicitud;
import modelo.Usuario;
import vista.DialogEliminarSolicitud;
import vista.VistaPrincipal;
import vista.VistaSolicitud;

public class RechazarSolicitud implements ActionListener{
    private Solicitud solicitud;
    private DAOSolicitud dao;
    private DialogEliminarSolicitud dialog;
    private String idSol;
    private ControladorSolicitud ctrlS;
    private VistaSolicitud vista;
    private Usuario usu;
    private VistaPrincipal vistaP;

    public RechazarSolicitud(DialogEliminarSolicitud dialog,Solicitud solicitud, DAOSolicitud dao,  String idSol, Usuario usu, VistaPrincipal vistaP) {
        this.solicitud = solicitud;
        this.dao = dao;
        this.dialog = dialog;
        this.idSol=idSol;
        this.dialog.btnRechazar.addActionListener(this);
        this.dialog.btnCerrar.addActionListener(this);
        this.usu=usu;
        this.vistaP=vistaP;
        
    }
    
    public void mostrarDialog() throws SQLException{
        System.out.println(idSol);
      String[] wea=dao.ob_sol(idSol);
        for (int i = 0; i <wea.length; i++) {
            System.out.println(wea[i]);
            
        }
      dialog.jLabel1.setText(wea[1]);
      dialog.jTextField2.setText(wea[2]);
     dialog.jTextField2.enable(false);
      dialog.jLabel3.setText(wea[0]);
      dialog.setLocationRelativeTo(null);
      dialog.setVisible(true); 
      dialog.setTitle("Rechazar Solicitud");
      
      
  }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(dialog.btnRechazar==e.getSource()){
            String motivo=(dialog.cbMotivo.getSelectedItem().toString());
            String nota=(dialog.txtNota.getText());
            try {
                dao.estadoRechazada(idSol,motivo,nota);
            } catch (SQLException ex) {
                Logger.getLogger(RechazarSolicitud.class.getName()).log(Level.SEVERE, null, ex);
            }
           volver();
           
           
        }
        if(dialog.btnCerrar==e.getSource()){
            volver();
        }
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
            ControladorSolicitud ctrlS1 = new ControladorSolicitud(vistaSol, daos, sol,usu,vistaP);
            
            try {
                ctrlS1.mostrarFormularioSolicitud();
                ctrlS1.cargar();
                ctrlS1.limpiar();
            } catch (SQLException ex) {
                Logger.getLogger(RechazarSolicitud.class.getName()).log(Level.SEVERE, null, ex);
            }
        
    }
    
}
