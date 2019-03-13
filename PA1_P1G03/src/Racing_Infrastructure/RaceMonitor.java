/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Racing_Infrastructure;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
import java.math.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

/**
 *
 * @author Pedro
 */
public class RaceMonitor {
    
    private ReentrantLock rl;
    private List<Condition> move;
    private boolean finish = false;
    private int startedCars;
    private int movedCars;
    private Set<Integer> finishedCars;
    private int totalCars;
    private int trackSize;
    
    public RaceMonitor(int n_threads, int trackSize) {
        rl = new ReentrantLock(true);
        totalCars = n_threads;
        move = new ArrayList();
        finishedCars = new HashSet();
        for (int i = 0; i<n_threads; i++){
            move.add(rl.newCondition());
        }
        this.trackSize = trackSize;
    }
    
    public void startRace(Car car){
        rl.lock();
        try {
            while ( startedCars != car.getid())
                // wait for Dec Thread to decrement coun
                move.get(car.getid()).await();
            
        } catch (Exception ex) {}
        finally {
            startedCars++;
            if(startedCars != totalCars)
                move.get(car.getid()+1).signal();
            System.out.println("Car "+car.getid()+ " can start the race");
            rl.unlock();
        }
    }
    
    public void move(Car car){
        rl.lock();
        try {
            while ( movedCars != car.getid())
                // wait for Dec Thread to decrement count
                move.get(car.getid()).await();
           
        } catch (Exception ex) {}
        finally {
            // always inside a finally block.
            // move random amount of steps
            int steps = generateRandomStep();
            car.move(steps);
            movedCars++;
            int position = car.getCurrentPosition();
            if (position>trackSize){
                finishedCars.add(car.getid());
            }
            for (int i = car.getid()+1; i<totalCars; i++){
                if (finishedCars.contains(i)){
                    continue;
                }else{
                    move.get(i).signal();
                }
            }
            
            if(movedCars == totalCars){
                move.get(0).signal();
                movedCars = 0;
            }else if(movedCars == totalCars-finishedCars.size()){
                move.get(0).signal();
            }
            
            System.out.println("Car: " + car.getid() + " is in position: " + position);
            rl.unlock();
        }
    }
    
    
    
    
    public int trackSize(){
        return trackSize;
    }
    
    public int generateRandomStep(){
        Random r = new Random();
        int low = 1;
        int high = 5;
        int result = r.nextInt(high-low) + low;
        return result;
    }
    
    
}
