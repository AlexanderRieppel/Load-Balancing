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
			srv = new ServerSocket(1234);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void run() {
		System.out.println("Thread gestartet");
		try {
			while (true) {
				Socket skt = srv.accept();
				this.start();
				handleClient(skt);
			}
			// ServerSocket beenden: srvr.close();
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
			while (!in.ready())
				this.sleep(100);
			out.print(pi(Integer.parseInt(in.readLine())));
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private static double pi(int iterations) {
		double res = 0;
		for (int i = 1; i < iterations; i += 4) {
			res += 1.0 / i - 1.0 / (i + 2);
		}
		return 4 * res;
	}
};
