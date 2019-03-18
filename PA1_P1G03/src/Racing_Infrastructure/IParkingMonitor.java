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
public interface IParkingMonitor {
    public void waitingForNewRace(Car car);
    public void prepareNewRace(Car car);
}
