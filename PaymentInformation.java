import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.swing.*;


public class PaymentInformation extends JPanel{
	private JButton submit;
	private JButton exist;
	private JTextField name;
	private JTextField cardnum;
	private JTextField type;
	private JTextField cvv;
	private JTextField expiry;
	private JFrame frame;
	private String username;
	public PaymentInformation(JFrame frame, String username){
		this.frame = frame;
		this.username = username;
		GridBagConstraints gcb = new GridBagConstraints();
		setLayout(new GridBagLayout());
		JLabel txt = new JLabel("Order Medication From Pharmacy");
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
		txt = new JLabel("Card Holder's Name:");
		pan.add(txt);
		name = new JTextField(20);
		pan.add(name);
		gcb.gridy = 2;
		gcb.gridx = 0;
		add(pan, gcb);
		pan = new JPanel();
		txt = new JLabel("Card Number: ");
		pan.add(txt);
		cardnum = new JTextField(20);
		pan.add(cardnum);
		add(pan, gcb);
		gcb.gridy = 3;
		gcb.weighty = 4.4;
		add(pan, gcb);
		pan = new JPanel();
		txt = new JLabel("Type of Card: ");
		pan.add(txt);
		type = new JTextField(20);
		pan.add(type);
		gcb.gridy = 4;
		gcb.weighty = 4.4;
		add(pan, gcb);
		pan = new JPanel();
		txt = new JLabel("CVV: ");
		pan.add(txt);
		cvv = new JTextField(20);
		pan.add(cvv);
		gcb.gridy = 5;
		gcb.weighty = 4.4;
		add(pan, gcb);
		pan = new JPanel();
		txt = new JLabel("Date Of Expiry YYYY-MM-DD: ");
		pan.add(txt);
		expiry = new JTextField(20);
		pan.add(expiry);
		gcb.gridy = 6;
		add(pan, gcb);
		pan = new JPanel();
		JPanel tmp = new JPanel();
		submit = new JButton("Order");
		submit.addActionListener(new OrderListener());
		tmp.add(submit);
		exist = new JButton("Use Saved Card");
		exist.addActionListener(new ExistListener());
		tmp.add(exist);
		gcb.gridy = 8;
		add(tmp, gcb);
	}
	private class ExistListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {	
			Statement stmt;
			Connection con = null;
			try {
				Class.forName("com.mysql.jdbc.Driver").newInstance();
				con = DriverManager.getConnection("jdbc:mysql://academic-mysql.cc.gatech.edu/cs4400_Group_34", "cs4400_Group_34","9BkPSv5Z");
				stmt = con.createStatement();
				String query = "SELECT * From Payment_Information WHERE UsernameOfPatient = " +username +";";
				ResultSet info = stmt.executeQuery(query);
				if(info.next()){
					change();
					return;
				}
				JOptionPane.showMessageDialog(frame, "You do not have a saved Card");
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				System.err.println(e1.getMessage());
			} finally{
				try{	
					if(con!=null)
						con.close();
				} catch (SQLException e1) {}
			}
		}
	}
	private class OrderListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {	
			String name2 = "\"";
			name2 = name2 + name.getText().toString();
			name2 = name2+ "\"";
			String card = cardnum.getText().toString().trim();
			String type2 = "\"";
			type2 = type2 + type.getText().toString().trim();
			type2 = type2 + "\"";
			String cvv2 = cvv.getText().toString().trim();
			String expiry2 = "'" + expiry.getText().toString().trim() + "'";
			Statement stmt;
			Connection con = null;
			try {
				Class.forName("com.mysql.jdbc.Driver").newInstance();
				con = DriverManager.getConnection("jdbc:mysql://academic-mysql.cc.gatech.edu/cs4400_Group_34", "cs4400_Group_34","9BkPSv5Z");
				stmt = con.createStatement();
				String query = "INSERT INTO Payment_Information (cardNumber, cardType, cvv, cardExpirationDate, cardHolderName, UsernameOfPatient) " +
						"VALUES (" + card + ", " + type2+", " + cvv2 +", " + expiry2 + ", " + name2 + ", " + username+");";

				stmt.executeUpdate(query);
				System.out.println("success");
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				JOptionPane.showMessageDialog(frame, "This Card already Exists OR Please Enter Valid Inputs!!!");
			} finally{
				try{	
					if(con!=null)
						con.close();
				} catch (SQLException e1) {}
			}
		}
	}
	public void change(){
		frame.getContentPane().removeAll();
		PatientHome nur = new PatientHome(frame, username);
		frame.add(nur);
		frame.repaint();
		frame.setVisible(true);
	}
}
