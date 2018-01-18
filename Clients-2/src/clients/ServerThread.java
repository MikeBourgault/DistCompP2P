/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clients;
import java.io.*;
import java.net.*;
import java.lang.Exception;
/**
 *
 * @author colby
 */
public class ServerThread{
	
		private PrintWriter out, outTo; // writers (for writing back to the machine and to destination)
	    private BufferedReader in; // reader (for reading from the machine connected to)
		private String inputLine, outputLine, destination, addr; // communication strings
		private Socket outSocket, acceptedSocket; // socket for communicating with a destination
	// Constructor
	ServerThread() throws IOException
	{
		//out = new PrintWriter(toRouter.getOutputStream(), true);
		//in = new BufferedReader(new InputStreamReader(toRouter.getInputStream()));
		//addr = toRouter.getInetAddress().getHostAddress();
	}
	public void run() {
      	try {
            
            
            
		// Variables for setting up connection and communication
     Socket Socket = null; // socket to connect with ServerRouter
     PrintWriter out = null; // for writing to ServerRouter
     BufferedReader in = null; // for reading form ServerRouter
		InetAddress addr = InetAddress.getLocalHost();
		String host = addr.getHostAddress(); // Server machine's IP			
		String routerName = "192.168.1.224"; // ServerRouter host name
		int SockNum = 5555; // port number
                
                
     ServerSocket serverSocket = new ServerSocket(6000);
		
		// Tries to connect to the ServerRouter
 while(true){
     try {
            acceptedSocket = serverSocket.accept();
            System.out.println(acceptedSocket.getPort());
       
        
        out = new PrintWriter(acceptedSocket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(acceptedSocket.getInputStream()));
        
        
     } 
         catch (IOException e) {
           System.err.println("Couldn't connect to peer");
           System.exit(1);
        } 

			
  	// Variables for message passing			
     String fromServer; // messages sent to ServerRouter
     String fromClient; // messages received from ServerRouter      
     String address = acceptedSocket.getInetAddress().getHostAddress(); // destination IP (Client)
     int port = acceptedSocket.getPort(); //gets socket information from accepted connection for possible future use
		
		// Communication process (initial sends/receives)
                fromClient = in.readLine();
                out.println("Peer is connected with you on port: " + port);// initial send
		//fromClient = in.readLine();// initial receive from router (verification of connection)
		//System.out.println("ServerRouter: " + fromClient);
                
		         
		// Communication while loop
  	while ((fromClient = in.readLine()) != null) {
        System.out.println("Client said: " + fromClient);
        if (fromClient.equals("Bye.")) // exit s-tatement
				break;
			fromServer = fromClient.toUpperCase(); // converting received message to upper case
			System.out.println("Server said: " + fromServer);
        out.println(fromServer); // sending the converted message back to the Client via ServerRouter
     }
		
		// closing connections
     out.flush();
     out.close();
     in.close();
     acceptedSocket.close();
     }
  }			catch (IOException e) {
      System.err.println("Could not listen to socket.");
      System.exit(1);
	}
	}
}
