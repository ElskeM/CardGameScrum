package com.yrgo.sp.cardgame.security;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.yrgo.sp.cardgame.data.SecurityRoleRepository;
import com.yrgo.sp.cardgame.domain.user.SecurityRole;

@Component("SecurityRoleManager")
public class SecurityRoleManager {
	
	@Autowired
	private SecurityRoleRepository roleData;
	
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
