package br.com.neurotech.challenge.service.implementation;

import br.com.neurotech.challenge.entity.Credit;
import br.com.neurotech.challenge.entity.NeurotechClient;
import br.com.neurotech.challenge.entity.VehicleModel;
import br.com.neurotech.challenge.repository.NeurotechClientRepository;
import br.com.neurotech.challenge.service.CreditService;
import org.springframework.stereotype.Service;

import java.util.logging.Logger;

@Service
public class CreditServiceImpl implements CreditService {

    private final NeurotechClientRepository neurotechClientRepository;

    public CreditServiceImpl(NeurotechClientRepository repository) {
        this.neurotechClientRepository = repository;
    }


    @Override
    public boolean checkCredit(String clientId, VehicleModel model) {

        return neurotechClientRepository.findById(clientId)
                .map(client -> {
                    var isEligible = false;

                    switch (model) {
                        case HATCH -> {
                            isEligible = isEligibleForHatch(client, model);
                        }
                        case SUV -> {
                            isEligible = isEligibleForSUV(client, model);
                        }
                    }
                    return isEligible;
                })
                .orElseGet(() -> {
                    Logger.getLogger(getClass().getName()).warning("Cliente não encontrado");
                    return false;
                });
    }

    @Override
    public Credit createCreditForClient(NeurotechClient client) {
        Credit.CreditType creditType = determineCreditType(client);
        Credit credit = new Credit();
        credit.setType(creditType);

        switch (creditType) {
            case FIXED_INTEREST:
                credit.setInterestRate(Credit.FIXED_INTEREST_RATE);
                break;
            case VARIABLE_INTEREST:
                credit.setInterestRate(Credit.VARIABLE_INTEREST_RATE);
                break;
            case PAYROLL:
                credit.setInterestRate(Credit.PAYROLL_RATE);
                break;
        }

        credit.setClient(client);
        return credit;
    }

    private Credit.CreditType determineCreditType(NeurotechClient client) {
        if (client.getAge() >= 18 && client.getAge() <= 25) {
            return Credit.CreditType.FIXED_INTEREST;
        } else if (client.getAge() >= 21 && client.getAge() <= 65 && client.getIncome() >= 5000 && client.getIncome() <= 15000) {
            return Credit.CreditType.VARIABLE_INTEREST;
        } else if (client.getAge() > 65) {
            return Credit.CreditType.PAYROLL;
        }
        throw new IllegalArgumentException("Cliente não se qualifica para nenhum crédito.");
    }

    private boolean isEligibleForHatch(NeurotechClient client, VehicleModel model) {
        return VehicleModel.HATCH.equals(model) &&
                client.getIncome() >= Credit.HATCH_INCOME_MIN &&
                client.getIncome() <= Credit.HATCH_INCOME_MAX;
    }

    private boolean isEligibleForSUV(NeurotechClient client, VehicleModel model) {

        return VehicleModel.SUV.equals(model) &&
                client.getIncome() > Credit.SUV_INCOME_MIN &&
                client.getAge() > Credit.SUV_AGE_MIN;
    }
}
