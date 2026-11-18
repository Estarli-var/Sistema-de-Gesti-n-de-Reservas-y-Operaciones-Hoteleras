/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package MODELOS;

/**
 *
 * @author estar
 */
public class Indicadores {
    private int totalHabitaciones;
    private int ocupadas;
    private int noOcupadas;
    private int confirmadas;
    private int vencidas;
    private long ingresosEsperados;

    public Indicadores(int totalHabitaciones, int ocupadas,
            int noOcupadas, int confirmadas, int vencidas,
            long ingresosEsperados) {

        this.totalHabitaciones = totalHabitaciones;
        this.ocupadas = ocupadas;
        this.noOcupadas = noOcupadas;
        this.confirmadas = confirmadas;
        this.vencidas = vencidas;
        this.ingresosEsperados = ingresosEsperados;
    }

    public int getTotalHabitaciones() {
        return totalHabitaciones;
    }

    public int getOcupadas() {
        return ocupadas;
    }

    public int getNoOcupadas() {
        return noOcupadas;
    }

    public int getConfirmadas() {
        return confirmadas;
    }

    public int getVencidas() {
        return vencidas;
    }

    public long getIngresosEsperados() {
        return ingresosEsperados;
    }
}
