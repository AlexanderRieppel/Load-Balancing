package balance;

import java.io.*;
import java.net.*;
/**
 * Client
 * @author Alexander Rieppel
 *
 */
public class Main {

	/**
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			Socket cs = new Socket(args.length <= 1 ? "127.0.0.1" : args[0],1234);
			PrintWriter bw = new PrintWriter(new OutputStreamWriter(cs.getOutputStream()));
			BufferedReader in = new BufferedReader(new InputStreamReader(cs.getInputStream()));
			Thread.sleep(100);
			bw.println(args.length <= 1 ?args[0]:args[1]);
			bw.flush();
			while (!in.ready())Thread.sleep(100);
			System.out.println(in.readLine());
			bw.close();
			in.close();
			cs.close();

		} catch (IOException | InterruptedException e) {
			System.out.println(e.getMessage());
		}
	}
}
