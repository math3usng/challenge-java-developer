package br.com.neurotech.challenge.repository;

import br.com.neurotech.challenge.entity.Credit;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Interface de repositório para a entidade Credit.
 * <p>
 * Fornece métodos para realizar operações CRUD na tabela de Créditos.
 * Estende JpaRepository para herdar métodos prontos como salvar, deletar e buscar dados.
 *
 */
public interface CreditRepository extends JpaRepository<Credit, String> {
}
