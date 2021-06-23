/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.util.ArrayList;

/**
 *
 * @author Claudio
 */
public interface CRUD {
    public boolean Agregar(Object obj);

    public boolean Modificar(Object obj);

    public boolean Eliminar(Object obj);

    public ArrayList<Object[]> consultar();
    
}
