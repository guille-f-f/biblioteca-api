package com.egg.biblioteca.servicios;

import com.egg.biblioteca.entidades.Usuario;
import com.egg.biblioteca.enumeraciones.Rol;
import com.egg.biblioteca.excepciones.MiExcepcion;
import com.egg.biblioteca.repositorios.UsuarioRepositorio;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UsuarioServicio implements UserDetailsService {

    @Autowired
    private UsuarioRepositorio usuarioRepositorio;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Transactional
    public String registrar(String nombre, String email, String password, String password2) throws MiExcepcion {
        validar(nombre, email, password, password2);

        if (usuarioRepositorio.buscarPorEmail(email).isPresent()) {
            throw new MiExcepcion("El email ya está registrado.");
        }
        Usuario usuario = new Usuario();
        usuario.setNombre(nombre);
        usuario.setEmail(email);
        usuario.setPassword(passwordEncoder.encode(password)); // Cifrado de contraseña
        usuario.setRol(Rol.USER); // Asignación del ENUM Rol
        usuarioRepositorio.save(usuario);

        return "Usuario cargado exitosamente.";
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        // Buscar el usuario en la base de datos por su email
        // Si no se encuentra, se lanza una excepción UsernameNotFoundException
        Usuario usuario = usuarioRepositorio.buscarPorEmail(email).orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado con email: " + email));

        // Crear una lista para almacenar los roles/permisos del usuario
        List<GrantedAuthority> permisos = new ArrayList<>();

        // Convertir el rol del usuario en un permiso reconocido por Spring Security
        permisos.add(new SimpleGrantedAuthority("ROLE_" + usuario.getRol().toString()));

        // Obtener el request actual para acceder a la sesión del usuario
        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();

        // Obtener la sesión del usuario; si no existe, se crea una nueva
        HttpSession session = attr.getRequest().getSession(true);

        // Guardar el usuario en la sesión con el atributo "usuariosession"
        session.setAttribute("usuariosession", usuario);

        // Retornar un objeto User de Spring Security con email, contraseña y permisos
        return new User(usuario.getEmail(), usuario.getPassword(), permisos);
    }

    private void validar(String nombre, String email, String password, String password2) throws MiExcepcion {
        if (nombre.isEmpty() || nombre == null) {
            throw new MiExcepcion("el nombre no puede ser nulo o estar vacío");
        }
        if (email.isEmpty() || email == null) {
            throw new MiExcepcion("el email no puede ser nulo o estar vacío");
        }
        if (password.isEmpty() || password == null || password.length() <= 5) {
            throw new MiExcepcion("La contraseña no puede estar vacía, y debe tener más de 5 dígitos");
        }
        if (!password.equals(password2)) {
            throw new MiExcepcion("Las contraseñas ingresadas deben ser iguales");
        }
    }

}
