/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package MODELOS;

/**
 *
 * @author Aaron Diaz
 */
public class Recepcionista {
    private String Usuario;
    private String Contraseña;

    public Recepcionista(String Usuario, String Contraseña) {
        this.Usuario = Usuario;
        this.Contraseña = Contraseña;
    }

    public String getUsuario() {
        return Usuario;
    }
    
    //devuelve verdadero si usuario y contraseña coinciden; falso en caso contrario
    public boolean ValidarCredenciales(String Usuario, String Contraseña){
        
        //equals distingue mayusculas, si el valor es null devuelve false
        return this.Usuario.equals(Usuario) && this.Contraseña.equals(Contraseña);
    }
}
