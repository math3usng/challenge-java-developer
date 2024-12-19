package br.com.neurotech.challenge.form;

import br.com.neurotech.challenge.entity.NeurotechClient;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

/**
 * Classe de formulário para a entidade {@link NeurotechClient}.
 * Representa os dados necessários para a criação/atualização de um cliente.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class NeurotechClientForm {
    /**
     * Nome do cliente.
     * É um campo obrigatório que deve ser preenchido.
     */
    @NotBlank(message = "O nome é obrigatório")
    private String name;
    /**
     * Idade do cliente.
     * Deve ser maior ou igual a 18 anos.
     */
    @NotNull(message = "A idade é obrigatória")
    @Min(value = 18, message = "A idade mínima é 18 anos")
    private Integer age;
    /**
     * Renda do cliente.
     * Representa a renda declarada do cliente e é obrigatória.
     */
    @NotNull(message = "A renda é obrigatória")
    private Double income;
}
