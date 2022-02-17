package com.nwabundo.mkobo.serviceimpl;

import com.chompfooddeliveryapp.exception.BadRequestException;
import com.nwabundo.mkobo.dto.EditUserDTO;
import com.nwabundo.mkobo.dto.SignUpUserDto;
import com.nwabundo.mkobo.payload.SignupResponseDTO;
import com.nwabundo.mkobo.payload.UserResponseDto;
import com.nwabundo.mkobo.filters.JWTUtil;
import com.nwabundo.mkobo.model.UserModel;
import com.nwabundo.mkobo.repository.UserRepository;
import com.nwabundo.mkobo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;

@Service
public class UserServiceImpl implements UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final JWTUtil jwtUtil;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, final PasswordEncoder passwordEncoder, JWTUtil jwtUtil) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }


    @Override
    public SignupResponseDTO createUser(SignUpUserDto signUpUserDto) {

        if(userRepository.existsByEmail(signUpUserDto.getEmail())){

            return new SignupResponseDTO(signUpUserDto.getEmail(), signUpUserDto.getFirstName(), signUpUserDto.getLastName(), " already exists");

        }else{
            UserModel user = new UserModel();

            user.setUsername(signUpUserDto.getEmail());
            user.setPassword(passwordEncoder.encode(signUpUserDto.getPassword()));
            user.setFirstName(signUpUserDto.getFirstName());
            user.setLastName(signUpUserDto.getLastName());
            user.setEmail(signUpUserDto.getEmail());
            userRepository.save(user);
            return new SignupResponseDTO(signUpUserDto.getEmail(), signUpUserDto.getFirstName(), signUpUserDto.getLastName(), " created");
        }

    }

    @Override
    public UserResponseDto updateUserDetails(EditUserDTO userDTO, HttpServletRequest request) {

        final String authorization = request.getHeader("Authorization");
        final String token = authorization.split(" ")[1];
        final String email = jwtUtil.extractUsername(token);
        var user = userRepository.findByEmail(email);

        if(user.isEmpty())throw new BadRequestException("user does not exist");

        if(userDTO.getFirstName().isEmpty() && userDTO.getLastName().isEmpty()) {
            return new UserResponseDto(user.get().getEmail(), user.get().getFirstName(), user.get().getLastName());
        }

        if(!userDTO.getFirstName().isEmpty()) user.get().setFirstName(userDTO.getFirstName());
        if (!userDTO.getLastName().isEmpty()) user.get().setLastName(userDTO.getLastName());

        userRepository.save(user.get());

        return new UserResponseDto(user.get().getEmail(), user.get().getFirstName(), user.get().getLastName());
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var userDetails = userRepository.findByEmail(username);
        if(userDetails.isEmpty()){
            throw new BadRequestException("Username not found");
        }else{
            return new User(username, userDetails.get().getPassword(), new ArrayList<>());
        }
    }
}
