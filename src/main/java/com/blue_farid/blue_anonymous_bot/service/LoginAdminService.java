package com.blue_farid.blue_anonymous_bot.service;

import com.blue_farid.blue_anonymous_bot.dto.LoginDto;
import com.blue_farid.blue_anonymous_bot.dto.LoginResponseDto;
import com.blue_farid.blue_anonymous_bot.exception.LoginException;
import com.blue_farid.blue_anonymous_bot.utils.HttpBasicTokenUtils;
import com.blue_farid.blue_anonymous_bot.utils.PasswordUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginAdminService {
    private final UserDetailsService userDetailsService;

    private final HttpBasicTokenUtils tokenUtils;

    private final PasswordUtils passwordUtils;

    public LoginResponseDto login(LoginDto dto) throws LoginException {
        UserDetails userDetails = userDetailsService.loadUserByUsername(dto.getUsername());
        if (passwordUtils.isMatch(dto.getPassword(), userDetails.getPassword())) {
            return new LoginResponseDto(tokenUtils.build(dto.getUsername(), dto.getPassword()),  "Login successful!");
        } else {
            throw new LoginException();
        }
    }
}
