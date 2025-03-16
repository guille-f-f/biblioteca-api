package com.egg.biblioteca.entidades;

import com.egg.biblioteca.enumeraciones.Rol;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "usuario")
@Getter // Genera automáticamente los métodos getter para todos los atributos de la clase.
@Setter // Genera automáticamente los métodos setter para todos los atributos de la clase.
@NoArgsConstructor // Genera un constructor sin argumentos. Es útil en JPA y frameworks como Spring, donde se necesita un constructor vacío.
@AllArgsConstructor // Genera un constructor con todos los atributos de la clase.
@Builder // Permite construir objetos de manera más elegante usando el patrón Builder.
//Usuario usuario = Usuario.builder().nombre("Juan").edad(30).build();
public class Usuario {
    @Id
    @GeneratedValue(generator = "UUID")  // Generador de UUID en JPA
    @Column(updatable = false, nullable = false) // Evita que el ID sea modificado después de la creación.
    private UUID id;
    private String nombre;
    private String email;
    private String password;
    @Enumerated(EnumType.STRING) // Almacena el nombre del ENUM como texto en la BD
    private Rol rol;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "imagen_id")
    private Imagen imagen;

    @PrePersist // Se usa para asignar un UUID antes de que la entidad sea insertada en la base de datos.
    public void prePersist() {
        if (id == null) {
            id = UUID.randomUUID();  // Genera UUID antes de insertar en BD
        }
    }
}
