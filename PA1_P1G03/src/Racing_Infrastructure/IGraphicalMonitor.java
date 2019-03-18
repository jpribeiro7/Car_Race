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
public interface IGraphicalMonitor {
    public void setRaceConfig(int cars, int timeout);
    public void setRaceAvailable();
    public void setPrepareRace();
    public void setStartRace();
    public void setStopRace();
}
