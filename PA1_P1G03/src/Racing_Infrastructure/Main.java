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
    public static void main(String[] args) {
        
        while(true)
        {
        int nIncThreads = 10;
        Car[] myThreadAdd = new Car[nIncThreads];
        
        System.out.println("Number of cars to race: ");
        Scanner in = new Scanner(System.in);
        int num = in.nextInt();            //String s = in.next();
        System.out.println("Time between cars (seconds):");
        Scanner in1 = new Scanner(System.in);
        float sec = in1.nextFloat();

        // Monitor: the shared area
        ParkingMonitor parkingMonitor = new ParkingMonitor(nIncThreads);
        RaceMonitor raceMonitor = new RaceMonitor(num, 15);
        
        // Threads to increment
        for (int i=0; i<nIncThreads; i++) {
            // create instance of class extending Thread
            myThreadAdd[i] = new Car(i,parkingMonitor, raceMonitor, num, sec);
            // launch thread
            myThreadAdd[i].start();
        }
        
        
        try {
            // wait for end of computation in case the Threads die voluntarly
            for (int i =0; i<nIncThreads; i++)
                // wait while the thread does not die
                myThreadAdd[i].join();
        } catch(Exception ex) {}
        
            System.out.println("\n\n1 - New race\n0 - Exit\n");
            Scanner ini2 = new Scanner(System.in);
            int x = ini2.nextInt();
            if(x==0)
                System.exit(0);
        }
    }
    
}
