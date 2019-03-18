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
public class GraphicController extends Thread{
    private IGraphicalMonitor graphicalMonitor;

    public GraphicController(IGraphicalMonitor graphicalMonitor) {
        this.graphicalMonitor = graphicalMonitor;
    }
    
    
    @Override
    public void run() {
        graphicalMonitor.setRaceConfig(5, 1);
    }
    
}
