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

import com.example.trabalhouninter.entity.Produto;
import com.example.trabalhouninter.repository.ProdutoRepository;


@RestController
@RequestMapping({ "/produtos" })
public class ProdutoController {
	private ProdutoRepository repository;

	ProdutoController(ProdutoRepository produtoRepository) {
		this.repository = produtoRepository;
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
	public Produto create(@RequestBody Produto produto) {
		return repository.save(produto);
	}

	@PutMapping(value = "/{id}")
	public ResponseEntity<?> updatePut(@PathVariable("id") long id, @RequestBody Produto produto) {
		return repository.findById(id).map(record -> {
			record.setNome(produto.getNome());
			record.setPreco(produto.getPreco());
			Produto updated = repository.save(record);
			return ResponseEntity.ok().body(updated);
		}).orElse(ResponseEntity.notFound().build());
	}
	
	@PatchMapping(value = "/{id}")
	public ResponseEntity<?> updatePatch(@PathVariable("id") long id, @RequestBody Produto produto) {
		return repository.findById(id).map(record -> {
			if (produto.getNome() != null) {
				record.setNome(produto.getNome());
			}
			if (produto.getPreco() != null) {
				record.setPreco(produto.getPreco());
			}
			Produto updated = repository.save(record);
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