package studentjobfinderAPI.studentjobfinder.Payload.Request;

public class PasswordChangeRequest {
	
	
	String oldPassword;
	String newPassword;
	
	
	public String getOldPassword() {
		return oldPassword;
	}
	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}
	public String getNewPassword() {
		return newPassword;
	}
	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}
}
