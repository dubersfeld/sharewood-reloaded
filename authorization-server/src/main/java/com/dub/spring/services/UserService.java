package com.dub.spring.services;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.validation.annotation.Validated;

import com.dub.spring.controller.UserPrincipal;
import com.dub.spring.oauth.entities.MyUser;


@Validated
public interface UserService extends UserDetailsService {
    @Override
    UserDetails loadUserByUsername(String username);// custom implementation

    
}
