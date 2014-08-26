import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.*;


public class PatientHome extends JPanel{
	private String mess;
	private JButton makeAppointments;
	private JButton viewHistory;
	private JButton orderMedication;
	private JButton communicate;
	private JButton rateADoctor;
	private JButton editProfile;
	private JButton messages;
	private JFrame frame;
	private String username;
	public PatientHome(JFrame frame, String username){
		this.username = username;
		this.frame = frame;
		GridBagConstraints gcb = new GridBagConstraints();
		setLayout(new GridBagLayout());
		JLabel txt = new JLabel("Homepage for Patient");
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
		makeAppointments = new JButton("Make an Appointment");
		makeAppointments.addActionListener(new ApptListener());
		pan.add(makeAppointments, BorderLayout.CENTER);
		add(pan, gcb);
		pan = new JPanel();
		gcb.gridy = 2;
		pan = new JPanel();
		pan.setLayout(new BorderLayout());
		viewHistory = new JButton("View Visit History");
		viewHistory.addActionListener(new HistoryListener());
		pan.add(viewHistory, BorderLayout.CENTER);
		add(pan, gcb);
		gcb.gridy = 3;
		pan = new JPanel();
		pan.setLayout(new BorderLayout());
		orderMedication = new JButton("Order Medication");
		orderMedication.addActionListener(new OrderListener());
		pan.add(orderMedication, BorderLayout.CENTER);
		add(pan, gcb);
		gcb.gridy = 4;
		pan = new JPanel();
		pan.setLayout(new BorderLayout());
		communicate = new JButton("Communicate");
		communicate.addActionListener(new SendListener());
		pan.add(communicate, BorderLayout.CENTER);
		add(pan, gcb);
		gcb.gridy = 5;
		pan = new JPanel();
		pan.setLayout(new BorderLayout());
		rateADoctor = new JButton("Rate a Doctor");
		rateADoctor.addActionListener(new RateListener());
		pan.add(rateADoctor, BorderLayout.CENTER);
		add(pan, gcb);
		gcb.gridy = 6;
		pan = new JPanel();
		pan.setLayout(new BorderLayout());
		editProfile = new JButton("Edit Profile");
		editProfile.addActionListener(new EditListener());
		pan.add(editProfile, BorderLayout.CENTER);
		add(pan, gcb);
		gcb.gridy = 7;
		pan = new JPanel();
		pan.setLayout(new BorderLayout());
		mess = messages();
		messages = new JButton("You you have " + mess + " unread message(s)");
		messages.addActionListener(new MessageListener());
		pan.add(messages, BorderLayout.CENTER);
		add(pan, gcb);
	}
	private class MessageListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			frame.getContentPane().removeAll();
			InboxPatient nur = new InboxPatient(frame, username);
			frame.add(nur);
			frame.repaint();
			frame.setVisible(true);
		}
	}
	private class RateListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			frame.getContentPane().removeAll();
			RateADoctor nur = new RateADoctor(frame, username);
			frame.add(nur);
			frame.repaint();
			frame.setVisible(true);
		}
	}
	public String messages(){
		Connection con = null;
		Statement stmt;
		try {
			String query = "SELECT Count(status) AS Count FROM Send_Message_To_Patient " +
					"WHERE status = 'Unread' AND UsernameOfPatient = " + username + ";";
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
	private class SendListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			frame.getContentPane().removeAll();
			SendMessageToDoctor nur = new SendMessageToDoctor(frame, username);
			frame.add(nur);
			frame.repaint();
			frame.setVisible(true);
		}
	}
	private class HistoryListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			frame.getContentPane().removeAll();
			//PatientHistory nur = new PatientHistory(frame, username);
			PatientVisitHistory nur = new PatientVisitHistory(frame, username);
			JScrollPane sp = new JScrollPane(nur);
			frame.add(sp);
			frame.repaint();
			frame.setVisible(true);
		}
	}
	private class EditListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			frame.getContentPane().removeAll();
			PatientProfile nur = new PatientProfile(frame, username);
			JScrollPane sp = new JScrollPane(nur);
			frame.add(sp);
			frame.repaint();
			frame.setVisible(true);
		}
	}
	private class OrderListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			frame.getContentPane().removeAll();
			OrderMedication nur = new OrderMedication(frame, username);
			frame.add(nur);
			frame.repaint();
			frame.setVisible(true);
		}
	}
	private class ApptListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			frame.getContentPane().removeAll();
			ScheduleAapointments nur = new ScheduleAapointments(frame, username);
			JScrollPane sp = new JScrollPane(nur);
			frame.add(sp);
			frame.repaint();
			frame.setVisible(true);
		}
	}
}
