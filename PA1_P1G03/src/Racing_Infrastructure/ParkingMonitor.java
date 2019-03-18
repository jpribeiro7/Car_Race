/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Racing_Infrastructure;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Pedro
 */
public class ParkingMonitor {

    private boolean waitingRace = false;
    private int numberOfCars;
    private int carsToRace;
    private int inLine;
    
    public ParkingMonitor(int numberOfCars, int carsToRace){
        this.numberOfCars = numberOfCars;
        this.carsToRace = carsToRace;
    }
    

    public synchronized void waitingForNewRace(Car car) {
        try {
            System.out.println("I am car: "+ car.getid()+" waiting for a race ... ");
            while (waitingRace || car.getid()>=carsToRace) {
                wait();
            }
            notifyAll();
        } catch (InterruptedException ex) {
            notifyAll();
        }
    }
    
    public synchronized void prepareNewRace(Car car) {
        try {
            
            waitingRace = false;
            while (car.getid()!=inLine) {
                wait();
            }
            System.out.println("I am car: "+car.getid()+" and I've lined up for the race");
            inLine++;
            notifyAll();
        } catch (InterruptedException ex) {
            notifyAll();
        }
    }

}
