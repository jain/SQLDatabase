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

public class DoctorViewsHistory extends JPanel{
	// for messages just show in dialog box, too hard to show else
	private JButton back;
	private JFrame frame;
	private String username;
	private JTextField name;
	private JButton create;
	private ArrayList<Integer> phones;
	private JPanel billPane;
	private ArrayList<String> names;
	private ArrayList<String> unames;
	private JTextField phone;
	private Map<JButton, String> patients;
	private Map<JButton, Record> visit;
	public DoctorViewsHistory(JFrame frame, String username){
		this.frame = frame; 
		this.username = username;
		phones = new ArrayList<Integer>();
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
		txt = new JLabel("Patient Name:");
		pan.add(txt);
		name = new JTextField(10);
		pan.add(name);
		pan.add(new JLabel("Phone Number: "));
		phone = new JTextField(10);
		pan.add(phone);
		create = new JButton("Get Patient");
		create.addActionListener(new CreateListener());
		pan.add(create);
		add(pan, gcb);	
		billPane = new JPanel();
		gcb.gridy = 3;
		gcb.weighty = 18;
		add(billPane, gcb);
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
	}
	private class LoginListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			frame.getContentPane().removeAll();
			DoctorHomepage nur = new DoctorHomepage(frame, username);
			frame.add(nur);
			frame.repaint();
			frame.setVisible(true);
		}
	}
	private class SelectListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			String puname = "\"";
			puname = puname+ patients.get(e.getSource());
			puname = puname + "\"";
			//System.out.println(puname);
			frame.getContentPane().removeAll();
			DoctorViewsPatientHistory nur = new DoctorViewsPatientHistory(frame, username, puname);
			frame.add(nur);
			frame.repaint();
			frame.setVisible(true);
			// swiwtch frame
		}
	}
	private class RecordListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			Record record = visit.get(e.getSource());
			frame.getContentPane().removeAll();
			RecordAVisit nur = new RecordAVisit(frame, username, record.getUsername(), record.getName());
			frame.add(nur);
			frame.repaint();
			frame.setVisible(true);
		}
		
	}
	private class CreateListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent v) {
			int num = 0;
			try{
				if(!phone.getText().trim().equals("")){
					num = Integer.parseInt(phone.getText().toString().trim());
				}
			}catch (Exception E){
				JOptionPane.showMessageDialog(frame, "Please enter a valid Home Phone Number!!!");
				return;
			}
			if(phone.getText().toString().trim().equals("") && !name.getText().toString().trim().equals("")){
				String patient = "\"";
				patient = patient + name.getText().toString().trim();
				patient = patient + "\"";
				String query = "SELECT * FROM Patient WHERE Name = " + patient +";";
				populate(query);
			}
			else if(name.getText().toString().trim().equals("") && !phone.getText().toString().trim().equals("")){
				String query = "SELECT * FROM Patient WHERE HomePhone = " + num +";";
				populate(query);
			}
			else if(name.getText().toString().trim().equals("") && phone.getText().toString().trim().equals("")){
				String query = "SELECT * FROM Patient;";
				populate(query);
			}
			else{
				String patient = "\"";
				patient = patient + name.getText().toString().trim();
				patient = patient + "\"";
				String query = "SELECT * FROM Patient WHERE Name = " + patient +" AND HomePhone = " + num +";";
				populate(query);
			}
		}
	}
	public void populate(String query){
		names = new ArrayList<String>();
		unames = new ArrayList<String>();
		billPane.removeAll();
		billPane.revalidate();
		phones = new ArrayList<Integer>();
		patients = new HashMap<JButton, String>();
		visit = new HashMap<JButton, Record>();
		Connection con = null;
		Statement stmt;
		try {
			con = DriverManager.getConnection("jdbc:mysql://academic-mysql.cc.gatech.edu/cs4400_Group_34", "cs4400_Group_34","9BkPSv5Z");
			stmt = con.createStatement();
			ResultSet info = stmt.executeQuery(query);
			while(info.next()){
				names.add(info.getString("Name"));
				phones.add(info.getInt("HomePhone"));
				unames.add(info.getString("Username"));
			}
			billPane.setLayout(new GridLayout( phones.size()+1, 4));
			JLabel lbl = new JLabel("Patient Name");
			lbl.setBorder(BorderFactory.createLineBorder(Color.black));
			lbl.setBackground(Color.blue);
			lbl.setHorizontalAlignment(SwingConstants.CENTER);
			lbl.setOpaque(true);
			billPane.add(lbl);
			lbl = new JLabel("Phone Number");
			lbl.setBorder(BorderFactory.createLineBorder(Color.black));
			lbl.setHorizontalAlignment(SwingConstants.CENTER);
			lbl.setBackground(Color.blue);
			lbl.setOpaque(true);
			billPane.add(lbl);
			lbl = new JLabel("Select One");
			lbl.setBorder(BorderFactory.createLineBorder(Color.black));
			lbl.setHorizontalAlignment(SwingConstants.CENTER);
			lbl.setBackground(Color.blue);
			lbl.setOpaque(true);
			billPane.add(lbl);
			lbl = new JLabel("Record A Visit");
			lbl.setBorder(BorderFactory.createLineBorder(Color.black));
			lbl.setHorizontalAlignment(SwingConstants.CENTER);
			lbl.setBackground(Color.blue);
			lbl.setOpaque(true);
			billPane.add(lbl);
			for(int i = 0; i<phones.size(); i++){
				JLabel label = new JLabel(names.get(i));
				label.setBorder(BorderFactory.createLineBorder(Color.black));
				label.setHorizontalAlignment(SwingConstants.CENTER);
				billPane.add(label);
				label = new JLabel(phones.get(i)+"");
				label.setBorder(BorderFactory.createLineBorder(Color.black));
				label.setHorizontalAlignment(SwingConstants.CENTER);
				billPane.add(label);
				JButton button =  new JButton("View");
				button.addActionListener(new SelectListener());
				billPane.add(button);
				button.setBorder(BorderFactory.createLineBorder(Color.black));
				patients.put(button, unames.get(i));
				button =  new JButton("Record");
				button.addActionListener(new RecordListener());
				billPane.add(button);
				button.setBorder(BorderFactory.createLineBorder(Color.black));
				Record record = new Record(unames.get(i), names.get(i));
				visit.put(button, record);
			}
			billPane.revalidate();
		} catch (Exception e) {
			System.err.println("Exception :" + e.getMessage());
		} finally{
			try{	
				if(con!=null)
					con.close();
			} catch (SQLException e1) {}
		}
	}
	private class Record{
		private String username;
		private String name;
		public Record(String username, String name){
			setUsername(username);
			setName(name);
		}
		/**
		 * @return the username
		 */
		public String getUsername() {
			return username;
		}
		/**
		 * @param username the username to set
		 */
		public void setUsername(String username) {
			this.username = username;
		}
		/**
		 * @return the name
		 */
		public String getName() {
			return name;
		}
		/**
		 * @param name the name to set
		 */
		public void setName(String name) {
			this.name = name;
		}
	}
}
