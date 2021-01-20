package com.yrgo.sp.cardgame.data;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.yrgo.sp.cardgame.domain.user.SecurityRole;

public interface SecurityRoleRepository extends JpaRepository<SecurityRole, Long>{
	Optional<SecurityRole> findByAuthority(String authority);
}
