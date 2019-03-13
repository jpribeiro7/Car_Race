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
    private int parkedCars = 0;
    
    public ParkingMonitor(int numberOfCars){
        this.numberOfCars = numberOfCars;
    }

    public synchronized void waitingForNewRace() {
        try {
            System.out.println("Waiting for a race ... ");
            parkedCars++;
            while (waitingRace) {
                wait();
            }
            notifyAll();
        } catch (InterruptedException ex) {
            notifyAll();
        }
    }
    
    public synchronized void prepareNewRace() {
        try {
            System.out.println("Waiting for race to start ... ");
            waitingRace = false;
            while (parkedCars!=numberOfCars) {
                wait();
            }
            System.out.println("Race has started");
            notifyAll();
        } catch (InterruptedException ex) {
            notifyAll();
        }
    }

    public void findRace(){
        waitingRace = false;
    }
}
