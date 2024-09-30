package server;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Pipe implements Runnable{
final Socket so;
private BufferedReader msd;
private PrintWriter out;


public Pipe(Socket sock){
	this.so=sock;
	
	try {
		msd = new BufferedReader(new InputStreamReader(so.getInputStream())); 
		out = new PrintWriter(so.getOutputStream(),true);
	} catch (Exception e) {
		e.printStackTrace();
	}
	
}

	@Override
	public void run() {
		try {
			String ms;
			while((ms = msd.readLine())!=null) {
				String tujuan = ms.substring(0,ms.indexOf(">"));
				System.out.println(so.getInetAddress()+" -> "+tujuan+" : "+ms.substring(ms.indexOf(">")+1));
				if(MainMenu.client.containsKey(tujuan)) {
					Socket tjx = MainMenu.client.get(tujuan);
					PrintWriter pt = new PrintWriter(tjx.getOutputStream(),true);
					pt.println(ms.substring(ms.indexOf(">")+1));
				}else {
					out.println("no user associated with that address");
				}
			}
			MainMenu.client.remove(so.getInetAddress().toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}