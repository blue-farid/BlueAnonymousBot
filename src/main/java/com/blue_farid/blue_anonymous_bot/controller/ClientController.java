package com.blue_farid.blue_anonymous_bot.controller;

import com.blue_farid.blue_anonymous_bot.service.ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class ClientController {
    private final ClientService clientService;

    @GetMapping("/admin/clients")
    public String listEntities(Model model) {
        model.addAttribute("clients", clientService.getClients());
        return "clients";
    }
}
