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
public class Car extends Thread{
    private int id;
    private IParkingMonitor parkingMonitor;
    private IRaceMonitor raceMonitor;
    private int currentPosition = 0;
    private boolean isFinished=false;

    public Car(int id, IParkingMonitor parkingMonitor, IRaceMonitor raceMonitor) {
        this.id = id;
        this.parkingMonitor = parkingMonitor;
        this.raceMonitor = raceMonitor;
    }
    
    
    
    @Override
    public void run(){ 
        while(true){
        parkingMonitor.waitingForNewRace(this);
        parkingMonitor.prepareNewRace(this);
        raceMonitor.startRace(this);
        while(currentPosition < raceMonitor.trackSize() ){
            raceMonitor.move(this);
            
        }
        isFinished = true;
        System.out.println("I am car: "+id+" and i finished");
        raceMonitor.finish(this); 
        isFinished = false;
        currentPosition = 0;
        }
    }
    
    public int getCurrentPosition(){
       return currentPosition; 
    }
    
    public void move(int step){
        currentPosition+=step;
    }
    
    public int getid(){
        return id;
    }
    public boolean isFinished(){
        return isFinished;
    }
    
    
}
