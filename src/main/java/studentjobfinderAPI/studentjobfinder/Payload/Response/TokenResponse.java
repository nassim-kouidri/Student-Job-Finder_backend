package studentjobfinderAPI.studentjobfinder.Payload.Response;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import studentjobfinderAPI.studentjobfinder.Model.*;

@Getter
@Setter
public class TokenResponse {


	public TokenResponse(String token, Role role , Object user) {
		this.token = token;
		this.role = role;
		this.user = user;
	}

	
	private String token;
	private Role role;
	private List <String> favOffersId;
	private Object user;

	public List<String> getFavOffers() {
		return favOffersId;
	}
	public void setFavOffers(List<String> favOffers) {
		this.favOffersId = favOffers;
	}
	public Object getUser() {
		return user;
	}

	public void setUser(Object user) {
		this.user = user;
	}
	public String getToken() {
		return token;
	}
	public Role getRole() {
		return role;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public void setRole(Role role) {
		this.role = role;
	}

	
}
	

