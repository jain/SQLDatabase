import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.swing.*;


public class PatientHistory extends JPanel{
	// for messages just show in dialog box, too hard to show else
	private JButton back;
	private JFrame frame;
	private String username;
	private JPanel history;
	//private Map<Map<String, String>, JComboBox> status;
	public PatientHistory(JFrame frame, String username) {
		this.username = username;
		this.frame = frame; 
		GridBagConstraints gcb = new GridBagConstraints();
		setLayout(new GridBagLayout());
		JLabel txt = new JLabel("Patient History");
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
		history = new JPanel();
		generate();
		gcb.gridy = 1;
		gcb.weighty = 18;
		int rows = entries() + 1;
		history.setLayout(new GridLayout(rows,4));
		add(history, gcb);
		JPanel pan = new JPanel();
		pan.setLayout(new GridBagLayout());
		GridBagConstraints gcb2 = new GridBagConstraints();
		gcb2.gridy = 0;
		gcb2.gridx = 0;
		gcb2.weighty = 1;
		gcb2.weightx = 1;
		back = new JButton("Back");
		JPanel buttons = new JPanel();
		buttons.add(back);
		pan.add(buttons, gcb2);
		gcb.gridy = 4;
		gcb.weighty = 4;
		add(pan, gcb);
		back.addActionListener(new BackListener());
	}
	public void generate(){
		JLabel txt = new JLabel("Doctor");
		txt.setBorder(BorderFactory.createLineBorder(Color.black));
		txt.setHorizontalAlignment(SwingConstants.CENTER);
		history.add(txt);
		txt = new JLabel("Date");
		txt.setBorder(BorderFactory.createLineBorder(Color.black));
		txt.setHorizontalAlignment(SwingConstants.CENTER);
		history.add(txt);
		txt = new JLabel("Billing Amount");
		txt.setBorder(BorderFactory.createLineBorder(Color.black));
		txt.setHorizontalAlignment(SwingConstants.CENTER);
		history.add(txt);
		txt = new JLabel("Diastolic");
		txt.setBorder(BorderFactory.createLineBorder(Color.black));
		txt.setHorizontalAlignment(SwingConstants.CENTER);
		history.add(txt);
		txt = new JLabel("Systolic");
		txt.setBorder(BorderFactory.createLineBorder(Color.black));
		txt.setHorizontalAlignment(SwingConstants.CENTER);
		history.add(txt);
		Connection con = null;
		Statement stmt;
		Statement stmt2;
		try {
			String query = "SELECT * FROM Visit " + 
					"WHERE UsernameOfPatient = " + username + 
					" ORDER BY dateOfVisit;";
			con = DriverManager.getConnection("jdbc:mysql://academic-mysql.cc.gatech.edu/cs4400_Group_34", "cs4400_Group_34","9BkPSv5Z");
			stmt = con.createStatement();
			ResultSet info = stmt.executeQuery(query);
			while(info.next()){
				query = "Select FirstName, LastName FROM Doctor"
						+ " WHERE Username = \"" + info.getString("UsernameOfDoctor") + "\";";
				con = DriverManager.getConnection("jdbc:mysql://academic-mysql.cc.gatech.edu/cs4400_Group_34", "cs4400_Group_34","9BkPSv5Z");
				stmt2 = con.createStatement();
				ResultSet temp = stmt2.executeQuery(query);
				temp.next();
				txt = new JLabel(temp.getString("FirstName") + " " + temp.getString("LastName"));
				txt.setBorder(BorderFactory.createLineBorder(Color.black));
				txt.setHorizontalAlignment(SwingConstants.CENTER);
				history.add(txt);
				txt = new JLabel(info.getString("dateOfVisit"));
				txt.setBorder(BorderFactory.createLineBorder(Color.black));
				txt.setHorizontalAlignment(SwingConstants.CENTER);
				history.add(txt);
				txt = new JLabel(info.getString("billingAmount"));
				txt.setBorder(BorderFactory.createLineBorder(Color.black));
				txt.setHorizontalAlignment(SwingConstants.CENTER);
				history.add(txt);
				txt = new JLabel(info.getString("diastolic"));
				txt.setBorder(BorderFactory.createLineBorder(Color.black));
				txt.setHorizontalAlignment(SwingConstants.CENTER);
				history.add(txt);
				txt = new JLabel(info.getString("systolic"));
				txt.setBorder(BorderFactory.createLineBorder(Color.black));
				txt.setHorizontalAlignment(SwingConstants.CENTER);
				history.add(txt);
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
	public int entries(){
		Connection con = null;
		Statement stmt;
		try {
			String query = "SELECT Count(dateOfVisit) AS Count FROM Visit " +
					"WHERE UsernameOfPatient = " + username + ";";
			con = DriverManager.getConnection("jdbc:mysql://academic-mysql.cc.gatech.edu/cs4400_Group_34", "cs4400_Group_34","9BkPSv5Z");
			stmt = con.createStatement();
			ResultSet info = stmt.executeQuery(query);
			info.next();
			return (Integer.parseInt(info.getString("Count")));
		} catch (Exception e) {
			System.err.println("Exception :" + e.getMessage());
		} finally{
			try{	
				if(con!=null)
					con.close();
			} catch (SQLException e1) {}
		}
		return -1;
	}
	private class BackListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent v) {
			frame.getContentPane().removeAll();
			PatientHome nur = new PatientHome(frame, username);
			frame.add(nur);
			frame.repaint();
			frame.setVisible(true);
		}
	}
}
