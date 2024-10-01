package server;

import java.net.ServerSocket;
import javax.net.ssl.SSLServerSocketFactory;

public class SLServer {
	public static ServerSocket svck;
	public static synchronized ServerSocket serv() {
		return svck;
	}
	public static synchronized void setsrv(ServerSocket s) {
		svck = s;
	}
	
	
	public SLServer() throws Exception {
		SSLServerSocketFactory ss = (SSLServerSocketFactory)SSLServerSocketFactory.getDefault();
		ServerSocket server = ss.createServerSocket(5000);
		setsrv(server);
	}
}
