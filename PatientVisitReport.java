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
// edit depending on data input

public class PatientVisitReport extends JPanel{
	// for messages just show in dialog box, too hard to show else
	private JButton back;
	private JFrame frame;
	private String username;
	private JLabel pans[][];
	private ArrayList<Noob> list;
	private Map<String, Noob> map;
	private JComboBox<String> month;
	private JComboBox<Integer> year;
	public PatientVisitReport(JFrame frame, String username){
		list = new ArrayList<Noob>();
		map = new HashMap<String, Noob>();
		this.frame = frame; 
		this.username = username;
		GridBagConstraints gcb = new GridBagConstraints();
		setLayout(new GridBagLayout());
		JLabel txt = new JLabel("Patient Visit History");
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
		gcb.weighty = 3;
		gcb.weightx = 1;
		add(panel, gcb);
		JPanel pan = new JPanel();
		gcb.gridy = 1;
		gcb.weighty = 2;
		txt = new JLabel("Select Month");
		pan.add(txt);
		month = new JComboBox<String>();
		pan.add(month);
		year = new JComboBox<Integer>();
		pan.add(year);
		add(pan, gcb);	
		pan = new JPanel();
		gcb.gridy = 2;
		gcb.weighty = 18;
		combo();
		getDocs();
		pan.setLayout(new GridLayout(1+list.size(),4));
		pans = new JLabel[1+list.size()][4];
		for(int x = 0; x<pans.length; x++){
			for(int y =0; y<4; y++){
				pans[x][y] = new JLabel();
				pans[x][y].setOpaque(true);
				pan.add(pans[x][y]);
				pans[x][y].setHorizontalAlignment(SwingConstants.CENTER);
				pans[x][y].setBorder(BorderFactory.createLineBorder(Color.black));
			}
		}
		pans[0][0].setBackground(Color.blue);
		pans[0][1].setBackground(Color.blue);
		pans[0][2].setBackground(Color.blue);
		pans[0][3].setBackground(Color.blue);
		pans[0][0].setText("Doctor Name");
		pans[0][1].setText("<html>No of Patients<br>Seen</html>");
		pans[0][2].setText("<html>No of<br>Prescriptions<br>Written</html>");
		pans[0][3].setText("Total Billing ($)");
		for(int x = 1; x<pans.length; x++){
			Noob tmp = list.get(x -1);
			pans[x][0].setText("Dr. " + tmp.getFname() + " " + tmp.getLname());
			pans[x][1].setText(tmp.getPats() + "");
			pans[x][2].setText(tmp.getPres() + "");
			float tot = tmp.getBill();
			pans[x][3].setText(String.format("%.2f", tot));
		}
		pan.setBorder(BorderFactory.createLineBorder(Color.black));
		add(pan, gcb);
		pan = new JPanel();
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
		back.addActionListener(new LoginListener());
		month.setSelectedIndex(3);
		year.setSelectedItem(2014);
		month.addActionListener(new GetListener());
		year.addActionListener(new GetListener());
		visits();
		setter();
	}
	public void setter(){
		for(int x = 1; x<pans.length; x++){
			Noob tmp = list.get(x -1);
			pans[x][0].setText("Dr. " + tmp.getFname() + " " + tmp.getLname());
			pans[x][1].setText(tmp.getPats() + "");
			pans[x][2].setText(tmp.getPres() + "");
			float tot = tmp.getBill();
			pans[x][3].setText(String.format("%.2f", tot));
		}
	}
	public void combo(){
		for(int i = 0; i<500; i++){
			year.addItem(2014-i);
		}
		month.addItem("January");
		month.addItem("February");
		month.addItem("March");
		month.addItem("April");
		month.addItem("May");
		month.addItem("June");
		month.addItem("July");
		month.addItem("August");
		month.addItem("September");
		month.addItem("October");
		month.addItem("November");
		month.addItem("December");
	}
	public void getDocs(){
		String query = "Select * FROM Doctor;";
		Connection con = null;
		Statement stmt;
		try {
			con = DriverManager.getConnection("jdbc:mysql://academic-mysql.cc.gatech.edu/cs4400_Group_34", "cs4400_Group_34","9BkPSv5Z");
			stmt = con.createStatement();
			ResultSet info = stmt.executeQuery(query);
			while(info.next()){
				String uname = info.getString("Username");
				String fname = info.getString("FirstName");
				String lname = info.getString("LastName");
				Noob tmp = new Noob(uname, fname, lname);
				map.put(uname, tmp);
				list.add(tmp);
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
	public void getSurgeries(){
		String query = "Select * FROM Surgery;";
		Connection con = null;
		Statement stmt;
		try {
			con = DriverManager.getConnection("jdbc:mysql://academic-mysql.cc.gatech.edu/cs4400_Group_34", "cs4400_Group_34","9BkPSv5Z");
			stmt = con.createStatement();
			ResultSet info = stmt.executeQuery(query);
			while(info.next()){
				/*Noob tmp = new Noob(info.getString("surgeryType"), info.getFloat("CostOfSurgery"), info.getString("CPTCode"));
				list.add(tmp);
				map.put(tmp.getCpt(), tmp);*/
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
	private class LoginListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			frame.getContentPane().removeAll();
			AdministrativeHomepage nur = new AdministrativeHomepage(frame, username);
			frame.add(nur);
			frame.repaint();
			frame.setVisible(true);
		}
	}
	private class Noob{
		private String fname;
		private String lname;
		private String uname;
		private int pats;
		private int pres;
		private float bill;
		public Noob(String uname, String fname, String lname){
			setUname(uname);
			setFname(fname);
			setLname(lname);
			pats = 0;
			pres = 0;
			bill = 0;
		}
		public String getFname() {
			return fname;
		}
		public void setFname(String fname) {
			this.fname = fname;
		}
		public String getLname() {
			return lname;
		}
		public void setLname(String lname) {
			this.lname = lname;
		}
		public void setUname(String uname) {
			this.uname = uname;
		}
		public int getPats() {
			return pats;
		}
		public void setPats(int pats) {
			this.pats = pats;
		}
		public int getPres() {
			return pres;
		}
		public void setPres(int pres) {
			this.pres = pres;
		}
		public float getBill() {
			return bill;
		}
		public void setBill(float bill) {
			this.bill = bill;
		}
	}
	public void visits(){
		String query = "SELECT * FROM Visit;";
		Connection con = null;
		Statement stmt;
		try {
			con = DriverManager.getConnection("jdbc:mysql://academic-mysql.cc.gatech.edu/cs4400_Group_34", "cs4400_Group_34","9BkPSv5Z");
			stmt = con.createStatement();
			ResultSet info = stmt.executeQuery(query);
			while(info.next()){
				String date = info.getString("dateOfVisit");
				int yr = Integer.parseInt(date.substring(0,4));
				int mth = Integer.parseInt(date.substring(5, 7));
				if(((int)year.getSelectedItem()) == yr && (month.getSelectedIndex()+1)==mth){
					String uname = info.getString("UsernameOfDoctor");
					//System.out.println(uname);
					Noob tmp = map.get(uname);
					tmp.setPats(tmp.getPats()+1);
					float bill = info.getFloat("billingAmount");
					tmp.setBill(tmp.getBill()+bill);
				}
			}
			query = "SELECT * FROM Results_In;";
			info = stmt.executeQuery(query);
			while(info.next()){
				String date = info.getString("dateOfVisit");
				int yr = Integer.parseInt(date.substring(0,4));
				int mth = Integer.parseInt(date.substring(5, 7));
				if(((int)year.getSelectedItem()) == yr && (month.getSelectedIndex()+1)==mth){
					String uname = info.getString("UsernameOfDoctor");
					Noob tmp = map.get(uname);
					tmp.setPres(tmp.getPres()+1);
				}
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
	private class GetListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent arg0) {
			// TODO Auto-generated method stub
			map = new HashMap<String, Noob>();
			list = new ArrayList<Noob>();
			getDocs();
			visits();
			setter();
		}
	}
}
