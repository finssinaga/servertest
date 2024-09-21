package server;

import java.awt.EventQueue;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import javax.swing.JOptionPane;


public class socketthread {
public static ArrayList<String> conect = new ArrayList<>();
private ServerSocket svc; 
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

public synchronized static void setlist(String conn){
	conect.add(conn);
}
public synchronized static ArrayList<String> getlist(){
	return conect;
}
}

