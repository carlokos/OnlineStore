package com.javaschool.OnlineStore.security.Services;

import java.util.List;
import java.util.stream.Collectors;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.javaschool.OnlineStore.exceptions.ResourceNotFoundException;
import com.javaschool.OnlineStore.models.RolEntity;
import com.javaschool.OnlineStore.models.UserEntity;
import com.javaschool.OnlineStore.repositories.UserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService{

    private UserRepository userRepository;

    @Autowired
    public CustomUserDetailsService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    /*
     * It is a override method so i can't really change the name, but the idea is to find by user's email because it's unique 
     * and the main validation for my app
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity user = userRepository.findByEmail(username).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        return new User(user.getEmail(), user.getPassword(), mapRolesToAuthorities(user.getRoles()));
    }
    
    private Collection<GrantedAuthority> mapRolesToAuthorities(List<RolEntity> roles){
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
    }
    
}
