package com.yrgo.sp.cardgame.security;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.yrgo.sp.cardgame.data.SecurityRoleRepository;
import com.yrgo.sp.cardgame.domain.user.SecurityRole;

/**
 * @author ptemrz
 * SecurityRoleManager entity to manage the different available roles in the system
 *
 */
@Component("SecurityRoleManager")
public class SecurityRoleManager {
	
	@Autowired
	private SecurityRoleRepository roleData;
	
	/**
	 * Method to setup default roles
	 * Available roles are: Admin, Player and Teacher
	 */
	@PostConstruct
	private void setUpDefaultRoles() {
		SecurityRole player = new SecurityRole("PLAYER");
		SecurityRole admin = new SecurityRole("ADMIN");
		SecurityRole teacher = new SecurityRole("TEACHER");
		if (roleData.count() == 0) {
			roleData.save(player);
			roleData.save(admin);
			roleData.save(teacher);
		}
	}
}
