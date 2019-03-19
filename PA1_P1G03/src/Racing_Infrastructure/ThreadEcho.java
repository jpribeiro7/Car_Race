package Racing_Infrastructure;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


import com.sun.jmx.snmp.tasks.ThreadService;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import javafx.scene.paint.Stop;
import sun.dc.pr.PRError;

/**
 * This class implements the thread responsible for
 * dealing with the new incoming clients
 * @author Óscar Pereira
 */
class ThreadEcho extends Thread {

    private final Socket socket;
    private PrintWriter out = null;
    private BufferedReader in = null;
    private IGraphicalMonitor graphicalMonitor;
    private Car[] cars;

    // constructor receives the socket
    public ThreadEcho(Socket socket, IGraphicalMonitor graphicalMonitor, Car[] cars) {
        this.socket = socket;
        this.cars = cars;
        this.graphicalMonitor = graphicalMonitor;
    }
    
    @Override
    public void run() {
        try {
            // socket´s output stream
            out = new PrintWriter(socket.getOutputStream(), true);
            // socket's input stream
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            while (true) {
                // wait for a message from the client
                System.out.println("Thread is waiting for a new message");
                String request = in.readLine();
                String[] aux = request.split(",");
                String expression = aux[0];
                String numcars = aux[1];
                String id = aux[1];
                String timeout = aux[2];
                // null message?
                if (expression == null) {
                    // end of communication with this client
                    System.out.println("End of communication");
                    break;
                }
                System.out.println("Server received a new message: "+ expression);
                System.out.println("Server received a new message: " + numcars);
                System.out.println("Server received a new message: " + timeout);
                //System.out.println("\n\nServer received a new message: " + text2);

                // random sleep to induce a processing delay
                //sleep( (int) (Math.random() * 10) * 1000);
                // send echo to client
                //out.println("Server echo (Nº of cars:" + numcars + ", Timeout:"+timeout+ ")");
                
                
                
                switch(expression){
                        case "prepareNew":
                            graphicalMonitor.setRaceConfig(Integer.parseInt(numcars),Integer.parseInt(numcars));
                            //graphicalMonitor.setRaceAvailable();
                            //graphicalMonitor.setPrepareRace();
                            out.println("Preparing new race");
                            break;
                        case "startRace":
                            graphicalMonitor.setStartRace();
                            out.println("Race started");
                            break;                           
                        case "stopRace":
                            graphicalMonitor.setStopRace();
                            out.println("Race stoped");
                            break;
                        case "getPositionCar":
                            int pos = cars[Integer.parseInt(id)].getCurrentPosition();
                            String PosAndId = id + "," + String.valueOf(pos);
                            out.println(PosAndId);
                            break;             
                }
            
                
                /*try {
                    // wait for end of computation in case the Threads die voluntarly
                    for (int i =0; i<10; i++)
                        // wait while the thread does not die
                        myThreadAdd[i].join();
                    System.out.println("JOIIIIIIIINNNNN\n\n");
                } catch(Exception ex) {}
        */
            }
            // close everything
            socket.close();
            out.close();
            in.close();
        } catch (IOException ex) {}
    }
}
