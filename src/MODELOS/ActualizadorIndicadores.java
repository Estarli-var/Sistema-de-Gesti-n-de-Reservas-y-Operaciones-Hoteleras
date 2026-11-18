/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package MODELOS;
import javax.swing.SwingUtilities;
import java.util.function.Consumer;

/**
 *
 * @author estar
 */

public class ActualizadorIndicadores implements Runnable {

    private final GestorHotel gestor;
    private final Consumer<Indicadores> callback;

    public ActualizadorIndicadores(Consumer<Indicadores> callback) {

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

        System.out.println("========================================");
        System.out.println("Inicio del hilo: " + nombreHilo);

        try {

            for (int ciclo = 1; ciclo <= 5; ciclo++) {

                // Calcular los seis indicadores del hotel.
                Indicadores indicadores = gestor.calcularIndicadores();

                // Enviar los resultados a la vista en el EDT.
                SwingUtilities.invokeLater(() -> {
                    callback.accept(indicadores);
                });

                System.out.println(
                        nombreHilo + " - Ciclo " + ciclo
                        + " completado.");

                // Esperar un segundo antes del siguiente ciclo.
                Thread.sleep(1000);
            }

            // Obtener y enviar una última actualización.
            Indicadores indicadoresFinales =
                    gestor.calcularIndicadores();

            SwingUtilities.invokeLater(() -> {
                callback.accept(indicadoresFinales);
            });

            System.out.println(
                    "El actualizador terminó correctamente.");

        } catch (InterruptedException e) {

            System.out.println(
                    "El hilo " + nombreHilo
                    + " fue interrumpido.");

            Thread.currentThread().interrupt();

        } finally {

            System.out.println("Fin del hilo: " + nombreHilo);
            System.out.println("========================================");
        }
    }
}
