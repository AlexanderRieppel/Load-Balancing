package LeastBalancer;

import java.util.concurrent.PriorityBlockingQueue;

import LeastServer.Server;
/**
 * Fuegt Angegebenen Server zur Serverqueue hinzu
 * @author Thomas Traxler
 *
 */
public class AddServer implements Befehl{

	private PriorityBlockingQueue<Server>qs;
	
	public AddServer (PriorityBlockingQueue<Server> s){
		qs=s;
	}
	@Override
	public void execute(String[] args) {
		
		try {
			qs.add(new Server(args[1],Integer.parseInt(args[2])));
		} catch (NumberFormatException e) {
			System.out.println("Keine gueltigen parameter angegeben.");
		}
		
	}

}
