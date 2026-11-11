/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package Habitaciones;

/**
 *
 * @author Aaron Diaz
 */
public class HabitacionDoble extends Habitacion {

    public HabitacionDoble(String numero){
        super(numero);
    }
    
    //Capacidad máxima 2
    @Override
    public int getCapacidad(){
        return 2;
    }
    
    //Tarifa por noche
    @Override
    public long getTarifa(){
        return 40000;
    }
    
    //Nombre del tipo de habitacion
    @Override
    public String getTipo(){
        return "Doble";
    }
}
