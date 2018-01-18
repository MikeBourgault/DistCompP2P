package clients;   

import java.io.*;
   import java.net.*;
   import java.util.*;

    public class TCPClient extends Peer{
        
        //public Reader reader;
        
        //public double lookUpTime;
    	
        public TCPClient(String routerHostIP, int routerRort) throws UnknownHostException {
            super("Client", routerHostIP, routerRort);
        }
      	
        @Override
        public void run(){
            
        try{
			// Variables for setting up connection and communication
        Socket Socket = null; // socket to connect with ServerRouter
        PrintWriter out = null; // for writing to ServerRouter
        BufferedReader in = null; // for reading form ServerRouter
        String host = getLocalIP(); // Client machine's IP
			
// Tries to connect to the ServerRouter
      
           out = new PrintWriter(getSocket().getOutputStream(), true);
           in = new BufferedReader(new InputStreamReader(getSocket().getInputStream()));

      	// Variables for message passing
      
        Reader reader = new FileReader(".//src//clients//text.txt"); 

        
        
	@SuppressWarnings("resource")
        BufferedReader fromFile =  new BufferedReader(reader); // reader for the string file
        String fromServer; // messages received from ServerRouter
        String fromUser; // messages sent to ServerRouter
	//String address = "127.0.0.2"; // destination IP (Server)
        //lookUpTime = 3.4234;
        //String name = "colby";
        Scanner s = new Scanner(System.in);
        System.out.println("Enter location of peer you wish to connect to:");
        String address = s.nextLine();

        long t0, t1, t;
        long totalT = 0;
                        // Communication process (initial sends/receives
        out.println("Client");// initial send (IP of the destination Server)
        fromServer = in.readLine();//initial receive from router (verification of connection)
        out.println(address);
        fromServer = in.readLine();
        String serverInfo = fromServer;     // full file name
        System.out.println(serverInfo);
        String[] parts = serverInfo.split(":", 2); // String array, each element is text between colons
        String IP = parts[0];
        out.close();
        in.close();
        getSocket().close();
        if(fromServer.equals("NF:NF")){
            System.out.println("Host is not on network!");
            //System.exit(0);
        }
        
        //int port = Integer.parseInt(parts[1]);

        
        else{
        
        try{
           Socket = new Socket(IP, 6000); //CHANGE FROM HARD CODED LATER
           out = new PrintWriter(Socket.getOutputStream(), true);
           in = new BufferedReader(new InputStreamReader(Socket.getInputStream()));
        
        }
        
        catch(IOException e){
            System.out.println("Failed to connect to Peer");
        }

        //out.println(address);
        //System.out.println("Peer: " + fromServer);
        out.println(host); // Client sends the IP of its machine as initial send
        fromServer = in.readLine();
        System.out.println("Server: " + fromServer);
        out.println("Start");
        t0 = System.currentTimeMillis();

        // Communication while loop
        while ((fromServer = in.readLine()) != null) {
            System.out.println("Server: \"" + fromServer + "\"");
            t1 = System.currentTimeMillis();

            if (fromServer.equals("Bye.")) {
                System.out.println("HELLO?");
                break;
            }
            t = t1 - t0;
            totalT += t;
            System.out.println("Cycle time: " + t);

            fromUser = fromFile.readLine(); // reading strings from a file
            if (fromUser != null) {
                System.out.println("Client: " + fromUser);
                out.println(fromUser); // sending the strings to the Server via ServerRouter
                System.out.println("ONE SENT");
                t0 = System.currentTimeMillis();
            }
            else{
                System.out.println("FINISHED LOOP!");
                System.out.println("Total transmission cycle: " + totalT);
                out.close();
                in.close();
                getSocket().close();
            }
        }
            


        // closing connections
        out.flush();
        out.close();
        in.close();
        Socket.close();}
        //System.out.println("Lookup Time: " + lookUpTime);
    } catch (IOException e){
        System.err.println("Ended");
    }
    }
        
    }