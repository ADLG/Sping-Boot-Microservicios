package com.moto.service.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.moto.service.entity.Moto;
import com.moto.service.respository.RepoMoto;

@Service
public class SvcMoto
{
	@Autowired
	private RepoMoto repoMoto;
	
	public List<Moto> getAll()
	{
		return repoMoto.findAll();
	}

	public Moto getMotoById(int id)
	{
		return repoMoto.findById(id).orElseThrow(null);
	}

	public Moto save(Moto moto)
	{
		Moto nuevoMoto = repoMoto.save(moto);
		return nuevoMoto;
	}
	
	public List<Moto> byUsuarioId(int usuarioId)
	{
		return repoMoto.findByUsuarioId(usuarioId);
	}
}
