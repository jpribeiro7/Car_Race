/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Racing_Infrastructure;

import java.util.Scanner;


/**
 *
 * @author Pedro
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws InterruptedException {
        
        
        int nIncThreads = 10;
        Car[] myThreadAdd = new Car[nIncThreads];
        
        /*
        System.out.println("Number of cars to race: ");
        Scanner in = new Scanner(System.in);
        int num = Integer.parseInt(in.nextLine());        //String s = in.next();
        System.out.println("Time between cars (seconds):");
        float sec = Integer.parseInt(in.nextLine());
        */
        // Monitor: the shared area
        int num =5;
        int sec=1;
        ParkingMonitor parkingMonitor = new ParkingMonitor(nIncThreads,num);
        RaceMonitor raceMonitor = new RaceMonitor(num, 15);
        
        // Threads to increment
        for (int i=0; i<nIncThreads; i++) {
            // create instance of class extending Thread
            myThreadAdd[i] = new Car(i,parkingMonitor, raceMonitor, num, sec);
            // launch thread
            myThreadAdd[i].start();
        }
        
       
        // wait for end of computation in case the Threads die voluntarly
        for (int i =0; i<nIncThreads; i++)
            // wait while the thread does not die
            myThreadAdd[i].join();
    }
    
}
