package com.springsecurity.securityjwt.service;

import com.springsecurity.securityjwt.Util.JwtUtil;
import com.springsecurity.securityjwt.entity.Role;
import com.springsecurity.securityjwt.entity.User;
import com.springsecurity.securityjwt.payloaddto.LoginRequest;
import com.springsecurity.securityjwt.payloaddto.LoginResponse;
import com.springsecurity.securityjwt.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class JwtService implements UserDetailsService {
    @Autowired
    private UserRepo userRepo;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepo.findById(username).get();
        if ( user != null) {
            return new org.springframework.security.core.userdetails.User(
                    user.getUserName(),
                    user.getUsePassword(),
                    getAuthority(user)
            );
        } else {
            throw new UsernameNotFoundException("User not found with username " + username);
        }
    }

    private Set getAuthority(User user){
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
//        for(Role role : user.getRole()){
//            authorities.add(new SimpleGrantedAuthority("ROLE_"+ role.getRoleName()));
//        }
        user.getRole().forEach(role -> {
            authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getRoleName()));
        });
        return  authorities;
    }

    public LoginResponse createJwtToken(LoginRequest loginRequest) throws Exception {
        String userName = loginRequest.getUserName();
        String userPassword = loginRequest.getUserPassword();

        authenticate(userName,userPassword); // LOGIN DETAILS VALIDITY CHECKING =>

        UserDetails userDetails = loadUserByUsername(userName);
        String newGeneratedToken = jwtUtil.generateToken(userDetails); // NEW TOKEN GENERATED PART
        User user = userRepo.findById(userName).get();

        LoginResponse loginResponse = new LoginResponse(
                user,
                newGeneratedToken
        );
        return loginResponse;
    }

    // LOGIN DETAILS VALIDITY CHECKING =>
    private void authenticate(String userName, String userPassword) throws Exception{
        try{
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userName,userPassword));

        }catch (BadCredentialsException e){
            throw new Exception("INVALID_CREDENTIALS", e);
        }

    }
}
