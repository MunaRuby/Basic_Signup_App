package com.nwabundo.mkobo.controller;

import com.nwabundo.mkobo.dto.EditUserDTO;
import com.nwabundo.mkobo.dto.SignUpUserDto;
import com.nwabundo.mkobo.dto.UserResponseDto;
import com.nwabundo.mkobo.model.UserModel;
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
    public String createuser(@RequestBody SignUpUserDto userDTO){
        userService.createUser(userDTO);

        return userDTO.getFirstName() + " " + userDTO.getLastName() + " has been created";
    }

    @PostMapping("edituser")
    public ResponseEntity<UserResponseDto>  updateUserDetails(@RequestBody EditUserDTO editUserDTO, HttpServletRequest request){
        return ResponseEntity.ok().body(userService.updateUserDetails(editUserDTO, request));
    }

    @GetMapping("hello")
    public String hello(){
        return "Hello world";
    }
}
