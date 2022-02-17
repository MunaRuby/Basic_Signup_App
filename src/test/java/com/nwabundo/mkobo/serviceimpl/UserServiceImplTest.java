package com.nwabundo.mkobo.serviceimpl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.chompfooddeliveryapp.exception.BadRequestException;
import com.nwabundo.mkobo.dto.SignUpUserDto;
import com.nwabundo.mkobo.filters.JWTUtil;
import com.nwabundo.mkobo.model.UserModel;
import com.nwabundo.mkobo.payload.SignupResponseDTO;
import com.nwabundo.mkobo.repository.UserRepository;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
@ContextConfiguration(classes = {UserServiceImpl.class})
@ExtendWith(SpringExtension.class)
class UserServiceImplTest {
    @MockBean
    private JWTUtil jWTUtil;
    @MockBean
    private PasswordEncoder passwordEncoder;
    @MockBean
    private UserRepository userRepository;
    @Autowired
    private UserServiceImpl userServiceImpl;
    @Test
    void testCreateUser() {
        when(this.userRepository.existsByEmail((String) any())).thenReturn(true);
        SignupResponseDTO actualCreateUserResult = this.userServiceImpl
                .createUser(new SignUpUserDto("Jane", "Doe", "jane.doe@example.org", "iloveyou"));
        assertEquals("jane.doe@example.org", actualCreateUserResult.getEmail());
        assertEquals("Doe", actualCreateUserResult.getLastName());
        assertEquals("Jane", actualCreateUserResult.getFirstName());
        assertEquals(" already exists", actualCreateUserResult.getFeedback());
        verify(this.userRepository).existsByEmail((String) any());
    }
    @Test
    void testCreateUser3() {
        UserModel userModel = new UserModel();
        userModel.setEmail("jane.doe@example.org");
        userModel.setFirstName("Jane");
        userModel.setId(123L);
        userModel.setLastName("Doe");
        userModel.setPassword("iloveyou");
        userModel.setUsername("janedoe");
        when(this.userRepository.save((UserModel) any())).thenReturn(userModel);
        when(this.userRepository.existsByEmail((String) any())).thenReturn(false);
        when(this.passwordEncoder.encode((CharSequence) any())).thenReturn("secret");
        SignupResponseDTO actualCreateUserResult = this.userServiceImpl
                .createUser(new SignUpUserDto("Jane", "Doe", "jane.doe@example.org", "iloveyou"));
        assertEquals("jane.doe@example.org", actualCreateUserResult.getEmail());
        assertEquals("Doe", actualCreateUserResult.getLastName());
        assertEquals("Jane", actualCreateUserResult.getFirstName());
        assertEquals(" created", actualCreateUserResult.getFeedback());
        verify(this.userRepository).existsByEmail((String) any());
        verify(this.userRepository).save((UserModel) any());
        verify(this.passwordEncoder).encode((CharSequence) any());
    }
    @Test
    void testCreateUser4() {
        when(this.userRepository.save((UserModel) any())).thenThrow(new BadRequestException("An error occurred"));
        when(this.userRepository.existsByEmail((String) any())).thenReturn(false);
        when(this.passwordEncoder.encode((CharSequence) any())).thenReturn("secret");
        assertThrows(BadRequestException.class,
                () -> this.userServiceImpl.createUser(new SignUpUserDto("Jane", "Doe", "jane.doe@example.org", "iloveyou")));
        verify(this.userRepository).existsByEmail((String) any());
        verify(this.userRepository).save((UserModel) any());
        verify(this.passwordEncoder).encode((CharSequence) any());
    }
    @Test
    void testLoadUserByUsername() throws UsernameNotFoundException {
        UserModel userModel = new UserModel();
        userModel.setEmail("jane.doe@example.org");
        userModel.setFirstName("Jane");
        userModel.setId(123L);
        userModel.setLastName("Doe");
        userModel.setPassword("iloveyou");
        userModel.setUsername("janedoe");
        Optional<UserModel> ofResult = Optional.of(userModel);
        when(this.userRepository.findByEmail((String) any())).thenReturn(ofResult);
        UserDetails actualLoadUserByUsernameResult = this.userServiceImpl.loadUserByUsername("janedoe");
        assertTrue(actualLoadUserByUsernameResult.getAuthorities().isEmpty());
        assertTrue(actualLoadUserByUsernameResult.isEnabled());
        assertTrue(actualLoadUserByUsernameResult.isCredentialsNonExpired());
        assertTrue(actualLoadUserByUsernameResult.isAccountNonLocked());
        assertTrue(actualLoadUserByUsernameResult.isAccountNonExpired());
        assertEquals("janedoe", actualLoadUserByUsernameResult.getUsername());
        assertEquals("iloveyou", actualLoadUserByUsernameResult.getPassword());
        verify(this.userRepository).findByEmail((String) any());
    }
    @Test
    void testLoadUserByUsername2() throws UsernameNotFoundException {
        when(this.userRepository.findByEmail((String) any())).thenThrow(new BadRequestException("An error occurred"));
        assertThrows(BadRequestException.class, () -> this.userServiceImpl.loadUserByUsername("janedoe"));
        verify(this.userRepository).findByEmail((String) any());
    }
    @Test
    void testLoadUserByUsername3() throws UsernameNotFoundException {
        when(this.userRepository.findByEmail((String) any())).thenReturn(Optional.empty());
        assertThrows(BadRequestException.class, () -> this.userServiceImpl.loadUserByUsername("janedoe"));
        verify(this.userRepository).findByEmail((String) any());
    }
}