package com.github.arlekinside.diploma.ws.controller;

import com.github.arlekinside.diploma.data.entity.User;
import com.github.arlekinside.diploma.ws.config.security.user.UserDetailsAdapter;
import com.github.arlekinside.diploma.ws.dto.UserDTO;
import com.github.arlekinside.diploma.logic.exception.UserExistsException;
import com.github.arlekinside.diploma.logic.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<UserDTO> register(@RequestBody UserDTO userDTO) {
        try {
            var user = userService.registerUser(userDTO.getUsername(), userDTO.getPassword());
            userDTO.setPassword(null);
            return ResponseEntity.ok(userDTO);
        } catch (UserExistsException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping
    public ResponseEntity<User> getCurrentUser(Authentication authentication) {
        var user = ((UserDetailsAdapter) authentication.getPrincipal()).user();

        return ResponseEntity.ok(user);
    }
}
