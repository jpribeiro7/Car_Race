package Control_Center;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

/**
 * This is a client aimed to contact a server
 * on localhost and port 5000. User may insert a line to
 * be sent to the server. The server will answer with the same
 * message. Connection is closed if a null message is sent
 * to the server.
 * @author Óscar Pereira
 */
public class EchoClient2 {
        
    private static Socket echoSocket;
    private static PrintWriter out;
    private static BufferedReader in;
    private static String host;
    private static int port;
    
    public EchoClient2(){
        echoSocket = null;
        out = null;
        in = null;
        host = "localhost";
        port = 5000;
    }
    
    public void prepareRace(int numberOfCars, int timeOut) throws IOException{
        // open a connection with the server
        try {
            // create a socket
            echoSocket = new Socket( host, port );
            // socket's output stream
            out = new PrintWriter( echoSocket.getOutputStream(), true );
            // socket's input stream
            in = new BufferedReader( new InputStreamReader( echoSocket.getInputStream() ) );

        } catch( UnknownHostException e ) {
            System.err.println( "Don't know about " + host );
            System.exit( 1 );
        }
        catch (IOException e ) {
            System.err.println( "Couldn't get I/O for the connection to: " + host );
            System.exit( 1 );
        }
        System.out.println( "Connection is established with the Server" );
        
        // input stream from the console (messages to be sento to the server)
        BufferedReader stdIn = new BufferedReader( new InputStreamReader( System.in ) );
        BufferedReader stdIn1 = new BufferedReader( new InputStreamReader( System.in ) );
        
        String expression = "prepareNew," + String.valueOf(numberOfCars) + "," + String.valueOf(timeOut);
        
        while( true ) {
                       
            // send the message to the server
            out.println(expression);           
            
            // wait for echo
            String txt = in.readLine();
            // print echo
            System.out.println( "Client received echo: " + txt );
            if(txt.equals("Preparing new race"))
                break;
        }
        // empty message -> close connection
        out.close();
        in.close();
        echoSocket.close();
        System.out.println( "Client closed the connection" );
        //System.exit( 0 );
    }
    
    public void StartRace() throws IOException{
        // open a connection with the server
        try {
            // create a socket
            echoSocket = new Socket( host, port );
            // socket's output stream
            out = new PrintWriter( echoSocket.getOutputStream(), true );
            // socket's input stream
            in = new BufferedReader( new InputStreamReader( echoSocket.getInputStream() ) );

        } catch( UnknownHostException e ) {
            System.err.println( "Don't know about " + host );
            System.exit( 1 );
        }
        catch (IOException e ) {
            System.err.println( "Couldn't get I/O for the connection to: " + host );
            System.exit( 1 );
        }
        System.out.println( "Connection is established with the Server" );
        
        // input stream from the console (messages to be sento to the server)
        BufferedReader stdIn = new BufferedReader( new InputStreamReader( System.in ) );
        BufferedReader stdIn1 = new BufferedReader( new InputStreamReader( System.in ) );
        
        String expression = "startRace,0,0";
        
        while( true ) {
                       
            // send the message to the server
            out.println(expression);           
            
            // wait for echo
            String txt = in.readLine();
            // print echo
            System.out.println( "Client received echo: " + txt );
            if(txt.equals("Race started"))
                break;
        }
        // empty message -> close connection
        out.close();
        in.close();
        echoSocket.close();
        System.out.println( "Client closed the connection" );
        //System.exit( 0 );
        
    }
    
    
    
    public void StopRace() throws IOException{
        // open a connection with the server
        try {
            // create a socket
            echoSocket = new Socket( host, port );
            // socket's output stream
            out = new PrintWriter( echoSocket.getOutputStream(), true );
            // socket's input stream
            in = new BufferedReader( new InputStreamReader( echoSocket.getInputStream() ) );

        } catch( UnknownHostException e ) {
            System.err.println( "Don't know about " + host );
            System.exit( 1 );
        }
        catch (IOException e ) {
            System.err.println( "Couldn't get I/O for the connection to: " + host );
            System.exit( 1 );
        }
        System.out.println( "Connection is established with the Server" );
        
        // input stream from the console (messages to be sento to the server)
        BufferedReader stdIn = new BufferedReader( new InputStreamReader( System.in ) );
        BufferedReader stdIn1 = new BufferedReader( new InputStreamReader( System.in ) );
        
        String expression = "stopRace,0,0";
        
        while( true ) {
                       
            // send the message to the server
            out.println(expression);           
            
            // wait for echo
            String txt = in.readLine();
            // print echo
            System.out.println( "Client received echo: " + txt );
            if(txt.equals("Race stoped"))
                break;
        }
        // empty message -> close connection
        out.close();
        in.close();
        echoSocket.close();
        System.out.println( "Client closed the connection" );
        //System.exit( 0 );        
    }
    
    
    public String getPosition(int id) throws IOException{
        // open a connection with the server
        try {
            // create a socket
            echoSocket = new Socket( host, port );
            // socket's output stream
            out = new PrintWriter( echoSocket.getOutputStream(), true );
            // socket's input stream
            in = new BufferedReader( new InputStreamReader( echoSocket.getInputStream() ) );

        } catch( UnknownHostException e ) {
            System.err.println( "Don't know about " + host );
            System.exit( 1 );
        }
        catch (IOException e ) {
            System.err.println( "Couldn't get I/O for the connection to: " + host );
            System.exit( 1 );
        }
        System.out.println( "Connection is established with the Server" );
        
        // input stream from the console (messages to be sento to the server)
        BufferedReader stdIn = new BufferedReader( new InputStreamReader( System.in ) );
        BufferedReader stdIn1 = new BufferedReader( new InputStreamReader( System.in ) );
        
        String expression = "getPositionCar,"+String.valueOf(id)+",0";
         String txt;
        
        while( true ) {
                       
            // send the message to the server
            out.println(expression);           
            
            // wait for echo
            txt = in.readLine();
            // print echo
            System.out.println( "Client received echo: " + txt );
            if(txt.equals("Race stoped"))
                break;
        }
        // empty message -> close connection
        out.close();
        in.close();
        echoSocket.close();
        System.out.println( "Client closed the connection" );
        //System.exit( 0 );        
        return txt;
    }
    
    
    
}
