package com.nwabundo.mkobo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;



@Data
@AllArgsConstructor
public class SignUpUserDto {

    private String firstName;

    private String lastName;

    private String email;

    private String password;
}
