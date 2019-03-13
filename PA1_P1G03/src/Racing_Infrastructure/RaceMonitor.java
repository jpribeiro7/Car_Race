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
    private int totalCars;
    private int finishedCars;
    private int trackSize;

    public RaceMonitor(int n_threads, int trackSize) {
        rl = new ReentrantLock(true);
        totalCars = n_threads;
        move = new ArrayList();
        for (int i = 0; i < n_threads; i++) {
            move.add(rl.newCondition());
        }
        this.trackSize = trackSize;
    }

    public void startRace(Car car) {
        rl.lock();
        try {
            while (startedCars != car.getid()) // wait for Dec Thread to decrement coun
            {
                move.get(car.getid()).await();
            }

        } catch (Exception ex) {
        } finally {
            startedCars++;
            if (startedCars != totalCars) {
                move.get(car.getid() + 1).signal();
            }
            System.out.println("Car " + car.getid() + " can start the race");
            rl.unlock();
        }
    }

    public void move(Car car) {
        rl.lock();
        try {
            while (movedCars != car.getid()) // wait for Dec Thread to decrement count
            {
                move.get(car.getid()).await();
            }

        } catch (Exception ex) {
        } finally {
            // always inside a finally block.
            // move random amount of steps
            int steps = generateRandomStep();
            car.move(steps);
            
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
            

            System.out.println("Car: " + car.getid() + " is in position: " + position);
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
                    System.out.println("I am car: "+car.getid()+" and i am signaling "+movedCars);
                } else if (movedCars == totalCars) {
                    move.get(0).signal();
                    movedCars = 0;
                    System.out.println("I am car: "+car.getid()+" and i am signaling "+movedCars);
                }
                move.get(car.getid()).await();
            }

        } catch (Exception ex) {
        } finally {
            for(Condition c : move){
                c.signal();
            }
            System.out.println("Race is over");
            rl.unlock();
        }
    }

    public int trackSize() {
        return trackSize;
    }

    public int generateRandomStep() {
        Random r = new Random();
        int low = 1;
        int high = 5;
        int result = r.nextInt(high - low) + low;
        return result;
    }

}
