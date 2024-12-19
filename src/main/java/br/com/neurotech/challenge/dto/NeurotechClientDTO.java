package br.com.neurotech.challenge.dto;


/**
 * Representa um cliente Neurotech com suas informações principais.
 *
 * @param id     Identificador único do cliente.
 * @param name   Nome do cliente.
 * @param age    Idade do cliente.
 * @param income Renda mensal do cliente.
 */
public record NeurotechClientDTO(String id, String name, Integer age, Double income) {}