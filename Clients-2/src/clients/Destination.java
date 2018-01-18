package clients;

import java.util.*;

public class Destination{
	
	private String destIP;
	private String HostIP;
	private int HostPort;
	
	public Destination(String HostIP, int HostPort) {
		
		this.HostIP = HostIP;
		this.HostPort = HostPort;
	}
	
	public String getDestIP() {
		
		Scanner s = new Scanner(System.in);
		this.destIP = s.nextLine();
		return this.destIP;
	}
	
	public String getInfo() {
		
		String info = this.HostIP + "   " + this.HostPort + "    " + this.destIP;
		return info;
		
	}

}
