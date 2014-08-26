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


public class DoctorProfile extends JPanel{
	private JButton submit;
	private JButton add;
	private JTextField lic;
	private JTextField fname;
	private JTextField lname;
	private JTextField birth;
	private JTextField workPhone;
	private JTextField room;
	private JTextField addr;
	private JComboBox<String> specialty;
	private JComboBox<String> avail0;
	private JComboBox<String> avail1;
	private JComboBox<String> avail2;
	private ArrayList<ArrayList<JComboBox>> avail;
	private ArrayList<JPanel> all;
	private GridBagConstraints gcb;
	private String uname;
	private JFrame frame;
	public DoctorProfile(JFrame frame, String uname){
		this.frame = frame;
		this.uname = uname;
		avail = new ArrayList<ArrayList<JComboBox>>();
		gcb = new GridBagConstraints();
		setLayout(new GridBagLayout());
		JLabel txt = new JLabel("Doctor Profile");
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
		txt = new JLabel("License Number:");
		pan.add(txt);
		lic = new JTextField("", 20);
		pan.add(lic);
		gcb.gridy = 3;
		gcb.weighty = 1;
		add(pan, gcb);
		pan = new JPanel();
		txt = new JLabel("First Name:");
		pan.add(txt);
		fname = new JTextField("", 20);
		pan.add(fname);
		gcb.gridy = 4;
		gcb.weighty = 1;
		add(pan, gcb);
		pan = new JPanel();
		txt = new JLabel("Last Name:");
		pan.add(txt);
		lname = new JTextField("", 20);
		pan.add(lname);
		gcb.gridy = 5;
		gcb.weighty = 1;
		add(pan, gcb);
		pan = new JPanel();
		txt = new JLabel("Date of Birth YYYY-MM-DD:");
		pan.add(txt);
		birth = new JTextField("", 20);
		pan.add(birth);
		gcb.gridy = 6;
		gcb.weighty = 1;
		add(pan, gcb);
		gcb.gridy = 8;
		gcb.weighty = 1;
		pan = new JPanel();
		txt = new JLabel("Work Phone:");
		pan.add(txt);
		workPhone = new JTextField("", 20);
		pan.add(workPhone);
		gcb.gridy = 7;
		gcb.weighty = 1;
		add(pan, gcb);
		pan = new JPanel();
		txt = new JLabel("Specialty:");
		pan.add(txt);
		specialty = new JComboBox<String>();
		specialty.addItem("General Physician");
		specialty.addItem("Heart Specialist");
		specialty.addItem("Eye Physician");
		specialty.addItem("Orthopedics");
		specialty.addItem("Psychiatry");
		gcb.gridy = 8;
		specialty.addItem("Gynecologist");
		pan.add(specialty);
		gcb.weighty = 1;
		add(pan, gcb);
		gcb.gridy = 9;
		gcb.weighty = 1;
		pan = new JPanel();
		txt = new JLabel("Room Number:");
		pan.add(txt);
		room = new JTextField("", 20);
		pan.add(room);
		add(pan, gcb);
		pan = new JPanel();
		txt = new JLabel("Home Address:");
		pan.add(txt);
		addr = new JTextField("", 20);
		pan.add(addr);
		gcb.gridy = 10;
		gcb.weighty = 1;
		add(pan, gcb);
		all = new ArrayList<JPanel>();
		pan = new JPanel();
		txt = new JLabel("Availability:");
		pan.add(txt);
		avail0 = new JComboBox<String>();
		avail1 = new JComboBox<String>();
		avail2 = new JComboBox<String>();
		avail();
		pan.add(avail0);
		pan.add(new JLabel("From: "));
		pan.add(avail1);
		pan.add(new JLabel("To: "));
		pan.add(avail2);
		gcb.gridy = 11;
		gcb.weighty = 1;
		add = new JButton("Add More");
		add.addActionListener(new AddListener());
		pan.add(add);
		all.add(pan);
		add(all.get(0), gcb);
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
		gcb.gridy = 12;
		gcb.weighty = 4;
		all.add(pan);
		add(all.get(1), gcb);
		submit.addActionListener(new SubmitListener());
	}
	private class SubmitListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			for(int i = 0; i<avail.size(); i++){
				JComboBox<String> day = avail.get(i).get(0);
				JComboBox<String> start = avail.get(i).get(1);
				JComboBox<String> end = avail.get(i).get(2);
				if(!(start.getSelectedIndex()<end.getSelectedIndex())){
					JOptionPane.showMessageDialog(frame, "Please Enter valid availability times");
					return;
				}
			}
			String license = "\"";
			license = license + lic.getText().toString();
			license = license + "\"";
			String firstName = "\"";
			firstName = firstName + fname.getText().toString();
			firstName = firstName + "\"";
			String lastName = "\"";
			lastName = lastName + lname.getText().toString();
			lastName = lastName + "\"";
			String bDay = "'" + birth.getText().toString() + "'";
			String workNumber = workPhone.getText().toString();
			String roomNumber = room.getText().toString();
			String address = "\"";
			address = address + addr.getText().toString();
			address = address + "\"";
			String spec = "'" + String.valueOf(specialty.getSelectedItem()) + "'";
			Statement stmt;
			Connection con = null;
			String type = "";
			try {
				Class.forName("com.mysql.jdbc.Driver").newInstance();
				con = DriverManager.getConnection("jdbc:mysql://academic-mysql.cc.gatech.edu/cs4400_Group_34", "cs4400_Group_34","9BkPSv5Z");
				stmt = con.createStatement();
				String query = "INSERT INTO Doctor (Username, LicenseNo, FirstName, LastName, DateOfBirth, WorkPhone, Specialty, RoomNumber, HomeAddress)"
						+ " VALUES (" + uname + ", " + license + ", " + firstName + "," + lastName + ", " + bDay + ", "+ workNumber + ", " + spec + ", " + roomNumber + ", " + address + ")"
						+ " ON DUPLICATE KEY "
						+ "UPDATE FirstName = " + firstName + ", LastName = " + lastName + ", DateOfBirth = " + bDay + ", WorkPhone = " + workNumber + ", Specialty = " + spec + ", RoomNumber = " + roomNumber + ", HomeAddress = " + address + ";";
				stmt.executeUpdate(query);
				enterAvail();
				frame.getContentPane().removeAll();
				DoctorHomepage nur = new DoctorHomepage(frame, uname);
				frame.add(nur);
				frame.repaint();
				frame.setVisible(true);
			} catch (Exception e1) {
				JOptionPane.showMessageDialog(DoctorProfile.this,
						"Something was entered incorrectly!");
			} finally{
				try{	
					if(con!=null)
						con.close();
				} catch (SQLException e1) {}
				//				}
				//				if(type.equals("Doctor")){
				//					checkDoctorProfile();
				//				}else if(type.equals("Patient")){
				//					checkPatientProfile();
				//				}else{
				//					frame.getContentPane().removeAll();
				//					AdministrativeHomepage nur = new AdministrativeHomepage(frame);
				//					frame.add(nur);
				//					frame.repaint();
				//					frame.setVisible(true);
			}
			//			} else {
			//				JOptionPane.showMessageDialog(DoctorProfile.this,
			//		            	"Please Enter a Valid Birthdate");
			//			}
		}
	}
	private class AddListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			setVisible(false);
			setEnabled(false);
			//remove(10);
			//remove(10);
			for(int i = 0; i<all.size(); i++){
				remove(10);
			}
			JPanel last = all.remove(all.size()-1);
			JPanel pan = new JPanel();
			JLabel txt = new JLabel("                  ");
			pan.add(txt);
			avail0 = new JComboBox<String>();
			avail1 = new JComboBox<String>();
			avail2 = new JComboBox<String>();
			avail();
			pan.add(avail0);
			pan.add(new JLabel("From: "));
			pan.add(avail1);
			pan.add(new JLabel("To: "));
			pan.add(avail2);
			all.add(pan);
			all.add(last);
			for(int i = 0; i<all.size(); i++){
				gcb.gridx = 0;
				gcb.gridy = 11 + i;
				gcb.weighty = 1;
				add(all.get(i), gcb);
			}
			setVisible(true);
			setEnabled(true);
		}
	}
	public boolean checkDate(){
		String DATE_FORMAT = "dd/MM/yyyy";
		try{
			DateFormat df = new SimpleDateFormat(DATE_FORMAT);
			df.setLenient(false);
			df.parse(birth.getText());
		}catch (ParseException e){
			System.out.println("fail");
			return false;
		}
		System.out.println("yay");
		return true;
	}
	public void avail(){
		avail.add(new ArrayList<JComboBox>());
		avail.get(avail.size()-1).add(avail0);
		avail.get(avail.size()-1).add(avail1);
		avail.get(avail.size()-1).add(avail2);
		avail0.addItem("Monday");
		avail0.addItem("Tuesday");
		avail0.addItem("Wednesday");
		avail0.addItem("Thursday");
		avail0.addItem("Friday");
		avail0.addItem("Saturday");
		avail0.addItem("Sunday");
		int j = 0;
		int time = 0;
		for(int i = 0; i<48; i++){
			if(time==60){
				time = 0;
				j++;
			}
			if(time == 0){
				avail1.addItem(j + ":" + time + "0");
				avail2.addItem(j+":" + time +"0");	
			}else{
				avail1.addItem(j + ":" + time);
				avail2.addItem(j+":" + time);	
			}
			time+= 30;

			/*if(time == 0){
				avail1.addItem(j + ":" + time + ":00");
				avail2.addItem(j+":" + time + ":00");
			}else{
				avail1.addItem(j + ":" + time + " am");
				avail2.addItem(j+":" + time + " am");
			}*/
		}
		/*j = 0;
		time = 0;
		for(int i = 0; i<24; i++){
			time+= 30;
			if(time==60){
				time = 0;
				j++;
			}
			if(time == 0){
				avail1.addItem(j + ":" + time + "0 pm");
				avail2.addItem(j+":" + time + "0 pm");
			}else{
				avail1.addItem(j + ":" + time + " pm");
				avail2.addItem(j+":" + time + " pm");
			}
		}*/
	}
	public void enterAvail(){
		for(int i = 0; i<avail.size();i++){
			JComboBox<String> day = avail.get(i).get(0);
			JComboBox<String> start = avail.get(i).get(1);
			JComboBox<String> end = avail.get(i).get(2);
			Connection con = null;
			Statement stmt;
			try {
				String d = "\"";
				d = d+ day.getSelectedItem().toString().trim();
				d = d + "\"";
				String query = "INSERT INTO Doctor_Availability (Username, Begin, Day, End) VALUES" +
						" (" + uname + ", '" + start.getSelectedItem().toString().trim() +"', " + d + ", '" + end.getSelectedItem().toString().trim() +"');";
				Class.forName("com.mysql.jdbc.Driver").newInstance();
				con = DriverManager.getConnection("jdbc:mysql://academic-mysql.cc.gatech.edu/cs4400_Group_34", "cs4400_Group_34","9BkPSv5Z");
				stmt = con.createStatement();
				stmt.executeUpdate(query);
			} catch (Exception e) {
				JOptionPane.showMessageDialog(frame, "One of the Times Entered exists twice, entry was not added!!!");
				//System.out.println(e.getMessage());
			} finally{
				try{	
					if(con!=null)
						con.close();
				} catch (SQLException e1) {}
			}
		}
	}
}
