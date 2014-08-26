import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.*;


public class AdministrativeHomepage extends JPanel{
	private JButton billing;
	private JButton doctorPerformanceRecord;
	private JButton surgeryReport;
	private JButton patientVisitReport;
	private JFrame frame;
	private String username;
	public AdministrativeHomepage(JFrame frame, String username) {
		this.username = username;
		this.frame = frame;
		GridBagConstraints gcb = new GridBagConstraints();
		setLayout(new GridBagLayout());
		JLabel txt = new JLabel(" Homepage");
		JPanel panel = new JPanel();
		Font myFont = new Font("Serif", Font.BOLD, 50);
		txt.setFont(myFont);
		panel.setBackground(Color.yellow);
		txt.setHorizontalAlignment(SwingConstants.CENTER);
		panel.setLayout(new BorderLayout());
		panel.add(txt, BorderLayout.CENTER);
		panel.setBorder(BorderFactory.createLineBorder(Color.black));
		myFont = new Font("Times New Roman",Font.PLAIN ,20);
		gcb.fill = GridBagConstraints.BOTH;
		gcb.gridy = 0;
		gcb.gridx = 0;
		gcb.weighty = 2;
		gcb.weightx = 1;
		add(panel, gcb);
		gcb.gridy = 2;
		gcb.weighty = 5.5;
		billing = new JButton("Billing");
		billing.setFont(myFont);
		billing.addActionListener(new SubmitListener());
		add(billing, gcb);
		gcb.gridy = 3;
		gcb.weighty = 5.5;
		doctorPerformanceRecord = new JButton("Doctor Performance Report");
		doctorPerformanceRecord.addActionListener(new DoctorPerformanceListener());
		doctorPerformanceRecord.setFont(myFont);
		add(doctorPerformanceRecord, gcb);
		gcb.gridy = 4;
		gcb.weighty = 5.5;
		surgeryReport = new JButton("Surgery Report");
		surgeryReport.setFont(myFont);
		surgeryReport.addActionListener(new SurgeryPerformanceListener ());
		add(surgeryReport, gcb);
		gcb.gridy = 5;
		gcb.weighty = 5.5;
		patientVisitReport = new JButton("Patient Visit Report");
		patientVisitReport.addActionListener(new PatientVisitReportListener());
		patientVisitReport.setFont(myFont);
		add(patientVisitReport, gcb);
	}
	private class PatientVisitReportListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			frame.getContentPane().removeAll();
			PatientVisitReport nur = new PatientVisitReport(frame, username);
			JScrollPane sp = new JScrollPane(nur);
			frame.add(sp);
			frame.repaint();
			frame.setVisible(true);
		}
	}
	private class SubmitListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			frame.getContentPane().removeAll();
			Billing nur = new Billing(frame, username);
			JScrollPane sp = new JScrollPane(nur);
			frame.add(sp);
			frame.repaint();
			frame.setVisible(true);
		}
	}
	private class DoctorPerformanceListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			frame.getContentPane().removeAll();
			DoctorPerformanceReport nur = new DoctorPerformanceReport(frame, username);
			frame.add(nur);
			frame.repaint();
			frame.setVisible(true);
		}
	}
	private class SurgeryPerformanceListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			frame.getContentPane().removeAll();
			SurgeriesPerformed nur = new SurgeriesPerformed(frame, username);
			frame.add(nur);
			frame.repaint();
			frame.setVisible(true);
		}
	}
}
