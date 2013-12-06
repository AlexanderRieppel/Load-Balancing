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
/**
 * Balancerklasse mit Server Probes
 * @author Alexander Rieppel
 *
 */
public class ResponseBalancer extends Thread {

	private static ConcurrentHashMap<String, Befehl> arg = new ConcurrentHashMap<String, Befehl>();
	private PriorityQueue<ResponseServer> q;
	private ServerSocket srvr;
	/**
	 * Balancer Init
	 * @param args
	 */
	public static void main (String[] args) {
		System.out.println("Balancer started");
		ResponseBalancer b = new ResponseBalancer();
	}
	/**
	 * neuer Balancer gestartet, empfaengt eingehende Clients
	 */
	public ResponseBalancer() {
		q = new PriorityQueue<ResponseServer>(10,new ResponseServerComparator());
		arg.put("addServer", new AddResponseServer(q));
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
	public ResponseBalancer (PriorityQueue pq, ServerSocket srv,ConcurrentHashMap<String, Befehl> ar){
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
				new ResponseBalancer(q,srvr,arg).start();
				while(!in.ready())this.sleep(100);;
				ResponseServer s = q.remove();
				q.add(s);

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
	 * @author Alexander Rieppel
	 *
	 */
	private class ResponseServerComparator implements Comparator<ResponseServer>
	{
	    @Override
	    public int compare(ResponseServer x, ResponseServer y)
	    {
	        if (x.getConnections() < y.getConnections())
	        {
	            return -1;
	        }
	        if (x.getConnections() > y.getConnections())
	        {
	            return 1;
	        }
	        return 0;
	    }
	}
}
