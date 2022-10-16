import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class Home extends JFrame implements ActionListener {
	
	private static final long serialVersionUID = 1L;
	
	// change all window settings here
	private static int windowWidth = 800;
	private static int windowHeight = 500;
	private static String loggedUser = Login.getUser;
	public static String windowTitle = "MyCRM - PreAlpha v0.0.0.2 - @"  + loggedUser;
	public static ImageIcon logo;
	
	// initialize MenuBar
	private static JMenuBar menubar;
	private static JMenu fileMenu;
	private static JMenu viewMenu;
	private static JMenuItem makeLead;
	private static JMenuItem openLeads;
	private static JMenuItem closedLeads;
	private static JMenuItem fileExit;
	
	Home() {
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setBounds(500, 500, windowWidth, windowHeight);
		this.setTitle(windowTitle);
		
		logo = new ImageIcon("promoting.png");
		this.setIconImage(logo.getImage());
		
		menubar = new JMenuBar();
		
		fileMenu = new JMenu("File");
		viewMenu = new JMenu("View");
		menubar.add(fileMenu);
		menubar.add(viewMenu);
		
		makeLead = new JMenuItem("Create Lead");
		openLeads = new JMenuItem("Open Leads");
		closedLeads = new JMenuItem("Closed Leads");
		fileExit = new JMenuItem("Exit");
		
		makeLead.addActionListener(this);
		openLeads.addActionListener(this);
		closedLeads.addActionListener(this);
		fileExit.addActionListener(this);
		
		fileMenu.add(makeLead); 
		fileMenu.add(fileExit);
		viewMenu.add(openLeads);
		viewMenu.add(closedLeads);
		
		this.setJMenuBar(menubar);
		this.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == makeLead) {
			new CreateLead();
			System.out.println(loggedUser + ": opened lead creation form");
		}
		else if (e.getSource() == openLeads) {
			new OpenUI();
			System.out.println(loggedUser + ": viewing 'opened leads'");
		}
		else if (e.getSource() == closedLeads) {
			System.out.println(loggedUser + ": viewing 'closed leads'");
		}
		else if (e.getSource() == fileExit) {
			System.out.println(loggedUser + ": closed program (menubar)");
			System.exit(0);
		}
	}
}
