package balance;
import java.lang.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.io.*;
import java.rmi.*;
import java.rmi.server.*;

public class CalcPI extends Thread{
	private ServerSocket srv;
    public CalcPI (){
		try {
			srv = new ServerSocket(1234);
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.start();
    }
    public static void main (String[] args){
    	new CalcPI();
    }
	
	public void run(){
		System.out.println("Thread gestartet");
        try {
            while (true) {             
                Socket skt = srv.accept();
				this.start();
				handleClient(skt);
				skt.close();
            }
            // ServerSocket beenden: srv.close(); 
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
	}
	
	private void handleClient (Socket skt){
		PrintWriter out;
		try {
			out = new PrintWriter(skt.getOutputStream(), true);
			BufferedReader in = new BufferedReader(new InputStreamReader(skt.getInputStream()));
		while(!in.ready()) this.sleep(100);
			try {
				out.print(CalcPI.pi(Integer.parseInt(in.readLine())));
			}catch (NumberFormatException nfe){
				out.print("Not a Number");
			}
        	out.close();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
    private static double pi (int iterations){
        double res = 0;
        for (int i = 1; i < iterations; i += 4) {
            res += 1.0/i - 1.0/(i+2);
        }
        return 4*res;
    }
};
