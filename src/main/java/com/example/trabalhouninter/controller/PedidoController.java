package com.example.trabalhouninter.controller;

import java.time.LocalDateTime;
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
import com.example.trabalhouninter.entity.Pedido;
import com.example.trabalhouninter.entity.Produto;
import com.example.trabalhouninter.repository.ClienteRepository;
import com.example.trabalhouninter.repository.PedidoRepository;
import com.example.trabalhouninter.repository.ProdutoRepository;
import com.example.trabalhouninter.request.PedidoRequest;
import com.example.trabalhouninter.response.PedidoResponse;

@RestController
@RequestMapping({ "/pedidos" })
public class PedidoController {
	private PedidoRepository repository;
	private ClienteRepository clienteRepository;
	private ProdutoRepository produtoRepository;

	PedidoController(PedidoRepository repository, ClienteRepository clienteRepository,
			ProdutoRepository produtoRepository) {
		this.repository = repository;
		this.clienteRepository = clienteRepository;
		this.produtoRepository = produtoRepository;
	}

	@GetMapping
	public List<PedidoResponse> findAll() {
		return repository.findAll().stream().map(e -> new PedidoResponse(e)).toList();
	}

	@GetMapping(path = { "/{id}" })
	public ResponseEntity<?> findById(@PathVariable long id) {
		return repository.findById(id).map(record -> ResponseEntity.ok().body(record))
				.orElse(ResponseEntity.notFound().build());
	}

	@PostMapping
	public ResponseEntity<?> create(@RequestBody PedidoRequest request) {
		if (request.getQtd() == null || request.getQtd() <= 0) {
			return ResponseEntity.badRequest().body("A quantidade precisa ser um valor acima de 0");
		}
		
		if (request.getClienteId() == null || request.getProdutoId() == null) {
			return ResponseEntity.badRequest().body("clienteId e produtoId são obrigatórios");
		}
		
		Cliente cliente = clienteRepository.findById(request.getClienteId()).orElse(null);
		Produto produto = produtoRepository.findById(request.getProdutoId()).orElse(null);

		if (cliente == null || produto == null) {
			return ResponseEntity.notFound().build();
		}

		Pedido pedido = new Pedido();
		pedido.setCliente(cliente);
		pedido.setProduto(produto);
		pedido.setQtd(request.getQtd());
		pedido.setData(request.getData());
		Pedido pedidoSalvo = repository.save(pedido);
		PedidoResponse response = new PedidoResponse(pedidoSalvo);
		return ResponseEntity.ok().body(response);
	}

	@PutMapping(value = "/{id}")
	public ResponseEntity<?> updatePut(@PathVariable("id") long id, @RequestBody PedidoRequest request) {
		if (request.getClienteId() == null || request.getProdutoId() == null) {
			return ResponseEntity.badRequest().body("clienteId e produtoId são obrigatórios");
		}

		if (request.getQtd() == null || request.getQtd() <= 0) {
			return ResponseEntity.badRequest().body("A quantidade precisa ser um valor acima de 0");
		}

		Cliente cliente = clienteRepository.findById(request.getClienteId()).orElse(null);
		Produto produto = produtoRepository.findById(request.getProdutoId()).orElse(null);

		if (cliente == null || produto == null) {
			return ResponseEntity.notFound().build();
		}

		return repository.findById(id).map(record -> {
			record.setQtd(request.getQtd());
			record.setData(LocalDateTime.now());
			record.setCliente(cliente);
			record.setProduto(produto);
			record.setData(request.getData());
			Pedido updated = repository.save(record);
			PedidoResponse response = new PedidoResponse(updated);
			return ResponseEntity.ok().body(response);
		}).orElse(ResponseEntity.notFound().build());
	}

	@PatchMapping(value = "/{id}")
	public ResponseEntity<?> updatePatch(@PathVariable("id") long id, @RequestBody PedidoRequest request) {
		return repository.findById(id).map(record -> {
			if (request.getQtd() != null && request.getQtd() < 0) {
				record.setQtd(request.getQtd());
			}

			if (request.getClienteId() != null) {
				Cliente cliente = clienteRepository.findById(request.getClienteId()).orElse(null);
				if (cliente == null) {
					return ResponseEntity.notFound().build();
				}
				record.setCliente(cliente);
			}

			if (request.getProdutoId() != null) {
				Produto produto = produtoRepository.findById(request.getProdutoId()).orElse(null);
				if (produto == null) {
					return ResponseEntity.notFound().build();
				}
				record.setProduto(produto);
			}

			if (request.getData() != null) {
				record.setData(request.getData());
			}
			Pedido updated = repository.save(record);
			PedidoResponse response = new PedidoResponse(updated);
			return ResponseEntity.ok().body(response);
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