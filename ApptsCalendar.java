import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.swing.*;
// edit depending on data input

public class ApptsCalendar extends JPanel{
	// for messages just show in dialog box, too hard to show else
	private JButton back;
	private JFrame frame;
	private JComboBox<Integer> day;
	private JComboBox<String> month;
	private JComboBox<Integer> year;
	private JPanel pans[][];
	private JPanel cal;
	private JLabel pans2[][];
	private String uname;
	public ApptsCalendar(JFrame frame, String uname){
		this.uname = uname;
		this.frame = frame; 
		GridBagConstraints gcb = new GridBagConstraints();
		setLayout(new GridBagLayout());
		JLabel txt = new JLabel("Appointments Calendar");
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
		gcb.weighty = 1;
		day = new JComboBox<Integer>();
		month = new JComboBox<String>();
		year = new JComboBox<Integer>();
		combo();
		month.addActionListener(new MonthListener());
		year.addActionListener(new MonthListener());
		pan.add(new JLabel("Select Date:"));
		pan.add(day);
		pan.add(month);
		pan.add(year);
		add(pan, gcb);
		cal = new JPanel();
		cal.setLayout(new GridLayout(5,7));
		cal.setBorder(BorderFactory.createLineBorder(Color.black));
		pans = new JPanel[5][7];
		pans2 = new JLabel[5][7];
		for(int j = 0; j<5; j++){
			for(int i = 0; i<7; i++){ 
				pans[j][i] = new JPanel();
				pans2[j][i] = new JLabel();
				pans[j][i].add(pans2[j][i]);
				pans[j][i].setBorder(BorderFactory.createLineBorder(Color.black));
				cal.add(pans[j][i]);
			}
		}
		gcb.gridy = 2;
		gcb.weighty = 17;
		generate(31);
		add(cal, gcb);
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
		gcb.gridy = 3;
		gcb.weighty = 4;
		add(pan, gcb);
		back.addActionListener(new LoginListener());
	}
	public void combo(){
		for(int i = 0; i<500; i++){
			year.addItem(2014-i);
		}
		month.addItem("January");
		month.addItem("February");
		month.addItem("March");
		month.addItem("April");
		month.addItem("May");
		month.addItem("June");
		month.addItem("July");
		month.addItem("August");
		month.addItem("September");
		month.addItem("October");
		month.addItem("November");
		month.addItem("December");
		for(int i = 1; i<32; i++){
			day.addItem(i);
		}
	}
	private class LoginListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
		}
	}
	public void generate(int count){
		int num = 0;
		for(int j = 0; j<5; j++){
			for(int i = 0; i<7; i++){ 
				if(num<count){
					num++;
					pans2[j][i].setText("" + num);
				}else{
					pans2[j][i].setText("");
				}
			}}

	}
	private class MonthListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent arg0) {
			// TODO Auto-generated method stub
			Calendar calendar = Calendar.getInstance();
			calendar.set(Calendar.YEAR, (int)year.getSelectedItem());
			calendar.set(Calendar.MONTH,month.getSelectedIndex());
			int days = calendar.getActualMaximum(Calendar.DATE);
			for(int i = 1; i<days+1; i++){
				day.addItem(i);
			}
			generate(days);
		}

	}
}
