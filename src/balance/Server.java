package balance;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Server {
	
	private String url;
	private int port;
	private int connections;
	
	public Server (String url, int port){
		this.url=url;
		this.port=port;
	}
	
	public String calcPi (int iterations) throws IOException {
		Socket skt = new Socket (url,port);
		connections++;
		
		PrintWriter out = new PrintWriter(skt.getOutputStream(), true);
		BufferedReader in = new BufferedReader(new InputStreamReader(skt.getInputStream()));
		out.println(iterations);
		while(!in.ready());
		connections--;
		return in.readLine();
		 
	}
	
	public int getConnections(){
		return connections;
	}
}
