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

import com.bandampla.lojavirtual.dto.ImagemProdutoDTO;
import com.bandampla.lojavirtual.dto.ProdutoDTO;
import com.bandampla.lojavirtual.exception.ExceptionCustom;
import com.bandampla.lojavirtual.mapper.ProdutoMapper;
import com.bandampla.lojavirtual.model.CategoriaProduto;
import com.bandampla.lojavirtual.model.ImagemProduto;
import com.bandampla.lojavirtual.model.MarcaProduto;
import com.bandampla.lojavirtual.model.PessoaJuridica;
import com.bandampla.lojavirtual.model.Produto;
import com.bandampla.lojavirtual.model.ProdutoFornecedor;
import com.bandampla.lojavirtual.repository.CategoriaProdutoRepository;
import com.bandampla.lojavirtual.repository.ImagemProdutoRepository;
import com.bandampla.lojavirtual.repository.MarcaProdutoRepository;
import com.bandampla.lojavirtual.repository.PessoaJuridicaRepository;
import com.bandampla.lojavirtual.repository.ProdutoFornecedorRepository;
import com.bandampla.lojavirtual.repository.ProdutoRepository;
import com.bandampla.lojavirtual.repository.specification.ProdutoFornecedorSpec;
import com.bandampla.lojavirtual.repository.specification.ProdutoSpec;
import com.bandampla.lojavirtual.security.UsuarioLogadoPrincipal;
import com.bandampla.lojavirtual.util.ImageUtil;

@Service
public class ProdutoService {

	private final ImagemProdutoRepository imagemProdutoRepository;
	private final ProdutoRepository produtoRepository;
	private final PessoaJuridicaRepository pessoaJuridicaRepository;
	private final CategoriaProdutoRepository categoriaProdutoRepository;
	private final MarcaProdutoRepository marcaProdutoRepository;
	private final ProdutoFornecedorRepository produtoFornecedorRepository;
	private final ProdutoMapper produtoMapper;

	public ProdutoService(ImagemProdutoRepository imagemProdutoRepository, ProdutoRepository produtoRepository,
			PessoaJuridicaRepository pessoaJuridicaRepository, CategoriaProdutoRepository categoriaProdutoRepository,
			ProdutoMapper produtoMapper, MarcaProdutoRepository marcaProdutoRepository,
			ProdutoFornecedorRepository produtoFornecedorRepository, SendMailService sendMailService) {

		this.imagemProdutoRepository = imagemProdutoRepository;
		this.produtoRepository = produtoRepository;
		this.pessoaJuridicaRepository = pessoaJuridicaRepository;
		this.categoriaProdutoRepository = categoriaProdutoRepository;
		this.marcaProdutoRepository = marcaProdutoRepository;
		this.produtoFornecedorRepository = produtoFornecedorRepository;
		this.produtoMapper = produtoMapper;
	}

	/*
	 * =================== CADASTRAR PRODUTO ===================
	 */
	@Transactional
	public ProdutoDTO cadastrar(ProdutoDTO dto, UsuarioLogadoPrincipal usuarioLogado)
			throws ExceptionCustom, MessagingException, IOException {

		if (dto.getId() == null) {
			Specification<Produto> specDuplicado = Specification.where(ProdutoSpec.nomeExato(dto.getNome()))
					.and(ProdutoSpec.empresaIgual(usuarioLogado.getEmpresaId()));
			if (!produtoRepository.findAll(specDuplicado).isEmpty()) {
				throw new ExceptionCustom("Já existe Produto com nome '" + dto.getNome() + "' cadastrado.");
			}
		}

		PessoaJuridica empresa = pessoaJuridicaRepository.findById(usuarioLogado.getEmpresaId())
				.orElseThrow(() -> new ExceptionCustom("Empresa não encontrada"));

		CategoriaProduto categoriaProduto = categoriaProdutoRepository.findById(dto.getCategoriaId())
				.orElseThrow(() -> new ExceptionCustom("Categoria não encontrada"));

		if (!categoriaProduto.getEmpresa().getId().equals(empresa.getId())) {
			throw new ExceptionCustom("A categoria informada não pertence à sua empresa.");
		}

		MarcaProduto marcaProduto = marcaProdutoRepository.findById(dto.getMarcaId())
				.orElseThrow(() -> new ExceptionCustom("Marca não encontrada"));

		if (!marcaProduto.getEmpresa().getId().equals(empresa.getId())) 
			throw new ExceptionCustom("A marca informada não pertence à sua empresa.");
		

		PessoaJuridica fornecedor = pessoaJuridicaRepository.findById(dto.getFornecedorId())
				.orElseThrow(() -> new ExceptionCustom("Fornecedor não encontrado"));

		if (!fornecedor.getMatriz().getId().equals(empresa.getId())) {
			throw new ExceptionCustom("Este fornecedor não pertence à sua empresa.");
		}

		Specification<ProdutoFornecedor> specCodigoDuplicado = Specification
				.where(ProdutoFornecedorSpec.codigoProdutoFornecedorExato(dto.getCodigoProdutoFornecedor()))
				.and(ProdutoFornecedorSpec.fornecedorIgual(dto.getFornecedorId()))
				.and(ProdutoFornecedorSpec.empresaIgual(usuarioLogado.getEmpresaId()));

		if (produtoFornecedorRepository.findOne(specCodigoDuplicado).isPresent()) {
			throw new ExceptionCustom("Já existe um produto cadastrado com o código '"
					+ dto.getCodigoProdutoFornecedor() + "' para este fornecedor.");
		}

		/* Converter DTO para Model (Fica puramente em memória) */
		Produto model = produtoMapper.toModel(dto);
		model.setEmpresa(empresa);
		model.setCategoriaProduto(categoriaProduto);
		model.setMarcaProduto(marcaProduto);
		model.setImagens(new ArrayList<>()); // Garante a lista limpa em memória

		/* Processar imagens convertendo Base64 para byte[] ANTES de salvar */
		if (dto.getImagens() != null && !dto.getImagens().isEmpty()) {
			for (ImagemProdutoDTO imgDto : dto.getImagens()) {
				if (imgDto == null || imgDto.getImagemOriginal() == null
						|| imgDto.getImagemOriginal().trim().isEmpty()) {
					continue;
				}

				try {
					// 🔥 Chama a classe utilitária que faz o parse e o redimensionamento
					ImageUtil.ResultadoImagem resultado = ImageUtil.processarImagem(imgDto.getImagemOriginal());

					ImagemProduto imagem = new ImagemProduto();
					imagem.setProduto(model);
					imagem.setEmpresa(empresa);
					imagem.setImagemOriginal(resultado.getBytesOriginal()); // Salva BYTEA
					imagem.setImagemMiniatura(resultado.getBytesMiniatura()); // Salva BYTEA

					model.getImagens().add(imagem);
				} catch (Exception e) {
					throw new ExceptionCustom("Erro ao processar imagem do produto: " + e.getMessage());
				}
			}
		}

		/* Salvar Produto e Imagens simultaneamente via CASCADE */
		Produto produtoSalvo = produtoRepository.save(model);

		/* Criar vínculo ProdutoFornecedor */
		ProdutoFornecedor produtoFornecedor = new ProdutoFornecedor();
		produtoFornecedor.setProduto(produtoSalvo);
		produtoFornecedor.setFornecedor(fornecedor);
		produtoFornecedor.setEmpresa(empresa);
		produtoFornecedor.setCodigoProdutoFornecedor(dto.getCodigoProdutoFornecedor());
		produtoFornecedorRepository.save(produtoFornecedor);

		/* Montar DTO de retorno reconstruindo o Base64 para o JSON */
		ProdutoDTO dtoRetorno = produtoMapper.toDTO(produtoSalvo);
		dtoRetorno.setFornecedorId(fornecedor.getId());
		dtoRetorno.setCodigoProdutoFornecedor(produtoFornecedor.getCodigoProdutoFornecedor());
		/* Converter os binários puros de volta para Base64 no JSON de resposta */
		dtoRetorno
				.setImagens(converterImagensParaDTO(produtoSalvo.getImagens(), produtoSalvo.getId(), empresa.getId()));

		return dtoRetorno;

	}

	/*
	 * =================== ATUALIZAR PRODUTO ===================
	 */
	@Transactional // 🔥 Crucial para que o orphanRemoval e o Cascade funcionem perfeitamente
	public ProdutoDTO atualizar(Long id, ProdutoDTO dto, UsuarioLogadoPrincipal usuarioLogado)
			throws ExceptionCustom, IOException {

		if (id == null || id <= 0) {
			throw new ExceptionCustom("ID inválido");
		}

		Produto existente = produtoRepository.findById(id)
				.orElseThrow(() -> new ExceptionCustom("Produto não encontrado"));

		if (!existente.getEmpresa().getId().equals(usuarioLogado.getEmpresaId())) {
			throw new ExceptionCustom("Você não tem permissão para alterar este produto.");
		}

		/* Verificar se houve alteração nos campos básicos */
		ProdutoDTO dtoAntigo = produtoMapper.toDTO(existente);
		if (dtoAntigo.equals(dto) && (dto.getImagens() == null || dto.getImagens().isEmpty())) {
			throw new ExceptionCustom("Nenhuma alteração detectada.");
		}

		PessoaJuridica empresa = pessoaJuridicaRepository.findById(usuarioLogado.getEmpresaId())
				.orElseThrow(() -> new ExceptionCustom("Empresa não encontrada"));

		CategoriaProduto novaCategoria = categoriaProdutoRepository.findById(dto.getCategoriaId())
				.orElseThrow(() -> new ExceptionCustom("Categoria não encontrada"));

		if (!novaCategoria.getEmpresa().getId().equals(empresa.getId())) {
			throw new ExceptionCustom("A categoria informada não pertence à sua empresa.");
		}

		MarcaProduto marcaProduto = marcaProdutoRepository.findById(dto.getMarcaId())
				.orElseThrow(() -> new ExceptionCustom("Marca não encontrada"));

		if (!marcaProduto.getEmpresa().getId().equals(empresa.getId())) {
			throw new ExceptionCustom("A marca informada não pertence à sua empresa.");
		}

		PessoaJuridica fornecedor = pessoaJuridicaRepository.findById(dto.getFornecedorId())
				.orElseThrow(() -> new ExceptionCustom("Fornecedor não encontrado"));

		if (!fornecedor.getMatriz().getId().equals(empresa.getId())) {
			throw new ExceptionCustom("Este fornecedor não pertence à sua empresa.");
		}

		/* Validar duplicidade do nome */
		Specification<Produto> specDuplicado = Specification.where(ProdutoSpec.nomeExato(dto.getNome()))
				.and(ProdutoSpec.empresaIgual(usuarioLogado.getEmpresaId()));

		List<Produto> duplicados = produtoRepository.findAll(specDuplicado);
		if (!duplicados.isEmpty() && !duplicados.get(0).getId().equals(id)) {
			throw new ExceptionCustom("Já existe Produto com nome '" + dto.getNome() + "' cadastrado.");
		}

		/* 2. Atualizar campos textuais e numéricos do Produto */
		produtoMapper.atualizarCamposDoProduto(dto, existente);
		existente.setEmpresa(empresa);
		existente.setCategoriaProduto(novaCategoria);
		existente.setMarcaProduto(marcaProduto);

		/* 3. FLUXO DE ATUALIZAÇÃO DAS IMAGENS (Substituição física por BYTEA) */
		if (dto.getImagens() != null) {
			// 🔥 Passo crucial: Limpa a lista existente.
			// O 'orphanRemoval = true' fará o Hibernate disparar um 'DELETE FROM
			// imagem_produto' físico no banco para as fotos antigas.
			existente.getImagens().clear();

			for (ImagemProdutoDTO imgDto : dto.getImagens()) {
				// Ignora objetos vazios ou nulos enviados acidentalmente pelo JSON
				if (imgDto == null || imgDto.getImagemOriginal() == null
						|| imgDto.getImagemOriginal().trim().isEmpty()) {
					continue;
				}
				try {
					// 🔥 Reutilização da classe utilitária
					ImageUtil.ResultadoImagem resultado = ImageUtil.processarImagem(imgDto.getImagemOriginal());

					ImagemProduto imagem = new ImagemProduto();
					imagem.setProduto(existente);
					imagem.setEmpresa(empresa);
					imagem.setImagemOriginal(resultado.getBytesOriginal());
					imagem.setImagemMiniatura(resultado.getBytesMiniatura());

					existente.getImagens().add(imagem);
				} catch (Exception e) {
					throw new ExceptionCustom("Erro ao processar nova imagem: " + e.getMessage());
				}
			}
		}

		// Salva o produto. O CascadeType.ALL insere os novos registros do BYTEA
		Produto produtoAtualizado = produtoRepository.save(existente);

		/* Buscar e atualizar vínculo ProdutoFornecedor */
		Specification<ProdutoFornecedor> spec = Specification
				.where(ProdutoFornecedorSpec.produtoIgual(produtoAtualizado.getId()))
				.and(ProdutoFornecedorSpec.empresaIgual(usuarioLogado.getEmpresaId()));

		ProdutoFornecedor produtoFornecedor = produtoFornecedorRepository.findOne(spec).orElseGet(() -> {
			ProdutoFornecedor pf = new ProdutoFornecedor();
			pf.setProduto(produtoAtualizado);
			pf.setEmpresa(empresa);
			return pf;
		});

		produtoFornecedor.setFornecedor(fornecedor);
		produtoFornecedor.setCodigoProdutoFornecedor(dto.getCodigoProdutoFornecedor());
		produtoFornecedorRepository.save(produtoFornecedor);

		/* 4. Montar JSON de Retorno convertendo BYTEA de volta para Base64 */
		ProdutoDTO dtoRetorno = produtoMapper.toDTO(produtoAtualizado);
		dtoRetorno.setFornecedorId(fornecedor.getId());
		dtoRetorno.setCodigoProdutoFornecedor(produtoFornecedor.getCodigoProdutoFornecedor());
		dtoRetorno.setImagens(
				converterImagensParaDTO(produtoAtualizado.getImagens(), produtoAtualizado.getId(), empresa.getId()));

		return dtoRetorno;
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