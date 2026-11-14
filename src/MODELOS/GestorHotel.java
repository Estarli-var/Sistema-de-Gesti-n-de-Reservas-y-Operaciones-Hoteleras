/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package MODELOS;

import java.util.List;
import java.util.ArrayList;

/**
 *
 * @author Aaron Diaz
 */
public class GestorHotel {
    private static final GestorHotel instancia = new GestorHotel();
    
    private final List<Huesped> huespedes;
    private final List<Habitacion> habitaciones;
    private final List<Recepcionista> recepcionistas;
    private Recepcionista SesionActiva;
    
    //para el objeto candado para synchronized
    private final Object Lock;

    public GestorHotel() {
        this.huespedes = new ArrayList<>();
        this.habitaciones = new ArrayList<>();
        this.recepcionistas = new ArrayList<>();
        this.SesionActiva = null;
        this.Lock = new Object();
        
        recepcionistas.add(new Recepcionista("recepcion1","Hotel123"));
        recepcionistas.add(new Recepcionista("recepcion2","Hotel456"));
    }
    
    public static GestorHotel getInstancia(){
        return instancia;
    }
    
    public void IniciarSesion(String Usuario, String Contraseña){
        synchronized (Lock){
            if (SesionActiva != null) {
                throw new IllegalStateException("Ya hay un sesión activa");
            }
            
            //Se prueba con cada recepcionista predefinido
            for (Recepcionista recepci : recepcionistas) {
                if (recepci.ValidarCredenciales(Usuario, Contraseña)) {
                    SesionActiva = recepci;
                    return;
                }
            }
            throw new IllegalArgumentException("Usuario o contraseña incorrectos");
        }
    }

    //Cirere de sesion activas
    public void CerrarSesion(){
        synchronized(Lock){
            SesionActiva = null;
        }
    }
    
    //devuelve al recepcionista con la sesion activa o null si no hay
    public Recepcionista getSesionActiva(){
        synchronized(Lock){
            return SesionActiva;
        }
    }
    
    public void RegistrarHuesped(String Identificacion, String Nombre, String Telefono, String Correo){
        if (Validador.EsVacio(Identificacion) || Validador.EsVacio(Nombre) || Validador.EsVacio(Telefono) || Validador.EsVacio(Correo)) {
           throw new IllegalArgumentException("Todos los campos son OBLIGATORIOS"); 
        }
        
        Identificacion = Identificacion.trim();
        Nombre = Nombre.trim();
        Telefono = Telefono.trim();
        Correo = Correo.trim();
        
        //formato del correo
        if (Validador.CorreoValido(Correo)) {
            throw new IllegalArgumentException("El correo electronico no es válido");
        }
        
        synchronized(Lock){
            //identificacion debe ser unica
            if (BuscarHuesped(Identificacion) != null) {
                throw new IllegalArgumentException("Ya existe un huésped con esa identificacion");                
            }
            huespedes.add(new Huesped(Identificacion, Nombre, Telefono, Correo));
        }
    }
        //devuelve una copia de la lista
        public List<Huesped> getHuespedes(){
            synchronized(Lock){
                return new ArrayList<>(huespedes);
            }
        }
        
        public Huesped BuscarHuesped(String Identificacion){
            if (Identificacion == null) {
                return null;
            }
            synchronized(Lock){
                for (Huesped huesp : huespedes) {
                    if (huesp.getIdentificacion().equals(Identificacion.trim())) {
                        return huesp;
                    }
                }
                return null;
            }
        }
        
        public void RegistrarHabitacion(String Numero, String Tipo){
            if (Validador.EsVacio(Numero)) {
                throw new IllegalArgumentException("El número de habitacion es OBLIGATORIO");
            }
            Numero = Numero.trim();
            
            synchronized(Lock){
                if (BuscarHabitacion(Numero) != null) {
                    throw new IllegalArgumentException("Ya existe una habitación con ese número");
                }
                habitaciones.add(HabitacionFactory.crearHabitacion(Numero, Tipo));
            }
        }
        public List<Habitacion> getHabitaciones(){
            synchronized(Lock){
                return new ArrayList<>(habitaciones);
            }
        }
        
        public Habitacion BuscarHabitacion(String Numero){
            if (Numero == null) {
                return null;
            }
            synchronized(Lock){
                for (Habitacion habi : habitaciones) {
                    if (habi.getNumero().equals(Numero.trim())) {
                        return habi;
                    }
                }
                return null;
            }
        }
        
        public int ObtenerCapacidadporTipo(String Tipo){
            return HabitacionFactory.crearHabitacion("Temporal",Tipo).getCapacidad();
        }
        
        public long ObtenerTarifaporTipo(String Tipo){
            return HabitacionFactory.crearHabitacion("Temporal",Tipo).getTarifa();
        }
        
}    