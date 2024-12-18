package br.com.neurotech.challenge.service;

import br.com.neurotech.challenge.entity.Credit;
import br.com.neurotech.challenge.entity.NeurotechClient;
import br.com.neurotech.challenge.entity.VehicleModel;

public interface CreditService {
	
	/**
	 * Efetua a checagem se o cliente está apto a receber crédito
	 * para um determinado modelo de veículo
	 */
	boolean checkCredit(String clientId, VehicleModel model);

	/**
	 * Cria um novo credito para um cliente de acordo com a categoria que ele mais se encaixa
	 * @param client
	 * @return
	 */
	Credit createCreditForClient(NeurotechClient client);
	
}
