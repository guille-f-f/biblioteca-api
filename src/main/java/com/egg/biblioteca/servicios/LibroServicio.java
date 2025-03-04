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
    public void crearLibro(Long isbn, Integer ejemplares, String titulo, UUID idAutor, UUID idEditorial) throws MiExcepcion {

        validar(isbn, ejemplares, titulo, idAutor, idEditorial);

        Libro libro = new Libro();

        libro.setIsbn(isbn);
        libro.setEjemplares(ejemplares);
        libro.setTitulo(titulo);
        libro.setAlta(new Date());
        Optional<Autor> optionalAutor = autorRepositorio.findById(idAutor);
        if (optionalAutor.isPresent()) {
            Autor autor = optionalAutor.get();
            libro.setAutor(autor);
        }

        Optional<Editorial> optionalEditorial = editorialRepositorio.findById(idEditorial);
        if (optionalEditorial.isPresent()) {
            Editorial editorial = optionalEditorial.get();
            libro.setEditorial(editorial);
        }

        libroRepositorio.save(libro);
    }

    @Transactional(readOnly = true)
    public List<Libro> listarLibros() {
        return libroRepositorio.findAll();
    }

    @Transactional
    public void modificarLibro(Long isbn, String titulo, Integer ejemplares, UUID idAutor, UUID idEditorial) throws MiExcepcion {

        validar(isbn, ejemplares, titulo, idAutor, idEditorial);

        Optional<Libro> optionalLibro = libroRepositorio.findById(isbn);

        if (optionalLibro.isPresent()) {
            Libro libro = optionalLibro.get();
            libro.setTitulo(titulo);
            libro.setEjemplares(ejemplares);

            // Buscar autor y asignarlo si está presente
            libro.setAutor(autorRepositorio.findById(idAutor)
                    .orElseThrow(() -> new MiExcepcion("Autor inexistente.")));

            // Buscar editorial y asignarla si está presente
            libro.setEditorial(editorialRepositorio.findById(idEditorial)
                    .orElseThrow(() -> new MiExcepcion("Editorial inexistente.")));

            libroRepositorio.save(libro);
        } else {
            throw new EntityNotFoundException("Libro con ISBN " + isbn + " no encontrado.");
        }
    }

    private void validar(Long isbn, Integer ejemplares, String titulo, UUID idAutor, UUID idEditorial) throws MiExcepcion {
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

        if (idEditorial == null) {
            throw new MiExcepcion("El id de la editorial no puede ser nulo o estár vacío.");
        }
    }

}

