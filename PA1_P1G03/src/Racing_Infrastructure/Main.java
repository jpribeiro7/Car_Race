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
        
        
        int nThreads = 10;
        Car[] myThreadAdd = new Car[nThreads];
        
        int num =5;
        int sec=1;
        Monitor monitor = new Monitor(nThreads);
        GraphicController gc = new GraphicController((IGraphicalMonitor) monitor);
        gc.start();
        
        
        //Thread.sleep(200);
        
        // Threads to increment
        for (int i=0; i<nThreads; i++) {
            // create instance of class extending Thread
            myThreadAdd[i] = new Car(i,(IParkingMonitor) monitor, (IRaceMonitor) monitor, num, sec);
            // launch thread
            myThreadAdd[i].start();
        }
        
        
        
       
        // wait for end of computation in case the Threads die voluntarly
        for (int i =0; i<nThreads; i++)
            // wait while the thread does not die
            myThreadAdd[i].join();
    }
    
}
