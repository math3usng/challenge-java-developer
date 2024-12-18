package br.com.neurotech.challenge.controllers;

import br.com.neurotech.challenge.dto.EligibleClientDTO;
import br.com.neurotech.challenge.dto.ErrorDTO;
import br.com.neurotech.challenge.entity.Credit;
import br.com.neurotech.challenge.entity.NeurotechClient;
import br.com.neurotech.challenge.entity.VehicleModel;
import br.com.neurotech.challenge.form.CheckCreditForm;
import br.com.neurotech.challenge.service.ClientService;
import br.com.neurotech.challenge.service.CreditService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
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

    @Operation(summary = "Verifica se um cliente é elegível para crédito com base no modelo do veículo",
            description = "Retorna true se o cliente for elegível, caso contrário false.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorna se o cliente é elegível ou não",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Boolean.class))),
            @ApiResponse(responseCode = "404", description = "Cliente não encontrado",
                    content = @Content)
    })
    @PostMapping("/check/{clientId}")
    public ResponseEntity<?> checkCredit(
            @PathVariable String clientId,
            @Valid @RequestBody()
            CheckCreditForm checkCreditForm) {

        try {
            NeurotechClient client = clientService.get(clientId);

            if (client == null) {
                return ResponseEntity.notFound().build();
            }

            boolean eligible = this.creditService.checkCredit(client.getId(), checkCreditForm.getVehicleModel());
            return ResponseEntity.ok(eligible);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ErrorDTO(e.getMessage()));
        }

    }

    @Operation(summary = "Obtém uma lista de clientes elegíveis para crédito com base em critérios",
            description = "Retorna todos os clientes que atendem aos critérios fornecidos, incluindo modelo de veículo, faixa etária e tipo de crédito.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de clientes elegíveis",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = EligibleClientDTO.class))),
            @ApiResponse(responseCode = "400", description = "Parâmetros inválidos",
                    content = @Content)
    })
    @GetMapping("/eligible/{vehicleModel}")
    public ResponseEntity<List<EligibleClientDTO>> getEligibleClients(
            @PathVariable @Schema(description = "Modelo de veículo", example = "HATCH") VehicleModel vehicleModel,
            @RequestParam() @Schema(description = "Idade mínima dos clientes", example = "18") Integer minAge,
            @RequestParam() @Schema(description = "Idade máxima dos clientes", example = "60") Integer maxAge,
            @RequestParam() @Schema(description = "Tipo de crédito", example = "FIXED_INTEREST") Credit.CreditType creditType) {

        var clients = clientService.getEligibleClients(
                vehicleModel, minAge, maxAge, creditType);

        var dto = clients.stream().map(client -> new EligibleClientDTO(client.getName(), client.getIncome())).toList();

        return ResponseEntity.ok(dto);
    }
}
