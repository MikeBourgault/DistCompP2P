import java.io.*;
import java.net.*;
import java.lang.Exception;

	
public class SRComThread extends Thread 
{
	private Object [][] RTable; // routing table
	private PrintWriter out, out2, outTo; // writers (for writing back to the machine and to destination)
   private BufferedReader in, in2; // reader (for reading from the machine connected to)
	private String inputLine, outputLine, destination, addr; // communication strings
	private Socket outSocket; // socket for communicating with a destination
	private int ind; // indext in the routing table
	boolean routert;
	String lookup;
	private Socket TCPSocket, TCPSocket2;
        private String nodeType;

	// Constructor
	SRComThread(Object [][] Table, Socket toClient, int index) throws IOException
	{
		
			out = new PrintWriter(toClient.getOutputStream(), true);
			in = new BufferedReader(new InputStreamReader(toClient.getInputStream()));
			RTable = Table;
			addr = toClient.getInetAddress().getHostAddress();
			RTable[index][0] = addr; // IP addresses 
			RTable[index][1] = toClient; // sockets for communication
			ind = index;}
	
	// Run method (will run for each machine that connects to the ServerRouter)
	@Override
	public void run()
	{
		try
		{
	
		
		// Initial sends/receives
                nodeType =  in.readLine(); //ServerRouter receives nodeType (Peer or ServerRouter) as initial receive
                out.println("Connected to the router1."); // confirmation of connection

		
		
		// waits 10 seconds to let the routing table fill with all machines' information
		try{
    		Thread.currentThread().sleep(10000); 
	   }
		catch(InterruptedException ie){
		System.out.println("Thread interrupted");
		}
		
                if(nodeType.equals("Client")){
                destination = in.readLine(); // initial read (the destination for writing)
		System.out.println("Checking for " + destination + " in local routing tables.");
		boolean found = false;
                Socket thisPort;
		// loops through the routing table to find the destination
                long t0, t1, t;
                t0 = System.currentTimeMillis();
		for ( int i=0; i<10; i++) 
				{
					if (destination.equals((String) RTable[i][0])){
                                                t1 = System.currentTimeMillis();
						//outSocket = (Socket) RTable[i][1]; // gets the socket for communication from the table
						found = true;
                                                thisPort = (Socket) RTable[i][1];
                                                String foundPort =  Integer.toString(thisPort.getPort());
                                                String foundAddress = (String) RTable[i][0];
						System.out.println("Found destination: " + destination + " in router1's table.");
                                                String concatDestinationInfo = foundAddress + ":" + foundPort;
                                                System.out.println(concatDestinationInfo);
                                                out.println(concatDestinationInfo);//sends desired destination's socket information back to 
                                                in.close();
                                                out.close();
                                                //toClient.close();
                                                t = t1-t0;
                                                System.out.println("Lookup took: " + t);
                                                Thread.currentThread().interrupt();
						//outTo = new PrintWriter(outSocket.getOutputStream(), true); // assigns a writer
				}}
                t1 = System.currentTimeMillis();
                if(found ==false)
                {
                    t = t1-t0;
                    System.out.println("Lookup took: " + t);
                }
		if(found ==false) {
        	 try {
                 TCPSocket2 = new Socket("127.0.0.1" , 5901); //initialize connection between other SR 
                 System.out.println("Created TCP Socket for SR to SR connection.");
             } catch (UnknownHostException e) {
                 System.err.println("Don't know about router: ");
                 e.printStackTrace();
                 System.exit(1);
             } catch (IOException e) {
                 System.err.println("Couldn't get I/O for the connection to Server router " );
                 e.printStackTrace();
                 System.exit(1);
             }
        	 
			out2 = new PrintWriter(TCPSocket2.getOutputStream(), true);
			in2 = new BufferedReader(new InputStreamReader(TCPSocket2.getInputStream()));
			
	        String fromServer; // messages received from ServerRouter
	        String fromUser; // messages sent to ServerRouter
		    //String address ="192.168.1.216"; // destination IP (Server)
				
			out2.println("ServerRouter");// initial send to other SR stating node type
			fromServer = in2.readLine();//initial receive from router (verification of connection)
			System.out.println("ServerRouter: " + fromServer);
                        out2.println(destination);
                        fromServer = in2.readLine();
                        out.println(fromServer);
                        out.close();
                        in.close();
                        in2.close();
                        out2.close();
                        TCPSocket2.close();
                        
			//out.println("192.168.1.224"); // Client sends the IP of its machine as initial send
	
		}
                } //end of if statement
                
                
                if(nodeType.equals("ServerRouter")){
                destination = in.readLine(); // initial read (the destination for writing)
		System.out.println("Checking for " + destination + " in local routing tables.");
                    
                    boolean found = false;
                    Socket thisPort;
		// loops through the routing table to find the destination
                long t0, t1, t;
                t0 = System.currentTimeMillis();
		for ( int i=0; i<10; i++) 
				{
					if (destination.equals((String) RTable[i][0])){
                                                t1 = System.currentTimeMillis();
						//outSocket = (Socket) RTable[i][1]; // gets the socket for communication from the table
						found = true;
                                                thisPort = (Socket) RTable[i][1];
                                                String foundPort =  Integer.toString(thisPort.getPort());
                                                String foundAddress = (String) RTable[i][0];
						System.out.println("Found destination: " + destination + " in router1's table.");
                                                String concatDestinationInfo = foundAddress + ":" + foundPort;
                                                out.println(concatDestinationInfo);//sends desired destination's socket information back to 
                                                in.close();
                                                out.close();
                                                //TCPSocket.close();
                                                Thread.currentThread().interrupt();
						//outTo = new PrintWriter(outSocket.getOutputStream(), true); // assigns a writer
				}}
                t1 = System.currentTimeMillis();
                if(found ==false)
                {
                    t = t1-t0;
                    System.out.println("Lookup took: " + t);
                }
		if(found ==false) {
                    System.out.println("Did not find peer in router1's table.");
                    out.println("NF:NF"); //tells SR that the peer does not exist in its routing table
                    Thread.currentThread().interrupt(); //interrupts current thread due to client no longer needing connection
                    
                } //end if
                
                } //end ServerRouter if statement
                
                
                if(nodeType.equals("Server")){
                    while(true){
                        //keeps connection active until socket closes from serverside
                    }
                    
                }		 
		 }// end try
			catch (IOException e) {
               System.err.println("Could not listen to socket.");
               System.exit(1);
         }
	
	}	
}
