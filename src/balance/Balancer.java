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

public class Balancer extends Thread {

	private static ConcurrentHashMap<String, Befehl> arg = new ConcurrentHashMap<String, Befehl>();
	private PriorityQueue<Server> q;
	private ServerSocket srvr;

	public static void main (String[] args) {
		Balancer b = new Balancer();
	}
	public Balancer() {
		q = new PriorityQueue<Server>(10,new ServerComparator());
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

	

	public void run() {
		try {
				
				Socket skt = srvr.accept();
				this.start();
				PrintWriter out = new PrintWriter(skt.getOutputStream(), true);
				BufferedReader in = new BufferedReader(new InputStreamReader(skt.getInputStream()));
				while(!in.ready());
				Server s = q.remove();
				q.add(s);
				out.print(s.calcPi(Integer.parseInt(in.readLine())));
				out.close();
				
		} catch (IOException ex) {
			System.out.println(ex.getMessage());
		}
	}
	private class ServerComparator implements Comparator<Server>
	{
	    @Override
	    public int compare(Server x, Server y)
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
