package com.cartera.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class MenuAdminController {

    @GetMapping("/menu")
    public String menuAdmin() {
        return "menu_admin"; // archivo menu_admin.html
    }
}
