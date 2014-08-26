import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.regex.Pattern;

import javax.swing.*;
// edit depending on data input

public class ScheduleAapointments extends JPanel{
	// for messages just show in dialog box, too hard to show else
	private JButton back;
	private JFrame frame;
	private String username;
	private JButton create;
	private ArrayList<Noob> nodes;
	private JPanel billPane;
	private JComboBox<String> specialty;
	private Map<JCheckBox, Noob> map;
	private JButton request;
	public ScheduleAapointments(JFrame frame, String username){
		map = new HashMap<JCheckBox, Noob>();
		specialty = new JComboBox<String>();
		generate();
		nodes = new ArrayList<Noob>();
		this.frame = frame; 
		this.username = username;
		GridBagConstraints gcb = new GridBagConstraints();
		setLayout(new GridBagLayout());
		JLabel txt = new JLabel("Schedule Appointments with doctors");
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
		txt = new JLabel("Specialty :");
		pan.add(txt);
		pan.add(specialty);
		create = new JButton("Search");
		create.addActionListener(new SearchListener());
		pan.add(create);
		add(pan, gcb);	
		billPane = new JPanel();
		gcb.gridy = 3;
		gcb.weighty = 18;
		//pan.setBorder(BorderFactory.createLineBorder(Color.black));
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
		request = new JButton("Schedule Appointments");
		request.addActionListener(new SelectListener());
		buttons.add(request);
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
			PatientHome nur = new PatientHome(frame, username);
			frame.add(nur);
			frame.repaint();
			frame.setVisible(true);
		}
	}
	private class SelectListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			Noob noob;
			if(map.size()==0){
				return;
			}
			for(JCheckBox key:map.keySet()){
				if(key.isSelected()){
					noob = map.get(key);
					String time = noob.getAvail();
					String[] tokens = time.split(Pattern.quote(":"));
					String a = tokens[1].trim();
					a = a+ ":"+tokens[2] + ":" + tokens[3].substring(0,2).trim();
					String need = noob.getAvail().substring(0, 3);
					Date date=Calendar.getInstance().getTime();
					date.setDate(date.getDate()+1);
					while(!date.toString().substring(0,3).equals(need)){
						date.setDate(date.getDate()+1);
					}
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
					String dt = sdf.format(date);
					String uname = "\"";
					uname = uname+ noob.getUsername();
					uname = uname + "\"";
					String query = "INSERT INTO Request_Appointment (UsernameOfDoctor, UsernameOfPatient, date, scheduledTime) " +
							"VALUES ("+uname+", "+username +", '"+ dt + "', '" + a+"');";
					Connection con = null;
					Statement stmt;
					try {
						con = DriverManager.getConnection("jdbc:mysql://academic-mysql.cc.gatech.edu/cs4400_Group_34", "cs4400_Group_34","9BkPSv5Z");
						stmt = con.createStatement();
						stmt.executeUpdate(query);
					}catch (SQLException v){
						JOptionPane.showMessageDialog(frame, "You already scheduled an appointment with " + noob.getName() );
						System.out.println("asd");
					}
					finally{
						try{	
							if(con!=null)
								con.close();
						} catch (SQLException e1) {}
					}
				}
			}
		}
	}
	private class SearchListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent v) {
			map = new HashMap<JCheckBox, Noob>();
			billPane.removeAll();
			nodes = new ArrayList<Noob>();
			String query = "SELECT Doctor.Username, FirstName, LastName, WorkPhone, RoomNumber, " +
					"Begin, Day, End, x.Rating AS Rating from Doctor " +
					"LEFT JOIN(SELECT rates.UsernameOfDoctor, avg(Rating)" +
					" AS Rating From rates " +
					"GROUP BY rates.UsernameOfDoctor" +
					") AS x " +
					"ON x.UsernameOfDoctor = Doctor.Username " +
					"LEFT JOIN Doctor_Availability " +
					"ON Doctor_Availability.Username = Doctor.Username " +
					"WHERE Doctor.Specialty = '" + specialty.getSelectedItem() + "';";
			Connection con = null;
			Statement stmt;
			try {
				con = DriverManager.getConnection("jdbc:mysql://academic-mysql.cc.gatech.edu/cs4400_Group_34", "cs4400_Group_34","9BkPSv5Z");
				stmt = con.createStatement();
				ResultSet info = stmt.executeQuery(query);
				nodes.add(new Noob("troll", "Doctor Name", "Phone Number", "Room Number", "Availability", "Average Rating"));
				while(info.next()){
					String doc = "Dr. " + info.getString("FirstName");
					doc = doc + " " + info.getString("LastName");
						String phone = info.getString("WorkPhone");
					String rm = info.getString("RoomNumber");
					String avail = info.getString("Day");
					if(avail==null){
						continue;
					}
					avail  = avail + ": ";
					avail = avail + info.getString("Begin");
					avail = avail + " - " + info.getString("End");
					String rating = info.getString("Rating");
					String uname = info.getString("Username");
					Noob node = new Noob(uname, doc, phone, rm, avail, rating);
					nodes.add(node);
				}
				billPane.setLayout(new GridLayout(nodes.size(), 5));
				for(int i = 0; i<nodes.size(); i++){
					JLabel lbl = new JLabel(nodes.get(i).getName());
					lbl.setBorder(BorderFactory.createLineBorder(Color.black));
					lbl.setHorizontalAlignment(SwingConstants.CENTER);
					billPane.add(lbl);
					lbl = new JLabel(nodes.get(i).getPhone());
					lbl.setHorizontalAlignment(SwingConstants.CENTER);
					lbl.setBorder(BorderFactory.createLineBorder(Color.black));
					billPane.add(lbl);
					lbl = new JLabel(nodes.get(i).getRm());
					lbl.setHorizontalAlignment(SwingConstants.CENTER);
					lbl.setBorder(BorderFactory.createLineBorder(Color.black));
					billPane.add(lbl);
					if(i!=0){
						JPanel tmp = new JPanel();
						tmp.setLayout(new BorderLayout());
						JCheckBox box = new JCheckBox(nodes.get(i).getAvail());
						tmp.setBorder(BorderFactory.createLineBorder(Color.black));
						tmp.add(box, BorderLayout.CENTER);
						billPane.add(tmp);
						map.put(box, nodes.get(i));
					}else{
						lbl = new JLabel(nodes.get(i).getAvail());
						lbl.setBorder(BorderFactory.createLineBorder(Color.black));
						lbl.setHorizontalAlignment(SwingConstants.CENTER);
						billPane.add(lbl);
					}
					lbl = new JLabel(nodes.get(i).getRating());
					lbl.setHorizontalAlignment(SwingConstants.CENTER);
					lbl.setBorder(BorderFactory.createLineBorder(Color.black));
					billPane.add(lbl);
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
	}
	private class Noob{
		private String name;
		private String phone;
		private String rm;
		private String avail;
		private String rating;
		private String username;
		public Noob(String username, String name, String phone, String rm, String avail, String rating){
			setName(name);
			setPhone(phone);
			setRm(rm);
			setAvail(avail);
			setRating(rating);
			setUsername(username);
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		/**
		 * @return the phone
		 */
		public String getPhone() {
			return phone;
		}
		/**
		 * @param phone the phone to set
		 */
		public void setPhone(String phone) {
			this.phone = phone;
		}
		/**
		 * @return the rm
		 */
		public String getRm() {
			return rm;
		}
		/**
		 * @param rm the rm to set
		 */
		public void setRm(String rm) {
			this.rm = rm;
		}
		/**
		 * @return the avail
		 */
		public String getAvail() {
			return avail;
		}
		/**
		 * @param avail the avail to set
		 */
		public void setAvail(String avail) {
			this.avail = avail;
		}
		/**
		 * @return the rating
		 */
		public String getRating() {
			return rating;
		}
		/**
		 * @param rating the rating to set
		 */
		public void setRating(String rating) {
			this.rating = rating;
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
	}
	public void generate(){
		specialty.addItem("General Physician");
		specialty.addItem("Heart Specialist");
		specialty.addItem("Eye Physician");
		specialty.addItem("Orthopedics");
		specialty.addItem("Psychiatry");
		specialty.addItem("Gynecologist");
	}
}
