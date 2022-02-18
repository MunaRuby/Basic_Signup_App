package com.nwabundo.mkobo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import javax.validation.constraints.Email;


@Data
@AllArgsConstructor
public class SignUpUserDto {

    @NotBlank(message = "First name cannot be blank")
    @Size(min = 2, message = "First name must have a minimum of 2 characters and above")
    private String firstName;

    @NotBlank(message = "Last name cannot be blank")
    @Size(min = 2, message = "Last name must have a minimum of 2 characters and above")
    private String lastName;

    @Email(message = "Enter a valid Email")
    private String email;

    private String password;
}
