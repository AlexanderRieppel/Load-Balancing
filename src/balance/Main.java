package balance;

import java.io.*;
import java.net.*;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			Socket cs = new Socket(args.length == 0 ? "127.0.0.1" : args[0],1234);
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(cs.getOutputStream()));
			BufferedReader br = new BufferedReader(new InputStreamReader(cs.getInputStream()));
			bw.write(args[1]);
			bw.flush();
			bw.close();
			while (!br.ready());

		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}
}
