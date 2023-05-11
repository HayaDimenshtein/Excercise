
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.time.LocalDate;
import java.util.ArrayList;
public class CovidDB {
	private Connection con;
	private Statement stmt;
	private PreparedStatement ps;
		public CovidDB()
		{
			try{ 
			Class.forName("com.mysql.jdbc.Driver");  
			con=DriverManager.getConnection(  
					"jdbc:mysql://localhost:3306/CovidDB","root","pass"); 
			stmt=con.createStatement(); 
			}catch(Exception e){ System.out.println(e);}  
		}
		
			
		 
		  
		void addMember(Member m){			
			LocalDate currentDate = LocalDate.now();
			  boolean vaccineDatesValid = true;
			try {
				// Check if the date of birth is not after the current date
		        if (m.getDateOfBirth().isBefore(currentDate)||m.getDateOfBirth().equals(currentDate))
		        {
		        	ps=con.prepareStatement("INSERT INTO mem (first_name, last_name, ID, city, street, house_number, "
						+ "date_of_birth, phone_number, cell_phone_number, photo) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
		        	ps.setString(1, m.getFirstName());
		        	ps.setString(2, m.getLastName());
		        	ps.setString(3, m.getId());
		        	ps.setString(4, m.getCity());
		        	ps.setString(5, m.getStreet());
		        	ps.setInt(6, m.getHouseNumber());
		        	ps.setDate(7,  java.sql.Date.valueOf(m.getDateOfBirth()));
		        	ps.setString(8, m.getPhoneNumber());
		        	ps.setString(9, m.getCellPhoneNumber());				
		        	try {
		        		if(m.getPhoto()!=null)
		        			ps.setBinaryStream(10, m.getPhoto(), m.getPhoto().available());
		        		else 
		        		    ps.setNull(10, Types.BLOB); 
		        		
		        	} catch (IOException e) {
		        		e.printStackTrace();
		        	}  
		        	ps.executeUpdate();
		        }
		        else {
		                System.out.println("Error: Date of birth should not be after the current date.");
		        }
                for (int i = 1; i < m.getVaccineDates().length; i++) {
                    if (m.getVaccineDates()[i].isBefore(m.getVaccineDates()[i - 1])) {
                        vaccineDatesValid = false;
                        break;
                    }
                }
		        // Check if the number of vaccines is not bigger than 4 and that every vaccine is after the previous one
		        if(m.getVaccineDates().length <= 4 && vaccineDatesValid) {

		        	for(int i=0; i<m.getVaccineDates().length; i++)
		        	{
		        		ps=con.prepareStatement("INSERT INTO mem_vaccine_info (ID, vaccine_num, vaccine_date, manufacturer) VALUES (?, ?, ?, ?)");
		        		ps.setString(1,m.getId() );
		        		ps.setInt(2, i+1 );
		        		ps.setDate(3, java.sql.Date.valueOf(m.getVaccineDates()[i]));
		        		ps.setString(4, m.getManufacturers()[i]);
		        		ps.executeUpdate();
		        	}
		        }
		        else 
		            System.out.println("Error: num of vaccines should not exceed 4.");
				if (m.getPositiveTestDate() != null && m.getRecoveryDate() != null) {
		            // Check if positive test date is earlier than recovery date
		            if (m.getPositiveTestDate().isBefore(m.getRecoveryDate())) {
		                ps = con.prepareStatement("INSERT INTO mem_recovery_info (ID, positive_test_date, recovery_date) VALUES (?, ?, ?)");
		                ps.setString(1, m.getId());
		                ps.setDate(2, java.sql.Date.valueOf(m.getPositiveTestDate()));
		                ps.setDate(3, java.sql.Date.valueOf(m.getRecoveryDate()));
		                ps.executeUpdate();
		            } else {
		                System.out.println("Error: Positive test date should be earlier than recovery date.");
		            }
			
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		void addvaccine(String id, int vaccineNum, LocalDate vaccineDate, String manufacturer )
		{
			try {
		        // Check if the member with the given ID exists
		        ps = con.prepareStatement("SELECT * FROM mem WHERE ID = ?");
		        ps.setString(1, id);
		        ResultSet memberResult = ps.executeQuery();
		        if (memberResult.next()) {
		            // Check if the member has less than 4 vaccines
		            ps = con.prepareStatement("SELECT COUNT(*) FROM mem_vaccine_info WHERE ID = ?");
		            ps.setString(1, id);
		            ResultSet vaccineCountResult = ps.executeQuery();
		            if (vaccineCountResult.next()) {
		                int vaccineCount = vaccineCountResult.getInt(1);
		                if (vaccineCount < 4) {
		                	ps=con.prepareStatement("INSERT INTO mem_vaccine_info (ID, vaccine_num, vaccine_date, manufacturer ) VALUES (?, ?, ?, ?) ");
		                	ps.setString(1, id);
		                	ps.setInt(2, vaccineNum);
		                	ps.setDate(3, java.sql.Date.valueOf(vaccineDate));
		                	ps.setString(4, manufacturer);
		                	ps.executeUpdate();
		                }
		            }
		        }
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		void addRecovery(String id, LocalDate positiveTestDate, LocalDate recoveryDate)
		{
			try {
				// Check if the member with the given ID exists
		        ps = con.prepareStatement("SELECT * FROM mem WHERE ID = ?");
		        ps.setString(1, id);
		        ResultSet memberResult = ps.executeQuery();
		        if (memberResult.next()) {
		        	ps = con.prepareStatement("SELECT * FROM mem_recovery_info WHERE ID = ?");
		            ps.setString(1, id);
		            ResultSet recoveryResult = ps.executeQuery();
		            if (!recoveryResult.next()) {
		            	ps=con.prepareStatement("INSERT INTO mem_recovery_info (ID, positive_test_date, recovery_date ) VALUES (?, ?, ?) ");
		            	ps.setString(1, id);
		            	ps.setDate(2, java.sql.Date.valueOf(positiveTestDate));
		            	ps.setDate(3, java.sql.Date.valueOf(recoveryDate));
		            	ps.executeUpdate();
		            }
		        }
			} catch (SQLException e) {
				e.printStackTrace();
			}
        	
        }
		
		Member getMember(String id)
		{
			ResultSet rs;
			Member result=new Member();
			LocalDate[] dates;
			String[] manufacturers;
			try {
				rs = stmt.executeQuery("select * from mem where ID='"+id+"'");  
				if(rs.next()) {
					result.setFirstName(rs.getString(1));
					result.setLastName(rs.getString(2));
					result.setId(rs.getString(3));
					result.setCity(rs.getString(4));
					result.setStreet(rs.getString(5));
					result.setHouseNumber(rs.getInt(6));
					result.setDateOfBirth(rs.getDate(7).toLocalDate());
					result.setPhoneNumber(rs.getString(8));
					result.setCellPhoneNumber(rs.getString(9));
					result.setPhoto(rs.getBinaryStream(10));
				}
				rs = stmt.executeQuery("select count(*) from mem_vaccine_info where ID='"+id+"'");  
				 if (rs.next()) 
				 {
		             dates=new LocalDate[rs.getInt(1)];
		             manufacturers=new String[rs.getInt(1)];
				 }
				 else
				 {
					 dates=new LocalDate[0];
		             manufacturers=new String[0];
				 }
				 if(dates.length>0 && manufacturers.length>0) {
					 rs = stmt.executeQuery("select * from mem_vaccine_info where ID='"+id+"'");  
					 while(rs.next())
					 {
						 dates[rs.getInt(2)-1]=rs.getDate(3).toLocalDate();
						 manufacturers[rs.getInt(2)-1]= rs.getString(4);
					 }
					 result.setVaccineDates(dates);
					 result.setManufacturers(manufacturers);
				 }
				rs = stmt.executeQuery("select * from mem_recovery_info where ID='"+id+"'"); 
				if (rs != null && rs.next())
				{
					result.setPositiveTestDate(rs.getDate(2).toLocalDate());
					result.setRecoveryDate(rs.getDate(3).toLocalDate());
				}
			} catch (SQLException e) {
				
				e.printStackTrace();
			}  
			return result;
		}
		
		ArrayList<Member> getAllMembers()
		{
			ResultSet rs;
			ArrayList<Member> result=new ArrayList<Member>();
			LocalDate[]dates;
			String[]manufacturers;
			int numOfVaccines=0;
			try {
				rs = stmt.executeQuery("select * from mem "); 
				int i=0;
				while(rs.next()) {
					result.add(new Member());
					result.get(i).setFirstName(rs.getString(1));
					result.get(i).setLastName(rs.getString(2));
					result.get(i).setId(rs.getString(3));
					result.get(i).setCity(rs.getString(4));
					result.get(i).setStreet(rs.getString(5));
					result.get(i).setHouseNumber(rs.getInt(6));
					result.get(i).setDateOfBirth(rs.getDate(7).toLocalDate());
					result.get(i).setPhoneNumber(rs.getString(8));
					result.get(i).setCellPhoneNumber(rs.getString(9));	
					result.get(i).setPhoto(rs.getBinaryStream(10));
					i++;
				}
				for(int j=0; j<i; j++)
				{
					rs = stmt.executeQuery("select count(*) from mem_vaccine_info where ID='"+result.get(j).getId()+"'"); 
					 if (rs.next()) 
					 {
						 numOfVaccines=rs.getInt(1);
						
					 }
					 result.get(j).setVaccineDates(new LocalDate[numOfVaccines]);
					 result.get(j).setManufacturers(new String[numOfVaccines]);
					 dates=new LocalDate[numOfVaccines];
					 manufacturers=new String[numOfVaccines];
					 rs = stmt.executeQuery("select * from mem_vaccine_info where ID='"+result.get(j).getId()+"'"); 
					 while(rs.next())
					 {
						 dates[rs.getInt(2)-1]=rs.getDate(3).toLocalDate();
						 manufacturers[rs.getInt(2)-1]= rs.getString(4);
					 }
					 for(int k=0; k<numOfVaccines; k++)
					 {
						 result.get(j).setSingleVaccineDate(numOfVaccines-1, dates[k]);
						 result.get(j).setSingleManufacturer(numOfVaccines-1, manufacturers[k]);
					 }
					 rs = stmt.executeQuery("select * from mem_recovery_info where ID='"+result.get(j).getId()+"'"); 
					 if(rs.next())
					 {
						result.get(j).setPositiveTestDate(rs.getDate(2).toLocalDate());
					 	result.get(j).setRecoveryDate(rs.getDate(3).toLocalDate());
					 }
				}
			} catch (SQLException e) {		
				e.printStackTrace();
			}  
			return result;
			}
			
		
		ArrayList<Integer> getActiveCasesPerDayLastMonth() {
	        ArrayList<Integer> activeCasesPerDay = new ArrayList<>();
	        LocalDate currentDate = LocalDate.now();
	        LocalDate lastMonthDate = currentDate.minusMonths(1);
	        LocalDate startDate = lastMonthDate.withDayOfMonth(1);
	        LocalDate endDate = lastMonthDate.withDayOfMonth(lastMonthDate.lengthOfMonth());

	        try {
	            ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM mem_recovery_info WHERE recovery_date >= '" + startDate + "' AND recovery_date <= '" + endDate + "'");
	            if (rs.next()) {
	                int totalRecovered = rs.getInt(1);
	                for (int i = 1; i <= endDate.getDayOfMonth(); i++) {
	                    rs = stmt.executeQuery("SELECT COUNT(*) FROM mem WHERE DATE(date_of_birth) <= '" + lastMonthDate.withDayOfMonth(i) + "' AND (SELECT COUNT(*) FROM mem_recovery_info WHERE recovery_date = '" + lastMonthDate.withDayOfMonth(i) + "') = 0");
	                    if (rs.next()) {
	                        int activeCases = rs.getInt(1) - totalRecovered;
	                        activeCasesPerDay.add(activeCases);
	                    }
	                }
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }

	        return activeCasesPerDay;
	    }

	    long getUnvaccinatedMemberCount() {
	    		long unvaccinatedCount=0;
	            ResultSet rs;
				try {
					rs = stmt.executeQuery("SELECT COUNT(*) FROM mem WHERE (SELECT COUNT(*) FROM mem_vaccine_info WHERE ID = mem.ID) = 0");
					if (rs.next()) 
						unvaccinatedCount = rs.getLong(1);
				} catch (SQLException e) {
					e.printStackTrace();
				}
				return unvaccinatedCount;
	    }
	       






		
		void finish()
		{
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}



