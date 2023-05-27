package org.example.spring1.service;

import lombok.RequiredArgsConstructor;
import org.example.spring1.domain.User;
import org.example.spring1.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserDetailService implements UserDetailsService {
	private final UserRepository userRepository;

	@Override
	public User loadUserByUsername(String email) {
		return userRepository.findByEmail(email)
				.orElseThrow(() -> new IllegalArgumentException(email));
	}


}
