package com.bandampla.lojavirtual.service;

import java.util.Calendar;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.bandampla.lojavirtual.dto.request.CepDTO;
import com.bandampla.lojavirtual.dto.request.PessoaFisicaRequestDTO;
import com.bandampla.lojavirtual.dto.request.PessoaJuridicaRequestDTO;
import com.bandampla.lojavirtual.enums.RoleUser;
import com.bandampla.lojavirtual.exception.ExceptionCustom;
import com.bandampla.lojavirtual.mapper.PessoaMapper;
import com.bandampla.lojavirtual.model.Pessoa;
import com.bandampla.lojavirtual.model.PessoaFisica;
import com.bandampla.lojavirtual.model.PessoaJuridica;
import com.bandampla.lojavirtual.model.Usuario;
import com.bandampla.lojavirtual.repository.PessoaFisicaRepository;
import com.bandampla.lojavirtual.repository.PessoaJuridicaRepository;
import com.bandampla.lojavirtual.repository.UsuarioRepository;
import com.bandampla.lojavirtual.util.ValidaCNPJ;
import com.bandampla.lojavirtual.util.ValidaCPF;

@Service
public class PessoaUserService {

    @Autowired
    private PessoaJuridicaRepository pessoaJuridicaRepository;

    @Autowired
    private PessoaFisicaRepository pessoaFisicaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private SendMailService sendMailService;

    @Autowired
    private PessoaMapper mapper;

    // ============================================================
    // SALVAR PESSOA JURÍDICA
    // ============================================================
    public PessoaJuridica salvarPessoaJuridica(PessoaJuridicaRequestDTO dto) throws ExceptionCustom {

        if (dto == null) {
            throw new ExceptionCustom("Pessoa Jurídica não pode ser NULL");
        }

        String cnpj = ValidaCNPJ.cnpjSemMascara(dto.getCnpj());

        if (!ValidaCNPJ.isCNPJ(cnpj)) {
            throw new ExceptionCustom("CNPJ inválido: " + ValidaCNPJ.cnpjComMascara(cnpj));
        }

		Optional<PessoaJuridica> cnpjOpt = pessoaJuridicaRepository.findByCnpj(dto.getCnpj());
		if (cnpjOpt.isPresent() && !cnpjOpt.get().getId().equals(dto.getId())) {
			throw new ExceptionCustom("CNPJ já cadastrado no sistema.");
		}

		Optional<PessoaJuridica> inscricaoEstadual = pessoaJuridicaRepository
				.findByInscricaoEstadual(dto.getInscricaoEstadual());
		if (inscricaoEstadual.isPresent() && !inscricaoEstadual.get().getId().equals(dto.getId())) {
			throw new ExceptionCustom("Inscrição Estadual já cadastrada");
		}

        // Converte DTO → Model
        PessoaJuridica pj = mapper.toModel(dto);
        pj.setCnpj(ValidaCNPJ.cnpjSemMascara(dto.getCnpj()));
        // Salva
        pj = pessoaJuridicaRepository.save(pj);

        // Cria usuário
        criarUsuarioAcesso(pj.getEmail(), pj, pj, RoleUser.ROLE_ADMIN);

        return pj;
    }

    // ============================================================
    // SALVAR PESSOA FÍSICA
    // ============================================================
    public PessoaFisica salvarPessoaFisica(PessoaFisicaRequestDTO dto) throws ExceptionCustom {

        if (dto == null) {
            throw new ExceptionCustom("Pessoa Física não pode ser NULL");
        }

        dto.setCpf(ValidaCPF.cpfSemMascara(dto.getCpf()));

        if (!ValidaCPF.isCPF(dto.getCpf())) {
            throw new ExceptionCustom("CPF inválido: " + ValidaCPF.cpfComMascara(dto.getCpf()));
        }

		Optional<PessoaFisica> cpf = pessoaFisicaRepository.findByCpf(dto.getCpf());
		if (cpf.isPresent() && !cpf.get().getId().equals(dto.getId())) {
			throw new ExceptionCustom(
					"Já existe CPF: " + ValidaCPF.cpfComMascara(dto.getCpf()) + " cadastrado no sistema.");
		}

        // Buscar empresa pelo CNPJ enviado no JSON
        String cnpj = ValidaCNPJ.cnpjSemMascara(dto.getEmpresa().getCnpj());

        PessoaJuridica empresa = pessoaJuridicaRepository.findByCnpj(cnpj)
                .orElseThrow(() -> new ExceptionCustom(
                        "Empresa não encontrada para o CNPJ: " + ValidaCNPJ.cnpjComMascara(cnpj)));

        // Converte DTO → Model
        PessoaFisica pf = mapper.toModel(dto, empresa);

        // Salva
        pf = pessoaFisicaRepository.save(pf);

        // Cria usuário
        criarUsuarioAcesso(pf.getEmail(), pf, empresa, RoleUser.ROLE_USER);

        return pf;
    }
    
    public CepDTO consultaCep(String cep) {    	
    	return new RestTemplate().getForEntity("https://viacep.com.br/ws/"+ cep+ "/json/", CepDTO.class).getBody();
	}

    // ============================================================
    // MÉTODOS PRIVADOS
    // ============================================================

    private void criarUsuarioAcesso(String email, Object pessoa, PessoaJuridica empresa, RoleUser role) {

        Long idPessoa = pessoa instanceof PessoaFisica
                ? ((PessoaFisica) pessoa).getId()
                : ((PessoaJuridica) pessoa).getId();

        Usuario usuario = usuarioRepository.finUserByPessoa(idPessoa, email);

        if (usuario != null)
            return;

        removerConstraintAcesso();

        usuario = new Usuario();
        usuario.setLogin(email);

        String senha = "" + Calendar.getInstance().getTimeInMillis();
        usuario.setSenha(new BCryptPasswordEncoder().encode(senha));

        usuario.setCreateAt(Calendar.getInstance().getTime());
        usuario.setUpdateAt(Calendar.getInstance().getTime());
        usuario.setEmpresa(empresa);
        usuario.setPessoa((Pessoa) pessoa);

        usuario = usuarioRepository.save(usuario);
        usuarioRepository.insereAcessoUser(usuario.getId(), role);

        enviarEmailAcesso(email, senha);
    }

    private void removerConstraintAcesso() {
        String constraint = usuarioRepository.consultaConstraintAcesso();
        if (constraint != null) {
            jdbcTemplate.execute("begin; ALTER TABLE usuario_acesso DROP CONSTRAINT " + constraint+ "; commit");
        }
    }

    private void enviarEmailAcesso(String email, String senha) {
        StringBuilder msg = new StringBuilder();
        msg.append("<b>Segue abaixo seus dados de acesso para a loja virtual</b><br/>")
                .append("<b>Login: </b>").append(email).append("<br/>")
                .append("<b>Senha: </b>").append(senha).append("<br/><br/>")
                .append("Obrigado");

        try {
            sendMailService.enviarEmailHtml(
                    "Credencial Criada para acesso à plataforma Loja Virtual Bandampla!",
                    msg.toString(),
                    email);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
