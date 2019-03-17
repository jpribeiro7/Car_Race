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
    private boolean running = false;
    private int stop = 0;

    public Car(int id, ParkingMonitor parkingMonitor, RaceMonitor raceMonitor, int contenders, float timeMil, int stop) {
        this.id = id;
        this.parkingMonitor = parkingMonitor;
        this.raceMonitor = raceMonitor;
        this.contenders = contenders;
        this.time = (int) (timeMil*1000);
        this.stop = stop;
    }
    
    
    public void terminate(){
        stop = 1;
        System.out.println("Stop should be 1, and is = " + stop);
    }
    
    @Override
    public void run(){ 
        running = true;
        while(running = true){
        parkingMonitor.waitingForNewRace(this.getid());
         if(this.getid()<=contenders-1){
        parkingMonitor.prepareNewRace();
        //raceMonitor.checkRacers(this);
        raceMonitor.startRace(this);
        while(currentPosition < raceMonitor.trackSize() ){
                             
            try {         
                raceMonitor.move(this,time,stop);
                System.out.println("Thread "+id+" and stop ="+stop);
                
            } catch (InterruptedException ex) {
                Logger.getLogger(Car.class.getName()).log(Level.SEVERE, null, ex);
            }
                
            if(stop!=0){
                System.out.println("Shuting down");
                return;
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
         running = false;
         break;
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
