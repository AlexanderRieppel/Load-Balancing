package balance;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.PriorityBlockingQueue;
/**
 * Balancerklasse mit Least Connection
 * @author Thomas Traxler
 *
 */
public class Balancer extends Thread {

	private static ConcurrentHashMap<String, Befehl> arg = new ConcurrentHashMap<String, Befehl>();
	private PriorityBlockingQueue<Server> q;
	private ServerSocket srvr;
	/**
	 * Balancer Init
	 * @param args
	 */
	public static void main (String[] args) {
		System.out.println("Balancer started");
		Balancer b = new Balancer();
	}
	/**
	 * neuer Balancer gestartet, empfaengt eingehende Clients
	 */
	public Balancer() {
		q = new PriorityBlockingQueue<Server>(10,new ServerComparator());
		arg.put("addServer", new AddServer(q));
		try {
			srvr = new ServerSocket(1234);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		this.start();
		while (true) {
			BufferedReader br = new BufferedReader(new InputStreamReader(
					System.in));
			try {
				if (br.ready()){
					String s = br.readLine();
					arg.get(s.split(" ", 2)[0]).execute(s.split(" "));
				}
					
			} catch (IOException e) {
				e.printStackTrace();
			}

		}

	}
	/**
	 * Startet neue Clientverbindung
	 * @param pq
	 * @param srv
	 * @param ar
	 */
	private Balancer (PriorityBlockingQueue pq, ServerSocket srv,ConcurrentHashMap<String, Befehl> ar){
		arg=ar;
		srvr=srv;
		q=pq;
	}
	
	/**
	 * Verarbeitet neuen Client
	 */
	public void run() {
		try {
				
				Socket skt = srvr.accept();
				BufferedReader in = new BufferedReader(new InputStreamReader(skt.getInputStream()));
				new Balancer(q,srvr,arg).start();
				while(!in.ready())this.sleep(100);
				while(q.size()==0);
				Server s = q.remove();
				q.add(s);
				System.out.println("Neue Verbindung bei Server "+s.getIdentify()+" mit "+s.getConnections()+" Connections");
				PrintWriter out = new PrintWriter(skt.getOutputStream(), true);
				out.println(s.calcPi(Integer.parseInt(in.readLine()))+"\n");
				out.flush();
				out.close();
				
		} catch (IOException | InterruptedException ex) {
			System.out.println(ex.getMessage());
		}
	}
	/**
	 * Vergleicht zwei Server
	 * @author Thomas Traxler
	 *
	 */
	private class ServerComparator implements Comparator<Server>
	{
	    @Override
	    public int compare(Server x, Server y)
	    {
	        if (x.getConnections() < y.getConnections())
	        {
	            return 1;
	        }
	        if (x.getConnections() > y.getConnections())
	        {
	            return -1;
	        }
	        return 0;
	    }
	}
}
