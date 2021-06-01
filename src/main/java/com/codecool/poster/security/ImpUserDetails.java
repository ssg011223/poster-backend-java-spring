package com.codecool.poster.security;

import com.codecool.poster.model.Person;
import com.codecool.poster.repository.PersonRepository;
import com.codecool.poster.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;
import java.util.stream.Collectors;

public class ImpUserDetails implements UserDetailsService {
    @Autowired
    private PersonRepository personRepository;
    @Autowired
    private RoleRepository roleRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Person person = personRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Username: " + username + " not found"));
        List<GrantedAuthority> roles = roleRepository.findAllByPersonId(person).stream()
                .map(p -> new SimpleGrantedAuthority(p.getUserRole().toString())).collect(Collectors.toList());

        return new User(person.getUsername(), person.getPassword(), roles);
    }
}
