package br.com.neurotech.challenge.form;

import br.com.neurotech.challenge.entity.VehicleModel;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CheckCreditForm {
    @NotNull
    private VehicleModel vehicleModel;
}
