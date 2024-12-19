package br.com.neurotech.challenge.service;

import br.com.neurotech.challenge.entity.Credit;
import br.com.neurotech.challenge.entity.NeurotechClient;
import br.com.neurotech.challenge.entity.VehicleModel;

import java.util.List;


/**
 * Serviço para gerenciar operações relacionadas a clientes.
 * Fornece funcionalidades para salvar, recuperar, excluir e listar 
 * clientes, além de realizar filtragem com base em critérios específicos.
 */
public interface ClientService {

	/**
	 * Salva um novo cliente.
	 *
	 * @param client Instância de {@link NeurotechClient} que representa o cliente a ser salvo.
	 * @return ID do cliente recém-salvo.
	 */
	String save(NeurotechClient client);

	/**
	 * Recupera um cliente baseado no seu ID.
	 *
	 * @param id Identificador único do cliente.
	 * @return O cliente correspondente ao ID fornecido, representado por {@link NeurotechClient}.
	 */
	NeurotechClient get(String id);

	/**
	 * Retorna uma lista com todos os clientes previamente salvos.
	 *
	 * @return Lista contendo instâncias de {@link NeurotechClient}.
	 */
	List<NeurotechClient> getAll();

	/**
	 * Exclui um cliente baseado no seu ID.
	 *
	 * @param id O identificador único do cliente a ser excluído.
	 */
	void delete(String id);

	/**
	 * Obtém uma lista de clientes elegíveis com base nos critérios fornecidos.
	 *
	 * @param vehicleModel Modelo de veículo desejado para a elegibilidade.
	 * @param minAge Idade mínima do cliente.
	 * @param maxAge Idade máxima do cliente.
	 * @param creditType Tipo de crédito a ser considerado para a elegibilidade.
	 * @return Lista de clientes que atendem aos critérios, representados por instâncias de {@link NeurotechClient}.
	 */
	List<NeurotechClient> getEligibleClients(VehicleModel vehicleModel, Integer minAge, Integer maxAge, Credit.CreditType creditType);
}
