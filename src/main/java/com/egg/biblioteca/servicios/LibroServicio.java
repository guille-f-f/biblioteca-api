package com.egg.biblioteca.servicios;

import com.egg.biblioteca.entidades.Autor;
import com.egg.biblioteca.entidades.Editorial;
import com.egg.biblioteca.entidades.Libro;
import com.egg.biblioteca.excepciones.MiExcepcion;
import com.egg.biblioteca.repositorios.AutorRepositorio;
import com.egg.biblioteca.repositorios.EditorialRepositorio;
import com.egg.biblioteca.repositorios.LibroRepositorio;
import jakarta.persistence.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.swing.text.html.Option;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class LibroServicio {

    @Autowired
    private LibroRepositorio libroRepositorio;

    @Autowired
    private AutorRepositorio autorRepositorio;

    @Autowired
    private EditorialRepositorio editorialRepositorio;

    @Transactional
    public void crearLibro(Long isbn, Integer ejemplares, String titulo, UUID idAutor, String idEditorial) {
        Optional<Autor> optionalAutor = autorRepositorio.findById(idAutor);
        if (optionalAutor.isPresent()) {
            Autor autor = optionalAutor.get();
        }
        Editorial editorial = editorialRepositorio.findById(idEditorial).get();
        Libro libro = new Libro();

        libro.setIsbm(isbn);
        libro.setEjemplares(ejemplares);
        libro.setTitulo(titulo);
        libro.setAlta(new Date());

        libroRepositorio.save(libro);
    }

    @Transactional(readOnly = true)
    public List<Libro> listarLibros() {
        return libroRepositorio.findAll();
    }

    @Transactional
    public void modificarLibro(Long isbn, String titulo, Integer ejemplares, UUID idAutor, String idEditorial) {
        validar(isbn, titulo, ejemplares, idAutor, idEditorial);
        Optional<Libro> opcionalLibro = libroRepositorio.findById(isbn);
        if (opcionalLibro.isPresent()) {
            Libro libro = opcionalLibro.get();
            libro.setTitulo(titulo);
            libro.setEjemplares(ejemplares);
            // Buscar autor y asignarlo si está presente
            autorRepositorio.findById(idAutor).ifPresentOrElse(libro::setAutor,
                    () -> { throw new MiExcepcion("Autor inexistente."); });

            // Buscar editorial y asignarla si está presente
            editorialRepositorio.findById(idEditorial).ifPresentOrElse(libro::setEditorial,
                    () -> { throw new MiExcepcion("Editorial inexistente."); });
            libroRepositorio.save(libro);
        } else {
            throw new EntityNotFoundException("Libro con ISBN " + isbn + " no encontrado.");
        }
    }

    private void validar(Long isbn, Integer ejemplares, String titulo, UUID idAutor, String idEditorial) throws MiExcepcion {
        if (isbn == null || isbn < 0) {
            throw new MiExcepcion("El número de ISBN no puede ser nulo o negativo.");
        }

        if (ejemplares < 0) {
            throw new MiExcepcion("El número de ejemplares debe ser positivo.");
        }

        if (titulo == null || titulo.isEmpty()) {
            throw new MiExcepcion("El titulo del libro es nulo o se encuentra vacío.");
        }

        if (idAutor == null) {
            throw new MiExcepcion("El id del autor no puede ser nulo.");
        }

        if (idEditorial == null || idEditorial.isEmpty()) {
            throw new MiExcepcion("El id de la editorial no puede ser nulo o estár vacío.");
        }
    }

}

