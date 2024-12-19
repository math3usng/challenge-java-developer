package br.com.neurotech.challenge.dto;

/**
 * Um DTO que representa um cliente elegível.
 * Este record contém informações básicas sobre um cliente,
 * incluindo seu nome e renda.
 *
 * @param name   o nome do cliente
 * @param income a renda do cliente
 */
public record EligibleClientDTO(String name, Double income) {}
