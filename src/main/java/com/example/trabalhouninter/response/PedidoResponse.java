package com.example.trabalhouninter.response;

import java.math.BigDecimal;

import com.example.trabalhouninter.entity.Cliente;
import com.example.trabalhouninter.entity.Pedido;
import com.example.trabalhouninter.entity.Produto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PedidoResponse {
	private Produto produto;
	private Cliente cliente;
	private int qtd;
	private BigDecimal total;

	public PedidoResponse(Pedido pedido) {
		this.produto = pedido.getProduto();
		this.cliente = pedido.getCliente();
		this.qtd = pedido.getQtd();
		this.total = pedido.total();
	}
}