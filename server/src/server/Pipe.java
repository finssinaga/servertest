package server;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Base64;

public class Pipe implements Runnable{
final Socket so;
private BufferedReader msd;
private PrintWriter out;

public void broadcast(String message, Socket scx) throws Exception {
	for (Runnable user : MainMenu.client.keySet()) {
		if(MainMenu.client.get(user)!=scx) {
			MainMenu.client.get(user).getOutputStream();
			new PrintWriter(MainMenu.client.get(user).getOutputStream(),true).println(message);
		}
	}
}
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
				if(ms.contains(">")) {
					broadcast(ms.substring(ms.indexOf(">")+1), so);
					MainMenu.getjtext().setText(MainMenu.getjtext().getText()+"\n"+ms);
				}
				if(ms.contains("[REG]")) {
					out.println("Welcome "+ms.substring(0,ms.indexOf("#"))+". Current online user : "+MainMenu.client.size());
					MainMenu.getjtext().setText(MainMenu.getjtext().getText()+"\n"+"User Login : "+ms.substring(0,ms.indexOf("#")));
				}
			}
			MainMenu.client.remove(this);
			MainMenu.getjtext().setText(MainMenu.getjtext().getText()+"\n"+"removed user "+so.getInetAddress());
		} catch (Exception e) {
			MainMenu.getjtext().setText(MainMenu.getjtext().getText()+"\n"+e);
		}
	}
public String setuser(String user, String pass) {
	String usr,pas;
	usr=null;
	pass = null;
	try {
		Class.forName("com.mysql.cj.jdbc.Driver");
		Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/chat","chat","chatter");
		Statement stat = con.createStatement();
		ResultSet res = stat.executeQuery("select * from user where username='"+user+"' and password='"+"'");
		while(res.next()) {
			usr=res.getString(1);
			pass=res.getString(2);
		}
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	String com = usr+"0"+pass;
	return com;
}
}