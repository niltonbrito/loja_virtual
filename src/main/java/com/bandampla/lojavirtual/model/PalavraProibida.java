package com.bandampla.lojavirtual.model;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "palavra_proibida")
@SequenceGenerator(name = "seq_palavra_proibida", sequenceName = "seq_palavra_proibida", allocationSize = 1, initialValue = 1)
public class PalavraProibida extends EntidadeAuditavel {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_palavra_proibida")
	private Long id;

	@Column(nullable = false, unique = true)
	private String descricao;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PalavraProibida other = (PalavraProibida) obj;
		return Objects.equals(id, other.id);
	}
}