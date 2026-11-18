/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package controladores;

import MODELOS.GestorHotel;
import MODELOS.Validador;
import vistas.FrmHotel;
import vistas.FrmLogin;

/**
 *
 * @author Aaron Diaz
 */
public class ControladorLogin {
    
    private final FrmLogin Vista;
    private final GestorHotel Gestor;

    public ControladorLogin(FrmLogin Vista) {
        this.Vista = Vista;
        this.Gestor = GestorHotel.getInstancia();
    }
    
    public void IniciarSesion(){
        String Usuario = Vista.getUsuario();
        String Contraseña = Vista.getContraseña();
        
        //si falta un campo por llenar 
        if (Validador.EsVacio(Usuario) || Validador.EsVacio(Contraseña)) {
            Vista.MostrarMensaje("Ingrese usuario y contraseña");
            return;
        }
        
        try{
            //el modelo valida y guarda la sesion activs
            Gestor.IniciarSesion(Usuario, Contraseña);
            
            //se abre si la cedenciales es correcta y cierra el login
            FrmHotel frmHotel = new FrmHotel();
            frmHotel.setVisible(true);
            Vista.dispose();
        } catch(IllegalArgumentException | IllegalStateException e ){
            Vista.MostrarMensaje(e.getMessage());
            Vista.LimpiarCampos();
        }
    }
    
}
