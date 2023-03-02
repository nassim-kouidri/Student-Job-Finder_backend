package studentjobfinderAPI.studentjobfinder.Configuration;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.stereotype.Service;

import studentjobfinderAPI.studentjobfinder.Model.Account;
import studentjobfinderAPI.studentjobfinder.Model.Role;
import studentjobfinderAPI.studentjobfinder.Repository.AdminRepository;
import studentjobfinderAPI.studentjobfinder.Repository.CompanyRepository;
import studentjobfinderAPI.studentjobfinder.Repository.StudentRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {

	@Autowired
	CompanyRepository companyRepository;
	@Autowired
	AdminRepository adminRepository;

	@Autowired
	StudentRepository studentRepository;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Account account = companyRepository.findCompanyByEmail(email);
		if (account==null) {
			account = studentRepository.findStudentByEmail(email);
		} if (account== null) {
			account= adminRepository.findAdminByEmail(email);
		}
		//Use the following line is password are not encrypted in database.
		account.setPassword(new BCryptPasswordEncoder().encode(account.getPassword()));
		return new org.springframework.security.core.userdetails.User(
				account.getEmail(),
				account.getPassword(),
				getGrantedAuthorities(account.getRole()));
	}

	private List<GrantedAuthority> getGrantedAuthorities(Role role) {
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		//Spring Security automatically prefix role with ROLE_
		//so if the role name in database isn't prefix with ROLE_
		//we have to it
		authorities.add(new SimpleGrantedAuthority("ROLE_" + role));
		return authorities;
	}
	

}