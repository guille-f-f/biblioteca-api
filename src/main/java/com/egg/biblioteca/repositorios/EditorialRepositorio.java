package com.egg.biblioteca.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.egg.biblioteca.entidades.Editorial;

import java.util.UUID;

@Repository
public interface EditorialRepositorio extends JpaRepository<Editorial, UUID> {

}
