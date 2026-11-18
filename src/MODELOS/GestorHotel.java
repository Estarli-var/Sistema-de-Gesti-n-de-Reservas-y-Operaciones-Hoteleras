/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package MODELOS;

import java.util.List;
import java.util.ArrayList;
import ENUMs.EstadoReserva;
import java.time.LocalDate;

/**
 *
 * @author Aaron Diaz
 */
public class GestorHotel {
    private static final GestorHotel instancia = new GestorHotel();
    
    private final List<Huesped> huespedes;
    private final List<Habitacion> habitaciones;
    private final List<Recepcionista> recepcionistas;
    private final List<Reserva> reservas;
    private int contadorReservas;
    private Recepcionista SesionActiva;
    
    //para el objeto candado para synchronized
    private final Object Lock;

    private GestorHotel() {
        this.huespedes = new ArrayList<>();
        this.habitaciones = new ArrayList<>();
        this.recepcionistas = new ArrayList<>();
        this.SesionActiva = null;
        this.Lock = new Object();
        this.reservas = new ArrayList<>();
        this.contadorReservas = 1;
        
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
        if (!Validador.CorreoValido(Correo)) {
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
        
        
    public void registrarReserva(Huesped huesped,Habitacion habitacion,int personas,LocalDate entrada,LocalDate salida) {

        if (huesped == null || habitacion == null
                || entrada == null || salida == null) {
            throw new IllegalArgumentException(
                    "Todos los datos de la reserva son obligatorios.");
        }

        if (personas <= 0) {
            throw new IllegalArgumentException(
                    "La cantidad de personas debe ser mayor que cero.");
        }

        if (entrada.isBefore(LocalDate.now())) {
            throw new IllegalArgumentException(
                    "La fecha de entrada no puede ser anterior a hoy.");
        }

        if (!salida.isAfter(entrada)) {
            throw new IllegalArgumentException(
                    "La fecha de salida debe ser posterior a la entrada.");
        }

        synchronized (Lock) {

            if (SesionActiva == null) {
                throw new IllegalStateException(
                        "Debe iniciar sesión para registrar una reserva.");
            }

            // Recuperar los objetos originales del sistema.
            Huesped huespedRegistrado =
                    BuscarHuesped(huesped.getIdentificacion());

            if (huespedRegistrado == null) {
                throw new IllegalArgumentException(
                        "El huésped no está registrado.");
            }

            Habitacion habitacionRegistrada =
                    BuscarHabitacion(habitacion.getNumero());

            if (habitacionRegistrada == null) {
                throw new IllegalArgumentException(
                        "La habitación no está registrada.");
            }

            if (personas > habitacionRegistrada.getCapacidad()) {
                throw new IllegalArgumentException(
                        "La cantidad de personas supera la capacidad de la habitación.");
            }

            if (!hayDisponibilidad(habitacionRegistrada, entrada, salida)) {
                throw new IllegalArgumentException(
                        "La habitación ya está reservada para ese período.");
            }

            String codigo = obtenerProximoCodigo();

            Reserva nuevaReserva = new Reserva(
                    codigo,
                    huespedRegistrado,
                    habitacionRegistrada,
                    personas,
                    entrada,
                    salida,
                    SesionActiva.getUsuario()
            );

            reservas.add(nuevaReserva);
            contadorReservas++;
        }
    }

    public List<Reserva> getReservas() {
        synchronized (Lock) {
            return new ArrayList<>(reservas);
        }
    }

    public Reserva buscarReserva(String codigo) {

        if (codigo == null) {
            return null;
        }

    synchronized (Lock) {
        for (Reserva reserva : reservas) {
            if (reserva.getCodigo().equalsIgnoreCase(codigo.trim())) {
                return reserva;
            }
        }
        return null;
    }
}

    public String obtenerProximoCodigo() {
        synchronized (Lock) {
            return String.format("R%04d", contadorReservas);
        }
    }


    public boolean hayDisponibilidad(
            Habitacion habitacion,
            LocalDate entrada,
            LocalDate salida) {

        if (habitacion == null || entrada == null || salida == null) {
            throw new IllegalArgumentException(
                    "La habitación y las fechas son obligatorias.");
        }

        if (!salida.isAfter(entrada)) {
            throw new IllegalArgumentException(
                    "El período de reserva no es válido.");
        }

        synchronized (Lock) {

            for (Reserva reserva : reservas) {

                boolean mismaHabitacion =
                        reserva.getHabitacion().getNumero()
                                .equals(habitacion.getNumero());

                boolean estadoActivo =
                        reserva.getEstado() == EstadoReserva.CONFIRMADA
                        || reserva.getEstado() == EstadoReserva.HOSPEDADO;

                if (mismaHabitacion && estadoActivo
                        && reserva.seSuperpone(entrada, salida)) {
                    return false;
                }
            }

            return true;
        }
    }
    
    public void realizarCheckIn(String codigo) {

        if (codigo == null || codigo.trim().isEmpty()) {
            throw new IllegalArgumentException(
                    "El código de la reserva es obligatorio.");
        }

        synchronized (Lock) {

            Reserva reserva = buscarReserva(codigo);

            if (reserva == null) {
                throw new IllegalArgumentException(
                        "No existe una reserva con ese código.");
            }

            if (reserva.getEstado() != EstadoReserva.CONFIRMADA) {
                throw new IllegalStateException(
                        "La reserva no se encuentra confirmada.");
            }

            LocalDate hoy = LocalDate.now();

            if (!reserva.getFechaEntrada().equals(hoy)) {
                throw new IllegalStateException(
                        "El check-in solo puede realizarse en la fecha de entrada.");
            }

            reserva.hacerCheckIn();
        }
    }
    
    public void realizarCheckOut(String codigo) {

        if (codigo == null || codigo.trim().isEmpty()) {
            throw new IllegalArgumentException(
                    "El código de la reserva es obligatorio.");
        }

        synchronized (Lock) {

            Reserva reserva = buscarReserva(codigo);

            if (reserva == null) {
                throw new IllegalArgumentException(
                        "No existe una reserva con ese código.");
            }

            if (reserva.getEstado() != EstadoReserva.HOSPEDADO) {
                throw new IllegalStateException(
                        "No se puede realizar el check-out porque el huésped no está hospedado.");
            }

            LocalDate fechaActual = LocalDate.now();

            reserva.hacerCheckOut(fechaActual);
        }
    }
    public int consultarDisponibilidad(LocalDate fecha) {

        if (fecha == null) {
            throw new IllegalArgumentException(
                    "La fecha de consulta es obligatoria.");
        }

        synchronized (Lock) {

            return Recursividad.contarDisponibles(
                    habitaciones,
                    reservas,
                    fecha,
                    0
            );
        }
    }
    
    public List<String> getHabitacionesDisponibles(LocalDate fecha) {

        if (fecha == null) {
            throw new IllegalArgumentException(
                    "La fecha de consulta es obligatoria.");
        }

        synchronized (Lock) {

            List<String> disponibles = new ArrayList<>();

            for (Habitacion habitacion : habitaciones) {

                if (Recursividad.estaLibre(
                        habitacion,
                        reservas,
                        fecha)) {

                    disponibles.add(habitacion.getNumero());
                }
            }

            return disponibles;
        }
    }
    
    public void actualizarVencidas(LocalDate hoy) {

        if (hoy == null) {
            throw new IllegalArgumentException(
                    "La fecha de actualización es obligatoria.");
        }

        synchronized (Lock) {

            for (Reserva reserva : reservas) {

                if (reserva.vencidaEn(hoy)) {
                    reserva.marcarVencida();
                }
            }
        }
    }
    
    public List<Reserva> simularVencimientos(LocalDate fechaReferencia) {

        if (fechaReferencia == null) {
            throw new IllegalArgumentException(
                    "La fecha de referencia es obligatoria.");
        }

        if (fechaReferencia.isBefore(LocalDate.now())) {
            throw new IllegalArgumentException(
                    "La fecha de referencia no puede ser anterior a hoy.");
        }

        synchronized (Lock) {

            List<Reserva> reservasConfirmadas = new ArrayList<>();

            for (Reserva reserva : reservas) {

                if (reserva.getEstado() == EstadoReserva.CONFIRMADA) {
                    reservasConfirmadas.add(reserva);
                }
            }

            return reservasConfirmadas;
        }
    }
    
    public Indicadores calcularIndicadores() {

        synchronized (Lock) {

            int totalHabitaciones = habitaciones.size();

            int ocupadas = 0;
            int confirmadas = 0;
            int vencidas = 0;

            // Contar las habitaciones ocupadas actualmente.
            for (Habitacion habitacion : habitaciones) {

                for (Reserva reserva : reservas) {

                    boolean mismaHabitacion
                            = reserva.getHabitacion().getNumero()
                                    .equals(habitacion.getNumero());

                    boolean estaHospedado
                            = reserva.getEstado() == EstadoReserva.HOSPEDADO;

                    if (mismaHabitacion && estaHospedado) {
                        ocupadas++;
                        break;
                    }
                }
            }

            // Contar las reservas según su estado.
            for (Reserva reserva : reservas) {

                if (reserva.getEstado() == EstadoReserva.CONFIRMADA) {
                    confirmadas++;

                } else if (reserva.getEstado() == EstadoReserva.VENCIDA) {
                    vencidas++;
                }
            }

            int noOcupadas = totalHabitaciones - ocupadas;

            // Calcular ingresos mediante el método recursivo.
            long ingresosEsperados
                    = Recursividad.calcularIngresosEsperados(reservas, 0);

            return new Indicadores(
                    totalHabitaciones,
                    ocupadas,
                    noOcupadas,
                    confirmadas,
                    vencidas,
                    ingresosEsperados
            );
        }
    }
}    