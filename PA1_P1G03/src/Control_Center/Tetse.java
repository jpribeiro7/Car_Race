/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Control_Center;

import java.io.IOException;
import java.util.Scanner;

/**
 *
 * @author Nuno Aparicio
 */
public class Tetse {
    
        public static void main( String[] args ) throws IOException, InterruptedException {
        
            
            
            while (true){
                EchoClient2 t = new EchoClient2();
                Scanner keyboard = new Scanner(System.in);
                System.out.println("enter an integer");
                int myint = keyboard.nextInt();
                switch(myint){
                        case 1: 
                           t.prepareRace(2,500);
                            System.out.println("ppppp");
                           break;
                        case 2:
                           t.StartRace();
                            System.out.println("ssss");
                           break;
                }
            }
            
            
            
        }
    
}
