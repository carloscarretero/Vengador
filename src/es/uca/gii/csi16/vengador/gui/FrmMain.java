package es.uca.gii.csi16.vengador.gui;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.UIManager;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class FrmMain {
	
	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FrmMain window = new FrmMain();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	/**
	 * Create the application.
	 */
	public FrmMain() throws Exception {
		UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setTitle("Skynet Resistance");
		frame.setBounds(5, 5, 1300, 700);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JMenuBar menuBar = new JMenuBar();
		menuBar.setBorderPainted(false);
		frame.setJMenuBar(menuBar);
		
		JMenu mitNew = new JMenu("Nuevo");
		menuBar.add(mitNew);
		
		JMenuItem mitNewRefugio = new JMenuItem("Refugio");
		mitNewRefugio.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				IfrRefugio ifrRefugio = new IfrRefugio(null);
				ifrRefugio.setBounds(0, 0, 450, 300);
				frame.getContentPane().add(ifrRefugio);
				ifrRefugio.setVisible(true);
			}
		});
		mitNew.add(mitNewRefugio);
		
		JMenu mitSearch = new JMenu("Buscar");
		menuBar.add(mitSearch);
		
		JMenuItem mitSearchRefugio = new JMenuItem("Refugio");
		mitSearchRefugio.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				IfrRefugios ifrRefugios = new IfrRefugios(frame);
				ifrRefugios.setBounds(5, 5, 1200, 600);
				frame.getContentPane().add(ifrRefugios, 0);
				ifrRefugios.setVisible(true);
			}
		});
		mitSearch.add(mitSearchRefugio);
		frame.getContentPane().setLayout(null);
	}
	
	

}
