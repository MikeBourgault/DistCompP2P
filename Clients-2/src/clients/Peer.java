package clients;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public abstract class Peer{
	
	private String hostPort;
    private String routerHostIP;
    private int routerPort;
    private Socket TCPSocket;

    public Peer(String name, String routerHostIP, int routerRort) {
        try {
            this.TCPSocket = new Socket(routerHostIP, routerRort);
            System.out.println("Created Socket");
        } catch (UnknownHostException e) {
            System.err.println("Don't know about router: " + routerHostIP);
            e.printStackTrace();
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to: " + routerHostIP);
            e.printStackTrace();
            System.exit(1);
        } catch (Exception e) {
            System.err.println("CAUGHT YA!!!");
            e.printStackTrace();
        }
        System.out.println("Initialized peer");
        this.routerHostIP = routerHostIP;
        this.routerPort = routerRort;
        //this.peerSetup = new SetupDialog(name, getHostIP()+":"+getHostPort());
    }

    public String getLocalIP() {
        return this.TCPSocket.getLocalAddress().toString().substring(1);
    }

    public int getLocalPort() {return this.TCPSocket.getLocalPort();}

    public String getRouterIP() {
        return routerHostIP;
    }

    public int getRouterPort() {
        return routerPort;
    }

    public Socket getSocket() {
        return TCPSocket;
    }

    //public String promptDestIP() {
        //String addr = this.peerSetup.showAndWait().get();
        //return addr;
    //5}

    public abstract void run() throws IOException;
}
