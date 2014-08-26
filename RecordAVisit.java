import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;

import javax.swing.*;


public class RecordAVisit extends JPanel{
	private JButton submit;
	private JButton drug;
	private JTextField visit;
	private JTextField billingAmount;
	private JTextField name;
	private JTextField systolic;
	private JTextField diastolic;
	private JTextArea diagnosis;
	private JPanel top;
	private JPanel bot;
	private JTextField dosage;
	private JTextField months;
	private JTextField days;
	private JTextArea notes;
	private JFrame frame;
	private JTextField dname;
	private String patientUsr;
	private String uname;
	public RecordAVisit(JFrame frame, String uname, String patientUsername, String patientName) {
		this.uname = uname;
		this.frame = frame; 
		GridBagConstraints gcb = new GridBagConstraints();
		setLayout(new GridBagLayout());
		JLabel txt = new JLabel("Record a Visit");
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
		top = new JPanel();
		top.setBorder(BorderFactory.createLineBorder(Color.black));
		gcb.gridy =1 ;
		gcb.weighty = 10;
		add(top, gcb);
		gcb.gridy = 2;
		gcb.weighty = 12;
		bot = new JPanel();
		bot.setBorder(BorderFactory.createLineBorder(Color.black));
		add(bot, gcb);
		top();
		bot();
	}
	public void top() {
		top.setLayout(new GridBagLayout());
		GridBagConstraints gcb = new GridBagConstraints();
		gcb.fill = GridBagConstraints.BOTH;
		gcb.gridy = 0;
		gcb.gridx = 0;
		gcb.weighty = 1;
		gcb.weightx = 1;
		JPanel pan = new JPanel();
		pan.add(new JLabel("Date of Visit:"));
		visit = new JTextField(20);
		pan.add(visit);
		top.add(pan, gcb);
		gcb.gridy = 1;
		pan = new JPanel();
		pan.add(new JLabel("Patient Name:"));
		name = new JTextField(20);
		pan.add(name);
		top.add(pan, gcb);
		gcb.gridy = 2;
		pan = new JPanel();
		pan.add(new JLabel("Blood Pressure:"));
		pan.add(new JLabel("Systolic:"));
		systolic = new JTextField(5);
		pan.add(systolic);
		pan.add(new JLabel("Diastolic:"));
		diastolic = new JTextField(5);
		pan.add(diastolic);
		top.add(pan, gcb);
		gcb.gridy = 3;
		gcb.weighty = 3;
		pan = new JPanel();
		pan.add(new JLabel("Diagnosis:"));
		diagnosis = new JTextArea(2, 20);
		diagnosis.setBorder(BorderFactory.createLineBorder(Color.black));
		pan.add(diagnosis);
		top.add(pan, gcb);
		gcb.gridy = 4;
		gcb.weighty = 1;
		pan = new JPanel();
		pan.add(new JLabel("Billing Amount:"));
		billingAmount = new JTextField(20);
		billingAmount.setBorder(BorderFactory.createLineBorder(Color.black));
		pan.add(billingAmount);
		top.add(pan, gcb);
	}
	public void bot() {
		bot.setLayout(new GridBagLayout());
		GridBagConstraints gcb = new GridBagConstraints();
		gcb.fill = GridBagConstraints.BOTH;
		gcb.gridy = 0;
		gcb.gridx = 0;
		gcb.weighty = 1;
		gcb.weightx = 1;
		JPanel pan = new JPanel();
		pan.add(new JLabel("Drug Name:"));
		dname = new JTextField(20);
		pan.add(dname);
		bot.add(pan, gcb);
		gcb.gridy = 1;
		pan = new JPanel();
		pan.add(new JLabel("Dosage:"));
		dosage = new JTextField(20);
		pan.add(dosage);
		pan.add(new JLabel("per day"));
		bot.add(pan, gcb);
		gcb.gridy = 2;
		pan = new JPanel();
		pan.add(new JLabel("Duration:"));
		months = new JTextField(20);
		pan.add(months);
		pan.add(new JLabel("months"));
		days = new JTextField(20);
		pan.add(days);
		pan.add(new JLabel("days"));
		bot.add(pan, gcb);
		gcb.gridy = 3;
		gcb.weighty = 4;
		pan = new JPanel();
		pan.add(new JLabel("Notes:"));
		notes = new JTextArea(3, 20);
		pan.add(notes);
		notes.setBorder(BorderFactory.createLineBorder(Color.black));
		bot.add(pan, gcb);
		notes.setLineWrap(true);
		diagnosis.setLineWrap(true);
		pan = new JPanel();
		gcb.gridy = 4;
		gcb.weighty=2;
		drug = new JButton("Add New Prescription");
		submit = new JButton("Submit");
		JPanel buttons = new JPanel();
		buttons.add(submit);
		buttons.add(new JPanel());
		buttons.add(new JPanel());
		buttons.add(new JPanel());
		buttons.add(new JPanel());
		buttons.add(new JPanel());
		buttons.add(new JPanel());
		buttons.add(new JPanel());
		buttons.add(drug);
		pan.add(buttons);
		gcb.gridy = 4;
		gcb.weighty = 10;
		submit.addActionListener(new SubmitListener());
		drug.addActionListener(new DrugListener());
		bot.add(pan, gcb);
	}
	private class SubmitListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			//if(checkDate()&&checkPhone(workPhone)>-1&&checkPhone(homePhone)>-1&&weight()>-1&&height()!=null){	
			String visit2 = visit.getText().toString();
			String systolic2 = systolic.getText().toString();
			String diastolic2 =  diastolic.getText().toString();
			String billingAmount2 = billingAmount.getText().toString();
			Statement stmt;
			Connection con = null;
			String type = "";
			try {
				Class.forName("com.mysql.jdbc.Driver").newInstance();
				con = DriverManager.getConnection("jdbc:mysql://academic-mysql.cc.gatech.edu/cs4400_Group_34", "cs4400_Group_34","9BkPSv5Z");
				stmt = con.createStatement();
				String query = "INSERT INTO Visit( UsernameOfDoctor, UsernameOfPatient, DateOfVisit, BillingAmount, Diastolic, Systolic)"
					 + " VALUES (" + uname + ", \"myPatient\", '" + visit2 + " " + Calendar.getInstance().getTime().toString().substring(11, 19) + "', " + billingAmount2 + ", " + diastolic2 + ", " + systolic2 + ");";
				stmt.executeUpdate(query);
				frame.getContentPane().removeAll();
				DoctorHomepage nur = new DoctorHomepage(frame, uname);
				frame.add(nur);
				frame.repaint();
				frame.setVisible(true);
			} catch (Exception e1) {
				JOptionPane.showMessageDialog(RecordAVisit.this,
		            	"Something was entered incorrectly!");
			} finally{
				try{	
					if(con!=null)
						con.close();
				} catch (SQLException e1) {}
				//					}
				//					if(type.equals("Doctor")){
				//						checkDoctorProfile();
				//					}else if(type.equals("Patient")){
				//						checkPatientProfile();
				//					}else{
				//						frame.getContentPane().removeAll();
				//						AdministrativeHomepage nur = new AdministrativeHomepage(frame);
				//						frame.add(nur);
				//						frame.repaint();
				//						frame.setVisible(true);
			}
		}
	}
	
	private class DrugListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			dname.setText("");
			dosage.setText("");
			days.setText("");
			months.setText("");
			notes.setText("");
		}
	}
}
