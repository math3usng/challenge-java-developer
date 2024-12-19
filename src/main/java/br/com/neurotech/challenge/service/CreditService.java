package br.com.neurotech.challenge.service;

import br.com.neurotech.challenge.entity.Credit;
import br.com.neurotech.challenge.entity.NeurotechClient;
import br.com.neurotech.challenge.entity.VehicleModel;


/**
 * Interface responsável por definir as operações relacionadas à análise e criação de créditos
 * para clientes da Neurotech, com base em modelos de veículos e informações específicas do cliente.
 */
public interface CreditService {
	
	/**
	 * Verifica se o cliente está apto para receber crédito para um modelo de veículo específico.
	 * 
	 * @param clientId O identificador único do cliente que será avaliado.
	 * @param model O modelo de veículo para o qual o crédito será analisado.
	 * @return true se o cliente estiver apto a receber crédito, false caso contrário.
	 */
	boolean checkCredit(String clientId, VehicleModel model);

	/**
	 * Cria um novo crédito para um cliente com base na categoria de crédito mais adequada ao perfil do cliente.
	 * 
	 * @param client O cliente para o qual o crédito será criado.
	 * @return Uma instância de {@link Credit} representando o crédito gerado para o cliente.
	 */
	Credit createCreditForClient(NeurotechClient client);
	
}
