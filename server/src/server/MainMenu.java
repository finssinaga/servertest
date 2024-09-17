package server;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.HeadlessException;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;

public class MainMenu extends JFrame {

	private JPanel contentPane;
	private socketthread tt;
	private boolean stop = false;

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
					if(!stop){
					tt.start();
					JOptionPane.showMessageDialog(null, "connected");
					stop=true;
					}continue;
				}
				
			}
		});
		ss.start();
		Thread msg = new Thread(new Runnable() {
			public void run() {
				while (!stop){
					if (tt.getmsg()!=null){
						JOptionPane.showMessageDialog(null, tt.getmsg());
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
