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
import java.util.ArrayList;

import javax.swing.*;


public class PatientProfile extends JPanel{
	private JButton submit;
	private JButton addAll;
	private JTextField emergencyPhone;
	private JTextField emergencyName;
	private JTextField birth;
	private JTextField name;
	private JTextField addr;
	private JTextField homePhone;
	private JTextField workPhone;
	private JTextField weight;
	private JTextField heightft;
	private JTextField heightin;
	private ArrayList<JTextField> allergies;
	private int counter;
	private JPanel all;
	private JComboBox<String> gender;
	private JComboBox<String> income;
	private JFrame frame;
	private String uname;
	public PatientProfile(JFrame frame, String uname){
		this.uname = uname;
		this.frame = frame;
		GridBagConstraints gcb = new GridBagConstraints();
		setLayout(new GridBagLayout());
		JLabel txt = new JLabel("Patient Profile");
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
		gcb.weighty = 3;
		add(pan, gcb);
		pan = new JPanel();
		txt = new JLabel("Patient Name:");
		pan.add(txt);
		name = new JTextField("", 20);
		pan.add(name);
		gcb.gridy = 3;
		gcb.weighty = 1;
		add(pan, gcb);
		pan = new JPanel();
		txt = new JLabel("Date of Birth YYYY-MM-DD:");
		pan.add(txt);
		birth = new JTextField("", 20);
		pan.add(birth);
		gcb.gridy = 4;
		gcb.weighty = 1;
		add(pan, gcb);
		pan = new JPanel();
		txt = new JLabel("Gender:");
		pan.add(txt);
		gender = new JComboBox<String>();
		gender.addItem("Male");
		gender.addItem("Female");
		pan.add(gender);
		gcb.gridy = 5;
		gcb.weighty = 1;
		add(pan, gcb);
		pan = new JPanel();
		txt = new JLabel("Address:");
		pan.add(txt);
		addr = new JTextField("", 20);
		pan.add(addr);
		gcb.gridy = 6;
		gcb.weighty = 1;
		add(pan, gcb);
		gcb.gridy = 7;
		gcb.weighty = 1;
		pan = new JPanel();
		txt = new JLabel("Home Phone (Only Integers Please):");
		pan.add(txt);
		homePhone = new JTextField("", 20);
		pan.add(homePhone);
		add(pan, gcb);
		gcb.gridy = 8;
		gcb.weighty = 1;
		pan = new JPanel();
		txt = new JLabel("Work Phone (Only Integers Please)");
		pan.add(txt);
		workPhone = new JTextField("", 20);
		pan.add(workPhone);
		gcb.gridy = 9;
		gcb.weighty = 1;
		add(pan, gcb);
		pan = new JPanel();
		txt = new JLabel("Weight (lbs):");
		pan.add(txt);
		weight = new JTextField("", 20);
		pan.add(weight);
		gcb.gridy = 10;
		gcb.weighty = 1;
		add(pan, gcb);
		pan = new JPanel();
		txt = new JLabel("Height:");
		pan.add(txt);
		heightft = new JTextField("", 10);
		heightin = new JTextField("", 10);
		pan.add(heightft);
		pan.add(new JLabel("ft"));
		pan.add(heightin);
		pan.add(new JLabel("in"));
		gcb.gridy = 11;
		gcb.weighty = 1;
		add(pan, gcb);
		pan = new JPanel();
		txt = new JLabel("Annual Income ($):");
		pan.add(txt);
		income = new JComboBox<String>();
		income.addItem("0-24,999");
		income.addItem("25,000-50,000");
		income.addItem("50,000+");
		pan.add(income);
		gcb.gridy = 12;
		gcb.weighty = 1;
		add(pan, gcb);
		all = new JPanel();
		txt = new JLabel("Allergies:");
		all.add(txt);
		allergies = new ArrayList<JTextField>();
		JTextField tmp = new JTextField("", 10);
		allergies.add(tmp);
		all.add(tmp);
		addAll = new JButton("Add More");
		addAll.addActionListener(new AddListener());
		counter = 2;
		all.add(addAll);
		gcb.gridy = 13;
		gcb.weighty = 1;
		add(all, gcb);
		
		gcb.gridy = 14;
		pan = new JPanel();
		txt = new JLabel("Emergency Contact Name:");
		pan.add(txt);
		emergencyName = new JTextField("", 20);
		pan.add(emergencyName);
		add(pan, gcb);
		
		gcb.gridy = 15;
		pan = new JPanel();
		txt = new JLabel("Emergency Phone (Only Integers Please):");
		pan.add(txt);
		emergencyPhone = new JTextField("", 20);
		pan.add(emergencyPhone);
		add(pan, gcb);
		
		pan = new JPanel();
		pan.setLayout(new GridBagLayout());
		GridBagConstraints gcb2 = new GridBagConstraints();
		gcb2.gridy = 0;
		gcb2.gridx = 0;
		gcb2.weighty = 1;
		gcb2.weightx = 1;
		submit = new JButton("Submit");
		JPanel buttons = new JPanel();
		buttons.add(submit);
		pan.add(buttons, gcb2);
		gcb.gridy = 16;
		gcb.weighty = 3;
		add(pan, gcb);
		submit.addActionListener(new SubmitListener());
	}
	private class SubmitListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			double we = weight();
			String he = heightft.getText().toString() + "." + heightin.getText().toString();
			String income2 = income.getSelectedItem().toString();
			String name2 = "\"";
			name2 = name2 + name.getText().toString();
			name2 = name2 + "\"";
			String gender2 =  "'" + gender.getSelectedItem() + "'";
			String bDay = "'" + birth.getText().toString() + "'";
			String workNumber = workPhone.getText().toString();
			String homeNumber = homePhone.getText().toString();
			String address = "\"";
			address = address + addr.getText().toString();
			address = address + "\"";
			String emergencyPhone2 = emergencyPhone.getText().toString();
			String emergencyName2 = "\"";
			emergencyName2 = emergencyName2 + emergencyName.getText().toString();
			emergencyName2 = emergencyName2 + "\"";
			Statement stmt;
			Connection con = null;
			String type = "";
			try {
				Class.forName("com.mysql.jdbc.Driver").newInstance();
				con = DriverManager.getConnection("jdbc:mysql://academic-mysql.cc.gatech.edu/cs4400_Group_34", "cs4400_Group_34","9BkPSv5Z");
				stmt = con.createStatement();
				String query = "INSERT INTO Patient (Username, AnnualIncome, Name, HomePhone, DateofBirth, Gender, Address, WorkPhone, ContactName, ContactPhone, Weight, Height)"
						+ " VALUES (" + uname + ", '" + income2 + "', " + name2 + ", " + homeNumber + ", " + bDay + ", " + gender2 + ", " + address + ", " + workNumber + ", " + emergencyName2 + " , " + emergencyPhone2 + ", " + we + ", " + he + ")"
						+ " ON DUPLICATE KEY"
						+ " UPDATE AnnualIncome = '" + income2 + "', Name = " + name2 + ", HomePhone = " + homeNumber + ", DateofBirth = " + bDay + ", Gender = " + gender2 + ", Address = " + address + ", WorkPhone = " + workNumber + ", ContactName  = " + emergencyName2 + ", ContactPhone = " + emergencyPhone2 + ", Weight = " + we + ", Height = " + 79 + ";";
				stmt.executeUpdate(query);
				for (JTextField joe: allergies) {
					query = "INSERT INTO Patient2 (UsernameOfPatient, Allergies) Values (" + uname + ", \"" + joe.getText().toString() + "\") ON DUPLICATE KEY UPDATE UsernameOfPatient = " + uname + ", Allergies = \"" + joe.getText().toString() + "\";";
					stmt.executeUpdate(query);
				}
				frame.getContentPane().removeAll();
				PatientHome nur = new PatientHome(frame, uname);
				frame.add(nur);
				frame.repaint();
				frame.setVisible(true);
			} catch (Exception e1) {
				System.err.println("Exeption: " + e1.getMessage());
				JOptionPane.showMessageDialog(PatientProfile.this,
		            	"Something was entered incorrectly!");
			} finally {
				try {	
					if(con!=null)
						con.close();
				} catch (SQLException e1) { }
			}
		}
	}

	public double weight(){
		if(weight.getText().equals("")){
			return 0;
		}
		try{
			return Double.parseDouble(weight.getText().toString());
		} catch (Exception e) {
			weight.setText("");
			JOptionPane.showMessageDialog(PatientProfile.this,
	            	"Please enter an appropriate value for your weight");
		}
		return -1;
	}
	
	private class AddListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			all.setVisible(false);
			all.setEnabled(false);
			all.remove(counter);
			counter++;
			JTextField tmp = new JTextField("", 10);
			allergies.add(tmp);
			all.add(tmp);
			all.add(addAll);
			all.setEnabled(true);
			all.setVisible(true);
		}
	}
	
	public boolean checkDate(){
		if(birth.getText().toString().equals("")){
			return true;
		}
		String DATE_FORMAT = "dd/MM/yyyy";
		try{
		DateFormat df = new SimpleDateFormat(DATE_FORMAT);
		df.setLenient(false);
		df.parse(birth.getText());
		}catch (ParseException e){
			JOptionPane.showMessageDialog(PatientProfile.this,
	            	"Please correct the date format");
			birth.setText("");
			return false;
		}
		System.out.println("yay");
		return true;
	}
}
