package com.egg.biblioteca.controladores;

import com.egg.biblioteca.entidades.Usuario;
import com.egg.biblioteca.excepciones.MiExcepcion;
import com.egg.biblioteca.servicios.LibroServicio;
import com.egg.biblioteca.servicios.UsuarioServicio;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/")
public class PortalControlador {

    @Autowired
    private UsuarioServicio usuarioServicio;

    @Autowired
    private LibroServicio libroServicio;

    @GetMapping("/")
    public String index(ModelMap modelo) {
        modelo.addAttribute("libros", libroServicio.listarLibros());
        return "index.html";
    }

    @GetMapping("/registro")
    public String registro(@RequestParam(required = false) String error, ModelMap modelo) {
        if (error != null) {
            modelo.put("error", "Usuario o contraseña inválidos.");
        }
        return "registro.html";
    }

    @GetMapping("/login")
    public String login(@RequestParam(required = false) String error, ModelMap modelo) {
        if (error != null) {
            modelo.put("error", "Usuario o Contraseña inválidos!");
        }
        return "login.html";
    }

    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @GetMapping("/inicio")
    public String inicio(HttpSession session) {
        Usuario usuarioLogueado = (Usuario) session.getAttribute("usuariosession");
        if (usuarioLogueado.getRol().toString().equals("ADMIN")) {
            System.out.println("Ingresamos al panel administrador");
            return "redirect:/admin/dashboard";
        }
        return "inicio.html";
    }

    @PostMapping("/registro")
    public String registro(@RequestParam String nombre, @RequestParam String email, @RequestParam String password, @RequestParam String password2, @RequestParam(required = false) MultipartFile file, ModelMap modelo) {
        try {
            System.out.println("Registro de usuario");
            usuarioServicio.registrar(nombre, email, password, password2, file);
            System.out.println("Registro de usuario exitoso");

            modelo.put("exito", "Usuario registrado correctamente.");
            return "redirect:/inicio";
        } catch (MiExcepcion e) {
            modelo.put("error", e.getMessage());
            return "registro.html";
        }
    }

}
