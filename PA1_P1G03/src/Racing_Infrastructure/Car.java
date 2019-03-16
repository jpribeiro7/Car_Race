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
    private ParkingMonitor parkingMonitor;
    private RaceMonitor raceMonitor;
    private int currentPosition = 0;
    private boolean isFinished=false;
    private int contenders;
    private int time;

    public Car(int id, ParkingMonitor parkingMonitor, RaceMonitor raceMonitor, int contenders, float timeMil) {
        this.id = id;
        this.parkingMonitor = parkingMonitor;
        this.raceMonitor = raceMonitor;
        this.contenders = contenders;
        this.time = (int) (timeMil*1000);
    }
    
    
    
    @Override
    public void run(){ 
        parkingMonitor.waitingForNewRace(this.getid());
         if(this.getid()<=contenders-1){
        parkingMonitor.prepareNewRace();
        //raceMonitor.checkRacers(this);
        raceMonitor.startRace(this);
        while(currentPosition < raceMonitor.trackSize() ){
            try {
                raceMonitor.move(this,time);
            } catch (InterruptedException ex) {
                Logger.getLogger(Car.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        isFinished = true;
        System.out.println("I am car: "+id+" and i finished");
        raceMonitor.finish(this);
        System.out.println("Car id:"+id);        
        }
        
         else{
             System.out.println("I'm car nº"+this.getid()+" ad i'm not allowed on this race!");
         
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
