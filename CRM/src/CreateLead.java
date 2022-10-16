import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import javax.swing.JScrollPane;

public class CreateLead extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;

	private static ImageIcon logo;
	public static JTextField prospect;
	public static JTextField car;
	public static JTextArea notes;
	public static Font fieldFont;
	public static Font labelFont;
	public static JComboBox statusDropdown;
	
	private static JPanel panel;
	private static JScrollPane noteScroll;
	private static JLabel nameLabel; 
	private static JLabel carLabel;
	private static JLabel noteLabel;
	private static JButton submitLead;
	
	CreateLead() {
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setBounds(500, 500, 1000, 500);
		this.setResizable(false);
		this.setTitle(Home.windowTitle+ " - Lead Creation Form");
		
		panel = new JPanel();
		panel.setLayout(null);
		this.add(panel);
		
		this.setIconImage(Home.logo.getImage());
		
		fieldFont = new Font("Arial", Font.PLAIN, 15);
		labelFont = new Font("Arial", Font.PLAIN, 13);
		
		nameLabel = new JLabel("Prospect Name");
		nameLabel.setBounds(100, 35, 100, 50);
		nameLabel.setFont(labelFont);
		panel.add(nameLabel);
		
		prospect = new JTextField(40);
		prospect.setBounds(200, 50, 300, 25);
		prospect.setFont(fieldFont);
		panel.add(prospect);
		
		carLabel = new JLabel("Year/Make/Model");
		carLabel.setBounds(87, 87, 300, 50);
		carLabel.setFont(labelFont);
		panel.add(carLabel);
		
		car = new JTextField(40);
		car.setBounds(200, 100, 300, 25);
		car.setFont(fieldFont);
		panel.add(car);
		
		noteLabel = new JLabel("Notes");
		noteLabel.setBounds(153, 135, 300, 50);
		noteLabel.setFont(labelFont);
		panel.add(noteLabel);
		
		notes = new JTextArea();
		notes.setFont(fieldFont);
		notes.setLineWrap(true);
		notes.setWrapStyleWord(true);
		panel.add(notes);
		
		noteScroll = new JScrollPane(notes, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		noteScroll.setBounds(200, 150, 500, 250);
		panel.add(noteScroll);
		
		String[] statusChoices = {"Cold Call", "Follow Up Call", "Appointment Set", "Sold", "Closed"};		
		statusDropdown = new JComboBox(statusChoices);
		statusDropdown.setBounds(525, 52, 200, 25);
		panel.add(statusDropdown);
		
		submitLead = new JButton("Create Lead");
		submitLead.setFont(labelFont);
		submitLead.setBounds(720, 370, 150, 25);
		submitLead.addActionListener(this);
		panel.add(submitLead);
		
		this.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == submitLead) {
			System.out.println("User " + Login.getUser + " entered a new lead");
			System.out.println("Customer: " + prospect.getText());
			System.out.println("Car: " + car.getText());
			System.out.println("Status: " + statusDropdown.getSelectedItem());
			System.out.println("Notes: " + notes.getText());
			
			String selectedStatus = String.valueOf(statusDropdown.getSelectedItem());
			
			try {
				String sql = "INSERT INTO created_leads VALUES (?, ?, ?, ?, ?);";
				
				Connection db_connection = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/crm_reldb", "root", "Data_Root31");
				PreparedStatement enterData = db_connection.prepareStatement(sql);
				
				enterData.setString(1, Login.getUser);
				enterData.setString(2, prospect.getText());
				enterData.setString(3, car.getText());
				enterData.setString(4, selectedStatus);
				enterData.setString(5, notes.getText());
				enterData.executeUpdate(); // only really important
				
				db_connection.close();
				System.out.println("SQL: Lead created and sent to database");
				System.out.println("");
			}
			catch (SQLException sqlException) {
				System.out.println("SQL: Cannot send to database");
				sqlException.printStackTrace();
			}
			
			this.dispose();
		}
	}
	
}