package com.egg.biblioteca.controladores;

import com.egg.biblioteca.enumeraciones.Rol;
import com.egg.biblioteca.excepciones.MiExcepcion;
import com.egg.biblioteca.servicios.UsuarioServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
@RequestMapping("/admin")
public class AdminControlador {

    @Autowired
    UsuarioServicio usuarioServicio;

    @GetMapping("/dashboard")
    public String panelAdministrativo() {
        return "panel.html";
    }

    @GetMapping("/listar-usuarios")
    public String listarUsuarios(ModelMap modelo) {
        modelo.addAttribute("usuarios", usuarioServicio.listarUsuarios());
        return "usuario_list.html";
    }

    @GetMapping("/usuario/modificar-rol/{idUsuario}")
    public String modificarRol(@PathVariable String idUsuario) throws MiExcepcion, IOException {
        usuarioServicio.modificarRolDeUsuario(idUsuario);
        return "redirect:/admin/listar-usuarios";
    }

    @PostMapping("/usuario/modificar/{idUsuario}")
    public String modificarUsuario(@PathVariable String id, @RequestParam String nombre, @RequestParam String email, @RequestParam String password, @RequestParam Rol rol, @RequestParam(required = false) MultipartFile file) throws MiExcepcion, IOException {
        usuarioServicio.modificarUsuario(id, nombre, email, password, rol, file);
        return "redirect:/admin/listar-usuarios";
    }
}