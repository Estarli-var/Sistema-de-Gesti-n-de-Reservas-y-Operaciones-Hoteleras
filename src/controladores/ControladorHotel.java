/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package controladores;
import java.util.List;
import java.util.Locale;
import javax.swing.JOptionPane;
import MODELOS.GestorHotel;
import MODELOS.Habitacion;
import MODELOS.Huesped;
import vistas.FrmHotel;
import vistas.FrmLogin;
/**
 *
 * @author Aaron Diaz
 */
public class ControladorHotel {
    
    private final FrmHotel frmHotel;
    private final GestorHotel Gestor;

    public ControladorHotel(FrmHotel frmHotel) {
        this.frmHotel = frmHotel;
        this.Gestor = GestorHotel.getInstancia();
    }
    
    //Devuelve el usuasio con sesion activa
    
    public String getUsuarioActivo(){
        return Gestor.getSesionActiva().getUsuario();
    }
    
    public void CambiarUsuario(){
        int Respuesta = JOptionPane.showConfirmDialog(frmHotel, "Para ingresar con otro usuario debe cerrarse la sesión actual.\n¿Desea continuar?",
            "Cambiar usuario",JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        
        if (Respuesta == JOptionPane.YES_OPTION) {
            //los datos registrados se conservan
            Gestor.CerrarSesion();
            //se cierra la ventana principal
            frmHotel.dispose();
            //Login nuevo: campos vacio y mensaje oculto
            new FrmLogin().setVisible(true);
        }
    }
    
    public String RegistrarHuesped(String Identificacion, String Nombre, String Telefono, String Correo){
        try{
            Gestor.RegistrarHuesped(Identificacion, Nombre, Telefono, Correo);
            return null;
        }catch(IllegalArgumentException e){
            return e.getMessage();
        }
    }
    
    public List<Huesped> getHuespedes(){
        return Gestor.getHuespedes();
    }
    
    public String RegistrarHabitacion(String Numero, String Tipo){
        try{
            Gestor.RegistrarHabitacion(Numero, Tipo);
            return null;
        }catch(IllegalArgumentException e){
            return e.getMessage();
        }
    }

    public List<Habitacion> getHabitaciones(){
        return Gestor.getHabitaciones();
    }
    
    //pide al modelo la capacidad y la tarifa y retorna un arreglo de dos texto
    public String[] ObtenerCapacidadYTarifa(String Tipo){
        int Capacidad = Gestor.ObtenerCapacidadporTipo(Tipo);
        long Tarifa = Gestor.ObtenerTarifaporTipo(Tipo);
        return new String[] {String.valueOf(Capacidad), FormatearTarifa(Tarifa)};
    }
    
    public String FormatearTarifa(long Tarifa){
        //%,d separa los miles con coma, se cambia por punto
        return "₡" + String.format(Locale.US, "%,d", Tarifa).replace(",",".");
    }
    
}
