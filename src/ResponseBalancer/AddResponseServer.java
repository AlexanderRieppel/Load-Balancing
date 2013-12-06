package ResponseBalancer;

import java.util.concurrent.PriorityBlockingQueue;

import ResponseServer.ResponseServer;
/**
 * Fuegt Angegebenen Server zur Serverqueue hinzu
 * @author Thomas Traxler
 *
 */
public class AddResponseServer implements Befehl{

	private PriorityBlockingQueue<ResponseServer>qs;
	
	public AddResponseServer (PriorityBlockingQueue<ResponseServer> s){
		qs=s;
	}
	@Override
	public void execute(String[] args) {
		
		try {
			qs.add(new ResponseServer(args[1],Integer.parseInt(args[2])));
		} catch (NumberFormatException e) {
			System.out.println(e.getMessage());
		}
		
	}

}
