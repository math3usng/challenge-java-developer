package br.com.neurotech.challenge.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;


/**
 * Representa uma entidade de empréstimo no sistema. 
 * Esta classe utiliza o padrão de herança SINGLE_TABLE para gerenciar diferentes tipos de empréstimos em uma única tabela.
 * Inclui informações como cliente, valor do empréstimo, quantidade de parcelas, renda do cliente e status do empréstimo.
 */
@Data
@Entity
public class Credit {

    public static final double HATCH_INCOME_MIN = 5000.0;
    public static final double HATCH_INCOME_MAX = 15000.0;
    public static final double SUV_INCOME_MIN = 8000.0;
    public static final int    SUV_AGE_MIN = 20;

    public final static Double FIXED_INTEREST_RATE = 5.0;
    public final static Double VARIABLE_INTEREST_RATE = 7.5;
    public final static Double PAYROLL_RATE = 4.0;

    /**
     * Identificador do empréstimo
     */
    @Id
    private String id;
    /**
     * Cliente que está solicitando o empréstimo
     */
    @ManyToOne
    @JoinColumn(name = "client_id", nullable = false)
    private NeurotechClient client;

    /**
     * Taxa de juros aplicada ao crédito
     */
    @Column(nullable = false)
    private Double interestRate;

    @Enumerated(EnumType.STRING)
    private CreditType type;

    @PrePersist
    public void generateId() {
        this.id = UUID.randomUUID().toString();
    }

    public enum CreditType {
        FIXED_INTEREST,       // Juros fixos
        VARIABLE_INTEREST,    // Juros variáveis
        PAYROLL               // Crédito consignado
    }
}

