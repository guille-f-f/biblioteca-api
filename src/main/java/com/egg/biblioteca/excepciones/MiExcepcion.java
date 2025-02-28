package com.egg.biblioteca.excepciones;

import java.io.Serial;

public class MiExcepcion extends Exception {

    @Serial
    private static final long serialVersionUID = 1L; // Serialización recomendada

    // Constructor con mensaje
    public MiExcepcion(String mensaje) {
        super("Error: " + mensaje); // Modificando directamente el mensaje
    }

    // Constructor con mensaje y causa (opcional)
    public MiExcepcion(String mensaje, Throwable causa) {
        super("Error: " + mensaje, causa); // Modificando directamente el mensaje y pasando la causa
    }
}