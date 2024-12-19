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

/**
 * Controlador responsável por gerenciar operações relacionadas a crédito para os clientes.
 * Inclui endpoints para verificar elegibilidade e obter uma lista de clientes qualificados.
 */
@RestController()
@RequestMapping("/api/clients")
public class CreditController {

    private final ClientService clientService;
    private final CreditService creditService;

    /**
     * Construtor da classe CreditController.
     *
     * @param clientService Serviço responsável pelos clientes.
     * @param creditService Serviço responsável pelas operações de crédito.
     */
    public CreditController(ClientService clientService, CreditService creditService) {
        this.clientService = clientService;
        this.creditService = creditService;
    }

    /**
     * Verifica se um cliente é elegível para crédito com base no modelo do veículo.
     *
     * @param clientId        ID do cliente a ser verificado.
     * @param checkCreditForm Objeto contendo os dados do modelo do veículo.
     * @return Retorna um ResponseEntity contendo true se o cliente for elegível ou 404 caso o cliente não seja encontrado.
     */
    @Operation(summary = "Verifica se um cliente é elegível para crédito com base no modelo do veículo",
            description = "Retorna true se o cliente for elegível, caso contrário false.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorna se o cliente é elegível ou não",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Boolean.class))),
            @ApiResponse(responseCode = "404", description = "Cliente não encontrado",
                    content = @Content),
            @ApiResponse(responseCode = "400", description = "Error ao análisar crédito", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDTO.class)))
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

    /**
     * Obtém uma lista de clientes elegíveis para crédito com base nos critérios fornecidos.
     *
     * @param vehicleModel Modelo do veículo (exemplo: HATCH, SUV).
     * @param minAge       Idade mínima dos clientes.
     * @param maxAge       Idade máxima dos clientes.
     * @param creditType   Tipo de crédito solicitado.
     * @return Retorna um ResponseEntity contendo uma lista de clientes elegíveis no formato DTO.
     */
    @Operation(summary = "Obtém uma lista de clientes elegíveis para crédito com base em critérios",
            description = "Retorna todos os clientes que atendem aos critérios fornecidos, incluindo modelo de veículo, faixa etária e tipo de crédito.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de clientes elegíveis",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = EligibleClientDTO.class))),
            @ApiResponse(responseCode = "400", description = "Parâmetros inválidos",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDTO.class))),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDTO.class)))
    })
    @GetMapping("/eligible/{vehicleModel}")
    public ResponseEntity<?> getEligibleClients(
            @PathVariable @Schema(description = "Modelo de veículo", example = "HATCH") VehicleModel vehicleModel,
            @RequestParam() @Schema(description = "Idade mínima dos clientes", example = "18") Integer minAge,
            @RequestParam() @Schema(description = "Idade máxima dos clientes", example = "60") Integer maxAge,
            @RequestParam() @Schema(description = "Tipo de crédito", example = "FIXED_INTEREST") Credit.CreditType creditType) {
        try {
            if (minAge < 0 || maxAge < 0 || minAge > maxAge) {
                return ResponseEntity.badRequest().body(null);
            }

            var clients = clientService.getEligibleClients(
                    vehicleModel, minAge, maxAge, creditType);

            var dto = clients.stream()
                    .map(client -> new EligibleClientDTO(client.getName(), client.getIncome()))
                    .toList();

            return ResponseEntity.ok(dto);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(List.of(new ErrorDTO("Parâmetros inválidos: " + e.getMessage())));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(List.of(new ErrorDTO("Erro interno no servidor: " + e.getMessage())));
        }
    }
}
