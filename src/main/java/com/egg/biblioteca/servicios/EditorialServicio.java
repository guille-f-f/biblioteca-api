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

    private void validar(String nombreEditorial) throws MiExcepcion {
        if (nombreEditorial == null || nombreEditorial.isEmpty()) {
            throw new MiExcepcion("El nombre de la editorial no puede ser nulo, y encontrarse vacío.");
        }
    }
}

