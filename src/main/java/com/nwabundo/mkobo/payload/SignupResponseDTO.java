package com.nwabundo.mkobo.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@AllArgsConstructor
@Setter
@Getter
public class SignupResponseDTO {
    public  String email;
    public String firstName;
    public String lastName;
    public String feedback;
}
