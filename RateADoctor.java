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


public class RateADoctor extends JPanel{
	private JButton submit;
	private JComboBox<String> select;
	private JComboBox<Integer> rating;
	private String username;
	private JFrame frame;
	private ArrayList<String> docs;
	//Connection con;
	public RateADoctor(JFrame frame, String username){
		this.frame = frame;
		this.username = username;
		docs = new ArrayList<String>();
		GridBagConstraints gcb = new GridBagConstraints();
		setLayout(new GridBagLayout());
		JLabel txt = new JLabel("Rate A Doctor");
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
		gcb.gridy = 2;
		gcb.weighty = 10;
		add(pan, gcb);
		pan = new JPanel();
		txt = new JLabel("Select Doctors:          ");
		pan.add(txt);
		select = new JComboBox<String>();
		selector();
		pan.add(select);
		gcb.gridy = 3;
		gcb.weighty = 1;
		add(pan, gcb);
		pan = new JPanel();
		txt = new JLabel("Rating:          ");
		pan.add(txt);
		rating = new JComboBox<Integer>();
		rating.addItem(0);
		rating.addItem(1);
		rating.addItem(2);
		rating.addItem(3);
		rating.addItem(4);
		rating.addItem(5);
		pan.add(rating);
		gcb.gridy = 4;
		gcb.weighty = 1;
		add(pan, gcb);
		pan = new JPanel();
		pan.setLayout(new GridBagLayout());
		GridBagConstraints gcb2 = new GridBagConstraints();
		gcb2.gridy = 0;
		gcb2.gridx = 0;
		gcb2.weighty = 1;
		gcb2.weightx = 1;
		submit = new JButton("Submit Rating");
		JPanel buttons = new JPanel();
		buttons.add(submit);
		pan.add(buttons, gcb2);
		gcb.gridy = 5;
		gcb.weighty = 10;
		add(pan, gcb);
		submit.addActionListener(new SubmitListener());
	}
	private class SubmitListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent v) {
			if (docs.size()==0){
				frame.getContentPane().removeAll();
				PatientHome nur = new PatientHome(frame, username);
				frame.add(nur);
				frame.repaint();
				frame.setVisible(true);
				return;
			}
			Connection con = null;
			Statement stmt;
			String dname = "\"";
			dname = dname+  docs.get(select.getSelectedIndex());
			dname = dname + "\"";
			String rate = rating.getSelectedIndex() + "";
			try {
				String query = "INSERT INTO rates(UsernameOfDoctor, UsernameOfPatient, Rating)" + 
				"VALUES (" + dname + "," +username +"," + rate + ")" + 
				"ON DUPLICATE KEY UPDATE Rating = " + rate + ";";
				con = DriverManager.getConnection("jdbc:mysql://academic-mysql.cc.gatech.edu/cs4400_Group_34", "cs4400_Group_34","9BkPSv5Z");
				stmt = con.createStatement();
				stmt.executeUpdate(query);
				frame.getContentPane().removeAll();
				PatientHome nur = new PatientHome(frame, username);
				frame.add(nur);
				frame.repaint();
				frame.setVisible(true);
			} catch (Exception e) {
				System.err.println("Exception :" + e.getMessage());
			} finally{
				try{	
					if(con!=null)
						con.close();
				} catch (SQLException e1) {}
			}
		}
	}
	public void selector(){
		// add doctors
		Connection con = null;
		Statement stmt;
		try {
			String query = "SELECT * From Visit LEFT JOIN Doctor " + "" +
					"ON Visit.UsernameOfDoctor = Doctor.Username WHERE UsernameOfPatient = " + username + ";";
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			con = DriverManager.getConnection("jdbc:mysql://academic-mysql.cc.gatech.edu/cs4400_Group_34", "cs4400_Group_34","9BkPSv5Z");
			stmt = con.createStatement();
			ResultSet info = stmt.executeQuery(query);
			while(info.next()){
				String tmp = info.getString("FirstName") + " " + info.getString("LastName");
				select.addItem(tmp);
				docs.add(info.getString("UsernameOfDoctor"));
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
}
