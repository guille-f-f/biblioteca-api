package com.egg.biblioteca.controladores;

import com.egg.biblioteca.entidades.Autor;
import com.egg.biblioteca.entidades.Editorial;
import com.egg.biblioteca.entidades.Libro;
import com.egg.biblioteca.excepciones.MiExcepcion;
import com.egg.biblioteca.servicios.AutorServicio;
import com.egg.biblioteca.servicios.EditorialServicio;
import com.egg.biblioteca.servicios.LibroServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

@Controller
@RequestMapping("/libro")
public class LibroControlador {
    @Autowired
    private LibroServicio libroServicio;

    @Autowired
    private EditorialServicio editorialServicio;

    @Autowired
    private AutorServicio autorServicio;

    @GetMapping("/registrar")
    public String registrar(ModelMap modelo) {

        List<Libro> libros = libroServicio.listarLibros();
        List<Autor> autores = autorServicio.listarAutores();
        List<Editorial> editoriales = editorialServicio.listarEditoriales();

        modelo.addAttribute("libros", libros);
        modelo.addAttribute("autores", autores);
        modelo.addAttribute("editoriales", editoriales);
        return "libro_form.html";
    }

    @PostMapping("/registro")
    public String registro(@RequestParam(required = false) Long isbn, @RequestParam String titulo, @RequestParam String imagen, @RequestParam(required = false) Integer ejemplares, @RequestParam(required = false) String idAutor, @RequestParam(required = false) String idEditorial, ModelMap modelo) {
        try {
            // Pars string a uuid
            UUID idAutorParseUUID = UUID.fromString(idAutor);
            UUID idEditorialParseUUID = UUID.fromString(idEditorial);
            libroServicio.crearLibro(isbn, ejemplares, titulo, imagen, idAutorParseUUID, idEditorialParseUUID);
            modelo.put("exito", "Libro cargado exitosamente!");
        } catch (MiExcepcion e) {
            modelo.put("error", e.getMessage());
            Logger.getLogger(LibroControlador.class.getName()).log(Level.SEVERE, null, e);
            List<Libro> libros = libroServicio.listarLibros();
            List<Autor> autores = autorServicio.listarAutores();
            List<Editorial> editoriales = editorialServicio.listarEditoriales();
            modelo.addAttribute("libros", libros);
            modelo.addAttribute("autores", autores);
            modelo.addAttribute("editoriales", editoriales);
            return "libro_form.html";
        }

        return "index.html";
    }

    @GetMapping("/lista")
    public String listar(ModelMap modelo) {
        modelo.addAttribute("libros", libroServicio.listarLibros());
        return "libro_list.html";
    }

    @GetMapping("/modificar/{isbn}")
    public String modificar(@PathVariable String isbn, ModelMap modelo) throws MiExcepcion {
        modelo.addAttribute("libro", libroServicio.obtenerLibroPorId(isbn));
        modelo.addAttribute("autores", autorServicio.listarAutores());
        modelo.addAttribute("editoriales", editorialServicio.listarEditoriales());
        return "libro_modificar.html";
    }

    @PostMapping("/modificar")
    public String modificar(@RequestParam String isbn, @RequestParam String titulo, @RequestParam(required = false, defaultValue = "1") Integer ejemplares, @RequestParam String imagen, @RequestParam String idAutor, @RequestParam String idEditorial, ModelMap modelo) throws MiExcepcion {
        try {
            libroServicio.modificarLibro(isbn, titulo, ejemplares, imagen, idAutor, idEditorial);
            modelo.put("exito", "Libro modificado.");
        } catch (MiExcepcion e) {
            modelo.put("error", e.getMessage());
            return "libro_modificar.html";
        }
        return "redirect:/libro/lista";
    }

}
