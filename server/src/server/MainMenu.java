package server;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;

public class MainMenu extends JFrame {

	private JPanel contentPane;
	private socketthread tt;
	private ServerSocket server;
	private Socket sock;
	private JLabel label;
	private Thread ss;
	private JTextArea edit;
	private JScrollPane scrollPane;

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
		ss = new Thread(new Runnable() {
			public void run() {
				while(ss.isAlive()) {
				server = tt.svc();
				while(true){
					try {
						sock = server.accept();
						System.out.println(sock.getInetAddress());
						Pipe so = new Pipe(sock);
						new Thread(so).start();
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
		label = new JLabel();
		new Thread(new Runnable() {
			public void run() {
				while(true) {
					try {
						String string = sock.getInetAddress().toString();
						if(!socketthread.getlist().contains(string)) {
						socketthread.setlist(string);
						continue;}
						System.out.println(sock.getInputStream().read());
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		});
		contentPane.add(label, BorderLayout.NORTH);
		
		scrollPane = new JScrollPane();
		contentPane.add(scrollPane, BorderLayout.CENTER);
		edit = new JTextArea();
		scrollPane.setViewportView(edit);
		
	}

}
