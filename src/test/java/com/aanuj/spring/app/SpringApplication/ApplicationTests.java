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
		User user = new User(4L, "aanujjain750@gmail.com", "Aanuj@1234");
		String token  = jwtService.generateToken(user);
		System.out.println(token);

		Long id = jwtService.getUserIdFromToken(token);
		System.out.println(id);
	}

}
