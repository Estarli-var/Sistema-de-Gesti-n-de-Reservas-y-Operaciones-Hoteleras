/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package MODELOS;

import MODELOS.Habitacion;

/**
 *
 * @author Aaron Diaz
 */
public class HabitacionFamiliar extends Habitacion{
    public HabitacionFamiliar(String numero){
        super(numero);
    }
    
    //Capacidad máxima 4
    @Override
    public int getCapacidad(){
        return 4;
    }
    
    //Tarifa por noche
    @Override
    public long getTarifa(){
        return 65000;
    }
    
    //Nombre del tipo de habitacion
    @Override
    public String getTipo(){
        return "Familiar";
    }
}
