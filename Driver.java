import java.awt.Dimension;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.DriverManager; 

import javax.swing.JFrame;


public class Driver {

	/**
	 * @param args
	 */
	// ssh  vjain40@academic-mysql.cc.gatech.edu
	//  mysql -u cs4400_Group_34 -p
//	Username: cs4400_Group_34
//	Password:	9BkPSv5Z
	//create Table User(Username varchar(255) NOT NULL, Password varchar(255), Type varchar(255), Primary key (Username));
//create Table Patient(Username varchar(255) primary key, Annual_Income int, Name varchar(255), Home_Phone int, DateofBirth int, Gender ENUM('male', 'female'), Address varchar(255), Work_Phone int, Contact_Name varchar(255), Weight int, ContactPhone int, Height int, foreign key (Username) references User (Username), CONSTRAINT NamePhone UNIQUE (Name, Home_Phone));
	//CREATE TABLE Doctor(Username varchar(255) primary key, FirstName varchar(255), LastName varchar(255), DateOfBirth int, WorkPhone int, Specialty varchar(255), RoomNumber int, HomeAddress varchar(255), foreign key (Username) references User (Username));

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Connection con = null;
		/*try{
			Class.forName("com.mysql.jdbc.Driver").newInstance();
		} catch (Exception e){
			System.err.println("Exception :" + e.getMessage());
		}*/
		try{
			con = DriverManager.getConnection("jdbc:mysql://academic-mysql.cc.gatech.edu/cs4400_Group_34", "cs4400_Group_34","9BkPSv5Z");
			JFrame frame = new JFrame("GTMRS");
			frame.pack();
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			//frame.setExtendedState(Frame.MAXIMIZED_BOTH);
			//frame.setSize(700, 500);
			//Statement stmt = con.createStatement();
			//String query = "INSERT INTO test (usr, pass, troll) Values (10, 4, \"vik\");";
			//stmt.executeUpdate(query);
			//executeQuery only for select
			Login log = new Login(frame);
			frame.add(log);
			frame.setSize(new Dimension( 640, 480 ));
			frame.setVisible(true);
		} catch (Exception e){
			System.err.println("Exception: " + e.getMessage());
		} finally{
			try{	
				if(con!=null)
					con.close();
			} catch (SQLException e) {}
		}		
	}

}
