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

public class Billing extends JPanel{
	// for messages just show in dialog box, too hard to show else
	private JButton back;
	private JFrame frame;
	private String username;
	private JLabel pans[][];
	private JTextField name;
	private JButton create;
	private Map<JButton, String> map;
	private ArrayList<Integer> phones;
	private ArrayList<Node> nodes;
	private boolean incomeDiscount;
	private Map<JButton, Boolean> incomes;
	private JPanel billPane;
	private float cst;
	public Billing(JFrame frame, String username){
		cst = 0f;
		incomes = new HashMap<JButton, Boolean>();
		incomeDiscount = false;
		nodes = new ArrayList<Node>();
		map = new HashMap<JButton, String>();
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
		txt = new JLabel("Patient Name :");
		pan.add(txt);
		name = new JTextField(20);
		pan.add(name);
		create = new JButton("Create Bill");
		create.addActionListener(new CreateListener());
		pan.add(create);
		add(pan, gcb);	
		billPane = new JPanel();
		gcb.gridy = 3;
		gcb.weighty = 18;
		/*pan.setLayout(new GridLayout(2,4));
		pans = new JLabel[2][4];
		for(int x = 0; x<pans.length; x++){
			for(int y =0; y<4; y++){
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
		pans[0][0].setText("Doctor Name");
		pans[0][1].setText("<html>No of Patients<br>Seen</html>");
		pans[0][2].setText("<html>No of<br>Prescriptions<br>Written</html>");
		pans[0][3].setText("Total Billing ($)");
		/*for(int x = 1; x<pans.length; x++){
			Noob tmp = list.get(x -1);
			pans[x][0].setText("Dr. " + tmp.getFname() + " " + tmp.getLname());
			pans[x][1].setText(tmp.getPats() + "");
			pans[x][2].setText(tmp.getPres() + "");
			float tot = tmp.getBill();
			pans[x][3].setText(String.format("%.2f", tot));
			System.out.println("asd");
		}*/
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
			AdministrativeHomepage nur = new AdministrativeHomepage(frame, username);
			frame.add(nur);
			frame.repaint();
			frame.setVisible(true);
		}
	}
	private class SelectListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			//System.out.println(map.get(e.getSource()));
			cst = 0f;
			String uname = "\"";
			uname = uname+ map.get(e.getSource());
			incomeDiscount = incomes.get(e.getSource());
			uname = uname + "\"";
			billPane.removeAll();
			billPane.revalidate();
			String query = "SELECT * FROM Visit WHERE UsernameOfPatient = " + uname +";";
			Connection con = null;
			Statement stmt;
			Statement stmt2;
			Statement stmt3;
			try {
				con = DriverManager.getConnection("jdbc:mysql://academic-mysql.cc.gatech.edu/cs4400_Group_34", "cs4400_Group_34","9BkPSv5Z");
				stmt = con.createStatement();
				stmt2 = con.createStatement();
				stmt3 = con.createStatement();
				ResultSet info = stmt.executeQuery(query);
				Node node = new Node("Visits", "Cost ($)");
				nodes.add(node);
				while(info.next()){
					nodes.add(new Node(info.getString("dateOfVisit"), String.format("%.2f", info.getFloat("billingAmount"))));
				}
				nodes.add(new Node("Surgery", ""));
				query = "SELECT * FROM Performs_Surgery WHERE UsernameOfPatient = " + uname +";";
				ResultSet rs = stmt2.executeQuery(query);
				while(rs.next()){
					float cpt = rs.getFloat("CPTCode");
					query = "SELECT * FROM Surgery;";
					ResultSet rs2 = stmt3.executeQuery(query);
					rs2.next();
					while(rs2.getFloat("CPTCode")!=cpt){
						rs2.next();
					}
					String surgery = rs2.getString("surgeryType");
					float cost = rs2.getFloat("CostOfSurgery");
					if(incomeDiscount){
						cost = cost*0.5f;
					}
					cst+=cost;
					nodes.add(new Node(surgery, String.format("%.2f", cost)));
				}
			} catch (Exception v) {
				System.err.println("Exception :" + v.getMessage());
			} finally{
				try{	
					if(con!=null)
						con.close();
				} catch (SQLException e1) {}
			}
			billPane.setLayout(new GridLayout(nodes.size()+1, 2));
			/*JLabel label[][] = new JLabel[nodes.size()][2];
			for(int i = 0; i<nodes.size(); i++){
				label[i][0] = new JLabel();
				billPane.add(label[i][0]);
				label[i][1] = new JLabel();
				billPane.add(label[i][1]);
			}*/
			for(int i = 0; i<nodes.size(); i++){
				JLabel label = new JLabel(nodes.get(i).getType());
				label.setBorder(BorderFactory.createLineBorder(Color.black));
				billPane.add(label);
				label = new JLabel(nodes.get(i).getCost());
				label.setBorder(BorderFactory.createLineBorder(Color.black));
				billPane.add(label);
			}
			JLabel label = new JLabel("Total Cost");
			label.setBorder(BorderFactory.createLineBorder(Color.black));
			billPane.add(label);
			label = new JLabel(String.format("%.2f", cst));
			label.setBorder(BorderFactory.createLineBorder(Color.black));
			billPane.add(label);	
			billPane.revalidate();
		}
	}
	private class CreateListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent v) {
			cst = 0f;
			nodes = new ArrayList<Node>();
			billPane.removeAll();
			billPane.revalidate();
			phones = new ArrayList<Integer>();
			incomes = new HashMap<JButton, Boolean>();
			//incomes = new ArrayList<Boolean>();
			//billPane.repaint();
			String patient = "\"";
			patient = patient + name.getText().toString().trim();
			patient = patient + "\"";
			String query = "SELECT * FROM Patient WHERE Name = " + patient +";";
			Connection con = null;
			Statement stmt;
			try {
				con = DriverManager.getConnection("jdbc:mysql://academic-mysql.cc.gatech.edu/cs4400_Group_34", "cs4400_Group_34","9BkPSv5Z");
				stmt = con.createStatement();
				ResultSet info = stmt.executeQuery(query);
				ArrayList<String> uname = new ArrayList<String>();
				ArrayList<Boolean> income = new ArrayList<Boolean>();
				while(info.next()){
					phones.add(info.getInt("HomePhone"));
					uname.add(info.getString("Username"));
					if(info.getString("AnnualIncome").equals("0-24,999")){
						income.add(true);
					}else{
						income.add(false);
					}
					//billPane.add(new JTextField(20));
					//System.out.println(info.getInt("HomePhone") + "");
					//billPane.revalidate();
				}
				billPane.setLayout(new GridLayout( phones.size()+1, 3));
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
				for(int i = 0; i<phones.size(); i++){
					JLabel label = new JLabel(name.getText().toString().trim());
					label.setBorder(BorderFactory.createLineBorder(Color.black));
					label.setHorizontalAlignment(SwingConstants.CENTER);
					billPane.add(label);
					label = new JLabel(phones.get(i)+"");
					label.setBorder(BorderFactory.createLineBorder(Color.black));
					label.setHorizontalAlignment(SwingConstants.CENTER);
					billPane.add(label);
					JButton button =  new JButton("Select");
					button.addActionListener(new SelectListener());
					billPane.add(button);
					button.setBorder(BorderFactory.createLineBorder(Color.black));
					map.put(button, uname.get(i));
					incomes.put(button, income.get(i));
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
	private class Node{
	private String type;
	private String cost;
		public Node(String type, String cost){
			this.setType(type);
			this.setCost(cost);
		}
		public String getType() {
			return type;
		}
		public void setType(String type) {
			this.type = type;
		}
		public String getCost() {
			return cost;
		}
		public void setCost(String cost) {
			this.cost = cost;
		}
	}
}
