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

public class SurgeriesPerformed extends JPanel{
	// for messages just show in dialog box, too hard to show else
	private JButton back;
	private JFrame frame;
	private String username;
	private JLabel pans[][];
	private ArrayList<Noob> list;
	private Map<String, Noob> map;
	public SurgeriesPerformed(JFrame frame, String username){
		list = new ArrayList<Noob>();
		map = new HashMap<String, Noob>();
		this.frame = frame; 
		this.username = username;
		GridBagConstraints gcb = new GridBagConstraints();
		setLayout(new GridBagLayout());
		JLabel txt = new JLabel("Surgeries Performed");
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
		getSurgeries();
		getDocs();
		pan.setLayout(new GridLayout(1+list.size(),5));
		pans = new JLabel[1+list.size()][5];
		for(int x = 0; x<pans.length; x++){
			for(int y =0; y<5; y++){
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
		pans[0][4].setBackground(Color.blue);
		pans[0][0].setText("Surgery Type");
		pans[0][1].setText("CPT code");
		pans[0][2].setText("No of Procedures");
		pans[0][3].setText("<html>No of Doctors<br>Performing the<br>Procedure</html>");
		pans[0][4].setText("Total Billing ($)");
		for(int x = 1; x<pans.length; x++){
			Noob tmp = list.get(x-1);
			pans[x][0].setText(tmp.getType());
			pans[x][1].setText(tmp.getCpt());
			int proc = tmp.getProc();
			pans[x][2].setText(proc + "");
			pans[x][3].setText(tmp.getDocs() + "");
			float tot = tmp.getBill();
			pans[x][4].setText(String.format("%.2f", tot));
			//pans[x][4].setText(String.format("%.2f", tmp.getCost()));
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
	}
	public void getDocs(){
		String query = "Select * FROM Performs_Surgery LEFT OUTER JOIN Patient ON Patient.Username = UsernameOfPatient WHERE 'startTime' > CURDATE( ) - INTERVAL 3 MONTH;";
		Connection con = null;
		Statement stmt;
		try {
			con = DriverManager.getConnection("jdbc:mysql://academic-mysql.cc.gatech.edu/cs4400_Group_34", "cs4400_Group_34","9BkPSv5Z");
			stmt = con.createStatement();
			ResultSet info = stmt.executeQuery(query);
			while(info.next()){
				String cpt = info.getString("CPTCode");
				String income = info.getString("AnnualIncome");
				Noob tmp = map.get(cpt);
				if("0-24,999".equals(income)){
					tmp.setBill(tmp.getBill() + tmp.getCost()*0.5f);
				}else{
					tmp.setBill(tmp.getBill() + tmp.getCost());
				}
				tmp.setProc(tmp.getProc()+1);
				String doc = info.getString("UsernameOfDoctor");
				if(!tmp.checkDocs(doc)){
					tmp.addDocs(doc);
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
	public void getSurgeries(){
		String query = "Select * FROM Surgery;";
		Connection con = null;
		Statement stmt;
		try {
			con = DriverManager.getConnection("jdbc:mysql://academic-mysql.cc.gatech.edu/cs4400_Group_34", "cs4400_Group_34","9BkPSv5Z");
			stmt = con.createStatement();
			ResultSet info = stmt.executeQuery(query);
			while(info.next()){
				Noob tmp = new Noob(info.getString("surgeryType"), info.getFloat("CostOfSurgery"), info.getString("CPTCode"));
				list.add(tmp);
				map.put(tmp.getCpt(), tmp);
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
		private String type;
		private float cost;
		private String cpt;
		private int proc;
		private float bill;
		private ArrayList<String> docs;
		public Noob(String type, float cost, String cpt){
			this.setType(type);
			this.setCost(cost);
			this.setCpt(cpt);
			setBill(0);
			setProc(0);
			docs = new ArrayList<String>();
		}
		public void addDocs(String var){
			docs.add(var);
		}
		public int getDocs(){
			return docs.size();
		}
		public boolean checkDocs(String var){
			return docs.contains(var);
		}
		public String getType() {
			return type;
		}
		public void setType(String type) {
			this.type = type;
		}
		public float getCost() {
			return cost;
		}
		public void setCost(float cost) {
			this.cost = cost;
		}
		public String getCpt() {
			return cpt;
		}
		public void setCpt(String cpt) {
			this.cpt = cpt;
		}
		public int getProc() {
			return proc;
		}
		public void setProc(int proc) {
			this.proc = proc;
		}
		public float getBill() {
			return bill;
		}
		public void setBill(float bill) {
			this.bill = bill;
		}
	}
}
