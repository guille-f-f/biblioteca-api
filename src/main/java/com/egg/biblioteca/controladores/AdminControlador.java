package com.egg.biblioteca.controladores;

import com.egg.biblioteca.entidades.Usuario;
import com.egg.biblioteca.excepciones.MiExcepcion;
import com.egg.biblioteca.servicios.UsuarioServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

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
    public String modificarRol(@PathVariable String idUsuario) throws MiExcepcion {
        usuarioServicio.modificarRolDeUsuario(idUsuario);
        return "redirect:/admin/listar-usuarios";
    }

}
