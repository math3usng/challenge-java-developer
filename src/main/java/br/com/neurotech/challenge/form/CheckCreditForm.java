package br.com.neurotech.challenge.form;

import br.com.neurotech.challenge.entity.VehicleModel;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Classe de formulário utilizada para operações de verificação de crédito de veículo.
 * Esta classe contém os dados necessários para realizar verificações relacionadas a crédito.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CheckCreditForm {
    /**
     * O modelo de veículo que será utilizado na verificação de crédito.
     * Este campo é obrigatório e não pode ser nulo, conforme definido pela anotação {@code @NotNull}.
     */
    @NotNull
    private VehicleModel vehicleModel;
}
