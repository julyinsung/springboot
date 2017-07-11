package mysample.boot.security.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import mysample.boot.security.domain.UserRole;
import mysample.boot.security.mapper.user.UserMapper;

@Service("customUserDetailsService")
public class CustomUserDetailsService implements UserDetailsService {

	@Autowired
	UserMapper userMapper;
	
	
	/*
	 * DB에서 계정정보를 조회한다.
	 * UserDetails 인터페이스에서 비밀번호를 맅넌하기 때문에 Spring Security에서는 계정에 맞는 비밀번호르인지 아닌지 확인하는 작업을
	 * DB레벨에서 하는게 아니라 DB에서 검색된 비밀번호를 자바 레벨에서 하는 것임.
	 * @see org.springframework.security.core.userdetails.UserDetailsService#loadUserByUsername(java.lang.String)
	 */
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// username = loginId
//		mysample.boot.security.domain.User user = userMapper.getUserByLoginId(username);
//		List<GrantedAuthority> authorities = buildUserAuthority(user.getUserRoles());
		
		mysample.boot.security.domain.User user = userMapper.getUserByLoginId(username);
		user.setAuthorities(buildUserAuthority(user.getUserRoles()));
		return user;//buildUserForAuthentication(user, authorities);
	}

	private UserDetails buildUserForAuthentication(mysample.boot.security.domain.User user, List<GrantedAuthority> authorities) {
		System.out.println("password: "+user.getPassword());
		return new User(user.getUsername(), user.getPassword(),
				true, true, true, true, authorities);
	}

	private List<GrantedAuthority> buildUserAuthority(List<UserRole> userRoles) {
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>(0);
		for (UserRole userRole : userRoles) {
			authorities.add(new SimpleGrantedAuthority(userRole.getRoleName()));
		}
		return authorities;
	}

}
