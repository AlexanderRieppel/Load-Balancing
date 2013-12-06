package balance;

import java.io.IOException;
import java.net.Socket;
import java.util.Queue;

public class AddServer implements Befehl{

	private Queue<Server>qs;
	
	public AddServer (Queue<Server> s){
		qs=s;
	}
	@Override
	public void execute(String[] args) {
		
		try {
			qs.add(new Server(new Socket(args[1],Integer.parseInt(args[2]))));
		} catch (NumberFormatException | IOException e) {
			e.printStackTrace();
		}
		
	}

}
