package com.nwabundo.mkobo.controller;

import com.nwabundo.mkobo.api.ApiResponse;
import com.nwabundo.mkobo.dto.JWTResponse;
import com.nwabundo.mkobo.dto.LoginDTO;
import com.nwabundo.mkobo.filters.JWTDataSource;
import com.nwabundo.mkobo.filters.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

import static com.nwabundo.mkobo.serviceimpl.ApiResponseBuilder.buildResponseEntity;
import static org.springframework.http.HttpStatus.OK;

@RestController
public class AuthController {
    private UserDetailsService userDetailsService;
    private JWTUtil jwtUtil;
    private AuthenticationManager authenticationManager;
    private final JWTDataSource jwtDataSource;

    @Autowired
    public AuthController( final UserDetailsService userDetailsService, final JWTUtil jwtUtil,
                          final AuthenticationManager authenticationManager, final JWTDataSource jwtDataSource) {
        this.userDetailsService = userDetailsService;
        this.jwtUtil = jwtUtil;
        this.authenticationManager = authenticationManager;
        this.jwtDataSource = jwtDataSource;
    }

    @ResponseStatus(OK)
    @PostMapping("/login")
    public ResponseEntity<ApiResponse<JWTResponse>> login (@RequestBody LoginDTO dto, HttpServletResponse response) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(dto.getEmail(), dto.getPassword()));
        } catch (BadCredentialsException e) {
            throw new Exception("incorrect username or passoword!");
        }

        final UserDetails userDetails = userDetailsService.loadUserByUsername(dto.getEmail());
        final String jwt = jwtUtil.generateToken(userDetails);
        response.addHeader("Authorization", "Bearer " + jwt);
        return buildResponseEntity(new ApiResponse<>("user signed in sucessfully", OK, new JWTResponse(jwt, jwtDataSource.getTokenPrefix(), jwtDataSource.getExpirationDate())));
    }
}
