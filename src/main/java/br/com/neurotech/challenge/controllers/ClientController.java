package br.com.neurotech.challenge.controllers;

import br.com.neurotech.challenge.dto.Error;
import br.com.neurotech.challenge.dto.NeurotechClientDTO;
import br.com.neurotech.challenge.entity.NeurotechClient;
import br.com.neurotech.challenge.repository.CreditRepository;
import br.com.neurotech.challenge.service.ClientService;
import br.com.neurotech.challenge.service.CreditService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import java.net.URI;

/**
 * SpringBoot ClientController with RESTful routes for the client entity.
 */
@RestController()
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

    @PostMapping()
    ResponseEntity<?> createClient(HttpServletRequest request, @Valid @RequestBody NeurotechClientDTO form) {
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
            return ResponseEntity.badRequest().body(new Error(e.getMessage()));
        }
    }

    @GetMapping("/{id}")
    ResponseEntity<?> getClient(@PathVariable String id) {
        try {
            var client = clientService.get(id);

            var dto = new NeurotechClientDTO();
            dto.setName(client.getName());
            dto.setAge(client.getAge());
            dto.setIncome(client.getIncome());

            return ResponseEntity.ok(dto);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping()
    ResponseEntity<?> getAllClients() {
        try {
            var clients = clientService.getAll();

            var dto = clients.stream()
                    .map(NeurotechClientDTO::fromEntity)
                    .toList();

            return ResponseEntity.ok(dto);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    
    @DeleteMapping("/{id}")
    ResponseEntity<?> deleteClient(@PathVariable String id) {
        try {
            clientService.delete(id);
            return ResponseEntity.ok("Cliente removido com sucesso");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    ResponseEntity<?> updateClient(@PathVariable String id, @Valid @RequestBody NeurotechClientDTO form) {
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
