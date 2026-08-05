package com.example.trabalhouninter.entity;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "pedidos")
public class Pedido {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private int qtd;

	private LocalDateTime data;

	@ManyToOne
	@JoinColumn(name = "cliente_id")
	private Cliente cliente;

	@ManyToOne
	@JoinColumn(name = "produto_id")
	private Produto produto;

	public BigDecimal total() {
		if (produto == null || produto.getPreco() == null) {
			return BigDecimal.ZERO;
		}

		BigDecimal preco = produto.getPreco();
		BigDecimal quantidade = BigDecimal.valueOf(qtd);

		return preco.multiply(quantidade).setScale(2, RoundingMode.HALF_UP);
	}
}
