package com.egg.biblioteca.controladores;

import com.egg.biblioteca.servicios.LibroServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequestMapping("/")
public class PortalControlador {

    @Autowired
    private LibroServicio libroServicio;

    @GetMapping("/")
    public String index(ModelMap modelo) {
        modelo.addAttribute("libros", libroServicio.listarLibros());
        return "index.html";
    }

}
