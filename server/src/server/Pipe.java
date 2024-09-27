package server;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Pipe implements Runnable{
final Socket so;
Pipe(Socket sock){this.so=sock;}

	
	public void run() {
		try {
			System.out.println("client connected"+so.getInetAddress()+so.getInputStream());
			BufferedReader msd = new BufferedReader(new InputStreamReader(so.getInputStream())); 
			PrintWriter out = new PrintWriter(so.getOutputStream(),true);
			String ms;
			while((ms = msd.readLine())!=null) {
				System.out.println("["+so.getInetAddress()+"]"+ms);
				out.println(ms);
				System.out.println(out.checkError());
			}
			
			while(so.getInputStream().read()<0) {
				so.close();
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}