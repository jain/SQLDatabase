import java.awt.Dimension;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import java.util.regex.Pattern;

import javax.swing.JFrame;
import javax.swing.JPanel;


public class GuiTester {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		/*JFrame frame = new JFrame("Login");
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(new Dimension( 640, 480 ));
		//frame.add(new Login(frame));
		//JPanel nur = new NewUserRegistration();
		//frame.add(nur);
		//frame.add(new PatientProfile(frame, ""));
		//frame.add(new DoctorProfile());
		//frame.add(new RateADoctor());
		//frame.add(new SendMessageToDoctor(frame));
		//frame.add(new DoctorSendsMessage(frame));
		//frame.add(new Inbox(frame));
		//frame.add(new DoctorPerformanceReport(frame));
		//frame.add(new SurgeriesPerformed(frame));
		//frame.add(new ApptsCalendar(frame));
		//frame.add(new RecordAVisit(frame));
		//frame.add(new PatientHome(frame));s
		//frame.setVisible(true);
		/*int year = Calendar.getInstance().get(Calendar.YEAR);
		int month= Calendar.getInstance().get(Calendar.MONTH)+1;
		int day = Calendar.getInstance().get(Calendar.DATE);
		String dateTime = "\"";
		dateTime = dateTime + year + "-" +month+ "-" + day + " " + Calendar.getInstance().getTime().toString().substring(11, 19);
		dateTime = dateTime + "\"";
		System.out.println(dateTime);*/
		/*String date = "2013-04-24 02:58:58";
		int year = Integer.parseInt(date.substring(0,4));
		int month = Integer.parseInt(date.substring(5, 7));
		System.out.println(year + "," + month);*/
		/*String tmp = "Monday: 05:00:00 - 06:00:00";
		String[] tokens = tmp.split(Pattern.quote(":"));
		System.out.println(tokens[0]);
		String a = tokens[1].trim();
		a = a+ ":"+tokens[2] + ":" + tokens[3].substring(0,2).trim();
		System.out.println(a);
		Calendar localCalendar = Calendar.getInstance(TimeZone.getDefault());
		Date currentTime = localCalendar.getTime();
		int currentDay = localCalendar.get(Calendar.DATE);
		int currentMonth = localCalendar.get(Calendar.MONTH) + 1;
		int currentYear = localCalendar.get(Calendar.YEAR);
		int currentDayOfWeek = localCalendar.get(Calendar.DAY_OF_WEEK);
		int currentDayOfMonth = localCalendar.get(Calendar.DAY_OF_MONTH);
		int CurrentDayOfYear = localCalendar.get(Calendar.DAY_OF_YEAR);

		System.out.println("Current Date and time details in local timezone");
		System.out.println("Current Date: " + currentTime);
		System.out.println("Current Day: " + currentDay);
		System.out.println("Current Month: " + currentMonth);
		System.out.println("Current Year: " + currentYear);
		System.out.println("Current Day of Week: " + currentDayOfWeek);
		System.out.println("Current Day of Month: " + currentDayOfMonth);
		System.out.println("Current Day of Year: " + CurrentDayOfYear);
		switch (tokens[0]){
		case "Monday":
			break;
		case "Tuesday":
			break;
		case "Wednesday":
			break;
		case "Thursday":
			break;
		case "Friday":
			break;
		case "Saturday":
			break;
		case "Sunday":
			break;
		}
		Date date=Calendar.getInstance().getTime();
		while(!date.toString().substring(0,3).equals("Mon")){
			date.setDate(date.getDate()+1);
		}
		System.out.println(date);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String dt = sdf.format(date);
		System.out.println(dt);*/
		//System.out.println(Integer.parseInt("1"));
		//Date date=Calendar.getInstance().getTime();
		//System.out.println("2014-04-25 00:50:55".substring(0, 10));
	}

}
