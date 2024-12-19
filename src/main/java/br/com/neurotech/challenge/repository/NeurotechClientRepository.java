package br.com.neurotech.challenge.repository;

import br.com.neurotech.challenge.entity.Credit;
import br.com.neurotech.challenge.entity.NeurotechClient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
/**
 * Repositório responsável por gerenciar interações com a entidade NeurotechClient no banco de dados.
 */
public interface NeurotechClientRepository extends JpaRepository<NeurotechClient, String> {


    /**
     * Busca clientes elegíveis com base em intervalos de renda, idade e tipo de crédito.
     *
     * @param minIncome Renda mínima para a busca.
     * @param maxIncome Renda máxima para a busca.
     * @param minAge Idade mínima para a busca.
     * @param maxAge Idade máxima para a busca.
     * @param creditType Tipo de crédito associado.
     * @return Lista de clientes elegíveis que atendem aos critérios especificados.
     */
    @Query("SELECT c FROM NeurotechClient c " +
            "JOIN c.credits credit " +
            "WHERE c.income BETWEEN :minIncome AND :maxIncome " +
            "AND c.age BETWEEN :minAge AND :maxAge " +
            "AND credit.type = :creditType")
    List<NeurotechClient> findEligibleClients(@Param("minIncome") Double minIncome,
                                     @Param("maxIncome") Double maxIncome,
                                     @Param("minAge") Integer minAge,
                                     @Param("maxAge") Integer maxAge,
                                     @Param("creditType") Credit.CreditType creditType);

}
