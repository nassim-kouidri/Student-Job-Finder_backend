package studentjobfinderAPI.studentjobfinder.Model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

@Document("Admin")

@Getter
@Setter
public class Admin extends Account{

    public Admin(String email, String password, Role role , String name , String fname,AccountStatus accountStatus  ) {
        super(email, password, role,accountStatus);
        this.name = name;
        this.fname = fname;


    }
    private String name;
    private String fname;
    @DBRef
    private Image profilePic;
	public String getName() {
		return name;
	}
	public String getFname() {
		return fname;
	}
	public Image getProfilePic() {
		return profilePic;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setFname(String fname) {
		this.fname = fname;
	}
	public void setProfilePic(Image profilePic) {
		this.profilePic = profilePic;
	}
    
    

}
