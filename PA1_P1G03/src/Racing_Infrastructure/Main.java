/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Racing_Infrastructure;


/**
 *
 * @author Pedro
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        int nIncThreads = 10;
        Car[] myThreadAdd = new Car[nIncThreads];
        
        // Monitor: the shared area
        ParkingMonitor parkingMonitor = new ParkingMonitor(nIncThreads);
        RaceMonitor raceMonitor = new RaceMonitor(nIncThreads, 15);
        // Threads to increment
        for (int i=0; i<nIncThreads; i++) {
            // create instance of class extending Thread
            myThreadAdd[i] = new Car(i,parkingMonitor, raceMonitor);
            // launch thread
            myThreadAdd[i].start();
        }
        
        
        try {
            // wait for end of computation in case the Threads die voluntarly
            for (int i =0; i<nIncThreads; i++)
                // wait while the thread does not die
                myThreadAdd[i].join();
        } catch(Exception ex) {}
    }
    
}
