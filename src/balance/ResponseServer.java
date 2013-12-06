package balance;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class ResponseServer extends Thread{
	
	private String url;
	private int port;
	private long respTime;
	
	public ResponseServer (String url, int port){
		this.url=url;
		this.port=port;
		this.start();
	}
	
	public String calcPi (int iterations) throws IOException {
		Socket skt = new Socket (url,port);
		
		PrintWriter out = new PrintWriter(skt.getOutputStream(), true);
		BufferedReader in = new BufferedReader(new InputStreamReader(skt.getInputStream()));
		out.println(iterations);
		while(!in.ready());
		return in.readLine();
		 
	}
	
	public long getConnections(){
		return respTime;
	}
	public void run(){
		while(true){
			long time = System.currentTimeMillis();
			Socket skt;
			try {
				skt = new Socket (url,port);
				PrintWriter out = new PrintWriter(skt.getOutputStream(), true);
				BufferedReader in = new BufferedReader(new InputStreamReader(skt.getInputStream()));
				out.println(1);
				while(!in.ready());
			} catch ( IOException e) {
				e.printStackTrace();
			}
			respTime=System.currentTimeMillis()-time;
			try {
				this.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
