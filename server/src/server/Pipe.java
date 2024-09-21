package server;

import java.io.DataInputStream;
import java.net.Socket;

public class Pipe implements Runnable{
final Socket so;
Pipe(Socket sock){this.so=sock;}

	@Override
	public void run() {
		System.out.println(so.getInetAddress());
		try {
			while(so.getInputStream().read() != -1) {
			DataInputStream msg = new DataInputStream(so.getInputStream());
			System.out.println(msg.readUTF());
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