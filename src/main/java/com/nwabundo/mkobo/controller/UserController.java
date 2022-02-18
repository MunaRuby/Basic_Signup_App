package com.nwabundo.mkobo.controller;

import com.nwabundo.mkobo.dto.EditUserDTO;
import com.nwabundo.mkobo.dto.SignUpUserDto;
import com.nwabundo.mkobo.payload.SignupResponseDTO;
import com.nwabundo.mkobo.payload.UserResponseDto;
import com.nwabundo.mkobo.service.UserService;
import com.nwabundo.mkobo.serviceimpl.SignupErrorValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequestMapping("/")
public class UserController {

    private final UserService userService;
    private final SignupErrorValidationService validationService;

    @Autowired
    public UserController(UserService userService, SignupErrorValidationService validationService) {
        this.userService = userService;
        this.validationService = validationService;
    }


    @PostMapping("create")
    public ResponseEntity<?> createuser(@Valid @RequestBody SignUpUserDto userDTO, BindingResult result){
        ResponseEntity<?> errorMap = validationService.validate(result);
        if(errorMap != null) return errorMap;
        return ResponseEntity.ok().body(userService.createUser(userDTO));

    }

    @PostMapping("edituser")
    public ResponseEntity<UserResponseDto>  updateUserDetails(@RequestBody EditUserDTO editUserDTO, HttpServletRequest request){
        return ResponseEntity.ok().body(userService.updateUserDetails(editUserDTO, request));
    }

}
