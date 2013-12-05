package balance
import java.lang.*;
import java.io.*;
import java.rmi.*;
import java.rmi.server.*;

public class CalcPI extends Thread{
	private ServerSocket srv;
    public CalcPI (){
		srv = new ServerSocket(1234);
    }
	
	public void run(){
		System.out.println("Thread gestartet");
        try {
            while (true) {             
                Socket skt = srv.accept();
				this.start();
				handleClient(skt);
            }
            // ServerSocket beenden: srvr.close(); 
        } catch (InterruptedException ie) {
            System.out.println(ie.getMessage());
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
	}
	
	private void handleClient (Socket skt){
		PrintWriter out = new PrintWriter(skt.getOutputStream(), true);
		BufferedReader in = new BufferedReader(new InputStreamReader(skt.getInputStream()));
		while(!in.ready()) this.sleep(100);
        out.print(pi(Integer.parseInt(in.readLine())));
        out.close();
	}
    private static double pi (int iterations){
        double res = 0;
        for (int i = 1; i < iterations; i += 4) {
            res += 1.0/i - 1.0/(i+2);
        }
        return 4*res;
    }
};
