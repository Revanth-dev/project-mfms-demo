package com.payswiff.mfmsproject.security;

import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.payswiff.mfmsproject.models.Employee;
import com.payswiff.mfmsproject.repositories.EmployeeRepository;


@Service
public class CustomeUserDetailsService implements UserDetailsService {

	@Autowired
	EmployeeRepository employeeRepository;

	@Override
	public UserDetails loadUserByUsername(String emailOrPhone) throws UsernameNotFoundException {
		
		Employee dbEmployee=employeeRepository.findByEmployeeEmailOrEmployeePhoneNumber(emailOrPhone,emailOrPhone)
		.orElseThrow(()->new UsernameNotFoundException("Employee not found with email or phone :"+emailOrPhone));
		// TODO Auto-generated method stub
		
		Set<GrantedAuthority> grantedAuthorities = dbEmployee.getRoles()
				.stream()
				.map((role)->new SimpleGrantedAuthority(role.getName()))
				.collect(Collectors.toSet());
		
		
		return new User(dbEmployee.getEmployeeEmail(),dbEmployee.getEmployeePassword(),grantedAuthorities);
	}

}
