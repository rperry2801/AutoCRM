import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.ImageIcon;
import javax.swing.JButton;

public class Login implements ActionListener {
	
	private static JFrame window;
	private static ImageIcon icon;
	private static JPanel panel;
	private static JLabel userLabel;
	private static JLabel passLabel;
	private static JTextField userText;
	private static JPasswordField passText;
	private static JButton button;
	private static JLabel formOutput;
	
	public static String getUser;
	
	public static void main(String[] args) {
		window = new JFrame();
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setBounds(500, 500, 400, 400); // x, y, width, height
		window.setResizable(false);
		window.setTitle("MyCRM - Login");
		
		panel = new JPanel();
		panel.setLayout(null);
		window.add(panel);
		
		userLabel = new JLabel("Username");
		userLabel.setBounds(10, 20, 80, 25);
		panel.add(userLabel);
		
		passLabel = new JLabel("Password");
		passLabel.setBounds(10, 60, 80, 25);
		panel.add(passLabel);
		
		userText = new JTextField();
		userText.setBounds(100, 20, 165, 25);
		panel.add(userText);
		
		passText = new JPasswordField();
		passText.setBounds(100, 60, 165, 25);
		panel.add(passText);
		
		button = new JButton("Login");
		button.setBounds(100, 100, 100, 25);
		button.addActionListener(new Login());
		panel.add(button);
		
		formOutput = new JLabel("Log in with your username and password");
		formOutput.setBounds(10, 150, 400, 25);
		panel.add(formOutput);
		
		icon = new ImageIcon("server.png");
		window.setIconImage(icon.getImage());
		
		window.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) { // code that runs when login button is pressed
		
		getUser = userText.getText();
		
		String userName = getUser;
		String password = passText.getText();
		
		System.out.println("A login attempt was made");
		System.out.println("Username: " + userName);
		System.out.println("Password: " + password);
		System.out.println("Connecting to database...");
		
		formOutput.setText("Connecting to Database");
		
		try {
			Connection db_connection = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/crm_reldb", "root", "Data_Root31");
			
			PreparedStatement st = (PreparedStatement) db_connection.prepareStatement("Select name, password from users where name=? and password=?");
			st.setString(1, userName);
			st.setString(2, password);
			ResultSet rs = st.executeQuery();
			if (rs.next()) {
				formOutput.setText("Authentication Successful");
				System.out.println("Login attempt WAS successful");
				new Home();
				window.dispose();
				db_connection.close();
			}
			else {
				formOutput.setText("Username or Password is Incorrect");
				System.out.println("Login attempt was NOT successfull");
			}
			System.out.println("");
		} 
		catch (SQLException sqlException) {
			formOutput.setText("Cannot connect to database");
			System.out.println("SQL: Cannot connect to database");
			sqlException.printStackTrace();
		}
	}
	
}
