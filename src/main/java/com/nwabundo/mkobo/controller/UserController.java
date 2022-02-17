package com.nwabundo.mkobo.controller;

import com.nwabundo.mkobo.dto.EditUserDTO;
import com.nwabundo.mkobo.dto.SignUpUserDto;
import com.nwabundo.mkobo.payload.SignupResponseDTO;
import com.nwabundo.mkobo.payload.UserResponseDto;
import com.nwabundo.mkobo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }


    @PostMapping("create")
    public ResponseEntity<SignupResponseDTO> createuser(@RequestBody SignUpUserDto userDTO){
        return ResponseEntity.ok().body(userService.createUser(userDTO));

    }

    @PostMapping("edituser")
    public ResponseEntity<UserResponseDto>  updateUserDetails(@RequestBody EditUserDTO editUserDTO, HttpServletRequest request){
        return ResponseEntity.ok().body(userService.updateUserDetails(editUserDTO, request));
    }

}
