package com.egg.biblioteca.servicios;

import com.egg.biblioteca.entidades.Autor;
import com.egg.biblioteca.excepciones.MiExcepcion;
import com.egg.biblioteca.repositorios.AutorRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class AutorServicio {
    @Autowired
    private AutorRepositorio autorRepositorio;

    @Transactional
    public void crearAutor(String nombre) throws MiExcepcion {
        validar(nombre);
        Autor autor = new Autor();
        autor.setNombre(nombre);
        autorRepositorio.save(autor);
    }

    @Transactional(readOnly = true)
    public List<Autor> listarAutores() {
        return autorRepositorio.findAll();
    }

    @Transactional
    public void modificarAutor(UUID id, String nombre) throws MiExcepcion {
        validar(nombre);
        Optional<Autor> opcionalAutor = autorRepositorio.findById(id);
        if (opcionalAutor.isPresent()) {
            Autor autor = opcionalAutor.get();
            autor.setNombre(nombre);
            autorRepositorio.save(autor);
        }

    }

    private void validar(String nombre) throws MiExcepcion {
        if (nombre == null || nombre.isEmpty()) {
           throw new MiExcepcion("El nombre no puede ser nulo, ni estár vacío.");
        }
    }

}


