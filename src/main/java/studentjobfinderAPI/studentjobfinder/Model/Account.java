package studentjobfinderAPI.studentjobfinder.Model;


    import lombok.Getter;
    import lombok.Setter;

import java.sql.Date;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Random;

import javax.xml.crypto.Data;

import org.springframework.data.annotation.Id;
    import org.springframework.data.mongodb.core.index.Indexed;
    import org.springframework.data.mongodb.core.mapping.DBRef;
    import org.springframework.data.mongodb.core.mapping.Document;
    import org.springframework.format.annotation.DateTimeFormat;


@Document("Account")
@Getter
@Setter
public class Account {
    @Id
    private String id;
    @Indexed(unique = true)
    private String email;
    private String password;
    private Role role;
    private AccountStatus accountStatus;
    private String verificationCode;
    private boolean isEnabled;
    private java.util.Date verificationCodeExpirationDate = new Date(System.currentTimeMillis());
    private Reports reports = new Reports();

   
    public Account(String email, String password , Role role , AccountStatus accountStatus) {
        this.email = email;
        this.password = password;
        this.role = role;
        this.accountStatus = accountStatus;
        Random rnd = new Random();
        int number = rnd.nextInt(999999);
        this.verificationCode = String.format("%06d", number);
    }

    public Account (){

    }
    public Reports getReports() {
		return reports;
	}

	public void setReports(Reports reports) {
		this.reports = reports;
	}
	
	public java.util.Date getVerificationCodeExpirationDate() {
		return verificationCodeExpirationDate;
	}


	public void setVerificationCodeExpirationDate(java.util.Date verificationCodeExpirationDate) {
		this.verificationCodeExpirationDate = verificationCodeExpirationDate;
	}


	public boolean isEnabled() {
		return isEnabled;
	}
	public void setEnabled(boolean isEnabled) {
		this.isEnabled = isEnabled;
	}
	public String getVerificationCode() {
		return verificationCode;
	}
	public void setVerificationCode(String verificationCode) {
		this.verificationCode = verificationCode;
	}
	public String getEmail() {
        return email;
    }
    public String getPassword() {
        return password;
    }
    public String getId() {
        return id;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public void setId(String id) {
        this.id = id;
    }
	public Role getRole() {
		return role;
	}
	public void setRole(Role role) {
		this.role = role;
	}
    public AccountStatus getAccountStatus() {
        return accountStatus;
    }
    public void setAccountStatus(AccountStatus accountStatus) {
        this.accountStatus = accountStatus;
    }

}
