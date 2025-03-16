package com.egg.biblioteca.servicios;

import com.egg.biblioteca.entidades.Imagen;
import com.egg.biblioteca.excepciones.MiExcepcion;
import com.egg.biblioteca.repositorios.ImagenRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

@Service
public class ImagenServicio {

    @Autowired
    ImagenRepositorio imagenRepositorio;

    @Transactional
    public Imagen guardar(MultipartFile file) throws MiExcepcion {
        if (file != null && !file.isEmpty()) {
            try {
                Imagen imagen = new Imagen();
                imagen.setNombre(file.getOriginalFilename());
                imagen.setMime(file.getContentType());
                imagen.setContenido(file.getBytes());
                return imagenRepositorio.save(imagen);
            } catch (IOException e) {
                throw new MiExcepcion("Error al procesar la imagen: " + e.getMessage());
            }
        } else {
            throw new MiExcepcion("Error: documento nulo.");
        }
    }

    public Imagen modificar(UUID idFile, MultipartFile file) throws MiExcepcion, IOException {
        Optional<Imagen> optionalImagen = imagenRepositorio.findById(idFile);
        if (optionalImagen.isPresent()) {
            Imagen imagen = optionalImagen.get();
            imagen.setNombre(file.getOriginalFilename());
            imagen.setMime(file.getContentType());
            imagen.setContenido(file.getBytes());
            imagenRepositorio.save(imagen);
            return imagen;
        } else {
            throw new MiExcepcion("Error: Imagen no encontrada.");
        }
    }

}
