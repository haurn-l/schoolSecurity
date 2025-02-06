package com.example.schoolmanagement.service;



import com.example.schoolmanagement.entity.User;
import com.example.schoolmanagement.exception.CustomException;
import com.example.schoolmanagement.repository.UserRepository;
import com.example.schoolmanagement.entity.CustomUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new CustomException("User not found with username: " + username+" "+ HttpStatus.NOT_FOUND));
        return new CustomUserDetails(user);
    }
}
