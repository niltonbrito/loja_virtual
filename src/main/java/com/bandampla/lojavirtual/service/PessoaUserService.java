package com.bandampla.lojavirtual.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.bandampla.lojavirtual.dto.CepDTO;
import com.bandampla.lojavirtual.dto.CnpjDTO;
import com.bandampla.lojavirtual.dto.PessoaFisicaDTO;
import com.bandampla.lojavirtual.dto.PessoaJuridicaDTO;
import com.bandampla.lojavirtual.enums.TipoCadastro;
import com.bandampla.lojavirtual.enums.TipoPessoa;
import com.bandampla.lojavirtual.exception.ExceptionCustom;
import com.bandampla.lojavirtual.mapper.PessoaMapper;
import com.bandampla.lojavirtual.model.Endereco;
import com.bandampla.lojavirtual.model.Pessoa;
import com.bandampla.lojavirtual.model.PessoaFisica;
import com.bandampla.lojavirtual.model.PessoaJuridica;
import com.bandampla.lojavirtual.repository.EnderecoRepository;
import com.bandampla.lojavirtual.repository.PessoaFisicaRepository;
import com.bandampla.lojavirtual.repository.PessoaJuridicaRepository;
import com.bandampla.lojavirtual.util.ValidaCEP;
import com.bandampla.lojavirtual.util.ValidaCNPJ;
import com.bandampla.lojavirtual.util.ValidaCPF;

@Service
public class PessoaUserService {

	// 🔥 Definição ideal: Todos as dependências injetadas via construtor imutável
	// (final)
	private final PessoaFisicaRepository pessoaFisicaRepository;
	private final PessoaJuridicaRepository pessoaJuridicaRepository;
	private final EnderecoRepository enderecoRepository;
	private final PessoaMapper pessoaMapper;

	public PessoaUserService(PessoaFisicaRepository pessoaFisicaRepository,
			PessoaJuridicaRepository pessoaJuridicaRepository, EnderecoRepository enderecoRepository, PessoaMapper pessoaMapper) {
		this.pessoaFisicaRepository = pessoaFisicaRepository;
		this.pessoaJuridicaRepository = pessoaJuridicaRepository;
		this.enderecoRepository = enderecoRepository;
		this.pessoaMapper = pessoaMapper;
	}

	@Transactional(rollbackFor = Exception.class)
	public PessoaJuridicaDTO salvarPessoaJuridica(PessoaJuridicaDTO dto) throws ExceptionCustom {
		if (dto == null) {
			throw new ExceptionCustom("Pessoa Jurídica não pode ser nula.");
		}

		PessoaJuridica pessoaJuridica = pessoaMapper.toModel(dto);

		if (pessoaJuridica.getTipoPessoa() == null) {
			throw new ExceptionCustom("Informe o tipo de Pessoa.");
		}
		if (pessoaJuridica.getTipoCadastro() == null) {
			throw new ExceptionCustom("Informe o tipo de Cadastro.");
		}

		/* Validação de CNPJ */
		Optional<PessoaJuridica> pjCnpj = pessoaJuridicaRepository
				.findByCnpj(ValidaCNPJ.cnpjSemMascara(pessoaJuridica.getCnpj().trim()));
		if (pjCnpj.isPresent() && !pjCnpj.get().getId().equals(pessoaJuridica.getId())) {
			throw new ExceptionCustom("CNPJ já cadastrado no sistema");
		}

		/* Validação de IE */
		if (pessoaJuridica.getInscricaoEstadual() != null) {
			Optional<PessoaJuridica> pjIe = pessoaJuridicaRepository
					.findByInscricaoEstadual(pessoaJuridica.getInscricaoEstadual().trim());
			if (pjIe.isPresent() && !pjIe.get().getId().equals(pessoaJuridica.getId())) {
				throw new ExceptionCustom("Inscrição Estadual já cadastrada");
			}
		}

		/* MATRIZ / FILIAL */
		if (pessoaJuridica.getMatriz() != null && pessoaJuridica.getMatriz().getCnpj() != null) {
			String cnpjMatriz = ValidaCNPJ.cnpjSemMascara(pessoaJuridica.getMatriz().getCnpj().trim());
			PessoaJuridica matriz = pessoaJuridicaRepository.findByCnpj(cnpjMatriz)
					.orElseThrow(() -> new ExceptionCustom("Matriz com CNPJ " + cnpjMatriz + " não encontrada."));
			pessoaJuridica.setMatriz(matriz);
		} else {
			pessoaJuridica.setMatriz(null);
		}

		cadastrarEnderecos(pessoaJuridica);
		pessoaJuridica.setTipoPessoa(TipoPessoa.JURIDICA);
		pessoaJuridica.setTipoCadastro(TipoCadastro.EMPRESA);
		pessoaJuridica.setCnpj(ValidaCNPJ.cnpjSemMascara(pessoaJuridica.getCnpj().trim()));

		PessoaJuridica salva = pessoaJuridicaRepository.save(pessoaJuridica);

		return pessoaMapper.toDTO(salva);
	}

	@Transactional(rollbackFor = Exception.class)
	public PessoaFisicaDTO salvarPessoaFisica(PessoaFisicaDTO dto) throws ExceptionCustom {
		if (dto == null) {
			throw new ExceptionCustom("Pessoa Física não pode ser nula.");
		}

		PessoaFisica pessoaFisica = pessoaMapper.toModel(dto);

		if (pessoaFisica.getTipoPessoa() == null) {
			throw new ExceptionCustom("Informe o tipo de Pessoa.");
		}
		if (pessoaFisica.getTipoCadastro() == null) {
			throw new ExceptionCustom("Informe o tipo de Cadastro.");
		}

		/* Validação de CPF */
		Optional<PessoaFisica> pessoaFisicaOpt = pessoaFisicaRepository
				.findByCpf(ValidaCPF.cpfSemMascara(pessoaFisica.getCpf()));
		if (pessoaFisicaOpt.isPresent() && !pessoaFisicaOpt.get().getId().equals(pessoaFisica.getId())) {
			throw new ExceptionCustom("CPF já cadastrado no sistema");
		}

		/* Empresa obrigatória */
		if (pessoaFisica.getEmpresa() == null || pessoaFisica.getEmpresa().getCnpj() == null) {
			throw new ExceptionCustom("Pessoa Física deve estar vinculada a uma empresa (CNPJ).");
		}

		cadastrarEnderecos(pessoaFisica);
		String cnpjEmpresa = ValidaCNPJ.cnpjSemMascara(pessoaFisica.getEmpresa().getCnpj());
		PessoaJuridica empresa = pessoaJuridicaRepository.findByCnpj(cnpjEmpresa)
				.orElseThrow(() -> new ExceptionCustom("Empresa com CNPJ " + cnpjEmpresa + " não encontrada."));

		pessoaFisica.setEmpresa(empresa);
		pessoaFisica.setTipoPessoa(TipoPessoa.FISICA);
		pessoaFisica.setTipoCadastro(TipoCadastro.CLIENTE);
		pessoaFisica.setCpf(ValidaCPF.cpfSemMascara(pessoaFisica.getCpf().trim()));

		PessoaFisica salva = pessoaFisicaRepository.save(pessoaFisica);

		return pessoaMapper.toDTO(salva);
	}

	private void cadastrarEnderecos(Pessoa pessoa) throws ExceptionCustom {
		if (pessoa.getEnderecos() == null)
			return;

		for (Endereco end : pessoa.getEnderecos()) {
			if (pessoa.getId() == null || end.getId() == null) {
				CepDTO cepDTO = consultaCep(ValidaCEP.cepSemMascara(end.getCep()));
				if (cepDTO == null || cepDTO.getCep() == null) {
					throw new ExceptionCustom("CEP inválido.");
				}
				end.setBairro(cepDTO.getBairro());
				end.setCep(ValidaCEP.cepSemMascara(cepDTO.getCep()));
				end.setCidade(cepDTO.getLocalidade());
				end.setComplemento(cepDTO.getComplemento());
				end.setRua(cepDTO.getLogradouro());
				end.setUf(cepDTO.getUf());
				end.setPessoa(pessoa);
				continue;
			}

			Endereco endBanco = enderecoRepository.findById(end.getId())
					.orElseThrow(() -> new ExceptionCustom("Endereço não encontrado."));
			String cepNovo = ValidaCEP.cepSemMascara(end.getCep());
			String cepAntigo = endBanco.getCep();

			if (!cepNovo.equals(cepAntigo)) {
				CepDTO cepDTO = consultaCep(cepNovo);
				if (cepDTO == null || cepDTO.getCep() == null) {
					throw new ExceptionCustom("CEP inválido.");
				}
				end.setBairro(cepDTO.getBairro());
				end.setCep(ValidaCEP.cepSemMascara(cepDTO.getCep()));
				end.setCidade(cepDTO.getLocalidade());
				end.setComplemento(cepDTO.getComplemento());
				end.setRua(cepDTO.getLogradouro());
				end.setUf(cepDTO.getUf());
				end.setPessoa(pessoa);
			} else {
				end.setPessoa(pessoa);
			}
		}
	}

	public CepDTO consultaCep(String cep) throws RestClientException, ExceptionCustom {
		return new RestTemplate()
				.getForEntity("https://viacep.com.br" + ValidaCEP.cepSemMascara(cep) + "/json/", CepDTO.class)
				.getBody();
	}

	public CnpjDTO consultaCnpj(String cnpj) throws ExceptionCustom {
		if (cnpj == null || cnpj.length() != 14) {
			throw new ExceptionCustom("CNPJ informado deve possuir 14 caracteres.");
		}
		if (!ValidaCNPJ.isCNPJ(cnpj)) {
			throw new ExceptionCustom("CNPJ informado é inválido.");
		}
		return new RestTemplate().getForEntity("receitaws.com.br" + ValidaCNPJ.cnpjSemMascara(cnpj), CnpjDTO.class)
				.getBody();
	}

	public List<PessoaJuridicaDTO> consultaPessoaJuridicaPorNome(String nome) throws ExceptionCustom {
		if (nome == null || nome.trim().isEmpty()) {
			throw new ExceptionCustom("Nome não pode estar vazio");
		}
		List<PessoaJuridica> lista = pessoaJuridicaRepository.findAllByNome(nome.trim());
		if (lista.isEmpty()) {
			throw new ExceptionCustom("Nenhuma pessoa jurídica encontrada com o nome informado.");
		}
		return pessoaMapper.toJuridicaDTOList(lista);
	}

	public List<PessoaJuridicaDTO> consultaPessoaJuridicaPorCnpj(String cnpj) throws ExceptionCustom {
		if (cnpj == null || cnpj.trim().isEmpty()) {
			throw new ExceptionCustom("CNPJ não pode estar vazio");
		}
		List<PessoaJuridica> lista = pessoaJuridicaRepository.findAllByCnpj(cnpj.trim());
		if (lista.isEmpty()) {
			throw new ExceptionCustom("Nenhuma pessoa jurídica encontrada com o CNPJ informado.");
		}
		return pessoaMapper.toJuridicaDTOList(lista);
	}
}