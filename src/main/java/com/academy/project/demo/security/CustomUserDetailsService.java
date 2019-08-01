package com.academy.project.demo.security;


import com.academy.project.demo.entity.User;
import com.academy.project.demo.exception.NotFoundException;
import com.academy.project.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class CustomUserDetailsService implements UserDetailsService {

    private UserRepository userRepository;

    @Autowired
    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new NotFoundException("User not found [email: " + email + "]")
                );

        return UserPrincipal.create(user);
    }

    @Transactional
    public UserDetails loadUserByIdToUserDetails(Long id) {
        return UserPrincipal.create(loadUserById(id));
    }



    @Transactional
    public User loadUserById(Long id){
        User user = userRepository.findById(id).orElseThrow(
                () -> new NotFoundException("User not found [id: " + id + "]")
        );
        return user;
    }
}
