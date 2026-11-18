/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package MODELOS;

import ENUMs.EstadoReserva;
import java.time.LocalDate;
import java.util.List;
/**
 *
 * @author estar
 */

public class Recursividad {

    private Recursividad() {
    }

    public static long calcularIngresosEsperados(
            List<Reserva> reservas, int indice) {

        if (reservas == null) {
            throw new IllegalArgumentException(
                    "La lista de reservas no puede ser nula.");
        }

        if (indice < 0 || indice > reservas.size()) {
            throw new IndexOutOfBoundsException(
                    "El índice está fuera del rango de reservas.");
        }

        if (indice == reservas.size()) {
            return 0;
        }

        Reserva reservaActual = reservas.get(indice);


        if (reservaActual.getEstado() == EstadoReserva.CONFIRMADA) {

            return reservaActual.getImporteTotal()
                    + calcularIngresosEsperados(
                            reservas, indice + 1);
        }

        return calcularIngresosEsperados(
                reservas, indice + 1);
    }


    public static int contarDisponibles(
            List<Habitacion> habitaciones,
            List<Reserva> reservas,
            LocalDate fecha,
            int indice) {

        if (habitaciones == null || reservas == null || fecha == null) {
            throw new IllegalArgumentException(
                    "Las habitaciones, reservas y fecha son obligatorias.");
        }

        if (indice < 0 || indice > habitaciones.size()) {
            throw new IndexOutOfBoundsException(
                    "El índice está fuera del rango de habitaciones.");
        }


        if (indice == habitaciones.size()) {
            return 0;
        }

        Habitacion habitacionActual = habitaciones.get(indice);


        int disponible = estaLibre(
                habitacionActual, reservas, fecha) ? 1 : 0;


        return disponible + contarDisponibles(
                habitaciones, reservas, fecha, indice + 1);
    }


    public static boolean estaLibre(
            Habitacion habitacion,
            List<Reserva> reservas,
            LocalDate fecha) {

        if (habitacion == null || reservas == null || fecha == null) {
            throw new IllegalArgumentException(
                    "La habitación, reservas y fecha son obligatorias.");
        }


        for (Reserva reserva : reservas) {

            boolean mismaHabitacion =
                    reserva.getHabitacion().getNumero()
                            .equals(habitacion.getNumero());

            boolean estadoActivo =
                    reserva.getEstado() == EstadoReserva.CONFIRMADA
                    || reserva.getEstado() == EstadoReserva.HOSPEDADO;

            boolean ocupaFecha = reserva.incluyeFecha(fecha);

            if (mismaHabitacion && estadoActivo && ocupaFecha) {
                return false;
            }
        }

        return true;
    }
}
