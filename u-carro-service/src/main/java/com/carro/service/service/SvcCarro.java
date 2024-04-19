package com.carro.service.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.carro.service.entity.Carro;
import com.carro.service.repository.RepoCarro;

@Service
public class SvcCarro
{
	@Autowired
	private RepoCarro repoCarro;
	
	public List<Carro> getAll()
	{
		return repoCarro.findAll();
	}

	public Carro getCarroById(int id)
	{
		return repoCarro.findById(id).orElseThrow(null);
	}

	public Carro save(Carro carro)
	{
		Carro nuevoCarro = repoCarro.save(carro);
		return nuevoCarro;
	}
	
	public List<Carro> byUsuarioId(int usuarioId)
	{
		return repoCarro.findByUsuarioId(usuarioId);
	}
}
