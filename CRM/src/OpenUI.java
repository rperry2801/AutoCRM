import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JFrame;
import javax.swing.JPanel;

import javax.swing.JLabel;
import javax.swing.JButton;

public class OpenUI extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;
	private static JPanel panel;
	private static JLabel nameLabel;
	private static JLabel carLabel;
	private static JLabel statusLabel;
	private static JButton editButton;
	
	private static int yAxisChange;
	
	OpenUI() {
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setBounds(500, 500, 800,500);
		this.setTitle(Home.windowTitle + " - Open Leads");
		this.setIconImage(Home.logo.getImage());
		
		panel = new JPanel();
		panel.setLayout(null);
		this.add(panel);
		
		pullLeads("SELECT * FROM created_leads WHERE logged_user=? AND lead_status != 'Sold' or 'Closed'");
		
		this.setVisible(true);
	}
	
	private void pullLeads(String sql) {
		try {
			Connection db_connection = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/crm_reldb", "root", "Data_Root31");
			PreparedStatement pullData = db_connection.prepareStatement(sql);
			pullData.setString(1, Login.getUser);
			
			yAxisChange = 50;
			
			ResultSet rs = pullData.executeQuery();
			
			while (rs.next()) {				
				nameLabel = new JLabel(rs.getString("customer_name"));
				nameLabel.setBounds(200, yAxisChange, 80, 25);
				panel.add(nameLabel);
				
				carLabel = new JLabel(rs.getString("car"));
				carLabel.setBounds(300, yAxisChange, 120, 25);
				panel.add(carLabel);
				
				statusLabel = new JLabel(rs.getString("lead_status"));
				statusLabel.setBounds(450, yAxisChange, 120, 25);
				panel.add(statusLabel);
				
				editButton = new JButton("Edit");
				editButton.setBounds(nameLabel.getX()-150, yAxisChange, 80, 25);
				editButton.addActionListener(this);
				panel.add(editButton);
				
				yAxisChange += 50;
			}
			
		}
		catch (SQLException sqlException) {
			System.out.println("SQL: Cannot pull data from database");
			sqlException.printStackTrace();
		}
		
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
	}
	
}
