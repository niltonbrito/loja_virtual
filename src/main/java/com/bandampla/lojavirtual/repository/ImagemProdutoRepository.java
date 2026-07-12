package com.bandampla.lojavirtual.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.bandampla.lojavirtual.model.ImagemProduto;

@Repository
public interface ImagemProdutoRepository
		extends JpaRepository<ImagemProduto, Long>, JpaSpecificationExecutor<ImagemProduto> {

}
