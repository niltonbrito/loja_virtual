package com.bandampla.lojavirtual.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.mail.MessagingException;
import javax.transaction.Transactional;
import javax.xml.bind.DatatypeConverter;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.bandampla.lojavirtual.dto.AvaliacaoProdutoDTO;
import com.bandampla.lojavirtual.dto.ImagemProdutoDTO;
import com.bandampla.lojavirtual.dto.ProdutoDTO;
import com.bandampla.lojavirtual.exception.ExceptionCustom;
import com.bandampla.lojavirtual.mapper.AvaliacaoProdutoMapper;
import com.bandampla.lojavirtual.mapper.ProdutoMapper;
import com.bandampla.lojavirtual.model.AvaliacaoProduto;
import com.bandampla.lojavirtual.model.CategoriaProduto;
import com.bandampla.lojavirtual.model.ImagemProduto;
import com.bandampla.lojavirtual.model.MarcaProduto;
import com.bandampla.lojavirtual.model.PessoaFisica;
import com.bandampla.lojavirtual.model.PessoaJuridica;
import com.bandampla.lojavirtual.model.Produto;
import com.bandampla.lojavirtual.model.ProdutoFornecedor;
import com.bandampla.lojavirtual.repository.AvaliacaoProdutoRepository;
import com.bandampla.lojavirtual.repository.CategoriaProdutoRepository;
import com.bandampla.lojavirtual.repository.ImagemProdutoRepository;
import com.bandampla.lojavirtual.repository.MarcaProdutoRepository;
import com.bandampla.lojavirtual.repository.PessoaFisicaRepository;
import com.bandampla.lojavirtual.repository.PessoaJuridicaRepository;
import com.bandampla.lojavirtual.repository.ProdutoFornecedorRepository;
import com.bandampla.lojavirtual.repository.ProdutoRepository;
import com.bandampla.lojavirtual.repository.specification.AvaliacaoProdutoSpec;
import com.bandampla.lojavirtual.repository.specification.ProdutoFornecedorSpec;
import com.bandampla.lojavirtual.repository.specification.ProdutoSpec;
import com.bandampla.lojavirtual.security.UsuarioLogadoPrincipal;
import com.bandampla.lojavirtual.util.ImageUtil;

@Service
public class AvaliacaoProdutoService {

	private final AvaliacaoProdutoRepository avaliacaoProdutoRepository;
	private final ProdutoRepository produtoRepository;
	private final PessoaJuridicaRepository pessoaJuridicaRepository;
	private final PessoaFisicaRepository pessoaFisicaRepository;
	private final AvaliacaoProdutoMapper avaliacaoProdutoMapper;

	public AvaliacaoProdutoService(AvaliacaoProdutoRepository avaliacaoProdutoRepository,
			ProdutoRepository produtoRepository, PessoaJuridicaRepository pessoaJuridicaRepository,
			PessoaFisicaRepository pessoaFisicaRepository, AvaliacaoProdutoMapper avaliacaoProdutoMapper) {
		this.avaliacaoProdutoRepository = avaliacaoProdutoRepository;
		this.produtoRepository = produtoRepository;
		this.pessoaJuridicaRepository = pessoaJuridicaRepository;
		this.pessoaFisicaRepository = pessoaFisicaRepository;
		this.avaliacaoProdutoMapper = avaliacaoProdutoMapper;
	}

	/*
	 * =================== CADASTRAR AVALIAÇÃO DO PRODUTO ===================
	 */
	public AvaliacaoProdutoDTO cadastrar(AvaliacaoProdutoDTO dto, UsuarioLogadoPrincipal usuarioLogado)
			throws ExceptionCustom {

		// 1. Validar se o produto existe
		Produto produto = produtoRepository.findById(dto.getProdutoId())
				.orElseThrow(() -> new ExceptionCustom("Produto não encontrado."));

		// 2. 🔥 CORREÇÃO DE SEGURANÇA: Garante que o produto de fato pertence à empresa logada para receber a nota
		if (!produto.getEmpresa().getId().equals(usuarioLogado.getEmpresaId())) {
			throw new ExceptionCustom("O produto informado não pertence à sua empresa e não pode ser avaliado.");
		}

		PessoaJuridica empresa = pessoaJuridicaRepository.findById(usuarioLogado.getEmpresaId())
				.orElseThrow(() -> new ExceptionCustom("Empresa não encontrada"));
		
		PessoaFisica pessoa = pessoaFisicaRepository.findById(dto.getPessoaId())
				.orElseThrow(() -> new ExceptionCustom("Pessoa avaliadora não encontrada"));

		// 3. Converter e persistir a árvore relacional
		AvaliacaoProduto model = avaliacaoProdutoMapper.toModel(dto);
		model.setEmpresa(empresa);
		model.setPessoa(pessoa);
		model.setProduto(produto);

		// 4. 🔥 CORREÇÃO DO RETORNO: Devolve o DTO estruturado de volta para o Controller
		return avaliacaoProdutoMapper.toDTO(avaliacaoProdutoRepository.save(model));
	}
	
	/*
	 * =================== ATUALIZAR PRODUTO ===================
	 */
	@Transactional // 🔥 Crucial para que o orphanRemoval e o Cascade funcionem perfeitamente
	public AvaliacaoProdutoDTO atualizar(Long id, AvaliacaoProdutoDTO dto, UsuarioLogadoPrincipal usuarioLogado)
			throws ExceptionCustom, IOException {

		if (id == null || id <= 0) {
			throw new ExceptionCustom("ID inválido");
		}

		Produto existente = produtoRepository.findById(id)
				.orElseThrow(() -> new ExceptionCustom("Produto não encontrado"));

		// 2. 🔥 CORREÇÃO DE SEGURANÇA: Garante que o produto de fato pertence à empresa logada para receber a nota
		if (!existente.getEmpresa().getId().equals(usuarioLogado.getEmpresaId())) {
			throw new ExceptionCustom("O produto informado não pertence à sua empresa e não pode ser avaliado.");
		}

		PessoaJuridica empresa = pessoaJuridicaRepository.findById(usuarioLogado.getEmpresaId())
				.orElseThrow(() -> new ExceptionCustom("Empresa não encontrada"));
		
		PessoaFisica pessoa = pessoaFisicaRepository.findById(dto.getPessoaId())
				.orElseThrow(() -> new ExceptionCustom("Pessoa avaliadora não encontrada"));

		/* Verificar se houve alteração nos campos básicos */
		AvaliacaoProdutoDTO dtoAntigo = avaliacaoProdutoMapper.toDTO(existente);
		if (dtoAntigo.equals(dto) && (dtoAntigo.getDescricao().equals(dto.getDescricao()) || dto.getDescricao().trim().isEmpty())) {
			throw new ExceptionCustom("Nenhuma alteração detectada.");
		}

		// 3. Converter e persistir a árvore relacional
		AvaliacaoProduto model = avaliacaoProdutoMapper.toModel(dto);
		model.setEmpresa(empresa);
		model.setPessoa(pessoa);
		model.setProduto(existente);

		// 4. 🔥 CORREÇÃO DO RETORNO: Devolve o DTO estruturado de volta para o Controller
		return avaliacaoProdutoMapper.toDTO(avaliacaoProdutoRepository.save(model));
	}

	/*
	 * =================== DELETAR PRODUTO ===================
	 */
	@Transactional // 🔥 Garante a atomicidade da exclusão do produto e seus filhos
	public void deletar(Long id, UsuarioLogadoPrincipal usuarioLogado) throws ExceptionCustom {

		if (id == null || id <= 0) {
			throw new ExceptionCustom("ID inválido");
		}
		Produto produto = produtoRepository.findById(id)
				.orElseThrow(() -> new ExceptionCustom("Produto não encontrado"));
		if (!produto.getEmpresa().getId().equals(usuarioLogado.getEmpresaId())) {
			throw new ExceptionCustom("Você não tem permissão para excluir este produto.");
		}
		/* 1. Remover vínculos de Fornecedor primeiro */
		Specification<ProdutoFornecedor> spec = Specification.where(ProdutoFornecedorSpec.produtoIgual(produto.getId()))
				.and(ProdutoFornecedorSpec.empresaIgual(usuarioLogado.getEmpresaId()));
		List<ProdutoFornecedor> vinculos = produtoFornecedorRepository.findAll(spec);
		vinculos.forEach(produtoFornecedorRepository::delete);

		/* 2. Deleção Física do Produto e das Imagens */
		// Como a entidade Produto possui cascade = CascadeType.ALL e orphanRemoval =
		// true,
		// o próprio Hibernate vai disparar automaticamente o comando:
		// DELETE FROM imagem_produto WHERE produto_id = :id;
		// Garantindo a limpeza física total das fotos no PostgreSQL 9.5.
		produtoRepository.delete(produto);
	}

	@Transactional
	public void deletarImagemEspecifica(Long imagemId, UsuarioLogadoPrincipal usuarioLogado) throws ExceptionCustom {
		if (imagemId == null || imagemId <= 0) {
			throw new ExceptionCustom("ID da imagem inválido.");
		}
		ImagemProduto imagem = imagemProdutoRepository.findById(imagemId)
				.orElseThrow(() -> new ExceptionCustom("Imagem não encontrada."));
		if (!imagem.getEmpresa().getId().equals(usuarioLogado.getEmpresaId())) {
			throw new ExceptionCustom("Você não tem permissão para excluir esta imagem.");
		}
		Produto produto = imagem.getProduto();
		if (produto != null) {
			produto.getImagens().remove(imagem);
		}
		imagemProdutoRepository.delete(imagem);
	}

	/*
	 * =================== CONSULTAS ===================
	 */
	public ProdutoDTO buscarPorId(Long id, UsuarioLogadoPrincipal usuarioLogado) throws ExceptionCustom {
		if (id == null || id <= 0) {
			throw new ExceptionCustom("ID inválido.");
		}
		Produto produto = produtoRepository.findById(id)
				.orElseThrow(() -> new ExceptionCustom("Produto não encontrado."));
		if (!produto.getEmpresa().getId().equals(usuarioLogado.getEmpresaId())) {
			throw new ExceptionCustom("Você não tem permissão para visualizar este produto.");
		}
		Specification<ProdutoFornecedor> specFornecedor = Specification
				.where(ProdutoFornecedorSpec.produtoIgual(produto.getId()))
				.and(ProdutoFornecedorSpec.empresaIgual(usuarioLogado.getEmpresaId()));
		ProdutoFornecedor pf = produtoFornecedorRepository.findOne(specFornecedor).orElse(null);
		ProdutoDTO dtoRetorno = produtoMapper.toDTO(produto);
		if (pf != null) {
			dtoRetorno.setFornecedorId(pf.getFornecedor().getId());
			dtoRetorno.setCodigoProdutoFornecedor(pf.getCodigoProdutoFornecedor());
		}
		dtoRetorno.setImagens(
				converterImagensParaDTO(produto.getImagens(), produto.getId(), usuarioLogado.getEmpresaId()));
		return dtoRetorno;
	}

	public List<ProdutoDTO> buscarPorDescricao(String descricao, UsuarioLogadoPrincipal usuarioLogado) {
		Specification<Produto> spec = Specification.where(ProdutoSpec.descricaoContem(descricao))
				.and(ProdutoSpec.empresaIgual(usuarioLogado.getEmpresaId()));

		return produtoRepository.findAll(spec).stream().map(produtoMapper::toDTO).collect(Collectors.toList());
	}

	public List<ProdutoDTO> buscarTodosPorEmpresa(UsuarioLogadoPrincipal usuarioLogado) {
		Specification<Produto> spec = Specification.where(ProdutoSpec.empresaIgual(usuarioLogado.getEmpresaId()));
		return produtoRepository.findAll(spec).stream().map(produtoMapper::toDTO).collect(Collectors.toList());
	}

	public Page<ProdutoDTO> buscarAvancado(String descricao, Boolean ativo, int page, int size,
			UsuarioLogadoPrincipal usuarioLogado) {

		Pageable pageable = PageRequest.of(page, size);

		Specification<Produto> spec = Specification
				.where(ProdutoSpec.nomeContem(descricao).or(ProdutoSpec.descricaoContem(descricao)))
				.and(ProdutoSpec.empresaIgual(usuarioLogado.getEmpresaId()))
				.and(ProdutoSpec.statusAtivoEquivalente(ativo));

		return produtoRepository.findAll(spec, pageable).map(produtoMapper::toDTO);
	}

	public Page<ProdutoDTO> buscarPaginado(int page, int size, String sort, String direction,
			UsuarioLogadoPrincipal usuarioLogado) {

		Pageable pageable = PageRequest.of(page, size, Sort.Direction.fromString(direction), sort);

		Specification<Produto> spec = Specification.where(ProdutoSpec.empresaIgual(usuarioLogado.getEmpresaId()));

		return produtoRepository.findAll(spec, pageable).map(produtoMapper::toDTO);
	}

	private List<ImagemProdutoDTO> converterImagensParaDTO(List<ImagemProduto> imagens, Long produtoId,
			Long empresaId) {
		if (imagens == null || imagens.isEmpty()) {
			return new ArrayList<>();
		}
		return imagens.stream().map(img -> {
			ImagemProdutoDTO dtoImg = new ImagemProdutoDTO();
			dtoImg.setId(img.getId());
			if (img.getImagemOriginal() != null) {
				dtoImg.setImagemOriginal(
						"data:image/jpeg;base64," + DatatypeConverter.printBase64Binary(img.getImagemOriginal()));
			}
			if (img.getImagemMiniatura() != null) {
				dtoImg.setImagemMiniatura(
						"data:image/png;base64," + DatatypeConverter.printBase64Binary(img.getImagemMiniatura()));
			}
			dtoImg.setProdutoId(produtoId);
			dtoImg.setEmpresaId(empresaId);
			return dtoImg;
		}).collect(Collectors.toList());
	}

	/*
	 * =================== ATUALIZAR APENAS IMAGENS (EXCLUSIVO) ===================
	 */
	@Transactional
	public void atualizarImagens(Long produtoId, List<ImagemProdutoDTO> imagens, UsuarioLogadoPrincipal usuarioLogado)
			throws ExceptionCustom {

		// 1. Buscar o produto no banco de dados
		Produto produto = produtoRepository.findById(produtoId)
				.orElseThrow(() -> new ExceptionCustom("Produto não encontrado"));

		// 2. Validar se o produto pertence à empresa do usuário logado
		if (!produto.getEmpresa().getId().equals(usuarioLogado.getEmpresaId())) {
			throw new ExceptionCustom("Você não tem permissão para alterar as imagens deste produto.");
		}

		// 3. Limpar a galeria atual. O orphanRemoval = true remove as linhas físicas do banco
		produto.getImagens().clear();

		// 4. Mapear e processar a nova lista de imagens vinda do JSON
		if (imagens != null && !imagens.isEmpty()) {
			for (ImagemProdutoDTO imgDto : imagens) {
				if (imgDto == null || imgDto.getImagemOriginal() == null
						|| imgDto.getImagemOriginal().trim().isEmpty()) {
					continue;
				}

				try {
					// 🔥 Reutilização limpa da classe utilitária adaptada para BYTEA
					ImageUtil.ResultadoImagem resultado = ImageUtil.processarImagem(imgDto.getImagemOriginal());

					ImagemProduto img = new ImagemProduto();
					img.setProduto(produto); // Vinculo da FK
					img.setEmpresa(produto.getEmpresa());
					img.setImagemOriginal(resultado.getBytesOriginal()); // Salva byte[]
					img.setImagemMiniatura(resultado.getBytesMiniatura()); // Salva byte[]

					produto.getImagens().add(img);
				} catch (Exception e) {
					throw new RuntimeException("Erro ao processar lote de imagens", e);
				}
			}
		}

		// 5. Salva o produto. O CascadeType.ALL insere a nova coleção de fotos no PostgreSQL
		produtoRepository.save(produto);
	}
}