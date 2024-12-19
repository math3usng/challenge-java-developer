package br.com.neurotech.challenge.services;

import br.com.neurotech.challenge.entity.Credit;
import br.com.neurotech.challenge.entity.NeurotechClient;
import br.com.neurotech.challenge.entity.VehicleModel;
import br.com.neurotech.challenge.repository.NeurotechClientRepository;
import br.com.neurotech.challenge.service.implementation.CreditServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CreditServiceImplTest {

    @Mock
    private NeurotechClientRepository neurotechClientRepository;

    @InjectMocks
    private CreditServiceImpl creditService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldCheckCreditEligibilityForHatch_Success() {
        // Mock client data
        NeurotechClient client = new NeurotechClient();
        client.setId("123");
        client.setIncome(15000.0);
        client.setAge(25);

        when(neurotechClientRepository.findById("123")).thenReturn(Optional.of(client));

        // Act
        boolean isEligible = creditService.checkCredit("123", VehicleModel.HATCH);

        // Assert
        assertTrue(isEligible, "Client should be eligible for HATCH");
        verify(neurotechClientRepository, times(1)).findById("123");
    }

    @Test
    void shouldCheckCreditEligibilityForSUV_Success() {
        // Mock client data
        NeurotechClient client = new NeurotechClient();
        client.setId("456");
        client.setIncome(8100.0);
        client.setAge(30);

        when(neurotechClientRepository.findById("456")).thenReturn(Optional.of(client));

        // Act
        boolean isEligible = creditService.checkCredit("456", VehicleModel.SUV);

        // Assert
        assertTrue(isEligible, "Client should be eligible for SUV");
        verify(neurotechClientRepository, times(1)).findById("456");
    }

    @Test
    void shouldReturnFalseWhenClientNotFound() {
        when(neurotechClientRepository.findById("999")).thenReturn(Optional.empty());

        // Act
        boolean isEligible = creditService.checkCredit("999", VehicleModel.HATCH);

        // Assert
        assertFalse(isEligible, "Client should not be eligible when not found");
        verify(neurotechClientRepository, times(1)).findById("999");
    }

    @Test
    void shouldCreateCreditForClient_FixedInterest() {
        // Mock client data
        NeurotechClient client = new NeurotechClient();
        client.setAge(20);
        client.setIncome(2500.0);

        // Act
        Credit credit = creditService.createCreditForClient(client);

        // Assert
        assertNotNull(credit);
        assertEquals(Credit.CreditType.FIXED_INTEREST, credit.getType());
        assertEquals(Credit.FIXED_INTEREST_RATE, credit.getInterestRate());
        assertEquals(client, credit.getClient());
    }

    @Test
    void shouldCreateCreditForClient_VariableInterest() {
        // Mock client data
        NeurotechClient client = new NeurotechClient();
        client.setAge(35);
        client.setIncome(10000.0);

        // Act
        Credit credit = creditService.createCreditForClient(client);

        // Assert
        assertNotNull(credit);
        assertEquals(Credit.CreditType.VARIABLE_INTEREST, credit.getType());
        assertEquals(Credit.VARIABLE_INTEREST_RATE, credit.getInterestRate());
        assertEquals(client, credit.getClient());
    }

    @Test
    void shouldThrowExceptionForClientWithoutCreditEligibility() {
        // Mock client data
        NeurotechClient client = new NeurotechClient();
        client.setAge(17);
        client.setIncome(1000.0);

        // Act & Assert
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            creditService.createCreditForClient(client);
        });

        assertEquals("Cliente não se qualifica para nenhum crédito.", exception.getMessage());
    }
}
