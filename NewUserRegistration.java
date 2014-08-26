import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.BatchUpdateException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.*;
import javax.xml.bind.ValidationException;


public class NewUserRegistration extends JPanel{
	private JButton register;
	private JTextField usr;
	private JTextField pss;
	private JTextField confirmpss;
	private JComboBox<String> select;
	private JFrame frame;
	public NewUserRegistration(JFrame frame){
		this.frame = frame;
		GridBagConstraints gcb = new GridBagConstraints();
		setLayout(new GridBagLayout());
		JLabel txt = new JLabel("New User Registration");
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
		gcb.gridy = 2;
		gcb.weighty = 8;
		add(pan, gcb);
		pan = new JPanel();
		txt = new JLabel("Username:               ");
		pan.add(txt);
		usr = new JTextField("", 20);
		pan.add(usr);
		gcb.gridy = 3;
		gcb.weighty = 1;
		add(pan, gcb);
		pan = new JPanel();
		txt = new JLabel("Password:               ");
		pan.add(txt);
		pss = new JTextField("", 20);
		pan.add(pss);
		gcb.gridy = 4;
		gcb.weighty = 1;
		add(pan, gcb);
		pan = new JPanel();
		txt = new JLabel("Confirm Password:");
		pan.add(txt);
		confirmpss = new JTextField("", 20);
		pan.add(confirmpss);
		gcb.gridy = 5;
		gcb.weighty = 1;
		add(pan, gcb);
		pan = new JPanel();
		txt = new JLabel("Type of User:          ");
		pan.add(txt);
		select = new JComboBox<String>();
		select.addItem("Doctor");
		select.addItem("Patient");
		select.addItem("Administrative Personnel    "); // if admin remove 4 spaces
		pan.add(select);
		gcb.gridy = 6;
		gcb.weighty = 1;
		add(pan, gcb);
		pan = new JPanel();
		pan.setLayout(new GridBagLayout());
		GridBagConstraints gcb2 = new GridBagConstraints();
		gcb2.gridy = 0;
		gcb2.gridx = 0;
		gcb2.weighty = 1;
		gcb2.weightx = 1;
		register = new JButton("Register");
		JPanel buttons = new JPanel();
		buttons.add(register);
		pan.add(buttons, gcb2);
		gcb.gridy = 7;
		gcb.weighty = 8;
		add(pan, gcb);
		register.addActionListener(new RegisterListener());
	}
	private class RegisterListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e){
			// TODO Auto-generated method stub
			if(!pss.getText().toString().equals(confirmpss.getText().toString())){
				pss.setText("");
				confirmpss.setText("");
				JOptionPane.showMessageDialog(NewUserRegistration.this,
		            	"PLease Ensure passwords match");
				return;
			}
			String uname = "\"";
			uname = uname+ usr.getText().toString();
			uname = uname + "\"";
			String query = "SELECT Username from User WHERE Username = " + uname +";";
			Connection con = null;
			Statement stmt;
			String psw = "\"";
			psw = psw + pss.getText().toString();
			psw = psw + "\"";
			String type = "\"";
			type = type + select.getSelectedItem().toString().trim();
			type = type + "\"";
			ResultSet info;
			try {
				Class.forName("com.mysql.jdbc.Driver").newInstance();
				con = DriverManager.getConnection("jdbc:mysql://academic-mysql.cc.gatech.edu/cs4400_Group_34", "cs4400_Group_34","9BkPSv5Z");
				stmt = con.createStatement();
				info = stmt.executeQuery(query);
				if(info.next()){
					JOptionPane.showMessageDialog(NewUserRegistration.this,
			            	"The Entered Username Already exists");
					usr.setText("");
					return;
				}
				query = "INSERT INTO User (Username, Password, Type) Values (" + uname + "," + psw + "," + type +");";
				stmt.executeUpdate(query);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				System.err.println("Exception :" + e1.getMessage());
			} finally{
				try{	
					if(con!=null)
						con.close();
				} catch (SQLException e1) {}
			}
			frame.getContentPane().removeAll();
			Login log = new Login(frame);
			frame.add(log);
			frame.repaint();
			frame.setVisible(true);
		}
	}
}
