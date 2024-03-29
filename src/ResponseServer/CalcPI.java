package ResponseServer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
/**
 * Berechnet PI
 * @author Thomas Traxler
 *
 */
public class CalcPI extends Thread {
	private ServerSocket srv;
	/**
	 * Init
	 * @param s
	 */
	public CalcPI(String s) {
		try {
			srv = new ServerSocket(Integer.parseInt(s));
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
		this.start();
    }
	/**
	 * init
	 * @param sr
	 */
	public CalcPI(ServerSocket sr){
		srv=sr;
	}
    public static void main (String[] args){
    	new CalcPI(args.length>0?args[0]:"1235");
    	if(args.length==0){
    		System.out.println("Port 1235");
    	}else{
    		System.out.println("Port "+args[0]);
    	}
	}
    /**
     * Startet anfragenbehandlung
     */
	public void run() {
		System.out.println("Thread started");
		try {
				Socket skt = srv.accept();
				System.out.println(skt.getPort());
				new CalcPI(srv).start();
				handleClient(skt);
				skt.close();
			
            // ServerSocket beenden: srv.close(); 
		} catch (IOException ex) {
			System.out.println(ex.getMessage());
		}
	}
	/**
	 * Handled eine Anfrage
	 * @param skt
	 */
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
			out.close();
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}
	/**
	 * Macht berechnung
	 * @param iterations
	 * @return
	 */
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
