package server;

import java.awt.EventQueue;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JOptionPane;


public class socketthread {
	

private String message;
private Socket sc;
private ServerSocket svc;
private InputStream input;
public socketthread() {
		try {
			svc = new ServerSocket(5000);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			JOptionPane.showMessageDialog(null, e);
		}
		
}
public synchronized ServerSocket svc() {
	return svc;
}
public synchronized Socket setSocket(Socket s){
	this.sc=s;
	return sc;
}
public synchronized InputStream getmsg(){
	try {
		input = sc.getInputStream();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	return input;
}
private static socketthread sct;
public synchronized static socketthread sct(){
	if (sct==null){
		sct = new socketthread();
	}
	return sct;
}
public static void main(String[] args){
	EventQueue.invokeLater(new Runnable() {
		
		public void run() {
			// TODO Auto-generated method stub
			socketthread ssss = socketthread.sct();
		}
	});
}

}

