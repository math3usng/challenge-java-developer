package br.com.neurotech.challenge.service.implementation;

import br.com.neurotech.challenge.entity.Credit;
import br.com.neurotech.challenge.entity.NeurotechClient;
import br.com.neurotech.challenge.entity.VehicleModel;
import br.com.neurotech.challenge.repository.NeurotechClientRepository;
import br.com.neurotech.challenge.service.ClientService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientServiceImpl implements ClientService {

    private final NeurotechClientRepository repository;

    public ClientServiceImpl(NeurotechClientRepository repository) {
        this.repository = repository;
    }

    @Override
    public String save(NeurotechClient client) {
        repository.save(client);
        return client.getId();
    }

    @Override
    public NeurotechClient get(String id) {
        return this.repository.findById(id).orElse(null);
    }

    public List<NeurotechClient> getAll() {
        return this.repository.findAll();
    }

    @Override
    public void delete(String id) {
        this.repository.deleteById(id);
    }

    @Override
    public List<NeurotechClient> getEligibleClients(VehicleModel vehicleModel, Integer minAge, Integer maxAge, Credit.CreditType creditType) {

        if (VehicleModel.HATCH.equals(vehicleModel)) {
            return this.repository.findEligibleClients(Credit.HATCH_INCOME_MIN, Credit.HATCH_INCOME_MAX, minAge, maxAge, creditType);
        }

        return List.of();
    }
}
