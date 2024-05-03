package com.usuario.service.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.usuario.service.entity.Usuario;
import com.usuario.service.models.Carro;
import com.usuario.service.models.Moto;
import com.usuario.service.service.SvcUsuario;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;

@RestController
@RequestMapping("/usuario")
public class CtrlUsuario
{
	@Autowired
	private SvcUsuario svcUsuario;

	@GetMapping
	public ResponseEntity<List<Usuario>> listarUsuarios()
	{
		List<Usuario> usuarios = svcUsuario.getAll();
		if (usuarios.isEmpty())
		{
			return ResponseEntity.noContent().build();	
		}
		return ResponseEntity.ok(usuarios);
	}

	@GetMapping("/{id}")
	public ResponseEntity<Usuario> obtenerUsuario(@PathVariable("id") int id)
	{
		Usuario usuario = svcUsuario.getUsuarioById(id);
		if (usuario == null)
		{
			return ResponseEntity.notFound().build();	
		}
		return ResponseEntity.ok(usuario);
	}

	@PostMapping
	public ResponseEntity<Usuario> guardarUsuario(@RequestBody Usuario usuario)
	{
		Usuario nuevoUsuario = svcUsuario.save(usuario);
		return ResponseEntity.ok(nuevoUsuario);
	}

	@CircuitBreaker(name = "carrosCB", fallbackMethod = "fallbackGetCarros")
	@GetMapping("/carros/{usuarioId}")
	public ResponseEntity<List<Carro>> listarCarros(@PathVariable("usuarioId") int id)
	{
		Usuario usuario = svcUsuario.getUsuarioById(id);
		if (usuario == null)
		{
			return ResponseEntity.notFound().build();	
		}

		List<Carro> carros = svcUsuario.getCarros(id);
		return ResponseEntity.ok(carros);
	}
	
	@GetMapping("/motos/{usuarioId}")
	public ResponseEntity<List<Moto>> listarMotos(@PathVariable("usuarioId") int id)
	{
		Usuario usuario = svcUsuario.getUsuarioById(id);
		if (usuario == null)
		{
			return ResponseEntity.notFound().build();	
		}

		List<Moto> motos = svcUsuario.getMotos(id);
		return ResponseEntity.ok(motos);
	}
	
	@CircuitBreaker(name = "carrosCB", fallbackMethod = "fallbackSaveCarro")
	@PostMapping("/carro/{usuarioId}")
	public ResponseEntity<Carro> guardarCarro(@PathVariable("usuarioId") int usuarioId, @RequestBody Carro carro)
	{
		Carro nuevoCarro = svcUsuario.saveCarro(usuarioId,carro);
		return ResponseEntity.ok(nuevoCarro);
	}

	@CircuitBreaker(name = "motosCB", fallbackMethod = "fallbackSaveMoto")
	@PostMapping("/moto/{usuarioId}")
	public ResponseEntity<Moto> guardarMoto(@PathVariable("usuarioId") int usuarioId, @RequestBody Moto moto)
	{
		Moto nuevaMoto = svcUsuario.saveMoto(usuarioId,moto);
		return ResponseEntity.ok(nuevaMoto);
	}

	@CircuitBreaker(name = "todosCB", fallbackMethod = "fallbackGetTodos")
	@GetMapping("/todos/{usuarioId}")
	public ResponseEntity<Map<String,Object>> listarTodosLosVehiculos(@PathVariable("usuarioId") int usuarioId)
	{
		Map<String,Object> resultado = svcUsuario.getUsuarioAndVehiculos(usuarioId);
		return ResponseEntity.ok(resultado);
	}

	private ResponseEntity<List<Carro>> fallbackGetCarros(@PathVariable("usuarioId") int id, RuntimeException runTimEx)
	{
		return new ResponseEntity("El usuario : "+id+"tiene los carros en el taller",HttpStatus.OK);
	}
	
	private ResponseEntity<List<Carro>> fallbackSaveCarro(@PathVariable("usuarioId") int id, @RequestBody Carro carro, RuntimeException runTimEx)
	{
		return new ResponseEntity("El usuario : "+id+"no tiene dinero para los carros",HttpStatus.OK);
	}

	private ResponseEntity<List<Moto>> fallbackGetMotos(@PathVariable("usuarioId") int id, RuntimeException runTimEx)
	{
		return new ResponseEntity("El usuario : "+id+"tiene las motos en el taller",HttpStatus.OK);
	}
	
	private ResponseEntity<List<Moto>> fallbackSaveMoto(@PathVariable("usuarioId") int id, @RequestBody Moto moto, RuntimeException runTimEx)
	{
		return new ResponseEntity("El usuario : "+id+"no tiene dinero para las motos",HttpStatus.OK);
	}

	private ResponseEntity<List<Moto>> fallbackGetTodos(@PathVariable("usuarioId") int id, RuntimeException runTimEx)
	{
		return new ResponseEntity("El usuario : "+id+"tiene los vehuculos en el taller",HttpStatus.OK);
	}
}