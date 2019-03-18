package Racing_Infrastructure;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * This class implements the thread responsible for
 * dealing with the new incoming clients
 * @author Óscar Pereira
 */
class ThreadEcho extends Thread {

    private final Socket socket;
    private PrintWriter out = null;
    private BufferedReader in = null;

    // constructor receives the socket
    public ThreadEcho(Socket socket) {
        this.socket = socket;
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
                String text = in.readLine();
                String text1 = in.readLine();
                // null message?
                if (text == null || text1 == null) {
                    // end of communication with this client
                    System.out.println("End of communication");
                    break;
                }
                System.out.println("Server received a new message: " + text );
                System.out.println("Server received a new message: " + text1 );

                // random sleep to induce a processing delay
                sleep( (int) (Math.random() * 10) * 1000);
                // send echo to client
                out.println("Server echo (Nº of cars:" + text + ", Timeout:"+text1+ ")");
                
                
                int nIncThreads = 10;
                Car[] myThreadAdd = new Car[nIncThreads];
                int num =  Integer.parseInt(text);
                float sec = Float.parseFloat(text1);
                // Monitor: the shared area
                ParkingMonitor parkingMonitor = new ParkingMonitor(nIncThreads);
                RaceMonitor raceMonitor = new RaceMonitor(num, 15);
                
                
                // Threads to increment
                for (int i=0; i<nIncThreads; i++) {
                    // create instance of class extending Thread
                    myThreadAdd[i] = new Car(i,parkingMonitor, raceMonitor, num, sec);
                    // launch thread
                    myThreadAdd[i].start();
                }
                
                try {
                    // wait for end of computation in case the Threads die voluntarly
                    for (int i =0; i<nIncThreads; i++)
                        // wait while the thread does not die
                        myThreadAdd[i].join();
                } catch(Exception ex) {}
        
            }
            // close everything
            socket.close();
            out.close();
            in.close();
        } catch (IOException | InterruptedException ex) {}
    }
}
