/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package Habitaciones;

/**
 *
 * @author Aaron Diaz
 */
public class HabitacionIndividual extends Habitacion{
    public HabitacionIndividual(String numero){
        super(numero);
    }
    
    //Capacidad máxima 1 
    @Override
    public int getCapacidad(){
        return 1;
    }
    
    //Tarifa por noche
    @Override
    public long getTarifa(){
        return 25000;
    }
    
    @Override
    public String getTipo(){
        return "Individual";
    }
    
}
