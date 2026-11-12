/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package Habitaciones;

/**
 *
 * @author Aaron Diaz
 */
public class HabitacionFactory {
    public static Habitacion crearHabitacion(String numero, String Tipo){
        //Si el nulo no existe, se rechaza con un mensaje
        if (Tipo == null) {
           throw new IllegalArgumentException("El tipode habitación no es válido"); 
        }
        
        // Segun el tipo, se crea la subclase correspondiente
        switch(Tipo){
            case "Individual":
                return new HabitacionIndividual(numero);
            case "Doble":
                return new HabitacionDoble(numero);
            case "Familiar":
                return new HabitacionFamiliar(numero);
            // Cualquier otro texto no es un tipo válido.
            default:
                throw new IllegalArgumentException("El tipo de habitación no es válido: " + Tipo);
        }
    }
}
