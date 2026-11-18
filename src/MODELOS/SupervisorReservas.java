/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package MODELOS;

import java.time.LocalDate;
import java.util.function.Consumer;
/**
 *
 * @author estar
 */

public class SupervisorReservas implements Runnable {

    private final GestorHotel gestor;
    private final Consumer<String> callback;

    public SupervisorReservas(Consumer<String> callback) {

        this.gestor = GestorHotel.getInstancia();

        if (callback == null) {
            throw new IllegalArgumentException(
                    "El callback no puede ser null.");
        }

        this.callback = callback;
    }

    @Override
    public void run() {

        String nombreHilo = Thread.currentThread().getName();

        System.out.println(
                "========================================");
        System.out.println(
                "Inicio del hilo: " + nombreHilo);

        try {

            for (int ciclo = 1; ciclo <= 5; ciclo++) {

                // Actualizar vencimientos con la fecha real del equipo.
                gestor.actualizarVencidas(LocalDate.now());

                System.out.println(
                        nombreHilo + " - Ciclo " + ciclo
                        + " completado.");

                // Esperar un segundo antes del siguiente ciclo.
                Thread.sleep(1000);
            }

            System.out.println(
                    "El supervisor terminó correctamente.");

            // Avisar a quien haya creado el hilo.
            callback.accept(
                    "La supervisión de reservas terminó correctamente.");

        } catch (InterruptedException e) {

            System.out.println(
                    "El hilo " + nombreHilo
                    + " fue interrumpido.");

            // Restaurar el estado de interrupción.
            Thread.currentThread().interrupt();

        } finally {

            System.out.println(
                    "Fin del hilo: " + nombreHilo);

            System.out.println(
                    "========================================");
        }
    }
}