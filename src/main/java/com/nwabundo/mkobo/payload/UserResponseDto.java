package com.nwabundo.mkobo.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
@AllArgsConstructor
public class UserResponseDto {
    public  String email;
    public String firstName;
    public String lastName;
}
