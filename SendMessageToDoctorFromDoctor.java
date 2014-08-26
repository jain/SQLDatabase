import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JFrame;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * NewJPanel.java
 *
 * Created on Mar 18, 2014, 12:24:44 AM
 */
/**
 *
 * @author vikram
 */
public class SendMessageToDoctorFromDoctor extends javax.swing.JPanel {
	private JFrame frame;
	private String username;
	private ArrayList<String> docs;
	/** Creates new form NewJPanel */
	public SendMessageToDoctorFromDoctor(JFrame frame, String username) {
		this.frame = frame;
		this.username = username;
		docs = new ArrayList<String>();
		initComponents();
		initialize();
	}
	public void initialize(){
		Connection con = null;
		Statement stmt;
		try {
			String query = "SELECT * From Doctor;";
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			con = DriverManager.getConnection("jdbc:mysql://academic-mysql.cc.gatech.edu/cs4400_Group_34", "cs4400_Group_34","9BkPSv5Z");
			stmt = con.createStatement();
			ResultSet info = stmt.executeQuery(query);
			while(info.next()){
				String tmp = info.getString("FirstName") + " " + info.getString("LastName");
				selector.addItem(tmp);
				docs.add(info.getString("Username"));
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
	/** This method is called from within the constructor to
	 * initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is
	 * always regenerated by the Form Editor.
	 */
	@SuppressWarnings("unchecked")
	// <editor-fold defaultstate="collapsed" desc="Generated Code">
	private void initComponents() {

		jPanel1 = new javax.swing.JPanel();
		submit = new javax.swing.JButton();
		submit.addActionListener(new SubmitListener());
		jScrollPane1 = new javax.swing.JScrollPane();
		message = new javax.swing.JTextArea();
		message.setColumns(20);
		message.setLineWrap(true);
		message.setRows(10);
		message.setWrapStyleWord(true);
		jLabel1 = new javax.swing.JLabel();
		jLabel2 = new javax.swing.JLabel();
		selector = new javax.swing.JComboBox();
		jPanel2 = new javax.swing.JPanel();
		jLabel3 = new javax.swing.JLabel();

		javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
		jPanel1.setLayout(jPanel1Layout);
		jPanel1Layout.setHorizontalGroup(
				jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGap(0, 100, Short.MAX_VALUE)
				);
		jPanel1Layout.setVerticalGroup(
				jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGap(0, 100, Short.MAX_VALUE)
				);
		setRequestFocusEnabled(false);

		submit.setText("Send Message");

		message.setColumns(20);
		message.setRows(5);
		jScrollPane1.setViewportView(message);

		jLabel1.setText("Message:");

		jLabel2.setText("Select Name:");

		jPanel2.setBackground(new java.awt.Color(-256,true));
		jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(-16777216,true)));

		jLabel3.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
		jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
		jLabel3.setText("Send Message To Doctor");

		javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
		jPanel2.setLayout(jPanel2Layout);
		jPanel2Layout.setHorizontalGroup(
				jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 579, Short.MAX_VALUE)
				);
		jPanel2Layout.setVerticalGroup(
				jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, 43, Short.MAX_VALUE)
				);

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
		this.setLayout(layout);
		layout.setHorizontalGroup(
				layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
				.addGroup(layout.createSequentialGroup()
						.addGap(84, 84, 84)
						.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
								.addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addGroup(layout.createSequentialGroup()
										.addGap(28, 28, 28)
										.addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
										.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
												.addComponent(selector, 0, 242, Short.MAX_VALUE)
												.addComponent(jScrollPane1))
												.addGap(148, 148, 148))
												.addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
														.addGap(455, 455, 455)
														.addComponent(submit, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
														.addContainerGap())
				);
		layout.setVerticalGroup(
				layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
						.addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addGap(45, 45, 45)
						.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
								.addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(selector))
								.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
								.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
										.addGroup(layout.createSequentialGroup()
												.addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 34, Short.MAX_VALUE)
												.addGap(109, 109, 109))
												.addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 143, Short.MAX_VALUE))
												.addGap(65, 65, 65)
												.addComponent(submit, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
												.addContainerGap())
				);

		getAccessibleContext().setAccessibleName("");
	}// </editor-fold>

	// Variables declaration - do not modify
	private javax.swing.JButton submit;
	private javax.swing.JComboBox selector;
	private javax.swing.JLabel jLabel1;
	private javax.swing.JLabel jLabel2;
	private javax.swing.JLabel jLabel3;
	private javax.swing.JPanel jPanel1;
	private javax.swing.JPanel jPanel2;
	private javax.swing.JScrollPane jScrollPane1;
	private javax.swing.JTextArea message;
	// End of variables declaration
	private class SubmitListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent v) {
			// TODO Auto-generated method stub
			int year = Calendar.getInstance().get(Calendar.YEAR);
			int month= Calendar.getInstance().get(Calendar.MONTH) + 1;
			int day = Calendar.getInstance().get(Calendar.DATE);
			String dateTime = "\"";
			dateTime = dateTime + year + "-" +month+ "-" + day + " " + Calendar.getInstance().getTime().toString().substring(11, 19);
			dateTime = dateTime + "\"";
			Connection con = null;
			Statement stmt;
			String dname = "\"";
			dname = dname+  docs.get(selector.getSelectedIndex());
			dname = dname + "\"";
			String mess = "\"";
			mess = mess+  message.getText().toString().trim();
			mess = mess + "\"";
			try {
				String query = "INSERT INTO Communicates_With(UsernameofDoctor, UsernameofDoctor2, Content, dateTime, Status)" + 
						"VALUES (" + dname + ", " +username +", " + mess +", " + dateTime +", 'Unread'"+ ")" + 
						"ON DUPLICATE KEY UPDATE Content = " + mess +", Status = 'Unread'" + ";";
				con = DriverManager.getConnection("jdbc:mysql://academic-mysql.cc.gatech.edu/cs4400_Group_34", "cs4400_Group_34","9BkPSv5Z");
				stmt = con.createStatement();
				stmt.executeUpdate(query);
				frame.getContentPane().removeAll();
				DoctorHomepage nur = new DoctorHomepage(frame, username);
				frame.add(nur);
				frame.repaint();
				frame.setVisible(true);
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
}
