package br.com.neurotech.challenge.form;

import br.com.neurotech.challenge.entity.NeurotechClient;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NeurotechClientForm {
    /**
     * Nome do cliente
     */
    @NotBlank(message = "O nome é obrigatório")
    private String name;
    /**
     * Idade do cliente
     */
    @NotNull(message = "A idade é obrigatória")
    @Min(value = 18, message = "A idade mínima é 18 anos")
    private Integer age;
    /**
     * Renda do cliente
     */
    @NotNull(message = "A renda é obrigatória")
    private Double income;
}
