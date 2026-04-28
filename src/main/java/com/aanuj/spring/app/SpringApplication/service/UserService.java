package com.aanuj.spring.app.SpringApplication.service;

import com.aanuj.spring.app.SpringApplication.dto.SignUpDTO;
import com.aanuj.spring.app.SpringApplication.dto.UserDTO;
import com.aanuj.spring.app.SpringApplication.entities.User;
import com.aanuj.spring.app.SpringApplication.exceptions.ResourceNotFoundException;
import com.aanuj.spring.app.SpringApplication.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final ModelMapper modelMapper;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;



    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username).orElseThrow(() ->
                new ResourceNotFoundException("User with email " +  username + " not found"));
    }

    public User getUserByUserId(Long userId){
        return userRepository.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User with id: "+ userId + " does not exists"));
    }

    public UserDTO signUp(SignUpDTO signUpDTO){
        Optional<User> user = userRepository.findByEmail(signUpDTO.getEmail());
        if(user.isPresent()){
            throw new BadCredentialsException("User with email already exists: "+ signUpDTO.getEmail());
        }
        User toBeCreatedCreate = modelMapper.map(signUpDTO, User.class);
        toBeCreatedCreate.setPassword(passwordEncoder.encode(toBeCreatedCreate.getPassword()));
        User saveUser = userRepository.save(toBeCreatedCreate);
        return modelMapper.map(saveUser, UserDTO.class);
    }
}
