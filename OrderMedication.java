import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.swing.*;


public class OrderMedication extends JPanel{
	private JButton submit;
	private JTextField medicineName;
	private JTextField consultingDoctor;
	private JComboBox<String> dosage;
	private JComboBox<String> month;
	private JComboBox<String> day;
	private JTextField date;
	private JFrame frame;
	private String username;
	private JButton add;
	private JTextField notes;
	private String dateOfVisit;
	private String docUname;
	public OrderMedication(JFrame frame, String username){
		this.frame = frame;
		this.username = username;
		GridBagConstraints gcb = new GridBagConstraints();
		setLayout(new GridBagLayout());
		JLabel txt = new JLabel("Order Medication From Pharmacy");
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
		txt = new JLabel("Medicine Name:");
		pan.add(txt);
		medicineName = new JTextField("", 20);
		pan.add(medicineName);
		gcb.gridy = 2;
		gcb.gridx = 0;
		add(pan, gcb);
		pan = new JPanel();
		txt = new JLabel("Dosage: ");
		pan.add(txt);
		dosage = new JComboBox<String>();
		for (int i = 0; i < 100; i++) {
			dosage.addItem("" + (i));
		}
		pan.add(dosage);
		txt = new JLabel("per day");
		pan.add(txt);
		add(pan, gcb);
		gcb.gridy = 3;
		gcb.weighty = 4.4;
		add(pan, gcb);
		pan = new JPanel();
		txt = new JLabel("Duration: ");
		pan.add(txt);
		month = new JComboBox<String>();
		for (int i = 0; i < 12; i++) {
			month.addItem("" + (i));
		}
		pan.add(month);
		txt = new JLabel(" months");
		pan.add(txt);
		day = new JComboBox<String>();
		for (int i = 0; i < 31; i++) {
			day.addItem("" + (i));
		}
		pan.add(day);
		txt = new JLabel("days");
		pan.add(txt);
		add(pan, gcb);
		gcb.gridy = 4;
		gcb.weighty = 4.4;
		add(pan, gcb);
		pan = new JPanel();
		txt = new JLabel("Consulting Doctor: ");
		pan.add(txt);
		consultingDoctor = new JTextField("", 20);
		pan.add(consultingDoctor);
		add(pan, gcb);
		gcb.gridy = 5;
		gcb.weighty = 4.4;
		add(pan, gcb);
		pan = new JPanel();
		txt = new JLabel("Notes: ");
		pan.add(txt);
		notes = new JTextField(20);
		pan.add(notes);
		gcb.gridy = 6;
		add(pan, gcb);
		pan = new JPanel();
		pan.add(new JLabel("Date of Visit YYYY-MM-DD: "));
		date = new JTextField(10);
		pan.add(date);
		gcb.gridy = 7;
		add(pan, gcb);
		JPanel tmp = new JPanel();
		submit = new JButton("Checkout");
		submit.addActionListener(new CheckoutListener());
		tmp.add(submit);
		add = new JButton("Add Prescription");
		add.addActionListener(new SubmitListener());
		tmp.add(add);
		gcb.gridy = 8;
		add(tmp, gcb);
	}
	private class CheckoutListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			frame.getContentPane().removeAll();
			PaymentInformation nur = new PaymentInformation(frame, username);
			frame.add(nur);
			frame.repaint();
			frame.setVisible(true);
		}
	}
	private class SubmitListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			if(!check()){
				return;
			}
			Statement stmt;
			Connection con = null;
			String medicine = "\"";
			medicine = medicine+ medicineName.getText().toString().trim();
			medicine = medicine + "\"";
			int dos = dosage.getSelectedIndex();
			String duration = "\"";
			duration = duration+ month.getSelectedIndex() + " months and " +day.getSelectedIndex() + " days";
			duration = duration + "\"";
			String note = "\"";
			note = note + notes.getText().toString().trim();
			note = note+ "\"";
			String dateOfVisit2 = "'";
			dateOfVisit2 = dateOfVisit2 + dateOfVisit;
			dateOfVisit2 = dateOfVisit2+ "'";
			String doc = "\"";
			doc = doc + docUname;
			doc = doc+ "\"";
			try{
				Class.forName("com.mysql.jdbc.Driver").newInstance();
				con = DriverManager.getConnection("jdbc:mysql://academic-mysql.cc.gatech.edu/cs4400_Group_34", "cs4400_Group_34","9BkPSv5Z");
				stmt = con.createStatement();
				String query = "INSERT INTO Order_Medication (UsernameOfPatient, UsernameOfDoctor, MedicineName, dateOfVisit, Dosage, Duration, Notes) " +
						"VALUES (" + username +", " +doc + ", "+ medicine + ", "+ dateOfVisit2 + ", "+dos+" , "+duration+ ", " + note+");";
				stmt.executeUpdate(query);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				System.err.println("Exception :" + e1.getMessage());
			} finally{
				try{	
					if(con!=null)
						con.close();
				} catch (SQLException e1) {}
			}
		}
	}
	public boolean check(){
		Statement stmt;
		Connection con = null;
		String type ="";
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			con = DriverManager.getConnection("jdbc:mysql://academic-mysql.cc.gatech.edu/cs4400_Group_34", "cs4400_Group_34","9BkPSv5Z");
			stmt = con.createStatement();
			String query = "SELECT * FROM Results_In LEFT OUTER JOIN Doctor ON Doctor.Username = UsernameOfDoctor " +
					"WHERE UsernameOfPatient =  " + username + ";";
			ResultSet info = stmt.executeQuery(query);
			info.next();
			String medicine = medicineName.getText().toString();
			String dat = date.getText().toString().trim();
			String[] name = consultingDoctor.getText().toString().trim().split(" ");
			String firstName = name[0];
			String lastName = name[1];
			boolean poss = info.getString("FirstName").trim().equals(firstName);
			poss = poss&&( info.getString("LastName").trim().equals(lastName));
			poss = poss&&( info.getString("dateOfVisit").substring(0, 10).equals(dat));
			poss = poss &&(info.getString("MedicineName").equals(medicine));
			while(!poss){
				info.next();
				poss = info.getString("FirstName").trim().equals(firstName);
				poss = poss&&( info.getString("LastName").trim().equals(lastName));
				poss = poss&&( info.getString("dateOfVisit").substring(0, 10).equals(dat));
				poss = poss &&(info.getString("MedicineName").equals(medicine));
			}
			docUname = info.getString("Username");
			dateOfVisit = info.getString("dateOfVisit");
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			JOptionPane.showMessageDialog(frame, "Your Medication Does not exist in our records");
			return false;
		} finally{
			try{	
				if(con!=null)
					con.close();
			} catch (SQLException e1) {}
		}
		return true;
	}
}
