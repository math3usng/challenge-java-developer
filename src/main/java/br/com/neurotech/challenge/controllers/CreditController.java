package br.com.neurotech.challenge.controllers;

import br.com.neurotech.challenge.dto.EligibleClients;
import br.com.neurotech.challenge.dto.NeurotechClientDTO;
import br.com.neurotech.challenge.entity.Credit;
import br.com.neurotech.challenge.entity.NeurotechClient;
import br.com.neurotech.challenge.entity.VehicleModel;
import br.com.neurotech.challenge.service.ClientService;
import br.com.neurotech.challenge.service.CreditService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@RequestMapping("/api/clients")
public class CreditController {

    private final ClientService clientService;
    private final CreditService creditService;

    public CreditController(ClientService clientService, CreditService creditService) {
        this.clientService = clientService;
        this.creditService = creditService;
    }

    @PostMapping("/check/{clientId}")
    public ResponseEntity<Boolean> checkCredit(@PathVariable String clientId, @RequestBody VehicleModel vehicleType) {
        NeurotechClient client = clientService.get(clientId);
        boolean eligible = this.creditService.checkCredit(client.getId(), vehicleType);
        return ResponseEntity.ok(eligible);
    }

    @GetMapping("/eligible/{vehicleModel}")
    public ResponseEntity<List<EligibleClients>> getEligibleClients(
            @PathVariable VehicleModel vehicleModel,
            @RequestParam() Integer minAge,
            @RequestParam() Integer maxAge,
            @RequestParam() Credit.CreditType creditType) {


        var clients = clientService.getEligibleClients(
                vehicleModel, minAge, maxAge, creditType);

        var dto = clients.stream().map(client -> new EligibleClients(client.getName(), client.getIncome())).toList();

        return ResponseEntity.ok(dto);
    }
}
