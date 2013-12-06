package balance;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
/**
 * Macht Verbindung von Balancer zu Server
 * @author Thomas Traxler
 *
 */
public class Server {
	
	private String url;
	private int port;
	private int connections;
	/**
	 * Init
	 * @param url
	 * @param port
	 */
	public Server (String url, int port){
		this.url=url;
		this.port=port;
	}
	/**
	 * Berechnung aufrufen udn Antwort zurueckgeben
	 * @param iterations
	 * @return
	 * @throws IOException
	 */
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
	/**
	 * Return damit Server vergleichbar sind
	 * @return
	 */
	public int getConnections(){
		return connections;
	}
}
