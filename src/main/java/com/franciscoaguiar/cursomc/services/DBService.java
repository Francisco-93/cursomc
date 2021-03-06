package com.franciscoaguiar.cursomc.services;

import com.franciscoaguiar.cursomc.domain.*;
import com.franciscoaguiar.cursomc.domain.enums.EstadoPagamento;
import com.franciscoaguiar.cursomc.domain.enums.Perfil;
import com.franciscoaguiar.cursomc.domain.enums.TipoCliente;
import com.franciscoaguiar.cursomc.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;

@Service
public class DBService {

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

    @Autowired
    private ItemPedidoRepository itemPedidoRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public void getInstantiateDatabase() throws ParseException {
        /***   PRODUTO E CATEGORIA   ***/

        Produto p1 = new Produto(null, "Compuatdor", 2000.0);
        Produto p2 = new Produto(null, "Impressora", 800.0);
        Produto p3 = new Produto(null, "Mouse", 80.0);
        Produto p4 = new Produto(null, "Mesa de escritório", 300.0);
        Produto p5 = new Produto(null, "Toalha", 50.0);
        Produto p6 = new Produto(null, "Colcha", 200.0);
        Produto p7 = new Produto(null, "TV true color", 1200.0);
        Produto p8 = new Produto(null, "Roçadeira", 800.0);
        Produto p9 = new Produto(null, "Abajour", 100.0);
        Produto p10 = new Produto(null, "Pendente", 180.0);
        Produto p11 = new Produto(null, "Shampoo", 30.0);

        Categoria cat1 = new Categoria(null, "Informática");
        Categoria cat2 = new Categoria(null, "Escritório");
        Categoria cat3 = new Categoria(null, "Cama, mesa e banho");
        Categoria cat4 = new Categoria(null, "Eletrônicos");
        Categoria cat5 = new Categoria(null, "Jardinagem");
        Categoria cat6 = new Categoria(null, "Decoração");
        Categoria cat7 = new Categoria(null, "Perfumaria");

        cat1.getProdutos().addAll(Arrays.asList(p1, p2, p3));
        cat2.getProdutos().addAll(Arrays.asList(p2, p4));
        cat3.getProdutos().addAll(Arrays.asList(p2, p4));
        cat4.getProdutos().addAll(Arrays.asList(p1, p2, p3, p7));
        cat5.getProdutos().addAll(Arrays.asList(p8));
        cat6.getProdutos().addAll(Arrays.asList(p9, p10));
        cat7.getProdutos().addAll(Arrays.asList(p11));

        p1.getCategorias().addAll(Arrays.asList(cat1));
        p2.getCategorias().addAll(Arrays.asList(cat1, cat2));
        p3.getCategorias().addAll(Arrays.asList(cat1));

        categoriaRepository.saveAll(Arrays.asList(cat1, cat2,cat3,cat4,cat5,cat6,cat7));
        produtoRepository.saveAll(Arrays.asList(p1,p2,p3, p4, p5, p6, p7, p8, p9, p10, p11));


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

        Cliente cli1 = new Cliente(null, "Francisco Frota de Aguiar", "francisco.fro.agui@gmail.com", "03714849106", TipoCliente.PESSOA_FISICA, passwordEncoder.encode("1234"));
        Cliente cli2 = new Cliente(null, "Miriã Costa de Aguiar", "miria.c.barros@gmail.com", "01833799186", TipoCliente.PESSOA_FISICA, passwordEncoder.encode("1234"));
        cli1.addPerfil(Perfil.ADMIN);

        cli1.getTelefones().addAll(Arrays.asList("33581722", "994011975"));
        cli2.getTelefones().addAll(Arrays.asList("33581722", "982724821"));

        Endereco e1 = new Endereco(null, "Rua Flores", "300", "Apto 203", "Jardins Europa", "38220834", cli1, c1);
        Endereco e2 = new Endereco(null, "Avenida Matos", "105", "Casa 15", "Beira Mar", "38777012", cli2, c2);
        Endereco e3 = new Endereco(null, "QR 208 Conjunto 08", "30", "casa", "Samambaia Norte", "72316109", cli1, c4);

        cli1.getEnderecos().addAll(Arrays.asList(e1,e3));
        cli2.getEnderecos().addAll(Arrays.asList(e2));

        clienteRepository.save(cli1);
        clienteRepository.save(cli2);
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



        /***   ITEM_PEDIDO PEDIDO E PRODUTO   ***/

        ItemPedido ip1 = new ItemPedido(ped1, p1, 0.0, 1, 2000.0);
        ItemPedido ip2 = new ItemPedido(ped1, p3, 0.0, 2, 80.0);
        ItemPedido ip3 = new ItemPedido(ped2, p2, 100.0, 1, 800.0);

        ped1.getItens().addAll(Arrays.asList(ip1,ip2));
        ped2.getItens().addAll(Arrays.asList(ip3));

        p1.getItens().addAll(Arrays.asList(ip1));
        p2.getItens().addAll(Arrays.asList(ip3));
        p3.getItens().addAll(Arrays.asList(ip2));

        itemPedidoRepository.saveAll(Arrays.asList(ip1,ip2,ip3));
    }

}
