package com.egg.biblioteca.controladores;

import com.egg.biblioteca.excepciones.MiExcepcion;
import com.egg.biblioteca.servicios.EditorialServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.logging.Logger;
import java.util.logging.Level;

@Controller
@RequestMapping("/editorial")
public class EditorialControlador {

    @Autowired
    EditorialServicio editorialServicio;

    @GetMapping("/registrar")
    public String registrar() {
        return "editorial_form.html";
    }

    @PostMapping("/registro")
    public String registro(@RequestParam("nombre") String nombre, ModelMap modelo) throws MiExcepcion {
        try {
            editorialServicio.crearEditorial(nombre);
            modelo.put("exito", "Editorial cargada exitosamente!");
        } catch (MiExcepcion e) {
            modelo.put("error", e.getMessage());
            Logger.getLogger(EditorialControlador.class.getName()).log(Level.SEVERE, null, e);
            return "editorial_form.html";
        }
        return "/index.html";
    }

    @GetMapping("/lista")
    public String listar(ModelMap modelo) {
        modelo.addAttribute("editoriales", editorialServicio.listarEditoriales());
        return "editorial_list.html";
    }

    @GetMapping("/modificar/{id}")
    public String modificar(@PathVariable String id, ModelMap modelo) throws MiExcepcion {
        modelo.addAttribute("editorial", editorialServicio.obtenerEditorialPorId(id));
        return "editorial_modificar.html";
    }

    @PostMapping("/modificar")
    public String modificar(@RequestParam String id, @RequestParam String nombre, ModelMap modelo) throws MiExcepcion {
        System.out.println("Ingresamos al controlador");
        try {
            editorialServicio.modificarEditorial(id, nombre);
            modelo.put("exito", "Editorial modificada.");
        } catch (MiExcepcion e) {
            modelo.put("error", e.getMessage());
            return "editorial_modificar.html";
        }
        return "redirect:/editorial/lista";
    }

}
