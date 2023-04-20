package com.blue_farid.blue_anonymous_bot.controller.admin;


import com.blue_farid.blue_anonymous_bot.dto.LoginDto;
import com.blue_farid.blue_anonymous_bot.dto.LoginResponseDto;
import com.blue_farid.blue_anonymous_bot.service.LoginAdminService;
import com.blue_farid.blue_anonymous_bot.utils.HttpBasicTokenUtils;
import com.blue_farid.blue_anonymous_bot.utils.PasswordUtils;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/blue-anonymous-bot/admin/login")
public class LoginAdminController {

    private final LoginAdminService service;

    @PostMapping
    public ResponseEntity<LoginResponseDto> login(@RequestBody  LoginDto dto) {
        try {
            return ResponseEntity.ok(service.login(dto));
        } catch (Exception e) {
            return ResponseEntity.status(401).body(new LoginResponseDto(null, e.getMessage()));
        }
    }

}
