import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.*;


public class DoctorPerformanceReport extends JPanel{
	// for messages just show in dialog box, too hard to show else
	private JButton back;
	private JLabel pans[][];
	private JFrame frame;
	private String username;
	public DoctorPerformanceReport(JFrame frame, String username){
		this.username = username;
		this.frame = frame; 
		GridBagConstraints gcb = new GridBagConstraints();
		setLayout(new GridBagLayout());
		JLabel txt = new JLabel("Doctor Performance Report");
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
		gcb.gridy = 1;
		gcb.weighty = 18;
		pan.setLayout(new GridLayout(7,3));
		pans = new JLabel[7][3];
		for(int i = 0; i<7; i++){for(int j = 0; j<3; j++){ pans[i][j] = new JLabel();
		pans[i][j].setOpaque(true);
		pan.add(pans[i][j]);
		pans[i][j].setHorizontalAlignment(SwingConstants.CENTER);
		pans[i][j].setBorder(BorderFactory.createLineBorder(Color.black));}}
		pans[0][0].setBackground(Color.blue);
		pans[0][1].setBackground(Color.blue);
		pans[0][2].setBackground(Color.blue);
		pans[0][0].setText("Specialty");
		pans[1][0].setText("General Physicians");
		pans[2][0].setText("Heart Specialists");
		pans[3][0].setText("Eye Physicians");
		pans[4][0].setText("Orthopedics");
		pans[5][0].setText("Psychiatry");
		pans[6][0].setText("Gynecologist");
		pans[0][1].setText("Average Rating");
		pans[0][2].setText("<html>Number of Surgeries<br>Performed</html>");
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
		back.addActionListener(new TrollListener());
		generate();
	}
	public void generate(){
		String query = "SELECT * FROM Doctor WHERE Specialty =  'General Physician';";
		pans[1][1].setText(ratings(query) + "");
		pans[1][2].setText(surgeries(query) + "");
		query = "SELECT * FROM Doctor WHERE Specialty =  'Heart Specialist';";
		pans[2][1].setText(ratings(query) + "");
		pans[2][2].setText(surgeries(query) + "");
		query = "SELECT * FROM Doctor WHERE Specialty =  'Eye Physician';";
		pans[3][1].setText(ratings(query) + "");
		pans[3][2].setText(surgeries(query) + "");
		query = "SELECT * FROM Doctor WHERE Specialty =  'Orthopedics';";
		pans[4][1].setText(ratings(query) + "");
		pans[4][2].setText(surgeries(query) + "");
		query = "SELECT * FROM Doctor WHERE Specialty =  'Psychiatry';";
		pans[5][1].setText(ratings(query) + "");
		pans[5][2].setText(surgeries(query) + "");
		query = "SELECT * FROM Doctor WHERE Specialty =  'Gynecologist';";
		pans[6][1].setText(ratings(query) + "");
		pans[6][2].setText(surgeries(query) + "");
	}
	public int surgeries(String query){
		Connection con = null;
		Statement stmt;
		try {
			con = DriverManager.getConnection("jdbc:mysql://academic-mysql.cc.gatech.edu/cs4400_Group_34", "cs4400_Group_34","9BkPSv5Z");
			stmt = con.createStatement();
			ResultSet info = stmt.executeQuery(query);
			ArrayList<Float> avg = new ArrayList<Float>();
			ArrayList<String> list = new ArrayList<String>();
			while(info.next()){
				String dname = "\"";
				dname = dname+ info.getString("Username");
				dname = dname + "\"";
				list.add(dname);
			}
			int sum = 0;
			for(int i = 0; i<list.size(); i++){
				String dname = list.get(i);
				String query2 = "SELECT COUNT(UsernameOfDoctor) AS Number From Performs_Surgery WHERE UsernameOfDoctor = " + dname + ";";
				ResultSet info2 = stmt.executeQuery(query2);
				if(info2.next()){
					sum+= info2.getInt("Number");
				}
			}
			return sum;
		} catch (Exception e) {
			System.err.println("Exception :" + e.getMessage());
		} finally{
			try{	
				if(con!=null)
					con.close();
			} catch (SQLException e1) {}
		}
		return 0;
	}
	public int ratings(String query){
		Connection con = null;
		Statement stmt;
		//Statement stmt2;
		try {
			con = DriverManager.getConnection("jdbc:mysql://academic-mysql.cc.gatech.edu/cs4400_Group_34", "cs4400_Group_34","9BkPSv5Z");
			stmt = con.createStatement();
			//stmt2 = con.createStatement();
			ResultSet info = stmt.executeQuery(query);
			ArrayList<Float> avg = new ArrayList<Float>();
			ArrayList<String> list = new ArrayList<String>();
			while(info.next()){
				String dname = "\"";
				dname = dname+ info.getString("Username");
				dname = dname + "\"";
				list.add(dname);
			}
			for(int i = 0; i<list.size(); i++){
				String dname = list.get(i);
				String query2 = "SELECT avg(Rating) AS Rating From rates WHERE UsernameOfDoctor = " + dname + ";";
				ResultSet info2 = stmt.executeQuery(query2);
				if(info2.next()){
					Float rates = info2.getFloat("Rating");
					avg.add(rates);
				}
			}
			double sum =0;
			for(int i = 0; i<avg.size(); i++){
				sum +=avg.get(i);
			}
			return (int) (sum/(int)avg.size());
		} catch (Exception e) {
			System.err.println("Exception :" + e.getMessage());
		} finally{
			try{	
				if(con!=null)
					con.close();
			} catch (SQLException e1) {}
		}
		return 0;
	}
	private class TrollListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			frame.getContentPane().removeAll();
			AdministrativeHomepage nur = new AdministrativeHomepage(frame, username);
			frame.add(nur);
			frame.repaint();
			frame.setVisible(true);
		}
	}
}
