package br.com.neurotech.challenge.service;

import br.com.neurotech.challenge.entity.Credit;
import br.com.neurotech.challenge.entity.NeurotechClient;
import br.com.neurotech.challenge.entity.VehicleModel;

import java.util.List;


public interface ClientService {
	
	/**
	 * Salva um novo cliente
	 * 
	 * @return ID do cliente recém-salvo
	 */
	String save(NeurotechClient client);
	
	/**
	 * Recupera um cliente baseado no seu ID
	 */
	NeurotechClient get(String id);

	List<NeurotechClient> getAll();

	void delete(String id);

	List<NeurotechClient> getEligibleClients(VehicleModel vehicleModel, Integer minAge, Integer maxAge, Credit.CreditType creditType);
}
