package com.egg.biblioteca.controladores;

import com.egg.biblioteca.entidades.Imagen;
import com.egg.biblioteca.entidades.Usuario;
import com.egg.biblioteca.excepciones.MiExcepcion;
import com.egg.biblioteca.servicios.ImagenServicio;
import com.egg.biblioteca.servicios.UsuarioServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
@RequestMapping("/imagen")
public class ImagenControlador {

    @Autowired
    private ImagenServicio imagenServicio;

    @Autowired
    private UsuarioServicio usuarioServicio;

    @GetMapping("/perfil/{idUsuario}")
    public ResponseEntity<byte[]> imagenUsuario(@PathVariable String idUsuario) throws MiExcepcion {
        Usuario usuario = usuarioServicio.buscarUsuarioPorId(idUsuario);

        if (usuario.getImagen() != null && usuario.getImagen().getContenido() != null) {
            byte[] imagen = usuario.getImagen().getContenido();
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.setContentType(MediaType.IMAGE_JPEG);
            return new ResponseEntity<>(imagen, httpHeaders, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

    @PostMapping("/perfil/{idUsuario}")
    public ResponseEntity<String> actualizar(@PathVariable String idUsuario, MultipartFile file) throws MiExcepcion, IOException {
        if (file == null || file.isEmpty()) {
            return new ResponseEntity<>("No se ha enviado una imagen válida.", HttpStatus.BAD_REQUEST);
        }

        Usuario usuario = usuarioServicio.buscarUsuarioPorId(idUsuario);

        if (usuario.getImagen() != null && usuario.getImagen().getId() != null) {
            imagenServicio.modificar(usuario.getImagen().getId(), file);
        } else {
            usuarioServicio.cargarFoto(usuario, file);
        }
        return new ResponseEntity<>("Imagen actualizada correctamente.", HttpStatus.OK);
    }

}
