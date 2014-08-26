import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.*;


public class Login extends JPanel{
	private JButton login;
	private JButton createAccount;
	private JTextField usr;
	private JTextField pss;
	private JFrame frame;
	public Login(JFrame frame){
		this.frame = frame; 
		GridBagConstraints gcb = new GridBagConstraints();
		setLayout(new GridBagLayout());
		JLabel txt = new JLabel("Login");
		JPanel panel = new JPanel();
		Font myFont = new Font("Serif", Font.BOLD, 50);
		txt.setFont(myFont);
		panel.setBackground(Color.yellow);
		txt.setHorizontalAlignment(SwingConstants.CENTER);
		panel.setLayout(new BorderLayout());
		panel.add(txt, BorderLayout.CENTER);
		panel.setBorder(BorderFactory.createLineBorder(Color.black));
		gcb.fill = GridBagConstraints.BOTH;
		gcb.gridy = 0;
		gcb.gridx = 0;
		gcb.weighty = 2;
		gcb.weightx = 1;
		add(panel, gcb);
		JPanel pan = new JPanel();
		gcb.gridy = 1;
		gcb.weighty = 10;
		add(pan, gcb);
		pan = new JPanel();
		txt = new JLabel("Username:");
		pan.add(txt);
		usr = new JTextField("", 20);
		pan.add(usr);
		gcb.gridy = 2;
		gcb.weighty = 1;
		add(pan, gcb);
		pan = new JPanel();
		txt = new JLabel("Password:");
		pan.add(txt);
		pss = new JTextField("", 20);
		pan.add(pss);
		gcb.gridy = 3;
		gcb.weighty = 1;
		add(pan, gcb);
		pan = new JPanel();
		pan.setLayout(new GridBagLayout());
		GridBagConstraints gcb2 = new GridBagConstraints();
		gcb2.gridy = 0;
		gcb2.gridx = 0;
		gcb2.weighty = 1;
		gcb2.weightx = 1;
		createAccount = new JButton("Create Account");
		login = new JButton("Login");
		JPanel buttons = new JPanel();
		buttons.add(login);
		buttons.add(new JPanel());
		buttons.add(new JPanel());
		buttons.add(new JPanel());
		buttons.add(new JPanel());
		buttons.add(new JPanel());
		buttons.add(new JPanel());
		buttons.add(new JPanel());
		buttons.add(createAccount);
		pan.add(buttons, gcb2);
		gcb.gridy = 4;
		gcb.weighty = 10;
		add(pan, gcb);
		login.addActionListener(new LoginListener());
		createAccount.addActionListener(new AccountListener());
	}
	private class LoginListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			String uname = "\"";
			uname = uname+ usr.getText().toString();
			uname = uname + "\"";
			String psw = "\"";
			psw = psw + pss.getText().toString();
			psw = psw + "\"";
			if(psw.length()==0||uname.length()==0){
				JOptionPane.showMessageDialog(Login.this,
		            	"PLease Enter a Valid Username and/or Password");
			}
			Statement stmt;
			Connection con = null;
			String type ="";
			try {
				Class.forName("com.mysql.jdbc.Driver").newInstance();
				con = DriverManager.getConnection("jdbc:mysql://academic-mysql.cc.gatech.edu/cs4400_Group_34", "cs4400_Group_34","9BkPSv5Z");
				stmt = con.createStatement();
				String query = "SELECT Type from User WHERE Username = " + uname + " AND Password = " + psw + ";";
				ResultSet info = stmt.executeQuery(query);
				if(info.next()){
					type = info.getString(1);
				}else{
					JOptionPane.showMessageDialog(Login.this,
			            	"PLease Enter a Valid Username and/or Password");
					usr.setText("");
					pss.setText("");
					return;
				}
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				System.err.println("Exception :" + e1.getMessage());
			} finally{
				try{	
					if(con!=null)
						con.close();
				} catch (SQLException e1) {}
			}
			if(type.equals("Doctor")){
				checkDoctorProfile();
			}else if(type.equals("Patient")){
				checkPatientProfile();
			}else{
				frame.getContentPane().removeAll();
				AdministrativeHomepage nur = new AdministrativeHomepage(frame, uname);
				frame.add(nur);
				frame.repaint();
				frame.setVisible(true);
			}
		}
		public void checkDoctorProfile() {
			Connection con = null;
			Statement stmt;
			try {
				String uname = "\"";
				uname = uname+ usr.getText().toString();
				uname = uname + "\"";
				String query = "Select * from Doctor where Username = " + uname + ";";
				Class.forName("com.mysql.jdbc.Driver").newInstance();
				con = DriverManager.getConnection("jdbc:mysql://academic-mysql.cc.gatech.edu/cs4400_Group_34", "cs4400_Group_34","9BkPSv5Z");
				stmt = con.createStatement();
				ResultSet info = stmt.executeQuery(query);
				if (info.next()) {
					goToDoctorHomepage(uname);
					return;
				} else {
					goToDoctorProfile(uname);
				}
			} catch (Exception e) {
				System.err.println("Exception :" + e.getMessage());
			} finally{
				try{	
					if(con!=null)
						con.close();
				} catch (SQLException e1) {}
			}
		}
		public void checkPatientProfile() {
			Connection con = null;
			Statement stmt;
			try {
				String uname = "\"";
				uname = uname+ usr.getText().toString();
				uname = uname + "\"";
				String query = "Select * from Patient where Username = " + uname + ";";
				Class.forName("com.mysql.jdbc.Driver").newInstance();
				con = DriverManager.getConnection("jdbc:mysql://academic-mysql.cc.gatech.edu/cs4400_Group_34", "cs4400_Group_34","9BkPSv5Z");
				stmt = con.createStatement();
				ResultSet info = stmt.executeQuery(query);
				if (info.next()) {
					goToPatientHomepage(uname);
					return;
				} else {
					goToPatientProfile(uname);
				}
			} catch (Exception e) {
				System.err.println("Exception :" + e.getMessage());
			} finally{
				try{	
					if(con!=null)
						con.close();
				} catch (SQLException e1) {}
			}
		}
		public void goToDoctorHomepage(String uname) {
			frame.getContentPane().removeAll();
			DoctorHomepage nur = new DoctorHomepage(frame, uname);
			frame.add(nur);
			frame.repaint();
			frame.setVisible(true);
		}
		public void goToPatientHomepage(String uname) {
			frame.getContentPane().removeAll();
			PatientHome nur = new PatientHome(frame, uname);
			frame.add(nur);
			frame.repaint();
			frame.setVisible(true);
		}
		public void goToPatientProfile(String uname) {
			frame.getContentPane().removeAll();
			PatientProfile nur = new PatientProfile(frame, uname);
			JScrollPane sp = new JScrollPane(nur);
			frame.add(sp);
			frame.repaint();
			frame.setVisible(true);
		}
		public void goToDoctorProfile(String uname) {
			frame.getContentPane().removeAll();
			DoctorProfile nur = new DoctorProfile(frame, uname);
			frame.add(nur);
			frame.repaint();
			frame.setVisible(true);
		}
	}

	private class AccountListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			frame.getContentPane().removeAll();
			NewUserRegistration nur = new NewUserRegistration(frame);
			frame.add(nur);
			frame.repaint();
			frame.setVisible(true);
		}
	}
}
