package com.egg.biblioteca.controladores;

import com.egg.biblioteca.entidades.Autor;
import com.egg.biblioteca.excepciones.MiExcepcion;
import com.egg.biblioteca.servicios.AutorServicio;

import io.micrometer.common.lang.NonNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

@Controller
@RequestMapping("/autor") // localhost:8080/autor
public class AutorControlador {

    @Autowired
    private AutorServicio autorServicio;

    @GetMapping("/registrar") // localhost:8080/autor/registrar
    public String registrar() {
        return "autor_form.html";
    }

    @PostMapping("/registro")
    public String registro(@RequestParam("nombre") String nombre) throws MiExcepcion {
        try {
            autorServicio.crearAutor(nombre);
        } catch (MiExcepcion e) {
            System.out.println(nombre);
            Logger.getLogger(AutorControlador.class.getName()).log(Level.SEVERE, null, e);
            return "autor_form.html";
        }
        return "index.html";
    }

    @GetMapping("/lista")
    public String listar(ModelMap modelo) {
        List<Autor> autores = autorServicio.listarAutores();
        modelo.addAttribute("autores", autores);
        return "autor_list.html";
    }

    @GetMapping("/modificar/{id}")
    public String modificar(@PathVariable String id, ModelMap modelo) {
        Autor autor = autorServicio.obtenerAutorPorId(id);
        modelo.addAttribute("autor", autor);
        return "autor_modificar.html";
    }

    @PostMapping("/modificar")
    public String modificar(@RequestParam String id, @RequestParam String nombre, ModelMap modelo) {
        try {
            UUID uuid = UUID.fromString(id);
            autorServicio.modificarAutor(uuid, nombre);
            return "redirect:../lista";
        } catch (MiExcepcion e) {
            modelo.put("error", e.getMessage());
            return "autor_modificar.html";
        }
    }
}