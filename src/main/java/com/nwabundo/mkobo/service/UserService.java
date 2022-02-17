package com.nwabundo.mkobo.service;

import com.nwabundo.mkobo.dto.EditUserDTO;
import com.nwabundo.mkobo.dto.SignUpUserDto;
import com.nwabundo.mkobo.dto.UserResponseDto;
import com.nwabundo.mkobo.model.UserModel;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
public interface UserService extends UserDetailsService {

    String createUser(SignUpUserDto userDTO);

    UserResponseDto updateUserDetails(EditUserDTO userDTO, HttpServletRequest request);




}
