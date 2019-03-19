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
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Pedro
 */
public class Monitor implements IGraphicalMonitor, IParkingMonitor, IRaceMonitor{
    private final int trackSize = 17;
    // Parking controls
    private boolean waitingRace = true;
    private int numberOfCars;
    private int carsToRace;
    private int inLine;
    
    
    // RaceTrack controls
    private ReentrantLock rl;
    private List<Condition> move;
    private boolean finish = false;
    private boolean startRace = false;
    private boolean stop = false;
    private int startedCars=0;
    private int movedCars;
    private int totalCars;
    private int finishedCars;
    private int time;
    

    public Monitor(int n_threads) {
        rl = new ReentrantLock(true);
        
    }
    
    // ParkingLot methods
    public synchronized void waitingForNewRace(Car car) {
        try {
            System.out.println("I am car: "+ car.getid()+" waiting for a race ... ");
            while (waitingRace || car.getid()>=totalCars) {
                wait();
            }
            notifyAll();
        } catch (InterruptedException ex) {
            notifyAll();
        }
    }
    
    public synchronized void prepareNewRace(Car car) {
        try {
            
            waitingRace = false;
            while (car.getid()!=inLine) {
                wait();
            }
            System.out.println("I am car: "+car.getid()+" and I've lined up for the race");
            inLine++;
            notifyAll();
        } catch (InterruptedException ex) {
            notifyAll();
        }
    }
    
    
    
    
    
    // RaceTrack Methods
    public void startRace(Car car) {
        rl.lock();
        try {
            while (startedCars != car.getid() || !startRace) 
            {
                move.get(car.getid()).await();
            }

        } catch (Exception ex) {
        } finally {
            startedCars++;
            if (startedCars != totalCars) {
                move.get(car.getid() + 1).signal();
            }else{
                
            }
            System.out.println("I am car: " + car.getid() + " can start the race");
            rl.unlock();
        }
    }

    public void move(Car car){
        rl.lock();
        try {
            while (movedCars != car.getid()) 
            {
                move.get(car.getid()).await();
            }

        } catch (Exception ex) {
        } finally {
            
            // always inside a finally block.
            // move random amount of steps
            int steps = generateRandomStep();
            car.move(steps);
            
            try {
                Thread.sleep(time);
            } catch (InterruptedException ex) {
                Logger.getLogger(Monitor.class.getName()).log(Level.SEVERE, null, ex);
            }
            int position = car.getCurrentPosition();
            if (position >= trackSize) {
                finishedCars++;
            }else{
                movedCars++;
                if (movedCars != totalCars) {
                move.get(car.getid() + 1).signal();
            } else if (movedCars == totalCars) {
                move.get(0).signal();
                movedCars = 0;
            }
            }
            

            System.out.println("I am car: " + car.getid() + "  in position: " + position);
            rl.unlock();
        }
    }

    public void finish(Car car) {
        rl.lock();
        try {
            while (finishedCars != totalCars) // wait for Dec Thread to decrement count
            {
                movedCars++;
                if (movedCars != totalCars) {
                    move.get(car.getid() + 1).signal();
                } else if (movedCars == totalCars) {
                    move.get(0).signal();
                    movedCars = 0;
                }
                move.get(car.getid()).await();
            }

        } catch (Exception ex) {
        } finally {
            for(Condition c : move){
                c.signal();
            }
            rl.unlock();
        }
    }

    public synchronized int trackSize() {
        return trackSize;
    }

    
    // Graphical Controls
    @Override
    public synchronized void setRaceConfig(int cars, int timeout) {
        totalCars = cars;
        move = new ArrayList();
        for (int i = 0; i < totalCars; i++) {
            move.add(rl.newCondition());
        }
        time = timeout;
        finish = false;
        stop = false;
        waitingRace = true;
        startRace = false;
        startedCars=0;
        movedCars=0;
        finishedCars=0;
        numberOfCars = 0;
        carsToRace = 0;
        inLine = 0;
        System.out.println("configs done");
        
    }

    @Override
    public synchronized void setRaceAvailable() {
        waitingRace = false;
        notifyAll();
    }

    @Override
    public synchronized void setPrepareRace() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setStartRace() {
        rl.lock();
        startRace = true;
        move.forEach(cnsmr->cnsmr.signal());
        rl.unlock();
    }

    @Override
    public void setStopRace() {
        rl.lock();
        stop = true;
        move.get(0).signal();
        rl.unlock();
    }
    
    
    
    private int generateRandomStep() {
        Random r = new Random();
        int low = 1;
        int high = 5;
        int result = r.nextInt(high - low) + low;
        return result;
    }

}
