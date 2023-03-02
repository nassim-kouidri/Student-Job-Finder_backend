package studentjobfinderAPI.studentjobfinder.Model;

import com.mongodb.internal.connection.Time;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.security.Timestamp;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Document("Contact")
public class Contact {
    @Id
    private String id;
    private String email;
    private String name;
    private String fname;
    private Role role;
    private String message;
    private ContactSiteStatus contactSiteStatus;
    private Date timeStamp = new Date();
	public String getId() {
		return id;
	}
	public String getEmail() {
		return email;
	}
	public String getName() {
		return name;
	}
	public String getFname() {
		return fname;
	}
	public Role getRole() {
		return role;
	}
	public String getMessage() {
		return message;
	}
	public ContactSiteStatus getContactSiteStatus() {
		return contactSiteStatus;
	}
	public Date getTimeStamp() {
		return timeStamp;
	}
	public void setId(String id) {
		this.id = id;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setFname(String fname) {
		this.fname = fname;
	}
	public void setRole(Role role) {
		this.role = role;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public void setContactSiteStatus(ContactSiteStatus contactSiteStatus) {
		this.contactSiteStatus = contactSiteStatus;
	}
	public void setTimeStamp(Date timeStamp) {
		this.timeStamp = timeStamp;
	}
    
    

}
