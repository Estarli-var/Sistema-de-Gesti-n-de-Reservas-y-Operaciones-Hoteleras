/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package MODELOS;

/**
 *
 * @author Aaron Diaz
 */
public class Validador {

    public Validador() {
    }
    
    public static boolean EsVacio(String Text){
        return Text == null || Text.trim().isEmpty();
    }
    
    public static boolean CorreoValido(String Correo){
        // si es vacio no es válido
        if (EsVacio(Correo)) {
            return false;
        }
        
        // Posicion del primer @ y del ultimo @: si son distintas, hay más de uno.
        int posic = Correo.indexOf("@");
        
        if (posic == - 1 || posic != Correo.lastIndexOf("@")) {
            // por si ahi mas de un @
            return false;
        }
        
        String Antes = Correo.substring(0, posic);
        String Despues = Correo.substring(posic + 1);
        
        if (EsVacio(Antes) || EsVacio(Despues)) {
            return false;
        }
        
        // Despues del @ debe haber al menos un punto
        return Despues.contains(".");
    }
    
}
