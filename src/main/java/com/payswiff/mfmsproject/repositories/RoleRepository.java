package com.payswiff.mfmsproject.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.payswiff.mfmsproject.models.Role;

public interface RoleRepository extends JpaRepository<Role,Long>{
	
	Optional<Role>  findByName(String name);

}
