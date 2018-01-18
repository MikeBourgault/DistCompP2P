package clients;

import java.net.UnknownHostException;
import java.util.*;
import java.io.IOException;

public class NodeSetup {
	
	
	
	public static void main(String[] args ) throws IOException {
		
	//while(true) {
	//Scanner s = new Scanner(System.in);
	//System.out.println("Enter server destination address for connection: ");
	//String serverIP = s.next();
        //s = new Scanner(System.in);
        
        //System.out.println("Would you like to send a message from this client? y or n");
	//String answer = s.next();
        //if(!answer.equals("y")){
            //continue;
        //}
        
       //try {
	//TCPServer s1 = new TCPServer("192.168.1.216", 5901);
        //s1.run();}
        
	//catch (UnknownHostException e) {
		//e.printStackTrace();
	//}
        while(true){
        try {
	TCPClient c1 = new TCPClient("127.0.0.1", 5901);
        c1.run();}
        
	catch (UnknownHostException e) {
		e.printStackTrace();
	}
        }
        //try{
        //Thread.sleep(100000);}
        //catch (InterruptedException ie){
            //ie.printStackTrace();
        //}
	
	//try {
	//TCPServer s1 = new TCPServer(serverIP, 5801);}
	//catch (UnknownHostException e) {
		//e.printStackTrace();
	//}
        

        
	//}
	}
}
