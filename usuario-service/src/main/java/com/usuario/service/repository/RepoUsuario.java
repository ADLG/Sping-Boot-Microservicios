package com.usuario.service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.usuario.service.entity.Usuario;

@Repository
public interface RepoUsuario extends JpaRepository<Usuario, Integer>
{

}
