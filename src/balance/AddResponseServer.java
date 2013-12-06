package balance;

import java.io.IOException;
import java.net.Socket;
import java.util.Queue;

public class AddResponseServer implements Befehl{

	private Queue<ResponseServer>qs;
	
	public AddResponseServer (Queue<ResponseServer> s){
		qs=s;
	}
	@Override
	public void execute(String[] args) {
		
		try {
			qs.add(new ResponseServer(args[1],Integer.parseInt(args[2])));
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
		
	}

}
