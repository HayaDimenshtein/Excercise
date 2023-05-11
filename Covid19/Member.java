import java.io.FileInputStream;
import java.io.InputStream;
import java.time.LocalDate;


public class Member {
	
	private String firstName;
    private String lastName;
    private String id;
    private String city;
    private String street;
    private int houseNumber;
    private LocalDate dateOfBirth;
    private String phoneNumber;
    private String cellPhoneNumber;
    private InputStream photo;
    private LocalDate[] vaccineDates;
    private String[] manufacturers;
    private LocalDate positiveTestDate;
    private LocalDate recoveryDate;
    
    public Member()
    {
    	
    }
    
    public Member(String firstName, String lastName, String id, String city, String street, int houseNumber, LocalDate dateOfBirth, String phoneNumber, String cellPhoneNumber, InputStream photo, LocalDate[] vaccineDates, String[] manufacturers, LocalDate positiveTestDate, LocalDate recoveryDate) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.id = id;
        this.city = city;
        this.street = street;
        this.houseNumber = houseNumber;
        this.dateOfBirth = dateOfBirth;
        this.phoneNumber = phoneNumber;
        this.cellPhoneNumber = cellPhoneNumber;
        this.photo = photo;
        this.vaccineDates = vaccineDates;
        this.manufacturers = manufacturers;
        this.positiveTestDate = positiveTestDate;
        this.recoveryDate = recoveryDate;
    }
    
    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getId() {
        return id;
    }
    
    public String getCity() {
        return city;
    }

    public String getStreet() {
        return street;
    }

    public int getHouseNumber() {
        return houseNumber;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getCellPhoneNumber() {
        return cellPhoneNumber;
    }
    
    public InputStream getPhoto() {
        return photo;
    }

    public LocalDate[] getVaccineDates() {
        return vaccineDates;
    }

    public String[] getManufacturers() {
        return manufacturers;
    }

    public LocalDate getPositiveTestDate() {
        return positiveTestDate;
    }

    public LocalDate getRecoveryDate() {
        return recoveryDate;
    }
    
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public void setHouseNumber(int houseNumber) {
        this.houseNumber = houseNumber;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setCellPhoneNumber(String cellPhoneNumber) {
        this.cellPhoneNumber = cellPhoneNumber;
    }

    public void setPhoto(InputStream photo) {
        this.photo = photo;
    }

    public void setVaccineDates(LocalDate[] vaccineDates) {
        this.vaccineDates = vaccineDates;
    }
    
    public void setSingleVaccineDate(int vaccineNum, LocalDate vaccineDate)
    {
    	this.vaccineDates[vaccineNum]=vaccineDate;
    }

    public void setManufacturers(String[] manufacturers) {
        this.manufacturers = manufacturers;
    }
    
    public void setSingleManufacturer(int vaccineNum, String manufacturer)
    {
    	this.manufacturers[vaccineNum]=manufacturer;
    }

    public void setPositiveTestDate(LocalDate positiveTestDate) {
        this.positiveTestDate = positiveTestDate;
    }

    public void setRecoveryDate(LocalDate recoveryDate) {
        this.recoveryDate = recoveryDate;
    }

    
}
