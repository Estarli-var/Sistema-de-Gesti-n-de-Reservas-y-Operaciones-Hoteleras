package MODELOS;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */



/**
 *
 * @author Aaron Diaz
 */
public class Huesped {
    
    private String Identificacion;
    private String NombreCompleto;
    private String Telefono;
    private String Correo;
    
    public Huesped(String Identificacion,String NombreCompleto,String Telefono,String Correo ){
        this.Identificacion = Identificacion;
        this.NombreCompleto = NombreCompleto;
        this.Telefono = Telefono;
        this.Correo = Correo;
    }

    public String getIdentificacion() {
        return Identificacion;
    }

    public String getNombreCompleto() {
        return NombreCompleto;
    }

    public String getTelefono() {
        return Telefono;
    }

    public String getCorreo() {
        return Correo;
    }

    @Override
    public String toString() {
        return Identificacion + " - " + NombreCompleto;
    }
    
}