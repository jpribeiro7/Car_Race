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
public class Car extends Thread{
    private int id;
    private ParkingMonitor parkingMonitor;
    private RaceMonitor raceMonitor;
    private int currentPosition = 0;

    public Car(int id, ParkingMonitor parkingMonitor, RaceMonitor raceMonitor) {
        this.id = id;
        this.parkingMonitor = parkingMonitor;
        this.raceMonitor = raceMonitor;
    }
    
    
    
    @Override
    public void run(){
        parkingMonitor.waitingForNewRace();
        parkingMonitor.prepareNewRace();
        raceMonitor.startRace(this);
        while(currentPosition < raceMonitor.trackSize() ){
            raceMonitor.move(this);
        }
        System.out.println("I am car "+id+" and I finished the race");
        
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
    
}
