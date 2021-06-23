/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 *
 * @author acer
 */
public class DAOUsuario implements Operacion {

    Usuario usu = new Usuario();
    //patron singleton
    Conexion conx = Conexion.getInstance();
    Connection con;

    @Override
    public boolean validarUsuario(Object obj) {
        usu = (Usuario) obj;
        //String sql = "UPDATE  persona SET Rut =?,Nombre = ?,Apellidos =?  WHERE Id =?";
        String sql = "SELECT * FROM usuarios WHERE usuario = ? AND clave = ?";
        Connection con;
        PreparedStatement pst;
        ResultSet rs;
        boolean usuarioValido = false;
        try {
            con = (Connection) conx.conectar();
            pst = (PreparedStatement) con.prepareStatement(sql);

            pst.setString(1, usu.getUsuario());
            pst.setString(2, usu.getClave());

            rs = pst.executeQuery();

            while (rs.next()) {
                usuarioValido = true;
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Ocurrio un errror" + e.getMessage());

        }
        return usuarioValido;

    }
}
