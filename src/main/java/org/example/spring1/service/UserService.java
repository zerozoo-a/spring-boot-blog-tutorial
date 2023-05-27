package org.example.spring1.service;

import lombok.RequiredArgsConstructor;
import org.example.spring1.domain.User;
import org.example.spring1.dto.AddUserRequest;
import org.example.spring1.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserService {
	private final UserRepository userRepository;
	private final BCryptPasswordEncoder bCryptPasswordEncoder;

	public Long save(AddUserRequest dto) {
		return userRepository.save(
				User.builder()
						.email(dto.getEmail())
						.password(bCryptPasswordEncoder.encode(dto.getPassword()))
						.build()).getId();
	}
}
