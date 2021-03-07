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
import com.franciscoaguiar.cursomc.repositories.PagamentoRepository;
import com.franciscoaguiar.cursomc.repositories.PedidoRepository;
import com.franciscoaguiar.cursomc.repositories.ProdutoRepository;

@SpringBootApplication
public class CursomcApplication implements CommandLineRunner{
	
	@Autowired
	private CategoriaRepository categoriaRepository;
	
	@Autowired
	private ProdutoRepository produtoRepository;
	
	@Autowired
	private EstadoRepository estadoRepository;
	
	@Autowired
	private CidadeRepository cidadeRepository;
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	@Autowired
	private EnderecoRepository enderecoRepository;
	
	@Autowired
	private PedidoRepository pedidoRepository;
	
	@Autowired
	private PagamentoRepository pagamentoRepository;
	
	public static void main(String[] args) {
		SpringApplication.run(CursomcApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		
		/***   PRODUTO E CATEGORIA   ***/
		
		Produto p1 = new Produto(null, "Compuatdor", 2000.0);
		Produto p2 = new Produto(null, "Impressora", 800.0);
		Produto p3 = new Produto(null, "Mouse", 80.0);
		
		Categoria cat1 = new Categoria(null, "Informática");
		Categoria cat2 = new Categoria(null, "Escritório");
		
		cat1.getProdutos().addAll(Arrays.asList(p1, p2, p3));
		cat2.getProdutos().addAll(Arrays.asList(p2));
		
		p1.getCategorias().addAll(Arrays.asList(cat1));
		p2.getCategorias().addAll(Arrays.asList(cat1, cat2));
		p3.getCategorias().addAll(Arrays.asList(cat1));
		
		categoriaRepository.saveAll(Arrays.asList(cat1, cat2));
		produtoRepository.saveAll(Arrays.asList(p1,p2,p3));
	
		
		/***   CIDADE E ESTADO   ***/
	
		Estado est1 = new Estado(null, "Minas Gerais");
		Estado est2 = new Estado(null, "São Paulo");
		Estado est3 = new Estado(null, "Distrito Federal");
		
		Cidade c1 = new Cidade(null, "Uberlândia", est1);
		Cidade c2 = new Cidade(null, "São Paulo", est2);
		Cidade c3 = new Cidade(null, "Campinas", est2);
		Cidade c4 = new Cidade(null, "Brasilia", est3);
		
		est1.getCidades().add(c1);
		est2.getCidades().addAll(Arrays.asList(c2, c3));
		est3.getCidades().add(c4);
		
		estadoRepository.saveAll(Arrays.asList(est1,est2, est3));
		cidadeRepository.saveAll(Arrays.asList(c1,c2,c3, c4));
		
		
		/***   ENDERECO CLIENTE CIDADE   ***/
		
		Cliente cli1 = new Cliente(null, "Francisco Frota de Aguiar", "francisco@gmail.com", "03714849106", TipoCliente.PESSOA_FISICA);
		cli1.getTelefones().addAll(Arrays.asList("33581722", "994011975"));
		
		Endereco e1 = new Endereco(null, "Rua Flores", "300", "Apto 203", "Jardins Europa", "38220834", cli1, c1);
		Endereco e2 = new Endereco(null, "Avenida Matos", "105", "Casa 15", "Beira Mar", "38777012", cli1, c2);
		Endereco e3 = new Endereco(null, "QR 208 Conjunto 08", "30", "casa", "Samambaia Norte", "72316109", cli1, c4);
		
		cli1.getEnderecos().addAll(Arrays.asList(e1,e2));
		
		clienteRepository.save(cli1);
		enderecoRepository.saveAll(Arrays.asList(e1,e2,e3));
		
		
		/***   PEDIDO ENDERECO E CLIENTE   ***/
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		
		Pedido ped1 = new Pedido(null, sdf.parse("30/09/2017 10:32"), cli1, e1);
		Pedido ped2 = new Pedido(null, sdf.parse("10/10/2017 19:35"), cli1, e2);
		
		Pagamento pagto1 = new PagamentoComCartao(null, EstadoPagamento.QUITADO, ped1, 6);
		ped1.setPagamento(pagto1);
		
		Pagamento pagto2 = new PagamentoComBoleto(null, EstadoPagamento.PENDENTE, ped2, sdf.parse("20/10/2017 00:00"), null);
		ped2.setPagamento(pagto2);
		
		cli1.getPedidos().addAll(Arrays.asList(ped1,ped2));
		
		pedidoRepository.saveAll(Arrays.asList(ped1,ped2));
		pagamentoRepository.saveAll(Arrays.asList(pagto1,pagto2));
		
	}

}
