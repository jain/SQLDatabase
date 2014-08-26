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


public class InboxDoctorDoctor extends JPanel{
	// for messages just show in dialog box, too hard to show else
	private JButton back;
	private JFrame frame;
	private String username;
	private JPanel inbox;
	//private Map<Map<String, String>, JComboBox> status;
	private ArrayList<Noob> status;
	public InboxDoctorDoctor(JFrame frame, String username){
		this.username = username;
		this.frame = frame; 
		//status = new HashMap<Map<String, String>, JComboBox>();
		status = new ArrayList<Noob>();
		GridBagConstraints gcb = new GridBagConstraints();
		setLayout(new GridBagLayout());
		JLabel txt = new JLabel("Inbox");
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
		inbox = new JPanel();
		generate();
		gcb.gridy = 1;
		gcb.weighty = 18;
		int rows = messages() + 1;
		inbox.setLayout(new GridLayout(rows,4));
		add(inbox, gcb);
		JPanel pan = new JPanel();
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
		back.addActionListener(new BackListener());
	}
	public void generate(){
		JLabel txt = new JLabel("Status");
		txt.setBorder(BorderFactory.createLineBorder(Color.black));
		txt.setHorizontalAlignment(SwingConstants.CENTER);
		inbox.add(txt);
		txt = new JLabel("Date Time");
		txt.setBorder(BorderFactory.createLineBorder(Color.black));
		txt.setHorizontalAlignment(SwingConstants.CENTER);
		inbox.add(txt);
		txt = new JLabel("From");
		txt.setBorder(BorderFactory.createLineBorder(Color.black));
		txt.setHorizontalAlignment(SwingConstants.CENTER);
		inbox.add(txt);
		txt = new JLabel("Message");
		txt.setBorder(BorderFactory.createLineBorder(Color.black));
		txt.setHorizontalAlignment(SwingConstants.CENTER);
		inbox.add(txt);
		Connection con = null;
		Statement stmt;
		try {
			String query = "SELECT * FROM Communicates_With " + 
		"LEFT OUTER JOIN Doctor ON Doctor.Username = Communicates_With.UsernameOfDoctor2 " +
					"WHERE UsernameOfDoctor = " + username + 
					" ORDER BY DATETIME;";
			con = DriverManager.getConnection("jdbc:mysql://academic-mysql.cc.gatech.edu/cs4400_Group_34", "cs4400_Group_34","9BkPSv5Z");
			stmt = con.createStatement();
			ResultSet info = stmt.executeQuery(query);
			while(info.next()){
				String stat = info.getString("status");
				JComboBox<String> st = new JComboBox<String>();
				st.addItem("Read");
				st.addItem("Unread");
				Noob noo = new Noob(info.getString("UsernameofDoctor2"), info.getString("dateTime"), st);
				status.add(noo);
				if(stat.equals("Read")){
					st.setSelectedIndex(0);
				}else{
					st.setSelectedIndex(1);
				}
				inbox.add(st);
				txt = new JLabel(info.getString("dateTime"));
				txt.setBorder(BorderFactory.createLineBorder(Color.black));
				txt.setHorizontalAlignment(SwingConstants.CENTER);
				inbox.add(txt);
				txt = new JLabel(info.getString("FirstName")+ " " + info.getString("LastName"));
				txt.setBorder(BorderFactory.createLineBorder(Color.black));
				txt.setHorizontalAlignment(SwingConstants.CENTER);
				inbox.add(txt);
				txt = new JLabel(info.getString("content"));
				txt.setBorder(BorderFactory.createLineBorder(Color.black));
				txt.setHorizontalAlignment(SwingConstants.CENTER);
				inbox.add(txt);
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
	public int messages(){
		Connection con = null;
		Statement stmt;
		try {
			String query = "SELECT Count(status) AS Count FROM Communicates_With " +
					"WHERE UsernameofDoctor = " + username + ";";
			con = DriverManager.getConnection("jdbc:mysql://academic-mysql.cc.gatech.edu/cs4400_Group_34", "cs4400_Group_34","9BkPSv5Z");
			stmt = con.createStatement();
			ResultSet info = stmt.executeQuery(query);
			info.next();
			return (Integer.parseInt(info.getString("Count")));
		} catch (Exception e) {
			System.err.println("Exception :" + e.getMessage());
		} finally{
			try{	
				if(con!=null)
					con.close();
			} catch (SQLException e1) {}
		}
		return -1;
	}
	private class BackListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent v) {
			// TODO Auto-generated method stub
			Connection con = null;
			Statement stmt;
			try {
				for(int i = 0; i<status.size(); i++){
					Noob noo = status.get(i);
					String query = "Update Communicates_With " + 
							"SET status = '" + noo.getTroll().getSelectedItem().toString() + "' " + 
					"WHERE UsernameofDoctor2 = " + "\"" + noo.getPat() + "\""+ 						
							" AND UsernameOfDoctor = " + username + 
							" AND dateTime = " + "\""+ noo.getDateTime() + "\""+ ";";

					con = DriverManager.getConnection("jdbc:mysql://academic-mysql.cc.gatech.edu/cs4400_Group_34", "cs4400_Group_34","9BkPSv5Z");
					stmt = con.createStatement();
					stmt.executeUpdate(query);
				}
			} catch (Exception e) {
				System.err.println("Exception :" + e.getMessage());
			} finally{
				try{	
					if(con!=null)
						con.close();
				} catch (SQLException e1) {}
			}
			frame.getContentPane().removeAll();
			DoctorHomepage nur = new DoctorHomepage(frame, username);
			frame.add(nur);
			frame.repaint();
			frame.setVisible(true);
		}
	}
	private class Noob{
		private String pat;
		private String dateTime;
		private JComboBox<String> troll;
		
		public Noob(String pat, String dateTime, JComboBox<String> troll){
			this.setTroll(troll);
			this.setPat(pat);
			this.setDateTime(dateTime);
		}
		public String getDateTime() {
			return dateTime;
		}

		public void setDateTime(String dateTime) {
			this.dateTime = dateTime;
		}

		public JComboBox<String> getTroll() {
			return troll;
		}

		public void setTroll(JComboBox<String> troll) {
			this.troll = troll;
		}

		public String getPat() {
			return pat;
		}

		public void setPat(String pat) {
			this.pat = pat;
		}
	}
}
