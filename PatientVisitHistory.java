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



public class PatientVisitHistory extends JPanel{
	// for messages just show in dialog box, too hard to show else
	private JFrame frame;
	private String username;
	private JComboBox<String> buttons;
	private JTextField diagnosis;
	private String myTime;
	private JTextField doctor;
	private JTextField systolic;
	private JTextField diastolic;
	private JPanel temp;
	private JPanel panel;
	private JLabel txt;
	private JPanel special;
	private GridBagConstraints gcb;
	private GridBagConstraints gcb2;
	private GridBagConstraints gcb3;
	private GridBagConstraints gcb4;
	//private Map<Map<String, String>, JComboBox> status;
	public PatientVisitHistory(JFrame frame, String username) {
		this.username = username;
		this.frame = frame; 
		gcb = new GridBagConstraints();
		setLayout(new GridBagLayout());
		txt = new JLabel("Patient Visit History");
		panel = new JPanel();
		Font myFont = new Font("Serif", Font.BOLD, 50);
		txt.setFont(myFont);
		panel.setBackground(Color.yellow);
		txt.setHorizontalAlignment(SwingConstants.CENTER);
		panel.setLayout(new BorderLayout());
		panel.add(txt, BorderLayout.CENTER);
		panel.setBorder(BorderFactory.createLineBorder(Color.black));
		gcb.fill = GridBagConstraints.BOTH;
		gcb.gridwidth = 2;
		gcb.gridy = 0;
		gcb.gridx = 0;
		gcb.weighty = 2;
		gcb.weightx = 1;
		add(panel, gcb);

		gcb.gridwidth = 1;
		gcb.gridy = 1;
		gcb.weighty = 10;
		gcb.weightx = 2;
		panel = new JPanel();
		panel.setLayout(new GridBagLayout());
		gcb2 = new GridBagConstraints();
		txt = new JLabel("Select a Date");
		buttons = new JComboBox<String>();
		generate();
		gcb2.gridy = 0;
		gcb2.weighty = 1;
		panel.add(txt, gcb2);
		gcb2.gridy = 1;
		panel.add(buttons, gcb2);
		gcb2.gridy = 2;
		JButton back = new JButton("Back");
		back.addActionListener(new BackListener());
		panel.add(back, gcb2);
		add(panel, gcb);
		panel = new JPanel();
		panel.setLayout(new GridBagLayout());
		gcb3 = new GridBagConstraints();
		gcb3.weighty = 1;
		gcb3.gridy = 0;
		temp = new JPanel();
		txt = new JLabel("Consulting Doctor: ");
		temp.add(txt);
		doctor = new JTextField("", 10);
		temp.add(doctor);
		panel.add(temp, gcb3);

		gcb3.gridy = 1;
		temp = new JPanel();
		txt = new JLabel("Blood Pressure: ");
		temp.add(txt);
		txt = new JLabel("Systolic: ");
		temp.add(txt);
		systolic = new JTextField("", 5);
		temp.add(systolic);
		txt = new JLabel("Diastolic: ");
		temp.add(txt);
		diastolic = new JTextField("", 5);
		temp.add(diastolic);
		panel.add(temp, gcb3);

		gcb3.gridy = 2;
		temp = new JPanel();
		txt = new JLabel("Diagnosis");
		temp.add(txt);
		diagnosis = new JTextField("", 10);
		temp.add(diagnosis);
		panel.add(temp, gcb3);

		gcb3.gridy = 3;
		temp = new JPanel();
		txt = new JLabel("Medications: ");
		temp.add(txt, gcb4);
		panel.add(temp, gcb3);
		gcb3.gridy=4;
		gcb3.weighty = 5;
		special = new JPanel();
		gcb3.fill = GridBagConstraints.BOTH;
		panel.add(special, gcb3);
		gcb.weightx = 5;
		gcb.gridx = 1;
		add(panel, gcb);
		buttons.addActionListener(new ComboListener());
		if(buttons.getItemCount()>0){
			buttons.setSelectedIndex(0);
		}
	}

	public void generate() {
		Statement stmt = null;
		Connection con = null;
		try {
			String query = "SELECT dateOfVisit FROM Visit " + 
					"WHERE UsernameOfPatient = " + username + 
					" ORDER BY dateOfVisit;";
			con = DriverManager.getConnection("jdbc:mysql://academic-mysql.cc.gatech.edu/cs4400_Group_34", "cs4400_Group_34", "9BkPSv5Z");
			stmt = con.createStatement();
			ResultSet info = stmt.executeQuery(query);

			while(info.next()) {
				//String tmp = info.getString("dateOfVisit");
				buttons.addItem(info.getString("dateOfVisit"));
			}
		} catch (SQLException e) {
			System.err.println("Exception: " + e.getMessage());
		} finally {
			try {	
				if(con != null)
					con.close();
			} catch (SQLException e1) {}
		}
	}

	private class ComboListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent v) {
			Connection con = null;
			Connection con2 = null;
			Connection con3 = null;
			Connection con4 = null;
			Statement stmt;
			Statement stmt2;
			Statement stmt3;
			Statement stmt4;
			ResultSet myResults;
			ResultSet nameResults;
			ResultSet info2;
			ResultSet info4;
			try {
				myTime = buttons.getItemAt(buttons.getSelectedIndex());
				String query = "Select * from Visit "//NATURAL JOIN Visit_Diagnosis "
						+ "Where dateOfVisit = '" + myTime + "' AND UsernameOfPatient = " + username+";";
				con = DriverManager.getConnection("jdbc:mysql://academic-mysql.cc.gatech.edu/cs4400_Group_34", "cs4400_Group_34","9BkPSv5Z");
				stmt = con.createStatement();
				myResults = stmt.executeQuery(query);
				String doctor1 = null;
				while (myResults.next()) {
					System.out.println("myResults");
					doctor1 = myResults.getString("UsernameOfDoctor");
					query = "Select FirstName, LastName FROM Doctor"
							+ " WHERE Username = \"" + doctor1 + "\";";
					con3 = DriverManager.getConnection("jdbc:mysql://academic-mysql.cc.gatech.edu/cs4400_Group_34", "cs4400_Group_34","9BkPSv5Z");
					stmt3 = con.createStatement();
					nameResults = stmt3.executeQuery(query);
					boolean poss = nameResults.next();
					System.out.println("nameResults");
					query = "SELECT Diagnosis From Visit_Diagnosis WHERE DateOfVisit = '" + myTime + "' AND UsernameOfDoctor = \"" + doctor1 +"\" AND " +
							"UsernameOfPatient = " + username + ";";
					con4 = DriverManager.getConnection("jdbc:mysql://academic-mysql.cc.gatech.edu/cs4400_Group_34", "cs4400_Group_34","9BkPSv5Z");
					stmt4 = con4.createStatement();
					info4 = stmt4.executeQuery(query);
					if(poss){
						doctor.setText(nameResults.getString("FirstName") + " " + nameResults.getString("LastName"));
					}
					if(myResults.getString("systolic")==null){
						systolic.setText("");
					}else{
						systolic.setText(myResults.getString("systolic"));
					}
					if(myResults.getString("diastolic")==null){
						diastolic.setText("");
					}else{
						diastolic.setText(myResults.getString("diastolic"));
					}
					if(info4.next()){
						if(info4.getString("diagnosis")==null){
							diagnosis.setText("");
						}
						diagnosis.setText(info4.getString("diagnosis"));
					}else{
						diagnosis.setText("");
					}
				}
				query = "SELECT * From Results_In WHERE UsernameOfPatient = " + username + " AND dateOfVisit = '" + myTime + "' AND UsernameOfDoctor = \"" + doctor1 + "\";";
				con2 = DriverManager.getConnection("jdbc:mysql://academic-mysql.cc.gatech.edu/cs4400_Group_34", "cs4400_Group_34","9BkPSv5Z");
				stmt2 = con2.createStatement();
				info2 = stmt2.executeQuery(query);
				int myInt = 1;
				ArrayList<ArrayList<String>> myArr = new ArrayList<ArrayList<String>>();
				myArr.add(new ArrayList<String>());
				myArr.get(0).add("Medicine Name");
				myArr.get(0).add("Dosage");
				myArr.get(0).add("Duration");
				myArr.get(0).add("Notes");
				System.out.println(myArr.size());
				if(doctor1==null){
					genTable(myArr);
					return;
				}
				while(info2.next()) {
					System.out.println(myArr.size());
					myArr.add(new ArrayList<String>());
					myArr.get(myInt).add(info2.getString("MedicineName"));
					myArr.get(myInt).add(info2.getString("Dosage"));
					myArr.get(myInt).add(info2.getString("Duration"));
					myArr.get(myInt).add(info2.getString("Notes"));
					myInt++;
				}
				genTable(myArr);
				con.close();
				con2.close();
				con3.close();
				con4.close();
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
	public void genTable(ArrayList<ArrayList<String>> list){
		//special.removeAll();
		//special.revalidate();
		//special.repaint();
		special = new JPanel();
		special.setLayout(new GridLayout(list.size(), 4));
		//System.out.println(list.size());
		for(int i = 0; i<list.size(); i++){
			for(int j = 0; j<4; j++){
				String stuff = list.get(i).get(j);
				if(stuff == null){
					stuff = "";
				}
				JLabel lbl = new JLabel(stuff);
				lbl.setBorder(BorderFactory.createLineBorder(Color.black));
				lbl.setHorizontalAlignment(SwingConstants.CENTER);
				special.add(lbl);
			}
		}
			panel.remove(4);
			panel.revalidate();
			gcb3.gridy = 4;
			gcb.weighty = 5;
			panel.add(special, gcb3);
			panel.revalidate();
			panel.repaint();
			repaint();
		//panel.remove(4);
	//	panel.revalidate();

	}
}