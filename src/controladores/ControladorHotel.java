/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package controladores;
import MODELOS.ActualizadorIndicadores;
import java.util.List;
import java.util.Locale;
import javax.swing.JOptionPane;
import MODELOS.GestorHotel;
import MODELOS.Habitacion;
import MODELOS.Huesped;
import MODELOS.Indicadores;
import MODELOS.Recepcionista;
import MODELOS.Reserva;
import MODELOS.SupervisorReservas;
import java.time.LocalDate;
import vistas.FrmHotel;
import vistas.FrmLogin;
/**
 *
 * @author Aaron Diaz
 */
public class ControladorHotel {
    
    private final FrmHotel frmHotel;
    private final GestorHotel Gestor;
    private Thread hiloSupervisor;
    private Thread hiloIndicadores;

    public ControladorHotel(FrmHotel frmHotel) {
        this.frmHotel = frmHotel;
        this.Gestor = GestorHotel.getInstancia();
    }
    
    //Devuelve el usuasio con sesion activa
    
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
    
    public boolean HayHilosActivos() {
    return (hiloSupervisor != null && hiloSupervisor.isAlive())
        || (hiloIndicadores != null && hiloIndicadores.isAlive());
}

    public String IniciarHilos() {
        if (HayHilosActivos()) {
            return "Ya hay procesos en ejecución. Espere a que terminen.";
        }
        SupervisorReservas supervisor = new SupervisorReservas(
            msg -> System.out.println("[Callback supervisor] " + msg)
        );
        ActualizadorIndicadores actualizador = new ActualizadorIndicadores(
        indicadores -> this.frmHotel.actualizarIndicadores(
        indicadores.getTotalHabitaciones(),
        indicadores.getOcupadas(),
        indicadores.getNoOcupadas(),
        indicadores.getConfirmadas(),
        indicadores.getVencidas(),
        FormatearTarifa(indicadores.getIngresosEsperados()))
);
        hiloSupervisor = new Thread(supervisor, "Hilo-Supervisor");
        hiloIndicadores = new Thread(actualizador, "Hilo-Indicadores");
        hiloSupervisor.start();
        hiloIndicadores.start();
        return null;
    }
    public void ActualizarIndicadoresEnVista() {
    Indicadores ind = Gestor.calcularIndicadores();
    frmHotel.actualizarIndicadores(
        ind.getTotalHabitaciones(),
        ind.getOcupadas(),
        ind.getNoOcupadas(),
        ind.getConfirmadas(),
        ind.getVencidas(),
        FormatearTarifa(ind.getIngresosEsperados()));
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
    
    public String RegistrarReserva(Huesped huesp, Habitacion habi, int personas, LocalDate entrada, LocalDate salida) {
            try {
                Gestor.registrarReserva(huesp, habi, personas, entrada, salida);
                return null;
            } catch (IllegalArgumentException | IllegalStateException e) {
                return e.getMessage();
            }
        }
        public List<Reserva> getReservas() { 
            return Gestor.getReservas(); 
        }
        
        public String ObtenerProximoCodigo() { 
            return Gestor.obtenerProximoCodigo(); 
        }
        
        public Reserva BuscarReserva(String codigo) { 
            return Gestor.buscarReserva(codigo);
        }
    
    public String RealizarCheckIn(String codigo) {
        try { Gestor.realizarCheckIn(codigo);
        return null;
        
        }catch (IllegalArgumentException | IllegalStateException e) {
            return e.getMessage();
        }
    }
        
    public String RealizarCheckOut(String codigo) { 
        try { Gestor.realizarCheckOut(codigo); 
        return null; 
        }
        catch (IllegalArgumentException | IllegalStateException e) {
            return e.getMessage();
        }
    }
    
    public int ConsultarDisponibilidad(LocalDate fecha) {
        return Gestor.consultarDisponibilidad(fecha); 
        }
    
    public List<String> GetHabitacionesDisponibles(LocalDate fecha) {
        return Gestor.getHabitacionesDisponibles(fecha);
    }
    
    public List<Reserva> SimularVencimientos(LocalDate fecha) {
        return Gestor.simularVencimientos(fecha);
    }
    
    public Indicadores CalcularIndicadores() { 
        return Gestor.calcularIndicadores(); 
    }
    
    public String getUsuarioActivo() {
    Recepcionista r = Gestor.getSesionActiva();
    return r != null ? r.getUsuario() : "Sin sesión";
}
}
