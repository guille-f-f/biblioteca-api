package com.egg.biblioteca.entidades;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "imagen")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Imagen {
    @Id
    @GeneratedValue(generator = "UUID")
    private UUID id;

    private String mime;
    private String nombre;

    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(columnDefinition = "LONGBLOB")
    private byte[] contenido;

    @PrePersist // Se usa para asignar un UUID antes de que la entidad sea insertada en la base de datos.
    public void prePersist() {
        if (id == null) {
            id = UUID.randomUUID();  // Genera UUID antes de insertar en BD
        }
    }
}
