package server;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.HeadlessException;
import java.awt.JobAttributes;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.sun.xml.internal.messaging.saaj.util.ByteInputStream;

import sun.misc.IOUtils;
import sun.nio.ch.IOUtil;

import javax.swing.JLabel;

public class MainMenu extends JFrame {

	private JPanel contentPane;
	private socketthread tt;
	private boolean stop = false;
	private ServerSocket server;
	private Socket sock;
	private Thread msg;
	private InputStream input;
	private DataInputStream data;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainMenu frame = new MainMenu(socketthread.sct());
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 * @throws InterruptedException 
	 * @throws IOException 
	 */
	public MainMenu(socketthread st) throws InterruptedException, IOException {
		this.tt=st;
		String message = null;
		Thread ss = new Thread(new Runnable() {
			public void run() {
				while(!stop){
					server = tt.svc();
					
					try {
						sock = server.accept();
						tt.setSocket(sock);
						sock.setPerformancePreferences(100, 0, 0);
						if(sock.isClosed()==true) {
							JOptionPane.showMessageDialog(null, "disconnected");
						}
						
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				
			}
		});
		ss.start();
		
		msg = new Thread(new Runnable() {
			public void run() {
				while(!stop) {
					try {
						input = sock.getInputStream();
						data = new DataInputStream(input);
						System.out.println(data.readUTF());
						/*BufferedInputStream in = new BufferedInputStream(sock.getInputStream());
						byte[] contents = new byte[1024];
						int bytesRead = 0;
						String strFileContents = ""; 
						bytesRead = in.read(contents);
						strFileContents += new String(contents, 0, bytesRead);              
						
						if (strFileContents!=null) {
						System.out.println(strFileContents);*/
						continue;
						
					} catch (Exception e) {
						// TODO Auto-generated catch block
						
					}finally {
						
					}
					try {
						Thread.sleep(500);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				
			}
		});
		msg.start();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JLabel label = new JLabel();
		label.setText(message);
		contentPane.add(label, BorderLayout.NORTH);
		
	}
	

}
