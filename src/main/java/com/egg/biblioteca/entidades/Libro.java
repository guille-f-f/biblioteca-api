package com.egg.biblioteca.entidades;

import java.util.Date;

import jakarta.persistence.*;

@Entity
@Table(name = "libro")
public class Libro {
    @Id
    private Long isbn;
    private String titulo;
    private Integer ejemplares;
    @Column(length = 1500) // Aumenta el tamaño del VARCHAR a 1500 caracteres
    private String imagen;

    @Temporal(TemporalType.DATE)
    private Date alta;

    @ManyToOne
    private Autor autor;

    @ManyToOne
    private Editorial editorial;
    
    public Libro() {
    }

    public Long getIsbn() {
        return isbn;
    }

    public void setIsbn(Long isbn) {
        this.isbn = isbn;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Integer getEjemplares() {
        return ejemplares;
    }

    public void setEjemplares(Integer ejemplares) {
        this.ejemplares = ejemplares;
    }

    public Date getAlta() {
        return alta;
    }

    public void setAlta(Date alta) {
        this.alta = alta;
    }

    public Editorial getEditorial() {
        return editorial;
    }

    public void setEditorial(Editorial editorial) {
        this.editorial = editorial;
    }

    public Autor getAutor() {
        return autor;
    }

    public void setAutor(Autor autor) {
        this.autor = autor;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    @Override
    public String toString() {
        return "Libro [isbn=" + isbn + ", titulo=" + titulo + ", ejemplares=" + ejemplares + ", alta=" + alta
                + ", autor=" + autor + ", editorial=" + editorial + ", imagen= " + imagen + "]";
    }
}
