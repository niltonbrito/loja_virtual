package com.bandampla.lojavirtual.service;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.imageio.ImageIO;
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

	private final ProdutoRepository produtoRepository;
	private final PessoaJuridicaRepository pessoaJuridicaRepository;
	private final CategoriaProdutoRepository categoriaProdutoRepository;
	private final MarcaProdutoRepository marcaProdutoRepository;
	private final ProdutoFornecedorRepository produtoFornecedorRepository;
	private final ProdutoMapper produtoMapper;

	private final SendMailService sendMailService;

	public ProdutoService(ProdutoRepository produtoRepository, PessoaJuridicaRepository pessoaJuridicaRepository,
			CategoriaProdutoRepository categoriaProdutoRepository, ProdutoMapper produtoMapper,
			MarcaProdutoRepository marcaProdutoRepository, ProdutoFornecedorRepository produtoFornecedorRepository,
			SendMailService sendMailService) {

		this.produtoRepository = produtoRepository;
		this.pessoaJuridicaRepository = pessoaJuridicaRepository;
		this.categoriaProdutoRepository = categoriaProdutoRepository;
		this.marcaProdutoRepository = marcaProdutoRepository;
		this.produtoFornecedorRepository = produtoFornecedorRepository;
		this.produtoMapper = produtoMapper;
		this.sendMailService = sendMailService;
	}

	/*
	 * =================== CADASTRAR PRODUTO ===================
	 */
	@Transactional
	public ProdutoDTO cadastrar(ProdutoDTO dto, UsuarioLogadoPrincipal usuarioLogado)
			throws ExceptionCustom, MessagingException, IOException {

		// validações de duplicidade, empresa, categoria, marca, fornecedor (como você
		// já tinha)
		/* 1. Validar duplicidade do nome */
		if (dto.getId() == null) {
			Specification<Produto> specDuplicado = Specification.where(ProdutoSpec.nomeExato(dto.getNome()))
					.and(ProdutoSpec.empresaIgual(usuarioLogado.getEmpresaId()));
			if (!produtoRepository.findAll(specDuplicado).isEmpty()) {
				throw new ExceptionCustom("Já existe Produto com nome '" + dto.getNome() + "' cadastrado.");
			}
		}

		/* 2. Buscar empresa */
		PessoaJuridica empresa = pessoaJuridicaRepository.findById(usuarioLogado.getEmpresaId())
				.orElseThrow(() -> new ExceptionCustom("Empresa não encontrada"));

		/* 3. Buscar categoria */
		CategoriaProduto categoriaProduto = categoriaProdutoRepository.findById(dto.getCategoriaId())
				.orElseThrow(() -> new ExceptionCustom("Categoria não encontrada"));

		if (!categoriaProduto.getEmpresa().getId().equals(empresa.getId())) {
			throw new ExceptionCustom("A categoria informada não pertence à sua empresa.");
		}

		/* 4. Buscar marca */
		MarcaProduto marcaProduto = marcaProdutoRepository.findById(dto.getMarcaId())
				.orElseThrow(() -> new ExceptionCustom("Marca não encontrada"));

		if (!marcaProduto.getEmpresa().getId().equals(empresa.getId())) {
			throw new ExceptionCustom("A marca informada não pertence à sua empresa.");
		}

		/* 5. Buscar fornecedor */
		PessoaJuridica fornecedor = pessoaJuridicaRepository.findById(dto.getFornecedorId())
				.orElseThrow(() -> new ExceptionCustom("Fornecedor não encontrado"));

		if (!fornecedor.getMatriz().getId().equals(empresa.getId())) {
			throw new ExceptionCustom("Este fornecedor não pertence à sua empresa.");
		}

		/* 6. Validar duplicidade do código do fornecedor */
		Specification<ProdutoFornecedor> specCodigoDuplicado = Specification
				.where(ProdutoFornecedorSpec.codigoProdutoFornecedorExato(dto.getCodigoProdutoFornecedor()))
				.and(ProdutoFornecedorSpec.fornecedorIgual(dto.getFornecedorId()))
				.and(ProdutoFornecedorSpec.empresaIgual(usuarioLogado.getEmpresaId()));

		if (produtoFornecedorRepository.findOne(specCodigoDuplicado).isPresent()) {
			throw new ExceptionCustom("Já existe um produto cadastrado com o código '"
					+ dto.getCodigoProdutoFornecedor() + "' para este fornecedor.");
		}

		/* 7. Salvar Produto */
		Produto model = produtoMapper.toModel(dto);
		model.setEmpresa(empresa);
		model.setCategoriaProduto(categoriaProduto);
		model.setMarcaProduto(marcaProduto);
		model.setImagens(new ArrayList<>());


		/* 7.1 Salvar Produto (sem imagens primeiro) */
		Produto produtoSalvo = produtoRepository.save(model);


		/* 7.2 Processar imagens */
		if (dto.getImagens() != null && !dto.getImagens().isEmpty()) {

		    List<ImagemProduto> imagens = new ArrayList<>();

		    for (ImagemProdutoDTO imgDto : dto.getImagens()) {

		        String imageBase64 = imgDto.getImagemOriginal();

		        if (imageBase64.contains("data:image")) {
		            imageBase64 = imageBase64.split(",")[1];
		        }

		        byte[] imageBytes = DatatypeConverter.parseBase64Binary(imageBase64);
		        BufferedImage original = ImageIO.read(new ByteArrayInputStream(imageBytes));

		        if (original == null) {
		            throw new ExceptionCustom("Imagem inválida.");
		        }

		        int largura = 800;
		        int altura = 600;

		        BufferedImage miniatura = new BufferedImage(largura, altura, BufferedImage.TYPE_INT_ARGB);
		        Graphics2D g = miniatura.createGraphics();
		        g.drawImage(original, 0, 0, largura, altura, null);
		        g.dispose();

		        ByteArrayOutputStream baos = new ByteArrayOutputStream();
		        ImageIO.write(miniatura, "png", baos);

		        String miniImageBase64 = "data:image/png;base64,"
		                + DatatypeConverter.printBase64Binary(baos.toByteArray());

		        ImagemProduto imagem = new ImagemProduto();
		        imagem.setProduto(produtoSalvo);
		        imagem.setEmpresa(empresa);
		        imagem.setImagemOriginal("data:image/jpeg;base64," + imageBase64); // CORREÇÃO
		        imagem.setImagemMiniatura(miniImageBase64); // AGORA NÃO FICA NULL

		        imagens.add(imagem);

		        original.flush();
		        miniatura.flush();
		        baos.close();
		    }

		    // CORREÇÃO CRÍTICA
		    for (ImagemProduto imagem : imagens) {
		        produtoSalvo.getImagens().add(imagem); // lista gerenciada
		    }

		    produtoRepository.save(produtoSalvo);
		}


		/* 8. Criar vínculo ProdutoFornecedor */
		ProdutoFornecedor produtoFornecedor = new ProdutoFornecedor();
		produtoFornecedor.setProduto(produtoSalvo);
		produtoFornecedor.setFornecedor(fornecedor);
		produtoFornecedor.setEmpresa(empresa);
		produtoFornecedor.setCodigoProdutoFornecedor(dto.getCodigoProdutoFornecedor());

		produtoFornecedorRepository.save(produtoFornecedor);
		
		/* 9. Montar DTO de retorno */
		ProdutoDTO dtoRetorno = produtoMapper.toDTO(produtoSalvo);
		dtoRetorno.setFornecedorId(fornecedor.getId());
		dtoRetorno.setCodigoProdutoFornecedor(produtoFornecedor.getCodigoProdutoFornecedor());

		/* 9.1 Preencher imagens manualmente */
		List<ImagemProdutoDTO> imagensDTO = produtoSalvo.getImagens()
		    .stream()
		    .map(img -> {
		        ImagemProdutoDTO dtoImg = new ImagemProdutoDTO();
		        dtoImg.setId(img.getId());
		        dtoImg.setImagemOriginal(img.getImagemOriginal());
		        dtoImg.setImagemMiniatura(img.getImagemMiniatura());
		        dtoImg.setProdutoId(produtoSalvo.getId());
		        dtoImg.setEmpresaId(empresa.getId());
		        return dtoImg;
		    })
		    .collect(Collectors.toList());

		dtoRetorno.setImagens(imagensDTO);

		return dtoRetorno;
	}

	/*
	 * =================== ATUALIZAR PRODUTO ===================
	 */
	public ProdutoDTO atualizar(Long id, ProdutoDTO dto, UsuarioLogadoPrincipal usuarioLogado) throws ExceptionCustom {

		if (id == null || id <= 0) {
			throw new ExceptionCustom("ID inválido");
		}

		Produto existente = produtoRepository.findById(id)
				.orElseThrow(() -> new ExceptionCustom("Produto não encontrado"));

		if (!existente.getEmpresa().getId().equals(usuarioLogado.getEmpresaId())) {
			throw new ExceptionCustom("Você não tem permissão para alterar este produto.");
		}

		/* Verificar se houve alteração */
		ProdutoDTO dtoAntigo = produtoMapper.toDTO(existente);
		if (dtoAntigo.equals(dto)) {
			throw new ExceptionCustom("Nenhuma alteração detectada.");
		}

		/* Buscar empresa */
		PessoaJuridica empresa = pessoaJuridicaRepository.findById(usuarioLogado.getEmpresaId())
				.orElseThrow(() -> new ExceptionCustom("Empresa não encontrada"));

		/* Buscar categoria */
		CategoriaProduto novaCategoria = categoriaProdutoRepository.findById(dto.getCategoriaId())
				.orElseThrow(() -> new ExceptionCustom("Categoria não encontrada"));

		if (!novaCategoria.getEmpresa().getId().equals(empresa.getId())) {
			throw new ExceptionCustom("A categoria informada não pertence à sua empresa.");
		}

		/* Buscar marca */
		MarcaProduto marcaProduto = marcaProdutoRepository.findById(dto.getMarcaId())
				.orElseThrow(() -> new ExceptionCustom("Marca não encontrada"));

		if (!marcaProduto.getEmpresa().getId().equals(empresa.getId())) {
			throw new ExceptionCustom("A marca informada não pertence à sua empresa.");
		}

		/* Buscar fornecedor */
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

		/* Atualizar Produto */
		produtoMapper.atualizarCamposDoProduto(dto, existente);
		existente.setEmpresa(empresa);
		existente.setCategoriaProduto(novaCategoria);
		existente.setMarcaProduto(marcaProduto);

		Produto produtoAtualizado = produtoRepository.save(existente);

		/* Buscar vínculo ProdutoFornecedor */
		Specification<ProdutoFornecedor> spec = Specification
				.where(ProdutoFornecedorSpec.produtoIgual(produtoAtualizado.getId()))
				.and(ProdutoFornecedorSpec.empresaIgual(usuarioLogado.getEmpresaId()));

		ProdutoFornecedor produtoFornecedor = produtoFornecedorRepository.findOne(spec).orElseGet(() -> {
			ProdutoFornecedor pf = new ProdutoFornecedor();
			pf.setProduto(produtoAtualizado);
			pf.setEmpresa(empresa);
			return pf;
		});

		/* Atualizar vínculo */
		produtoFornecedor.setFornecedor(fornecedor);
		produtoFornecedor.setCodigoProdutoFornecedor(dto.getCodigoProdutoFornecedor());

		produtoFornecedorRepository.save(produtoFornecedor);

		/* Retorno */
		ProdutoDTO dtoRetorno = produtoMapper.toDTO(produtoAtualizado);
		dtoRetorno.setFornecedorId(fornecedor.getId());
		dtoRetorno.setCodigoProdutoFornecedor(produtoFornecedor.getCodigoProdutoFornecedor());

		return dtoRetorno;
	}

	/*
	 * =================== DELETAR PRODUTO ===================
	 */
	public void deletar(Long id, UsuarioLogadoPrincipal usuarioLogado) throws ExceptionCustom {

		if (id == null || id <= 0) {
			throw new ExceptionCustom("ID inválido");
		}

		Produto produto = produtoRepository.findById(id)
				.orElseThrow(() -> new ExceptionCustom("Produto não encontrado"));

		if (!produto.getEmpresa().getId().equals(usuarioLogado.getEmpresaId())) {
			throw new ExceptionCustom("Você não tem permissão para excluir este produto.");
		}

		/* Remover vínculos */
		Specification<ProdutoFornecedor> spec = Specification.where(ProdutoFornecedorSpec.produtoIgual(produto.getId()))
				.and(ProdutoFornecedorSpec.empresaIgual(usuarioLogado.getEmpresaId()));

		List<ProdutoFornecedor> vinculos = produtoFornecedorRepository.findAll(spec);
		vinculos.forEach(produtoFornecedorRepository::delete);

		produtoRepository.delete(produto);
	}

	/*
	 * =================== CONSULTAS ===================
	 */

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

	@Transactional
	public void atualizarImagens(Long id, List<ImagemProdutoDTO> imagens, UsuarioLogadoPrincipal usuarioLogado)
			throws ExceptionCustom {

		Produto produto = produtoRepository.findById(id)
				.orElseThrow(() -> new ExceptionCustom("Produto não encontrado"));

		if (!produto.getEmpresa().getId().equals(usuarioLogado.getEmpresaId())) {
			throw new ExceptionCustom("Você não tem permissão para alterar este produto.");
		}

		produto.getImagens().clear();

		if (imagens != null && !imagens.isEmpty()) {
			produto.setImagens(imagens.stream().map(imgDto -> {
				try {
					String mini = ImageUtil.gerarMiniatura(imgDto.getImagemOriginal());
					com.bandampla.lojavirtual.model.ImagemProduto img = new com.bandampla.lojavirtual.model.ImagemProduto();
					img.setProduto(produto);
					img.setEmpresa(produto.getEmpresa());
					img.setImagemOriginal(imgDto.getImagemOriginal());
					img.setImagemMiniatura(mini);
					return img;
				} catch (Exception e) {
					throw new RuntimeException("Erro ao processar imagem", e);
				}
			}).collect(Collectors.toList()));
		}

		produtoRepository.save(produto);
	}

}