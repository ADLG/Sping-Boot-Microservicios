package com.usuario.service.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.usuario.service.entity.Usuario;
import com.usuario.service.feignclients.CarroFeignClient;
import com.usuario.service.feignclients.MotoFeignClient;
import com.usuario.service.models.Carro;
import com.usuario.service.models.Moto;
import com.usuario.service.repository.RepoUsuario;

@Service
public class SvcUsuario
{
	@Autowired
	private RepoUsuario repoUsuario;

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private CarroFeignClient carroFeignClient;

	@Autowired
	private MotoFeignClient motoFeignClient;

	public List<Carro> getCarros(int usuarioId)
	{
		List<Carro> carros = restTemplate.getForObject("http://localhost:8002/carro/usuario/"+usuarioId, List.class);	
		return carros;
	}

	public List<Moto> getMotos(int usuarioId)
	{
		List<Moto> motos = restTemplate.getForObject("http://localhost:8003/moto/usuario/"+usuarioId, List.class);	
		return motos;
	}

	public Carro saveCarro(int usuarioId, Carro carro)
	{
		carro.setUsuarioId(usuarioId);
		Carro nuevoCarro = carroFeignClient.save(carro);
		return nuevoCarro;
	}

	public Moto saveMoto(int usuarioId, Moto moto)
	{
		moto.setUsuarioId(usuarioId);
		Moto nuevaMoto = motoFeignClient.save(moto);
		return nuevaMoto;
	}

	public Map<String,Object> getUsuarioAndVehiculos(int usuarioId)
	{
		Map<String,Object> resultado = new HashMap<>();
		Usuario usuario = repoUsuario.findById(usuarioId).orElse(null);

		if (usuario == null)
		{
			resultado.put("Mensaje","El usuario no existe");
			return resultado;	
		}
		resultado.put("Usuario",usuario);

		List<Carro> carros = carroFeignClient.getCarros(usuarioId);
		if (carros.isEmpty())
		{
			resultado.put("Carros","El usuario no tiene carros");
		}
		else
		{
			resultado.put("Carros",carros);
		}

		List<Moto> motos = motoFeignClient.getMotos(usuarioId);
		if (motos.isEmpty())
		{
			resultado.put("Motos","El usuario no tiene motos");
		}
		else
		{
			resultado.put("Motos",motos);
		}

		return resultado;
	}
	
	public List<Usuario> getAll()
	{
		return repoUsuario.findAll();
	}

	public Usuario getUsuarioById(int id)
	{
		return repoUsuario.findById(id).orElseThrow(null);
	}

	public Usuario save(Usuario usuario)
	{
		Usuario nuevoUsuario = repoUsuario.save(usuario);
		return nuevoUsuario;
	}
}
