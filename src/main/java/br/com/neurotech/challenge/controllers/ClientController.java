package br.com.neurotech.challenge.controllers;

import br.com.neurotech.challenge.dto.ErrorDTO;
import br.com.neurotech.challenge.dto.NeurotechClientDTO;
import br.com.neurotech.challenge.entity.NeurotechClient;
import br.com.neurotech.challenge.form.NeurotechClientForm;
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
 * Controlador REST para gerenciar operações relacionadas à entidade Cliente.
 * Proporciona endpoints para criar, recuperar, atualizar e excluir registros de clientes.
 */
@RestController
@RequestMapping("/api/clients")
public class ClientController {

    private final ClientService clientService;
    private final CreditService creditService;
    private final CreditRepository creditRepository;

    /**
     * Construtor do controlador de clientes.
     *
     * @param clientService    Serviço de gerenciamento de clientes.
     * @param creditService    Serviço de gerenciamento de crédito associado aos clientes.
     * @param creditRepository Repositório de créditos.
     */
    public ClientController(ClientService clientService, CreditService creditService, CreditRepository creditRepository) {
        this.clientService = clientService;
        this.creditService = creditService;
        this.creditRepository = creditRepository;
    }

    /**
     * Endpoint para criar um novo cliente e associar um registro de crédito.
     *
     * @param form Dados do cliente fornecidos no corpo da requisição.
     * @return Resposta HTTP 201 (Created) se bem-sucedido ou HTTP 400 (Bad Request) em caso de erro.
     */
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

    /**
     * Endpoint para recuperar um cliente específico pelo seu ID.
     *
     * @param id Identificador do cliente a ser recuperado.
     * @return Dados do cliente ou mensagem de erro no formato HTTP 400 (Bad Request).
     */
    @Operation(summary = "Obtém um cliente pelo ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cliente recuperado com sucesso", content = @Content(schema = @Schema(implementation = NeurotechClientForm.class))),
            @ApiResponse(responseCode = "400", description = "Cliente não encontrado", content = @Content(schema = @Schema(implementation = ErrorDTO.class)))
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
            return ResponseEntity.badRequest().body(new ErrorDTO(e.getMessage()));
        }
    }

    /**
     * Endpoint para recuperar todos os clientes cadastrados.
     *
     * @return Lista de clientes ou mensagem de erro se não for possível recuperar os dados.
     */
    @Operation(summary = "Obtém todos os clientes")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Clientes recuperados com sucesso", content = @Content(schema = @Schema(implementation = NeurotechClientDTO.class))),
            @ApiResponse(responseCode = "400", description = "Erro ao recuperar clientes", content = @Content(schema = @Schema(implementation = ErrorDTO.class)))
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
            return ResponseEntity.badRequest().body(new ErrorDTO(e.getMessage()));
        }
    }

    /**
     * Endpoint para excluir um cliente pelo seu ID.
     *
     * @param id Identificador do cliente a ser excluído.
     * @return Mensagem indicando o sucesso ou erro na exclusão do cliente.
     */
    @Operation(summary = "Exclui um cliente pelo ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cliente deletado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Erro ao deletar cliente", content = @Content(schema = @Schema(implementation = ErrorDTO.class)))
    })
    @DeleteMapping("/{id}")
    ResponseEntity<?> deleteClient(@PathVariable String id) {
        try {
            clientService.delete(id);
            return ResponseEntity.ok("Cliente removido com sucesso");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ErrorDTO(e.getMessage()));
        }
    }

    /**
     * Endpoint para atualizar informações de um cliente existente pelo seu ID.
     *
     * @param id   ID do cliente a ser atualizado.
     * @param form Dados atualizados do cliente fornecidos no corpo da requisição.
     * @return Mensagem indicando o sucesso ou falha na atualização.
     */
    @Operation(summary = "Atualiza um cliente pelo ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cliente atualizado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Erro ao atualizar cliente", content = @Content(schema = @Schema(implementation = ErrorDTO.class)))
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
            return ResponseEntity.badRequest().body(new ErrorDTO(e.getMessage()));
        }
    }

}
