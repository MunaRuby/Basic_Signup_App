package com.nwabundo.mkobo.service;

import com.nwabundo.mkobo.dto.EditUserDTO;
import com.nwabundo.mkobo.dto.SignUpUserDto;
import com.nwabundo.mkobo.payload.SignupResponseDTO;
import com.nwabundo.mkobo.payload.UserResponseDto;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
public interface UserService extends UserDetailsService {

    SignupResponseDTO createUser(SignUpUserDto userDTO);

    UserResponseDto updateUserDetails(EditUserDTO userDTO, HttpServletRequest request);




}
