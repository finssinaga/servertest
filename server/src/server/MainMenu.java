package server;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.Certificate;
import java.security.KeyException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.Principal;
import java.security.PublicKey;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextField;
import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.TrustManager;
import javax.swing.JButton;

public class MainMenu extends JFrame {

	private JPanel contentPane;
	private ServerSocket server;
	private Socket sock;
	private SSLSocket ssock;
	private Thread ss;
	private JTextArea edit;
	private JScrollPane scrollPane;
	public static Map<Runnable, Socket> client = new HashMap<Runnable, Socket>();
	
	public static JTextArea ar;
	private JSplitPane splitPane;
	private JTextField pesan;
	private JButton Send;
	private static Runnable th;
	
	public static synchronized JTextArea getjtext() {
		return ar;
	}
	public static void setjtext(JTextArea jt) {
		ar=jt;
	};
	public static synchronized Runnable getth() {
		return th;
	}

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainMenu frame = new MainMenu();
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
	public MainMenu() {
		ss = new Thread(new Runnable() {
			public void run() {
				while(ss.isAlive()) {
				server = ser();
				while(true){
					try {
						sock = server.accept();
						getjtext().setText(MainMenu.getjtext().getText()+"\n"+sock.getInetAddress().toString());
						Pipe so = new Pipe(sock);
						new Thread(so).start();
						client.put(so, sock);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
			}
		});
		ss.start();
		
				
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		new Thread(new Runnable() {
			public void run() {
				while(true) {
					try {
						System.out.println(client.keySet());
						Thread.sleep(500);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		});
		Send = new JButton("Send");
		Send.setBounds(0,0,WIDTH, HEIGHT);
		splitPane = new JSplitPane();
		splitPane.setResizeWeight(1);
		contentPane.add(splitPane, BorderLayout.SOUTH);
		
		pesan = new JTextField();
		splitPane.setLeftComponent(pesan);
		pesan.setColumns(10);
		
		
		splitPane.setRightComponent(Send);
		
		scrollPane = new JScrollPane();
		contentPane.add(scrollPane, BorderLayout.CENTER);
		edit = new JTextArea();
		scrollPane.setViewportView(edit);
		ar = edit;
		
		Send.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				for (Runnable socket : client.keySet()) {
					try {
						new PrintWriter(client.get(socket).getOutputStream(),true).println("[SERVER] "+pesan.getText().toString());
						getjtext().setText(getjtext().getText()+"\n"+"[SERVER] "+pesan.getText());
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				pesan.setText("");
			}
		});
	}
	public static ServerSocket sv() {
		SSLServerSocketFactory svx = (SSLServerSocketFactory) SSLServerSocketFactory.getDefault();
		ServerSocket ssx = null;
		try {
			ssx = svx.createServerSocket(5000);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			JOptionPane.showMessageDialog(null, e);
		}
		return ssx;
	}
	public static ServerSocket ser() {
		ServerSocket svc = null;
		try {
			svc = new ServerSocket(5000);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			JOptionPane.showMessageDialog(null, e);
		}
		return svc;
	}

}
