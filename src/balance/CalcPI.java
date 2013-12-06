package balance;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class CalcPI extends Thread {
	private ServerSocket srv;

	public CalcPI() {
		try {
			srv = new ServerSocket(1235);
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.start();
    }
	
	public CalcPI(ServerSocket sr){
		srv=sr;
	}
    public static void main (String[] args){
    	new CalcPI();
	}

	public void run() {
		System.out.println("Thread started");
		try {
				Socket skt = srv.accept();
				
				new CalcPI(srv).start();
				handleClient(skt);
				skt.close();
			
            // ServerSocket beenden: srv.close(); 
		} catch (IOException ex) {
			System.out.println(ex.getMessage());
		}
	}

	private void handleClient(Socket skt) {
		PrintWriter out;
		try {
			out = new PrintWriter(skt.getOutputStream(), true);
			BufferedReader in = new BufferedReader(new InputStreamReader(
					skt.getInputStream()));
			try {
				out.println(CalcPI.pi(Integer.parseInt(in.readLine())));
			}catch (NumberFormatException nfe){
				out.print("Not a Number");
			}
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static double pi(int iterations) {
		double res = 0;
		for (int i = 1; i < iterations; i += 4) {
			res += 1.0 / i - 1.0 / (i + 2);
			System.out.println(iterations+" i:"+i+" res:"+res);
		}
		System.out.println("fertig");
		return 4 * res;
	}
};
