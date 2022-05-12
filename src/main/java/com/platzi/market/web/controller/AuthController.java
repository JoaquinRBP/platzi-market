package com.platzi.market.web.controller;

import com.platzi.market.domain.dto.AuthenticationRequest;
import com.platzi.market.domain.dto.AuthenticationResponse;
import com.platzi.market.domain.service.PlatziUserDetailService;
import com.platzi.market.web.security.JWTUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private AuthenticationManager authenticationManager;

    private PlatziUserDetailService platziUserDetailService;
    private JWTUtil jwtUtil;
    public AuthController(AuthenticationManager authenticationManager,PlatziUserDetailService platziUserDetailService,
    JWTUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.platziUserDetailService=platziUserDetailService;
        this.jwtUtil=jwtUtil;
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> createToken(@RequestBody AuthenticationRequest authenticationRequest){
        try{
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(),authenticationRequest.getPassword()));
            UserDetails userDetails=platziUserDetailService.loadUserByUsername(authenticationRequest.getUsername());
            String jwt = jwtUtil.generateToken(userDetails);
            return new ResponseEntity<>(new AuthenticationResponse(jwt),HttpStatus.OK);
        }catch(BadCredentialsException e){
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }
}
