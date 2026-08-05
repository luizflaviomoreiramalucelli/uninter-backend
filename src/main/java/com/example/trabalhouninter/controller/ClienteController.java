package com.example.trabalhouninter.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.trabalhouninter.entity.Cliente;
import com.example.trabalhouninter.repository.ClienteRepository;

@RestController
@RequestMapping({ "/clientes" })
public class ClienteController {
	private ClienteRepository repository;

	ClienteController(ClienteRepository clienteRepository) {
		this.repository = clienteRepository;
	}

	@GetMapping
	public List<?> findAll() {
		return repository.findAll();
	}

	@GetMapping(path = { "/{id}" })
	public ResponseEntity<?> findById(@PathVariable long id) {
		return repository.findById(id).map(record -> ResponseEntity.ok().body(record))
				.orElse(ResponseEntity.notFound().build());
	}

	@PostMapping
	public Cliente create(@RequestBody Cliente contato) {
		return repository.save(contato);
	}

	@PutMapping(value = "/{id}")
	public ResponseEntity<?> updatePut(@PathVariable("id") long id, @RequestBody Cliente cliente) {
		return repository.findById(id).map(record -> {
			record.setNome(cliente.getNome());
			record.setEmail(cliente.getEmail());
			record.setEndereco(cliente.getEndereco());
			record.setCpf(cliente.getCpf());
			Cliente updated = repository.save(record);
			return ResponseEntity.ok().body(updated);
		}).orElse(ResponseEntity.notFound().build());
	}

	@PatchMapping(value = "/{id}")
	public ResponseEntity<?> updatePatch(@PathVariable("id") long id, @RequestBody Cliente cliente) {
		return repository.findById(id).map(record -> {
			if (cliente.getNome() != null) {
				record.setNome(cliente.getNome());
			}
			if (cliente.getEmail() != null) {
				record.setEmail(cliente.getEmail());
			}
			if (cliente.getEndereco() != null) {
				record.setEndereco(cliente.getEndereco());
			}
			if (cliente.getCpf() != null) {
				record.setCpf(cliente.getCpf());
			}
			Cliente updated = repository.save(record);
			return ResponseEntity.ok().body(updated);
		}).orElse(ResponseEntity.notFound().build());
	}

	@DeleteMapping(path = { "/{id}" })
	public ResponseEntity<?> delete(@PathVariable long id) {
		return repository.findById(id).map(record -> {
			repository.deleteById(id);
			return ResponseEntity.ok().build();
		}).orElse(ResponseEntity.notFound().build());
	}

}