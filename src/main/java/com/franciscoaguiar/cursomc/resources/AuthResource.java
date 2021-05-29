package com.franciscoaguiar.cursomc.resources;

import com.franciscoaguiar.cursomc.dto.EmailDTO;
import com.franciscoaguiar.cursomc.security.JWTUtil;
import com.franciscoaguiar.cursomc.security.UserSS;
import com.franciscoaguiar.cursomc.services.AuthService;
import com.franciscoaguiar.cursomc.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
@RequestMapping(value = "/auth")
public class AuthResource {

    @Autowired
    private JWTUtil jwtUtil;

    @Autowired
    private AuthService service;

    @PostMapping(value = "/refresh_token")
    public ResponseEntity<Void> refreshToken(HttpServletResponse response){
        UserSS user = UserService.authenticated();
        String token = jwtUtil.generateToken(user.getUsername());
        response.addHeader("Authorization", "Bearer " + token);
        return ResponseEntity.noContent().build();
    }

    @PostMapping(value = "/forgot")
    public ResponseEntity<Void> forgot(@RequestBody @Valid EmailDTO objDto){
        service.sendNewPassword(objDto.getEmail());
        return ResponseEntity.noContent().build();
    }
}
