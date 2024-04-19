package com.carro.service.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.carro.service.entity.Carro;

public interface RepoCarro extends JpaRepository<Carro, Integer>
{
	List<Carro> findByUsuarioId(int usuarioId);
}