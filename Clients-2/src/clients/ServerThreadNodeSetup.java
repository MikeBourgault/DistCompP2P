package clients;

import java.net.UnknownHostException;
import java.util.*;
import java.io.IOException;

public class ServerThreadNodeSetup {
	
	
	
	public static void main(String[] args ) throws IOException {
		
       
       try {
	ServerThread s1 = new ServerThread();
        s1.run();}
        
	catch (UnknownHostException e) {
		e.printStackTrace();
	}

	}
}
