package balance;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Server {
	
	private Socket skt;
	private int connections;
	
	public Server (Socket s){
		skt=s;
	}
	
	public double calcPi (int iterations) throws IOException {
		connections++;
		PrintWriter out = new PrintWriter(skt.getOutputStream(), true);
		BufferedReader in = new BufferedReader(new InputStreamReader(skt.getInputStream()));
		out.println(iterations);
		while(!in.ready());
		out.close();
		in.close();
		connections--;
		return Double.parseDouble(in.readLine());
		 
	}
	
	public int getConnections(){
		return connections;
	}
}
