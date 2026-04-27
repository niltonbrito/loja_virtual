/**
 * 
 */
package com.bandampla.lojavirtual.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bandampla.lojavirtual.model.Acesso;

/**
 * @author: Nilton Brito 
 * @Email:  <nilton.brito@outlook.com>
 * @Data:   27 de abr. de 2026
 */

@Repository
@Transactional
public interface AcessoRepository extends JpaRepository<Acesso, Long>{

}
