package br.com.neurotech.challenge.dto;

import br.com.neurotech.challenge.entity.NeurotechClient;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NeurotechClientDTO {
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

    public static NeurotechClientDTO fromEntity(NeurotechClient client) {
        return new NeurotechClientDTO(client.getName(), client.getAge(), client.getIncome());
    }
}
