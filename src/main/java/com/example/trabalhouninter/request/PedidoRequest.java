package com.example.trabalhouninter.request;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PedidoRequest {
	private Long clienteId;
	private Long produtoId;
	private LocalDateTime data;
	private Integer qtd;
}
