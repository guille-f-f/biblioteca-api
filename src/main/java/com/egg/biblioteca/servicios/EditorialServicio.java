package com.egg.biblioteca.servicios;

import com.egg.biblioteca.entidades.Editorial;
import com.egg.biblioteca.excepciones.MiExcepcion;
import com.egg.biblioteca.repositorios.EditorialRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class EditorialServicio {

    @Autowired
    private EditorialRepositorio editorialRepositorio;

    @Transactional
    public void crearEditorial(String nombreEditorial) throws MiExcepcion{
        validar(nombreEditorial);
        Editorial editorial = new Editorial();
        editorial.setNombre(nombreEditorial);
        editorialRepositorio.save(editorial);
    }

    @Transactional(readOnly = true)
    public List<Editorial> listarEditoriales() {
        return editorialRepositorio.findAll();
    }

    @Transactional
    public void modificarEditoriales(UUID idEdiorial, String nombreEditorial) throws MiExcepcion {
        validar(nombreEditorial);
        Optional<Editorial> opcionalEditorial = editorialRepositorio.findById(idEdiorial);
        if (opcionalEditorial.isPresent()) {
            Editorial editorial = opcionalEditorial.get();
            editorial.setNombre(nombreEditorial);
            editorialRepositorio.save(editorial);
        }
    }

    @Transactional(readOnly = true)
    public Editorial obtenerEditorialPorId(String idEditorial) throws MiExcepcion {
        validar(idEditorial);
        UUID uuidEditorial = UUID.fromString(idEditorial);
        Optional<Editorial> optionalEditorial = editorialRepositorio.findById(uuidEditorial);
        if (optionalEditorial.isPresent()) {
            return optionalEditorial.get();
        } else {
            throw new MiExcepcion("Editorial no localizada.");
        }
    }

    @Transactional
    public void modificarEditorial(String idEditorial, String nombre) throws MiExcepcion {
        validar(idEditorial);
        validar(nombre);
        UUID uuidEditorial = UUID.fromString(idEditorial);
        Optional<Editorial> optionalEditorial = editorialRepositorio.findById(uuidEditorial);
        if (optionalEditorial.isPresent()) {
            Editorial editorial = optionalEditorial.get();
            editorial.setNombre(nombre);
            editorialRepositorio.save(editorial);
        } else {
            throw new MiExcepcion("Editorial no localizada.");
        }
    }

    private void validar(String parametro) throws MiExcepcion {
        if (parametro == null || parametro.isEmpty()) {
            throw new MiExcepcion("El nombre de la editorial no puede ser nulo, y encontrarse vacío.");
        }
    }
}

