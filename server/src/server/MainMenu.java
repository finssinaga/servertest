package server;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.HeadlessException;
import java.awt.JobAttributes;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.sun.xml.internal.messaging.saaj.util.ByteInputStream;

import sun.misc.IOUtils;
import sun.nio.ch.IOUtil;

import javax.swing.JLabel;
import javax.swing.JTextArea;

public class MainMenu extends JFrame {

	private JPanel contentPane;
	private socketthread tt;
	private boolean stop;
	private ServerSocket server;
	private Socket sock;
	private JLabel label;
	private InputStream input;
	private DataInputStream data;
	private boolean msgreceive;
	private Thread ss;
	private int dataread;
	private Thread mm;
	private JTextArea edit;

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
	public MainMenu(socketthread st) {
		this.tt=st;
		edit = new JTextArea();
		setstop(true);
		//msg().start();
		ss = new Thread(new Runnable() {
			public void run() {
				while(ss.isAlive()) {
				server = tt.svc();
				while(getstop()){
					try {
						sock = server.accept();
						System.out.println("connected");
						setstop(false);
						msgreceive(true);
						setint(sock.getInputStream().read());
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				while(!getstop()) {
					try {
						if(getint()<0) {
							setint(sock.getInputStream().read());
							System.out.println("Disconnect");
							setstop(true);
							msgreceive(false);
							continue;
						}
						Thread.sleep(500);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
					try {
						input = sock.getInputStream();
						data = new DataInputStream(input);
						System.out.println(data.read());
						int m = data.read();
						label.setText(String.valueOf(m));
						Thread.sleep(500);
					} catch (Exception e) {
						e.printStackTrace();
					}
			}
			}
		});
		ss.start();
		msg().start();
				
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		label = new JLabel();
		Thread chek = new Thread(new Runnable() {
			
			@Override
			public void run() {
				while(true) {
					try {
						System.out.print(getstop());
						System.out.println(getint());
						Thread.sleep(500);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				
			}
		});
		//chek.start();
		contentPane.add(label, BorderLayout.NORTH);
		contentPane.add(edit, BorderLayout.SOUTH);
		
	}
public synchronized boolean getstop(){
		return this.stop;
	};
public synchronized boolean setstop(boolean s) {
	this.stop=s;
	return this.stop;
}

/*public synchronized Thread msgforward() {
	this.ii = new Thread(new Runnable() {
		
		@Override
		public void run() {
			while(ii.isAlive()) {
				try {
					while(sock.getInputStream().read()>0) {
						try {
							InputStream input = sock.getInputStream();
							DataInputStream data = new DataInputStream(input);
							String message = data.readUTF();
							
							OutputStream out = sock.getOutputStream();
							DataOutputStream dout = new DataOutputStream(out);
							dout.writeUTF(message);
							dout.flush();
							dout.close();
							data.close();
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		}
	});
	return this.ii;
}*/
public synchronized int setint(int i) {
	this.dataread=i;
	return this.dataread;
}
public synchronized int getint() {
	return this.dataread;
}

public synchronized boolean msgreceive(boolean msg) {
	this.msgreceive=msg;
	return this.msgreceive;
}
public synchronized boolean getmsgrec() {
	return this.msgreceive;
}
public synchronized Thread msg() {
	mm = new Thread(new Runnable() {
		@Override
		public void run() {
			while(mm.isAlive()) {
				try {
					while(sock.getInputStream().available()>0) {
					InputStream input = sock.getInputStream();
					DataInputStream data = new DataInputStream(input);
					System.out.println(data.read());
					int m = data.read();
					label.setText(String.valueOf(m));
					edit.setText(String.valueOf(m));
					//Thread.sleep(500);
			}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	});
	return mm;
}
	
	/*BufferedInputStream in = new BufferedInputStream(sock.getInputStream());
	byte[] contents = new byte[1024];
	int bytesRead = 0;
	String strFileContents = ""; 
	bytesRead = in.read(contents);
	strFileContents += new String(contents, 0, bytesRead);              
	
	if (strFileContents!=null) {
	System.out.println(strFileContents);*/
}
