package com.nhk.thesis.security.service;

import com.nhk.thesis.entity.User;
import com.nhk.thesis.entity.constant.UserRole;
import com.nhk.thesis.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UserSecurityServices implements UserDetailsService {

    private UserRepository userRepository;

    @Autowired
    public UserSecurityServices(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    private List<GrantedAuthority> getAuthority(List<UserRole> role){
        Set<GrantedAuthority> roles = new HashSet<>();

        role.forEach(r -> {
            roles.add(new SimpleGrantedAuthority(r.name()));
        });

        List<GrantedAuthority> list = new ArrayList<>(roles);

        return list;
    }

    private UserDetails buildUser(User user, List<GrantedAuthority> list){
        //System.out.println("authority: " +list.get(0));
        return new org.springframework.security.core.userdetails.User(user.getAccount(), user.getPassword(), list);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findUserByAccount(username);
        if(user != null){
            //System.out.println("Found user: " + username);
            UserDetails userDetail = buildUser(user, getAuthority(user.getRole()));
            System.out.println(userDetail.getAuthorities());
            return userDetail;//buildUser(user, getAuthority(user.getRole().name()));
        }else
            throw new UsernameNotFoundException("Email not found!");
    }
}
