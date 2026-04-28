package com.aanuj.spring.app.SpringApplication;

import com.aanuj.spring.app.SpringApplication.entities.User;
import com.aanuj.spring.app.SpringApplication.service.JwtService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ApplicationTests {

	@Autowired
	private JwtService jwtService;

	@Test
	void contextLoads() {
		User user = new User(4l, "aanujjain750@gmail.com", "1234", "Aanuj Jain");
		String token  = jwtService.generateAccessToken(user);
		System.out.println(token);

		Long id = jwtService.getUserIdFromToken(token);
		System.out.println(id);
	}

}
