package com.franciscoaguiar.cursomc;

import java.text.SimpleDateFormat;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.franciscoaguiar.cursomc.domain.Categoria;
import com.franciscoaguiar.cursomc.domain.Cidade;
import com.franciscoaguiar.cursomc.domain.Cliente;
import com.franciscoaguiar.cursomc.domain.Endereco;
import com.franciscoaguiar.cursomc.domain.Estado;
import com.franciscoaguiar.cursomc.domain.ItemPedido;
import com.franciscoaguiar.cursomc.domain.Pagamento;
import com.franciscoaguiar.cursomc.domain.PagamentoComBoleto;
import com.franciscoaguiar.cursomc.domain.PagamentoComCartao;
import com.franciscoaguiar.cursomc.domain.Pedido;
import com.franciscoaguiar.cursomc.domain.Produto;
import com.franciscoaguiar.cursomc.domain.enums.EstadoPagamento;
import com.franciscoaguiar.cursomc.domain.enums.TipoCliente;
import com.franciscoaguiar.cursomc.repositories.CategoriaRepository;
import com.franciscoaguiar.cursomc.repositories.CidadeRepository;
import com.franciscoaguiar.cursomc.repositories.ClienteRepository;
import com.franciscoaguiar.cursomc.repositories.EnderecoRepository;
import com.franciscoaguiar.cursomc.repositories.EstadoRepository;
import com.franciscoaguiar.cursomc.repositories.ItemPedidoRepository;
import com.franciscoaguiar.cursomc.repositories.PagamentoRepository;
import com.franciscoaguiar.cursomc.repositories.PedidoRepository;
import com.franciscoaguiar.cursomc.repositories.ProdutoRepository;

@SpringBootApplication
public class CursomcApplication {

	public static void main(String[] args) {
		SpringApplication.run(CursomcApplication.class, args);
	}

}
