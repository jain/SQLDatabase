import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.*;


public class DoctorHomepage extends JPanel {
	private String mess;
	private JButton viewAppointments;
	private JButton patientVisits;
	private JButton recordSurgery;
	private JButton communicate;
	private JButton editProfile;
	private JButton messages;
	private JFrame frame;
	private String uname;
	public DoctorHomepage(JFrame frame, String uname){
		this.uname = uname;
		this.frame = frame;
		GridBagConstraints gcb = new GridBagConstraints();
		setLayout(new GridBagLayout());
		JLabel txt = new JLabel("Homepage for Doctors");
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
		gcb.weighty = 1;
		gcb.weightx = 1;
		add(panel, gcb);
		gcb.gridy = 1;
		gcb.weightx = 1;
		gcb.weighty = 4.4;
		JPanel pan = new JPanel();
		pan.setLayout(new BorderLayout());
		viewAppointments = new JButton("View Appointments Calendar");
		viewAppointments.addActionListener(new AppointmentsListener());
		pan.add(viewAppointments, BorderLayout.CENTER);
		add(pan, gcb);
		pan = new JPanel();
		gcb.gridy = 2;
		pan = new JPanel();
		pan.setLayout(new BorderLayout());
		patientVisits = new JButton("Patient Visits");
		patientVisits.addActionListener(new VisitsListener());
		pan.add(patientVisits, BorderLayout.CENTER);
		add(pan, gcb);
		gcb.gridy = 3;
		pan = new JPanel();
		pan.setLayout(new BorderLayout());
		recordSurgery = new JButton("Record a Surgery");
		recordSurgery.addActionListener(new RecordSurgeryListener());
		pan.add(recordSurgery, BorderLayout.CENTER);
		add(pan, gcb);
		gcb.gridy = 4;
		pan = new JPanel();
		pan.setLayout(new BorderLayout());
		communicate = new JButton("Communicate with Patient");
		communicate.addActionListener(new CommunicatePatientListener());
		pan.add(communicate, BorderLayout.CENTER);
		add(pan, gcb);
		gcb.gridy = 5;
		pan = new JPanel();
		pan.setLayout(new BorderLayout());
		communicate = new JButton("Communicate with Doctor");
		communicate.addActionListener(new CommunicateDoctorListener());
		pan.add(communicate, BorderLayout.CENTER);
		add(pan, gcb);
		gcb.gridy = 6;
		pan = new JPanel();
		pan.setLayout(new BorderLayout());
		editProfile = new JButton("Edit Profile");
		editProfile.addActionListener(new EditProfileListener());
		pan.add(editProfile, BorderLayout.CENTER);
		add(pan, gcb);
		gcb.gridy = 7;
		pan = new JPanel();
		pan.setLayout(new BorderLayout());
		mess = messagesDoctor();
		messages = new JButton("You you have " + mess + " unread message(s) from Doctors");
		messages.addActionListener(new DoctorMessagesListener());
		pan.add(messages, BorderLayout.CENTER);
		add(pan, gcb);
		gcb.gridy = 8;
		pan = new JPanel();
		pan.setLayout(new BorderLayout());
		mess = messagesPatient();
		messages = new JButton("You you have " + mess + " unread message(s) from Patients");
		messages.addActionListener(new PatientMessagesListener());
		pan.add(messages, BorderLayout.CENTER);
		add(pan, gcb);
		
	}
	public String messagesDoctor(){
		Connection con = null;
		Statement stmt;
		try {
			String query = "SELECT Count(status) AS Count FROM Communicates_With " +
					"WHERE status = 'Unread' AND UsernameOfDoctor = " + uname + ";";
			con = DriverManager.getConnection("jdbc:mysql://academic-mysql.cc.gatech.edu/cs4400_Group_34", "cs4400_Group_34","9BkPSv5Z");
			stmt = con.createStatement();
			ResultSet info = stmt.executeQuery(query);
			info.next();
			return (info.getString("Count"));
		} catch (Exception e) {
			System.err.println("Exception :" + e.getMessage());
		} finally{
			try{	
				if(con!=null)
					con.close();
			} catch (SQLException e1) {}
		}
		return "";
	}	
	public String messagesPatient(){
		Connection con = null;
		Statement stmt;
		try {
			String query = "SELECT Count(status) AS Count FROM Send_Message_To_Doctor " +
					"WHERE status = 'Unread' AND UsernameOfDoctor = " + uname + ";";
			con = DriverManager.getConnection("jdbc:mysql://academic-mysql.cc.gatech.edu/cs4400_Group_34", "cs4400_Group_34","9BkPSv5Z");
			stmt = con.createStatement();
			ResultSet info = stmt.executeQuery(query);
			info.next();
			return (info.getString("Count"));
		} catch (Exception e) {
			System.err.println("Exception :" + e.getMessage());
		} finally{
			try{	
				if(con!=null)
					con.close();
			} catch (SQLException e1) {}
		}
		return "";
	}
	private class AppointmentsListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			frame.getContentPane().removeAll();
			ApptsCalendar nur = new ApptsCalendar(frame, uname);
			frame.add(nur);
			frame.repaint();
			frame.setVisible(true);
		}
	}
	private class VisitsListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			frame.getContentPane().removeAll();
			DoctorViewsHistory nur = new DoctorViewsHistory(frame, uname);
			JScrollPane sp = new JScrollPane(nur);
			frame.add(sp);
			frame.repaint();
			frame.setVisible(true);
		}
	}
	private class RecordSurgeryListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			/*frame.getContentPane().removeAll();
			RecordAVisit nur = new RecordAVisit(frame, uname);
			frame.add(nur);
			frame.repaint();
			frame.setVisible(true);*/
		}
	}
	private class CommunicatePatientListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			frame.getContentPane().removeAll();
			SendMessageToPatient nur = new SendMessageToPatient(frame, uname);
			frame.add(nur);
			frame.repaint();
			frame.setVisible(true);
		}
	}
	private class CommunicateDoctorListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			frame.getContentPane().removeAll();
			SendMessageToDoctorFromDoctor nur = new SendMessageToDoctorFromDoctor(frame, uname);
			frame.add(nur);
			frame.repaint();
			frame.setVisible(true);
		}
	}
	private class EditProfileListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			frame.getContentPane().removeAll();
			DoctorProfile nur = new DoctorProfile(frame, uname);
			frame.add(nur);
			frame.repaint();
			frame.setVisible(true);
		}
	}
	private class PatientMessagesListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			frame.getContentPane().removeAll();
			InboxDoctorPatient nur = new InboxDoctorPatient(frame, uname);
			frame.add(nur);
			frame.repaint();
			frame.setVisible(true);
		}
	}
	private class DoctorMessagesListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			frame.getContentPane().removeAll();
			InboxDoctorDoctor nur = new InboxDoctorDoctor(frame, uname);
			frame.add(nur);
			frame.repaint();
			frame.setVisible(true);
		}
	}
}
