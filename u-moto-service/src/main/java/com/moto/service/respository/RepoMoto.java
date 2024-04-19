package com.moto.service.respository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.moto.service.entity.Moto;

@Repository
public interface RepoMoto extends JpaRepository<Moto, Integer>
{
	List<Moto> findByUsuarioId(int usuarioId);
}
