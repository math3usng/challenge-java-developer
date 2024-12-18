package br.com.neurotech.challenge.entity;

import ch.qos.logback.core.net.server.Client;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Representa um cliente da Neurotech
 */
@Data
@Entity
public class NeurotechClient {
    /**
     * Identificação unica de um cliente
     */
    @Id
    private String id;
    /**
     * Nome do cliente
     */
    @Column(nullable = false)
    private String name;
    /**
     * Idade do cliente
     */
    @Column(nullable = false)
    private Integer age;
    /**
     * Renda do cliente
     */
    @Column(nullable = false)
    private Double income;

    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Credit> credits = new ArrayList<>();

    @PrePersist
    public void generateId() {
        this.id = UUID.randomUUID().toString();
    }
}