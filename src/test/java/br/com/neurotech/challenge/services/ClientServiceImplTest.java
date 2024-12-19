package br.com.neurotech.challenge.services;

import br.com.neurotech.challenge.entity.Credit;
import br.com.neurotech.challenge.entity.NeurotechClient;
import br.com.neurotech.challenge.entity.VehicleModel;
import br.com.neurotech.challenge.repository.NeurotechClientRepository;
import br.com.neurotech.challenge.service.implementation.ClientServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ClientServiceImplTest {


    @Mock
    private NeurotechClientRepository repository;

    @InjectMocks
    private ClientServiceImpl clientService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldSaveClientAndReturnId() {
        // Arrange
        NeurotechClient client = new NeurotechClient();
        client.setId("123");
        client.setName("John Doe");

        when(repository.save(client)).thenReturn(client);

        // Act
        String clientId = clientService.save(client);

        // Assert
        assertEquals("123", clientId);
        verify(repository, times(1)).save(client);
    }

    @Test
    void shouldGetClientById() {
        // Arrange
        NeurotechClient client = new NeurotechClient();
        client.setId("123");
        client.setName("John Doe");

        when(repository.findById("123")).thenReturn(Optional.of(client));

        // Act
        NeurotechClient foundClient = clientService.get("123");

        // Assert
        assertNotNull(foundClient);
        assertEquals("John Doe", foundClient.getName());
        verify(repository, times(1)).findById("123");
    }

    @Test
    void shouldReturnNullWhenClientNotFound() {
        // Arrange
        when(repository.findById("999")).thenReturn(Optional.empty());

        // Act
        NeurotechClient foundClient = clientService.get("999");

        // Assert
        assertNull(foundClient);
        verify(repository, times(1)).findById("999");
    }

    @Test
    void shouldGetAllClients() {
        // Arrange
        NeurotechClient client1 = new NeurotechClient();
        client1.setId("1");
        client1.setName("Alice");

        NeurotechClient client2 = new NeurotechClient();
        client2.setId("2");
        client2.setName("Bob");

        List<NeurotechClient> clients = List.of(client1, client2);

        when(repository.findAll()).thenReturn(clients);

        // Act
        List<NeurotechClient> allClients = clientService.getAll();

        // Assert
        assertNotNull(allClients);
        assertEquals(2, allClients.size());
        assertEquals("Alice", allClients.get(0).getName());
        assertEquals("Bob", allClients.get(1).getName());
        verify(repository, times(1)).findAll();
    }

    @Test
    void shouldDeleteClientById() {
        // Act
        clientService.delete("123");

        // Assert
        verify(repository, times(1)).deleteById("123");
    }

    @Test
    void shouldGetEligibleClientsForHatch() {
        // Arrange
        NeurotechClient eligibleClient = new NeurotechClient();
        eligibleClient.setId("1");
        eligibleClient.setName("John Doe");
        eligibleClient.setIncome(3000.0);
        eligibleClient.setAge(25);

        List<NeurotechClient> eligibleClients = List.of(eligibleClient);

        when(repository.findEligibleClients(
                Credit.HATCH_INCOME_MIN,
                Credit.HATCH_INCOME_MAX,
                20,
                30,
                Credit.CreditType.FIXED_INTEREST)
        ).thenReturn(eligibleClients);

        // Act
        List<NeurotechClient> clients = clientService.getEligibleClients(
                VehicleModel.HATCH,
                20,
                30,
                Credit.CreditType.FIXED_INTEREST
        );

        // Assert
        assertNotNull(clients);
        assertEquals(1, clients.size());
        assertEquals("John Doe", clients.get(0).getName());
        verify(repository, times(1)).findEligibleClients(
                Credit.HATCH_INCOME_MIN,
                Credit.HATCH_INCOME_MAX,
                20,
                30,
                Credit.CreditType.FIXED_INTEREST
        );
    }

    @Test
    void shouldReturnEmptyListWhenNoEligibleClientsForNonHatchModel() {
        // Act
        List<NeurotechClient> clients = clientService.getEligibleClients(
                VehicleModel.SUV,
                20,
                30,
                Credit.CreditType.FIXED_INTEREST
        );

        // Assert
        assertNotNull(clients);
        assertTrue(clients.isEmpty(), "Clients list should be empty for non-HATCH models");
        verify(repository, never()).findEligibleClients(anyDouble(), anyDouble(), anyInt(), anyInt(), any());
    }
}
