package clients;     

   import java.io.*;
   import java.net.*;

    public class TCPServer extends Peer{
    	
        public TCPServer(String routerHostIP, int routerRort) throws UnknownHostException {
            super("Server", routerHostIP, routerRort);
        }
    	@Override
    	public void run() throws IOException{
    	   
    	  
    	    System.out.println("SERVER:");

            // Variables for setting up connection and communication
            
            //ServerThread sThread = new ServerThread();
            //sThread.run();
            Socket Socket = null; // socket to connect with ServerRouter
            PrintWriter out = null; // for writing to ServerRouter
            BufferedReader in = null; // for reading form ServerRouter
            String host = getLocalIP(); // Server machine's IP
            String routerName = "192.168.1.224"; //IP of server router 
            int SockNum = getRouterPort(); // port number
            //int hostPort = getLocalPort(); not needed. This architecture has all serverthreads listening on port 6000

            out = new PrintWriter(getSocket().getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(getSocket().getInputStream()));

            // Variables for message passing
            String fromServer; // messages sent to ServerRouter
            String fromClient; // messages received from ServerRouter
            //String address = "192.168.1.216"; // destination IP (Client)

            // Communication process (initial sends/receives)

 
            
            
 
            //sThread.run();
            
            out.println("Server");// initial send (IP of the destination Client)
            fromClient = in.readLine();// initial receive from router (verification of connection)
            System.out.println("ServerRouter: " + fromClient);// prints verification of connection to server router

            // Communication while loop
            while ((fromClient = in.readLine()) != null) {  //blocks while waiting for input which will never come which leaves connection always up to SR

                System.out.println("Client said: \"" + fromClient + "\"");
                if (fromClient.equals("Bye.")) // exit statement
                    break;

                fromServer = fromClient.toUpperCase(); // converting received message to upper case
                System.out.println("Server said: " + fromServer);
                out.println(fromServer); // sending the converted message back to the Client via ServerRouter
            }

            // closing connections
            out.close();
            in.close();
            getSocket().close();

       }

    }
