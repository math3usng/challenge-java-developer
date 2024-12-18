package br.com.neurotech.challenge.controllers;

import br.com.neurotech.challenge.dto.ErrorDTO;
import br.com.neurotech.challenge.dto.NeurotechClientDTO;
import br.com.neurotech.challenge.form.NeurotechClientForm;
import br.com.neurotech.challenge.entity.NeurotechClient;
import br.com.neurotech.challenge.repository.CreditRepository;
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
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

/**
 * SpringBoot ClientController with RESTful routes for the client entity.
 */
@RestController
@RequestMapping("/api/clients")
public class ClientController {

    private final ClientService clientService;
    private final CreditService creditService;
    private final CreditRepository creditRepository;

    public ClientController(ClientService clientService, CreditService creditService, CreditRepository creditRepository) {
        this.clientService = clientService;
        this.creditService = creditService;
        this.creditRepository = creditRepository;
    }

    @Operation(summary = "Cria um novo cliente e associa um registro de crédito")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Cliente criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Erro ao criar cliente", content = @Content(schema = @Schema(implementation = ErrorDTO.class)))
    })
    @PostMapping
    ResponseEntity<?> createClient(@Valid @RequestBody NeurotechClientForm form) {
        try {
            var client = new NeurotechClient();
            client.setName(form.getName());
            client.setAge(form.getAge());
            client.setIncome(form.getIncome());

            this.clientService.save(client);
            var credit = this.creditService.createCreditForClient(client);
            this.creditRepository.save(credit);

            URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(client.getId())
                    .toUri();

            return ResponseEntity.created(location).build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ErrorDTO(e.getMessage()));
        }
    }

    @Operation(summary = "Obtém um cliente pelo ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cliente recuperado com sucesso", content = @Content(schema = @Schema(implementation = NeurotechClientForm.class))),
            @ApiResponse(responseCode = "400", description = "Cliente não encontrado", content = @Content(schema = @Schema(implementation = String.class)))
    })
    @GetMapping("/{id}")
    ResponseEntity<?> getClient(@PathVariable String id) {
        try {
            var client = clientService.get(id);

            var dto = new NeurotechClientForm();
            dto.setName(client.getName());
            dto.setAge(client.getAge());
            dto.setIncome(client.getIncome());

            return ResponseEntity.ok(dto);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @Operation(summary = "Obtém todos os clientes")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Clientes recuperados com sucesso", content = @Content(schema = @Schema(implementation = NeurotechClientDTO.class))),
            @ApiResponse(responseCode = "400", description = "Erro ao recuperar clientes", content = @Content(schema = @Schema(implementation = String.class)))
    })
    @GetMapping
    ResponseEntity<?> getAllClients() {
        try {
            var clients = clientService.getAll();

            var dto = clients.stream()
                    .map(client -> new NeurotechClientDTO(client.getId(), client.getName(), client.getAge(), client.getIncome()))
                    .toList();

            return ResponseEntity.ok(dto);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @Operation(summary = "Exclui um cliente pelo ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cliente deletado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Erro ao deletar cliente", content = @Content(schema = @Schema(implementation = String.class)))
    })
    @DeleteMapping("/{id}")
    ResponseEntity<?> deleteClient(@PathVariable String id) {
        try {
            clientService.delete(id);
            return ResponseEntity.ok("Cliente removido com sucesso");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @Operation(summary = "Atualiza um cliente pelo ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cliente atualizado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Erro ao atualizar cliente", content = @Content(schema = @Schema(implementation = String.class)))
    })
    @PutMapping("/{id}")
    ResponseEntity<?> updateClient(@PathVariable String id, @Valid @RequestBody NeurotechClientForm form) {
        try {
            var client = clientService.get(id);

            client = new NeurotechClient();
            client.setName(form.getName());
            client.setAge(form.getAge());
            client.setIncome(form.getIncome());

            clientService.save(client);
            return ResponseEntity.ok("Cliente atualizado com sucesso");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
