import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.time.LocalDate;


import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Main {

	public static void main(String[] args) {
		CovidDB myDb=new CovidDB();
		LocalDate[] dates= new LocalDate[2];
		String[] manus= new String[2];
		dates[0]=LocalDate.of(2000, 1, 5);
		dates[1]=LocalDate.of(2000, 1, 5);
		manus[0]="1";
		manus[1]="3";
		
		
		
		Member m=new Member("haya", "jacobi", "683156130", "jer", "ramot", 31,  LocalDate.of(2000, 1, 5), "1111111", "1111111111", null, dates, manus,LocalDate.of(2000, 1, 5), LocalDate.of(2000, 1, 5) );
		myDb.addMember(m);
		myDb.getAllMembers();
		myDb.getMember("984116140");
	}

}
