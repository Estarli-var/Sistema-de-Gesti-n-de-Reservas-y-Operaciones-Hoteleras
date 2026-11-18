/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package MODELOS;
import ENUMs.EstadoReserva;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
/**
 *
 * @author estar
 */

public class Reserva {


    private final String codigo;
    private final Huesped huesped;
    private final Habitacion habitacion;
    private final int cantidadPersonas;
    private final LocalDate fechaEntrada;
    private final LocalDate fechaSalida;
    private LocalDate fechaSalidaReal;
    private final int noches;
    private final long importeTotal;
    private EstadoReserva estado;
    private final String recepcionista;


    public Reserva(String codigo, Huesped huesped,Habitacion habitacion, int cantidadPersonas,LocalDate fechaEntrada, LocalDate fechaSalida,String recepcionista) {
        this.codigo = codigo;
        this.huesped = huesped;
        this.habitacion = habitacion;
        this.cantidadPersonas = cantidadPersonas;
        this.fechaEntrada = fechaEntrada;
        this.fechaSalida = fechaSalida;
        this.recepcionista = recepcionista;

        this.noches = calcularNoches(fechaEntrada, fechaSalida);

        this.importeTotal = calcularImporte(
                habitacion.getTarifa(), noches);

        this.fechaSalidaReal = null;
        this.estado = EstadoReserva.CONFIRMADA;
    }
    
    public String getCodigo() {
        return codigo;
    }

    public Huesped getHuesped() {
        return huesped;
    }

    public Habitacion getHabitacion() {
        return habitacion;
    }

    public int getCantidadPersonas() {
        return cantidadPersonas;
    }

    public LocalDate getFechaEntrada() {
        return fechaEntrada;
    }

    public LocalDate getFechaSalida() {
        return fechaSalida;
    }

    public LocalDate getFechaSalidaReal() {
        return fechaSalidaReal;
    }

    public int getNoches() {
        return noches;
    }

    public long getImporteTotal() {
        return importeTotal;
    }

    public EstadoReserva getEstado() {
        return estado;
    }

    public String getRecepcionista() {
        return recepcionista;
    }

    public static int calcularNoches(
            LocalDate entrada, LocalDate salida) {

        return (int) ChronoUnit.DAYS.between(entrada, salida);
    }

    public static long calcularImporte(long tarifa, int noches) {
        return tarifa * noches;
    }

    public boolean bloqueaDisponibilidad() {
        return estado == EstadoReserva.CONFIRMADA || estado == EstadoReserva.HOSPEDADO;
    }

    public boolean incluyeFecha(LocalDate fecha){
        return !fecha.isBefore(fechaEntrada) && fecha.isBefore(fechaSalida);
    }

    public boolean seSuperpone(LocalDate entrada, LocalDate salida) {
        return entrada.isBefore(fechaSalida)&& salida.isAfter(fechaEntrada);
    }

    void hacerCheckIn() {
        if (estado != EstadoReserva.CONFIRMADA) {
            throw new IllegalStateException(
                    "La reserva no está confirmada.");
        }
        estado = EstadoReserva.HOSPEDADO;
    }


    void hacerCheckOut(LocalDate fechaActual) {
        if (estado != EstadoReserva.HOSPEDADO) {
            throw new IllegalStateException(
                    "El huésped no se encuentra hospedado.");
        }
        fechaSalidaReal = fechaActual;
        estado = EstadoReserva.FINALIZADA;
    }

    void marcarVencida() {
        if (estado != EstadoReserva.CONFIRMADA) {
            throw new IllegalStateException(
                    "Solo las reservas confirmadas pueden vencer.");
        }
        estado = EstadoReserva.VENCIDA;
    }


    public boolean vencidaEn(LocalDate fecha) {
        return estado == EstadoReserva.CONFIRMADA && fechaEntrada.isBefore(fecha);
    }

}