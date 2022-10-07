package com.blue_farid.blue_anonymous_bot.controller;

import com.blue_farid.blue_anonymous_bot.service.ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/blue-anonymous-bot/admin")
@RestController
@RequiredArgsConstructor
public class AdminController {
    private final ClientService clientService;

    @PutMapping("set")
    public void addAdmin(long id, boolean value) {
        clientService.setAdmin(clientService.getClientById(id), value);
    }
}
