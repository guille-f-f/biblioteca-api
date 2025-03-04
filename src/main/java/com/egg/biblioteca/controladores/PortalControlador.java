package com.egg.biblioteca.controladores;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequestMapping("/")
public class PortalControlador {

    @GetMapping("/")
    public String index() {
        return "index.html";
    }

}
